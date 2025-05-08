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
            return client.prompt().user("What tools are available").call().entity(AvailableTools.class);
        } catch (Exception e) {
            return AvailableTools.error("Failed to retrieve available tools: " + e.getMessage());
        }
    }

    public BookOfferings listAllBooks() {
        try {
            return client.prompt().user("List all available books that we offer").call().entity(BookOfferings.class);
        } catch (Exception e) {
            return BookOfferings.error("Failed to retrieve book list: " + e.getMessage());
        }
    }

    public BookOfferings bookQuery(String query) {
        try {
            return client
                    .prompt()
                    .system("""
                You are a knowledgeable book assistant with access to our book catalog.
                If the query is not about books then please respond with an error message.
                """)
                    .user(query)
                    .call()
                    .entity(BookOfferings.class);
        } catch (Exception e) {
            return BookOfferings.error(e.getMessage());
        }
    }

}
