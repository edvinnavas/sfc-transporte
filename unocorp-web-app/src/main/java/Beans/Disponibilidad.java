package Beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ViewScoped
@Named(value = "Disponibilidad")
@Getter
@Setter
public class Disponibilidad implements Serializable {

    private static final long serialVersionUID = 1L;

    private Entidades.UsuarioSesion usuario_sesion;
    private List<Entidades.RegTblDisponibilidad> lst_reg_tbl_disponibilidad;
    private Entidades.RegTblDisponibilidad sel_reg_tbl_disponibilidad;
    private Long id_transportista;
    private List<SelectItem> lst_transportista;
    private Long id_predio;
    private List<SelectItem> lst_predio;
    private Date fecha;

    @PostConstruct
    public void init() {
        this.lst_reg_tbl_disponibilidad = new ArrayList<>();
        this.lst_transportista = new ArrayList<>();
        this.lst_predio = new ArrayList<>();
        this.id_transportista = Long.valueOf("0");
        this.id_predio = Long.valueOf("0");
        this.fecha = new Date();
        
        this.filtrar_tabla();
        for (Integer i = 0; i < 10; i++) {
            this.lst_reg_tbl_disponibilidad.add(new Entidades.RegTblDisponibilidad(Long.valueOf(i.toString()), "Cisterna-" + i.toString(), "Cabezal-" + i.toString()));
        }
    }

    public void cargar_vista(Entidades.UsuarioSesion usuario_sesion) {
        try {
            this.usuario_sesion = usuario_sesion;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema.", "Vista-Disponibilidad."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: cargar_vista(), ERRROR: " + ex.toString());
        }
    }
    
    public void filtrar_tabla() {
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
            ClientesRest.ClienteRestApi cliente_rest_api = new ClientesRest.ClienteRestApi();
            String json_result = cliente_rest_api.disponibilidad(this.id_transportista, this.id_predio, dateFormat1.format(this.fecha));

            // Type lista_viaje_type = new TypeToken<List<Entidades.Viaje>>() {
            // }.getType();
            // List<Entidades.Viaje> lista_viajes = new Gson().fromJson(json_result, lista_viaje_type);
            // this.lst_reg_tbl_viajes = new ArrayList<>();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: filtrar_tabla(), ERRROR: " + ex.toString());
        }
    }

}
