package dev.alsalman.mcp.webmvc.server;

import java.time.LocalDate;
import java.util.List;

public record Book(
    String title, 
    String description, 
    String type,
    String author,
    LocalDate publicationDate,
    String publisher,
    String isbn,
    int pageCount,
    List<String> categories,
    double price,
    int stockQuantity,
    double averageRating,
    int numberOfReviews
) {
    // Constructor with only required fields, setting defaults for others
    public Book(String title, String description, String type) {
        this(
            title, 
            description, 
            type, 
            "Unknown Author", 
            LocalDate.now(), 
            "Unknown Publisher", 
            "Unknown ISBN", 
            0, 
            List.of(), 
            0.0, 
            0, 
            0.0, 
            0
        );
    }
}
