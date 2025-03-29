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

# cONNECTER POSTGRES

kubectl exec -it <pod_name> -- psql -U <DATABASE_USER> -d samagp

psql -U postgres 
\l
CREATE DATABASE samagp;
