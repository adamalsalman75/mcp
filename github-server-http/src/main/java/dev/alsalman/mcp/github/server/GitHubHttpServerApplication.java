package dev.alsalman.mcp.github.server;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GitHubHttpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitHubHttpServerApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider getGitHubMetrics(GitHubMetricService gitHubMetricService) {
        return MethodToolCallbackProvider.builder().toolObjects(gitHubMetricService).build();
    }
}