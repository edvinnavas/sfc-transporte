package Beans;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.lang.reflect.Type;
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
    private List<SelectItem> lst_cabezal;

    @PostConstruct
    public void init() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            this.usuario_sesion = (Entidades.UsuarioSesion) session.getAttribute("usuario_sesion");

            this.fecha = new Date();
            
            ClientesRest.ClienteRestApi cliente_rest_api = new ClientesRest.ClienteRestApi();
            String json_result = cliente_rest_api.usuario_predio(this.usuario_sesion.getId_usuario());

            Type usuario_predio_type = new TypeToken<Entidades.Usuario_Predio>() {
            }.getType();
            Entidades.Usuario_Predio usuario_predio = new Gson().fromJson(json_result, usuario_predio_type);

            this.lst_transportista = new ArrayList<>();
            for (Integer i = 0; i < usuario_predio.getLst_transportista().size(); i++) {
                this.lst_transportista.add(new SelectItem(usuario_predio.getLst_transportista().get(i).getId_transportista(), usuario_predio.getLst_transportista().get(i).getNombre_transportista()));
            }
            if (!lst_transportista.isEmpty()) {
                this.id_transportista = Long.valueOf(this.lst_transportista.get(0).getValue().toString());
                this.actualizar_lista_predio();
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: init(), ERRROR: " + ex.toString());
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

    public void actualizar_lista_predio() {
        try {
            ClientesRest.ClienteRestApi cliente_rest_api = new ClientesRest.ClienteRestApi();
            String json_result = cliente_rest_api.usuario_predio(this.usuario_sesion.getId_usuario());

            Type usuario_predio_type = new TypeToken<Entidades.Usuario_Predio>() {
            }.getType();
            Entidades.Usuario_Predio usuario_predio = new Gson().fromJson(json_result, usuario_predio_type);

            this.lst_predio = new ArrayList<>();
            for (Integer i = 0; i < usuario_predio.getLst_transportista().size(); i++) {
                if (usuario_predio.getLst_transportista().get(i).getId_transportista().equals(this.id_transportista)) {
                    for (Integer j = 0; j < usuario_predio.getLst_transportista().get(i).getLst_predio().size(); j++) {
                        this.lst_predio.add(new SelectItem(usuario_predio.getLst_transportista().get(i).getLst_predio().get(j).getId_predio(), usuario_predio.getLst_transportista().get(i).getLst_predio().get(j).getNombre_predio()));
                    }
                }
            }
            if (!lst_predio.isEmpty()) {
                this.id_predio = Long.valueOf(this.lst_predio.get(0).getValue().toString());
            }
            
            this.lst_cabezal = new ArrayList<>();
            
            cliente_rest_api = new ClientesRest.ClienteRestApi();
            json_result = cliente_rest_api.lista_cabezales(this.id_transportista, this.id_predio);
            

            this.filtrar_tabla();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: actualizar_lista_predio(), ERRROR: " + ex.toString());
        }
    }

    public void filtrar_tabla() {
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
            
            ClientesRest.ClienteRestApi cliente_rest_api = new ClientesRest.ClienteRestApi();
            String json_result = cliente_rest_api.disponibilidad(this.id_transportista, this.id_predio, dateFormat1.format(this.fecha));

            Type lista_disponibilidad_type = new TypeToken<List<Entidades.Disponibilidad>>() {
            }.getType();
            List<Entidades.Disponibilidad> lista_disponibilidad = new Gson().fromJson(json_result, lista_disponibilidad_type);
            
            this.lst_reg_tbl_disponibilidad = new ArrayList<>();
            for (Integer i = 0; i < lista_disponibilidad.size(); i++) {
                this.lst_reg_tbl_disponibilidad.add(new Entidades.RegTblDisponibilidad(Long.valueOf(i.toString()), lista_disponibilidad.get(i).getNombre_cisterna(), lista_disponibilidad.get(i).getNombre_cabezal()));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: filtrar_tabla(), ERRROR: " + ex.toString());
        }
    }

}
