package dev.alsalman.mcp.github.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for GitHub API.
 * This test makes real API calls to GitHub and verifies the response structure.
 */
public class GitHubApiTest {

    private static final Logger log = LoggerFactory.getLogger(GitHubApiTest.class);
    private static final String GITHUB_USER = "adamalsalman75";
    private static final String GITHUB_API_URL = "https://api.github.com";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGitHubApiResponse() throws IOException {
        // Create RestClient to call GitHub API
        RestClient restClient = RestClient.builder()
                .baseUrl(GITHUB_API_URL)
                .defaultHeader("Accept", "application/vnd.github.v3+json")
                .build();

        // Make API call to get user data
        String response = restClient.get()
                .uri("/users/{username}", GITHUB_USER)
                .retrieve()
                .body(String.class);

        log.info("GitHub API Response received for user: {}", GITHUB_USER);
        System.out.println("[DEBUG_LOG] GitHub API Response: " + response);

        // Parse response to JSON
        JsonNode jsonNode = objectMapper.readTree(response);

        // Basic response validation
        assertNotNull(response, "GitHub API response should not be null");
        assertTrue(jsonNode.has("login"), "Response should contain user login information");
        assertEquals(GITHUB_USER, jsonNode.get("login").asText(), "Login should match the requested username");

        // Verify all required fields exist
        String[] requiredFields = {"public_repos", "followers", "following", "created_at", "updated_at"};
        for (String field : requiredFields) {
            assertTrue(jsonNode.has(field), "Response should contain field: " + field);
            assertNotNull(jsonNode.get(field), "Field should not be null: " + field);
            System.out.println("[DEBUG_LOG] Field found: " + field + " = " + jsonNode.get(field).asText());
        }

        // Verify numeric fields have valid values
        assertTrue(jsonNode.get("public_repos").isNumber(), "public_repos should be a number");
        assertTrue(jsonNode.get("followers").isNumber(), "followers should be a number");
        assertTrue(jsonNode.get("following").isNumber(), "following should be a number");

        // Verify date fields have valid format
        assertTrue(jsonNode.get("created_at").isTextual(), "created_at should be a string");
        assertTrue(jsonNode.get("updated_at").isTextual(), "updated_at should be a string");

        // Log success
        log.info("GitHub API test completed successfully");
    }
}
