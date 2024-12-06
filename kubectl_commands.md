# Important commands for Kubernetes+Minikube

## Start Minikube (Kubernetes cluster instance)

```bash
minikube start --driver=docker
minikube status
```

## Creating pods

```bash
kubectl apply -f backend-deployment.yaml
kubectl apply -f backend-service.yaml
kubectl apply -f frontend-deployment.yaml
kubectl apply -f frontend-service.yaml
```

### Check pods

```bash
kubectl get pods
```

## Creating HPA

```bash
kubectl apply -f backend-hpa.yaml
kubectl apply -f frontend-hpa.yaml
```

### Check HPA

```bash
kubectl get hpa
```

## Running the application

```bash
kubectl port-forward service/frontend 3000:3000
kubectl port-forward service/backend 8081:8081
```

## Monitoring pods

```bash
kubectl get pods --watch
```
