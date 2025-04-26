package dev.alsalman.mcp.webmvc.server;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private final List<Book> books = new ArrayList<>();

    @Tool(name="adam-books",description = "list all books Adam offers")
    public List<Book> listBooks() {
        log.info("Listing all courses");
        return books;
    }

    @PostConstruct
    void init() {
        books.addAll(
                List.of(
                    new Book("Art of Java", "Art of Java"),
                    new Book("Art of MCP", "Learn how to use MCP")
                )
        );
    }

}
