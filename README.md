# SAMAGP-API-MS



# Docker generation et push de l'image et le tag
1 - Connexion à Docker Hub
Si tu n'es pas encore connecté :
docker login

2 - Build de l'image Docker
docker build -t samagp-api:v1 -t samagp-api:latest .

3 - Taguer l'image avec ton nom Docker Hub
docker tag samagp-api:v1 samagp/samagp-api:v1
docker tag samagp-api:latest samagp/samagp-api:latest
4 - Push de l'image vers Docker Hub
docker push samagp/samagp-api:v1
docker push samagp/samagp-api:latest

# CONNECTER POSTGRES

kubectl exec -it <pod_name> -- psql -U <DATABASE_USER> -d samagp

psql -U postgres 
\l
CREATE DATABASE samagp;

# pour lancer en localhost avec docker
docker run --name postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=samagp -p 5435:5432 -d postgres

# Les volumes ne sont pas accessibles directement depuis le système de fichiers, mais tu peux y accéder via un conteneur temporaire :
docker run --rm -it \
-v keycloak-data:/data \
alpine \
sh
# puis 
cd /data
ls -l
