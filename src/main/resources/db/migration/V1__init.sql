
CREATE TABLE usuarios (
                          id BIGSERIAL PRIMARY KEY,
                          nombre VARCHAR(100),
                          apellido VARCHAR(100),
                          email VARCHAR(150) UNIQUE NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          estado BOOLEAN DEFAULT TRUE,
                          fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       nombre VARCHAR(50) UNIQUE NOT NULL
);


CREATE TABLE permisos (
                          id BIGSERIAL PRIMARY KEY,
                          nombre VARCHAR(100) UNIQUE NOT NULL
);


CREATE TABLE usuario_roles (
                               id BIGSERIAL PRIMARY KEY,
                               usuario_id BIGINT NOT NULL,
                               rol_id BIGINT NOT NULL,
                               CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
                               CONSTRAINT fk_rol FOREIGN KEY (rol_id) REFERENCES roles(id),
                               CONSTRAINT unique_usuario_rol UNIQUE (usuario_id, rol_id)
);


CREATE TABLE rol_permisos (
                              id BIGSERIAL PRIMARY KEY,
                              rol_id BIGINT NOT NULL,
                              permiso_id BIGINT NOT NULL,
                              CONSTRAINT fk_rol_perm FOREIGN KEY (rol_id) REFERENCES roles(id),
                              CONSTRAINT fk_perm FOREIGN KEY (permiso_id) REFERENCES permisos(id),
                              CONSTRAINT unique_rol_perm UNIQUE (rol_id, permiso_id)
);


CREATE TABLE empresas (
                          id BIGSERIAL PRIMARY KEY,
                          usuario_id BIGINT UNIQUE,
                          nombre_empresa VARCHAR(150),
                          ruc VARCHAR(20),
                          descripcion TEXT,
                          direccion VARCHAR(200),
                          pagina_web VARCHAR(150),
                          estado_validacion VARCHAR(30) DEFAULT 'PENDIENTE',
                          CONSTRAINT fk_empresa_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);


CREATE TABLE postulantes (
                             id BIGSERIAL PRIMARY KEY,
                             usuario_id BIGINT UNIQUE,
                             fecha_nacimiento DATE,
                             genero VARCHAR(20),
                             direccion VARCHAR(200),
                             CONSTRAINT fk_postulante_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE ofertas (
                         id BIGSERIAL PRIMARY KEY,
                         empresa_id BIGINT NOT NULL,
                         titulo VARCHAR(150),
                         descripcion TEXT,
                         salario NUMERIC(10,2),
                         ubicacion VARCHAR(150),
                         tipo_empleo VARCHAR(50),
                         estado VARCHAR(30) DEFAULT 'PENDIENTE',
                         fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_oferta_empresa FOREIGN KEY (empresa_id) REFERENCES empresas(id)
);


CREATE TABLE postulaciones (
                               id BIGSERIAL PRIMARY KEY,
                               postulante_id BIGINT NOT NULL,
                               oferta_id BIGINT NOT NULL,
                               fecha_postulacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               estado VARCHAR(30) DEFAULT 'ENVIADO',
                               CONSTRAINT fk_postulacion_postulante FOREIGN KEY (postulante_id) REFERENCES postulantes(id),
                               CONSTRAINT fk_postulacion_oferta FOREIGN KEY (oferta_id) REFERENCES ofertas(id),
                               CONSTRAINT unique_postulacion UNIQUE (postulante_id, oferta_id)
);


INSERT INTO roles (nombre) VALUES
                               ('ADMIN'),
                               ('EMPRESA'),
                               ('POSTULANTE');


INSERT INTO permisos (nombre) VALUES
                                  ('OFERTA_CREATE'),
                                  ('OFERTA_VIEW'),
                                  ('OFERTA_UPDATE'),
                                  ('OFERTA_DELETE'),
                                  ('POSTULAR'),
                                  ('VALIDAR_OFERTA');




INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r, permisos p
WHERE r.nombre = 'ADMIN';



INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r, permisos p
WHERE r.nombre = 'EMPRESA'
  AND p.nombre IN ('OFERTA_CREATE', 'OFERTA_VIEW', 'OFERTA_UPDATE');



INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r, permisos p
WHERE r.nombre = 'POSTULANTE'
  AND p.nombre IN ('OFERTA_VIEW', 'POSTULAR');
