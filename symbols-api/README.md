## Build image

```bash
./gradlew clean build && docker build . -t lfyuomrgylo/symbols-api && docker push lfyuomrgylo/symbols-api
```

## Deploy to minikube

```bash
minikube start --vm-driver=virtualbox
minikube addons enable ingress

for file in `ls | grep -v -P '^deployment\..*\.yml$'` ; do kubectl create -f ; done

# Добавить новый символ
curl \
    -XPUT \
    -H "Host: symbols-registry.com" \
    -H "Content-type: text/plain" \
    -d 'biba kuka' \
    `minikube ip`/symbols/biba
```