FROM ubuntu:xenial-20190904
LABEL authors="Allen Yin <yinxinla@163.com>"

#================================================
# Customize sources for apt-get
#================================================
RUN  echo "deb http://archive.ubuntu.com/ubuntu bionic main universe\n" > /etc/apt/sources.list \
  && echo "deb http://archive.ubuntu.com/ubuntu bionic-updates main universe\n" >> /etc/apt/sources.list \
  && echo "deb http://security.ubuntu.com/ubuntu bionic-security main universe\n" >> /etc/apt/sources.list

# No interactive frontend during docker build
ENV DEBIAN_FRONTEND=noninteractive \
    DEBCONF_NONINTERACTIVE_SEEN=true

#========================
# Miscellaneous packages
# Includes minimal runtime used for executing non GUI Java programs
#========================
RUN apt-get -qqy update \
  && apt-get -qqy --no-install-recommends install \
    bzip2 \
    ca-certificates \
    openjdk-8-jre-headless \
    tzdata \
    sudo \
    wget \
    language-pack-en \
    locales \
    supervisor \
  && apt-get autoclean \
  && apt-get autoremove \
  && rm -rf /var/lib/apt/lists/* /var/cache/apt/* \
  && localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8 \
  && sed -i 's/securerandom\.source=file:\/dev\/random/securerandom\.source=file:\/dev\/urandom/' ./usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/java.security

#===================
# Timezone settings
# Possible alternative: https://github.com/docker/docker/issues/3359#issuecomment-32150214
#===================
ENV LANG en_US.utf8

ENV TZ "Asia/Shanghai"
RUN echo "${TZ}" > /etc/timezone \
  && dpkg-reconfigure --frontend noninteractive tzdata

#========================================
# Add normal user with passwordless sudo
#========================================
RUN useradd airbot \
         --shell /bin/bash  \
         --create-home \
  && usermod -a -G sudo airbot \
  && echo 'ALL ALL = (ALL) NOPASSWD: ALL' >> /etc/sudoers \
  && echo 'airbot:secret' | chpasswd
ENV HOME=/home/airbot

#=======================================
# Create shared / common bin directory
#=======================================
RUN  mkdir -p /opt/bin

#======================================
# Add Grid check script
#======================================
COPY entry_point.sh /opt/bin/
RUN chmod +x /opt/bin/entry_point.sh

#======================================
# Add Supervisor configuration file
#======================================
COPY supervisord.conf /etc

#==========
# Selenium & relaxing permissions for OpenShift and other non-sudo environments
#==========
RUN  mkdir -p /opt/airbot /var/run/supervisor /var/log/supervisor \
  && touch /opt/airbot/config.json \
  && chmod -R 777 /opt/airbot /var/run/supervisor /var/log/supervisor /etc/passwd \
  && chgrp -R 0 /opt/airbot ${HOME} /var/run/supervisor /var/log/supervisor \
  && chmod -R g=u /opt/airbot ${HOME} /var/run/supervisor /var/log/supervisor

#===================================================
# Run the following commands as non-privileged user
#===================================================
USER airbot


CMD ["/opt/bin/entry_point.sh"]