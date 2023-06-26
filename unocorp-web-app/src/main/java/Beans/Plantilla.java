package Beans;

import Entidades.UsuarioSesion;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@SessionScoped
@Named(value = "Plantilla")
@Getter
@Setter
public class Plantilla implements Serializable {

    private static final long serialVersionUID = 1L;

    private UsuarioSesion usuario_sesion;
    private String nombre_sesion_usuario;

    public void onload(UsuarioSesion usuario_sesion) {
        try {
            if (usuario_sesion.getId_usuario() != null) {
                this.usuario_sesion = usuario_sesion;
                this.nombre_sesion_usuario = "Usuario: " + this.usuario_sesion.getNombre_sesion_usuario();
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: onload(), ERRROR: " + ex.toString());
        }
    }

    public String logout() {
        String resultado;

        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            resultado = "index.xhtml";
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: logout(), ERRROR: " + ex.toString());

            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            resultado = "index.xhtml";
        }

        return resultado;
    }

    public Boolean render_menu(String menu) {
        Boolean resultado = false;

        try {
            for (Integer i = 0; i < this.usuario_sesion.getLista_opcion_menu().size(); i++) {
                if (this.usuario_sesion.getLista_opcion_menu().get(i).equals(menu)) {
                    resultado = true;
                }
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: render_menu(), ERRROR: " + ex.toString());
            resultado = false;
        }

        return resultado;
    }

}
