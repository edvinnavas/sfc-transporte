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

@ViewScoped
@Named(value = "Viajes")
@Getter
@Setter
public class Viajes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Entidades.UsuarioSesion usuario_sesion;
    private List<Entidades.RegTblViajes> lst_reg_tbl_viajes;
    private Entidades.RegTblViajes sel_reg_tbl_viajes;
    private Date fecha_inicial;
    private Date fecha_final;
    private String estado;
    private String tipo_flete;

    @PostConstruct
    public void init() {
        try {
            this.lst_reg_tbl_viajes = new ArrayList<>();
            this.fecha_inicial = new Date();
            this.fecha_final = new Date();
            this.estado = "ACT";
            this.tipo_flete = "CIF";
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
            String json_result = cliente_rest_api.lista_viajes(dateFormat1.format(this.fecha_inicial), dateFormat1.format(this.fecha_final), this.estado, this.tipo_flete);
            
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
                this.lst_reg_tbl_viajes.add(regtblviajes);
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: filtrar_tabla(), ERRROR: " + ex.toString());
        }
    }

}
