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

    public String lista_viajes(String fecha_inicio, String fecha_final) {
        String resultado = "";
        
        Connection conn = null;

        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            
            Base_Datos ctrl_base_datos = new Base_Datos();
            conn = ctrl_base_datos.obtener_conexion_mysql();
            
            conn.setAutoCommit(false);
            
            List<Entidad.Viaje> lista_viajes = new ArrayList<>();
            
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
                    + "V.TIPO_FLETE_VIAJE "
                    + "FROM "
                    + "VIAJES V "
                    + "WHERE "
                    + "V.FECHA_VIAJE BETWEEN '" + dateFormat2.format(dateFormat1.parse(fecha_inicio)) + "' AND '" + dateFormat2.format(dateFormat1.parse(fecha_final)) + "'";
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
                viaje.setFecha_viaje(dateFormat2.format(rs.getDate(5)));
                
                Entidad.Estado_Viaje estado_viaje = new Entidad.Estado_Viaje();
                estado_viaje.setId_estado_viaje(rs.getLong(6));
                estado_viaje.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM ESTADO_VIAJE A WHERE A.ID_ESTADO_VIAJE=" + rs.getLong(6), conn));
                estado_viaje.setNombre(ctrl_base_datos.ObtenerString("SELECT A.NOMBRE FROM ESTADO_VIAJE A WHERE A.ID_ESTADO_VIAJE=" + rs.getLong(6), conn));
                viaje.setEstado_viaje(estado_viaje);
                
                Entidad.Transportista transportista = new Entidad.Transportista();
                transportista.setId_transportista(rs.getLong(7));
                transportista.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM TRANSPORTISTA A WHERE A.ID_TRANSPORTISTA=" + rs.getLong(7), conn));
                transportista.setNombre(ctrl_base_datos.ObtenerString("SELECT A.NOMBRE FROM TRANSPORTISTA A WHERE A.ID_TRANSPORTISTA=" + rs.getLong(7), conn));
                transportista.setPais(pais);
                viaje.setTransportista(transportista);
                
                Entidad.Vehiculo vehiculo = new Entidad.Vehiculo();
                vehiculo.setId_vehiculo(rs.getLong(8));
                vehiculo.setCodigo(ctrl_base_datos.ObtenerString("SELECT A.CODIGO FROM VEHICULO A WHERE A.ID_VEHICULO=" + rs.getLong(8), conn));
                vehiculo.setPlaca(ctrl_base_datos.ObtenerString("SELECT A.PLACA FROM VEHICULO A WHERE A.ID_VEHICULO=" + rs.getLong(8), conn));
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
