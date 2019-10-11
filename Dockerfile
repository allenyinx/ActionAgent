FROM airta/agent_base_publish:latest

ARG JAR_FILE=target/agent-*.jar
COPY ${JAR_FILE} /home/airbot/airgent.jar
