package dev.alsalman.sampleclient;

import java.util.List;

/**
 * A record representing what Adam offers.
 */
public record AdamOfferings(
    List<Book> books,
    String errorMessage
) {
    /**
     * Creates an AdamOfferings instance with a list of books.
     */
    public static AdamOfferings of(List<Book> books) {
        return new AdamOfferings(books, null);
    }

    /**
     * Creates an AdamOfferings instance for error cases.
     */
    public static AdamOfferings error(String errorMessage) {
        return new AdamOfferings(null, errorMessage);
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
        String type
    ) {}
}