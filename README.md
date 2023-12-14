# sfc-transporte
Servicio Rest API para consultar envíos y viajes en JDE.
Jersey Rest API.

docker run -d --name UNOCORP-PORTAINER -p 8000:8000 -p 9443:9443 -p 9000:9000 --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v "/home/VolumenDocker/Portainer":/data --env TZ=America/Guatemala portainer/portainer-ce:latest

docker network create unocorp_network

docker run -d --name UNOCORP-MYSQL --network unocorp_network -p 3306:3306 --restart=always --env MYSQL_ROOT_PASSWORD=Un0Corp2023 --env TZ=America/Guatemala --volume "/home/VolumenDocker/UnoCorpMysql":/var/lib/mysql mysql:latest
docker run -d --name UNOCORP-MYSQL --network unocorp_network -p 3306:3306 --restart=always --env MYSQL_ROOT_PASSWORD=Un0Corp2023 --env TZ=America/Guatemala --volume "C:\VolumeDocker\UnoCorpMysql":/var/lib/mysql mysql:latest

mysql> CREATE DATABASE db_transportes;
mysql> CREATE USER 'user_transportes'@'%' IDENTIFIED BY 'TransGPS2023';
mysql> GRANT ALL ON db_transportes.* TO 'user_transportes'@'%';

docker run -d --name UNOCORP-PAYARA --network unocorp_network -p 9001:8080 -p 4848:4848 --restart=always --env TZ=America/Guatemala payara/server-full:6.2023.6

docker build -t unocorp/transportes-cliente-rest-api:1.0.0 .
docker run -p 9002:8018 -t -i --network unocorp_network --name UNOCORP-CLIENTE-REST-VIAJES --restart=always --env TZ=America/Guatemala unocorp/transportes-cliente-rest-api:1.0.0

docker build -t unocorp/unocorp-rest-api:1.0.0 .
docker run -p 9003:8080 -t -i --network unocorp_network --name UNOCORP-REST-API --restart=always --env TZ=America/Guatemala unocorp/unocorp-rest-api:1.0.0

docker build -t unocorp/unocorp-cliente-rest-sms:1.0.0 .
docker run -p 9004:8080 -t -i --network unocorp_network --name UNOCORP-REST-API-SMS --restart=always --env TZ=America/Guatemala unocorp/unocorp-cliente-rest-sms:1.0.0

docker build -t unocorp/unocorp-cliente-rest-geotab:1.0.0 .
docker run -p 9005:8080 -t -i --network unocorp_network --name UNOCORP-REST-API-GEOTAB --restart=always --env TZ=America/Guatemala unocorp/unocorp-cliente-rest-geotab:1.0.0
