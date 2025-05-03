-- CREATE DATABASE TABLE STRUCTURE


CREATE TABLE IF NOT EXISTS ZONE_GEO(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name_zone VARCHAR(255) NOT NULL,
    type_zone_geo VARCHAR(50) NOT NULL,
    parent_id BIGINT,
    FOREIGN KEY (parent_id) REFERENCES ZONE_GEO(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS ITINERAIRE(
    id BIGINT AUTO_INCREMENT PRIMARY KEY
    zone_geo_depart UUID NOT NULL,
    zone_geo_arrive UUID NOT NULL,
    CONSTRAINT fk_zone_geo_depart FOREIGN KEY (zone_geo_id) REFERENCES ZONE_GEO(id) ON DELETE CASCADE,
    CONSTRAINT fk_zone_geo_arrive FOREIGN KEY (zone_geo_id) REFERENCES ZONE_GEO(id) ON DELETE CASCADE,

);

CREATE TABLE IF NOT EXISTS CLIENT(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    profile VARCHAR(50),  -- Profile, peut être un Enum (ajuster en fonction de l'implémentation)
    phone VARCHAR(20) NOT NULL,
    address TEXT NOT NULL
);
CREATE INDEX idx_client_email ON CLIENT(email);

CREATE INDEX idx_client_phone ON CLIENT(phone);

CREATE INDEX idx_client_address ON CLIENT(address);

CREATE TABLE IF NOT EXISTS ANNONCE(
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      itineraire_id INT NOT NULL,  -- Assuming itineraire is a foreign key referring to another table
      itineraire_details_depart VARCHAR(255) NOT NULL,
      itineraire_details_arrive VARCHAR(255) NOT NULL,
      description TEXT NOT NULL,
      date_depart TIMESTAMP NOT NULL,
      date_arrive TIMESTAMP NOT NULL,
      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      client_id UUID NOT NULL,  -- Assuming the client_id is a UUID, adjust if needed
      CONSTRAINT fk_annonce_client FOREIGN KEY (client_id) REFERENCES CLIENTS(id) ON DELETE CASCADE,
      CONSTRAINT fk_annonce_itineraire FOREIGN KEY (itineraire_id) REFERENCES ITINERAIRE(id) ON DELETE CASCADE

);

-- Optionally, create an index for better performance when querying by client_id
CREATE INDEX idx_client_id ON ANNONCE(client_id);

-- Optionally, create an index for better performance when querying by itineraire_id
CREATE INDEX idx_itineraire_id ON ANNONCE(itineraire_id);

CREATE TABLE IF NOT EXISTS AVIS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comment TEXT,
    annonce_id BIGINT NOT NULL,
    client_id UUID NOT NULL,
    CONSTRAINT fk_avis_annonce FOREIGN KEY (annonce_id) REFERENCES ANNONCE(id) ON DELETE CASCADE,
    CONSTRAINT fk_avis_client FOREIGN KEY (client_id) REFERENCES CLIENT(id) ON DELETE CASCADE
);


CREATE INDEX idx_avis_annonce_id ON AVIS(annonce_id);

CREATE INDEX idx_avis_client_id ON AVIS(client_id);

CREATE TABLE IF NOT EXISTS FACTURATION();

CREATE TABLE IF NOT EXISTS favoris (
    client_id BIGINT NOT NULL,
    itineraire_id BIGINT NOT NULL,
    PRIMARY KEY (client_id, itineraire_id),
    FOREIGN KEY (client_id) REFERENCES CLIENT(id) ON DELETE CASCADE,
    FOREIGN KEY (itineraire_id) REFERENCES ITINERAIRE(id) ON DELETE CASCADE
);