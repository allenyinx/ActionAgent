FROM airta/agent_base_publish:latest

ARG JAR_FILE=target/agent-*.jar

ARG BUILD_DATE
ARG VCS_REF
ARG VERSION

LABEL org.label-schema.build-date=$BUILD_DATE \
      org.label-schema.name="Airgent" \
      org.label-schema.description="airta webdriver agent" \
      org.label-schema.url="http://www.airta.co" \
      org.label-schema.vcs-ref=$VCS_REF \
      org.label-schema.vcs-url="https://github.com/allenyinx/ActionAgent" \
      org.label-schema.vendor="airta group" \
      org.label-schema.version=$VERSION \
      org.label-schema.schema-version="1.0"

COPY driver/chromedriver /home/airbot/chromedriver
RUN sudo chmod +x /home/airbot/chromedriver
COPY ${JAR_FILE} /home/airbot/airgent.jar
