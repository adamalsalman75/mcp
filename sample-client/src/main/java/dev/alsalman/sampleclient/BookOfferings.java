package dev.alsalman.sampleclient;

import java.time.LocalDate;
import java.util.List;

/**
 * A record representing book offerings.
 */
public record BookOfferings(
    List<Book> books,
    String errorMessage
) {
    /**
     * Creates a BookOfferings instance with a list of books.
     */
    public static BookOfferings of(List<Book> books) {
        return new BookOfferings(books, null);
    }

    /**
     * Creates a BookOfferings instance for error cases.
     */
    public static BookOfferings error(String errorMessage) {
        return new BookOfferings(null, errorMessage);
    }

    /**
     * Checks if this object represents an error.
     */
    public boolean hasError() {
        return errorMessage != null;
    }

    /**
     * A record representing a book.
     */
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
}
