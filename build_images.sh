#!/bin/bash

# Script pour builder toutes les images Docker
set -e

echo "Building all Docker images..."

# Builder l'image de l'API
echo "Building API image..."
cd .
docker build -t samagp-api:latest .

# Builder l'image Angular
echo "Building Angular image..."
cd ../test/samagp-angular
docker build -t samagp-angular:latest .

echo "All images built successfully!"
echo ""
echo "Available images:"
docker images | grep -E "(samagp-api|samagp-angular)"