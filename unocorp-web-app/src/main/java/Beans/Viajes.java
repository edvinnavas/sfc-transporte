package Beans;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.primefaces.PrimeFaces;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polygon;

@ViewScoped
@Named(value = "Viajes")
@Getter
@Setter
public class Viajes implements Serializable {

    private static final long serialVersionUID = 1L;

    // Página de Viajes.
    private Entidades.UsuarioSesion usuario_sesion;
    private List<Entidades.RegTblViajes> lst_reg_tbl_viajes;
    private Entidades.RegTblViajes sel_reg_tbl_viajes;
    private Date fecha_inicial;
    private Date fecha_final;
    private String estado;
    private String tipo_flete;
    private Boolean rastreable;
    
    // Dialog Viaje-Ubicaciones.
    private List<Entidades.RegTblUbicaciones> lst_reg_tbl_ubicaciones;
    private Entidades.RegTblUbicaciones sel_reg_tbl_ubicaciones;
    private MapModel<Long> mapa_model;
    private String central_map;

    // Dialog Cliente-Destino.
    private String id_cliente_destino;
    private String codigo_cliente_destino;
    private String nombre_cliente_destino;
    private String padre_cliente_destino;
    private MapModel<Long> mapa_model_destino_cliente;
    private String central_map_destino_cliente;
    private String coor_sup_izq;
    private String coor_inf_der;
    private Boolean editar_sup_izq;
    private Boolean editar_inf_der;
    
    @PostConstruct
    public void init() {
        try {
            this.lst_reg_tbl_viajes = new ArrayList<>();
            this.fecha_inicial = new Date();
            this.fecha_final = new Date();
            this.estado = "ACT";
            this.tipo_flete = "CIF";
            this.rastreable = true;
            
            this.lst_reg_tbl_ubicaciones = new ArrayList<>();
            this.mapa_model = new DefaultMapModel<>();
            
            this.id_cliente_destino = "";
            this.codigo_cliente_destino = "";
            this.nombre_cliente_destino = "";
            this.padre_cliente_destino = "";
            this.mapa_model_destino_cliente = new DefaultMapModel<>();
            this.coor_sup_izq = "";
            this.coor_inf_der = "";
            this.editar_sup_izq = false;
            this.editar_inf_der = false;
            
            this.filtrar_tabla();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: init(), ERRROR: " + ex.toString());
        }
    }

    public void cargar_vista(Entidades.UsuarioSesion usuario_sesion) {
        try {
            this.usuario_sesion = usuario_sesion;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema.", "Vista-Viajes."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: cargar_vista(), ERRROR: " + ex.toString());
        }
    }

    public void filtrar_tabla() {
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
            ClientesRest.ClienteRestApi cliente_rest_api = new ClientesRest.ClienteRestApi();
            String rastreable_bean;
            if (this.rastreable) {
                rastreable_bean = "SI";
            } else {
                rastreable_bean = "NO";
            }
            String json_result = cliente_rest_api.lista_viajes(dateFormat1.format(this.fecha_inicial), dateFormat1.format(this.fecha_final), this.estado, this.tipo_flete, rastreable_bean);

            Type lista_viaje_type = new TypeToken<List<Entidades.Viaje>>() {
            }.getType();
            List<Entidades.Viaje> lista_viajes = new Gson().fromJson(json_result, lista_viaje_type);

            this.lst_reg_tbl_viajes = new ArrayList<>();

            for (Integer i = 0; i < lista_viajes.size(); i++) {
                Entidades.RegTblViajes regtblviajes = new Entidades.RegTblViajes();
                regtblviajes.setId_reg_tbl_viajes(Long.valueOf(i.toString()));
                regtblviajes.setCodigo_pais(lista_viajes.get(i).getPais().getCodigo());
                regtblviajes.setNombre_pais(lista_viajes.get(i).getPais().getNombre());
                regtblviajes.setCodigo_compania(lista_viajes.get(i).getCompania().getCodigo());
                regtblviajes.setNombre_compania(lista_viajes.get(i).getCompania().getNombre());
                regtblviajes.setCodigo_planta(Long.valueOf(lista_viajes.get(i).getPlanta().getCodigo()));
                regtblviajes.setNombre_planta(lista_viajes.get(i).getPlanta().getNombre());
                regtblviajes.setNumero_viaje(lista_viajes.get(i).getNumero_viaje());
                regtblviajes.setFecha_viaje(lista_viajes.get(i).getFecha_viaje());
                regtblviajes.setCodigo_viaje(Integer.valueOf(lista_viajes.get(i).getEstado_viaje().getCodigo()));
                regtblviajes.setNombre_viaje(lista_viajes.get(i).getEstado_viaje().getNombre());
                regtblviajes.setVehiculo(lista_viajes.get(i).getVehiculo().getCodigo());
                regtblviajes.setPlaca_vehiculo(lista_viajes.get(i).getVehiculo().getPlaca());
                regtblviajes.setCodigo_transportista(Long.valueOf(lista_viajes.get(i).getTransportista().getCodigo()));
                regtblviajes.setNombre_transportista(lista_viajes.get(i).getTransportista().getNombre());
                regtblviajes.setTipo_orden_venta(lista_viajes.get(i).getTipo_orden_venta());
                regtblviajes.setNumero_orden_venta(lista_viajes.get(i).getNumero_orden_venta());
                regtblviajes.setCodigo_cliente(Long.valueOf(lista_viajes.get(i).getCliente().getCodigo()));
                regtblviajes.setNombre_cliente(lista_viajes.get(i).getCliente().getNombre());
                regtblviajes.setCodigo_cliente_destino(Long.valueOf(lista_viajes.get(i).getCliente_destino().getCodigo()));
                regtblviajes.setNombre_cliente_destino(lista_viajes.get(i).getCliente_destino().getNombre());
                regtblviajes.setTipo_flete_viaje(lista_viajes.get(i).getTipo_flete_viaje());
                regtblviajes.setFecha_hora(lista_viajes.get(i).getFecha_hora());
                regtblviajes.setEstado(lista_viajes.get(i).getEstado());
                regtblviajes.setFecha_hora_terminado(lista_viajes.get(i).getFecha_hora_terminado());
                String rastreable_viaje = "NO";
                if (lista_viajes.get(i).getTransportista().getRastreable() == 1) {
                    rastreable_viaje = "SI";
                }
                regtblviajes.setRastreable(rastreable_viaje);
                regtblviajes.setDisponibilidad(lista_viajes.get(i).getDisponibilidad());
                if (lista_viajes.get(i).getDisponibilidad().equals("NO")) {
                    regtblviajes.setCisterna_disponibilidad("-");
                    regtblviajes.setCabezal("-");
                    regtblviajes.setPlaca_cabezal("-");
                    regtblviajes.setImei_cabezal("-");
                } else {
                    regtblviajes.setCisterna_disponibilidad(lista_viajes.get(i).getCisterna_disponibilidad().getCodigo());
                    regtblviajes.setCabezal(lista_viajes.get(i).getCabezal_disponibilidad().getCodigo());
                    regtblviajes.setPlaca_cabezal(lista_viajes.get(i).getCabezal_disponibilidad().getPlaca());
                    regtblviajes.setImei_cabezal(lista_viajes.get(i).getCabezal_disponibilidad().getImei());
                }
                regtblviajes.setNumero_ubicaciones_gps(lista_viajes.get(i).getNumero_ubicaciones_gps());
                this.lst_reg_tbl_viajes.add(regtblviajes);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: filtrar_tabla(), ERRROR: " + ex.toString());
        }
    }

    public void mostrar_ubicaciones() {
        try {
            if (this.sel_reg_tbl_viajes != null) {
                ClientesRest.ClienteRestApi cliente_rest_api = new ClientesRest.ClienteRestApi();
                String json_result = cliente_rest_api.lista_viajes_ubicaciones(this.sel_reg_tbl_viajes.getCodigo_pais(), this.sel_reg_tbl_viajes.getCodigo_compania(), this.sel_reg_tbl_viajes.getCodigo_planta().toString(), this.sel_reg_tbl_viajes.getNumero_viaje(), this.sel_reg_tbl_viajes.getCodigo_cliente().toString(), this.sel_reg_tbl_viajes.getCodigo_cliente_destino().toString());

                Type viaje_ubicacion_type = new TypeToken<Entidades.Viaje_Ubicacion>() {
                }.getType();
                Entidades.Viaje_Ubicacion viaje_ubicacion = new Gson().fromJson(json_result, viaje_ubicacion_type);

                this.lst_reg_tbl_ubicaciones = new ArrayList<>();

                this.mapa_model = new DefaultMapModel<>();
                Integer contador = 0;
                Double sum_latitude = 0.00;
                Double sum_longitude = 0.00;
                for (Integer i = 0; i < viaje_ubicacion.getLista_ubicaciones().size(); i++) {
                    contador = i + 1;
                    Entidades.RegTblUbicaciones regtblubicaciones = new Entidades.RegTblUbicaciones();
                    regtblubicaciones.setId_reg_tbl_ubicaciones(Long.valueOf(contador.toString()));
                    regtblubicaciones.setFecha_hora_ubicacion(viaje_ubicacion.getLista_ubicaciones().get(i).getFecha_hora_ubicacion());
                    regtblubicaciones.setLatitude(viaje_ubicacion.getLista_ubicaciones().get(i).getLatitude());
                    regtblubicaciones.setLogitude(viaje_ubicacion.getLista_ubicaciones().get(i).getLogitude());
                    regtblubicaciones.setDescripcion_ubicacion(viaje_ubicacion.getLista_ubicaciones().get(i).getDescripcion_ubicacion());
                    regtblubicaciones.setEta_hora(viaje_ubicacion.getLista_ubicaciones().get(i).getEta_hora());
                    regtblubicaciones.setEda_kms(viaje_ubicacion.getLista_ubicaciones().get(i).getEda_kms());
                    this.lst_reg_tbl_ubicaciones.add(regtblubicaciones);
                    
                    String desc_ubicacion = "Fecha-Hora: " + viaje_ubicacion.getLista_ubicaciones().get(i).getFecha_hora_ubicacion() + " [" + viaje_ubicacion.getLista_ubicaciones().get(i).getLatitude() + "," + viaje_ubicacion.getLista_ubicaciones().get(i).getLogitude() + "]";
                    this.mapa_model.addOverlay(new Marker<>(new LatLng(Double.parseDouble(viaje_ubicacion.getLista_ubicaciones().get(i).getLatitude()), Double.parseDouble(viaje_ubicacion.getLista_ubicaciones().get(i).getLogitude())), desc_ubicacion, Long.valueOf(contador.toString())));
                    sum_latitude = sum_latitude + Double.valueOf(viaje_ubicacion.getLista_ubicaciones().get(i).getLatitude());
                    sum_longitude = sum_longitude + Double.valueOf(viaje_ubicacion.getLista_ubicaciones().get(i).getLogitude());
                }
                Double avg_latitude = sum_latitude / contador;
                Double avg_longitude = sum_longitude / contador;
                this.central_map = avg_latitude.toString() + ", " + avg_longitude;
                
                LatLng planta_coord1 = new LatLng(viaje_ubicacion.getPlanta().getZona_latitud_1(), viaje_ubicacion.getPlanta().getZona_longitud_1());
                LatLng planta_coord2 = new LatLng(viaje_ubicacion.getPlanta().getZona_latitud_2(), viaje_ubicacion.getPlanta().getZona_longitud_2());
                LatLng planta_coord3 = new LatLng(viaje_ubicacion.getPlanta().getZona_latitud_3(), viaje_ubicacion.getPlanta().getZona_longitud_3());
                LatLng planta_coord4 = new LatLng(viaje_ubicacion.getPlanta().getZona_latitud_4(), viaje_ubicacion.getPlanta().getZona_longitud_4());
                LatLng planta_coord5 = new LatLng(viaje_ubicacion.getPlanta().getZona_latitud_5(), viaje_ubicacion.getPlanta().getZona_longitud_5());
                
                Polygon<Long> planta_polygon = new Polygon<>();
                planta_polygon.setData(1L);
                planta_polygon.getPaths().add(planta_coord1);
                planta_polygon.getPaths().add(planta_coord2);
                planta_polygon.getPaths().add(planta_coord3);
                planta_polygon.getPaths().add(planta_coord4);
                planta_polygon.getPaths().add(planta_coord5);
                
                planta_polygon.setStrokeColor("#03386E");
                planta_polygon.setFillColor("#03386E");
                planta_polygon.setStrokeOpacity(0.7);
                planta_polygon.setFillOpacity(0.7);
                
                this.mapa_model.addOverlay(planta_polygon);
                
                LatLng cliente_destino_coord1 = new LatLng(viaje_ubicacion.getCliente_destino().getZona_latitud_1(), viaje_ubicacion.getCliente_destino().getZona_longitud_1());
                LatLng cliente_destino_coord2 = new LatLng(viaje_ubicacion.getCliente_destino().getZona_latitud_2(), viaje_ubicacion.getCliente_destino().getZona_longitud_2());
                LatLng cliente_destino_coord3 = new LatLng(viaje_ubicacion.getCliente_destino().getZona_latitud_3(), viaje_ubicacion.getCliente_destino().getZona_longitud_3());
                LatLng cliente_destino_coord4 = new LatLng(viaje_ubicacion.getCliente_destino().getZona_latitud_4(), viaje_ubicacion.getCliente_destino().getZona_longitud_4());
                LatLng cliente_destino_coord5 = new LatLng(viaje_ubicacion.getCliente_destino().getZona_latitud_5(), viaje_ubicacion.getCliente_destino().getZona_longitud_5());
                
                Polygon<Long> cliente_destino_polygon = new Polygon<>();
                cliente_destino_polygon.setData(1L);
                cliente_destino_polygon.getPaths().add(cliente_destino_coord1);
                cliente_destino_polygon.getPaths().add(cliente_destino_coord2);
                cliente_destino_polygon.getPaths().add(cliente_destino_coord3);
                cliente_destino_polygon.getPaths().add(cliente_destino_coord4);
                cliente_destino_polygon.getPaths().add(cliente_destino_coord5);
                
                cliente_destino_polygon.setStrokeColor("#FF9900");
                cliente_destino_polygon.setFillColor("#FF9900");
                cliente_destino_polygon.setStrokeOpacity(0.7);
                cliente_destino_polygon.setFillOpacity(0.7);
                
                this.mapa_model.addOverlay(cliente_destino_polygon);

                PrimeFaces.current().executeScript("PF('widvarUbicaciones').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un viaje."));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: mostrar_ubicaciones(), ERRROR: " + ex.toString());
        }
    }
    
    public void mostrar_cliente_destino() {
        try {
            if (this.sel_reg_tbl_viajes != null) {
                ClientesRest.ClienteRestApi cliente_rest_api = new ClientesRest.ClienteRestApi();
                String json_result = cliente_rest_api.obtener_cliente_destino(this.sel_reg_tbl_viajes.getCodigo_cliente_destino().toString());

                Type cliente_destino_type = new TypeToken<Entidades.Cliente_Destino>() {
                }.getType();
                Entidades.Cliente_Destino cliente_destino = new Gson().fromJson(json_result, cliente_destino_type);
                
                this.id_cliente_destino = cliente_destino.getId_cliente_destino().toString();
                this.codigo_cliente_destino = cliente_destino.getCodigo();
                this.nombre_cliente_destino = cliente_destino.getNombre();
                this.padre_cliente_destino = cliente_destino.getCliente().getCodigo() + " - " + cliente_destino.getCliente().getNombre();
                this.coor_sup_izq = cliente_destino.getZona_latitud_1() + "," + cliente_destino.getZona_longitud_1();
                this.coor_inf_der = cliente_destino.getZona_latitud_3() + "," + cliente_destino.getZona_longitud_3();
                this.editar_sup_izq = false;
                this.editar_inf_der = false;
                
                Double sum_latitude = cliente_destino.getZona_latitud_1() + cliente_destino.getZona_latitud_2() + cliente_destino.getZona_latitud_3() + cliente_destino.getZona_latitud_4() + cliente_destino.getZona_latitud_5();
                Double sum_longitude = cliente_destino.getZona_longitud_1() + cliente_destino.getZona_longitud_2() + cliente_destino.getZona_longitud_3() + cliente_destino.getZona_longitud_4() + cliente_destino.getZona_longitud_5();
                
                Double avg_latitude = sum_latitude / 5;
                Double avg_longitude = sum_longitude / 5;
                
                this.central_map_destino_cliente = avg_latitude.toString() + ", " + avg_longitude;
                
                LatLng planta_coord1 = new LatLng(cliente_destino.getZona_latitud_1(), cliente_destino.getZona_longitud_1());
                LatLng planta_coord2 = new LatLng(cliente_destino.getZona_latitud_2(), cliente_destino.getZona_longitud_2());
                LatLng planta_coord3 = new LatLng(cliente_destino.getZona_latitud_3(), cliente_destino.getZona_longitud_3());
                LatLng planta_coord4 = new LatLng(cliente_destino.getZona_latitud_4(), cliente_destino.getZona_longitud_4());
                LatLng planta_coord5 = new LatLng(cliente_destino.getZona_latitud_5(), cliente_destino.getZona_longitud_5());
                
                Polygon<Long> planta_polygon = new Polygon<>();
                planta_polygon.setData(1L);
                planta_polygon.getPaths().add(planta_coord1);
                planta_polygon.getPaths().add(planta_coord2);
                planta_polygon.getPaths().add(planta_coord3);
                planta_polygon.getPaths().add(planta_coord4);
                planta_polygon.getPaths().add(planta_coord5);
                
                planta_polygon.setStrokeColor("#FF9900");
                planta_polygon.setFillColor("#FF9900");
                planta_polygon.setStrokeOpacity(0.7);
                planta_polygon.setFillOpacity(0.7);
                
                this.mapa_model_destino_cliente.addOverlay(planta_polygon);

                PrimeFaces.current().executeScript("PF('widvarClienteDestino').show();");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un viaje."));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: mostrar_cliente_destino(), ERRROR: " + ex.toString());
        }
    }
    
    public void selecionar_coordenada(PointSelectEvent event) {
        try {
            LatLng latlng = event.getLatLng();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Coordenada selecionada.","Latitud:" + latlng.getLat() + ", Longitud:" + latlng.getLng()));
            
            if(this.editar_sup_izq) {
                this.coor_sup_izq = latlng.getLat() + "," + latlng.getLng();
                this.editar_sup_izq = false;
                this.editar_inf_der = false;
            }
            
            if(this.editar_inf_der) {
                this.coor_inf_der = latlng.getLat() + "," + latlng.getLng();
                this.editar_sup_izq = false;
                this.editar_inf_der = false;
            }
        } catch(Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: selecionar_coordenada(), ERRROR: " + ex.toString());
        }
    }
    
    public void destino_cliente_actualizar_geozona() {
        try {
            if (this.sel_reg_tbl_viajes != null) {
                String parametros_cliente_destino = this.id_cliente_destino + "♣" + this.coor_sup_izq + "♣" + this.coor_inf_der;

                ClientesRest.ClienteRestApi cliente_rest_api = new ClientesRest.ClienteRestApi();
                String json_result = cliente_rest_api.cliente_destino_modificar_geozona(parametros_cliente_destino);
                String respuesta_json_result = json_result;

                json_result = cliente_rest_api.obtener_cliente_destino(this.sel_reg_tbl_viajes.getCodigo_cliente_destino().toString());

                Type cliente_destino_type = new TypeToken<Entidades.Cliente_Destino>() {
                }.getType();
                Entidades.Cliente_Destino cliente_destino = new Gson().fromJson(json_result, cliente_destino_type);

                Double sum_latitude = cliente_destino.getZona_latitud_1() + cliente_destino.getZona_latitud_2() + cliente_destino.getZona_latitud_3() + cliente_destino.getZona_latitud_4() + cliente_destino.getZona_latitud_5();
                Double sum_longitude = cliente_destino.getZona_longitud_1() + cliente_destino.getZona_longitud_2() + cliente_destino.getZona_longitud_3() + cliente_destino.getZona_longitud_4() + cliente_destino.getZona_longitud_5();

                Double avg_latitude = sum_latitude / 5;
                Double avg_longitude = sum_longitude / 5;

                this.central_map_destino_cliente = avg_latitude.toString() + ", " + avg_longitude;

                LatLng planta_coord1 = new LatLng(cliente_destino.getZona_latitud_1(), cliente_destino.getZona_longitud_1());
                LatLng planta_coord2 = new LatLng(cliente_destino.getZona_latitud_2(), cliente_destino.getZona_longitud_2());
                LatLng planta_coord3 = new LatLng(cliente_destino.getZona_latitud_3(), cliente_destino.getZona_longitud_3());
                LatLng planta_coord4 = new LatLng(cliente_destino.getZona_latitud_4(), cliente_destino.getZona_longitud_4());
                LatLng planta_coord5 = new LatLng(cliente_destino.getZona_latitud_5(), cliente_destino.getZona_longitud_5());

                Polygon<Long> planta_polygon = new Polygon<>();
                planta_polygon.setData(1L);
                planta_polygon.getPaths().add(planta_coord1);
                planta_polygon.getPaths().add(planta_coord2);
                planta_polygon.getPaths().add(planta_coord3);
                planta_polygon.getPaths().add(planta_coord4);
                planta_polygon.getPaths().add(planta_coord5);

                planta_polygon.setStrokeColor("#FF9900");
                planta_polygon.setFillColor("#FF9900");
                planta_polygon.setStrokeOpacity(0.7);
                planta_polygon.setFillOpacity(0.7);

                this.mapa_model_destino_cliente.addOverlay(planta_polygon);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente-Destino.", respuesta_json_result));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema...", "Debe seleccionar un viaje."));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: destino_cliente_actualizar_geozona(), ERRROR: " + ex.toString());
        }
    }
    
    public void selecionar_checkbox() {
        if(this.editar_sup_izq) {
            this.editar_sup_izq = true;
            this.editar_inf_der = false;
        }
        if(this.editar_inf_der) {
            this.editar_sup_izq = false;
            this.editar_inf_der = true;
        }
    }

}
