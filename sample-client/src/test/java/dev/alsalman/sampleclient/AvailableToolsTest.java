package dev.alsalman.sampleclient;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AvailableToolsTest {

    @Test
    void testCreateWithTools() {
        // Create test data
        List<AvailableTools.Tool> tools = Arrays.asList(
                new AvailableTools.Tool("tool1", "Description 1"),
                new AvailableTools.Tool("tool2", "Description 2")
        );

        // Create AvailableTools instance
        AvailableTools availableTools = AvailableTools.of(tools);

        // Verify the instance
        assertNotNull(availableTools, "AvailableTools should not be null");
        assertFalse(availableTools.hasError(), "Should not have an error");
        assertNull(availableTools.errorMessage(), "Error message should be null");
        assertEquals(tools, availableTools.tools(), "Tools list should match");
        assertEquals(2, availableTools.tools().size(), "Should have 2 tools");
        assertEquals("tool1", availableTools.tools().get(0).name(), "First tool name should match");
        assertEquals("Description 1", availableTools.tools().get(0).description(), "First tool description should match");
    }

    @Test
    void testCreateWithError() {
        // Create AvailableTools instance with error
        String errorMessage = "Test error message";
        AvailableTools availableTools = AvailableTools.error(errorMessage);

        // Verify the instance
        assertNotNull(availableTools, "AvailableTools should not be null");
        assertTrue(availableTools.hasError(), "Should have an error");
        assertEquals(errorMessage, availableTools.errorMessage(), "Error message should match");
        assertNull(availableTools.tools(), "Tools list should be null");
    }

    @Test
    void testToolRecord() {
        // Create a Tool instance
        String name = "testTool";
        String description = "Test description";
        AvailableTools.Tool tool = new AvailableTools.Tool(name, description);

        // Verify the instance
        assertEquals(name, tool.name(), "Tool name should match");
        assertEquals(description, tool.description(), "Tool description should match");
    }
}