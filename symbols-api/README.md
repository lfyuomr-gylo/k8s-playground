## Build image

```bash
./gradlew clean build && docker build . -t lfyuomrgylo/symbols-api && docker push lfyuomrgylo/symbols-api
```

## Deploy to minikube

```bash
minikube start --vm-driver=virtualbox

for component in `echo "db api" | tr " " "\n"` ; do
    for kind in `echo "deployment svc" | tr " " "\n"` ; do
        kubectl apply -f deployment.$component.$kind.yml
    done 
done

# Добавить новый символ
API_HOST=`minikube ip`
API_PORT=`kubectl get -f deployment.api.svc.yml -o jsonpath='{.spec.ports[0].nodePort}'`
curl \
    -XPUT \
    -H "Content-type: text/plain" \
    -d 'biba kuka' \
    $API_HOST:$API_PORT/symbols/biba
```