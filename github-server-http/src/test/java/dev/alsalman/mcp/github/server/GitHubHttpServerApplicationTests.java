package dev.alsalman.mcp.github.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the GitHub HTTP server application.
 * Tests the application context, controller endpoints, and service functionality.
 */
@SpringBootTest
@AutoConfigureMockMvc
class GitHubHttpServerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GitHubMetricService gitHubMetricService;

    @Autowired
    private GitHubMetricController gitHubMetricController;

    @Test
    void contextLoads() {
        // Verify that the application context loads successfully
        assert gitHubMetricService != null : "GitHubMetricService should be loaded";
        assert gitHubMetricController != null : "GitHubMetricController should be loaded";
    }

    @Test
    void testGetMetricsEndpoint() throws Exception {
        // Test the /api/github/metrics endpoint
        mockMvc.perform(get("/api/github/metrics")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.publicRepositories", isA(Number.class)))
                .andExpect(jsonPath("$.followers", isA(Number.class)))
                .andExpect(jsonPath("$.following", isA(Number.class)))
                .andExpect(jsonPath("$.accountCreatedAt", isA(String.class)))
                .andExpect(jsonPath("$.accountUpdatedAt", isA(String.class)));
    }

    @Test
    void testServiceReturnsValidMetrics() {
        // Test that the service returns valid metrics
        GitHubUserMetrics metrics = gitHubMetricService.getMetrics();

        // Verify that metrics object is not null
        assert metrics != null : "Metrics should not be null";

        if (!metrics.hasError()) {
            // Verify that metrics have valid values
            assert metrics.publicRepositories() >= 0 : "Public repositories count should be non-negative";
            assert metrics.followers() >= 0 : "Followers count should be non-negative";
            assert metrics.following() >= 0 : "Following count should be non-negative";
        } else {
            // If there's an error, verify the error message is not empty
            assert metrics.errorMessage() != null && !metrics.errorMessage().isEmpty() : 
                "Error message should not be null or empty";
        }
    }
}
