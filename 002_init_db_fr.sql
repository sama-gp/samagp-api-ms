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
    france_id INT;
    ara_id INT;
    bfc_id INT;
    bretagne_id INT;
    cvdl_id INT;
    corse_id INT;
    grandest_id INT;
    hdf_id INT;
    idf_id INT;
BEGIN
    -- Insérer le pays France et récupérer son ID
    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('France', 'PAYS', NULL)
    RETURNING id INTO france_id;

    -- Insérer les 13 régions de la France et récupérer leurs IDs
    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Auvergne-Rhône-Alpes', 'REGION', france_id)
    RETURNING id INTO ara_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Bourgogne-Franche-Comté', 'REGION', france_id)
    RETURNING id INTO bfc_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Bretagne', 'REGION', france_id)
    RETURNING id INTO bretagne_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Centre-Val de Loire', 'REGION', france_id)
    RETURNING id INTO cvdl_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Corse', 'REGION', france_id)
    RETURNING id INTO corse_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Grand Est', 'REGION', france_id)
    RETURNING id INTO grandest_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Hauts-de-France', 'REGION', france_id)
    RETURNING id INTO hdf_id;

    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('Île-de-France', 'REGION', france_id)
    RETURNING id INTO idf_id;

    -- Insérer les départements de chaque région
    -- Auvergne-Rhône-Alpes
    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES
        ('Ain', 'DEPARTEMENT', ara_id),
        ('Allier', 'DEPARTEMENT', ara_id),
        ('Ardèche', 'DEPARTEMENT', ara_id),
        ('Cantal', 'DEPARTEMENT', ara_id),
        ('Drôme', 'DEPARTEMENT', ara_id),
        ('Isère', 'DEPARTEMENT', ara_id),
        ('Loire', 'DEPARTEMENT', ara_id),
        ('Haute-Loire', 'DEPARTEMENT', ara_id),
        ('Puy-de-Dôme', 'DEPARTEMENT', ara_id),
        ('Rhône', 'DEPARTEMENT', ara_id),
        ('Savoie', 'DEPARTEMENT', ara_id),
        ('Haute-Savoie', 'DEPARTEMENT', ara_id);

    -- Bourgogne-Franche-Comté
    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES
        ('Côte-d\'Or', 'DEPARTEMENT', bfc_id),
        ('Doubs', 'DEPARTEMENT', bfc_id),
        ('Jura', 'DEPARTEMENT', bfc_id),
        ('Nièvre', 'DEPARTEMENT', bfc_id),
        ('Haute-Saône', 'DEPARTEMENT', bfc_id),
        ('Saône-et-Loire', 'DEPARTEMENT', bfc_id),
        ('Yonne', 'DEPARTEMENT', bfc_id),
        ('Territoire de Belfort', 'DEPARTEMENT', bfc_id);

    -- Et ainsi de suite pour les autres régions et départements...

    -- Afficher un message de succès
    RAISE NOTICE 'Insertion terminée avec succès.';
END $$;
