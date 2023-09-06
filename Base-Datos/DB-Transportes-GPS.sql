DROP TABLE IF EXISTS MENU;
CREATE TABLE MENU (
    ID_MENU BIGINT NOT NULL,
    NOMBRE VARCHAR(500) NOT NULL,
    CONSTRAINT PK_MENU PRIMARY KEY (ID_MENU)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS ROL;
CREATE TABLE ROL (
    ID_ROL BIGINT NOT NULL,
    NOMBRE VARCHAR(500) NOT NULL,
    ACTIVO INTEGER NOT NULL,
    FECHA_HORA TIMESTAMP NOT NULL,
    CONSTRAINT PK_ROL PRIMARY KEY (ID_ROL)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS ROL_MENU;
CREATE TABLE ROL_MENU (
    id_rol BIGINT NOT NULL,
    id_menu BIGINT NOT NULL,
    fecha_hora TIMESTAMP NOT NULL,
    CONSTRAINT PK_ROL_MENU PRIMARY KEY (ID_ROL, ID_MENU),
    CONSTRAINT FK_ROL_MENU_1 FOREIGN KEY (ID_ROL) REFERENCES ROL (ID_ROL),
    CONSTRAINT FK_ROL_MENU_2 FOREIGN KEY (ID_MENU) REFERENCES MENU (ID_MENU)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS USUARIO;
CREATE TABLE USUARIO (
    ID_USUARIO BIGINT NOT NULL,
    NOMBRE_COMPLETO VARCHAR(500) NOT NULL,
    NOMBRE_USUARIO VARCHAR(50) NOT NULL,
    CONTRASENA VARCHAR(255) NOT NULL,
    CORREO_ELECTRONICO VARCHAR(200),
    ACTIVO INTEGER NOT NULL,
    DESCRIPCION TEXT,
    ID_ROL BIGINT NOT NULL,
    FECHA_HORA TIMESTAMP NOT NULL,
    CONSTRAINT PK_USUARIO PRIMARY KEY (ID_USUARIO),
    CONSTRAINT FK_USUARIO_1 FOREIGN KEY (ID_ROL) REFERENCES ROL (ID_ROL)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS PROVEEDOR_GPS;
CREATE TABLE PROVEEDOR_GPS (
    ID_PROVEEDOR BIGINT NOT NULL,
    NOMBRE VARCHAR(200) NOT NULL,
    URL_SERVICIO VARCHAR(500) NOT NULL,
    ACTIVO INTEGER NOT NULL,
    FECHA_HORA TIMESTAMP NOT NULL,
    CONSTRAINT PK_PROVEEDOR PRIMARY KEY (ID_PROVEEDOR)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS PAIS;
CREATE TABLE PAIS (
    ID_PAIS BIGINT NOT NULL,
    CODIGO VARCHAR(10) NOT NULL,
    NOMBRE VARCHAR(500) NOT NULL,
    FECHA_HORA TIMESTAMP NOT NULL,
    CONSTRAINT PK_PAIS PRIMARY KEY (ID_PAIS)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS COMPANIA;
CREATE TABLE COMPANIA (
    ID_COMPANIA BIGINT NOT NULL,
    CODIGO VARCHAR(10) NOT NULL,
    NOMBRE VARCHAR(500) NOT NULL,
    ID_PAIS BIGINT NOT NULL,
    FECHA_HORA TIMESTAMP NOT NULL,
    CONSTRAINT PK_COMPANIA PRIMARY KEY (ID_COMPANIA),
    CONSTRAINT FK_COMPANIA_1 FOREIGN KEY (ID_PAIS) REFERENCES PAIS (ID_PAIS)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS PLANTA;
CREATE TABLE PLANTA (
    ID_PLANTA BIGINT NOT NULL,
    CODIGO VARCHAR(10) NOT NULL,
    NOMBRE VARCHAR(500) NOT NULL,
    ID_COMPANIA BIGINT NOT NULL,
    FECHA_HORA TIMESTAMP NOT NULL,
    CONSTRAINT PK_PLANTA PRIMARY KEY (ID_PLANTA),
    CONSTRAINT FK_PLANTA_1 FOREIGN KEY (ID_COMPANIA) REFERENCES COMPANIA (ID_COMPANIA)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS ESTADO_VIAJE;
CREATE TABLE ESTADO_VIAJE (
    ID_ESTADO_VIAJE BIGINT NOT NULL,
    CODIGO VARCHAR(10) NOT NULL,
    NOMBRE VARCHAR(500) NOT NULL,
    FECHA_HORA TIMESTAMP NOT NULL,
    CONSTRAINT PK_ESTADO_VIAJE PRIMARY KEY (ID_ESTADO_VIAJE)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS TRANSPORTISTA;
CREATE TABLE TRANSPORTISTA (
    ID_TRANSPORTISTA BIGINT NOT NULL,
    CODIGO VARCHAR(10) NOT NULL,
    NOMBRE VARCHAR(500) NOT NULL,
    ID_PAIS BIGINT NOT NULL,
    FECHA_HORA TIMESTAMP NOT NULL,
    CONSTRAINT PK_TRANSPORTISTA PRIMARY KEY (ID_TRANSPORTISTA),
    CONSTRAINT FK_TRANSPORTISTA_1 FOREIGN KEY (ID_PAIS) REFERENCES PAIS (ID_PAIS)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS VEHICULO;
CREATE TABLE VEHICULO (
    ID_VEHICULO BIGINT NOT NULL,
    CODIGO VARCHAR(50) NOT NULL,
    PLACA VARCHAR(50) NOT NULL,
    ID_TRANSPORTISTA BIGINT NOT NULL,
    FECHA_HORA TIMESTAMP NOT NULL,
    CONSTRAINT PK_VEHICULO PRIMARY KEY (ID_VEHICULO),
    CONSTRAINT FK_VEHICULO_1 FOREIGN KEY (ID_TRANSPORTISTA) REFERENCES TRANSPORTISTA (ID_TRANSPORTISTA)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS CABEZAL;
CREATE TABLE CABEZAL (
    ID_CABEZAL BIGINT NOT NULL,
    CODIGO VARCHAR(50) NOT NULL,
    PLACA VARCHAR(50) NOT NULL,
    IMEI VARCHAR(50) NOT NULL,
    ID_TRANSPORTISTA BIGINT NOT NULL,
    FECHA_HORA TIMESTAMP NOT NULL,
    CONSTRAINT PK_CABEZAL PRIMARY KEY (ID_CABEZAL),
    CONSTRAINT FK_CABEZAL_1 FOREIGN KEY (ID_TRANSPORTISTA) REFERENCES TRANSPORTISTA (ID_TRANSPORTISTA)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS CLIENTE;
CREATE TABLE CLIENTE (
    ID_CLIENTE BIGINT NOT NULL,
    CODIGO VARCHAR(10) NOT NULL,
    NOMBRE VARCHAR(500) NOT NULL,
    FECHA_HORA TIMESTAMP NOT NULL,
    CONSTRAINT PK_CLIENTE PRIMARY KEY (ID_CLIENTE)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS CLIENTE_DESTINO;
CREATE TABLE CLIENTE_DESTINO (
    ID_CLIENTE_DESTINO BIGINT NOT NULL,
    CODIGO VARCHAR(10) NOT NULL,
    NOMBRE VARCHAR(500) NOT NULL,
    ID_CLIENTE BIGINT NOT NULL,
    FECHA_HORA TIMESTAMP NOT NULL,
    CONSTRAINT PK_CLIENTE_DESTINO PRIMARY KEY (ID_CLIENTE_DESTINO),
    CONSTRAINT FK_CLIENTE_DESTINO_1 FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTE (ID_CLIENTE)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS SMS_OPEN_ENCABEZADO;
CREATE TABLE SMS_OPEN_ENCABEZADO (
	ID_SMS_OPEN BIGINT NOT NULL,
    FECHA_ACTUALIZACION TIMESTAMP NOT NULL,
    NUMERO_UBICACIONES INTEGER NOT NULL,
    CONSTRAINT PK_SMS_OPEN_ENCABEZADO PRIMARY KEY (ID_SMS_OPEN)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS SMS_OPEN_DETALLE;
CREATE TABLE SMS_OPEN_DETALLE (
    ID_SMS_OPEN BIGINT NOT NULL,
    CORRELATIVO BIGINT NOT NULL,
    NAME_VEHICULO VARCHAR(500),
    IMEI VARCHAR(500),
    ODOMETER VARCHAR(500),
    LATITUDE VARCHAR(500),
    LONGITUDE VARCHAR(500),
    DATETIME_UBICACION VARCHAR(500),
    SPEED VARCHAR(500),
    SPEEDMEASURE VARCHAR(500),
    HEADING VARCHAR(500),
    LOCATIONDESCRIPTION VARCHAR(2000),
    DIRVERNAME VARCHAR(500),
    DRAVERCODE VARCHAR(500),
    IGNITION VARCHAR(500),
    DATEUTC VARCHAR(500),
    ADDRESS VARCHAR(500),
    FECHA_HORA TIMESTAMP NOT NULL,
    CONSTRAINT PK_SMS_OPEN_DETALLE PRIMARY KEY (ID_SMS_OPEN, CORRELATIVO),
    CONSTRAINT FK_SMS_OPEN_DETALLE_1 FOREIGN KEY (ID_SMS_OPEN) REFERENCES SMS_OPEN_ENCABEZADO (ID_SMS_OPEN)
) ENGINE = INNODB DEFAULT CHARACTER SET = UTF8MB4;

DROP TABLE IF EXISTS VIAJES;
CREATE TABLE VIAJES (
    ID_PAIS BIGINT NOT NULL,
    ID_COMPANIA BIGINT NOT NULL,
    ID_PLANTA BIGINT NOT NULL,
    NUMERO_VIAJE BIGINT NOT NULL,
    FECHA_VIAJE DATE NOT NULL,
    ID_ESTADO_VIAJE BIGINT NOT NULL,
    ID_VEHICULO BIGINT NOT NULL,
    ID_TRANSPORTISTA BIGINT NOT NULL,
    TIPO_ORDEN_VENTA VARCHAR(3) NOT NULL,
    NUMERO_ORDEN_VENTA BIGINT NOT NULL,
    ID_CLIENTE BIGINT NOT NULL,
    ID_CLIENTE_DESTINO BIGINT NOT NULL,
    TIPO_FLETE_VIAJE VARCHAR(3) NOT NULL,
    FECHA_HORA TIMESTAMP,
    ESTADO VARCHAR(3),
    FECHA_HORA_TERMINADO TIMESTAMP,
    CONSTRAINT PK_VIAJES PRIMARY KEY (ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA),
    CONSTRAINT FK_VIAJES_1 FOREIGN KEY (ID_PAIS) REFERENCES PAIS (ID_PAIS),
    CONSTRAINT FK_VIAJES_2 FOREIGN KEY (ID_COMPANIA) REFERENCES COMPANIA (ID_COMPANIA),
    CONSTRAINT FK_VIAJES_3 FOREIGN KEY (ID_PLANTA) REFERENCES PLANTA (ID_PLANTA),
    CONSTRAINT FK_VIAJES_4 FOREIGN KEY (ID_ESTADO_VIAJE) REFERENCES ESTADO_VIAJE (ID_ESTADO_VIAJE),
    CONSTRAINT FK_VIAJES_6 FOREIGN KEY (ID_VEHICULO) REFERENCES VEHICULO (ID_VEHICULO),
    CONSTRAINT FK_VIAJES_7 FOREIGN KEY (ID_TRANSPORTISTA) REFERENCES TRANSPORTISTA (ID_TRANSPORTISTA),
    CONSTRAINT FK_VIAJES_8 FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTE (ID_CLIENTE),
    CONSTRAINT FK_VIAJES_9 FOREIGN KEY (ID_CLIENTE_DESTINO) REFERENCES CLIENTE_DESTINO (ID_CLIENTE_DESTINO)
) ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4;

DROP TABLE IF EXISTS DISPONIBILIDAD;
CREATE TABLE DISPONIBILIDAD (
    ID_TRANSPORTISTA BIGINT NOT NULL,
    ID_VEHICULO BIGINT NOT NULL,
    ID_CABEZAL BIGINT,
    FECHA DATE NOT NULL,
    FECHA_HORA TIMESTAMP,
    CONSTRAINT PK_DISPONIBILIDAD PRIMARY KEY (ID_TRANSPORTISTA, ID_VEHICULO, ID_CABEZAL, FECHA),
    CONSTRAINT FK_DISPONIBILIDAD_1 FOREIGN KEY (ID_TRANSPORTISTA) REFERENCES TRANSPORTISTA (ID_TRANSPORTISTA),
    CONSTRAINT FK_DISPONIBILIDAD_2 FOREIGN KEY (ID_VEHICULO) REFERENCES VEHICULO (ID_VEHICULO),
    CONSTRAINT FK_DISPONIBILIDAD_3 FOREIGN KEY (ID_CABEZAL) REFERENCES CABEZAL (ID_CABEZAL)
) ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4;

DROP TABLE IF EXISTS VIAJE_UBICACIONES;
CREATE TABLE VIAJE_UBICACIONES (
	ID_PAIS BIGINT NOT NULL,
	ID_COMPANIA BIGINT NOT NULL,
	ID_PLANTA BIGINT NOT NULL,
	NUMERO_VIAJE BIGINT NOT NULL,
    TIPO_ORDEN_VENTA VARCHAR(3) NOT NULL,
    NUMERO_ORDEN_VENTA BIGINT NOT NULL,
    FECHA_HORA TIMESTAMP NOT NULL,
    IMEI VARCHAR(500) NOT NULL,
    LATITUDE VARCHAR(500) NOT NULL,
    LONGITUDE VARCHAR(500) NOT NULL,
    LOCATIONDESCRIPTION VARCHAR(2000),
    ETA_HORAS DOUBLE NOT NULL,
    EDA_KMS DOUBLE NOT NULL,
	CONSTRAINT PK_VIAJE_UBICACIONES PRIMARY KEY (ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA, FECHA_HORA, IMEI),
    CONSTRAINT FK_VIAJE_UBICACIONES_1 FOREIGN KEY (ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA) REFERENCES VIAJES (ID_PAIS, ID_COMPANIA, ID_PLANTA, NUMERO_VIAJE, TIPO_ORDEN_VENTA, NUMERO_ORDEN_VENTA)
) ENGINE=INNODB DEFAULT CHARACTER SET=UTF8MB4;
    
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (1, 'menu-Archivo');
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (2, 'menuItem-Inicio');
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (3, 'menuItem-Roles');
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (4, 'menuItem-Usuarios');
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (5, 'menu-Transporte');
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (6, 'menuItem-Viajes');
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (7, 'menuItem-Países');
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (8, 'menuItem-Compañías');
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (9, 'menuItem-Plantas');
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (10, 'menuItem-Estado-Viaje');
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (11, 'menuItem-Transportistas');
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (12, 'menuItem-Vehículos');
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (13, 'menuItem-Clientes');
INSERT INTO MENU (ID_MENU, NOMBRE) VALUES (14, 'menuItem-Clientes-Destino');

INSERT INTO ROL (ID_ROL, NOMBRE, ACTIVO, FECHA_HORA) VALUES (1, 'Administrador', 1, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 1, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 2, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 3, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 4, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 5, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 6, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 7, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 8, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 9, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 10, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 11, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 12, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 13, CURRENT_TIMESTAMP);
INSERT INTO ROL_MENU (ID_ROL, ID_MENU, FECHA_HORA) VALUES (1, 14, CURRENT_TIMESTAMP);

INSERT INTO USUARIO (ID_USUARIO, NOMBRE_COMPLETO, NOMBRE_USUARIO, CONTRASENA, CORREO_ELECTRONICO, ACTIVO, DESCRIPCION, ID_ROL, FECHA_HORA) 
VALUES (1, 'Administrador', 'admin', SHA2('admin2023', 512), 'rmolina@uno-ca.com', 1, 'Usuario admnistrador del sitema.', 1, CURRENT_TIMESTAMP);
INSERT INTO PROVEEDOR_GPS (ID_PROVEEDOR, NOMBRE, URL_SERVICIO, ACTIVO, FECHA_HORA) VALUES (1, 'SMS-OPEN', 'http://190.120.7.3/wsunopetrol/service.asmx?WSDL', 1, CURRENT_TIMESTAMP);

SELECT A.* FROM MENU A;
SELECT A.* FROM ROL A;
SELECT A.* FROM ROL_MENU A;
SELECT A.* FROM USUARIO A;
SELECT A.* FROM PROVEEDOR_GPS A;

SELECT A.* FROM PAIS A;
SELECT A.* FROM COMPANIA A;
SELECT A.* FROM PLANTA A;
SELECT A.* FROM ESTADO_VIAJE A;
SELECT A.* FROM VEHICULO A;
SELECT A.* FROM CABEZAL A;
SELECT A.* FROM TRANSPORTISTA A;
SELECT A.* FROM CLIENTE A;
SELECT A.* FROM CLIENTE_DESTINO A;

SELECT A.* FROM PROVEEDOR_GPS A;

SELECT A.* FROM VIAJES A;
SELECT A.* FROM VIAJE_UBICACIONES A;
SELECT A.* FROM DISPONIBILIDAD A;
SELECT A.* FROM SMS_OPEN_ENCABEZADO A;
SELECT A.* FROM SMS_OPEN_DETALLE A;

SELECT A.* FROM DISPONIBILIDAD A WHERE A.FECHA='2023-09-06';

SELECT A.* FROM CLIENTE_DESTINO A WHERE A.CODIGO=728012;
UPDATE CLIENTE_DESTINO SET
ZONA_LATITUD_1=13.446487, ZONA_LONGITUD_1=-89.06347,
ZONA_LATITUD_2=13.445245, ZONA_LONGITUD_2=-89.06347,
ZONA_LATITUD_3=13.445245, ZONA_LONGITUD_3=-89.061732,
ZONA_LATITUD_4=13.446487, ZONA_LONGITUD_4=-89.061732,
ZONA_LATITUD_5=13.446487, ZONA_LONGITUD_5=-89.06347
WHERE CODIGO=728012;

SELECT A.* FROM VEHICULO A WHERE A.ID_TRANSPORTISTA IN (10, 42) ORDER BY A.CODIGO;
SELECT A.* FROM CABEZAL A WHERE A.ID_TRANSPORTISTA IN (10, 42) ORDER BY A.CODIGO;