package Beans;

import ClientesRest.ClienteRestApi;
import Entidades.UsuarioSesion;
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

    private UsuarioSesion usuario_sesion;
    private String txt_usuario;
    private String pass_contrasena;

    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.usuario_sesion = new UsuarioSesion();
        this.txt_usuario = "";
        this.pass_contrasena = "";
    }

    public String autenticar() {
        String resultado;

        try {
            ClienteRestApi cliente_rest_api = new ClienteRestApi();
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
                lista_opcion_menu.add("menu-Archivo");
                lista_opcion_menu.add("menuItem-Inicio");
                lista_opcion_menu.add("menuItem-Roles");
                lista_opcion_menu.add("menuItem-Usuarios");
                lista_opcion_menu.add("menu-Inventario");
                lista_opcion_menu.add("menuItem-Almacenes");
                lista_opcion_menu.add("menuItem-Articulos");
                lista_opcion_menu.add("menuItem-Almacen-Articulo");
                lista_opcion_menu.add("menuItem-Entrada-Inventario");
                lista_opcion_menu.add("menuItem-Salida-Inventario");
                lista_opcion_menu.add("menuItem-Reconciliacion-Inventario");
                lista_opcion_menu.add("menuItem-Reporte-Inventario");
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
