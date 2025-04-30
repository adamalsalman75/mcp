package dev.alsalman.mcp.github.server;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubMetricService {

    private static final Logger log = LoggerFactory.getLogger(GitHubMetricService.class);
    private final List<GitHubMetric> metrics = new ArrayList<>();
    private final RestClient restClient;
    private static final String GITHUB_USER = "adamalsalman75";
    private static final String GITHUB_API_URL = "https://api.github.com";

    public GitHubMetricService() {
        this.restClient = RestClient.builder()
                .baseUrl(GITHUB_API_URL)
                .defaultHeader("Accept", "application/vnd.github.v3+json")
                .build();
    }

    @Tool(name="github-metrics", description = "Get GitHub metrics for adamalsalman75 (Adam Al-Salman)")
    public List<GitHubMetric> getMetrics() {
        log.info("Getting GitHub metrics for {}", GITHUB_USER);
        return metrics;
    }

    @PostConstruct
    void init() {
        fetchUserData();
    }

    private void fetchUserData() {
        log.info("Fetching GitHub user data for {}", GITHUB_USER);

        try {
            UserData userData = restClient.get()
                    .uri("/users/{username}", GITHUB_USER)
                    .retrieve()
                    .body(UserData.class);

            metrics.clear();
            metrics.add(new GitHubMetric("Public Repositories", String.valueOf(userData.publicRepos()), "count"));
            metrics.add(new GitHubMetric("Followers", String.valueOf(userData.followers()), "count"));
            metrics.add(new GitHubMetric("Following", String.valueOf(userData.following()), "count"));
            metrics.add(new GitHubMetric("Account Created", userData.createdAt(), "date"));
            metrics.add(new GitHubMetric("Account Updated", userData.updatedAt(), "date"));
            log.info("GitHub metrics updated: {}", metrics);
        } catch (Exception error) {
            log.error("Error fetching GitHub user data", error);
            metrics.add(new GitHubMetric("Error", "Failed to fetch GitHub data", "error"));
        }
    }

    // Simple data record to map GitHub API response
    private static record UserData(
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
