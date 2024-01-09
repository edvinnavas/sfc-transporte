package Control;

import java.io.Serializable;
import java.sql.Connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Ctrl_Tracking implements Serializable {

    private static final long serialVersionUID = 1L;

    public String tracking(String tipo_orden, Long numero_orden) {
        String resultado = "";

        try {
            Base_Datos ctrl_base_datos = new Base_Datos();
            Connection conn = ctrl_base_datos.obtener_conexion_mysql();

            Entidad.Pduno.Tracking tracking = new Entidad.Pduno.Tracking();

            Long id_planta = ctrl_base_datos.ObtenerLong("SELECT A.ID_PLANTA FROM VIAJES A WHERE A.TIPO_ORDEN_VENTA='" + tipo_orden + "' AND A.NUMERO_ORDEN_VENTA=" + numero_orden, conn);
            Long id_cliente_destino = ctrl_base_datos.ObtenerLong("SELECT A.ID_CLIENTE_DESTINO FROM VIAJES A WHERE A.TIPO_ORDEN_VENTA='" + tipo_orden + "' AND A.NUMERO_ORDEN_VENTA=" + numero_orden, conn);
            Long id_pais = ctrl_base_datos.ObtenerLong("SELECT A.ID_PAIS FROM VIAJES A WHERE A.TIPO_ORDEN_VENTA='" + tipo_orden + "' AND A.NUMERO_ORDEN_VENTA=" + numero_orden, conn);
            Long numero_viaje = ctrl_base_datos.ObtenerLong("SELECT A.NUMERO_VIAJE FROM VIAJES A WHERE A.TIPO_ORDEN_VENTA='" + tipo_orden + "' AND A.NUMERO_ORDEN_VENTA=" + numero_orden, conn);
            String estado_viaje = ctrl_base_datos.ObtenerString("SELECT A.ESTADO FROM VIAJES A WHERE A.TIPO_ORDEN_VENTA='" + tipo_orden + "' AND A.NUMERO_ORDEN_VENTA=" + numero_orden, conn);

            Entidad.Pduno.Planta planta_origen = new Entidad.Pduno.Planta();
            planta_origen.setId_planta(id_planta);
            planta_origen.setCodigo(ctrl_base_datos.ObtenerString("SELECT P.CODIGO FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setNombre(ctrl_base_datos.ObtenerString("SELECT P.NOMBRE FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setPais(ctrl_base_datos.ObtenerString("SELECT P.NOMBRE FROM PAIS P WHERE P.ID_PAIS=" + id_pais, conn));
            planta_origen.setZona_latitud_1(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LATITUD_1 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setZona_longitud_1(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LONGITUD_1 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setZona_latitud_2(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LATITUD_2 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setZona_longitud_2(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LONGITUD_2 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setZona_latitud_3(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LATITUD_3 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setZona_longitud_3(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LONGITUD_3 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setZona_latitud_4(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LATITUD_4 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setZona_longitud_4(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LONGITUD_4 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setZona_latitud_5(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LATITUD_5 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setZona_longitud_5(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LONGITUD_5 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            tracking.setPlanta_origen(planta_origen);

            Entidad.Pduno.Cliente_Destino cliente_destino = new Entidad.Pduno.Cliente_Destino();
            cliente_destino.setId_cliente_destino(id_cliente_destino);
            cliente_destino.setCodigo(ctrl_base_datos.ObtenerString("SELECT C.CODIGO FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setNombre(ctrl_base_datos.ObtenerString("SELECT C.NOMBRE FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setPais(ctrl_base_datos.ObtenerString("SELECT P.NOMBRE FROM PAIS P WHERE P.ID_PAIS=" + id_pais, conn));
            cliente_destino.setZona_latitud_1(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LATITUD_1 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setZona_longitud_1(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LONGITUD_1 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setZona_latitud_2(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LATITUD_2 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setZona_longitud_2(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LONGITUD_2 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setZona_latitud_3(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LATITUD_3 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setZona_longitud_3(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LONGITUD_3 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setZona_latitud_4(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LATITUD_4 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setZona_longitud_4(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LONGITUD_4 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setZona_latitud_5(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LATITUD_4 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setZona_longitud_5(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LONGITUD_4 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            tracking.setCliente_destino(cliente_destino);

            tracking.setTipo_orden(tipo_orden);
            tracking.setNumero_orden(numero_orden);
            tracking.setNumero_viaje(numero_viaje);
            tracking.setEstado_viaje(estado_viaje);

            String max_fecha_hora = ctrl_base_datos.ObtenerString("SELECT MAX(A.FECHA_HORA) MAX_FECHA_HORA FROM VIAJE_UBICACIONES A WHERE A.TIPO_ORDEN_VENTA='" + tipo_orden + "' AND A.NUMERO_ORDEN_VENTA=" + numero_orden + " AND A.NUMERO_VIAJE=" + numero_viaje, conn);

            tracking.setFecha_hora(max_fecha_hora);
            tracking.setLatitud_actual(ctrl_base_datos.ObtenerDouble("SELECT A.LATITUDE FROM VIAJE_UBICACIONES A WHERE A.TIPO_ORDEN_VENTA='" + tipo_orden + "' AND A.NUMERO_ORDEN_VENTA=" + numero_orden + " AND A.NUMERO_VIAJE=" + numero_viaje + " AND A.FECHA_HORA='" + max_fecha_hora + "'", conn));
            tracking.setLongitud_actual(ctrl_base_datos.ObtenerDouble("SELECT A.LONGITUDE FROM VIAJE_UBICACIONES A WHERE A.TIPO_ORDEN_VENTA='" + tipo_orden + "' AND A.NUMERO_ORDEN_VENTA=" + numero_orden + " AND A.NUMERO_VIAJE=" + numero_viaje + " AND A.FECHA_HORA='" + max_fecha_hora + "'", conn));
            tracking.setTiempo_estimado_llegada("240 minutos");
            tracking.setDistancia_estimado_llegada("102 kilometros");

            conn.close();

            Gson gson = new GsonBuilder().serializeNulls().create();
            resultado = gson.toJson(tracking);

        } catch (Exception ex) {
            resultado = "PROYECTO:unocorp-rest-api|CLASE:" + this.getClass().getName() + "|METODO:tracking()|ERROR:" + ex.toString();
            System.out.println("PROYECTO:unocorp-rest-api|CLASE:" + this.getClass().getName() + "|METODO:tracking()|ERROR:" + ex.toString());
        }

        return resultado;
    }

}
