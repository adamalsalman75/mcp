package dev.alsalman.mcp.stdio;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SampleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleServerApplication.class, args);
    }

    @Bean
    public List<ToolCallback> getTools(CourseService courseService) {
        return List.of(ToolCallbacks.from(courseService));
    }
}
