package dev.alsalman.sampleclient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class MCPClientService {

    private final ChatClient client;

    public MCPClientService(ChatClient.Builder builder, ToolCallbackProvider tools) {
        this.client = builder
                .defaultTools(tools)
                .build();
    }

    public AvailableTools listAvailableTools() {
        try {
            return client.prompt("What tools are available").call().entity(AvailableTools.class);
        } catch (Exception e) {
            return AvailableTools.error("Failed to retrieve available tools: " + e.getMessage());
        }
    }

    public GitHubMetricsResponse gitlabMetrics() {
        try {
            return client.prompt("What can you tell me about Adam Al-Salman and github").call().entity(GitHubMetricsResponse.class);
        } catch (Exception e) {
            return GitHubMetricsResponse.error("Failed to retrieve GitHub metrics: " + e.getMessage());
        }
    }

    public BookOfferings listAllBooks() {
        try {
            return client.prompt("List all available books that we offer").call().entity(BookOfferings.class);
        } catch (Exception e) {
            return BookOfferings.error("Failed to retrieve book list: " + e.getMessage());
        }
    }

    public BookOfferings bookQuery(String query) {
        try {
            return client.prompt(query).call().entity(BookOfferings.class);
        } catch (Exception e) {
            return BookOfferings.error("Failed to analyze reading trends: " + e.getMessage());
        }
    }

    public BookOfferings getRecommendedBooks() {
        try {
            return client.prompt("Provide top three book recommendations based on popularity and ratings").call().entity(BookOfferings.class);
        } catch (Exception e) {
            return BookOfferings.error("Failed to get book recommendations: " + e.getMessage());
        }
    }
}
