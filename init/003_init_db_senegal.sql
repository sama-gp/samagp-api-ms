CREATE TABLE IF NOT EXISTS zone_geo(
    id SERIAL PRIMARY KEY,
    nom VARCHAR(255),
    type VARCHAR(100),
    parent_id INTEGER,
    CONSTRAINT fk_zone_geo_parent FOREIGN KEY (parent_id)
        REFERENCES zone_geo (id) ON DELETE SET NULL
);

DO $$
DECLARE
    senegal_id INT;
    dakar_id INT;
    diourbel_id INT;
    fatick_id INT;
    kaffrine_id INT;
    kaolack_id INT;
    kedougou_id INT;
    kolda_id INT;
    louga_id INT;
    matam_id INT;
    saintlouis_id INT;
    sedhiou_id INT;
    tambacounda_id INT;
    thies_id INT;
    ziguinchor_id INT;
BEGIN
    -- Insérer le pays Sénégal et récupérer son ID
    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Sénégal', 'PAYS', NULL)
    RETURNING id INTO senegal_id;

    -- Insérer les 14 régions et récupérer leurs IDs
    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Dakar', 'REGION', senegal_id)
    RETURNING id INTO dakar_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Diourbel', 'REGION', senegal_id)
    RETURNING id INTO diourbel_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Fatick', 'REGION', senegal_id)
    RETURNING id INTO fatick_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Kaffrine', 'REGION', senegal_id)
    RETURNING id INTO kaffrine_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Kaolack', 'REGION', senegal_id)
    RETURNING id INTO kaolack_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Kédougou', 'REGION', senegal_id)
    RETURNING id INTO kedougou_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Kolda', 'REGION', senegal_id)
    RETURNING id INTO kolda_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Louga', 'REGION', senegal_id)
    RETURNING id INTO louga_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Matam', 'REGION', senegal_id)
    RETURNING id INTO matam_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Saint-Louis', 'REGION', senegal_id)
    RETURNING id INTO saintlouis_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Sédhiou', 'REGION', senegal_id)
    RETURNING id INTO sedhiou_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Tambacounda', 'REGION', senegal_id)
    RETURNING id INTO tambacounda_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Thiès', 'REGION', senegal_id)
    RETURNING id INTO thies_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Ziguinchor', 'REGION', senegal_id)
    RETURNING id INTO ziguinchor_id;

    -- Insérer les départements de chaque région
    INSERT INTO zone_geo (nom, type, parent_id) VALUES
    -- Dakar
    ('Dakar', 'DEPARTEMENT', dakar_id),
    ('Guédiawaye', 'DEPARTEMENT', dakar_id),
    ('Pikine', 'DEPARTEMENT', dakar_id),
    ('Rufisque', 'DEPARTEMENT', dakar_id),

    -- Diourbel
    ('Bambey', 'DEPARTEMENT', diourbel_id),
    ('Diourbel', 'DEPARTEMENT', diourbel_id),
    ('Mbacké', 'DEPARTEMENT', diourbel_id),

    -- Fatick
    ('Fatick', 'DEPARTEMENT', fatick_id),
    ('Foundiougne', 'DEPARTEMENT', fatick_id),
    ('Gossas', 'DEPARTEMENT', fatick_id),

    -- Kaffrine
    ('Kaffrine', 'DEPARTEMENT', kaffrine_id),
    ('Koungheul', 'DEPARTEMENT', kaffrine_id),
    ('Malem Hodar', 'DEPARTEMENT', kaffrine_id),

    -- Kaolack
    ('Guinguinéo', 'DEPARTEMENT', kaolack_id),
    ('Kaolack', 'DEPARTEMENT', kaolack_id),
    ('Nioro du Rip', 'DEPARTEMENT', kaolack_id),

    -- Kédougou
    ('Kédougou', 'DEPARTEMENT', kedougou_id),
    ('Salémata', 'DEPARTEMENT', kedougou_id),
    ('Saraya', 'DEPARTEMENT', kedougou_id),

    -- Kolda
    ('Kolda', 'DEPARTEMENT', kolda_id),
    ('Médina Yoro Foulah', 'DEPARTEMENT', kolda_id),
    ('Vélingara', 'DEPARTEMENT', kolda_id),

    -- Louga
    ('Kébémer', 'DEPARTEMENT', louga_id),
    ('Linguère', 'DEPARTEMENT', louga_id),
    ('Louga', 'DEPARTEMENT', louga_id),

    -- Matam
    ('Kanel', 'DEPARTEMENT', matam_id),
    ('Matam', 'DEPARTEMENT', matam_id),
    ('Ranérou', 'DEPARTEMENT', matam_id),

    -- Saint-Louis
    ('Dagana', 'DEPARTEMENT', saintlouis_id),
    ('Podor', 'DEPARTEMENT', saintlouis_id),
    ('Saint-Louis', 'DEPARTEMENT', saintlouis_id),

    -- Sédhiou
    ('Bounkiling', 'DEPARTEMENT', sedhiou_id),
    ('Goudomp', 'DEPARTEMENT', sedhiou_id),
    ('Sédhiou', 'DEPARTEMENT', sedhiou_id),

    -- Tambacounda
    ('Bakel', 'DEPARTEMENT', tambacounda_id),
    ('Goudiry', 'DEPARTEMENT', tambacounda_id),
    ('Koumpentoum', 'DEPARTEMENT', tambacounda_id),
    ('Tambacounda', 'DEPARTEMENT', tambacounda_id),

    -- Thiès
    ('Mbour', 'DEPARTEMENT', thies_id),
    ('Thiès', 'DEPARTEMENT', thies_id),
    ('Tivaouane', 'DEPARTEMENT', thies_id),

    -- Ziguinchor
    ('Bignona', 'DEPARTEMENT', ziguinchor_id),
    ('Oussouye', 'DEPARTEMENT', ziguinchor_id),
    ('Ziguinchor', 'DEPARTEMENT', ziguinchor_id);
END $$;
