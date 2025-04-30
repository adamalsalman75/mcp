package dev.alsalman.sampleclient;

import java.util.List;

/**
 * A record representing available tools.
 */
public record AvailableTools(
    List<Tool> tools,
    String errorMessage
) {
    /**
     * Creates an AvailableTools instance with a list of tools.
     */
    public static AvailableTools of(List<Tool> tools) {
        return new AvailableTools(tools, null);
    }

    /**
     * Creates an AvailableTools instance for error cases.
     */
    public static AvailableTools error(String errorMessage) {
        return new AvailableTools(null, errorMessage);
    }

    /**
     * Checks if this object represents an error.
     */
    public boolean hasError() {
        return errorMessage != null;
    }

    /**
     * A record representing a tool.
     */
    public record Tool(
        String name,
        String description
    ) {}
}