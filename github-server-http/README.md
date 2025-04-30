# GitHub Server HTTP

This module is a Spring Boot application that implements a Model Context Protocol (MCP) server using HTTP and Server-Sent Events (SSE) for communication. It fetches metrics from GitHub for a specific user and provides them as a tool that can be accessed by MCP clients.

## Features

- Implements an MCP server using HTTP and SSE
- Fetches metrics from GitHub for a specific user
- Provides a GitHubMetricService as a tool that can be used by AI clients
- Uses Server-Sent Events (SSE) for real-time communication with clients

## Endpoints

- `/sse` - SSE endpoint for clients to subscribe to events

## Tools

- `github-metrics` - A tool that provides GitHub metrics for a specific user

## Configuration

The server is configured as an MCP server named "github-mcp-server". It's configured to be of type SYNC and has debugging enabled for Spring AI and MCP-related components.

## Usage

Start the application and connect to it using an MCP client, such as the sample-client module.

## GitHub Metrics

The following GitHub metrics are fetched:
- Public Repositories
- Followers
- Following
- Account Created
- Account Updated

## CI/CD

This module uses GitHub Actions for continuous integration and delivery. The workflow is defined in `.github/workflows/github-server-http.yml` and includes:

- Building and testing the module on every push and pull request to the main branch that affects this module
- Setting up Java 21
- Caching Maven dependencies
- Building the project with Maven
- Running tests

The workflow is triggered automatically when changes are made to the module or its workflow file.
