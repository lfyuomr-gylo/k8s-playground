# What is this?

This directory contains configuration files for MongoDB deployment for Symbols Service.
Deployment instructions are provided below.

There are two users created in the database: root user and a user with ``readWrite`` role in symbols database.
User credentials are expected to be stored in ``symbols-db-root-credentials`` and ``symbols-db-service-credentials``
secrets respectively. Authentication database is ``admin`` for both users.

# Deployment

1. Ensure you have secrets ``symbols-db-root-credentials`` and ``symbols-db-service-credentials`` 
   with keys ``username`` and ``password`` set in both of them.
1. Build docker image and push it to the registry.
   ```bash
   docker build -t lfyuomrgylo/symbols-mongo docker/
   docker push lfyuomrgylo/symbols-mongo
   ``` 
1. Deploy the database to Kubernetes and make it accessible via Service.
   ```bash
   for file in `ls k8s/` ; do kubectl apply -f k8s/$file ; done
   ```
