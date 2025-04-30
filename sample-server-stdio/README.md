# Sample Server STDIO

This module is a Spring Boot application that implements a Model Context Protocol (MCP) server using standard I/O (STDIO) for communication. It provides a CourseService as a tool that can be accessed by MCP clients.

## Features

- Implements an MCP server using standard I/O
- Provides a CourseService as a tool that can be used by AI clients
- Runs without a web application (no HTTP endpoints)

## Tools

- `adam-courses` - A tool that lists all available courses

## Configuration

The server is configured as an MCP server named "adam-mcp-server" with version 0.0.1. It's configured to run without a web application (spring.main.web-application-type=none), which means it uses standard I/O for communication instead of HTTP.

## Usage

The server is typically launched by an MCP client, such as the sample-client module, which executes the JAR file and communicates with it via standard I/O.

## CI/CD

This module uses GitHub Actions for continuous integration and delivery. The workflow is defined in `.github/workflows/sample-server-stdio.yml` and includes:

- Building and testing the module on every push and pull request to the main branch that affects this module
- Setting up Java 21
- Caching Maven dependencies
- Building the project with Maven
- Running tests

The workflow is triggered automatically when changes are made to the module or its workflow file.
