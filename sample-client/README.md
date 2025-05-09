# Sample Client

This module is a Spring Boot application that uses Spring AI to interact with Model Context Protocol (MCP) servers. It provides a client interface for accessing tools and services exposed by MCP servers.

## Features

- Connects to MCP servers using the Spring AI framework
- Provides REST endpoints for accessing server functionality
- Can be configured to interact with any of the MCP server modules (sample-server-stdio, sample-server-http, or github-server-http)

## Endpoints

- `/tools` - Returns a list of available tools from connected MCP servers
- `/adam` - Returns a list of what Adam has to offer from MCP servers
- `/github` - Returns GitHub metrics for Adam Al-Salman

## Configuration
- The client is configured to connect to MCP servers defined in `application.yaml`
- The client is currently configured to connect to two HTTP/SSE MCP servers:
  - "sample-server-http" at http://localhost:8081
  - "github-server-http" at http://localhost:8082
- The client can also connect to STDIO MCP servers using the configuration in `mcp-servers.json`, but this is currently commented out in `application.yaml`
- The configuration can be adjusted to connect to any of the available MCP server modules by uncommenting the appropriate sections in `application.yaml`

## Usage

### Prerequisites

- Java 21 or later
- Maven
- OpenAI API Key

### Running the Application

1. Export your OpenAI API key as an environment variable:
   ```bash
   export OPENAI_API_KEY=your_openai_api_key_here
   ```

   > **Important**: The application requires a valid OpenAI API key to function properly. Without this key, the application will fail to start or will not be able to process requests correctly.

2. Build the application:
   ```bash
   mvn clean package
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

   Alternatively, you can run the JAR file directly:
   ```bash
   java -jar target/sample-client-0.0.1-SNAPSHOT.jar
   ```

4. Access the REST endpoints to interact with the connected MCP servers:
   - http://localhost:8080/tools

```
curl -G http://localhost:8080/query --data-urlencode "query=what non fictional books do 0we have please format output as CSV"
```

## CI/CD

This module uses GitHub Actions for continuous integration and delivery. The workflow is defined in `.github/workflows/sample-client.yml` and includes:

- Building and testing the module on every push and pull request to the main branch that affects this module
- Setting up Java 21
- Caching Maven dependencies
- Building the project with Maven
- Running tests

The workflow is triggered automatically when changes are made to the module or its workflow file.
