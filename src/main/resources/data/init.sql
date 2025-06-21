-- 1. Création des types de zones géographiques
-- 1. Suppression des contraintes de clé étrangère temporairement
ALTER TABLE zones_geos DROP CONSTRAINT IF EXISTS fk_zone_geo_type;
ALTER TABLE type_zone_geo DROP CONSTRAINT IF EXISTS fk_type_parent;

-- 2. Insertion des types SEULEMENT s'ils n'existent pas
INSERT INTO type_zone_geo (code, libelle, parent_code)
SELECT 'PAYS', 'Pays', NULL
WHERE NOT EXISTS (SELECT 1 FROM type_zone_geo WHERE code = 'PAYS');

INSERT INTO type_zone_geo (code, libelle, parent_code)
SELECT 'REGION', 'Région', 'PAYS'
WHERE NOT EXISTS (SELECT 1 FROM type_zone_geo WHERE code = 'REGION');

INSERT INTO type_zone_geo (code, libelle, parent_code)
SELECT 'DEPARTEMENT', 'Département', 'REGION'
WHERE NOT EXISTS (SELECT 1 FROM type_zone_geo WHERE code = 'DEPARTEMENT');

-- 2. Insertion des pays SEULEMENT s'ils n'existent pas
INSERT INTO zones_geos (libelle, type_code)
SELECT 'Sénégal', 'PAYS'
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Sénégal' AND type_code = 'PAYS');

INSERT INTO zones_geos (libelle, type_code)
SELECT 'France', 'PAYS'
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'France' AND type_code = 'PAYS');

INSERT INTO zones_geos (libelle, type_code)
SELECT 'Turquie', 'PAYS'
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Turquie' AND type_code = 'PAYS');

INSERT INTO zones_geos (libelle, type_code)
SELECT 'Maroc', 'PAYS'
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Maroc' AND type_code = 'PAYS');

INSERT INTO zones_geos (libelle, type_code)
SELECT 'Canada', 'PAYS'
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Canada' AND type_code = 'PAYS');

INSERT INTO zones_geos (libelle, type_code)
SELECT 'Italie', 'PAYS'
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Italie' AND type_code = 'PAYS');

INSERT INTO zones_geos (libelle, type_code)
SELECT 'Espagne', 'PAYS'
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Espagne' AND type_code = 'PAYS');

-- 3. Insertion des régions du Sénégal SEULEMENT si elles n'existent pas
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Dakar', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Dakar' AND type_code = 'REGION');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Diourbel', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Diourbel' AND type_code = 'REGION');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Fatick', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Fatick' AND type_code = 'REGION');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Kaffrine', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Kaffrine' AND type_code = 'REGION');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Kaolack', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Kaolack' AND type_code = 'REGION');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Kédougou', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Kédougou' AND type_code = 'REGION');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Kolda', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Kolda' AND type_code = 'REGION');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Louga', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Louga' AND type_code = 'REGION');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Matam', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Matam' AND type_code = 'REGION');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Saint-Louis', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Saint-Louis' AND type_code = 'REGION');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Sédhiou', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Sédhiou' AND type_code = 'REGION');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Tambacounda', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Tambacounda' AND type_code = 'REGION');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Thiès', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Thiès' AND type_code = 'REGION');

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Ziguinchor', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Sénégal')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Ziguinchor' AND type_code = 'REGION');

-- 4. Insertion des départements pour chaque région SEULEMENT s'ils n'existent pas

-- Départements de Dakar
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Dakar', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Dakar')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Dakar' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Dakar'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Guédiawaye', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Dakar')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Guédiawaye' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Dakar'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Pikine', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Dakar')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Pikine' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Dakar'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Rufisque', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Dakar')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Rufisque' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Dakar'));

-- Départements de Diourbel
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Bambey', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Diourbel')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Bambey' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Diourbel'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Diourbel', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Diourbel')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Diourbel' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Diourbel'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Mbacké', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Diourbel')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Mbacké' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Diourbel'));

-- Départements de Fatick
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Fatick', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Fatick')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Fatick' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Fatick'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Foundiougne', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Fatick')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Foundiougne' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Fatick'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Gossas', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Fatick')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Gossas' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Fatick'));

-- Départements de Kaffrine
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Kaffrine', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Kaffrine')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Kaffrine' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Kaffrine'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Koungheul', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Kaffrine')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Koungheul' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Kaffrine'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Malem Hodar', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Kaffrine')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Malem Hodar' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Kaffrine'));

-- Départements de Kaolack
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Guinguinéo', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Kaolack')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Guinguinéo' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Kaolack'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Kaolack', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Kaolack')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Kaolack' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Kaolack'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Nioro du Rip', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Kaolack')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Nioro du Rip' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Kaolack'));

-- Départements de Kédougou
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Kédougou', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Kédougou')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Kédougou' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Kédougou'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Salémata', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Kédougou')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Salémata' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Kédougou'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Saraya', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Kédougou')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Saraya' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Kédougou'));

-- Départements de Kolda
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Kolda', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Kolda')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Kolda' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Kolda'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Médina Yoro Foulah', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Kolda')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Médina Yoro Foulah' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Kolda'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Vélingara', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Kolda')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Vélingara' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Kolda'));

-- Départements de Louga
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Kébémer', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Louga')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Kébémer' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Louga'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Linguère', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Louga')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Linguère' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Louga'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Louga', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Louga')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Louga' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Louga'));

-- Départements de Matam
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Kanel', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Matam')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Kanel' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Matam'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Matam', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Matam')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Matam' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Matam'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Ranérou', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Matam')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Ranérou' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Matam'));

-- Départements de Saint-Louis
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Dagana', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Saint-Louis')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Dagana' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Saint-Louis'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Podor', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Saint-Louis')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Podor' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Saint-Louis'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Saint-Louis', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Saint-Louis')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Saint-Louis' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Saint-Louis'));

-- Départements de Sédhiou
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Bounkiling', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Sédhiou')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Bounkiling' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Sédhiou'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Goudomp', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Sédhiou')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Goudomp' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Sédhiou'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Sédhiou', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Sédhiou')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Sédhiou' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Sédhiou'));

-- Départements de Tambacounda
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Bakel', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Tambacounda')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Bakel' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Tambacounda'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Goudiry', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Tambacounda')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Goudiry' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Tambacounda'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Koumpentoum', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Tambacounda')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Koumpentoum' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Tambacounda'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Tambacounda', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Tambacounda')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Tambacounda' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Tambacounda'));

-- Départements de Thiès
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Mbour', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Thiès')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Mbour' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Thiès'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Thiès', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Thiès')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Thiès' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Thiès'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Tivaouane', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Thiès')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Tivaouane' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Thiès'));

-- Départements de Ziguinchor
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Bignona', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Ziguinchor')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Bignona' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Ziguinchor'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Oussouye', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Ziguinchor')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Oussouye' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Ziguinchor'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Ziguinchor', 'DEPARTEMENT', (SELECT id FROM zone_geo WHERE libelle = 'Ziguinchor')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Ziguinchor' AND type_code = 'DEPARTEMENT' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Ziguinchor'));

-- 2. Insertion des villes/régions pour chaque pays SEULEMENT si elles n'existent pas

-- France (Régions et villes principales)
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Île-de-France', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'France')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Île-de-France' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'France'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Auvergne-Rhône-Alpes', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'France')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Auvergne-Rhône-Alpes' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'France'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Nouvelle-Aquitaine', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'France')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Nouvelle-Aquitaine' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'France'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Occitanie', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'France')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Occitanie' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'France'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Hauts-de-France', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'France')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Hauts-de-France' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'France'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Provence-Alpes-Côte d''Azur', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'France')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Provence-Alpes-Côte d''Azur' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'France'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Grand Est', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'France')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Grand Est' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'France'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Normandie', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'France')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Normandie' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'France'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Bretagne', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'France')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Bretagne' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'France'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Pays de la Loire', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'France')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Pays de la Loire' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'France'));

-- Villes françaises
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Paris', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Île-de-France')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Paris' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Île-de-France'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Lyon', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Auvergne-Rhône-Alpes')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Lyon' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Auvergne-Rhône-Alpes'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Marseille', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Provence-Alpes-Côte d''Azur')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Marseille' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Provence-Alpes-Côte d''Azur'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Toulouse', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Occitanie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Toulouse' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Occitanie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Nice', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Provence-Alpes-Côte d''Azur')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Nice' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Provence-Alpes-Côte d''Azur'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Nantes', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Pays de la Loire')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Nantes' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Pays de la Loire'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Strasbourg', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Grand Est')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Strasbourg' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Grand Est'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Montpellier', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Occitanie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Montpellier' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Occitanie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Bordeaux', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Nouvelle-Aquitaine')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Bordeaux' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Nouvelle-Aquitaine'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Lille', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Hauts-de-France')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Lille' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Hauts-de-France'));

-- Turquie (Villes principales)
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Istanbul', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Turquie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Istanbul' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Turquie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Ankara', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Turquie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Ankara' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Turquie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Izmir', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Turquie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Izmir' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Turquie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Bursa', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Turquie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Bursa' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Turquie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Antalya', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Turquie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Antalya' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Turquie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Adana', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Turquie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Adana' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Turquie'));

-- Maroc (Régions et villes principales)
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Casablanca-Settat', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Maroc')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Casablanca-Settat' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Maroc'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Rabat-Salé-Kénitra', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Maroc')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Rabat-Salé-Kénitra' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Maroc'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Fès-Meknès', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Maroc')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Fès-Meknès' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Maroc'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Marrakech-Safi', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Maroc')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Marrakech-Safi' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Maroc'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Tanger-Tétouan-Al Hoceïma', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Maroc')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Tanger-Tétouan-Al Hoceïma' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Maroc'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Casablanca', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Casablanca-Settat')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Casablanca' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Casablanca-Settat'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Rabat', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Rabat-Salé-Kénitra')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Rabat' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Rabat-Salé-Kénitra'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Fès', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Fès-Meknès')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Fès' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Fès-Meknès'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Marrakech', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Marrakech-Safi')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Marrakech' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Marrakech-Safi'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Tanger', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Tanger-Tétouan-Al Hoceïma')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Tanger' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Tanger-Tétouan-Al Hoceïma'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Agadir', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Marrakech-Safi')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Agadir' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Marrakech-Safi'));

-- Canada (Provinces et villes principales)
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Ontario', 'PROVINCE', (SELECT id FROM zone_geo WHERE libelle = 'Canada')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Ontario' AND type_code = 'PROVINCE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Canada'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Québec', 'PROVINCE', (SELECT id FROM zone_geo WHERE libelle = 'Canada')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Québec' AND type_code = 'PROVINCE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Canada'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Colombie-Britannique', 'PROVINCE', (SELECT id FROM zone_geo WHERE libelle = 'Canada')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Colombie-Britannique' AND type_code = 'PROVINCE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Canada'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Alberta', 'PROVINCE', (SELECT id FROM zone_geo WHERE libelle = 'Canada')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Alberta' AND type_code = 'PROVINCE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Canada'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Nouvelle-Écosse', 'PROVINCE', (SELECT id FROM zone_geo WHERE libelle = 'Canada')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Nouvelle-Écosse' AND type_code = 'PROVINCE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Canada'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Toronto', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Ontario')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Toronto' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Ontario'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Montréal', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Québec')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Montréal' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Québec'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Vancouver', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Colombie-Britannique')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Vancouver' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Colombie-Britannique'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Calgary', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Alberta')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Calgary' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Alberta'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Ottawa', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Ontario')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Ottawa' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Ontario'));

-- Italie (Régions et villes principales)
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Lombardie', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Italie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Lombardie' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Italie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Latium', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Italie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Latium' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Italie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Campanie', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Italie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Campanie' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Italie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Vénétie', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Italie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Vénétie' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Italie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Toscane', 'REGION', (SELECT id FROM zone_geo WHERE libelle = 'Italie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Toscane' AND type_code = 'REGION' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Italie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Rome', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Latium')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Rome' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Latium'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Milan', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Lombardie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Milan' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Lombardie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Naples', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Campanie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Naples' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Campanie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Venise', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Vénétie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Venise' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Vénétie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Florence', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Toscane')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Florence' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Toscane'));

-- Espagne (Communautés autonomes et villes principales)
INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Andalousie', 'COMMUNAUTE', (SELECT id FROM zone_geo WHERE libelle = 'Espagne')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Andalousie' AND type_code = 'COMMUNAUTE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Espagne'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Catalogne', 'COMMUNAUTE', (SELECT id FROM zone_geo WHERE libelle = 'Espagne')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Catalogne' AND type_code = 'COMMUNAUTE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Espagne'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Madrid', 'COMMUNAUTE', (SELECT id FROM zone_geo WHERE libelle = 'Espagne')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Madrid' AND type_code = 'COMMUNAUTE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Espagne'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Pays Basque', 'COMMUNAUTE', (SELECT id FROM zone_geo WHERE libelle = 'Espagne')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Pays Basque' AND type_code = 'COMMUNAUTE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Espagne'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Valence', 'COMMUNAUTE', (SELECT id FROM zone_geo WHERE libelle = 'Espagne')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Valence' AND type_code = 'COMMUNAUTE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Espagne'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Madrid', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Madrid')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Madrid' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Madrid'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Barcelone', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Catalogne')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Barcelone' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Catalogne'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Séville', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Andalousie')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Séville' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Andalousie'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Valence', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Valence')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Valence' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Valence'));

INSERT INTO zones_geos (libelle, type_code, parent_id)
SELECT 'Bilbao', 'VILLE', (SELECT id FROM zone_geo WHERE libelle = 'Pays Basque')
WHERE NOT EXISTS (SELECT 1 FROM zone_geo WHERE libelle = 'Bilbao' AND type_code = 'VILLE' AND parent_id = (SELECT id FROM zone_geo WHERE libelle = 'Pays Basque'));

-- Réactivation des contraintes de clé étrangère
ALTER TABLE zones_geos ADD CONSTRAINT fk_zone_geo_type FOREIGN KEY (type_code) REFERENCES type_zone_geo(code);
ALTER TABLE type_zone_geo ADD CONSTRAINT fk_type_parent FOREIGN KEY (parent_code) REFERENCES type_zone_geo(code);