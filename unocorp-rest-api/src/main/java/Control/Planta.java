package Control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Planta implements Serializable {

    private static final long serialVersionUID = 1L;

    public Planta() {
    }
    
    public String lista_plantas(Long id_tranasportista) {
        String resultado = "";

        Connection conn = null;

        try {
            Base_Datos ctrl_base_datos = new Base_Datos();
            conn = ctrl_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            List<Entidad.Planta> lista_plantas = new ArrayList<>();
            
            String sql = "SELECT "
                    + "P.ID_PLANTA, "
                    + "P.CODIGO, "
                    + "P.NOMBRE, "
                    + "P.ID_COMPANIA, "
                    + "P.FECHA_HORA, "
                    + "P.ZONA_LATITUD_1, "
                    + "P.ZONA_LONGITUD_1, "
                    + "P.ZONA_LATITUD_2, "
                    + "P.ZONA_LONGITUD_2, "
                    + "P.ZONA_LATITUD_3, "
                    + "P.ZONA_LONGITUD_3, "
                    + "P.ZONA_LATITUD_4, "
                    + "P.ZONA_LONGITUD_4, "
                    + "P.ZONA_LATITUD_5, "
                    + "P.ZONA_LONGITUD_5 "
                    + "FROM "
                    + "PLANTA P "
                    + "WHERE P.ID_COMPANIA IN (SELECT C.ID_COMPANIA "
                    + "FROM COMPANIA C "
                    + "WHERE C.ID_PAIS IN (SELECT T.ID_PAIS "
                    + "FROM TRANSPORTISTA T "
                    + "WHERE T.ID_TRANSPORTISTA=" + id_tranasportista + "))";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Entidad.Planta planta = new Entidad.Planta();
                planta.setId_planta(rs.getLong(1));
                planta.setCodigo(rs.getString(2));
                planta.setNombre(rs.getString(3));
                planta.setCompania(null);
                planta.setZona_latitud_1(rs.getDouble(6));
                planta.setZona_longitud_1(rs.getDouble(7));
                planta.setZona_latitud_2(rs.getDouble(8));
                planta.setZona_longitud_2(rs.getDouble(9));
                planta.setZona_latitud_3(rs.getDouble(10));
                planta.setZona_longitud_3(rs.getDouble(11));
                planta.setZona_latitud_4(rs.getDouble(12));
                planta.setZona_longitud_4(rs.getDouble(13));
                planta.setZona_latitud_5(rs.getDouble(14));
                planta.setZona_longitud_5(rs.getDouble(15));
                
                lista_plantas.add(planta);
            }
            rs.close();
            stmt.close();
            
            conn.commit();
            conn.setAutoCommit(true);

            Gson gson = new GsonBuilder().serializeNulls().create();
            resultado = gson.toJson(lista_plantas);
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;
                    resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: lista_plantas(), ERRROR: " + ex.toString();
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: rollback-lista_plantas(), ERRROR: " + ex.toString();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: finally-lista_plantas(), ERRROR: " + ex.toString();
            }
        }

        return resultado;
    }

}
