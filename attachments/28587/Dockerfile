FROM phusion/baseimage:latest

# faster mirror please...
RUN sed -i 's/archive.ubuntu.com/ubuntu-archive.mirrors.proxad.net/' /etc/apt/sources.list

RUN apt-get update && apt-get -y install openjdk-7-jdk git apg

RUN mkdir /etc/skel/.ssh
ADD authorized_keys /etc/skel/.ssh/authorized_keys

RUN useradd -m -s /bin/bash -d /home/jenkins jenkins && \
    echo jenkins:$(apg -n 1 -x 16) | chpasswd && \
    chmod 700 /home/jenkins/.ssh

