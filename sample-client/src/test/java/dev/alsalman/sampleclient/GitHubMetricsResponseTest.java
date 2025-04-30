package dev.alsalman.sampleclient;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GitHubMetricsResponseTest {

    @Test
    void testCreateWithMetrics() {
        // Create test data
        int publicRepositories = 10;
        int followers = 20;
        int following = 5;
        String accountCreatedAt = "2020-01-01T00:00:00Z";
        String accountUpdatedAt = "2023-05-15T12:30:45Z";

        // Create GitHubMetricsResponse instance
        GitHubMetricsResponse response = GitHubMetricsResponse.of(
                publicRepositories,
                followers,
                following,
                accountCreatedAt,
                accountUpdatedAt
        );

        // Verify the instance
        assertNotNull(response, "GitHubMetricsResponse should not be null");
        assertFalse(response.hasError(), "Should not have an error");
        assertNull(response.errorMessage(), "Error message should be null");
        assertEquals(publicRepositories, response.publicRepositories(), "Public repositories count should match");
        assertEquals(followers, response.followers(), "Followers count should match");
        assertEquals(following, response.following(), "Following count should match");
        assertEquals(accountCreatedAt, response.accountCreatedAt(), "Account created date should match");
        assertEquals(accountUpdatedAt, response.accountUpdatedAt(), "Account updated date should match");
    }

    @Test
    void testCreateWithError() {
        // Create GitHubMetricsResponse instance with error
        String errorMessage = "Test error message";
        GitHubMetricsResponse response = GitHubMetricsResponse.error(errorMessage);

        // Verify the instance
        assertNotNull(response, "GitHubMetricsResponse should not be null");
        assertTrue(response.hasError(), "Should have an error");
        assertEquals(errorMessage, response.errorMessage(), "Error message should match");
        assertEquals(0, response.publicRepositories(), "Public repositories count should be 0");
        assertEquals(0, response.followers(), "Followers count should be 0");
        assertEquals(0, response.following(), "Following count should be 0");
        assertNull(response.accountCreatedAt(), "Account created date should be null");
        assertNull(response.accountUpdatedAt(), "Account updated date should be null");
    }
}