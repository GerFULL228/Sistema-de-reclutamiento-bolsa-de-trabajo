
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'usuarios' AND column_name = 'estado'
    ) AND NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'usuarios' AND column_name = 'activo'
    ) THEN
        ALTER TABLE usuarios RENAME COLUMN estado TO activo;
    END IF;
END $$;
  

ALTER TABLE usuarios DROP COLUMN IF EXISTS estado;
 

INSERT INTO permisos (nombre) VALUES
    ('ENTREVISTA_CREATE'),
    ('ENTREVISTA_VIEW'),
    ('CV_CREATE'),
    ('CV_VIEW'),
    ('CV_UPDATE'),
    ('CV_DELETE')
ON CONFLICT (nombre) DO NOTHING;
 

INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r, permisos p
WHERE r.nombre = 'EMPRESA'
  AND p.nombre IN ('ENTREVISTA_CREATE', 'ENTREVISTA_VIEW')
ON CONFLICT (rol_id, permiso_id) DO NOTHING;
 

INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r, permisos p
WHERE r.nombre = 'POSTULANTE'
  AND p.nombre IN ('CV_CREATE', 'CV_VIEW', 'CV_UPDATE')
ON CONFLICT (rol_id, permiso_id) DO NOTHING;
 

INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r, permisos p
WHERE r.nombre = 'ADMIN'
  AND p.nombre IN ('ENTREVISTA_CREATE', 'ENTREVISTA_VIEW', 'CV_CREATE', 'CV_VIEW', 'CV_UPDATE', 'CV_DELETE')
ON CONFLICT (rol_id, permiso_id) DO NOTHING;
 

INSERT INTO usuarios (nombre, apellido, email, password, activo)
VALUES
    ('Admin',      'Sistema',   'admin@test.com',      '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', true),
    ('Empresa',    'Prueba',    'empresa@test.com',    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', true),
    ('Postulante', 'Prueba',    'postulante@test.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', true)
ON CONFLICT (email) DO NOTHING;
 

INSERT INTO usuario_roles (usuario_id, rol_id)
SELECT u.id, r.id
FROM usuarios u, roles r
WHERE u.email = 'admin@test.com' AND r.nombre = 'ADMIN'
ON CONFLICT (usuario_id, rol_id) DO NOTHING;
 
INSERT INTO usuario_roles (usuario_id, rol_id)
SELECT u.id, r.id
FROM usuarios u, roles r
WHERE u.email = 'empresa@test.com' AND r.nombre = 'EMPRESA'
ON CONFLICT (usuario_id, rol_id) DO NOTHING;
 
INSERT INTO usuario_roles (usuario_id, rol_id)
SELECT u.id, r.id
FROM usuarios u, roles r
WHERE u.email = 'postulante@test.com' AND r.nombre = 'POSTULANTE'
ON CONFLICT (usuario_id, rol_id) DO NOTHING;
 