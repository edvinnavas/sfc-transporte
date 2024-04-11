package Control;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
            String fecha_viaje = ctrl_base_datos.ObtenerString("SELECT A.FECHA_VIAJE FROM VIAJES A WHERE A.TIPO_ORDEN_VENTA='" + tipo_orden + "' AND A.NUMERO_ORDEN_VENTA=" + numero_orden, conn);

            Entidad.Pduno.Planta planta_origen = new Entidad.Pduno.Planta();
            planta_origen.setId_planta(id_planta);
            planta_origen.setCodigo(ctrl_base_datos.ObtenerString("SELECT P.CODIGO FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setPlanta(ctrl_base_datos.ObtenerString("SELECT P.NOMBRE FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setPais(ctrl_base_datos.ObtenerString("SELECT P.NOMBRE FROM PAIS P WHERE P.ID_PAIS=" + id_pais, conn));
            planta_origen.setPt1(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LATITUD_1 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn) + ", " + ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LONGITUD_1 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setPt2(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LATITUD_2 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn) + ", " + ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LONGITUD_2 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setPt3(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LATITUD_3 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn) + ", " + ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LONGITUD_3 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            planta_origen.setPt4(ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LATITUD_4 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn) + ", " + ctrl_base_datos.ObtenerDouble("SELECT P.ZONA_LONGITUD_4 FROM PLANTA P WHERE P.ID_PLANTA=" + id_planta, conn));
            tracking.setPlanta_origen(planta_origen);

            Entidad.Pduno.Cliente_Destino cliente_destino = new Entidad.Pduno.Cliente_Destino();
            cliente_destino.setId_cliente_destino(id_cliente_destino);
            cliente_destino.setCliente(ctrl_base_datos.ObtenerString("SELECT C.CODIGO FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setNombre(ctrl_base_datos.ObtenerString("SELECT C.NOMBRE FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setPais(ctrl_base_datos.ObtenerString("SELECT P.NOMBRE FROM PAIS P WHERE P.ID_PAIS=" + id_pais, conn));
            cliente_destino.setPt1(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LATITUD_1 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn) + "," + ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LONGITUD_1 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setPt2(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LATITUD_2 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn) + "," + ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LONGITUD_2 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setPt3(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LATITUD_3 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn) + "," + ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LONGITUD_3 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            cliente_destino.setPt4(ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LATITUD_4 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn) + "," + ctrl_base_datos.ObtenerDouble("SELECT C.ZONA_LONGITUD_4 FROM CLIENTE_DESTINO C WHERE C.ID_CLIENTE_DESTINO=" + id_cliente_destino, conn));
            tracking.setCliente_destino(cliente_destino);

            tracking.setNumero_viaje(numero_viaje);
            tracking.setStatusTiempo(estado_viaje);
            tracking.setFechahora(fecha_viaje);
            
            List<Entidad.Pduno.Ubicaciones> lst_ubicaciones = new ArrayList<>();
            String sql = "SELECT V.FECHA_HORA, V.LATITUDE, V.LONGITUDE "
                    + "FROM VIEW_VIAJE_UBICACIONES V "
                    + "WHERE V.TIPO_ORDEN_VENTA='" + tipo_orden + "' AND V.NUMERO_ORDEN_VENTA=" + numero_orden + " "
                    + "ORDER BY V.FECHA_HORA";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Entidad.Pduno.Ubicaciones ubicaciones = new Entidad.Pduno.Ubicaciones(tipo_orden + "-" + numero_orden,rs.getString(1), rs.getDouble(2), rs.getDouble(3));
                lst_ubicaciones.add(ubicaciones);
            }
            rs.close();
            stmt.close();

            tracking.setDetalle(lst_ubicaciones);

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
