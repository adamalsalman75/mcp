package dev.alsalman.mcp.github.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;

public class GitHubMetricServiceTest {

    /**
     * Test subclass of GitHubMetricService that overrides the fetchUserData method
     * to provide test data instead of making real API calls.
     */
    static class TestGitHubMetricService extends GitHubMetricService {
        private final boolean simulateError;

        public TestGitHubMetricService(boolean simulateError) {
            super(RestClient.builder()); // The RestClient won't be used
            this.simulateError = simulateError;
        }

        @Override
        protected void fetchUserData() {
            if (simulateError) {
                // Simulate an error
                userMetrics = GitHubUserMetrics.error("Failed to fetch GitHub data");
            } else {
                // Provide test data
                userMetrics = GitHubUserMetrics.of(
                    10,  // publicRepositories
                    20,  // followers
                    5,   // following
                    "2020-01-01T00:00:00Z",  // accountCreatedAt
                    "2023-05-15T12:30:45Z"   // accountUpdatedAt
                );
            }
        }
    }

    private TestGitHubMetricService gitHubMetricService;

    @BeforeEach
    void setUp() {
        // Create the test service with no error
        gitHubMetricService = new TestGitHubMetricService(false);
        // Initialize the service (this would normally happen automatically via @PostConstruct)
        gitHubMetricService.init();
    }

    @Test
    void testGetMetricsSuccess() {
        // Get the metrics from the service
        GitHubUserMetrics metrics = gitHubMetricService.getMetrics();

        // Print metrics for debugging
        System.out.println("[DEBUG_LOG] GitHub metrics: " + metrics);

        // Verify that metrics object is not null
        assertNotNull(metrics, "Metrics should not be null");

        // Verify that there is no error
        assertFalse(metrics.hasError(), "Metrics should not have an error");

        // Verify the exact values from our test data
        assertEquals(10, metrics.publicRepositories(), "Public repositories count should match test data");
        assertEquals(20, metrics.followers(), "Followers count should match test data");
        assertEquals(5, metrics.following(), "Following count should match test data");
        assertEquals("2020-01-01T00:00:00Z", metrics.accountCreatedAt(), "Account created date should match test data");
        assertEquals("2023-05-15T12:30:45Z", metrics.accountUpdatedAt(), "Account updated date should match test data");
    }

    @Test
    void testGetMetricsError() {
        // Create a test service that simulates an error
        TestGitHubMetricService errorService = new TestGitHubMetricService(true);
        errorService.init();

        // Get the metrics from the service
        GitHubUserMetrics metrics = errorService.getMetrics();

        // Print metrics for debugging
        System.out.println("[DEBUG_LOG] GitHub metrics with error: " + metrics);

        // Verify that metrics object is not null
        assertNotNull(metrics, "Metrics should not be null");

        // Verify that there is an error
        assertTrue(metrics.hasError(), "Metrics should have an error");
        assertEquals("Failed to fetch GitHub data", metrics.errorMessage(), "Error message should match expected");
    }
}
