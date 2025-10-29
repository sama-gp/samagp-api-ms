.PHONY: build build-api build-angular up down logs clean

# Variables
API_IMAGE=samagp-api
ANGULAR_IMAGE=samagp-angular

# Build toutes les images
build: build-api build-angular

# Build l'image API
build-api:
	@echo "Building $(API_IMAGE)..."
	docker build -t $(API_IMAGE):latest .

# Build l'image Angular
build-angular:
	@echo "Building $(ANGULAR_IMAGE)..."
	cd ../test/samagp-angular && docker build -t $(ANGULAR_IMAGE):latest .

# Démarrer en production
up:
	docker-compose -f docker-compose.prod.yml up -d

# Arrêter
down:
	docker-compose -f docker-compose.prod.yml down

# Voir les logs
logs:
	docker-compose -f docker-compose.prod.yml logs -f

# Nettoyer
clean:
	docker-compose -f docker-compose.prod.yml down -v
	docker rmi $(API_IMAGE):latest $(ANGULAR_IMAGE):latest || true

# Builder et démarrer
deploy: build up