package Control;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Ctrl_Base_Datos implements Serializable {

    private static final long serialVersionUID = 1L;

    public Ctrl_Base_Datos() {
    }

    public Connection obtener_conexion() {
        Connection resultado;

        try {
            String host = "10.252.7.201";
            String sid = "jdepd";
            String user = "PRODDTA";
            String pass = "PRODDTA";

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            resultado = DriverManager.getConnection("jdbc:oracle:thin:@//" + host + ":1521/" + sid, user, pass);
            // System.out.println("Conexión Satisfactoria !!!");
        } catch (Exception ex) {
            resultado = null;
            System.out.println("PROYECTO:api-grupoterra-svfel-v3|CLASE:" + this.getClass().getName() + "|METODO:obtener_conexion()|ERROR:" + ex.toString());
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
            resultado = "PROYECTO:api-grupoterra-svfel-v3|CLASE:" + this.getClass().getName() + "|METODO:ObtenerString()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:api-grupoterra-svfel-v3|CLASE:" + this.getClass().getName() + "|METODO:ObtenerString()|ERROR:" + ex.toString());
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
            System.out.println("PROYECTO:api-grupoterra-svfel-v3|CLASE:" + this.getClass().getName() + "|METODO:ObtenerEntero()|ERROR:" + ex.toString());
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
            System.out.println("PROYECTO:api-grupoterra-svfel-v3|CLASE:" + this.getClass().getName() + "|METODO:ObtenerLong()|ERROR:" + ex.toString());
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
            System.out.println("PROYECTO:api-grupoterra-svfel-v3|CLASE:" + this.getClass().getName() + "|METODO:ObtenerDouble()|ERROR:" + ex.toString());
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
            System.out.println("PROYECTO:api-grupoterra-svfel-v3|CLASE:" + this.getClass().getName() + "|METODO:ObtenerVectorString()|ERROR:" + ex.toString());
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
            resultado = null;
            System.out.println("PROYECTO:api-grupoterra-svfel-v3|CLASE:" + this.getClass().getName() + "|METODO:ObtenerVectorEntero()|ERROR:" + ex.toString());
        }

        return resultado;
    }

}
