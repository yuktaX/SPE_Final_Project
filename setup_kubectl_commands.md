# Important commands for Kubernetes+Minikube on local setup

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
```

```bash
kubectl port-forward service/backend 8081:8081
```

## Monitoring pods

```bash
kubectl get pods --watch
```

# Run these before executing pipeline - Setup K8s on Jenkins

```bash
minikube start
```

After starting Minikube, the kubeconfig file should be created automatically in the default location (~/.kube/config). Verify it exists:

```bash
ls ~/.kube/config
```

Make sure minikube context is setup

```bash
kubectl config current-context
```

Once the config file exists, copy it to the jenkins directory

```bash
sudo mkdir -p /var/lib/jenkins/.kube
sudo cp ~/.kube/config /var/lib/jenkins/.kube/config
sudo chown -R jenkins:jenkins /var/lib/jenkins/.kube
chmod 600 /var/lib/jenkins/.kube/config
```

Test the kubectl with jenkins user

```bash
sudo -u jenkins kubectl cluster-info --kubeconfig=/var/lib/jenkins/.kube/config
```

If this gives you a client key error, then run these cmds

```bash
ls -l /home/{user}/.minikube/profiles/minikube/
```

```bash
sudo chown -R yukta:jenkins /home/{user}/.minikube/profiles/minikube
sudo chmod -R 755 /home/{user}/.minikube/profiles/minikube
```

Then run the previous cmd to grant jenkins user permission.

You should see a successful connection. To run the application, this should show you where the backend and frontend is running along with the urls. Open the URL mentioned.

```bash
minikube service list
```
