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

Start the application and access the REST endpoints to interact with the connected MCP servers.
