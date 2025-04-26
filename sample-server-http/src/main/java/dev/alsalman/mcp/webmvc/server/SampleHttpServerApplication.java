package dev.alsalman.mcp.webmvc.server;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SampleHttpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleHttpServerApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider getBooks(BookService bookService) {
        return MethodToolCallbackProvider.builder().toolObjects(bookService).build();
    }
}
