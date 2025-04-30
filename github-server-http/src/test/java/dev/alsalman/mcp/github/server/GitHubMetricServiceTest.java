package dev.alsalman.mcp.github.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GitHubMetricServiceTest {

    @Autowired
    private GitHubMetricService gitHubMetricService;

    @Test
    void testGetMetrics() {
        List<GitHubMetric> metrics = gitHubMetricService.getMetrics();

        // Print metrics for debugging
        System.out.println("[DEBUG_LOG] GitHub metrics: " + metrics);

        // Verify that metrics are not empty
        assertFalse(metrics.isEmpty(), "Metrics should not be empty");

        // Verify that numeric metrics have non-zero values (except followers and following which can be zero)
        for (GitHubMetric metric : metrics) {
            if ("count".equals(metric.type())) {
                if (!metric.name().equals("Followers") && !metric.name().equals("Following")) {
                    assertNotEquals("0", metric.value(), "Count metric should not be zero: " + metric.name());
                } else {
                    // For followers and following, we just verify they are valid integers
                    assertNotNull(metric.value(), "Followers/Following metric should not be null: " + metric.name());
                    assertTrue(metric.value().matches("\\d+"), "Followers/Following metric should be a valid integer: " + metric.name());
                }
            }
            if ("date".equals(metric.type())) {
                assertNotNull(metric.value(), "Date metric should not be null: " + metric.name());
                assertFalse(metric.value().isEmpty(), "Date metric should not be empty: " + metric.name());
            }
        }
    }
}
