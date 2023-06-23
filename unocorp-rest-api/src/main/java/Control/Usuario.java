package Control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    public Usuario() {
    }

    public String autenticar(String usuario, String contrasena) {
        String resultado = "";

        Connection conn = null;

        try {
            Base_Datos ctrl_base_datos = new Base_Datos();
            conn = ctrl_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            String cadenasql = "SELECT "
                    + "U.ID_USUARIO, "
                    + "U.NOMBRE_COMPLETO, "
                    + "U.NOMBRE_USUARIO, "
                    + "'SECRETO' CONTRASENA, "
                    + "U.CORREO_ELECTRONICO, "
                    + "U.ACTIVO, "
                    + "U.DESCRIPCION "
                    + "FROM "
                    + "USUARIO U "
                    + "WHERE "
                    + "U.NOMBRE_USUARIO='" + usuario + "' AND "
                    + "TRIM(CONVERT(U.CONTRASENA USING UTF8MB4))=TRIM(SHA2('" + contrasena + "',512))";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            Entidad.Usuario entidad_usuario = null;
            while (rs.next()) {
                entidad_usuario = new Entidad.Usuario();
                entidad_usuario.setId_usuario(rs.getLong(1));
                entidad_usuario.setNombre_completo(rs.getString(2));
                entidad_usuario.setNombre_usuario(rs.getString(3));
                entidad_usuario.setContrasena(rs.getString(4));
                entidad_usuario.setCorreo_electronico(rs.getString(5));
                entidad_usuario.setActivo(rs.getInt(6));
                entidad_usuario.setDescripcion(rs.getString(7));
            }
            rs.close();
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            if (entidad_usuario == null) {
                resultado = "Usuario no autenticado.";
            } else {
                Gson gson = new GsonBuilder().serializeNulls().create();
                resultado = gson.toJson(entidad_usuario);
            }
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;
                    resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: autenticar(), ERRROR: " + ex.toString();
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: rollback-autenticar(), ERRROR: " + ex.toString();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: finally-autenticar(), ERRROR: " + ex.toString();
            }
        }

        return resultado;
    }

}
