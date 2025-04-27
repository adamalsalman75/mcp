# Sample Client

This module is a Spring Boot application that uses Spring AI to interact with Model Context Protocol (MCP) servers. It provides a client interface for accessing tools and services exposed by MCP servers.

## Features

- Connects to MCP servers using the Spring AI framework
- Provides REST endpoints for accessing server functionality
- Configured to interact with the sample-server-stdio module

## Endpoints

- `/tools` - Returns a list of available tools from connected MCP servers
- `/adam` - Returns a list of what Adam has to offer from MCP servers

## Configuration
The client is configured to connect to MCP servers defined in `application.yaml`
The client is currently using SSE MCP server endpoint.
The STDIO configuration `mcp-servers.json` file which isn't currently being used.

## Usage

Start the application and access the REST endpoints to interact with the connected MCP servers.
