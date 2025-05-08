package dev.alsalman.sampleclient;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookOfferingsTest {

    @Test
    void testCreateWithBooks() {
        // Create test data
        List<BookOfferings.Book> books = Arrays.asList(
                new BookOfferings.Book("Book 1", "Description 1", "type1"),
                new BookOfferings.Book("Book 2", "Description 2", "type2")
        );

        // Create BookOfferings instance
        BookOfferings bookOfferings = BookOfferings.of(books);

        // Verify the instance
        assertNotNull(bookOfferings, "BookOfferings should not be null");
        assertFalse(bookOfferings.hasError(), "Should not have an error");
        assertNull(bookOfferings.errorMessage(), "Error message should be null");
        assertEquals(books, bookOfferings.books(), "Books list should match");
        assertEquals(2, bookOfferings.books().size(), "Should have 2 books");
        assertEquals("Book 1", bookOfferings.books().get(0).title(), "First book title should match");
        assertEquals("Description 1", bookOfferings.books().get(0).description(), "First book description should match");
        assertEquals("type1", bookOfferings.books().get(0).type(), "First book type should match");
    }

    @Test
    void testCreateWithError() {
        // Create BookOfferings instance with error
        String errorMessage = "Test error message";
        BookOfferings bookOfferings = BookOfferings.error(errorMessage);

        // Verify the instance
        assertNotNull(bookOfferings, "BookOfferings should not be null");
        assertTrue(bookOfferings.hasError(), "Should have an error");
        assertEquals(errorMessage, bookOfferings.errorMessage(), "Error message should match");
        assertNull(bookOfferings.books(), "Books list should be null");
    }

    @Test
    void testBookRecord() {
        // Create a Book instance
        String title = "Test Book";
        String description = "Test description";
        String type = "Test type";
        BookOfferings.Book book = new BookOfferings.Book(title, description, type);

        // Verify the instance
        assertEquals(title, book.title(), "Book title should match");
        assertEquals(description, book.description(), "Book description should match");
        assertEquals(type, book.type(), "Book type should match");
    }
}
