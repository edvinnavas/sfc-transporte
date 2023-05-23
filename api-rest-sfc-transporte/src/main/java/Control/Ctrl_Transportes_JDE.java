package Control;

import Entidad.Respuesta_WS_Viaje;
import Entidad.Viaje;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Ctrl_Transportes_JDE implements Serializable {

    private static final long serialVersionUID = 1L;

    public Respuesta_WS_Viaje obtener_viajes(String fecha) {
        Respuesta_WS_Viaje resultado = new Respuesta_WS_Viaje();

        try {
            List<Viaje> lista_viajes = new ArrayList<>();

            Ctrl_Base_Datos ctrl_base_datos = new Ctrl_Base_Datos();
            Connection conn = ctrl_base_datos.obtener_conexion();

            Long TMLOAD = ctrl_base_datos.ObtenerLong("SELECT TO_NUMBER(SUBSTR(TO_CHAR(TO_DATE('" + fecha + "','YYYYMMDD')-10,'ccYYddd'),2,6)) IVD FROM DUAL", conn);
            System.out.println("********** TMLOAD: " + TMLOAD);

            String cadenasql = "SELECT DISTINCT "
                    + "(SELECT TRIM(B.DRDL01) FROM PRODCTL.F0005 B WHERE TRIM(B.DRSY)='00' AND TRIM(B.DRRT)='CN' AND TRIM(B.DRKY)=(SELECT TRIM(B.ALCTR) FROM PRODDTA.F0116 B WHERE ROWNUM=1 AND B.ALAN8=C.TOKCOO)) PAIS, "
                    + "C.TOKCOO CODIGO_COMPANIA, "
                    + "(SELECT TRIM(B.ABALPH) FROM PRODDTA.F0101 B WHERE B.ABAN8=C.TOKCOO) NOMBRE_COMPANIA, "
                    + "TRIM(A.TMVMCU) CODIGO_PLANTA, "
                    + "(SELECT TRIM(B.ABALPH) FROM PRODDTA.F0101 B WHERE B.ABAN8=A.TMVMCU) NOMBRE_PLANTA, "
                    + "A.TMLDNM NO_VIAJE, "
                    + "TO_CHAR(TO_DATE(TO_CHAR(A.TMLOAD + 1900000,'9999999'),'YYYYDDD'),'dd-MM-yyyy') FECHA_VIAJE, "
                    + "A.TMLDLS CODIGO_ESTADO_VIAJE, "
                    + "(SELECT TRIM(B.DRDL01) FROM PRODCTL.F0005 B WHERE TRIM(B.DRSY)='49' AND TRIM(B.DRRT)='SL' AND TRIM(B.DRKY)=A.TMLDLS) ESTADO_VIAJE, "
                    + "TRIM(A.TMPVEH) VEHICULO, "
                    + "A.TMCARS CODIGO_TRANSPORTISTA, "
                    + "(SELECT TRIM(B.ABALPH) FROM PRODDTA.F0101 B WHERE B.ABAN8=A.TMCARS) NOMBRE_TRANSPORTISTA, "
                    + "C.TODCTO TIPO_ORDEN_VENTA, "
                    + "C.TODOCO NO_ORDEN_VENTA, "
                    + "(SELECT DISTINCT SO.SDAN8 FROM (SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, SDAN8 FROM PRODDTA.F4211 B UNION ALL SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, SDAN8 FROM PRODDTA.F42119 B) SO WHERE SO.SDDCTO=C.TODCTO AND SO.SDDOCO=C.TODOCO) CODIGO_CLIENTE, "
                    + "(SELECT TRIM(B.ABALPH) FROM PRODDTA.F0101 B WHERE B.ABAN8=(SELECT DISTINCT SO.SDAN8 FROM (SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, SDAN8 FROM PRODDTA.F4211 B UNION ALL SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, SDAN8 FROM PRODDTA.F42119 B) SO WHERE SO.SDDCTO=C.TODCTO AND SO.SDDOCO=C.TODOCO)) NOMBRE_CLIENTE, "
                    + "(SELECT DISTINCT SO.SDSHAN FROM (SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, SDSHAN FROM PRODDTA.F4211 B UNION ALL SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, SDSHAN FROM PRODDTA.F42119 B) SO WHERE SO.SDDCTO=C.TODCTO AND SO.SDDOCO=C.TODOCO) CODIGO_CLIENTE_DESTINO, "
                    + "(SELECT TRIM(B.ABALPH) FROM PRODDTA.F0101 B WHERE B.ABAN8=(SELECT DISTINCT SO.SDSHAN FROM (SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, SDSHAN FROM PRODDTA.F4211 B UNION ALL SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, SDSHAN FROM PRODDTA.F42119 B) SO WHERE SO.SDDCTO=C.TODCTO AND SO.SDDOCO=C.TODOCO)) NOMBRE_CLIENTE_DESTINO "
                    + "FROM "
                    + "PRODDTA.F4960 A LEFT JOIN PRODDTA.F49631 C ON (A.TMVMCU=C.TOVMCU AND A.TMLDNM=C.TOLDNM) "
                    + "WHERE "
                    + "A.TMLOAD=" + TMLOAD + " AND TRIM(A.TMURCD) IS NULL AND C.TODOCO>0";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                Viaje viaje = new Viaje();
                viaje.setPAIS(rs.getString(1));
                viaje.setCODIGO_COMPANIA(rs.getString(2));
                viaje.setNOMBRE_COMPANIA(rs.getString(3));
                viaje.setCODIGO_PLANTA(rs.getLong(4));
                viaje.setNOMBRE_PLANTA(rs.getString(5));
                viaje.setNO_VIAJE(rs.getLong(6));
                viaje.setFECHA_VIAJE(rs.getString(7));
                viaje.setCODIGO_ESTADO_VIAJE(rs.getString(8));
                viaje.setESTADO_VIAJE(rs.getString(9));
                viaje.setVEHICULO(rs.getString(10));
                viaje.setCODIGO_TRANSPORTISTA(rs.getLong(11));
                viaje.setNOMBRE_TRANSPORTISTA(rs.getString(12));
                viaje.setTIPO_ORDEN_VENTA(rs.getString(13));
                viaje.setNO_ORDEN_VENTA(rs.getLong(14));
                viaje.setCODIGO_CLIENTE(rs.getLong(15));
                viaje.setNOMBRE_CLIENTE(rs.getString(16));
                viaje.setCODIGO_CLIENTE_DESTINO(rs.getLong(17));
                viaje.setNOMBRE_CLIENTE_DESTINO(rs.getString(18));
                lista_viajes.add(viaje);
            }
            rs.close();
            stmt.close();

            conn.close();

            resultado.setMensaje("Lista de viajes cargada correctamente.");
            resultado.setNumero_viajes(lista_viajes.size());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            resultado.setFecha_viajes(dateFormat.parse(fecha));
            resultado.setLista_viajes(lista_viajes);

        } catch (Exception ex) {
            resultado.setMensaje("PROYECTO:api-grupoterra-svfel-v3|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes()|ERROR:" + ex.toString());
            resultado.setLista_viajes(null);
            System.out.println("PROYECTO:api-grupoterra-svfel-v3|CLASE:" + this.getClass().getName() + "|METODO:obtener_viajes()|ERROR:" + ex.toString());
        }

        return resultado;
    }

}
