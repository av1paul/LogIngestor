# Log Ingestor and Query Interface

A Log Ingestor System that can efficiently handle vast volumes of log data, and a simple interface for querying this data using full-text search or specific field filters.

## Features
- **Log Ingestor System**
  - Large volume of logs can be ingested via an HTTP server, by sending POST HTTP request on path **http://localhost:3000**.
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
- **Query Interface**
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
   - Go to MongoDB Atlas/Compass and create a Database named "LogIngestor".
   - Inside the "LogIngestor" Database, create a Collection named "log". 

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




## Usage

```python
import foobar

# returns 'words'
foobar.pluralize('word')

# returns 'geese'
foobar.pluralize('goose')

# returns 'phenomenon'
foobar.singularize('phenomena')
```

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)
