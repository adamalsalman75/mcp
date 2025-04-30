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

    public AdamOfferings adam() {
        try {
            return client.prompt("Which does Adam offer").call().entity(AdamOfferings.class);
        } catch (Exception e) {
            return AdamOfferings.error("Failed to retrieve Adam's offerings: " + e.getMessage());
        }
    }
}
