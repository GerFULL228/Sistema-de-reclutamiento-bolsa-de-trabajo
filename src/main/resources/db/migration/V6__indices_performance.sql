-- Índices para acelerar los filtros/joins más usados por los dashboards de Admin y Empresa,
-- que antes forzaban un Seq Scan completo de las tablas a medida que crecen los datos.

-- Filtrado de ofertas por estado (listarOfertasActivas, counts de stats por estado).
CREATE INDEX IF NOT EXISTS idx_ofertas_estado ON ofertas (estado);

-- JOIN oferta -> empresa (listarOfertasEmpresa, countByEmpresa_Usuario_Id). Postgres no
-- indexa automáticamente las columnas de FK.
CREATE INDEX IF NOT EXISTS idx_ofertas_empresa_id ON ofertas (empresa_id);

-- Filtro por estado de validación en el panel admin (empresas pendientes/activas/rechazadas).
CREATE INDEX IF NOT EXISTS idx_empresas_estado_validacion ON empresas (estado_validacion);

-- Usado por existsByRuc antes de crear una empresa (registro y seeders).
CREATE INDEX IF NOT EXISTS idx_empresas_ruc ON empresas (ruc);

-- Filtro habilitado/deshabilitado en el panel admin (usuarios y empresas).
CREATE INDEX IF NOT EXISTS idx_usuarios_activo ON usuarios (activo);

-- Postulaciones por oferta (obtenerPorOferta / obtenerPorEstadoYEmpresa): el UNIQUE
-- existente (postulante_id, oferta_id) solo sirve como índice para filtros por
-- postulante_id; oferta_id como columna aislada necesita su propio índice.
CREATE INDEX IF NOT EXISTS idx_postulaciones_oferta_id ON postulaciones (oferta_id);
