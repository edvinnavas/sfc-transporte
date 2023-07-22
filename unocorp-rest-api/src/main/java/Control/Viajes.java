package Control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Viajes implements Serializable {

    private static final long serialVersionUID = 1L;

    public Viajes() {
    }

    public String lista_viajes(String fecha_inicio, String fecha_final, String estado, String tipo_flete, String rastreable) {
        String resultado = "";
        
        Connection conn = null;

        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            
            Base_Datos ctrl_base_datos = new Base_Datos();
            conn = ctrl_base_datos.obtener_conexion_mysql();
            
            conn.setAutoCommit(false);
            
            List<Entidad.Viaje> lista_viajes = new ArrayList<>();
            
            if(estado.equals("Ambos")) {
                estado = "%%";
            }
            
            if (tipo_flete.equals("Ambos")) {
                tipo_flete = "LIKE '%%'";
            } else {
                if (tipo_flete.equals("FOB")) {
                    tipo_flete = "LIKE '%FOB%'";
                } else {
                    tipo_flete = "NOT LIKE '%FOB%'";
                }
            }
            
            if(rastreable.equals("SI")) {
                rastreable = "1";
            } else {
                rastreable = "0, 1";
            }
            
            String cadenasql = "SELECT "
                    + "V.ID_PAIS, "
                    + "V.ID_COMPANIA, "
                    + "V.ID_PLANTA, "
                    + "V.NUMERO_VIAJE, "
                    + "V.FECHA_VIAJE, "
                    + "V.ID_ESTADO_VIAJE, "
                    + "V.ID_VEHICULO, "
                    + "V.ID_TRANSPORTISTA, "
                    + "V.TIPO_ORDEN_VENTA, "
                    + "V.NUMERO_ORDEN_VENTA, "
                    + "V.ID_CLIENTE, "
                    + "V.ID_CLIENTE_DESTINO, "
                    + "V.TIPO_FLETE_VIAJE, "
                    + "V.FECHA_HORA, "
                    + "V.ESTADO, "
                    + "IFNULL(V.FECHA_HORA_TERMINADO, DATE('2000-01-01 00:00:00')) FECHA_HORA_TERMINADO, "
                    + "CASE WHEN VD.CODIGO IS NULL THEN 'NO' ELSE 'SI' END DISPONIBILIDAD, "
                    + "IFNULL(VD.ID_VEHICULO, 0) CISTERNA_DISPONIBILIDAD, "
                    + "IFNULL(CD.ID_CABEZAL, 0) CABEZAL_DISPONIBILIDAD "
                    + "FROM "
                    + "VIAJES V "
                    + "LEFT JOIN TRANSPORTISTA T ON (V.ID_TRANSPORTISTA=T.ID_TRANSPORTISTA) "
                    + "LEFT JOIN DISPONIBILIDAD D ON (V.ID_TRANSPORTISTA=D.ID_TRANSPORTISTA AND V.ID_VEHICULO=D.ID_VEHICULO AND D.FECHA BETWEEN '" + dateFormat2.format(dateFormat1.parse(fecha_inicio)) + "' AND '" + dateFormat2.format(dateFormat1.parse(fecha_final)) + "') "
                    + "LEFT JOIN VEHICULO VD ON (D.ID_VEHICULO=VD.ID_VEHICULO) "
                    + "LEFT JOIN CABEZAL CD ON (D.ID_CABEZAL=CD.ID_CABEZAL) "
                    + "WHERE "
                    + "V.FECHA_VIAJE BETWEEN '" + dateFormat2.format(dateFormat1.parse(fecha_inicio)) + "' AND '" + dateFormat2.format(dateFormat1.parse(fecha_final)) + "' AND "
                    + "V.ESTADO LIKE '" + estado + "' AND "
                    + "V.TIPO_FLETE_VIAJE " + tipo_flete + " AND "
                    + "T.RASTREABLE IN (" + rastreable + ")";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(cadenasql);
            while(rs.next()) {
                Entidad.Viaje viaje = new Entidad.Viaje();
                
                Entidad.Pais pais = new Entidad.Pais();
                pais.setId_pais(rs.getLong(1));
                pais.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM PAIS A WHERE A.ID_PAIS=" + rs.getLong(1), conn));
                pais.setNombre(ctrl_base_datos.ObtenerString("SELECT A.NOMBRE FROM PAIS A WHERE A.ID_PAIS=" + rs.getLong(1), conn));
                viaje.setPais(pais);

                Entidad.Compania compania = new Entidad.Compania();
                compania.setId_compania(rs.getLong(2));
                compania.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM COMPANIA A WHERE A.ID_COMPANIA=" + rs.getLong(2), conn));
                compania.setNombre(ctrl_base_datos.ObtenerString("SELECT A.NOMBRE FROM COMPANIA A WHERE A.ID_COMPANIA=" + rs.getLong(2), conn));
                compania.setPais(pais);
                viaje.setCompania(compania);
                
                Entidad.Planta planta = new Entidad.Planta();
                planta.setId_planta(rs.getLong(3));
                planta.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM PLANTA A WHERE A.ID_PLANTA=" + rs.getLong(3), conn));
                planta.setNombre(ctrl_base_datos.ObtenerString("SELECT A.NOMBRE FROM PLANTA A WHERE A.ID_PLANTA=" + rs.getLong(3), conn));
                planta.setCompania(compania);
                viaje.setPlanta(planta);
                
                viaje.setNumero_viaje(rs.getLong(4));
                viaje.setFecha_viaje(rs.getString(5));
                
                Entidad.Estado_Viaje estado_viaje = new Entidad.Estado_Viaje();
                estado_viaje.setId_estado_viaje(rs.getLong(6));
                estado_viaje.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM ESTADO_VIAJE A WHERE A.ID_ESTADO_VIAJE=" + rs.getLong(6), conn));
                estado_viaje.setNombre(ctrl_base_datos.ObtenerString("SELECT A.NOMBRE FROM ESTADO_VIAJE A WHERE A.ID_ESTADO_VIAJE=" + rs.getLong(6), conn));
                viaje.setEstado_viaje(estado_viaje);
                
                Entidad.Transportista transportista = new Entidad.Transportista();
                transportista.setId_transportista(rs.getLong(7));
                transportista.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM TRANSPORTISTA A WHERE A.ID_TRANSPORTISTA=" + rs.getLong(8), conn));
                transportista.setNombre(ctrl_base_datos.ObtenerString("SELECT A.NOMBRE FROM TRANSPORTISTA A WHERE A.ID_TRANSPORTISTA=" + rs.getLong(8), conn));
                transportista.setPais(pais);
                transportista.setRastreable(ctrl_base_datos.ObtenerEntero("SELECT A.RASTREABLE FROM TRANSPORTISTA A WHERE A.ID_TRANSPORTISTA=" + rs.getLong(8), conn));
                viaje.setTransportista(transportista);
                
                Entidad.Vehiculo vehiculo = new Entidad.Vehiculo();
                vehiculo.setId_vehiculo(rs.getLong(8));
                vehiculo.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM VEHICULO A WHERE A.ID_VEHICULO=" + rs.getLong(7), conn));
                vehiculo.setPlaca(ctrl_base_datos.ObtenerString("SELECT A.PLACA FROM VEHICULO A WHERE A.ID_VEHICULO=" + rs.getLong(7), conn));
                vehiculo.setTransportista(transportista);
                viaje.setVehiculo(vehiculo);
                
                viaje.setTipo_orden_venta(rs.getString(9));
                viaje.setNumero_orden_venta(rs.getLong(10));
                
                Entidad.Cliente cliente = new Entidad.Cliente();
                cliente.setId_cliente(rs.getLong(11));
                cliente.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM CLIENTE A WHERE A.ID_CLIENTE=" + rs.getLong(11), conn));
                cliente.setNombre(ctrl_base_datos.ObtenerString("SELECT A.NOMBRE FROM CLIENTE A WHERE A.ID_CLIENTE=" + rs.getLong(11), conn));
                viaje.setCliente(cliente);
                
                Entidad.Cliente_Destino cliente_destino = new Entidad.Cliente_Destino();
                cliente_destino.setId_cliente_destino(rs.getLong(12));
                cliente_destino.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM CLIENTE_DESTINO A WHERE A.ID_CLIENTE_DESTINO=" + rs.getLong(12), conn));
                cliente_destino.setNombre(ctrl_base_datos.ObtenerString("SELECT A.NOMBRE FROM CLIENTE_DESTINO A WHERE A.ID_CLIENTE_DESTINO=" + rs.getLong(12), conn));
                cliente_destino.setCliente(cliente);
                viaje.setCliente_destino(cliente_destino);
                
                viaje.setTipo_flete_viaje(rs.getString(13));
                viaje.setFecha_hora(rs.getString(14));
                viaje.setEstado(rs.getString(15));
                String fecha_hora_terminado;
                if(rs.getString(16).equals("2000-01-01 00:00:00")) {
                    fecha_hora_terminado = "-";
                } else {
                    fecha_hora_terminado = rs.getString(16);
                }
                viaje.setFecha_hora_terminado(fecha_hora_terminado);
                viaje.setDisponibilidad(rs.getString(17));
                
                if(rs.getLong(18) == Long.parseLong("0")) {
                    viaje.setCisterna_disponibilidad(null);
                } else {
                    Entidad.Vehiculo cisterna_disponibilidad = new Entidad.Vehiculo();
                    cisterna_disponibilidad.setId_vehiculo(rs.getLong(18));
                    cisterna_disponibilidad.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM VEHICULO A WHERE A.ID_VEHICULO=" + rs.getLong(18), conn));
                    cisterna_disponibilidad.setPlaca(ctrl_base_datos.ObtenerString("SELECT A.PLACA FROM VEHICULO A WHERE A.ID_VEHICULO=" + rs.getLong(18), conn));
                    cisterna_disponibilidad.setTransportista(transportista);
                    viaje.setCisterna_disponibilidad(cisterna_disponibilidad);
                }
                
                if(rs.getLong(19) == Long.parseLong("0")) {
                    viaje.setCabezal_disponibilidad(null);
                } else {
                    Entidad.Cabezal cabezal_disponibilidad = new Entidad.Cabezal();
                    cabezal_disponibilidad.setId_cabezal(rs.getLong(19));
                    cabezal_disponibilidad.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM CABEZAL A WHERE A.ID_CABEZAL=" + rs.getLong(19), conn));
                    cabezal_disponibilidad.setPlaca(ctrl_base_datos.ObtenerString("SELECT A.PLACA FROM CABEZAL A WHERE A.ID_CABEZAL=" + rs.getLong(19), conn));
                    cabezal_disponibilidad.setImei(ctrl_base_datos.ObtenerString("SELECT A.IMEI FROM CABEZAL A WHERE A.ID_CABEZAL=" + rs.getLong(19), conn));
                    cabezal_disponibilidad.setTransportista(transportista);
                    viaje.setCabezal_disponibilidad(cabezal_disponibilidad);
                }
                
                Integer numero_ubicaciones_gps = 0;
                List<Entidad.Ubicacion> lista_ubicaciones = new ArrayList<>();
                cadenasql = "SELECT "
                        + "A.FECHA_HORA, "
                        + "A.IMEI, "
                        + "A.LATITUDE, "
                        + "A.LONGITUDE, "
                        + "A.LOCATIONDESCRIPTION, "
                        + "A.ETA_HORAS, "
                        + "A.EDA_KMS "
                        + "FROM "
                        + "VIAJE_UBICACIONES A "
                        + "WHERE "
                        + "A.ID_PAIS=" + pais.getId_pais() + " AND "
                        + "A.ID_COMPANIA=" + compania.getId_compania() + " AND "
                        + "A.ID_PLANTA=" + planta.getId_planta() + " AND "
                        + "A.NUMERO_VIAJE=" + viaje.getNumero_viaje() + " AND "
                        + "A.TIPO_ORDEN_VENTA='" + viaje.getTipo_orden_venta() + "' AND "
                        + "A.NUMERO_ORDEN_VENTA=" + viaje.getNumero_orden_venta();
                Statement stmt1 = conn.createStatement();
                ResultSet rs1 = stmt1.executeQuery(cadenasql);
                while(rs1.next()) {
                    Entidad.Ubicacion ubicacion = new Entidad.Ubicacion();
                    ubicacion.setFecha_hora_ubicacion(rs1.getString(1));
                    ubicacion.setImei(rs1.getString(2));
                    ubicacion.setLatitude(rs1.getString(3));
                    ubicacion.setLogitude(rs1.getString(4));
                    ubicacion.setDescripcion_ubicacion(rs1.getString(5));
                    ubicacion.setEta_hora(rs1.getDouble(6));
                    ubicacion.setEda_kms(rs1.getDouble(7));
                    lista_ubicaciones.add(ubicacion);
                    numero_ubicaciones_gps++;
                }
                rs1.close();
                stmt1.close();
                viaje.setNumero_ubicaciones_gps(numero_ubicaciones_gps);
                viaje.setLista_ubicaciones(lista_ubicaciones);
                
                lista_viajes.add(viaje);
            }
            rs.close();
            stmt.close();
            
            conn.commit();
            conn.setAutoCommit(true);

            Gson gson = new GsonBuilder().serializeNulls().create();
            resultado = gson.toJson(lista_viajes);
        } catch (Exception ex) {
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);
                    conn = null;
                    resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: lista_viajes(), ERRROR: " + ex.toString();
                }
            } catch (Exception ex1) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: rollback-lista_viajes(), ERRROR: " + ex.toString();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                resultado = "PROYECTO: unocorp-rest-api, CLASE: " + this.getClass().getName() + ", METODO: finally-lista_viajes(), ERRROR: " + ex.toString();
            }
        }

        return resultado;
    }

}
