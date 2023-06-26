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
    
    private Date fecha_inicio;
    private Date fecha_final;

    @PostConstruct
    public void init() {
        try {
            this.lst_reg_tbl_viajes = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fechap = "2023-01-15";
            this.fecha_inicio = dateFormat.parse(fechap);
            this.fecha_final = dateFormat.parse(fechap);
        } catch(Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: init(), ERRROR: " + ex.toString());
        }
    }

    public void cargar_vista(Entidades.UsuarioSesion usuario_sesion) {
        try {
            this.usuario_sesion = usuario_sesion;
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            ClientesRest.ClienteRestApi cliente_rest_api = new ClientesRest.ClienteRestApi();
            String json_result = cliente_rest_api.lista_viajes(dateFormat.format(this.fecha_inicio), dateFormat.format(this.fecha_final));
            
            Type lista_viaje_type = new TypeToken<List<Entidades.Viaje>>() {
            }.getType();
            List<Entidades.Viaje> lista_viajes = new Gson().fromJson(json_result, lista_viaje_type);
            
            for(Integer i=0; i < lista_viajes.size(); i++) {
                Entidades.RegTblViajes regtblviajes = new Entidades.RegTblViajes();
                regtblviajes.setCodigo_pais(lista_viajes.get(i).getPais().getCodigo());
                regtblviajes.setNombre_pais(lista_viajes.get(i).getPais().getNombre());
                this.lst_reg_tbl_viajes = new ArrayList<>();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema.", "Vista-Viajes."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: cargar_vista(), ERRROR: " + ex.toString());
        }

    }

}
