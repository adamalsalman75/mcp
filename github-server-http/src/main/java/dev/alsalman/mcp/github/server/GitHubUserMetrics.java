package dev.alsalman.mcp.github.server;

/**
 * A record representing GitHub user metrics.
 * This provides a structured representation of GitHub user data
 * instead of using a generic list of metrics.
 */
public record GitHubUserMetrics(
    int publicRepositories,
    int followers,
    int following,
    String accountCreatedAt,
    String accountUpdatedAt,
    String errorMessage
) {
    /**
     * Creates a GitHubUserMetrics instance with all metrics.
     */
    public static GitHubUserMetrics of(
            int publicRepositories,
            int followers,
            int following,
            String accountCreatedAt,
            String accountUpdatedAt
    ) {
        return new GitHubUserMetrics(
                publicRepositories,
                followers,
                following,
                accountCreatedAt,
                accountUpdatedAt,
                null
        );
    }

    /**
     * Creates a GitHubUserMetrics instance for error cases.
     */
    public static GitHubUserMetrics error(String errorMessage) {
        return new GitHubUserMetrics(0, 0, 0, null, null, errorMessage);
    }

    /**
     * Checks if this metrics object represents an error.
     */
    public boolean hasError() {
        return errorMessage != null;
    }
}