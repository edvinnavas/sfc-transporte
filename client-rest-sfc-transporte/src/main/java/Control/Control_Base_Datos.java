package Control;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Control_Base_Datos implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public Control_Base_Datos() {
    }

    public Connection obtener_conexion_mysql() {
        Connection resultado;

        try {
            String host_mysql_db = "UNOCORP-MYSQL";
            String usuario_db = "user_transportes";
            String contrasena_db = "TransGPS2023";

            Class.forName("com.mysql.cj.jdbc.Driver");
            resultado = DriverManager.getConnection("jdbc:mysql://" + host_mysql_db + ":3306/db_transportes", usuario_db, contrasena_db);
            // System.out.println("Conexión satisfactoria: " + usuario_db);
        } catch (Exception ex) {
            resultado = null;
            System.out.println("PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:obtener_conexion_mysql()|ERROR:" + ex.toString());
        }

        return resultado;
    }

    public String ObtenerString(String cadenasql, Connection conn) {
        String resultado = null;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado = rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            resultado = "PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:ObtenerString()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:ObtenerString()|ERROR:" + ex.toString());
        }

        return resultado;
    }

    public Integer ObtenerEntero(String cadenasql, Connection conn) {
        Integer resultado = null;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            resultado = -1;
            System.out.println("PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:ObtenerEntero()|ERROR:" + ex.toString());
        }

        return resultado;
    }

    public Long ObtenerLong(String cadenasql, Connection conn) {
        Long resultado = null;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado = rs.getLong(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            resultado = Long.valueOf(-1);
            System.out.println("PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:ObtenerLong()|ERROR:" + ex.toString());
        }

        return resultado;
    }

    public Double ObtenerDouble(String cadenasql, Connection conn) {
        Double resultado = null;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado = rs.getDouble(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            resultado = -1.00;
            System.out.println("PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:ObtenerDouble()|ERROR:" + ex.toString());
        }

        return resultado;
    }

    public List<String> ObtenerVectorString(String cadenasql, Connection conn) {
        List<String> resultado = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            resultado = new ArrayList<>();
            resultado.add(ex.toString());
            System.out.println("PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:ObtenerVectorString()|ERROR:" + ex.toString());
        }

        return resultado;
    }

    public List<Integer> ObtenerVectorEntero(String cadenasql, Connection conn) {
        List<Integer> resultado = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                resultado.add(rs.getInt(1));
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            resultado = new ArrayList<>();
            resultado.add(-1);
            System.out.println("PROYECTO:client-rest-sfc-transporte|CLASE:" + this.getClass().getName() + "|METODO:ObtenerVectorEntero()|ERROR:" + ex.toString());
        }

        return resultado;
    }

}
