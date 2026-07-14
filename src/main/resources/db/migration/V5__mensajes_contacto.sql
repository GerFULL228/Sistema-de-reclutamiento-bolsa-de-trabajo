CREATE TABLE mensajes_contacto (
                                    id BIGSERIAL PRIMARY KEY,
                                    nombre VARCHAR(150) NOT NULL,
                                    email VARCHAR(150) NOT NULL,
                                    asunto VARCHAR(200) NOT NULL,
                                    mensaje TEXT NOT NULL,
                                    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
