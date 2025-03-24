-- Insérer le pays Angleterre
INSERT INTO zone_geo (nom, type, parent_id) VALUES ('England', 'PAYS', NULL);

-- Récupérer l'ID du pays Angleterre
SET @england_id = LAST_INSERT_ID();

-- Insérer les 9 régions de l'Angleterre
INSERT INTO zone_geo (nom, type, parent_id) VALUES
('East Midlands', 'REGION', @england_id),
('East of England', 'REGION', @england_id),
('London', 'REGION', @england_id),
('North East', 'REGION', @england_id),
('North West', 'REGION', @england_id),
('South East', 'REGION', @england_id),
('South West', 'REGION', @england_id),
('West Midlands', 'REGION', @england_id),
('Yorkshire and the Humber', 'REGION', @england_id);

-- Récupérer les IDs des régions
SET @em_id = (SELECT id FROM zone_geo WHERE nom='East Midlands');
SET @ee_id = (SELECT id FROM zone_geo WHERE nom='East of England');
SET @london_id = (SELECT id FROM zone_geo WHERE nom='London');
SET @ne_id = (SELECT id FROM zone_geo WHERE nom='North East');
SET @nw_id = (SELECT id FROM zone_geo WHERE nom='North West');
SET @se_id = (SELECT id FROM zone_geo WHERE nom='South East');
SET @sw_id = (SELECT id FROM zone_geo WHERE nom='South West');
SET @wm_id = (SELECT id FROM zone_geo WHERE nom='West Midlands');
SET @yorkshire_id = (SELECT id FROM zone_geo WHERE nom='Yorkshire and the Humber');

-- Insérer tous les comtés de l'Angleterre
INSERT INTO zone_geo (nom, type, parent_id) VALUES
-- East Midlands
('Derbyshire', 'COUNTY', @em_id),
('Leicestershire', 'COUNTY', @em_id),
('Lincolnshire', 'COUNTY', @em_id),
('Northamptonshire', 'COUNTY', @em_id),
('Nottinghamshire', 'COUNTY', @em_id),
('Rutland', 'COUNTY', @em_id),

-- East of England
('Bedfordshire', 'COUNTY', @ee_id),
('Cambridgeshire', 'COUNTY', @ee_id),
('Essex', 'COUNTY', @ee_id),
('Hertfordshire', 'COUNTY', @ee_id),
('Norfolk', 'COUNTY', @ee_id),
('Suffolk', 'COUNTY', @ee_id),

-- London (London n'a pas de comtés mais des boroughs, donc on peut le laisser sans enfants)

-- North East
('County Durham', 'COUNTY', @ne_id),
('Northumberland', 'COUNTY', @ne_id),
('Tyne and Wear', 'COUNTY', @ne_id),

-- North West
('Cheshire', 'COUNTY', @nw_id),
('Cumbria', 'COUNTY', @nw_id),
('Greater Manchester', 'COUNTY', @nw_id),
('Lancashire', 'COUNTY', @nw_id),
('Merseyside', 'COUNTY', @nw_id),

-- South East
('Berkshire', 'COUNTY', @se_id),
('Buckinghamshire', 'COUNTY', @se_id),
('East Sussex', 'COUNTY', @se_id),
('Hampshire', 'COUNTY', @se_id),
('Kent', 'COUNTY', @se_id),
('Oxfordshire', 'COUNTY', @se_id),
('Surrey', 'COUNTY', @se_id),
('West Sussex', 'COUNTY', @se_id),

-- South West
('Bristol', 'COUNTY', @sw_id),
('Cornwall', 'COUNTY', @sw_id),
('Devon', 'COUNTY', @sw_id),
('Dorset', 'COUNTY', @sw_id),
('Gloucestershire', 'COUNTY', @sw_id),
('Somerset', 'COUNTY', @sw_id),
('Wiltshire', 'COUNTY', @sw_id),

-- West Midlands
('Herefordshire', 'COUNTY', @wm_id),
('Shropshire', 'COUNTY', @wm_id),
('Staffordshire', 'COUNTY', @wm_id),
('Warwickshire', 'COUNTY', @wm_id),
('West Midlands', 'COUNTY', @wm_id),
('Worcestershire', 'COUNTY', @wm_id),

-- Yorkshire and the Humber
('East Riding of Yorkshire', 'COUNTY', @yorkshire_id),
('North Yorkshire', 'COUNTY', @yorkshire_id),
('South Yorkshire', 'COUNTY', @yorkshire_id),
('West Yorkshire', 'COUNTY', @yorkshire_id);
