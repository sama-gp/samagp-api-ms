-- ===============================
--  SCHEMA SQL : création des tables
-- ===============================

DROP TABLE IF EXISTS zones_geos CASCADE;
DROP TABLE IF EXISTS type_zone_geo CASCADE;

-- Table des types de zones géographiques
CREATE TABLE type_zone_geo (
    code VARCHAR(50) PRIMARY KEY,
    libelle VARCHAR(255) NOT NULL,
    parent_code VARCHAR(50),
    CONSTRAINT fk_type_parent FOREIGN KEY (parent_code) REFERENCES type_zone_geo(code)
);

-- Table des zones géographiques
CREATE TABLE zones_geos (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(255) NOT NULL,
    type_code VARCHAR(50) NOT NULL,
    parent_id INTEGER,
    CONSTRAINT fk_zone_geo_type FOREIGN KEY (type_code) REFERENCES type_zone_geo(code),
    CONSTRAINT fk_zone_geo_parent FOREIGN KEY (parent_id) REFERENCES zones_geos(id)
);
