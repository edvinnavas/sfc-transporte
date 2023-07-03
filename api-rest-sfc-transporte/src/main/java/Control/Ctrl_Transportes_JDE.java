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

            Long TMLOAD = ctrl_base_datos.ObtenerLong("SELECT TO_NUMBER(SUBSTR(TO_CHAR(TO_DATE('" + fecha + "','YYYYMMDD'),'ccYYddd'),2,6)) IVD FROM DUAL", conn);
            System.out.println("********** TMLOAD: " + TMLOAD);

            String cadenasql = "SELECT DISTINCT "
                    + "(SELECT TRIM(B.ALCTR) FROM PRODDTA.F0116 B WHERE ROWNUM=1 AND B.ALAN8=C.TOKCOO) CODIGO_PAIS, "
                    + "(SELECT TRIM(B.DRDL01) FROM PRODCTL.F0005 B WHERE TRIM(B.DRSY)='00' AND TRIM(B.DRRT)='CN' AND TRIM(B.DRKY)=(SELECT TRIM(B.ALCTR) FROM PRODDTA.F0116 B WHERE ROWNUM=1 AND B.ALAN8=C.TOKCOO)) NOMBRE_PAIS, "
                    + "C.TOKCOO CODIGO_COMPANIA, "
                    + "(SELECT TRIM(B.ABALPH) FROM PRODDTA.F0101 B WHERE B.ABAN8=C.TOKCOO) NOMBRE_COMPANIA, "
                    + "TRIM(A.TMVMCU) CODIGO_PLANTA, "
                    + "(SELECT TRIM(B.ABALPH) FROM PRODDTA.F0101 B WHERE B.ABAN8=A.TMVMCU) NOMBRE_PLANTA, "
                    + "A.TMLDNM NUMERO_VIAJE, "
                    + "TO_CHAR(TO_DATE(TO_CHAR(A.TMLOAD + 1900000,'9999999'),'YYYYDDD'),'dd-MM-yyyy') FECHA_VIAJE, "
                    + "A.TMLDLS CODIGO_ESTADO_VIAJE, "
                    + "(SELECT TRIM(B.DRDL01) FROM PRODCTL.F0005 B WHERE TRIM(B.DRSY)='49' AND TRIM(B.DRRT)='SL' AND TRIM(B.DRKY)=A.TMLDLS) NOMBRE_ESTADO_VIAJE, "
                    + "TRIM(A.TMPVEH) VEHICULO, "
                    + "(SELECT TRIM(B.VMVEHS) FROM PRODDTA.F4930 B WHERE TRIM(B.VMVEHI)=TRIM(A.TMPVEH) AND ROWNUM=1) PLACA_VEHICULO, "
                    + "A.TMCARS CODIGO_TRANSPORTISTA, "
                    + "(SELECT TRIM(B.ABALPH) FROM PRODDTA.F0101 B WHERE B.ABAN8=A.TMCARS) NOMBRE_TRANSPORTISTA, "
                    + "C.TODCTO TIPO_ORDEN_VENTA, "
                    + "C.TODOCO NUMERO_ORDEN_VENTA, "
                    + "(SELECT DISTINCT SO.SDAN8 FROM (SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, B.SDAN8 FROM PRODDTA.F4211 B UNION ALL SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, B.SDAN8 FROM PRODDTA.F42119 B) SO WHERE SO.SDDCTO=C.TODCTO AND SO.SDDOCO=C.TODOCO AND ROWNUM=1) CODIGO_CLIENTE, "
                    + "(SELECT TRIM(B.ABALPH) FROM PRODDTA.F0101 B WHERE B.ABAN8=(SELECT DISTINCT SO.SDAN8 FROM (SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, B.SDAN8 FROM PRODDTA.F4211 B UNION ALL SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, B.SDAN8 FROM PRODDTA.F42119 B) SO WHERE SO.SDDCTO=C.TODCTO AND SO.SDDOCO=C.TODOCO AND ROWNUM=1)) NOMBRE_CLIENTE, "
                    + "(SELECT DISTINCT SO.SDSHAN FROM (SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, B.SDSHAN FROM PRODDTA.F4211 B UNION ALL SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, B.SDSHAN FROM PRODDTA.F42119 B) SO WHERE SO.SDDCTO=C.TODCTO AND SO.SDDOCO=C.TODOCO AND ROWNUM=1) CODIGO_CLIENTE_DESTINO, "
                    + "(SELECT TRIM(B.ABALPH) FROM PRODDTA.F0101 B WHERE B.ABAN8=(SELECT DISTINCT SO.SDSHAN FROM (SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, B.SDSHAN FROM PRODDTA.F4211 B UNION ALL SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, B.SDSHAN FROM PRODDTA.F42119 B) SO WHERE SO.SDDCTO=C.TODCTO AND SO.SDDOCO=C.TODOCO AND ROWNUM=1) AND ROWNUM=1) NOMBRE_CLIENTE_DESTINO, "
                    + "(SELECT DISTINCT NVL(TRIM(SO.SDZON),'-') FROM (SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, B.SDSHAN, B.SDZON FROM PRODDTA.F4211 B UNION ALL SELECT B.SDKCOO, B.SDDCTO, B.SDDOCO, B.SDSHAN, B.SDZON FROM PRODDTA.F42119 B) SO WHERE SO.SDDCTO=C.TODCTO AND SO.SDDOCO=C.TODOCO AND ROWNUM=1) TIPO_FLETE_VIAJE "
                    + "FROM "
                    + "PRODDTA.F4960 A LEFT JOIN PRODDTA.F49631 C ON (A.TMVMCU=C.TOVMCU AND A.TMLDNM=C.TOLDNM) "
                    + "WHERE "
                    + "A.TMLOAD=" + TMLOAD + " AND C.TODOCO>0 AND TRIM(C.TOKCOO) IS NOT NULL";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while (rs.next()) {
                Viaje viaje = new Viaje();
                viaje.setCODIGO_PAIS(rs.getString(1));
                viaje.setNOMBRE_PAIS(rs.getString(2));
                viaje.setCODIGO_COMPANIA(rs.getString(3));
                viaje.setNOMBRE_COMPANIA(rs.getString(4));
                viaje.setCODIGO_PLANTA(rs.getLong(5));
                viaje.setNOMBRE_PLANTA(rs.getString(6));
                viaje.setNUMERO_VIAJE(rs.getLong(7));
                viaje.setFECHA_VIAJE(rs.getString(8));
                viaje.setCODIGO_ESTADO_VIAJE(rs.getLong(9));
                viaje.setNOMBRE_ESTADO_VIAJE(rs.getString(10));
                viaje.setVEHICULO(rs.getString(11));
                viaje.setPLACA_VEHICULO(rs.getString(12));
                viaje.setCODIGO_TRANSPORTISTA(rs.getLong(13));
                viaje.setNOMBRE_TRANSPORTISTA(rs.getString(14));
                viaje.setTIPO_ORDEN_VENTA(rs.getString(15));
                viaje.setNUMERO_ORDEN_VENTA(rs.getLong(16));
                viaje.setCODIGO_CLIENTE(rs.getLong(17));
                viaje.setNOMBRE_CLIENTE(rs.getString(18));
                viaje.setCODIGO_CLIENTE_DESTINO(rs.getLong(19));
                viaje.setNOMBRE_CLIENTE_DESTINO(rs.getString(20));
                viaje.setTIPO_FLETE_VIAJE(rs.getString(21));
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
