FROM b3ds:1

MAINTAINER "B3 DIgital Solution"

USER root

WORKDIR /temp

COPY hadoop-2.7.1.tar.gz /temp
COPY etc /temp/etc

#install ssh add these inside base image
RUN yum install -y openssh-server openssh-clients rsync initscripts-9.49.41-1.el7_5.1.x86_64

RUN yum update -y libselinux

#configure passwordless ssh
RUN ssh-keygen -q -N "" -t dsa -f /etc/ssh/ssh_host_dsa_key
RUN ssh-keygen -q -N "" -t rsa -f /etc/ssh/ssh_host_rsa_key
RUN ssh-keygen -q -N "" -t rsa -f /root/.ssh/id_rsa
RUN cp /root/.ssh/id_rsa.pub /root/.ssh/authorized_keys

#Install hadoop
RUN tar -xzvf hadoop-2.7.1.tar.gz -C /opt/
RUN mkdir /opt/hadoop-2.7.1/logs

#Set environment
ENV HADOOP_HOME /opt/hadoop-2.7.1
ENV HADOOP_INSTALL /opt/hadoop-2.7.1
ENV HADOOP_MAPRED_HOME /opt/hadoop-2.7.1
ENV HADOOP_COMMON_HOME /opt/hadoop-2.7.1
ENV HADOOP_HDFS_HOME /opt/hadoop-2.7.1
ENV YARN_HOME /opt/hadoop-2.7.1
ENV HADOOP_COMMON_LIB_NATIVE_DIR /opt/hadoop-2.7.1/lib/native
ENV HADOOP_OPTS "-Djava.library.path=${HADOOP_INSTALL}/lib"


ENV PATH="${HADOOP_HOME}/bin:${HADOOP_HOME}/sbin:${PATH}"
RUN rm -r ${HADOOP_HOME}/etc
RUN cp -r /temp/etc "${HADOOP_HOME}"

RUN ${HADOOP_HOME}/bin/hdfs namenode -format
RUN rm hadoop-2.7.1.tar.gz

RUN mkdir -p /storage/hadoop/hdfs
RUN chown -R root:root /storage/hadoop/hdfs
ADD ssh_config /root/.ssh/config

RUN chmod 600 /root/.ssh/config
RUN chown root:root /root/.ssh/config

EXPOSE 50010 50020 50070 50075 50090 8020 9000
EXPOSE 10020 19888
EXPOSE 8030 8031 8032 8033 8040 8042 8088
EXPOSE 49707 2122

RUN systemctl enable sshd
#RUN service sshd start
COPY start.sh /temp
RUN chmod +x /temp/start.sh
#ENTRYPOINT ["/temp/start.sh"]

CMD ["/usr/sbin/init"]
