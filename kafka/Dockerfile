From centos:latest
RUN yum -y install net-tools supervisor


FROM java:8
RUN tar -xzvf ./confluent-oss-4.1.0-2.11.tar.gz -C  /etc/confluent-4.1.0


ADD ./start.sh /start.sh
ADD ./supervisord.conf /etc/supervisord.conf
RUN chmod 755 /start.sh

WORKDIR /etc/confluent-4.1.0/bin/

EXPOSE 2181
EXPOSE 8082
EXPOSE 9092

ENTRYPOINT /etc/confluent-4.1.0/bin/confluent start zookeeper
CMD ["/bin/bash", "/start.sh"]
