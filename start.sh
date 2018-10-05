#!/bin/bash

#Build Script of docker

directory=`echo ${PWD}`

#Base Image

#spark
sudo docker build --rm --no-cache -f directory/spark/Dockerfile -t spark2.0 . 


#kafka
sudo docker build --rm --no-cache -f directory/kafka/Dockerfile -t kafka2.0 .


#nifi
sudo docker build --rm --no-cache -f directory/nifi/Dockerfile -t nifi2.0 .


#MongoDBDocker
sudo docker build --rm --no-cache -f directory/mongo/Dockerfile -t mongo2.0 .


#Java Distributed packs
sudo docker build --rm --no-cache directory/application/Dockerfile -t javaDistributed2.0 .


#mariadbdocker
sudo docker build --rm --no-cache -f directory/mariadbdocker/Dockerfile -t mariadbdocker2.0 .


#LDAP
sudo docker build --rm --no-cache directory/ldap/Dockerfile -t ldap2.0 .



