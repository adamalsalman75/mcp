package dev.alsalman.sampleclient;

/**
 * A record representing GitHub metrics.
 */
public record GitHubMetricsResponse(
    int publicRepositories,
    int followers,
    int following,
    String accountCreatedAt,
    String accountUpdatedAt,
    String errorMessage
) {
    /**
     * Creates a GitHubMetricsResponse instance with all metrics.
     */
    public static GitHubMetricsResponse of(
            int publicRepositories,
            int followers,
            int following,
            String accountCreatedAt,
            String accountUpdatedAt
    ) {
        return new GitHubMetricsResponse(
                publicRepositories,
                followers,
                following,
                accountCreatedAt,
                accountUpdatedAt,
                null
        );
    }

    /**
     * Creates a GitHubMetricsResponse instance for error cases.
     */
    public static GitHubMetricsResponse error(String errorMessage) {
        return new GitHubMetricsResponse(0, 0, 0, null, null, errorMessage);
    }

    /**
     * Checks if this metrics object represents an error.
     */
    public boolean hasError() {
        return errorMessage != null;
    }
}