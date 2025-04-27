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

    public String listAvailableTools() {
        return client.prompt("What tools are available").call().content();
    }

    public String adam() {
        return client.prompt("Which does Adam offer").call().content();
    }
}