# Sample Client

This module is a Spring Boot application that uses Spring AI to interact with Model Context Protocol (MCP) servers. It provides a client interface for accessing tools and services exposed by MCP servers.

## Features

- Connects to MCP servers using the Spring AI framework
- Provides REST endpoints for accessing server functionality
- Configured to interact with the sample-server-stdio module

## Endpoints

- `/client/tools` - Returns a list of available tools from connected MCP servers
- `/client/courses` - Returns a list of courses from connected MCP servers

## Configuration

The client is configured to connect to MCP servers defined in the `mcp-servers.json` file. By default, it connects to the sample-server-stdio module.

## Usage

Start the application and access the REST endpoints to interact with the connected MCP servers.
