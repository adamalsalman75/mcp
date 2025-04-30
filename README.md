# Model Context Protocol (MCP) Sample Project

This project demonstrates the use of the Model Context Protocol (MCP) for building AI-powered applications. It consists of four modules that showcase different ways of implementing and interacting with MCP servers.

## Modules

### [Sample Client](./sample-client)

A Spring Boot application that uses Spring AI to interact with MCP servers. It provides a client interface for accessing tools and services exposed by MCP servers.

### [Sample Server HTTP](./sample-server-http)

A Spring Boot application that implements an MCP server using HTTP and Server-Sent Events (SSE) for communication. It provides a BookService as a tool that can be used by AI clients.

### [Sample Server STDIO](./sample-server-stdio)

A Spring Boot application that implements an MCP server using standard I/O (STDIO) for communication. It provides a CourseService as a tool that can be used by AI clients.

### [GitHub Server HTTP](./github-server-http)

A Spring Boot application that implements an MCP server using HTTP and Server-Sent Events (SSE) for communication. It fetches metrics from GitHub for a specific user and provides them as a tool that can be used by AI clients.

## Architecture

The project demonstrates a client-server architecture using the Model Context Protocol:

1. The **Sample Client** connects to MCP servers and provides REST endpoints for users to access the functionality of those servers.
2. The **Sample Server HTTP** implements an MCP server using HTTP and SSE, providing a BookService tool.
3. The **Sample Server STDIO** implements an MCP server using standard I/O, providing a CourseService tool.
4. The **GitHub Server HTTP** implements an MCP server using HTTP and SSE, providing GitHub metrics as a tool.

By default, the Sample Client is configured to connect to the Sample Server STDIO module.

## Getting Started

1. Build all modules using Maven
2. Start any of the server modules (Sample Server HTTP, Sample Server STDIO, or GitHub Server HTTP)
3. Start the Sample Client module
4. Access the Sample Client's REST endpoints to interact with the connected MCP servers

## Technologies Used

- Spring Boot
- Spring AI
- Model Context Protocol (MCP)
- Server-Sent Events (SSE)
