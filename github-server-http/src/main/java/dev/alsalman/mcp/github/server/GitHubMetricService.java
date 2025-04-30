package dev.alsalman.mcp.github.server;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GitHubMetricService {

    private static final Logger log = LoggerFactory.getLogger(GitHubMetricService.class);
    protected GitHubUserMetrics userMetrics;
    private final RestClient restClient;
    private static final String GITHUB_USER = "adamalsalman75";
    private static final String GITHUB_API_URL = "https://api.github.com";

    /**
     * Constructor that accepts a RestClient.Builder for dependency injection.
     * This allows for easier testing with mocked RestClient.
     * 
     * @param restClientBuilder the RestClient.Builder to use for creating the RestClient
     */
    public GitHubMetricService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl(GITHUB_API_URL)
                .defaultHeader("Accept", "application/vnd.github.v3+json")
                .build();
    }

    @Tool(name="github-metrics", description = "Get GitHub metrics for adamalsalman75 (Adam Al-Salman)")
    public GitHubUserMetrics getMetrics() {
        log.info("Getting GitHub metrics for {}", GITHUB_USER);
        return userMetrics;
    }

    @PostConstruct
    public void init() {
        fetchUserData();
    }

    protected void fetchUserData() {
        log.info("Fetching GitHub user data for {}", GITHUB_USER);

        try {
            UserData userData = restClient.get()
                    .uri("/users/{username}", GITHUB_USER)
                    .retrieve()
                    .body(UserData.class);

            userMetrics = GitHubUserMetrics.of(
                userData.publicRepos(),
                userData.followers(),
                userData.following(),
                userData.createdAt(),
                userData.updatedAt()
            );
            log.info("GitHub metrics updated: {}", userMetrics);
        } catch (Exception error) {
            log.error("Error fetching GitHub user data", error);
            userMetrics = GitHubUserMetrics.error("Failed to fetch GitHub data");
        }
    }

    // Simple data record to map GitHub API response
    private record UserData(
            int public_repos,  // GitHub API uses snake_case
            int followers,
            int following,
            String created_at, // GitHub API uses snake_case
            String updated_at  // GitHub API uses snake_case
    ) {
        // Accessor methods to maintain compatibility with existing code
        public int publicRepos() {
            return public_repos;
        }

        public String createdAt() {
            return created_at;
        }

        public String updatedAt() {
            return updated_at;
        }
    }
}
