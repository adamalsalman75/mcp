package dev.alsalman.mcp.github.server;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import static org.junit.jupiter.api.Assertions.*;

public class GitHubApiTest {

    private static final String GITHUB_USER = "adamalsalman75";
    private static final String GITHUB_API_URL = "https://api.github.com";

    @Test
    void testGitHubApiResponse() {
        RestClient restClient = RestClient.builder()
                .baseUrl(GITHUB_API_URL)
                .defaultHeader("Accept", "application/vnd.github.v3+json")
                .build();

        String response = restClient.get()
                .uri("/users/{username}", GITHUB_USER)
                .retrieve()
                .body(String.class);

        System.out.println("[DEBUG_LOG] GitHub API Response: " + response);

        // Add an assertion to make sure we can see the output
        assertNotNull(response, "GitHub API response should not be null");
        assertTrue(response.contains("login"), "Response should contain user login information");

        // Print field names we're interested in
        if (response.contains("public_repos")) {
            System.out.println("[DEBUG_LOG] Field found: public_repos");
        } else {
            System.out.println("[DEBUG_LOG] Field NOT found: public_repos");
        }

        if (response.contains("created_at")) {
            System.out.println("[DEBUG_LOG] Field found: created_at");
        } else {
            System.out.println("[DEBUG_LOG] Field NOT found: created_at");
        }

        if (response.contains("updated_at")) {
            System.out.println("[DEBUG_LOG] Field found: updated_at");
        } else {
            System.out.println("[DEBUG_LOG] Field NOT found: updated_at");
        }

        if (response.contains("followers")) {
            System.out.println("[DEBUG_LOG] Field found: followers");
        } else {
            System.out.println("[DEBUG_LOG] Field NOT found: followers");
        }

        if (response.contains("following")) {
            System.out.println("[DEBUG_LOG] Field found: following");
        } else {
            System.out.println("[DEBUG_LOG] Field NOT found: following");
        }
    }
}
