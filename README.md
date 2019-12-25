# Deployment

1. Start minikube
   ```bash
   minikube start --vm-driver=virtualbox
   minikube addons enable ingress
   ```
1. Create database-related secrets
   ```bash
   kubectl create secret generic symbols-db-root-credentials \
        --from-literal=username=root \
        --from-literal=password=super-secret-password
   
   kubectl create secret generic symbols-db-service-credentials \
        --from-literal=username=biba \
        --from-literal=password=kuka \
        --from-literal=dbname=symbols
   ```
1. Follow the [instructions](db/README.md) to deploy the database
1. Follow the [instructions](api/README.md) to deploy the service
