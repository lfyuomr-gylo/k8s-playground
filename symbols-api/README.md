## Build image

```bash
./gradlew clean build && docker build . -t lfyuomrgylo/symbols-api && docker push lfyuomrgylo/symbols-api
```

## Deploy to minikube

```bash
minikube start --vm-driver=virtualbox
minikube addons enable ingress

for file in `ls | grep -P '^deployment\..*\.yml$'` ; do kubectl create -f $file ; done

# Добавить новый символ
curl \
    -XPUT \
    -H "Host: symbols-registry.com" \
    -H "Content-type: text/plain" \
    -d 'biba kuka' \
    `minikube ip`/symbols/biba

# Получить символ
curl \
    -H "Host: symbols-registry.com" \
    `minikube ip`/symbols/biba

# Эмулируем падение монги, чтобы кубернетес пересоздал контейнер
kubectl exec  deploy/symbols-db kill 1 

# Убеждаемся, что данные не потерялись
curl \
    -H "Host: symbols-registry.com" \
    `minikube ip`/symbols/biba

```