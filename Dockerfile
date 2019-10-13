FROM airta/agent_base_publish:latest

ARG JAR_FILE=target/agent-*.jar

COPY driver/chromedriver /home/airbot/chromedriver
RUN chmod +x /home/airbot/chromedriver
COPY ${JAR_FILE} /home/airbot/airgent.jar
