# Log Ingestor and Query Interface

A Log Ingestor System that can efficiently handle vast volumes of log data, and a simple interface for querying this data using full-text search or specific field filters.

## Features
### Log Ingestor System
  - Large volume of logs can be ingested via an HTTP server, by sending `POST` HTTP request to the endpoint `http://localhost:3000`.
  - Request Body (JSON) format:
    ```json 
    {
      "level": "error",
      "message": "Failed to connect to DB",
      "resourceId": "server-1234",
      "timestamp": "2023-09-15T08:00:00Z",
      "traceId": "abc-xyz-123",
      "spanId": "span-456",
      "commit": "5e5342f",
      "metadata": {
        "parentResourceId": "server-0987"
      }
    }

    ```
### Query Interface
  - Command Line Interface (CLI) for fast and efficient full-text search across logs. Filters based on:
     - level
     - message
     - resourceId
     - timestamp
     - traceId
     - spanId
     - commit
     - metadata.parentResourceId
   - Logs can be searched within specific date ranges.
   - Regular expressions are used for search.
   - Multiple filters can be combined.
   - Provision for real-time log ingestion and searching capabilities.

## System Requirements

Before you begin, ensure your system meets the following requirements:

- **Operating System:** Windows 10/11
- **Java:** Version 17.0.0 or higher
- **Apache Maven:** Version 3.9.0 or higher
- **Apache Kafka:** Version 3.6.0 or higher
- **Python:** Version 3.6.0 or higher
- **MongoDB:** Version 7.0.2 or higher (Or can use MongoDB Atlas)
- **Git**
- **IntelliJ IDEA Community Editon**

## Setup
### MongoDB Setup: 

This project uses MongoDB as its Database. Follow these steps to set up the required MongoDB database and collection: 
   - Go to MongoDB Atlas/Compass and create a Database named `log-ingestor`.
   - Inside the "log-ingestor" Database, create a Collection named `log`. 

### Apache Kafka Setup: 
This project uses Apache Kafka. Follow these steps to start the Kafka Zookeeper and Kafka Server. 
   - Run the following commands in order to start all services in the correct order:
     ```bash
        .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
     ```
   - Open another terminal session and run:
     ```bash
        .\bin\windows\kafka-server-start.bat .\config\server.properties
     ```
### Spring Boot Application Repository Setup: 
This project is a Spring Boot Application. Follow these steps to setup and application repository. 
   - Open a terminal and in a directory of your choice, run the following to clone the repository:
     ```bash
        git clone https://github.com/dyte-submissions/november-2023-hiring-av1paul.git
     ```
### Import Project into IntelliJ IDEA

- Open IntelliJ IDEA.
- Click on `File` -> `Open...` and select the project directory (`LogIngestor`).
- IntelliJ IDEA will automatically recognize it as a Maven project and import the necessary dependencies.

### Configure Application

- Open `src/main/resources/application.properties` for application-specific configurations.
- Modify the configurations as needed.
  ```properties
     server.port=3000

     spring.data.mongodb.auto-index-creation=true
     spring.data.mongodb.uri=mongodb://localhost:27017
     spring.data.mongodb.database=log-ingestor

     spring.kafka.producer.bootstrap-servers=localhost:9092
     spring.kafka.consumer.bootstrap-servers=localhost:9092

     spring.kafka.producer.key-serializer=org.springframework.kafka.support.serializer.StringOrBytesSerializer
     spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
     spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
     spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer

     spring.kafka.consumer.group-id=log-consumer
     spring.kafka.consumer.max.poll.records=100
     spring.kafka.consumer.auto-offset-reset=earliest
     spring.kafka.consumer.properties.spring.json.trusted.packages=com.avi.pojo
  ```
### Run the Application
- Open the `LogIngestorApplication` class located in `src/main/java/com/avi`.
- Right-click on the file and select `Run 'Application'`.
- Alternatively, you can run the application from the command line (make sure in the project directory):
    ```bash
    ./mvnw clean spring-boot:run
    ```
- The application will be accessible at `http://localhost:3000`.

### Start the Command Line Interface
- Open a terminal instance in the project directory and the change directory to `cli-script` using
    ```bash
    cd cli-script
    ```
- To start the Interface, run the following command to start the Python Script:
    ```bash
    python .\search.py
    ```
- You might need to install the `requests` library if not already installed. This can be running:
    ```bash
    pip install requests
    ```


## Usage
### Add/Upload Logs
- Logs can be uploaded by send `POST` HTTP request to the endpoint `http://localhost:3000`.
Request Body (JSON) format:
   ```
   {
      "level": "error",
      "message": "Failed to connect to DB",
      "resourceId": "server-1234",
      "timestamp": "2023-09-15T08:00:00Z",
      "traceId": "abc-xyz-123",
      "spanId": "span-456",
      "commit": "5e5342f",
      "metadata": {
      "parentResourceId": "server-0987"
      }
   }
   ```
- This can done by using:
  - Postman
  - Curl

### Add/Upload Logs
- Logs can be uploaded by send `POST` HTTP request to the endpoint `http://localhost:3000`.
Request Body (JSON) format:
   ```
   {
      "level": "error",
      "message": "Failed to connect to DB",
      "resourceId": "server-1234",
      "timestamp": "2023-09-15T08:00:00Z",
      "traceId": "abc-xyz-123",
      "spanId": "span-456",
      "commit": "5e5342f",
      "metadata": {
      "parentResourceId": "server-0987"
      }
   }
   ```
- This can done by using:
  - Postman
  - Curl


## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)
