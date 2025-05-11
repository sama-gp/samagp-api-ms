-- Script d'insertion de données géographiques amélioré pour Flyway
-- Gestion du Sénégal et de la France avec leurs subdivisions administratives

-- Insertion des pays avec gestion des doublons
INSERT INTO zone_geo (nom, type, parent_id)
VALUES ('Sénégal', 'Pays', NULL), ('France', 'Pays', NULL)
ON CONFLICT (nom, type) WHERE parent_id IS NULL DO NOTHING;

-- Insertion des régions du Sénégal
WITH senegal_id AS (
    SELECT id FROM zone_geo WHERE nom = 'Sénégal' AND type = 'Pays'
)
INSERT INTO zone_geo (nom, type, parent_id)
SELECT unnest(array['Dakar', 'Thiès', 'Louga']), 'Région', (SELECT id FROM senegal_id)
ON CONFLICT (nom, type, COALESCE(parent_id, -1)) DO NOTHING;

-- Insertion des départements de Dakar
WITH dakar_id AS (
    SELECT id FROM zone_geo WHERE nom = 'Dakar' AND type = 'Région'
)
INSERT INTO zone_geo (nom, type, parent_id)
SELECT unnest(array['Plateau', 'Gorée', 'Yoff']), 'Département', (SELECT id FROM dakar_id)
ON CONFLICT (nom, type, COALESCE(parent_id, -1)) DO NOTHING;

-- Insertion des départements de Thiès
WITH thies_id AS (
    SELECT id FROM zone_geo WHERE nom = 'Thiès' AND type = 'Région'
)
INSERT INTO zone_geo (nom, type, parent_id)
SELECT unnest(array['Thiès-Ville', 'Mbour', 'Tivaouane']), 'Département', (SELECT id FROM thies_id)
ON CONFLICT (nom, type, COALESCE(parent_id, -1)) DO NOTHING;

-- Insertion des départements de Louga
WITH louga_id AS (
    SELECT id FROM zone_geo WHERE nom = 'Louga' AND type = 'Région'
)
INSERT INTO zone_geo (nom, type, parent_id)
SELECT unnest(array['Louga-Ville', 'Kébémer']), 'Département', (SELECT id FROM louga_id)
ON CONFLICT (nom, type, COALESCE(parent_id, -1)) DO NOTHING;

-- Insertion des régions de France
WITH france_id AS (
    SELECT id FROM zone_geo WHERE nom = 'France' AND type = 'Pays'
)
INSERT INTO zone_geo (nom, type, parent_id)
SELECT unnest(array[
    'Auvergne-Rhône-Alpes',
    'Bourgogne-Franche-Comté',
    'Bretagne',
    'Centre-Val de Loire',
    'Corse',
    'Grand Est',
    'Hauts-de-France',
    'Île-de-France',
    'Normandie',
    'Nouvelle-Aquitaine',
    'Occitanie',
    'Pays de la Loire',
    'Provence-Alpes-Côte d''Azur'
]), 'Région', (SELECT id FROM france_id)
ON CONFLICT (nom, type, COALESCE(parent_id, -1)) DO NOTHING;

-- Insertion des départements d'Île-de-France
WITH region_id AS (
    SELECT id FROM zone_geo WHERE nom = 'Île-de-France' AND type = 'Région'
)
INSERT INTO zone_geo (nom, type, parent_id)
SELECT unnest(array[
    'Paris',
    'Seine-et-Marne',
    'Yvelines',
    'Essonne',
    'Hauts-de-Seine',
    'Seine-Saint-Denis',
    'Val-de-Marne',
    'Val-d''Oise'
]), 'Département', (SELECT id FROM region_id)
ON CONFLICT (nom, type, COALESCE(parent_id, -1)) DO NOTHING;

-- Insertion des départements d'Auvergne-Rhône-Alpes
WITH region_id AS (
    SELECT id FROM zone_geo WHERE nom = 'Auvergne-Rhône-Alpes' AND type = 'Région'
)
INSERT INTO zone_geo (nom, type, parent_id)
SELECT unnest(array[
    'Ain',
    'Allier',
    'Ardèche',
    'Cantal',
    'Drôme',
    'Isère',
    'Loire',
    'Haute-Loire',
    'Puy-de-Dôme',
    'Rhône',
    'Savoie',
    'Haute-Savoie'
]), 'Département', (SELECT id FROM region_id)
ON CONFLICT (nom, type, COALESCE(parent_id, -1)) DO NOTHING;

-- Insertion des départements de Bretagne
WITH region_id AS (
    SELECT id FROM zone_geo WHERE nom = 'Bretagne' AND type = 'Région'
)
INSERT INTO zone_geo (nom, type, parent_id)
SELECT unnest(array[
    'Côtes-d''Armor',
    'Finistère',
    'Ille-et-Vilaine',
    'Morbihan'
]), 'Département', (SELECT id FROM region_id)
ON CONFLICT (nom, type, COALESCE(parent_id, -1)) DO NOTHING;