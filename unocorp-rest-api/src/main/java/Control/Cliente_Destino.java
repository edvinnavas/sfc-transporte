package Control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Cliente_Destino implements Serializable {

    private static final long serialVersionUID = 1L;

    public Cliente_Destino() {
    }
    
    public String obtener_cliente_destino(String codigo_cliente_destino) {
        String resultado = "";

        Connection conn = null;

        try {
            Base_Datos ctrl_base_datos = new Base_Datos();
            conn = ctrl_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);
            
            Entidad.Cliente_Destino cliente_destino = new Entidad.Cliente_Destino();
            String cadenasql = "SELECT "
                    + "A.ID_CLIENTE_DESTINO, "
                    + "A.CODIGO, "
                    + "A.NOMBRE, "
                    + "A.ID_CLIENTE, "
                    + "A.ZONA_LATITUD_1, "
                    + "A.ZONA_LONGITUD_1, "
                    + "A.ZONA_LATITUD_2, "
                    + "A.ZONA_LONGITUD_2, "
                    + "A.ZONA_LATITUD_3, "
                    + "A.ZONA_LONGITUD_3, "
                    + "A.ZONA_LATITUD_4, "
                    + "A.ZONA_LONGITUD_4, "
                    + "A.ZONA_LATITUD_5, "
                    + "A.ZONA_LONGITUD_5 "
                    + "FROM "
                    + "CLIENTE_DESTINO A "
                    + "WHERE "
                    + "A.CODIGO='" + codigo_cliente_destino + "'";
            Statement stmt1 = conn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(cadenasql);
            while (rs1.next()) {
                Long id_cliente = ctrl_base_datos.ObtenerLong("SELECT A.ID_CLIENTE FROM CLIENTE A WHERE A.ID_CLIENTE=" + rs1.getLong(4), conn);
                Entidad.Cliente cliente = new Entidad.Cliente();
                cliente.setId_cliente(ctrl_base_datos.ObtenerLong("SELECT A.ID_CLIENTE FROM CLIENTE A WHERE A.ID_CLIENTE=" + id_cliente, conn));
                cliente.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM CLIENTE A WHERE A.ID_CLIENTE=" + id_cliente, conn));
                cliente.setNombre(ctrl_base_datos.ObtenerString("SELECT A.NOMBRE FROM CLIENTE A WHERE A.ID_CLIENTE=" + id_cliente, conn));
                
                cliente_destino.setId_cliente_destino(rs1.getLong(1));
                cliente_destino.setCodigo(rs1.getString(2));
                cliente_destino.setNombre(rs1.getString(3));
                cliente_destino.setCliente(cliente);
                cliente_destino.setZona_latitud_1(rs1.getDouble(5));
                cliente_destino.setZona_longitud_1(rs1.getDouble(6));
                cliente_destino.setZona_latitud_2(rs1.getDouble(7));
                cliente_destino.setZona_longitud_2(rs1.getDouble(8));
                cliente_destino.setZona_latitud_3(rs1.getDouble(9));
                cliente_destino.setZona_longitud_3(rs1.getDouble(10));
                cliente_destino.setZona_latitud_4(rs1.getDouble(11));
                cliente_destino.setZona_longitud_4(rs1.getDouble(12));
                cliente_destino.setZona_latitud_5(rs1.getDouble(13));
                cliente_destino.setZona_longitud_5(rs1.getDouble(14));
            }
            rs1.close();
            stmt1.close();
            
            conn.commit();
            conn.setAutoCommit(true);
            
            Gson gson = new GsonBuilder().serializeNulls().create();
            resultado = gson.toJson(cliente_destino);
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;
                    resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: obtener_cliente_destino(), ERRROR: " + ex.toString();
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: rollback-obtener_cliente_destino(), ERRROR: " + ex.toString();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: finally-obtener_cliente_destino(), ERRROR: " + ex.toString();
            }
        }

        return resultado;
    }
    
    public String modificar_geozona(Long id_cliente_destino, String coordenada1, String coordenada2) {
        String resultado = "";

        Connection conn = null;

        try {
            Base_Datos ctrl_base_datos = new Base_Datos();
            conn = ctrl_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);
            
            String[] coordenadas_x_Y_1 = coordenada1.split(",");
            String[] coordenadas_x_Y_2 = coordenada2.split(",");
            
            Double latitud_1 = Double.valueOf(coordenadas_x_Y_1[0]);
            Double longitud_1 = Double.valueOf(coordenadas_x_Y_1[1]);
            
            Double latitud_3 = Double.valueOf(coordenadas_x_Y_2[0]);
            Double longitud_3 = Double.valueOf(coordenadas_x_Y_2[1]);
            
            Double latitud_2 = latitud_3;
            Double longitud_2 = longitud_1;
            
            Double latitud_4 = latitud_1;
            Double longitud_4 = longitud_3;
            
            Double latitud_5 = latitud_1;
            Double longitud_5 = longitud_1;
            
            String cadenasql = "UPDATE CLIENTE_DESTINO SET "
                    + "ZONA_LATITUD_1=" + latitud_1 + ", "
                    + "ZONA_LONGITUD_1=" + longitud_1 + ", "
                    + "ZONA_LATITUD_2=" + latitud_2 + ", "
                    + "ZONA_LONGITUD_2=" + longitud_2 + ", "
                    + "ZONA_LATITUD_3=" + latitud_3 + ", "
                    + "ZONA_LONGITUD_3=" + longitud_3 + ", "
                    + "ZONA_LATITUD_4=" + latitud_4 + ", "
                    + "ZONA_LONGITUD_4=" + longitud_4 + ", "
                    + "ZONA_LATITUD_5=" + latitud_5 + ", "
                    + "ZONA_LONGITUD_5=" + longitud_5 + " "
                    + "WHERE "
                    + "ID_CLIENTE_DESTINO=" + id_cliente_destino;
            Statement stmt1 = conn.createStatement();
            System.out.println("CADENASQL: " + cadenasql);
            stmt1.executeUpdate(cadenasql);
            stmt1.close();
            
            conn.commit();
            conn.setAutoCommit(true);
            
            resultado = "1,Geozona Cliente-Destino modificado exitosamente.";
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;
                    resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: modificar_geozona(), ERRROR: " + ex.toString();
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: rollback-modificar_geozona(), ERRROR: " + ex.toString();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: finally-modificar_geozona(), ERRROR: " + ex.toString();
            }
        }

        return resultado;
    }
    
}
