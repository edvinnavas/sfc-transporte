package Beans;

import Entidades.RegTblInicio;
import Entidades.UsuarioSesion;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ViewScoped
@Named(value = "Inicio")
@Getter
@Setter
public class Inicio implements Serializable {

    private static final long serialVersionUID = 1L;

    private UsuarioSesion usuario_sesion;
    private List<RegTblInicio> lst_reg_tbl_inicio;

    @PostConstruct
    public void init() {
        this.lst_reg_tbl_inicio = new ArrayList<>();
        for (Integer i = 0; i < 10; i++) {
            this.lst_reg_tbl_inicio.add(new RegTblInicio(Long.valueOf(i.toString()), "Asunto-" + i.toString(), new Date(), "NO"));
        }
    }

    public void cargar_vista(UsuarioSesion usuario_sesion) {
        try {
            this.usuario_sesion = usuario_sesion;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema.", "Vista-Inicio."));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: cargar_vista(), ERRROR: " + ex.toString());
        }

    }

}
