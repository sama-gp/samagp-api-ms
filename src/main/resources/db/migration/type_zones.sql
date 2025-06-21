-- Types de zone géographique
INSERT INTO TYPE_ZONE_GEO (code, libelle, parent_id) VALUES
  ('PAYS', 'Pays', NULL),
  ('VILLE', 'Département', 'PAYS'),
