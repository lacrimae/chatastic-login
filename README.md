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
2. Start the Redis server by running the following command in your terminal:

   ```bash
   redis-server
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
