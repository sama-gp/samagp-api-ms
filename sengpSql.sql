-- Adminer 4.8.1 PostgreSQL 16.8 (Debian 16.8-1.pgdg120+1) dump

DROP TABLE IF EXISTS "abonnements_client";
CREATE TABLE "public"."abonnements_client" (
    "id" uuid NOT NULL,
    "annonces_utilisees" integer,
    "created_at" timestamp(6),
    "date_debut" timestamp(6) NOT NULL,
    "date_fin" timestamp(6) NOT NULL,
    "renouvellement_auto" boolean,
    "statut" character varying(255) NOT NULL,
    "client_id" uuid NOT NULL,
    "plan_id" uuid NOT NULL,
    CONSTRAINT "abonnements_client_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "abonnements_client" ("id", "annonces_utilisees", "created_at", "date_debut", "date_fin", "renouvellement_auto", "statut", "client_id", "plan_id") VALUES
('0f2189de-6637-44a9-b4e8-750785316aad',	0,	'2025-11-02 13:28:28.633631',	'2025-11-02 13:28:28.61609',	'2025-12-02 13:28:28.61676',	't',	'EN_ATTENTE_PAIEMENT',	'd7ccedd3-592a-4159-9bcd-e6ea09307ae2',	'6be6331a-0cf4-4e4b-a20e-665cbefb09be'),
('2f620c02-db6a-4f05-a876-342f2e27e373',	0,	'2025-11-02 13:42:35.143961',	'2025-11-02 13:42:35.126217',	'2025-12-02 13:42:35.126973',	't',	'EN_ATTENTE_PAIEMENT',	'd7ccedd3-592a-4159-9bcd-e6ea09307ae2',	'6be6331a-0cf4-4e4b-a20e-665cbefb09be'),
('09480f20-6c4f-4402-8923-6456e0af8231',	0,	'2025-11-02 13:43:55.07913',	'2025-11-02 13:43:55.077964',	'2025-12-02 13:43:55.077979',	't',	'EN_ATTENTE_PAIEMENT',	'd7ccedd3-592a-4159-9bcd-e6ea09307ae2',	'6be6331a-0cf4-4e4b-a20e-665cbefb09be'),
('49ee9263-4ebf-4bbd-9507-635a454ff4f6',	0,	'2025-11-02 13:46:32.197653',	'2025-11-02 13:46:32.16561',	'2025-12-02 13:46:32.166203',	't',	'EN_ATTENTE_PAIEMENT',	'd7ccedd3-592a-4159-9bcd-e6ea09307ae2',	'6be6331a-0cf4-4e4b-a20e-665cbefb09be'),
('aad3aec1-7db7-4a8d-9011-cc20231376dc',	0,	'2025-11-02 20:28:43.626789',	'2025-11-02 20:28:43.609221',	'2025-12-02 20:28:43.610127',	't',	'EN_ATTENTE_PAIEMENT',	'2ddbb31e-c2a2-4e98-b2c7-b2961c099277',	'6be6331a-0cf4-4e4b-a20e-665cbefb09be'),
('79501906-aa9c-4f21-9fec-cd9bb6c25587',	0,	'2025-11-02 20:50:38.962647',	'2025-11-02 20:50:38.888096',	'2025-12-02 20:50:38.888123',	't',	'EN_ATTENTE_PAIEMENT',	'618eba90-6d17-48e3-a3cf-93a6037e2d85',	'7af454e7-d04a-4e69-8cde-ed9541b3b3eb'),
('b5f80f81-3d65-4238-8d6e-6df03ab4bbaa',	0,	'2025-11-02 20:52:01.924464',	'2025-11-02 20:52:01.909883',	'2025-12-02 20:52:01.910439',	't',	'EN_ATTENTE_PAIEMENT',	'618eba90-6d17-48e3-a3cf-93a6037e2d85',	'7af454e7-d04a-4e69-8cde-ed9541b3b3eb'),
('fac62e59-9f7f-4949-8483-b311f02ffde6',	0,	'2025-11-02 20:56:34.215496',	'2025-11-02 20:56:34.192055',	'2025-12-02 20:56:34.192849',	't',	'ACTIF',	'618eba90-6d17-48e3-a3cf-93a6037e2d85',	'6be6331a-0cf4-4e4b-a20e-665cbefb09be'),
('d897b42e-6121-475d-892f-9134ce6c879b',	0,	'2025-11-02 21:01:37.454084',	'2025-11-02 21:01:36.201978',	'2025-12-02 21:01:36.204424',	't',	'ACTIF',	'435cadcf-1a2d-41bd-a691-0d489155c5e6',	'6be6331a-0cf4-4e4b-a20e-665cbefb09be'),
('8b8dd6fd-0e44-4f50-b5f5-ad1fb349c0c3',	0,	'2025-11-03 22:17:09.834888',	'2025-11-03 22:17:09.819308',	'2025-12-03 22:17:09.819987',	't',	'EN_ATTENTE_PAIEMENT',	'd7ccedd3-592a-4159-9bcd-e6ea09307ae2',	'6be6331a-0cf4-4e4b-a20e-665cbefb09be'),
('0e81adf2-0f2e-483c-8639-2752fee8172c',	0,	'2025-11-03 22:25:37.655201',	'2025-11-03 22:25:37.628153',	'2025-12-03 22:25:37.628166',	't',	'EN_ATTENTE_PAIEMENT',	'd7ccedd3-592a-4159-9bcd-e6ea09307ae2',	'6be6331a-0cf4-4e4b-a20e-665cbefb09be'),
('417530d7-0bd9-4e56-b95e-b0f850732aa1',	0,	'2025-11-03 22:27:42.332644',	'2025-11-03 22:27:42.331805',	'2025-12-03 22:27:42.331812',	't',	'EN_ATTENTE_PAIEMENT',	'd7ccedd3-592a-4159-9bcd-e6ea09307ae2',	'6be6331a-0cf4-4e4b-a20e-665cbefb09be');

DROP TABLE IF EXISTS "annonces";
CREATE TABLE "public"."annonces" (
    "created_at" timestamp(6) NOT NULL,
    "date_arrive" timestamp(6) NOT NULL,
    "date_depart" timestamp(6) NOT NULL,
    "it_zn_arrivee" bigint,
    "it_zn_depart" bigint,
    "updated_at" timestamp(6) NOT NULL,
    "client_id" uuid NOT NULL,
    "id" uuid NOT NULL,
    "tarification_id" uuid,
    "description" character varying(255) NOT NULL,
    "itineraire_arrivee_details" character varying(255),
    "itineraire_depart_details" character varying(255),
    CONSTRAINT "annonces_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "annonces_tarification_id_key" UNIQUE ("tarification_id")
) WITH (oids = false);

INSERT INTO "annonces" ("created_at", "date_arrive", "date_depart", "it_zn_arrivee", "it_zn_depart", "updated_at", "client_id", "id", "tarification_id", "description", "itineraire_arrivee_details", "itineraire_depart_details") VALUES
('2025-06-26 01:27:11.1754',	'2025-06-29 02:13:00',	'2025-06-28 22:00:00',	13,	4,	'2025-06-27 01:02:58.969282',	'ae52473a-26d9-4dcf-ae30-4a68169bf68a',	'29fe67cb-62ff-4dd0-a9fe-46d89853ac17',	'e330881e-2e6e-4eba-9960-8d08f3e2c78d',	'il me reste 10 kg',	'Aéroport Mohammed V',	'Aéroport international Blaise Diagne'),
('2025-06-27 15:25:24.421269',	'2025-07-03 05:00:00',	'2025-07-02 21:00:00',	11,	4,	'2025-06-27 15:25:24.42129',	'ae52473a-26d9-4dcf-ae30-4a68169bf68a',	'51a1212a-2fd9-4edc-b818-e846d20a8b94',	'a28635a6-3259-45e9-bb5e-1ad5c375c88f',	'testtt',	'Aéroport Nantes Atlantique',	'Aéroport international Blaise Diagne'),
('2025-06-26 21:48:25.215379',	'2025-07-01 05:00:00',	'2025-06-30 22:00:00',	8,	4,	'2025-06-27 16:10:13.954391',	'ae52473a-26d9-4dcf-ae30-4a68169bf68a',	'1445e515-9be3-49da-9985-9cc789669e3a',	'5b9ced7e-e206-44d8-b87d-8d7d7cea7678',	'il me reste 5 kg',	'Aéroport Charles de Gaulle',	'Aéroport international Blaise Diagne'),
('2025-11-02 23:54:05.386291',	'2025-11-13 08:30:00',	'2025-11-12 01:30:00',	11,	4,	'2025-11-02 23:54:05.386299',	'435cadcf-1a2d-41bd-a691-0d489155c5e6',	'2f19999c-ab6e-44ba-9cbb-4ce97aacf63d',	'30dcd65e-8034-4964-a336-c2c416d57bd6',	'je vous appelle le lendemain',	'Aéroport Nantes Atlantique',	'Aéroport international Blaise Diagne'),
('2026-01-11 14:23:27.2475',	'2026-01-15 02:00:00',	'2026-01-14 21:00:00',	4,	11,	'2026-01-11 14:23:27.247537',	'd7ccedd3-592a-4159-9bcd-e6ea09307ae2',	'15c79ea3-4dfa-496b-9c0a-f159e14405e3',	'f3912ad6-3fae-4783-bd0b-6b65f79c63e3',	'dernier delai le 13',	'Aéroport international Blaise Diagne',	'Aéroport Nantes Atlantique');

DROP TABLE IF EXISTS "avis";
CREATE TABLE "public"."avis" (
    "id_client" bigint NOT NULL,
    "annonce_id" uuid NOT NULL,
    "id" uuid NOT NULL,
    "comment" character varying(255),
    CONSTRAINT "avis_pkey" PRIMARY KEY ("id")
) WITH (oids = false);


DROP TABLE IF EXISTS "client_addresses";
CREATE TABLE "public"."client_addresses" (
    "is_principal" boolean,
    "type" smallint,
    "client_id" uuid NOT NULL,
    "adresse1" character varying(255),
    "adresse2" character varying(255),
    "code_postal" character varying(255),
    "pays" character varying(255),
    "ville" character varying(255)
) WITH (oids = false);

INSERT INTO "client_addresses" ("is_principal", "type", "client_id", "adresse1", "adresse2", "code_postal", "pays", "ville") VALUES
('f',	0,	'ae52473a-26d9-4dcf-ae30-4a68169bf68a',	'15 bd jean Moukin',	NULL,	'44100',	'france',	'nantes');

DROP TABLE IF EXISTS "client_following";
CREATE TABLE "public"."client_following" (
    "followed_id" uuid NOT NULL,
    "follower_id" uuid NOT NULL,
    CONSTRAINT "client_following_pkey" PRIMARY KEY ("followed_id", "follower_id")
) WITH (oids = false);

INSERT INTO "client_following" ("followed_id", "follower_id") VALUES
('2ddbb31e-c2a2-4e98-b2c7-b2961c099277',	'd7ccedd3-592a-4159-9bcd-e6ea09307ae2'),
('435cadcf-1a2d-41bd-a691-0d489155c5e6',	'd7ccedd3-592a-4159-9bcd-e6ea09307ae2'),
('ae52473a-26d9-4dcf-ae30-4a68169bf68a',	'd7ccedd3-592a-4159-9bcd-e6ea09307ae2');

DROP TABLE IF EXISTS "client_phones";
CREATE TABLE "public"."client_phones" (
    "client_id" uuid NOT NULL,
    "phone_number" character varying(255)
) WITH (oids = false);

INSERT INTO "client_phones" ("client_id", "phone_number") VALUES
('ae52473a-26d9-4dcf-ae30-4a68169bf68a',	'771034559');

DROP TABLE IF EXISTS "clients";
CREATE TABLE "public"."clients" (
    "created_at" timestamp(6),
    "id" uuid NOT NULL,
    "email" character varying(255) NOT NULL,
    "first_name" character varying(255),
    "keycloak_id" character varying(255) NOT NULL,
    "last_name" character varying(255),
    "password" character varying(255),
    "profile" character varying(255),
    "profile_picture_url" character varying(255),
    "ninea" character varying(255),
    "pieces_recto" character varying(255),
    "pieces_verso" character varying(255),
    "identity_document_type" character varying(255),
    "is_valid" boolean,
    CONSTRAINT "UKixdoi563r6w5geuq05ux412nc" UNIQUE ("ninea"),
    CONSTRAINT "clients_keycloak_id_key" UNIQUE ("keycloak_id"),
    CONSTRAINT "clients_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "clients" ("created_at", "id", "email", "first_name", "keycloak_id", "last_name", "password", "profile", "profile_picture_url", "ninea", "pieces_recto", "pieces_verso", "identity_document_type", "is_valid") VALUES
('2025-06-26 01:27:11.299342',	'ae52473a-26d9-4dcf-ae30-4a68169bf68a',	'samagp2023@gmail.com',	'Sama',	'63751230-a264-4a91-85c4-8bae676388b3',	'GP',	NULL,	'USER',	NULL,	NULL,	NULL,	NULL,	NULL,	'f'),
('2025-10-18 14:09:43.805149',	'2ddbb31e-c2a2-4e98-b2c7-b2961c099277',	'ousmane@gmail.com',	'ousmane',	'a6ddb3ae-79ce-46ea-b11e-28fb13f30ac4',	'dione',	NULL,	'CLIENT',	NULL,	NULL,	NULL,	NULL,	NULL,	'f'),
('2025-10-18 18:23:39.997229',	'618eba90-6d17-48e3-a3cf-93a6037e2d85',	'vincent.dione@gmail.com',	'vincent',	'4a907b92-3ab2-43b1-82fc-b5bf77c172e5',	'dione',	NULL,	'CLIENT',	NULL,	NULL,	NULL,	NULL,	NULL,	'f'),
('2025-10-18 18:53:22.46643',	'0c9968e2-cc7a-4e3f-8ca6-8c66f5e96a15',	'test@gmail.com',	'test',	'006d4291-83a1-46d2-bf9c-0827102b8683',	'test',	NULL,	'CLIENT',	NULL,	NULL,	NULL,	NULL,	NULL,	'f'),
('2025-10-25 18:16:07.305712',	'435cadcf-1a2d-41bd-a691-0d489155c5e6',	'noura@gmail.com',	'noura',	'5e232ebe-9e0f-4d5b-ab2b-886fac4f633c',	'dione',	NULL,	'CLIENT',	'profile-pictures/user_5e232ebe-9e0f-4d5b-ab2b-886fac4f633c_d16c5f19-473e-4c63-8e5f-92f14adaf229.jpeg',	'34567890654',	NULL,	NULL,	'CARTE_NATIONALE',	't'),
('2025-10-18 13:44:19.866611',	'd7ccedd3-592a-4159-9bcd-e6ea09307ae2',	'ovd@gmail.com',	'Ousmane',	'30ca2352-e070-4e88-8b38-56585d717ea7',	'Dione',	NULL,	'GP',	NULL,	'32455676577',	'documents/pieces/client_30ca2352-e070-4e88-8b38-56585d717ea7_5efb9708-d994-4dc2-8cd0-1edc2cf26757.png',	'documents/pieces/client_30ca2352-e070-4e88-8b38-56585d717ea7_69ec3189-b0c4-4bd7-b36d-5027d2843f82.png',	'CARTE_NATIONALE',	'f');

DROP TABLE IF EXISTS "commentaires";
CREATE TABLE "public"."commentaires" (
    "date_creation" timestamp(6) NOT NULL,
    "annonce_id" uuid NOT NULL,
    "client_id" uuid NOT NULL,
    "id" uuid NOT NULL,
    "contenu" text NOT NULL,
    CONSTRAINT "commentaires_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "commentaires" ("date_creation", "annonce_id", "client_id", "id", "contenu") VALUES
('2025-06-29 00:46:55.414053',	'29fe67cb-62ff-4dd0-a9fe-46d89853ac17',	'ae52473a-26d9-4dcf-ae30-4a68169bf68a',	'ea91efc9-45bf-4541-8db6-36ce6e62a0e5',	'testtttt');

DROP TABLE IF EXISTS "frais_supplementaires";
CREATE TABLE "public"."frais_supplementaires" (
    "prix" double precision NOT NULL,
    "id" uuid NOT NULL,
    "tarification_id" uuid,
    "type" character varying(255) NOT NULL,
    CONSTRAINT "frais_supplementaires_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "frais_supplementaires" ("prix", "id", "tarification_id", "type") VALUES
(10,	'3ebaa0e5-dedb-4397-a257-da6ee7defb63',	'74c5ef8d-45ac-45d0-a4e7-95c35a64658e',	'passeport'),
(10,	'ed93ddfc-e72a-41a4-8689-7c8c0f61886a',	'835b7313-f071-4c4e-a09a-135cad6399d8',	'passeport'),
(10,	'28558f23-1af8-41bf-b999-b2a706ddc267',	NULL,	'passeport'),
(10,	'43008ce2-9f70-415e-ba6a-8d0c0de13b29',	NULL,	'passeport'),
(10,	'67eecdfb-7b01-4927-bc2f-0f985329e164',	'a28635a6-3259-45e9-bb5e-1ad5c375c88f',	'permis'),
(5,	'c0f7c8b1-7cf5-4e84-99cb-6524974b7bf5',	NULL,	'permis'),
(5,	'50ca5c54-05f3-4f70-992e-6cdcd19b7a82',	NULL,	'permis'),
(5,	'd2fb6016-6066-4da1-a0b7-0bc630572ace',	NULL,	'permis'),
(5,	'e7fa77aa-a6e4-4c13-b505-3f9c430f1ec0',	'5b9ced7e-e206-44d8-b87d-8d7d7cea7678',	'permis'),
(8,	'dba4bcdd-0644-4160-b1a6-87a2a5710912',	'5b9ced7e-e206-44d8-b87d-8d7d7cea7678',	'passeport'),
(8,	'05705f87-c8a0-4bcb-bdc6-4ca9f771a84f',	'30dcd65e-8034-4964-a336-c2c416d57bd6',	'passeport'),
(8,	'd8762cf7-8b9e-4b01-927b-4dbeb97d315a',	'30dcd65e-8034-4964-a336-c2c416d57bd6',	'permis'),
(8,	'c3ebaf2e-36c2-4918-9ee8-db1e3e4afcd3',	'f3912ad6-3fae-4783-bd0b-6b65f79c63e3',	'Passeport');

DROP TABLE IF EXISTS "historique_abonnements";
CREATE TABLE "public"."historique_abonnements" (
    "id" uuid NOT NULL,
    "created_at" timestamp(6),
    "date_debut" timestamp(6),
    "date_fin" timestamp(6),
    "methode_paiement" character varying(255),
    "montant_paye" numeric(10,2),
    "raison_changement" character varying(255),
    "statut" character varying(255),
    "client_id" uuid NOT NULL,
    "plan_id" uuid NOT NULL,
    CONSTRAINT "historique_abonnements_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "historique_abonnements" ("id", "created_at", "date_debut", "date_fin", "methode_paiement", "montant_paye", "raison_changement", "statut", "client_id", "plan_id") VALUES
('9dd06a5f-7baf-462c-8bd8-18d397bc4659',	'2025-11-02 20:56:36.669611',	'2025-11-02 20:56:34.192055',	'2025-12-02 20:56:34.192849',	'CARTE_BANCAIRE',	5000.00,	'NOUVEAU_ABONNEMENT_STRIPE',	'ACTIF',	'618eba90-6d17-48e3-a3cf-93a6037e2d85',	'6be6331a-0cf4-4e4b-a20e-665cbefb09be'),
('618ab67d-cf54-4f6d-ac60-7672f7c12ffe',	'2025-11-02 21:01:40.04881',	'2025-11-02 21:01:36.201978',	'2025-12-02 21:01:36.204424',	'CARTE_BANCAIRE',	5000.00,	'NOUVEAU_ABONNEMENT_STRIPE',	'ACTIF',	'435cadcf-1a2d-41bd-a691-0d489155c5e6',	'6be6331a-0cf4-4e4b-a20e-665cbefb09be');

DROP TABLE IF EXISTS "itineraires";
CREATE TABLE "public"."itineraires" (
    "it_zn_arrivee" bigint NOT NULL,
    "it_zn_depart" bigint NOT NULL,
    CONSTRAINT "itineraires_pkey" PRIMARY KEY ("it_zn_arrivee", "it_zn_depart")
) WITH (oids = false);

INSERT INTO "itineraires" ("it_zn_arrivee", "it_zn_depart") VALUES
(13,	4),
(8,	4),
(13,	11),
(11,	4),
(4,	11);

DROP TABLE IF EXISTS "paiements";
CREATE TABLE "public"."paiements" (
    "id" uuid NOT NULL,
    "created_at" timestamp(6),
    "date_confirmation" timestamp(6),
    "date_paiement" timestamp(6) NOT NULL,
    "details_transaction" text,
    "devise" character varying(255) NOT NULL,
    "id_transaction_fournisseur" character varying(255),
    "methode" character varying(255) NOT NULL,
    "montant" numeric(10,2) NOT NULL,
    "reference" character varying(255) NOT NULL,
    "statut" character varying(255) NOT NULL,
    "abonnement_client_id" uuid,
    CONSTRAINT "UK2i3036yyqgefhxtbs8vkqd9px" UNIQUE ("reference"),
    CONSTRAINT "paiements_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "paiements" ("id", "created_at", "date_confirmation", "date_paiement", "details_transaction", "devise", "id_transaction_fournisseur", "methode", "montant", "reference", "statut", "abonnement_client_id") VALUES
('2297ea09-5903-4c3e-b4c0-24e7053e89c0',	'2025-11-02 20:56:36.665538',	'2025-11-02 20:56:36.646526',	'2025-11-02 20:56:36.646517',	'Paiement Stripe réussi - {
  "amount": 500000,
  "amount_capturable": 0,
  "amount_details": {
    "tip": {
      "amount": null
    }
  },
  "amount_received": 500000,
  "application": null,
  "application_fee_amount": null,
  "automatic_payment_methods": {
    "allow_redirects": "always",
    "enabled": true
  },
  "canceled_at": null,
  "cancellation_reason": null,
  "capture_method": "automatic",
  "client_secret": "pi_3SP7OBGLF9nttjSE1Nx7tkNp_secret_uYs7PToGwS70Q8wvRntL83SI8",
  "confirmation_method": "automatic",
  "created": 1762113395,
  "currency": "xof",
  "customer": null,
  "description": null,
  "id": "pi_3SP7OBGLF9nttjSE1Nx7tkNp",
  "invoice": null,
  "last_payment_error": null,
  "latest_charge": "ch_3SP7OBGLF9nttjSE1AeOe7B0",
  "livemode": false,
  "metadata": {
    "abonnement_client_id": "fac62e59-9f7f-4949-8483-b311f02ffde6",
    "client_id": "618eba90-6d17-48e3-a3cf-93a6037e2d85",
    "plan_code": "BASIC_MENSUEL"
  },
  "next_action": null,
  "object": "payment_intent",
  "on_behalf_of": null,
  "payment_method": "pm_1SP7OBGLF9nttjSE3fk4wjyG",
  "payment_method_configuration_details": {
    "id": "pmc_1RdnsWGLF9nttjSEQy4AYpfB",
    "parent": null
  },
  "payment_method_options": {
    "acss_debit": null,
    "affirm": null,
    "afterpay_clearpay": null,
    "alipay": null,
    "au_becs_debit": null,
    "bacs_debit": null,
    "bancontact": null,
    "blik": null,
    "boleto": null,
    "card": {
      "capture_method": null,
      "installments": null,
      "mandate_options": null,
      "network": null,
      "request_extended_authorization": null,
      "request_incremental_authorization": null,
      "request_multicapture": null,
      "request_overcapture": null,
      "request_three_d_secure": "automatic",
      "setup_future_usage": null,
      "statement_descriptor_suffix_kana": null,
      "statement_descriptor_suffix_kanji": null
    },
    "card_present": null,
    "cashapp": null,
    "customer_balance": null,
    "eps": null,
    "fpx": null,
    "giropay": null,
    "grabpay": null,
    "ideal": null,
    "interac_present": null,
    "klarna": null,
    "konbini": null,
    "link": {
      "capture_method": null,
      "persistent_token": null,
      "setup_future_usage": null
    },
    "oxxo": null,
    "p24": null,
    "paynow": null,
    "paypal": null,
    "pix": null,
    "promptpay": null,
    "revolut_pay": null,
    "sepa_debit": null,
    "sofort": null,
    "us_bank_account": null,
    "wechat_pay": null,
    "zip": null
  },
  "payment_method_types": [
    "card",
    "link"
  ],
  "processing": null,
  "receipt_email": null,
  "review": null,
  "setup_future_usage": null,
  "shipping": null,
  "source": null,
  "statement_descriptor": null,
  "statement_descriptor_suffix": null,
  "status": "succeeded",
  "transfer_data": null,
  "transfer_group": null
}',	'XOF',	'pi_3SP7OBGLF9nttjSE1Nx7tkNp',	'CARTE_BANCAIRE',	5000.00,	'pi_3SP7OBGLF9nttjSE1Nx7tkNp',	'PAYE',	'fac62e59-9f7f-4949-8483-b311f02ffde6'),
('e3075256-a2f6-4ea9-97db-bcc3f04c0853',	'2025-11-02 21:01:40.045493',	'2025-11-02 21:01:40.037949',	'2025-11-02 21:01:40.037941',	'Paiement Stripe réussi - {
  "amount": 500000,
  "amount_capturable": 0,
  "amount_details": {
    "tip": {
      "amount": null
    }
  },
  "amount_received": 500000,
  "application": null,
  "application_fee_amount": null,
  "automatic_payment_methods": {
    "allow_redirects": "always",
    "enabled": true
  },
  "canceled_at": null,
  "cancellation_reason": null,
  "capture_method": "automatic",
  "client_secret": "pi_3SP7T4GLF9nttjSE0wnh2F0Y_secret_NYyLw6jznOHtmgC0GQNrVZ8Zr",
  "confirmation_method": "automatic",
  "created": 1762113698,
  "currency": "xof",
  "customer": null,
  "description": null,
  "id": "pi_3SP7T4GLF9nttjSE0wnh2F0Y",
  "invoice": null,
  "last_payment_error": null,
  "latest_charge": "ch_3SP7T4GLF9nttjSE0rzKWxTi",
  "livemode": false,
  "metadata": {
    "abonnement_client_id": "d897b42e-6121-475d-892f-9134ce6c879b",
    "client_id": "435cadcf-1a2d-41bd-a691-0d489155c5e6",
    "plan_code": "BASIC_MENSUEL"
  },
  "next_action": null,
  "object": "payment_intent",
  "on_behalf_of": null,
  "payment_method": "pm_1SP7T4GLF9nttjSEN3L7Akd0",
  "payment_method_configuration_details": {
    "id": "pmc_1RdnsWGLF9nttjSEQy4AYpfB",
    "parent": null
  },
  "payment_method_options": {
    "acss_debit": null,
    "affirm": null,
    "afterpay_clearpay": null,
    "alipay": null,
    "au_becs_debit": null,
    "bacs_debit": null,
    "bancontact": null,
    "blik": null,
    "boleto": null,
    "card": {
      "capture_method": null,
      "installments": null,
      "mandate_options": null,
      "network": null,
      "request_extended_authorization": null,
      "request_incremental_authorization": null,
      "request_multicapture": null,
      "request_overcapture": null,
      "request_three_d_secure": "automatic",
      "setup_future_usage": null,
      "statement_descriptor_suffix_kana": null,
      "statement_descriptor_suffix_kanji": null
    },
    "card_present": null,
    "cashapp": null,
    "customer_balance": null,
    "eps": null,
    "fpx": null,
    "giropay": null,
    "grabpay": null,
    "ideal": null,
    "interac_present": null,
    "klarna": null,
    "konbini": null,
    "link": {
      "capture_method": null,
      "persistent_token": null,
      "setup_future_usage": null
    },
    "oxxo": null,
    "p24": null,
    "paynow": null,
    "paypal": null,
    "pix": null,
    "promptpay": null,
    "revolut_pay": null,
    "sepa_debit": null,
    "sofort": null,
    "us_bank_account": null,
    "wechat_pay": null,
    "zip": null
  },
  "payment_method_types": [
    "card",
    "link"
  ],
  "processing": null,
  "receipt_email": null,
  "review": null,
  "setup_future_usage": null,
  "shipping": null,
  "source": null,
  "statement_descriptor": null,
  "statement_descriptor_suffix": null,
  "status": "succeeded",
  "transfer_data": null,
  "transfer_group": null
}',	'XOF',	'pi_3SP7T4GLF9nttjSE0wnh2F0Y',	'CARTE_BANCAIRE',	5000.00,	'pi_3SP7T4GLF9nttjSE0wnh2F0Y',	'PAYE',	'd897b42e-6121-475d-892f-9134ce6c879b');

DROP TABLE IF EXISTS "plans_abonnement";
CREATE TABLE "public"."plans_abonnement" (
    "id" uuid NOT NULL,
    "actif" boolean,
    "code" character varying(255) NOT NULL,
    "created_at" timestamp(6),
    "description" character varying(255),
    "devise" character varying(255),
    "duree" character varying(255) NOT NULL,
    "nom" character varying(255) NOT NULL,
    "nombre_annonces_inclus" integer,
    "prix" numeric(10,2) NOT NULL,
    "updated_at" timestamp(6),
    CONSTRAINT "UK7md1mc34bocrc830skrluo0y2" UNIQUE ("code"),
    CONSTRAINT "plans_abonnement_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "plans_abonnement" ("id", "actif", "code", "created_at", "description", "devise", "duree", "nom", "nombre_annonces_inclus", "prix", "updated_at") VALUES
('6be6331a-0cf4-4e4b-a20e-665cbefb09be',	't',	'BASIC_MENSUEL',	'2025-11-01 23:36:59.051334',	'Plan basique pour les petits besoins',	'XOF',	'MENSUEL',	'Basic Mensuel',	5,	5000.00,	'2025-11-01 23:36:59.051334'),
('7af454e7-d04a-4e69-8cde-ed9541b3b3eb',	't',	'PREMIUM_MENSUEL',	'2025-11-01 23:36:59.051334',	'Plan premium avec plus d''annonces',	'XOF',	'MENSUEL',	'Premium Mensuel',	20,	10000.00,	'2025-11-01 23:36:59.051334'),
('e50cad67-98a6-49f5-a633-6b97a71ee5eb',	't',	'PRO_SEMESTRIEL',	'2025-11-01 23:36:59.051334',	'Plan professionnel pour 6 mois',	'XOF',	'SEMESTRIEL',	'Pro Semestriel',	100,	50000.00,	'2025-11-01 23:36:59.051334'),
('a5433ee9-43a0-40d3-b45f-81b9ca1d1953',	't',	'BUSINESS_ANNUEL',	'2025-11-01 23:36:59.051334',	'Plan business illimité pour 1 an',	'XOF',	'ANNUEL',	'Business Annuel',	NULL,	150000.00,	'2025-11-01 23:36:59.051334');

DROP TABLE IF EXISTS "tarifications";
CREATE TABLE "public"."tarifications" (
    "prix_par_kg" double precision NOT NULL,
    "id" uuid NOT NULL,
    "devise" character varying(255),
    CONSTRAINT "tarifications_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "tarifications" ("prix_par_kg", "id", "devise") VALUES
(13,	'74c5ef8d-45ac-45d0-a4e7-95c35a64658e',	'EUR'),
(12,	'835b7313-f071-4c4e-a09a-135cad6399d8',	'EUR'),
(11,	'db18ed18-c0c8-40f9-b245-d675462267c4',	'EUR'),
(13,	'7aa1f443-7242-4bfc-ae52-81e3899ce197',	'EUR'),
(12,	'37379086-1808-461d-bde0-8d0176c8b856',	'EUR'),
(13,	'e330881e-2e6e-4eba-9960-8d08f3e2c78d',	'EUR'),
(13,	'a28635a6-3259-45e9-bb5e-1ad5c375c88f',	'EUR'),
(15,	'2e149f2f-3ef4-456c-b519-557161c198fb',	'EUR'),
(14,	'6770183d-0979-438f-b873-ae8da4eacf07',	'EUR'),
(14,	'd6172bac-f9f1-4976-894d-67019cf6c82e',	'EUR'),
(14,	'b803739f-6f01-4832-933b-225844214cec',	'EUR'),
(14,	'5b9ced7e-e206-44d8-b87d-8d7d7cea7678',	'EUR'),
(15,	'30dcd65e-8034-4964-a336-c2c416d57bd6',	'EUR'),
(13,	'f3912ad6-3fae-4783-bd0b-6b65f79c63e3',	'EUR');

DROP TABLE IF EXISTS "type_zone_geo";
CREATE TABLE "public"."type_zone_geo" (
    "code" character varying(255) NOT NULL,
    "libelle" character varying(255),
    "parent_code" character varying(255),
    CONSTRAINT "type_zone_geo_pkey" PRIMARY KEY ("code")
) WITH (oids = false);

INSERT INTO "type_zone_geo" ("code", "libelle", "parent_code") VALUES
('PAYS',	'Pays',	NULL),
('VILLE',	'Ville',	'PAYS'),
('AEROPORT',	'Aéroport',	'VILLE');

DROP TABLE IF EXISTS "zones_geos";
CREATE TABLE "public"."zones_geos" (
    "id" bigint DEFAULT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    "parent_id" bigint,
    "libelle" character varying(255),
    "type_code" character varying(255),
    CONSTRAINT "zones_geos_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

INSERT INTO "zones_geos" ("id", "parent_id", "libelle", "type_code") VALUES
(1,	NULL,	'Sénégal',	'PAYS'),
(2,	NULL,	'France',	'PAYS'),
(3,	NULL,	'Maroc',	'PAYS'),
(4,	1,	'Dakar',	'VILLE'),
(5,	4,	'Aéroport international Blaise Diagne',	'AEROPORT'),
(6,	1,	'Saint-Louis',	'VILLE'),
(7,	6,	'Aéroport international de Saint-Louis',	'AEROPORT'),
(8,	2,	'Paris',	'VILLE'),
(9,	8,	'Aéroport Charles de Gaulle',	'AEROPORT'),
(10,	8,	'Aéroport d Orly',	'AEROPORT'),
(11,	2,	'Nantes',	'VILLE'),
(12,	11,	'Aéroport Nantes Atlantique',	'AEROPORT'),
(13,	3,	'Casablanca',	'VILLE'),
(14,	13,	'Aéroport Mohammed V',	'AEROPORT'),
(15,	3,	'Marrakech',	'VILLE'),
(16,	15,	'Aéroport Marrakech-Ménara',	'AEROPORT');

ALTER TABLE ONLY "public"."abonnements_client" ADD CONSTRAINT "FK1x7ohjd598hdt2l8y5eq5umc9" FOREIGN KEY (client_id) REFERENCES clients(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."abonnements_client" ADD CONSTRAINT "FK7ph8b45m8xlwlps2bv86pj3y5" FOREIGN KEY (plan_id) REFERENCES plans_abonnement(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."annonces" ADD CONSTRAINT "FK6lum8x1is59in4vqkucpsju5" FOREIGN KEY (tarification_id) REFERENCES tarifications(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."annonces" ADD CONSTRAINT "FKajw34u5yali80x02342cqsinx" FOREIGN KEY (it_zn_arrivee, it_zn_depart) REFERENCES itineraires(it_zn_arrivee, it_zn_depart) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."annonces" ADD CONSTRAINT "FKqfyrt64ovphemnl63eab0l221" FOREIGN KEY (client_id) REFERENCES clients(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."avis" ADD CONSTRAINT "FKlatl5hutmuyukfw7ieocup52x" FOREIGN KEY (annonce_id) REFERENCES annonces(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."client_addresses" ADD CONSTRAINT "FKhyovl6ikya05h99cxcye01hyj" FOREIGN KEY (client_id) REFERENCES clients(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."client_following" ADD CONSTRAINT "FKq89cd122uyotdu0ud7av64pt3" FOREIGN KEY (followed_id) REFERENCES clients(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."client_following" ADD CONSTRAINT "FKscb960vq4wteyomxc6cukchsp" FOREIGN KEY (follower_id) REFERENCES clients(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."client_phones" ADD CONSTRAINT "FK9eufg1vfh8bfik653llp9xmop" FOREIGN KEY (client_id) REFERENCES clients(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."commentaires" ADD CONSTRAINT "FK7t7xwwtg9hhaq77o5csq4p46g" FOREIGN KEY (client_id) REFERENCES clients(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."commentaires" ADD CONSTRAINT "FKb0rbkettsnacl6uwuyv866xgb" FOREIGN KEY (annonce_id) REFERENCES annonces(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."frais_supplementaires" ADD CONSTRAINT "FKarr5216380qyx75b30nlpwqmg" FOREIGN KEY (tarification_id) REFERENCES tarifications(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."historique_abonnements" ADD CONSTRAINT "FK9tupn4877xw3ntnrxx7wjlji5" FOREIGN KEY (client_id) REFERENCES clients(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."historique_abonnements" ADD CONSTRAINT "FKnwad7najif9hgc52u7s183xnq" FOREIGN KEY (plan_id) REFERENCES plans_abonnement(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."itineraires" ADD CONSTRAINT "FKbng7lpwqhgr6lpd6o0v908jyc" FOREIGN KEY (it_zn_arrivee) REFERENCES zones_geos(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."itineraires" ADD CONSTRAINT "FKo9i3o0l6mc17quhxawvf0u2rs" FOREIGN KEY (it_zn_depart) REFERENCES zones_geos(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."paiements" ADD CONSTRAINT "FKija8ywlnipiplwnlg5wwhktkx" FOREIGN KEY (abonnement_client_id) REFERENCES abonnements_client(id) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."type_zone_geo" ADD CONSTRAINT "FKke0pkogxmituhce5h8mserxy8" FOREIGN KEY (parent_code) REFERENCES type_zone_geo(code) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."type_zone_geo" ADD CONSTRAINT "fk_type_parent" FOREIGN KEY (parent_code) REFERENCES type_zone_geo(code) NOT DEFERRABLE;

ALTER TABLE ONLY "public"."zones_geos" ADD CONSTRAINT "FK3slf19q916t0yljldc38ofbfk" FOREIGN KEY (type_code) REFERENCES type_zone_geo(code) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."zones_geos" ADD CONSTRAINT "FKad9asehrh6hfwavqdovd20jv9" FOREIGN KEY (parent_id) REFERENCES zones_geos(id) NOT DEFERRABLE;
ALTER TABLE ONLY "public"."zones_geos" ADD CONSTRAINT "fk_zone_geo_type" FOREIGN KEY (type_code) REFERENCES type_zone_geo(code) NOT DEFERRABLE;

-- 2026-01-11 15:29:42.288653+00