FROM centos/systemd
RUN yum -y update 
RUN yum -y install net-tools

COPY ./jdk1.8.0_181 /opt/jdk1.8.0_181 

COPY ./spark-2.3.2-bin-hadoop2.7 /opt/spark-2.3.2-bin-hadoop2.7

ENV JAVA_HOME=/opt/jdk1.8.0_181
ENV PATH="${JAVA_HOME}/bin:${PATH}"

ENV SPARK_HOME=/opt/spark-2.3.2-bin-hadoop2.7
ENV PATH="${SPARK_HOME}/bin:${PATH}"

CMD ["/usr/sbin/init"]
