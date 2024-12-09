# Commands to setup monitoring using Grafana and Prometheus

Run the below commands in the terminal

```bash
curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
```

```bash
docker pull grafana/loki:2.9.3
docker pull grafana/promtail:2.9.3
docker pull grafana/grafana:latest
```

```bash
helm repo add grafana https://grafana.github.io/helm-charts
```

```bash
helm install loki grafana/loki-stack   --namespace monitoring   --create-namespace   --set loki.image.tag=2.9.3   --set promtail.enabled=true   --set promtail.config.server.http_listen_port=3101   --set promtail.config.clients[0].url=http://loki:3100/loki/api/v1/push   --set promtail.config.positions.filename=/run/promtail/positions.yaml   --set promtail.config.scrape_configs[0].job_name="kubernetes-pods"   --set promtail.config.scrape_configs[0].kubernetes_sd_configs[0].role="pod"   --set promtail.config.scrape_configs[0].relabel_configs[0].action="keep"   --set promtail.config.scrape_configs[0].relabel_configs[0].source_labels="[_meta_kubernetes_namespace]"   --set promtail.config.scrape_configs[0].relabel_configs[0].regex=".*"   --set promtail.config.scrape_configs[0].relabel_configs[1].source_labels="[meta_kubernetes_pod_name]"   --set promtail.config.scrape_configs[0].relabel_configs[1].target_label="job"   --set promtail.config.scrape_configs[0].relabel_configs[2].source_labels="[meta_kubernetes_namespace]"   --set promtail.config.scrape_configs[0].relabel_configs[2].target_label="namespace"   --set promtail.config.scrape_configs[0].relabel_configs[3].source_labels="[_meta_kubernetes_pod_name]"   --set promtail.config.scrape_configs[0].relabel_configs[3].target_label="pod"   --set grafana.enabled=true   --set prometheus.enabled=true
```

After this, open 3 seperate terminals and run one cmd in each

```bash
kubectl port-forward --namespace monitoring svc/loki-prometheus-server 9090:80
```

```bash
kubectl port-forward --namespace monitoring service/loki-grafana 3000:80
```

```bash
kubectl port-forward svc/loki -n monitoring 3100:3100
```

Then open the port grafana is running on - localhost:3000. It will propmt a username and password. Username is 'admin'. To get the password run the below cmd. It will display the password.

```bash
kubectl get secret loki-grafana -n monitoring -o jsonpath="{.data.admin-password}" | base64 --decode
```

Once logged in. Go to Sidemenu > Connections > Data Sources > Loki/Prometheus > Test > Explore

A dashboard will appear where you can play around the labels and observe the monitoring.

