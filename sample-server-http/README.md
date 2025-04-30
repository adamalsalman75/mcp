# Sample Server HTTP

This module is a Spring Boot application that implements a Model Context Protocol (MCP) server using HTTP and Server-Sent Events (SSE) for communication. It provides a BookService as a tool that can be accessed by MCP clients.

## Features

- Implements an MCP server using HTTP and SSE
- Provides a BookService as a tool that can be used by AI clients
- Uses Server-Sent Events (SSE) for real-time communication with clients

## Endpoints

- `/sse` - SSE endpoint for clients to subscribe to events

## Tools

- `adam-books` - A tool that lists all available books

## Configuration

The server is configured as an MCP server named "webmvc-mcp-server". It's configured to be of type SYNC and has debugging enabled for Spring AI and MCP-related components.

## Usage

Start the application and connect to it using an MCP client, such as the sample-client module.
