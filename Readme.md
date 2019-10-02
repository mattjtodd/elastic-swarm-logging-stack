# ELK, Beats, Grafana Telemetry & Log Aggregation in Docker

This project provides an example configuration for a containerised stack to collect, aggregate and visualise metrics 
and logs from host, containers and running applications.   It relies heavily on the the 
[Elastic Stack](https://www.elastic.co/products/) and docker engine features such as logging and is designed to be
extended to provide additional behaviour and should not be considered production ready due to lack of HA, replication, 
security  and redundancy etc.  If desired this could be configured, but is not in scope for this project.

The core components are:

* [Elasticsearch](https://www.elastic.co/) - Document database for storing the log and metric data sample documents
* [Metricbeat](https://www.elastic.co/products/beats/metricbeat) - Agent to pull host and container metrics to be pushed to logstash
* [Logstash](https://www.elastic.co/products/logstash) - Data pipeline and transformation tool
* [Kibana](https://www.elastic.co/products/kibana) - Visualisation and searching tool for data store in Elasticsearch
* [Portainer](https://www.portainer.io/) - Docker Engine management UI
* Application - The target application (Spring boot 2.1.x) HTTP server to demonstrate logging

### Prerequisites

Docker Swarm enabled engine 19+
Docker compose 1.24.1+
At least 4G memory, but may work with less!

Swarm can be enabled on a single node by issuing:

`docker swarm init`

## Getting Started

Using the CLI in your favourite shell working in the root of this repo once cloned

Build the Spring application:

```docker-compose -f application.yml build```

Pull all the images:

Build the Spring application:

```docker-compose -f portainer.yml -f elastic.yml -f elk.yml pull```

Deploy Portainer to get a nice UI over Docker

`docker stack deploy -c portainer.yml portainer`

Point a browser at http://localhost:9000
 
Deploy elasticsearch and kibana along with populating the metricbeat index template:

```docker stack deploy -c elastic.yml elk``` 

Wait until the `metricbeat-index-management` has completed successfully, then deploy the rest of the ELK stack into 
the same stack:

```docker stack deploy -c elk.yml elk```

And the application into a separate stack:

```docker stack deploy -c application.yml application```
 
This will have started all of the applications with metricbeat collecting metrics about the docker swarm containers,
hosts and logging configured and available for the application.

The relevant visualisation tools are available in a browser at the following.  WARNING - no or minimal security 
configuration has been made to the services and will need to be configured to your risk profile as desired for 
production. 

* Portainer - http://localhost:9000/#/home
* Kibana - http://localhost:5601
* Grafana - http://localhost:3000
* Application - http://localhost:8080/greeting?message=<your-message-to-pass-to-the-logs>

A simple test for the log aggregation can be performed using curl / putty by requesting

There is a datasource and simple dashboard pre-configured in Grafana to show how data can be visualised.

`curl "localhost:8080/greeting?message=hello"`

## Teardown

`docker stack rm application elk portainer`



