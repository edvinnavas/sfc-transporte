package Control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    public Usuario() {
    }

    public String autenticar(String usuario, String contrasena) {
        String resultado = "";

        Connection conn = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

            Base_Datos ctrl_base_datos = new Base_Datos();
            conn = ctrl_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            Long id_usuario = Long.valueOf("0");
            Long id_rol = Long.valueOf("0");
            String cadenasql = "SELECT "
                    + "U.ID_USUARIO, "
                    + "U.ID_ROL "
                    + "FROM "
                    + "USUARIO U "
                    + "WHERE "
                    + "U.NOMBRE_USUARIO='" + usuario + "' AND "
                    + "TRIM(CONVERT(U.CONTRASENA USING UTF8MB4))=TRIM(SHA2('" + contrasena + "',512))";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                id_usuario = rs.getLong(1);
                id_rol = rs.getLong(2);
            }
            rs.close();
            stmt.close();

            List<Entidad.Menu> lista_menu = new ArrayList<>();
            cadenasql = "SELECT "
                    + "RM.ID_ROL, "
                    + "M.ID_MENU, "
                    + "M.NOMBRE "
                    + "FROM "
                    + "ROL_MENU RM LEFT JOIN MENU M ON (RM.ID_MENU=M.ID_MENU) "
                    + "WHERE "
                    + "RM.ID_ROL=" + id_rol;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                Entidad.Menu entidad_menu = new Entidad.Menu();
                entidad_menu.setId_menu(rs.getLong(2));
                entidad_menu.setNombre(rs.getString(3));
                lista_menu.add(entidad_menu);
            }
            rs.close();
            stmt.close();

            Entidad.Rol entidad_rol = new Entidad.Rol();
            cadenasql = "SELECT "
                    + "R.ID_ROL, "
                    + "R.NOMBRE, "
                    + "R.ACTIVO, "
                    + "R.FECHA_HORA "
                    + "FROM "
                    + "ROL R "
                    + "WHERE "
                    + "R.ID_ROL=" + id_rol;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                entidad_rol.setId_rol(rs.getLong(1));
                entidad_rol.setNombre(rs.getString(2));
                entidad_rol.setActivo(rs.getInt(3));
                entidad_rol.setFecha_hora(dateFormat.format(rs.getDate(4)));
            }
            rs.close();
            stmt.close();
            entidad_rol.setLista_menu(lista_menu);

            cadenasql = "SELECT "
                    + "U.ID_USUARIO, "
                    + "U.NOMBRE_COMPLETO, "
                    + "U.NOMBRE_USUARIO, "
                    + "'SECRETO' CONTRASENA, "
                    + "U.CORREO_ELECTRONICO, "
                    + "U.ACTIVO, "
                    + "U.DESCRIPCION, "
                    + "U.FECHA_HORA "
                    + "FROM "
                    + "USUARIO U "
                    + "WHERE "
                    + "U.ID_USUARIO=" + id_usuario;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(cadenasql);
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
                entidad_usuario.setFecha_hora(dateFormat.format(rs.getDate(8)));
                entidad_usuario.setRol(entidad_rol);
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
    
    public String usuario_predio(Long id_usuario) {
        String resultado = "";

        Connection conn = null;

        try {
            Base_Datos ctrl_base_datos = new Base_Datos();
            conn = ctrl_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            Entidad.Usuario_Predio usuario_predio = new Entidad.Usuario_Predio();
            usuario_predio.setId_usuario(ctrl_base_datos.ObtenerLong("SELECT U.ID_USUARIO FROM USUARIO U WHERE U.ID_USUARIO=" + id_usuario, conn));
            usuario_predio.setNombre_usuario(ctrl_base_datos.ObtenerString("SELECT U.NOMBRE_USUARIO FROM USUARIO U WHERE U.ID_USUARIO=" + id_usuario, conn));

            List<Long> lst_id_transportista = ctrl_base_datos.ObtenerVectorLong("SELECT DISTINCT T.ID_TRANSPORTISTA FROM USUARIO_PREDIO UP LEFT JOIN USUARIO U ON (UP.ID_USUARIO=U.ID_USUARIO) LEFT JOIN PREDIO P ON (UP.ID_PREDIO=P.ID_PREDIO) LEFT JOIN TRANSPORTISTA T ON (P.ID_TRANSPORTISTA=T.ID_TRANSPORTISTA) WHERE U.ID_USUARIO=" + id_usuario, conn);
            List<Entidad.Usuario_Predio_Transportista> lst_transportista = new ArrayList<>();
            for (Integer i = 0; i < lst_id_transportista.size(); i++) {
                Entidad.Usuario_Predio_Transportista usuario_predio_transportista = new Entidad.Usuario_Predio_Transportista();
                usuario_predio_transportista.setId_transportista(lst_id_transportista.get(i));
                usuario_predio_transportista.setNombre_transportista(ctrl_base_datos.ObtenerString("SELECT T.NOMBRE FROM TRANSPORTISTA T WHERE T.ID_TRANSPORTISTA=" + lst_id_transportista.get(i), conn));

                List<Long> lst_id_predio = ctrl_base_datos.ObtenerVectorLong("SELECT DISTINCT P.ID_PREDIO FROM USUARIO_PREDIO UP LEFT JOIN USUARIO U ON (UP.ID_USUARIO=U.ID_USUARIO) LEFT JOIN PREDIO P ON (UP.ID_PREDIO=P.ID_PREDIO) LEFT JOIN TRANSPORTISTA T ON (P.ID_TRANSPORTISTA=T.ID_TRANSPORTISTA) WHERE U.ID_USUARIO=" + id_usuario + " AND T.ID_TRANSPORTISTA=" + lst_id_transportista.get(i), conn);
                List<Entidad.Usuario_Transportista_Predio> lst_predio = new ArrayList<>();
                for (Integer j = 0; j < lst_id_predio.size(); j++) {
                    Entidad.Usuario_Transportista_Predio usuario_transportista_predio = new Entidad.Usuario_Transportista_Predio();
                    usuario_transportista_predio.setId_predio(lst_id_predio.get(j));
                    usuario_transportista_predio.setNombre_predio(ctrl_base_datos.ObtenerString("SELECT P.NOMBRE FROM PREDIO P WHERE P.ID_PREDIO=" + lst_id_predio.get(j), conn));
                    lst_predio.add(usuario_transportista_predio);
                }
                usuario_predio_transportista.setLst_predio(lst_predio);
                
                lst_transportista.add(usuario_predio_transportista);
            }
            usuario_predio.setLst_transportista(lst_transportista);

            conn.commit();
            conn.setAutoCommit(true);

            Gson gson = new GsonBuilder().serializeNulls().create();
            resultado = gson.toJson(usuario_predio);
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;
                    resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: usuario_predio(), ERRROR: " + ex.toString();
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: rollback-usuario_predio(), ERRROR: " + ex.toString();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: finally-usuario_predio(), ERRROR: " + ex.toString();
            }
        }

        return resultado;
    }

}
