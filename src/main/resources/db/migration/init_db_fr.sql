-- Insérer le pays France
INSERT INTO zone_geo (nom, type, parent_id) VALUES ('France', 'PAYS', NULL);

-- Récupérer l'ID du pays France
SET @france_id = LAST_INSERT_ID();

-- Insérer les 13 régions de la France métropolitaine
INSERT INTO zone_geo (nom, type, parent_id) VALUES
('Auvergne-Rhône-Alpes', 'REGION', @france_id),
('Bourgogne-Franche-Comté', 'REGION', @france_id),
('Bretagne', 'REGION', @france_id),
('Centre-Val de Loire', 'REGION', @france_id),
('Corse', 'REGION', @france_id),
('Grand Est', 'REGION', @france_id),
('Hauts-de-France', 'REGION', @france_id),
('Île-de-France', 'REGION', @france_id),
('Normandie', 'REGION', @france_id),
('Nouvelle-Aquitaine', 'REGION', @france_id),
('Occitanie', 'REGION', @france_id),
('Pays de la Loire', 'REGION', @france_id),
('Provence-Alpes-Côte d\'Azur', 'REGION', @france_id);

-- Récupérer les IDs des régions
SET @ara_id = (SELECT id FROM zone_geo WHERE nom='Auvergne-Rhône-Alpes');
SET @bfc_id = (SELECT id FROM zone_geo WHERE nom='Bourgogne-Franche-Comté');
SET @bretagne_id = (SELECT id FROM zone_geo WHERE nom='Bretagne');
SET @cvdl_id = (SELECT id FROM zone_geo WHERE nom='Centre-Val de Loire');
SET @corse_id = (SELECT id FROM zone_geo WHERE nom='Corse');
SET @grandest_id = (SELECT id FROM zone_geo WHERE nom='Grand Est');
SET @hdf_id = (SELECT id FROM zone_geo WHERE nom='Hauts-de-France');
SET @idf_id = (SELECT id FROM zone_geo WHERE nom='Île-de-France');
SET @normandie_id = (SELECT id FROM zone_geo WHERE nom='Normandie');
SET @na_id = (SELECT id FROM zone_geo WHERE nom='Nouvelle-Aquitaine');
SET @occitanie_id = (SELECT id FROM zone_geo WHERE nom='Occitanie');
SET @pdl_id = (SELECT id FROM zone_geo WHERE nom='Pays de la Loire');
SET @paca_id = (SELECT id FROM zone_geo WHERE nom='Provence-Alpes-Côte d\'Azur');

-- Insérer les départements de chaque région
INSERT INTO zone_geo (nom, type, parent_id) VALUES
-- Auvergne-Rhône-Alpes
('Ain', 'DEPARTEMENT', @ara_id),
('Allier', 'DEPARTEMENT', @ara_id),
('Ardèche', 'DEPARTEMENT', @ara_id),
('Cantal', 'DEPARTEMENT', @ara_id),
('Drôme', 'DEPARTEMENT', @ara_id),
('Isère', 'DEPARTEMENT', @ara_id),
('Loire', 'DEPARTEMENT', @ara_id),
('Haute-Loire', 'DEPARTEMENT', @ara_id),
('Puy-de-Dôme', 'DEPARTEMENT', @ara_id),
('Rhône', 'DEPARTEMENT', @ara_id),
('Savoie', 'DEPARTEMENT', @ara_id),
('Haute-Savoie', 'DEPARTEMENT', @ara_id),

-- Bourgogne-Franche-Comté
('Côte-d\'Or', 'DEPARTEMENT', @bfc_id),
('Doubs', 'DEPARTEMENT', @bfc_id),
('Jura', 'DEPARTEMENT', @bfc_id),
('Nièvre', 'DEPARTEMENT', @bfc_id),
('Haute-Saône', 'DEPARTEMENT', @bfc_id),
('Saône-et-Loire', 'DEPARTEMENT', @bfc_id),
('Yonne', 'DEPARTEMENT', @bfc_id),
('Territoire de Belfort', 'DEPARTEMENT', @bfc_id),

-- Bretagne
('Côtes-d\'Armor', 'DEPARTEMENT', @bretagne_id),
('Finistère', 'DEPARTEMENT', @bretagne_id),
('Ille-et-Vilaine', 'DEPARTEMENT', @bretagne_id),
('Morbihan', 'DEPARTEMENT', @bretagne_id),

-- Centre-Val de Loire
('Cher', 'DEPARTEMENT', @cvdl_id),
('Eure-et-Loir', 'DEPARTEMENT', @cvdl_id),
('Indre', 'DEPARTEMENT', @cvdl_id),
('Indre-et-Loire', 'DEPARTEMENT', @cvdl_id),
('Loir-et-Cher', 'DEPARTEMENT', @cvdl_id),
('Loiret', 'DEPARTEMENT', @cvdl_id),

-- Corse
('Corse-du-Sud', 'DEPARTEMENT', @corse_id),
('Haute-Corse', 'DEPARTEMENT', @corse_id),

-- Grand Est
('Ardennes', 'DEPARTEMENT', @grandest_id),
('Aube', 'DEPARTEMENT', @grandest_id),
('Marne', 'DEPARTEMENT', @grandest_id),
('Haute-Marne', 'DEPARTEMENT', @grandest_id),
('Meurthe-et-Moselle', 'DEPARTEMENT', @grandest_id),
('Meuse', 'DEPARTEMENT', @grandest_id),
('Moselle', 'DEPARTEMENT', @grandest_id),
('Bas-Rhin', 'DEPARTEMENT', @grandest_id),
('Haut-Rhin', 'DEPARTEMENT', @grandest_id),
('Vosges', 'DEPARTEMENT', @grandest_id),

-- Hauts-de-France
('Aisne', 'DEPARTEMENT', @hdf_id),
('Nord', 'DEPARTEMENT', @hdf_id),
('Oise', 'DEPARTEMENT', @hdf_id),
('Pas-de-Calais', 'DEPARTEMENT', @hdf_id),
('Somme', 'DEPARTEMENT', @hdf_id),

-- Île-de-France
('Paris', 'DEPARTEMENT', @idf_id),
('Seine-et-Marne', 'DEPARTEMENT', @idf_id),
('Yvelines', 'DEPARTEMENT', @idf_id),
('Essonne', 'DEPARTEMENT', @idf_id),
('Hauts-de-Seine', 'DEPARTEMENT', @idf_id),
('Seine-Saint-Denis', 'DEPARTEMENT', @idf_id),
('Val-de-Marne', 'DEPARTEMENT', @idf_id),
('Val-d\'Oise', 'DEPARTEMENT', @idf_id);

-- (Ajoutez les autres départements des régions restantes si nécessaire)
