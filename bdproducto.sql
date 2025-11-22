CREATE TABLE producto (
    id_producto SERIAL PRIMARY KEY,
    id_categoria INT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    precio NUMERIC(10,2) NOT NULL CHECK (precio >= 0),
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_producto_categoria
        FOREIGN KEY (id_categoria)
        REFERENCES categoria(id_categoria)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE categoria (
    id_categoria SERIAL PRIMARY KEY 
    nombre VARCHAR(50)
    activa BOOLEAN NOT NULL
)

CREATE TABLE inventario (
    id_inventario  SERIAL PRIMARY KEY,
    id_producto    INT NOT NULL UNIQUE,
    stock_actual   INT NOT NULL DEFAULT 0 CHECK (stock_actual >= 0),
    stock_minimo   INT NOT NULL DEFAULT 0 CHECK (stock_minimo >= 0),
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_inventario_producto
        FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)