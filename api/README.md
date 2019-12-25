# What is this?

This is a dummy CRU~~D~~ REST server for storing Symbols --- arbitrary text identified by its name.
This directory contains all configurations needed to deploy this service to Kubernetes.

# Deployment

1. Ensure that you've deployed database for this server. It should be available via ``symbols-db`` service.
1. Ensure you've created ``symbols-db-service-credentials`` secret with ``username`` and ``password`` keys.
1. Build docker image and push it to the registry.
   ```bash
   ./gradlew clean build
   docker build . -t lfyuomr-gylo/symbols-api
   docker push lfyuomr-gylo/symbols-api
   ``` 
1. Deploy to kubernetes
   ```bash
   for file in `ls k8s/` ; do kubectl apply -f k8s/$file ; done
   ```

## Verification

Let's ensure that everything is working well. The commands below assume we've deployed the service
to Minikube, so that cluster is available at ``minikube ip`` address.

```bash
# Add new symbol
curl \
    -XPUT \
    -H "Host: symbols-registry.com" \
    -H "Content-type: text/plain" \
    -d 'biba kuka' \
    `minikube ip`/symbols/biba

# Get this symbol
curl \
    -H "Host: symbols-registry.com" \
    `minikube ip`/symbols/biba

# Emulate database restart
kubectl exec  deploy/symbols-db kill 1 

# Ensure the data is still there
curl \
    -H "Host: symbols-registry.com" \
    `minikube ip`/symbols/biba

```
