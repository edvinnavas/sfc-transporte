package Entidad.SFC;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Viaje implements Serializable {

    private static final long serialVersionUID = 1L;

    private String CODIGO_PAIS;
    private String NOMBRE_PAIS;
    private String CODIGO_COMPANIA;
    private String NOMBRE_COMPANIA;
    private Long CODIGO_PLANTA;
    private String NOMBRE_PLANTA;
    private Long NUMERO_VIAJE;
    private String FECHA_VIAJE;
    private Long CODIGO_ESTADO_VIAJE;
    private String NOMBRE_ESTADO_VIAJE;
    private String VEHICULO;
    private String PLACA_VEHICULO;
    private Long CODIGO_TRANSPORTISTA;
    private String NOMBRE_TRANSPORTISTA;
    private String TIPO_ORDEN_VENTA;
    private Long NUMERO_ORDEN_VENTA;
    private Long CODIGO_CLIENTE;
    private String NOMBRE_CLIENTE;
    private Long CODIGO_CLIENTE_DESTINO;
    private String NOMBRE_CLIENTE_DESTINO;
    private String TIPO_FLETE_VIAJE;

    @Override
    public String toString() {
        return "Viaje{" + "CODIGO_PAIS=" + CODIGO_PAIS + ", NOMBRE_PAIS=" + NOMBRE_PAIS + ", CODIGO_COMPANIA=" + CODIGO_COMPANIA + ", NOMBRE_COMPANIA=" + NOMBRE_COMPANIA + ", CODIGO_PLANTA=" + CODIGO_PLANTA + ", NOMBRE_PLANTA=" + NOMBRE_PLANTA + ", NUMERO_VIAJE=" + NUMERO_VIAJE + ", FECHA_VIAJE=" + FECHA_VIAJE + ", CODIGO_ESTADO_VIAJE=" + CODIGO_ESTADO_VIAJE + ", NOMBRE_ESTADO_VIAJE=" + NOMBRE_ESTADO_VIAJE + ", VEHICULO=" + VEHICULO + ", PLACA_VEHICULO=" + PLACA_VEHICULO + ", CODIGO_TRANSPORTISTA=" + CODIGO_TRANSPORTISTA + ", NOMBRE_TRANSPORTISTA=" + NOMBRE_TRANSPORTISTA + ", TIPO_ORDEN_VENTA=" + TIPO_ORDEN_VENTA + ", NUMERO_ORDEN_VENTA=" + NUMERO_ORDEN_VENTA + ", CODIGO_CLIENTE=" + CODIGO_CLIENTE + ", NOMBRE_CLIENTE=" + NOMBRE_CLIENTE + ", CODIGO_CLIENTE_DESTINO=" + CODIGO_CLIENTE_DESTINO + ", NOMBRE_CLIENTE_DESTINO=" + NOMBRE_CLIENTE_DESTINO + ", TIPO_FLETE_VIAJE=" + TIPO_FLETE_VIAJE + '}';
    }
    
}
