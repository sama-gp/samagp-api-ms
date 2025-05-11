CREATE TABLE type_zone_geo (
    code VARCHAR(255) PRIMARY KEY,
    libelle VARCHAR(255),
    parent_code VARCHAR(255),
    CONSTRAINT fk_type_zone_geo_parent
        FOREIGN KEY (parent_code)
        REFERENCES type_zone_geo(code)
        ON DELETE SET NULL
);

-- Création de la table zone_geo
CREATE TABLE IF NOT EXISTS zone_geo (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    parent_id INTEGER,
    FOREIGN KEY (parent_id) REFERENCES zone_geo(id) ON DELETE CASCADE,
    CONSTRAINT unique_zone UNIQUE (nom, type, COALESCE(parent_id, -1))
);

-- Index pour améliorer les performances des requêtes hiérarchiques
CREATE INDEX IF NOT EXISTS idx_zone_geo_parent ON zone_geo(parent_id);
CREATE INDEX IF NOT EXISTS idx_zone_geo_type ON zone_geo(type);

CREATE TABLE itineraires (
    it_zn_depart BIGINT NOT NULL,
    it_zn_arrivee BIGINT NOT NULL,
    PRIMARY KEY (it_zn_depart, it_zn_arrivee),
    CONSTRAINT fk_depart FOREIGN KEY (it_zn_depart) REFERENCES zone_geo(id),
    CONSTRAINT fk_arrivee FOREIGN KEY (it_zn_arrivee) REFERENCES zone_geo(id)
);


CREATE TABLE clients (
    id UUID PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    profile VARCHAR(50) CHECK (profile IN ('GP', 'CLIENT')),
    phone VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL
);


CREATE TABLE annonces (
    id UUID PRIMARY KEY,
    itineraire_depart_details VARCHAR(255),
    itineraire_arrivee_details VARCHAR(255),
    description TEXT,
    date_depart TIMESTAMP NOT NULL,
    date_arrive TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    client_id UUID NOT NULL,
    itineraire_it_zn_depart BIGINT,
    itineraire_it_zn_arrivee BIGINT,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (itineraire_it_zn_depart, itineraire_it_zn_arrivee) REFERENCES itineraires(it_zn_depart, it_zn_arrivee)
);


CREATE TABLE avis (
    id UUID PRIMARY KEY,
    comment TEXT,
    annonce_id UUID NOT NULL,
    id_client BIGINT NOT NULL,
    CONSTRAINT fk_avis_annonce FOREIGN KEY (annonce_id) REFERENCES annonces(id)
);

