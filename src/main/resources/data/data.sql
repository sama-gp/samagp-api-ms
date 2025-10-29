-- ===============================
--  DONNÉES DE RÉFÉRENCE
-- ===============================

-- 1. Suppression des contraintes de clé étrangère temporairement
ALTER TABLE zones_geos DROP CONSTRAINT IF EXISTS fk_zone_geo_type;
ALTER TABLE type_zone_geo DROP CONSTRAINT IF EXISTS fk_type_parent;

-- 2. Création des types de zones géographiques (seulement s'ils n'existent pas)
INSERT INTO type_zone_geo (code, libelle, parent_code)
SELECT 'PAYS', 'Pays', NULL
WHERE NOT EXISTS (SELECT 1 FROM type_zone_geo WHERE code = 'PAYS');

INSERT INTO type_zone_geo (code, libelle, parent_code)
SELECT 'VILLE', 'Ville', 'PAYS'
WHERE NOT EXISTS (SELECT 1 FROM type_zone_geo WHERE code = 'VILLE');

INSERT INTO type_zone_geo (code, libelle, parent_code)
SELECT 'AEROPORT', 'Aéroport', 'VILLE'
WHERE NOT EXISTS (SELECT 1 FROM type_zone_geo WHERE code = 'AEROPORT');

-- 3. Insertion des pays
INSERT INTO zones_geos (libelle, type_code)
SELECT 'Sénégal', 'PAYS'
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Sénégal' AND type_code = 'PAYS');

INSERT INTO zones_geos (libelle, type_code)
SELECT 'France', 'PAYS'
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'France' AND type_code = 'PAYS');

INSERT INTO zones_geos (libelle, type_code)
SELECT 'Maroc', 'PAYS'
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Maroc' AND type_code = 'PAYS');

-- 4. Sénégal : villes et aéroports
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Dakar', 'VILLE', (SELECT id FROM zones_geos WHERE libelle = 'Sénégal' AND type_code = 'PAYS')
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Dakar' AND type_code = 'VILLE');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Aéroport international Blaise Diagne', 'AEROPORT',
       (SELECT id FROM zones_geos WHERE libelle = 'Dakar' AND type_code = 'VILLE')
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Aéroport international Blaise Diagne' AND type_code = 'AEROPORT');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Saint-Louis', 'VILLE', (SELECT id FROM zones_geos WHERE libelle = 'Sénégal' AND type_code = 'PAYS')
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Saint-Louis' AND type_code = 'VILLE');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Aéroport international de Saint-Louis', 'AEROPORT',
       (SELECT id FROM zones_geos WHERE libelle = 'Saint-Louis' AND type_code = 'VILLE')
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Aéroport international de Saint-Louis' AND type_code = 'AEROPORT');

-- 5. France : villes et aéroports
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Paris', 'VILLE', (SELECT id FROM zones_geos WHERE libelle = 'France' AND type_code = 'PAYS')
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Paris' AND type_code = 'VILLE');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Aéroport Charles de Gaulle', 'AEROPORT',
       (SELECT id FROM zones_geos WHERE libelle = 'Paris' AND type_code = 'VILLE')
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Aéroport Charles de Gaulle' AND type_code = 'AEROPORT');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Aéroport d Orly', 'AEROPORT',
       (SELECT id FROM zones_geos WHERE libelle = 'Paris' AND type_code = 'VILLE')
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Aéroport d Orly' AND type_code = 'AEROPORT');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Nantes', 'VILLE', (SELECT id FROM zones_geos WHERE libelle = 'France' AND type_code = 'PAYS')
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Nantes' AND type_code = 'VILLE');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Aéroport Nantes Atlantique', 'AEROPORT',
       (SELECT id FROM zones_geos WHERE libelle = 'Nantes' AND type_code = 'VILLE')
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Aéroport Nantes Atlantique' AND type_code = 'AEROPORT');

-- 6. Maroc : villes et aéroports
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Casablanca', 'VILLE', (SELECT id FROM zones_geos WHERE libelle = 'Maroc' AND type_code = 'PAYS')
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Casablanca' AND type_code = 'VILLE');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Aéroport Mohammed V', 'AEROPORT',
       (SELECT id FROM zones_geos WHERE libelle = 'Casablanca' AND type_code = 'VILLE')
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Aéroport Mohammed V' AND type_code = 'AEROPORT');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Marrakech', 'VILLE', (SELECT id FROM zones_geos WHERE libelle = 'Maroc' AND type_code = 'PAYS')
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Marrakech' AND type_code = 'VILLE');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Aéroport Marrakech-Ménara', 'AEROPORT',
       (SELECT id FROM zones_geos WHERE libelle = 'Marrakech' AND type_code = 'VILLE')
WHERE NOT EXISTS (SELECT 1 FROM zones_geos WHERE libelle = 'Aéroport Marrakech-Ménara' AND type_code = 'AEROPORT');

-- 7. Réactivation des contraintes
ALTER TABLE zones_geos ADD CONSTRAINT fk_zone_geo_type FOREIGN KEY (type_code) REFERENCES type_zone_geo(code);
ALTER TABLE type_zone_geo ADD CONSTRAINT fk_type_parent FOREIGN KEY (parent_code) REFERENCES type_zone_geo(code);
