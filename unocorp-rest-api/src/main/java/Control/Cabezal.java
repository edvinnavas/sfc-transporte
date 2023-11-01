package Control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Cabezal implements Serializable {

    private static final long serialVersionUID = 1L;

    public Cabezal() {
    }
    
    public String lista_cabezales(Long id_tranasportista, Long id_predio) {
        String resultado = "";

        Connection conn = null;

        try {
            Base_Datos ctrl_base_datos = new Base_Datos();
            conn = ctrl_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            List<Entidad.Cabezal> lista_cabezales = new ArrayList<>();
            
            String sql = "SELECT "
                    + "C.ID_CABEZAL, "
                    + "C.CODIGO, "
                    + "C.PLACA, "
                    + "C.IMEI "
                    + "FROM CABEZAL C "
                    + "WHERE "
                    + "C.ID_TRANSPORTISTA=" + id_tranasportista + " AND "
                    + "C.ID_PREDIO=" + id_predio;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Entidad.Cabezal cabezal = new Entidad.Cabezal();
                cabezal.setId_cabezal(rs.getLong(1));
                cabezal.setCodigo(rs.getString(2));
                cabezal.setPlaca(rs.getString(3));
                cabezal.setImei(rs.getString(4));
                cabezal.setTransportista(null);
                
                lista_cabezales.add(cabezal);
            }
            rs.close();
            stmt.close();
            
            conn.commit();
            conn.setAutoCommit(true);

            Gson gson = new GsonBuilder().serializeNulls().create();
            resultado = gson.toJson(lista_cabezales);
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;
                    resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: lista_cabezales(), ERRROR: " + ex.toString();
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: rollback-lista_cabezales(), ERRROR: " + ex.toString();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: finally-lista_cabezales(), ERRROR: " + ex.toString();
            }
        }

        return resultado;
    }

}
