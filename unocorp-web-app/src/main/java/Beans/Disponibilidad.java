package Beans;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;

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
    private List<SelectItem> lst_disponibilidad;
    private List<SelectItem> lst_planta;
    private List<SelectItem> lst_tipo_carga;

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
                SelectItem select_item = new SelectItem(usuario_predio.getLst_transportista().get(i).getId_transportista(), usuario_predio.getLst_transportista().get(i).getNombre_transportista());
                if(!this.lst_transportista.contains(select_item)) {
                    this.lst_transportista.add(select_item);
                }
            }
            if (!lst_transportista.isEmpty()) {
                this.id_transportista = Long.valueOf(this.lst_transportista.get(0).getValue().toString());
                this.actualizar_lista_predio();
            }
            
            this.lst_disponibilidad = new ArrayList<>();
            this.lst_disponibilidad.add(new SelectItem("-", "-"));
            this.lst_disponibilidad.add(new SelectItem("Puede viajar", "Puede viajar"));
            this.lst_disponibilidad.add(new SelectItem("No puede viajar", "No puede viajar"));
            
            this.lst_tipo_carga = new ArrayList<>();
            this.lst_tipo_carga.add(new SelectItem("-", "-"));
            this.lst_tipo_carga.add(new SelectItem("TOP", "TOP"));
            this.lst_tipo_carga.add(new SelectItem("BOTTOM", "BOTTOM"));
            this.lst_tipo_carga.add(new SelectItem("NO APLICA", "NO APLICA"));
            
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
            
            json_result = cliente_rest_api.lista_cabezales(this.id_transportista, this.id_predio);
            
            Type lista_cabezal_type = new TypeToken<List<Entidades.Cabezal>>() {
            }.getType();
            List<Entidades.Cabezal> lista_cabezal = new Gson().fromJson(json_result, lista_cabezal_type);
            
            this.lst_cabezal.add(new SelectItem("-", "-"));
            for(Integer i = 0; i < lista_cabezal.size(); i++) {
                this.lst_cabezal.add(new SelectItem(lista_cabezal.get(i).getCodigo(), lista_cabezal.get(i).getCodigo()));
            }
            
            this.lst_planta = new ArrayList<>();
            
            json_result = cliente_rest_api.lista_plantas(this.id_transportista);
            
            Type lista_planta_type = new TypeToken<List<Entidades.Planta>>() {
            }.getType();
            List<Entidades.Planta> lista_planta = new Gson().fromJson(json_result, lista_planta_type);
            
            this.lst_planta.add(new SelectItem("-", "-"));
            for(Integer i = 0; i < lista_planta.size(); i++) {
                this.lst_planta.add(new SelectItem(lista_planta.get(i).getCodigo(), lista_planta.get(i).getCodigo()));
            }

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
                this.lst_reg_tbl_disponibilidad.add(new Entidades.RegTblDisponibilidad(
                        Long.valueOf(i.toString()), 
                        lista_disponibilidad.get(i).getNombre_cisterna(), 
                        lista_disponibilidad.get(i).getNombre_tipo_carga_cisterna(),
                        lista_disponibilidad.get(i).getBomba_cisterna(),
                        lista_disponibilidad.get(i).getNombre_cabezal(),
                        lista_disponibilidad.get(i).getHora_inicio(),
                        lista_disponibilidad.get(i).getHora_final(),
                        lista_disponibilidad.get(i).getDisponibilida(),
                        lista_disponibilidad.get(i).getCodigo_planta()));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: filtrar_tabla(), ERRROR: " + ex.toString());
        }
    }
    
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema.", "Old: " + oldValue + ", New:" + newValue + ", Columna:" + event.getColumn().getHeaderText()));
        }
    }
    
    public void guardar_disponibilidad() {
        try {
            Integer error = 0;
            String error_mensaje = "";

            List<String> codigo_cabezales = new ArrayList<>();
            for (Integer i = 0; i < this.lst_reg_tbl_disponibilidad.size(); i++) {
                if (!this.lst_reg_tbl_disponibilidad.get(i).getCabezal().trim().equals("-")) {
                    if (codigo_cabezales.contains(this.lst_reg_tbl_disponibilidad.get(i).getCabezal())) {
                        error = 1;
                        error_mensaje = "CABEZAL REPETIDO " + this.lst_reg_tbl_disponibilidad.get(i).getCabezal();
                        codigo_cabezales.add(this.lst_reg_tbl_disponibilidad.get(i).getCabezal());
                    } else {
                        codigo_cabezales.add(this.lst_reg_tbl_disponibilidad.get(i).getCabezal());
                    }
                }
            }

            if (error == 0) {
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                List<Entidades.Disponibilidad> lista_disponibilidad = new ArrayList<>();
                for (Integer i = 0; i < this.lst_reg_tbl_disponibilidad.size(); i++) {
                    Entidades.Disponibilidad disponibilidad = new Entidades.Disponibilidad();
                    disponibilidad.setId_transportista(this.id_transportista);
                    disponibilidad.setNombre_transportista(null);
                    disponibilidad.setId_predio(this.id_predio);
                    disponibilidad.setNombre_predio(null);
                    disponibilidad.setId_cisterna(null);
                    disponibilidad.setNombre_cisterna(this.lst_reg_tbl_disponibilidad.get(i).getCisterna());
                    disponibilidad.setId_tipo_carga_cisterna(null);
                    disponibilidad.setNombre_tipo_carga_cisterna(this.lst_reg_tbl_disponibilidad.get(i).getTipo_carga());
                    disponibilidad.setBomba_cisterna(this.lst_reg_tbl_disponibilidad.get(i).getBomba());
                    disponibilidad.setId_cabezal(null);
                    disponibilidad.setNombre_cabezal(this.lst_reg_tbl_disponibilidad.get(i).getCabezal());
                    disponibilidad.setFecha(dateFormat1.format(this.fecha));
                    disponibilidad.setHora_inicio(this.lst_reg_tbl_disponibilidad.get(i).getHora_inicio());
                    disponibilidad.setHora_final(this.lst_reg_tbl_disponibilidad.get(i).getHora_final());
                    disponibilidad.setId_planta(null);
                    disponibilidad.setCodigo_planta(this.lst_reg_tbl_disponibilidad.get(i).getPlanta());
                    disponibilidad.setNombre_planta(null);
                    disponibilidad.setDisponibilida(this.lst_reg_tbl_disponibilidad.get(i).getDisponibilidad());

                    lista_disponibilidad.add(disponibilidad);
                }

                Gson gson = new GsonBuilder().serializeNulls().create();
                String jsonString = gson.toJson(lista_disponibilidad);

                ClientesRest.ClienteRestApi cliente_rest_api = new ClientesRest.ClienteRestApi();
                String result = cliente_rest_api.guardar_disponibilidad(jsonString);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje del sistema.", result));

                this.filtrar_tabla();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Mensaje del sistema.", error_mensaje));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: filtrar_tabla(), ERRROR: " + ex.toString());
        }
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        try {
            File temp_file = File.createTempFile("temp", "xlsx");
            temp_file.deleteOnExit();
            FileUtils.copyInputStreamToFile(event.getFile().getInputStream(), temp_file);

            XSSFWorkbook wb = new XSSFWorkbook(temp_file);
            XSSFSheet ws = wb.getSheetAt(0);

            Iterator<Row> rowIterator = ws.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType().toString()) {
                        case "STRING": {
                            System.out.println("COLUMN-INDEX: " + cell.getColumnIndex());
                            System.out.println("COLUMN-VALUE: " + cell.getStringCellValue());
                            break;
                        }
                        case "NUMERIC": {
                            System.out.println("COLUMN-INDEX: " + cell.getColumnIndex());
                            System.out.println("COLUMN-VALUE: " + cell.getNumericCellValue());
                            break;
                        }
                        default: {

                        }
                    }
                }
            }

            FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje del sistema.", ex.toString()));
            System.out.println("PROYECTO: unocorp-web-app, CLASE: " + this.getClass().getName() + ", METODO: handleFileUpload(), ERRROR: " + ex.toString());
        }
    }

}
