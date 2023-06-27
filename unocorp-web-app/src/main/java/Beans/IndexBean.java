package Beans;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named(value = "IndexBean")
@Getter
@Setter
public class IndexBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Entidades.UsuarioSesion usuario_sesion;
    private String txt_usuario;
    private String pass_contrasena;

    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.usuario_sesion = new Entidades.UsuarioSesion();
        this.txt_usuario = "";
        this.pass_contrasena = "";
    }

    public String autenticar() {
        String resultado;

        try {
            ClientesRest.ClienteRestApi cliente_rest_api = new ClientesRest.ClienteRestApi();
            String json_result = cliente_rest_api.autenticar(this.txt_usuario, this.pass_contrasena);
            if (!json_result.equals("Usuario no autenticado.")) {
                Type usuario_type = new TypeToken<Entidades.Usuario>() {
                }.getType();
                Entidades.Usuario usuario = new Gson().fromJson(json_result, usuario_type);

                this.usuario_sesion.setId_usuario(usuario.getId_usuario());
                this.usuario_sesion.setNombre_usuario(usuario.getNombre_usuario());
                this.usuario_sesion.setNombre_sesion_usuario(usuario.getNombre_completo());
                this.usuario_sesion.setContrasena_usuario(usuario.getContrasena());
                List<String> lista_opcion_menu = new ArrayList<>();
                for (Integer i = 0; i < usuario.getRol().getLista_menu().size(); i++) {
                    lista_opcion_menu.add(usuario.getRol().getLista_menu().get(i).getNombre());
                }
                this.usuario_sesion.setLista_opcion_menu(lista_opcion_menu);

                resultado = "inicio.xhtml";
            } else {
                resultado = "index.xhtml";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema.", "Usuario o contraseña incorrecto."));
            }
        } catch (Exception ex) {
            this.usuario_sesion = null;
            resultado = "index.xhtml";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: autenticar(), ERRROR: " + ex.toString());
        }

        return resultado;
    }

}
