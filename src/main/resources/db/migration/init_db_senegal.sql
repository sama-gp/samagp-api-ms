-- Insérer le pays Sénégal
INSERT INTO zone_geo (nom, type, parent_id) VALUES ('Sénégal', 'PAYS', NULL);

-- Récupérer l'ID du pays Sénégal
SET @senegal_id = LAST_INSERT_ID();

-- Insérer les 14 régions
INSERT INTO zone_geo (nom, type, parent_id) VALUES
('Dakar', 'REGION', @senegal_id),
('Diourbel', 'REGION', @senegal_id),
('Fatick', 'REGION', @senegal_id),
('Kaffrine', 'REGION', @senegal_id),
('Kaolack', 'REGION', @senegal_id),
('Kédougou', 'REGION', @senegal_id),
('Kolda', 'REGION', @senegal_id),
('Louga', 'REGION', @senegal_id),
('Matam', 'REGION', @senegal_id),
('Saint-Louis', 'REGION', @senegal_id),
('Sédhiou', 'REGION', @senegal_id),
('Tambacounda', 'REGION', @senegal_id),
('Thiès', 'REGION', @senegal_id),
('Ziguinchor', 'REGION', @senegal_id);

-- Récupérer les IDs des régions pour lier les départements
SET @dakar_id = (SELECT id FROM zone_geo WHERE nom='Dakar');
SET @diourbel_id = (SELECT id FROM zone_geo WHERE nom='Diourbel');
SET @fatick_id = (SELECT id FROM zone_geo WHERE nom='Fatick');
SET @kaffrine_id = (SELECT id FROM zone_geo WHERE nom='Kaffrine');
SET @kaolack_id = (SELECT id FROM zone_geo WHERE nom='Kaolack');
SET @kedougou_id = (SELECT id FROM zone_geo WHERE nom='Kédougou');
SET @kolda_id = (SELECT id FROM zone_geo WHERE nom='Kolda');
SET @louga_id = (SELECT id FROM zone_geo WHERE nom='Louga');
SET @matam_id = (SELECT id FROM zone_geo WHERE nom='Matam');
SET @saintlouis_id = (SELECT id FROM zone_geo WHERE nom='Saint-Louis');
SET @sedhiou_id = (SELECT id FROM zone_geo WHERE nom='Sédhiou');
SET @tambacounda_id = (SELECT id FROM zone_geo WHERE nom='Tambacounda');
SET @thies_id = (SELECT id FROM zone_geo WHERE nom='Thiès');
SET @ziguinchor_id = (SELECT id FROM zone_geo WHERE nom='Ziguinchor');

-- Insérer les départements de chaque région
INSERT INTO zone_geo (nom, type, parent_id) VALUES
-- Dakar
('Dakar', 'DEPARTEMENT', @dakar_id),
('Guédiawaye', 'DEPARTEMENT', @dakar_id),
('Pikine', 'DEPARTEMENT', @dakar_id),
('Rufisque', 'DEPARTEMENT', @dakar_id),

-- Diourbel
('Bambey', 'DEPARTEMENT', @diourbel_id),
('Diourbel', 'DEPARTEMENT', @diourbel_id),
('Mbacké', 'DEPARTEMENT', @diourbel_id),

-- Fatick
('Fatick', 'DEPARTEMENT', @fatick_id),
('Foundiougne', 'DEPARTEMENT', @fatick_id),
('Gossas', 'DEPARTEMENT', @fatick_id),

-- Kaffrine
('Kaffrine', 'DEPARTEMENT', @kaffrine_id),
('Koungheul', 'DEPARTEMENT', @kaffrine_id),
('Malem Hodar', 'DEPARTEMENT', @kaffrine_id),

-- Kaolack
('Guinguinéo', 'DEPARTEMENT', @kaolack_id),
('Kaolack', 'DEPARTEMENT', @kaolack_id),
('Nioro du Rip', 'DEPARTEMENT', @kaolack_id),

-- Kédougou
('Kédougou', 'DEPARTEMENT', @kedougou_id),
('Salémata', 'DEPARTEMENT', @kedougou_id),
('Saraya', 'DEPARTEMENT', @kedougou_id),

-- Kolda
('Kolda', 'DEPARTEMENT', @kolda_id),
('Médina Yoro Foulah', 'DEPARTEMENT', @kolda_id),
('Vélingara', 'DEPARTEMENT', @kolda_id),

-- Louga
('Kébémer', 'DEPARTEMENT', @louga_id),
('Linguère', 'DEPARTEMENT', @louga_id),
('Louga', 'DEPARTEMENT', @louga_id),

-- Matam
('Kanel', 'DEPARTEMENT', @matam_id),
('Matam', 'DEPARTEMENT', @matam_id),
('Ranérou', 'DEPARTEMENT', @matam_id),

-- Saint-Louis
('Dagana', 'DEPARTEMENT', @saintlouis_id),
('Podor', 'DEPARTEMENT', @saintlouis_id),
('Saint-Louis', 'DEPARTEMENT', @saintlouis_id),

-- Sédhiou
('Bounkiling', 'DEPARTEMENT', @sedhiou_id),
('Goudomp', 'DEPARTEMENT', @sedhiou_id),
('Sédhiou', 'DEPARTEMENT', @sedhiou_id),

-- Tambacounda
('Bakel', 'DEPARTEMENT', @tambacounda_id),
('Goudiry', 'DEPARTEMENT', @tambacounda_id),
('Koumpentoum', 'DEPARTEMENT', @tambacounda_id),
('Tambacounda', 'DEPARTEMENT', @tambacounda_id),

-- Thiès
('Mbour', 'DEPARTEMENT', @thies_id),
('Thiès', 'DEPARTEMENT', @thies_id),
('Tivaouane', 'DEPARTEMENT', @thies_id),

-- Ziguinchor
('Bignona', 'DEPARTEMENT', @ziguinchor_id),
('Oussouye', 'DEPARTEMENT', @ziguinchor_id),
('Ziguinchor', 'DEPARTEMENT', @ziguinchor_id);
