package Control;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Ctrl_Sfc_Transportes implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String user_rest_sfc;
    private final String pass_rest_sfc;

    public Ctrl_Sfc_Transportes(String user_rest_sfc, String pass_rest_sfc) {
        this.user_rest_sfc = user_rest_sfc;
        this.pass_rest_sfc = pass_rest_sfc;
    }

    public String obtner_viajes(String fecha) {
        String resultado = "";

        Connection conn = null;

        try {
            Control_Base_Datos control_base_datos = new Control_Base_Datos();
            conn = control_base_datos.obtener_conexion_mysql();

            conn.setAutoCommit(false);

            ClienteRest.Cliente_Rest_SFC_JDE cliente_rest_sfc_jde = new ClienteRest.Cliente_Rest_SFC_JDE(user_rest_sfc, pass_rest_sfc);
            String response_obtener_viajes = cliente_rest_sfc_jde.obtener_viajes(fecha);

            Type respuesta_ws_viaje_type = new TypeToken<Entidad.SFC.Respuesta_WS_Viaje>() {
            }.getType();
            Entidad.SFC.Respuesta_WS_Viaje respuesta_sfc_transportes = new Gson().fromJson(response_obtener_viajes, respuesta_ws_viaje_type);

            for (Integer i = 0; i < respuesta_sfc_transportes.getLista_viajes().size(); i++) {
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

                Long ID_PAIS = Long.valueOf("0");
                String sql = "SELECT P.ID_PAIS FROM PAIS P WHERE P.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_PAIS() + "'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    ID_PAIS = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_PAIS, Long.valueOf("0"))) {
                    sql = "SELECT IFNULL(MAX(P.ID_PAIS),0) FROM PAIS P";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        ID_PAIS = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_PAIS++;
                    sql = "INSERT INTO PAIS (ID_PAIS, CODIGO, NOMBRE, FECHA_HORA) VALUES ("
                            + ID_PAIS + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_PAIS() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_PAIS().replaceAll("'", "") + "',"
                            + "CURRENT_TIMESTAMP" + ")";
                    stmt = conn.createStatement();
                    // System.out.println(sql);
                    stmt.executeUpdate(sql);
                    stmt.close();
                }

                Long ID_COMPANIA = Long.valueOf("0");
                sql = "SELECT C.ID_COMPANIA FROM COMPANIA C WHERE C.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_COMPANIA() + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    ID_COMPANIA = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_COMPANIA, Long.valueOf("0"))) {
                    sql = "SELECT IFNULL(MAX(C.ID_COMPANIA),0) FROM COMPANIA C";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        ID_COMPANIA = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_COMPANIA++;
                    sql = "INSERT INTO COMPANIA (ID_COMPANIA, CODIGO, NOMBRE, ID_PAIS, FECHA_HORA) VALUES ("
                            + ID_COMPANIA + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_COMPANIA() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_COMPANIA().replaceAll("'", "") + "',"
                            + ID_PAIS + ","
                            + "CURRENT_TIMESTAMP" + ")";
                    stmt = conn.createStatement();
                    // System.out.println(sql);
                    stmt.executeUpdate(sql);
                    stmt.close();
                }

                Long ID_PLANTA = Long.valueOf("0");
                sql = "SELECT P.ID_PLANTA FROM PLANTA P WHERE P.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_PLANTA() + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    ID_PLANTA = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_PLANTA, Long.valueOf("0"))) {
                    sql = "SELECT IFNULL(MAX(P.ID_PLANTA),0) FROM PLANTA P";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        ID_PLANTA = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_PLANTA++;
                    sql = "INSERT INTO PLANTA (ID_PLANTA, CODIGO, NOMBRE, ID_COMPANIA, FECHA_HORA) VALUES ("
                            + ID_PLANTA + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_PLANTA() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_PLANTA().replaceAll("'", "") + "',"
                            + ID_COMPANIA + ","
                            + "CURRENT_TIMESTAMP" + ")";
                    stmt = conn.createStatement();
                    // System.out.println(sql);
                    stmt.executeUpdate(sql);
                    stmt.close();
                }

                Long ID_ESTADO_VIAJE = Long.valueOf("0");
                sql = "SELECT EV.ID_ESTADO_VIAJE FROM ESTADO_VIAJE EV WHERE EV.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_ESTADO_VIAJE() + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    ID_ESTADO_VIAJE = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_ESTADO_VIAJE, Long.valueOf("0"))) {
                    sql = "SELECT IFNULL(MAX(EV.ID_ESTADO_VIAJE),0) FROM ESTADO_VIAJE EV";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        ID_ESTADO_VIAJE = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_ESTADO_VIAJE++;
                    sql = "INSERT INTO ESTADO_VIAJE (ID_ESTADO_VIAJE, CODIGO, NOMBRE, FECHA_HORA) VALUES ("
                            + ID_ESTADO_VIAJE + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_ESTADO_VIAJE() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_ESTADO_VIAJE().replaceAll("'", "") + "',"
                            + "CURRENT_TIMESTAMP" + ")";
                    stmt = conn.createStatement();
                    // System.out.println(sql);
                    stmt.executeUpdate(sql);
                    stmt.close();
                }

                Long ID_TRANSPORTISTA = Long.valueOf("0");
                sql = "SELECT T.ID_TRANSPORTISTA FROM TRANSPORTISTA T WHERE T.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_TRANSPORTISTA() + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    ID_TRANSPORTISTA = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_TRANSPORTISTA, Long.valueOf("0"))) {
                    sql = "SELECT IFNULL(MAX(T.ID_TRANSPORTISTA),0) FROM TRANSPORTISTA T";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        ID_TRANSPORTISTA = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_TRANSPORTISTA++;
                    sql = "INSERT INTO TRANSPORTISTA (ID_TRANSPORTISTA, CODIGO, NOMBRE, ID_PAIS, FECHA_HORA) VALUES ("
                            + ID_TRANSPORTISTA + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_TRANSPORTISTA() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_TRANSPORTISTA().replaceAll("'", "") + "',"
                            + ID_PAIS + ","
                            + "CURRENT_TIMESTAMP" + ")";
                    stmt = conn.createStatement();
                    // System.out.println(sql);
                    stmt.executeUpdate(sql);
                    stmt.close();
                }

                Long ID_VEHICULO = Long.valueOf("0");
                sql = "SELECT V.ID_VEHICULO FROM VEHICULO V WHERE V.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getVEHICULO().replaceAll("'", "") + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    ID_VEHICULO = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_VEHICULO, Long.valueOf("0"))) {
                    sql = "SELECT IFNULL(MAX(V.ID_VEHICULO),0) FROM VEHICULO V";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        ID_VEHICULO = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_VEHICULO++;
                    sql = "INSERT INTO VEHICULO (ID_VEHICULO, CODIGO, PLACA, ID_TRANSPORTISTA, FECHA_HORA) VALUES ("
                            + ID_VEHICULO + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getVEHICULO().replaceAll("'", "") + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getPLACA_VEHICULO().replaceAll("'", "") + "',"
                            + ID_TRANSPORTISTA + ","
                            + "CURRENT_TIMESTAMP" + ")";
                    stmt = conn.createStatement();
                    // System.out.println(sql);
                    stmt.executeUpdate(sql);
                    stmt.close();
                }

                Long ID_CLIENTE = Long.valueOf("0");
                sql = "SELECT C.ID_CLIENTE FROM CLIENTE C WHERE C.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_CLIENTE() + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    ID_CLIENTE = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_CLIENTE, Long.valueOf("0"))) {
                    sql = "SELECT IFNULL(MAX(C.ID_CLIENTE),0) FROM CLIENTE C";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        ID_CLIENTE = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_CLIENTE++;
                    sql = "INSERT INTO CLIENTE (ID_CLIENTE, CODIGO, NOMBRE, FECHA_HORA) VALUES ("
                            + ID_CLIENTE + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_CLIENTE() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_CLIENTE().replaceAll("'", "") + "',"
                            + "CURRENT_TIMESTAMP" + ")";
                    stmt = conn.createStatement();
                    // System.out.println(sql);
                    stmt.executeUpdate(sql);
                    stmt.close();
                }

                Long ID_CLIENTE_DESTINO = Long.valueOf("0");
                sql = "SELECT CD.ID_CLIENTE_DESTINO FROM CLIENTE_DESTINO CD WHERE CD.CODIGO='" + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_CLIENTE_DESTINO() + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    ID_CLIENTE_DESTINO = rs.getLong(1);
                }
                rs.close();
                stmt.close();
                if (Objects.equals(ID_CLIENTE_DESTINO, Long.valueOf("0"))) {
                    sql = "SELECT IFNULL(MAX(CD.ID_CLIENTE_DESTINO),0) FROM CLIENTE_DESTINO CD";
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        ID_CLIENTE_DESTINO = rs.getLong(1);
                    }
                    rs.close();
                    stmt.close();
                    ID_CLIENTE_DESTINO++;
                    sql = "INSERT INTO CLIENTE_DESTINO (ID_CLIENTE_DESTINO, CODIGO, NOMBRE, ID_CLIENTE, FECHA_HORA) VALUES ("
                            + ID_CLIENTE_DESTINO + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getCODIGO_CLIENTE_DESTINO() + "','"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNOMBRE_CLIENTE_DESTINO().replaceAll("'", "") + "',"
                            + ID_CLIENTE + ","
                            + "CURRENT_TIMESTAMP" + ")";
                    stmt = conn.createStatement();
                    // System.out.println(sql);
                    stmt.executeUpdate(sql);
                    stmt.close();
                }

                Boolean exite_viaje = false;
                String ESTADO_ACTUAL = "";
                sql = "SELECT "
                        + "V.ID_PLANTA, "
                        + "V.NUMERO_VIAJE, "
                        + "V.ESTADO "
                        + "FROM "
                        + "VIAJES V "
                        + "WHERE "
                        + "V.ID_PAIS=" + ID_PAIS + " AND "
                        + "V.ID_COMPANIA=" + ID_COMPANIA + " AND "
                        + "V.ID_PLANTA=" + ID_PLANTA + " AND "
                        + "V.NUMERO_VIAJE=" + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_VIAJE() + " AND "
                        + "V.TIPO_ORDEN_VENTA='" + respuesta_sfc_transportes.getLista_viajes().get(i).getTIPO_ORDEN_VENTA() + "' AND "
                        + "V.NUMERO_ORDEN_VENTA=" + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_ORDEN_VENTA();
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    exite_viaje = true;
                    ESTADO_ACTUAL = rs.getString(3);
                }
                rs.close();
                stmt.close();

                String ESTADO;
                String FECHA_HORA_TERMINADO;
                if (Objects.equals(ID_ESTADO_VIAJE, Long.valueOf("2")) || Objects.equals(ID_ESTADO_VIAJE, Long.valueOf("5")) || Objects.equals(ID_ESTADO_VIAJE, Long.valueOf("10"))) {
                    ESTADO = "TER";
                    FECHA_HORA_TERMINADO = "CURRENT_TIMESTAMP";
                } else {
                    ESTADO = "ACT";
                    FECHA_HORA_TERMINADO = "NULL";
                }

                if (exite_viaje) {
                    if (!ESTADO_ACTUAL.equals("TER")) {
                        sql = "UPDATE VIAJES SET "
                                + "ID_ESTADO_VIAJE=" + ID_ESTADO_VIAJE + ", "
                                + "ESTADO='" + ESTADO + "', "
                                + "FECHA_HORA_TERMINADO=" + FECHA_HORA_TERMINADO + " "
                                + "WHERE "
                                + "ID_PAIS=" + ID_PAIS + " AND "
                                + "ID_COMPANIA=" + ID_COMPANIA + " AND "
                                + "ID_PLANTA=" + ID_PLANTA + " AND "
                                + "NUMERO_VIAJE=" + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_VIAJE() + " AND "
                                + "TIPO_ORDEN_VENTA='" + respuesta_sfc_transportes.getLista_viajes().get(i).getTIPO_ORDEN_VENTA() + "' AND "
                                + "NUMERO_ORDEN_VENTA=" + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_ORDEN_VENTA();
                        stmt = conn.createStatement();
                        // System.out.println(sql);
                        stmt.executeUpdate(sql);
                        stmt.close();
                    }
                } else {
                    sql = "INSERT INTO VIAJES ("
                            + "ID_PAIS, "
                            + "ID_COMPANIA, "
                            + "ID_PLANTA, "
                            + "NUMERO_VIAJE, "
                            + "FECHA_VIAJE, "
                            + "ID_ESTADO_VIAJE, "
                            + "ID_VEHICULO, "
                            + "ID_TRANSPORTISTA, "
                            + "TIPO_ORDEN_VENTA, "
                            + "NUMERO_ORDEN_VENTA, "
                            + "ID_CLIENTE, "
                            + "ID_CLIENTE_DESTINO, "
                            + "TIPO_FLETE_VIAJE, "
                            + "FECHA_HORA, "
                            + "ESTADO, "
                            + "FECHA_HORA_TERMINADO) VALUES ("
                            + ID_PAIS + ","
                            + ID_COMPANIA + ","
                            + ID_PLANTA + ","
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_VIAJE() + ",'"
                            + dateFormat2.format(dateFormat1.parse(respuesta_sfc_transportes.getLista_viajes().get(i).getFECHA_VIAJE())) + "',"
                            + ID_ESTADO_VIAJE + ","
                            + ID_VEHICULO + ","
                            + ID_TRANSPORTISTA + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getTIPO_ORDEN_VENTA() + "',"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getNUMERO_ORDEN_VENTA() + ","
                            + ID_CLIENTE + ","
                            + ID_CLIENTE_DESTINO + ",'"
                            + respuesta_sfc_transportes.getLista_viajes().get(i).getTIPO_FLETE_VIAJE() + "',"
                            + "CURRENT_TIMESTAMP" + ",'"
                            + ESTADO + "',"
                            + FECHA_HORA_TERMINADO + ")";
                    stmt = conn.createStatement();
                    // System.out.println(sql);
                    stmt.executeUpdate(sql);
                    stmt.close();
                }
            }
            
            Calendar fecha_cierre = Calendar.getInstance();
            fecha_cierre.add(Calendar.DATE, -2);
            
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            
            // CIERRA VIAJES CON CON ANTIGUEDAD MAYOR A 3 DIAS CON EL 82-TERMINADO MANUALMENTE.
            String sql = "UPDATE VIAJES "
                    + "SET ID_ESTADO_VIAJE=10, ESTADO='TER', FECHA_HORA_TERMINADO=CURRENT_TIMESTAMP "
                    + "WHERE ID_ESTADO_VIAJE NOT IN (2, 5, 10) AND FECHA_VIAJE < '" + dateFormat1.format(fecha_cierre.getTime()) + "'";
            Statement stmt = conn.createStatement();
            // System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.close();

            // ELIMINA GEOPOSICIONES DE SMS-OPEN CON ANTIGUEDAD MAYOR A 3 DIAS.
            sql = "DELETE FROM SMS_OPEN_DETALLE WHERE STR_TO_DATE(DATETIME_UBICACION, '%d-%m-%Y %H:%i:%s') <= '" + dateFormat1.format(fecha_cierre.getTime()) + " 23:59:59'";
            stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            // ELIMINA GEOPOSICIONES DE GEOTAB-CR CON ANTIGUEDAD MAYOR A 3 DIAS.
            sql = "DELETE FROM GEOTAB_DETALLE_CR WHERE STR_TO_DATE(DATETIME_UBICACION, '%Y-%m-%d %H:%i:%s') <= '" + dateFormat1.format(fecha_cierre.getTime()) + " 23:59:59'";
            stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            // ELIMINA GEOPOSICIONES DE GEOTAB-GT CON ANTIGUEDAD MAYOR A 3 DIAS.
            sql = "DELETE FROM GEOTAB_DETALLE_GT WHERE STR_TO_DATE(DATETIME_UBICACION, '%Y-%m-%d %H:%i:%s') <= '" + dateFormat1.format(fecha_cierre.getTime()) + " 23:59:59'";
            stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            // ELIMINA GEOPOSICIONES DE DISATEL CON ANTIGUEDAD MAYOR A 3 DIAS.
            sql = "DELETE FROM DISATEL_DETALLE WHERE STR_TO_DATE(DATETIME_UBICACION, '%Y/%m/%d %H:%i:%s') <= '" + dateFormat1.format(fecha_cierre.getTime()) + " 23:59:59'";
            stmt = conn.createStatement();
            // System.out.println("SQL: " + sql);
            stmt.executeUpdate(sql);
            stmt.close();

            sql = "SELECT " 
                    + "V.ID_PAIS, "
                    + "V.ID_COMPANIA, "
                    + "V.ID_PLANTA, "
                    + "V.NUMERO_VIAJE, "
                    + "V.TIPO_ORDEN_VENTA, "
                    + "V.NUMERO_ORDEN_VENTA, "
                    + "V.ID_CLIENTE_DESTINO, "
                    + "VU.LATITUDE, "
                    + "VU.LONGITUDE, "
                    + "VU.FECHA_HORA "
                    + "FROM "
                    + "VIAJES V "
                    + "LEFT JOIN VIEW_VIAJE_UBICACIONES VU ON (V.ID_PAIS=VU.ID_PAIS AND V.ID_COMPANIA=VU.ID_COMPANIA AND V.ID_PLANTA=VU.ID_PLANTA AND V.NUMERO_VIAJE=VU.NUMERO_VIAJE AND V.TIPO_ORDEN_VENTA=VU.TIPO_ORDEN_VENTA AND V.NUMERO_ORDEN_VENTA=VU.NUMERO_ORDEN_VENTA) "
                    + "WHERE "
                    + "(V.ID_ESTADO_VIAJE NOT IN (2, 5, 10)) AND "
                    + "(VU.LATITUDE IS NOT NULL) AND "
                    + "(VU.LONGITUDE IS NOT NULL) AND "
                    + "(VU.FECHA_HORA IS NOT NULL)";
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Long ID_PAIS = rs.getLong(1);
                Long ID_COMPANIA = rs.getLong(2);
                Long ID_PLANTA = rs.getLong(3);
                Long NUMERO_VIAJE = rs.getLong(4);
                String TIPO_ORDEN_VENTA = rs.getString(5);
                Long NUMERO_ORDEN_VENTA = rs.getLong(6);
                Long ID_CLIENTE_DESTINO = rs.getLong(7);
                Double LATITUDE = rs.getDouble(8);
                Double LONGITUDE = rs.getDouble(9);
                String FECHA_HORA = rs.getString(10);

                if (ID_CLIENTE_DESTINO != null) {
                    Double ZONA_LATITUD_1 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LATITUD_1,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LONGITUD_1 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LONGITUD_1,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LATITUD_2 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LATITUD_2,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LONGITUD_2 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LONGITUD_2,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LATITUD_3 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LATITUD_3,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LONGITUD_3 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LONGITUD_3,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LATITUD_4 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LATITUD_4,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LONGITUD_4 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LONGITUD_4,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LATITUD_5 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LATITUD_5,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);
                    Double ZONA_LONGITUD_5 = control_base_datos.ObtenerDouble("SELECT IFNULL(CD.ZONA_LONGITUD_5,0.00) UBU FROM CLIENTE_DESTINO CD WHERE CD.ID_CLIENTE_DESTINO=" + ID_CLIENTE_DESTINO, conn);

                    Point geozona[] = {
                        new Point(ZONA_LATITUD_1, ZONA_LONGITUD_1),
                        new Point(ZONA_LATITUD_2, ZONA_LONGITUD_2),
                        new Point(ZONA_LATITUD_3, ZONA_LONGITUD_3),
                        new Point(ZONA_LATITUD_4, ZONA_LONGITUD_4),
                        new Point(ZONA_LATITUD_5, ZONA_LONGITUD_5)
                    };

                    Point ubicacion_actual = new Point(LATITUDE, LONGITUDE);

                    Poligono poligono = new Poligono();
                    if (poligono.isInside(geozona, 5, ubicacion_actual)) {
                        // System.out.println("GEO-ZONA: {"
                                // + "(" + ZONA_LATITUD_1 + "," + ZONA_LONGITUD_1 + ");"
                                // + "(" + ZONA_LATITUD_2 + "," + ZONA_LONGITUD_2 + ");"
                                // + "(" + ZONA_LATITUD_3 + "," + ZONA_LONGITUD_3 + ");"
                                // + "(" + ZONA_LATITUD_4 + "," + ZONA_LONGITUD_4 + ");"
                                // + "(" + ZONA_LATITUD_5 + "," + ZONA_LONGITUD_5 + ")"
                                // + "}"); 
                        // System.out.println("UBICACIÓN-ACTUAL: {(" + LATITUDE + "," + LONGITUDE + ")}");

                        sql = "UPDATE "
                                + "VIAJES "
                                + "SET "
                                + "ID_ESTADO_VIAJE=5, "
                                + "ESTADO='TER', "
                                + "FECHA_HORA_TERMINADO='" + FECHA_HORA + "' "
                                + "WHERE "
                                + "ID_PAIS=" + ID_PAIS + " AND "
                                + "ID_COMPANIA=" + ID_COMPANIA + " AND "
                                + "ID_PLANTA=" + ID_PLANTA + " AND "
                                + "NUMERO_VIAJE=" + NUMERO_VIAJE + " AND "
                                + "TIPO_ORDEN_VENTA='" + TIPO_ORDEN_VENTA + "' AND "
                                + "NUMERO_ORDEN_VENTA=" + NUMERO_ORDEN_VENTA;
                        Statement stmt1 = conn.createStatement();
                        System.out.println("SQL: " + sql);
                        stmt1.executeUpdate(sql);
                        stmt1.close();
                    }
                }
            }
            rs.close();
            stmt.close();

            conn.commit();
            conn.setAutoCommit(true);

            resultado = "VIAJES ACTUALIZADOS EN TRANSPORTE-GPS.";
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;

                    resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:obtner_viajes()|ERROR:" + ex.toString();
                    System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:obtner_viajes()|ERROR:" + ex.toString());
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:obtner_viajes_rollback()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:obtner_viajes_rollback()|ERROR:" + ex.toString());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:obtner_viajes_finally()|ERROR:" + ex.toString();
                System.out.println("PROYECTO:unocorp-clientes-rest|CLASE:" + this.getClass().getName() + "|METODO:obtner_viajes_finally()|ERROR:" + ex.toString());
            }
        }

        return resultado;
    }

}
