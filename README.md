# api-clientes

## Instruções

### Usar docker no minikube

```
minikube docker-env
minikube -p minikube docker-env | Invoke-Expression
```

### Criando uma instância do postgres no kubernetes

```
kubectl create -f postgres-deployment.yaml
kubectl create -f postgres-service.yaml
kubectl create -f config-map.yaml
```
### Criando imagem da aplicação

```
docker build -f Dockerfile --build-arg JAR_FILE=target/api-clientes-0.0.1-SNAPSHOT.jar -t cliente-api:v1 . 
```

### Subindo a aplicação no kubernetes

```
kubectl create -f deployment.yaml
kubectl create -f service.yaml
```


### Descobrindo a endereço da aplicação no kubernetes

```
minikube service cliente-api --url
```
