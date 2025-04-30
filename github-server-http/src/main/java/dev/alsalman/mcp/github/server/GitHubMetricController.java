package dev.alsalman.mcp.github.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for GitHub metrics.
 * Exposes the GitHubMetricService via a REST API.
 */
@RestController
@RequestMapping("/api/github")
public class GitHubMetricController {

    private static final Logger log = LoggerFactory.getLogger(GitHubMetricController.class);
    private final GitHubMetricService gitHubMetricService;

    public GitHubMetricController(GitHubMetricService gitHubMetricService) {
        this.gitHubMetricService = gitHubMetricService;
    }

    /**
     * Get GitHub metrics for the configured user.
     * @return GitHubUserMetrics containing user metrics
     */
    @GetMapping("/metrics")
    public GitHubUserMetrics getMetrics() {
        log.info("REST request for GitHub metrics");
        return gitHubMetricService.getMetrics();
    }
}