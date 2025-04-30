package dev.alsalman.sampleclient;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdamOfferingsTest {

    @Test
    void testCreateWithBooks() {
        // Create test data
        List<AdamOfferings.Book> books = Arrays.asList(
                new AdamOfferings.Book("Book 1", "Description 1", "type1"),
                new AdamOfferings.Book("Book 2", "Description 2", "type2")
        );

        // Create AdamOfferings instance
        AdamOfferings adamOfferings = AdamOfferings.of(books);

        // Verify the instance
        assertNotNull(adamOfferings, "AdamOfferings should not be null");
        assertFalse(adamOfferings.hasError(), "Should not have an error");
        assertNull(adamOfferings.errorMessage(), "Error message should be null");
        assertEquals(books, adamOfferings.books(), "Books list should match");
        assertEquals(2, adamOfferings.books().size(), "Should have 2 books");
        assertEquals("Book 1", adamOfferings.books().get(0).title(), "First book title should match");
        assertEquals("Description 1", adamOfferings.books().get(0).description(), "First book description should match");
        assertEquals("type1", adamOfferings.books().get(0).type(), "First book type should match");
    }

    @Test
    void testCreateWithError() {
        // Create AdamOfferings instance with error
        String errorMessage = "Test error message";
        AdamOfferings adamOfferings = AdamOfferings.error(errorMessage);

        // Verify the instance
        assertNotNull(adamOfferings, "AdamOfferings should not be null");
        assertTrue(adamOfferings.hasError(), "Should have an error");
        assertEquals(errorMessage, adamOfferings.errorMessage(), "Error message should match");
        assertNull(adamOfferings.books(), "Books list should be null");
    }

    @Test
    void testBookRecord() {
        // Create a Book instance
        String title = "Test Book";
        String description = "Test description";
        String type = "Test type";
        AdamOfferings.Book book = new AdamOfferings.Book(title, description, type);

        // Verify the instance
        assertEquals(title, book.title(), "Book title should match");
        assertEquals(description, book.description(), "Book description should match");
        assertEquals(type, book.type(), "Book type should match");
    }
}