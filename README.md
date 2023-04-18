# Chatastic Login Microservice

## Overview

Welcome to the Chatastic Login microservice! This repository is one of many microservices that are part of the Chatastic
web app. You can find a list of all related microservices at the following URL:

https://github.com/stars/lacrimae/lists/chatastic

Each microservice is dedicated to a specific aspect of the Chatastic app, including authentication, user management,
messaging, and more. By breaking down the app into smaller, modular components, we can achieve greater flexibility,
scalability, and maintainability.

If you're interested in contributing to the Chatastic app, we encourage you to explore the microservice repositories
listed at the URL above. Each repository includes documentation and instructions for building, running, and testing the
microservice. You can also check out our main Chatastic repository at https://github.com/lacrimae/chatastic for an
overview of the entire app and information on how to get started.

We welcome contributions from developers of all skill levels, so don't hesitate to get involved and help us build the
best possible version of Chatastic!

## About

The Chatastic Login microservice handles all tasks related to user authentication, including user login, registration,
email confirmation, and logout. This microservice is designed to be modular, scalable, and easy to integrate with other
components of the Chatastic app. If you have any questions or issues related to the Login microservice, please feel free
to open an issue or submit a pull request.

## Run Locally

1. Ensure that Redis is installed on your local machine. If you haven't installed Redis yet, you can follow the
   instructions on the [Redis download page](https://redis.io/download/) to install it.   
   <br>
   Start the Redis server by running the following command in your terminal:

   ```bash
   redis-server
   ```

2. Make sure you have Kafka installed on your local machine. If you haven't installed Kafka yet, you can follow the
   documentation on the [Kafka download page](https://kafka.apache.org/quickstart) to install it.  
   <br>
   Once Kafka is installed, start the ZooKeeper server by running the following command in your terminal:
   ```bash
   zookeeper-server-start.sh config/zookeeper.properties
   ```

   Then, start the Kafka server by running the following command in your terminal:

   ```bash
   kafka-server-start /usr/local/etc/kafka/server.properties
   ```

   If you are using `brew`:
   ```bash
   brew install kafka
   /opt/homebrew/opt/kafka/bin/zookeeper-server-start /opt/homebrew/etc/kafka/zookeeper.properties
   /opt/homebrew/opt/kafka/bin/kafka-server-start /opt/homebrew/etc/kafka/server.properties
   ```

3. Clone the repository and navigate to the project directory:

   ```bash
   git clone https://github.com/lacrimae/chatastic-login.git
   cd chatastic-login
   ```

4. Build and run the application using Gradle by running the following command in your terminal:
   ```bash
   ./gradlew bootRun
   ```
   This will start the application on http://localhost:8080.

   Alternatively, you can also use the following command to run the application:
    ```bash
    gradle bootRun
    ```
   > You need to have Gradle installed on your system to use this command. You can download and install Gradle from the
   [official website](https://gradle.org/install/).
5. Access the application in your web browser at http://localhost:8080.

## Kafka Commands

You can use the following commands to list and get details about Kafka topics:

1. List all topics in the Kafka cluster:

   ```bash
   ./path-to-kafka/bin/kafka-topics --bootstrap-server=localhost:9092 --list
   ```

2. Get details about a specific topic:

   ```bash
   ./path-to-kafka/bin/kafka-topics --bootstrap-server=localhost:9092 --describe --topic users
   ```
3. Use the `kafka-console-consumer` tool to view the data of a Kafka topic from the command line:

   ```bash
   ./path-to-kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic users --from-beginning
   ```

Replace /path-to-kafka with the actual path to your Kafka installation directory. Running these commands will give you a
list of topics or details about a specific topic, such as the number of partitions and their replication factor.
