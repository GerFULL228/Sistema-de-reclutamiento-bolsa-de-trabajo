

INSERT INTO permisos(nombre) VALUES
                                 ('EMPRESA_VIEW'),
                                 ('EMPRESA_UPDATE'),
                                 ('VALIDAR_EMPRESA');



INSERT INTO permisos(nombre) VALUES
                                 ('POSTULANTE_VIEW'),
                                 ('POSTULANTE_UPDATE');



INSERT INTO permisos(nombre) VALUES
                                 ('POSTULACION_VIEW'),
                                 ('POSTULACION_REJECT'),
                                 ('POSTULACION_ACCEPT');



INSERT INTO permisos(nombre) VALUES
                                 ('CV_UPLOAD'),
                                 ('CV_VIEW'),
                                 ('CV_UPDATE'),
                                 ('CV_DELETE'),
                                 ('CV_DOWNLOAD');



INSERT INTO permisos(nombre) VALUES
                                 ('USUARIO_VIEW'),
                                 ('USUARIO_CREATE'),
                                 ('USUARIO_UPDATE'),
                                 ('USUARIO_DELETE'),
                                 ('ROL_VIEW'),
                                 ('ROL_ASSIGN');
INSERT INTO permisos(nombre) VALUES
                                 ('EMPRESA_VIEW_ALL'),
                                 ('POSTULANTE_VIEW_ALL'),
                                 ('OFERTA_VIEW_ALL'),
                                 ('POSTULACION_VIEW_ALL');

INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r, permisos p
WHERE r.nombre = 'ADMIN'
  AND NOT EXISTS (
    SELECT 1
    FROM rol_permisos rp
    WHERE rp.rol_id = r.id
      AND rp.permiso_id = p.id
);

INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r
         JOIN permisos p ON p.nombre IN (
                                         'OFERTA_CREATE',
                                         'OFERTA_VIEW',
                                         'OFERTA_UPDATE',
                                         'OFERTA_DELETE',

                                         'EMPRESA_VIEW',
                                         'EMPRESA_UPDATE',

                                         'POSTULACION_VIEW',
                                         'POSTULACION_ACCEPT',
                                         'POSTULACION_REJECT',

                                         'POSTULANTE_VIEW',

                                         'CV_VIEW',
                                         'CV_DOWNLOAD'
    )
WHERE r.nombre = 'EMPRESA'
  AND NOT EXISTS (
    SELECT 1
    FROM rol_permisos rp
    WHERE rp.rol_id = r.id
      AND rp.permiso_id = p.id
);

INSERT INTO rol_permisos (rol_id, permiso_id)
SELECT r.id, p.id
FROM roles r
         JOIN permisos p ON p.nombre IN (


                                         'POSTULANTE_VIEW',
                                         'POSTULANTE_UPDATE',

                                         'CV_UPLOAD',
                                         'CV_VIEW',
                                         'CV_UPDATE',
                                         'CV_DELETE'
    )
WHERE r.nombre = 'POSTULANTE';