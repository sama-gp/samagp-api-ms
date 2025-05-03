DO $$
DECLARE
    england_id INT;
    em_id INT;
    ee_id INT;
    london_id INT;
    ne_id INT;
    nw_id INT;
    se_id INT;
    sw_id INT;
    wm_id INT;
    yorkshire_id INT;
BEGIN
    -- Insérer le pays Angleterre et récupérer son ID
    INSERT INTO zone_geo (nom, type, parent_id)
    VALUES ('England', 'PAYS', NULL)
    RETURNING id INTO england_id;

    -- Insérer les régions et récupérer leurs IDs
    INSERT INTO zone_geo (nom, type, parent_id) VALUES
        ('East Midlands', 'REGION', england_id) RETURNING id INTO em_id;
    INSERT INTO zone_geo (nom, type, parent_id) VALUES
        ('East of England', 'REGION', england_id) RETURNING id INTO ee_id;
    INSERT INTO zone_geo (nom, type, parent_id) VALUES
        ('London', 'REGION', england_id) RETURNING id INTO london_id;
    INSERT INTO zone_geo (nom, type, parent_id) VALUES
        ('North East', 'REGION', england_id) RETURNING id INTO ne_id;
    INSERT INTO zone_geo (nom, type, parent_id) VALUES
        ('North West', 'REGION', england_id) RETURNING id INTO nw_id;
    INSERT INTO zone_geo (nom, type, parent_id) VALUES
        ('South East', 'REGION', england_id) RETURNING id INTO se_id;
    INSERT INTO zone_geo (nom, type, parent_id) VALUES
        ('South West', 'REGION', england_id) RETURNING id INTO sw_id;
    INSERT INTO zone_geo (nom, type, parent_id) VALUES
        ('West Midlands', 'REGION', england_id) RETURNING id INTO wm_id;
    INSERT INTO zone_geo (nom, type, parent_id) VALUES
        ('Yorkshire and the Humber', 'REGION', england_id) RETURNING id INTO yorkshire_id;

    -- Insérer les comtés avec leurs régions respectives
    INSERT INTO zone_geo (nom, type, parent_id) VALUES
        -- East Midlands
        ('Derbyshire', 'DEPARTEMENT', em_id),
        ('Leicestershire', 'DEPARTEMENT', em_id),
        ('Lincolnshire', 'DEPARTEMENT', em_id),
        ('Northamptonshire', 'DEPARTEMENT', em_id),
        ('Nottinghamshire', 'DEPARTEMENT', em_id),
        ('Rutland', 'DEPARTEMENT', em_id),

        -- East of England
        ('Bedfordshire', 'DEPARTEMENT', ee_id),
        ('Cambridgeshire', 'DEPARTEMENT', ee_id),
        ('Essex', 'DEPARTEMENT', ee_id),
        ('Hertfordshire', 'DEPARTEMENT', ee_id),
        ('Norfolk', 'DEPARTEMENT', ee_id),
        ('Suffolk', 'DEPARTEMENT', ee_id),

        -- North East
        ('DEPARTEMENT Durham', 'DEPARTEMENT', ne_id),
        ('Northumberland', 'DEPARTEMENT', ne_id),
        ('Tyne and Wear', 'DEPARTEMENT', ne_id),

        -- North West
        ('Cheshire', 'DEPARTEMENT', nw_id),
        ('Cumbria', 'DEPARTEMENT', nw_id),
        ('Greater Manchester', 'DEPARTEMENT', nw_id),
        ('Lancashire', 'DEPARTEMENT', nw_id),
        ('Merseyside', 'DEPARTEMENT', nw_id),

        -- South East
        ('Berkshire', 'DEPARTEMENT', se_id),
        ('Buckinghamshire', 'DEPARTEMENT', se_id),
        ('East Sussex', 'DEPARTEMENT', se_id),
        ('Hampshire', 'DEPARTEMENT', se_id),
        ('Kent', 'DEPARTEMENT', se_id),
        ('Oxfordshire', 'DEPARTEMENT', se_id),
        ('Surrey', 'DEPARTEMENT', se_id),
        ('West Sussex', 'DEPARTEMENT', se_id),

        -- South West
        ('Bristol', 'DEPARTEMENT', sw_id),
        ('Cornwall', 'DEPARTEMENT', sw_id),
        ('Devon', 'DEPARTEMENT', sw_id),
        ('Dorset', 'DEPARTEMENT', sw_id),
        ('Gloucestershire', 'DEPARTEMENT', sw_id),
        ('Somerset', 'DEPARTEMENT', sw_id),
        ('Wiltshire', 'DEPARTEMENT', sw_id),

        -- West Midlands
        ('Herefordshire', 'DEPARTEMENT', wm_id),
        ('Shropshire', 'DEPARTEMENT', wm_id),
        ('Staffordshire', 'DEPARTEMENT', wm_id),
        ('Warwickshire', 'DEPARTEMENT', wm_id),
        ('West Midlands', 'DEPARTEMENT', wm_id),
        ('Worcestershire', 'DEPARTEMENT', wm_id),

        -- Yorkshire and the Humber
        ('East Riding of Yorkshire', 'DEPARTEMENT', yorkshire_id),
        ('North Yorkshire', 'DEPARTEMENT', yorkshire_id),
        ('South Yorkshire', 'DEPARTEMENT', yorkshire_id),
        ('West Yorkshire', 'DEPARTEMENT', yorkshire_id);
    RAISE NOTICE 'Insertion terminée avec succès.';
END $$;

-- ENREGISTRE LES DONNEES DU SENEGAL