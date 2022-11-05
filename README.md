# 7 Days Of Code Java

Project developed for the challenge: [7 days of code Java](https://7daysofcode.io/) by Alura.

# Getting Started

You need following the below passes for run the project.

## Prerequisites

* [JDK 17](https://jdk.java.net/17/)
* [Maven 3.6+](https://maven.apache.org/download.cgi)

## Configuration

1. Create a developer account on link: https://imdb-api.com/api
2. Create a developer account on link: https://developer.marvel.com/account
3. Create an environment variable called: **IMDB_API_KEY** with value generated in step 1
    ``` shell
       export IMDB_API_KEY=replace with API Key generated
    ```
4. Create two environment variables called: **MARVEL_PUBLIC_API_KEY** and **MARVEL_PRIVATE_API_KEY**
   with values generated in step 2
     ``` shell
        export MARVEL_PUBLIC_API_KEY=replace with Public API Key generated
        export MARVEL_PRIVATE_API_KEY=replace with Private API Key generated
     ```
   
## Build and Run

1. Build app with package maven command:
     ``` shell
        mvn package
     ```
   **ps.**: You also can build to jdk-8 target. Only use jdk-8 maven profile:
     ``` shell
        mvn package -P jdk-8
     ```
2. And then run 
   ``` shell
        java -jar 7-days-of-code-java-jdk17-1.0-SNAPSHOT.jar
     ```
   Or if you build to jdk-8 target:
   ``` shell
        java -jar 7-days-of-code-java-jdk8-1.0-SNAPSHOT.jar
     ```
   **ps.**: If you want to translate .html output, pass the **locale** as argument following tag format:

   ``` shell
        java -Dlocale=pt-BR -jar 7-days-of-code-java-jdk17-1.0-SNAPSHOT.jar
   ```