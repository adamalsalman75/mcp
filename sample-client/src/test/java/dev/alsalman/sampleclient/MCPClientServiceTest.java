package dev.alsalman.sampleclient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MCPClientService.
 * Instead of trying to mock the complex chain of ChatClient calls or extend MCPClientService,
 * we'll directly test the behavior of the response objects that the service methods return.
 */
@ExtendWith(MockitoExtension.class)
public class MCPClientServiceTest {

    @Test
    void testAvailableToolsCreation() {
        // Create test data
        List<AvailableTools.Tool> tools = Arrays.asList(
                new AvailableTools.Tool("tool1", "Description 1"),
                new AvailableTools.Tool("tool2", "Description 2")
        );

        // Create AvailableTools instance
        AvailableTools availableTools = AvailableTools.of(tools);

        // Verify the instance
        assertNotNull(availableTools, "AvailableTools should not be null");
        assertFalse(availableTools.hasError(), "Should not have an error");
        assertNull(availableTools.errorMessage(), "Error message should be null");
        assertEquals(tools, availableTools.tools(), "Tools list should match");
        assertEquals(2, availableTools.tools().size(), "Should have 2 tools");
        assertEquals("tool1", availableTools.tools().get(0).name(), "First tool name should match");
        assertEquals("Description 1", availableTools.tools().get(0).description(), "First tool description should match");
    }

    @Test
    void testAvailableToolsError() {
        // Create AvailableTools instance with error
        String errorMessage = "Test error message";
        AvailableTools availableTools = AvailableTools.error(errorMessage);

        // Verify the instance
        assertNotNull(availableTools, "AvailableTools should not be null");
        assertTrue(availableTools.hasError(), "Should have an error");
        assertEquals(errorMessage, availableTools.errorMessage(), "Error message should match");
        assertNull(availableTools.tools(), "Tools list should be null");
    }

    @Test
    void testGitHubMetricsResponseCreation() {
        // Create test data
        int publicRepositories = 10;
        int followers = 20;
        int following = 5;
        String accountCreatedAt = "2020-01-01T00:00:00Z";
        String accountUpdatedAt = "2023-05-15T12:30:45Z";

        // Create GitHubMetricsResponse instance
        GitHubMetricsResponse response = GitHubMetricsResponse.of(
                publicRepositories,
                followers,
                following,
                accountCreatedAt,
                accountUpdatedAt
        );

        // Verify the instance
        assertNotNull(response, "GitHubMetricsResponse should not be null");
        assertFalse(response.hasError(), "Should not have an error");
        assertNull(response.errorMessage(), "Error message should be null");
        assertEquals(publicRepositories, response.publicRepositories(), "Public repositories count should match");
        assertEquals(followers, response.followers(), "Followers count should match");
        assertEquals(following, response.following(), "Following count should match");
        assertEquals(accountCreatedAt, response.accountCreatedAt(), "Account created date should match");
        assertEquals(accountUpdatedAt, response.accountUpdatedAt(), "Account updated date should match");
    }

    @Test
    void testGitHubMetricsResponseError() {
        // Create GitHubMetricsResponse instance with error
        String errorMessage = "Test error message";
        GitHubMetricsResponse response = GitHubMetricsResponse.error(errorMessage);

        // Verify the instance
        assertNotNull(response, "GitHubMetricsResponse should not be null");
        assertTrue(response.hasError(), "Should have an error");
        assertEquals(errorMessage, response.errorMessage(), "Error message should match");
        assertEquals(0, response.publicRepositories(), "Public repositories count should be 0");
        assertEquals(0, response.followers(), "Followers count should be 0");
        assertEquals(0, response.following(), "Following count should be 0");
        assertNull(response.accountCreatedAt(), "Account created date should be null");
        assertNull(response.accountUpdatedAt(), "Account updated date should be null");
    }

    @Test
    void testBookOfferingsCreation() {
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
    void testBookOfferingsError() {
        // Create BookOfferings instance with error
        String errorMessage = "Test error message";
        BookOfferings bookOfferings = BookOfferings.error(errorMessage);

        // Verify the instance
        assertNotNull(bookOfferings, "BookOfferings should not be null");
        assertTrue(bookOfferings.hasError(), "Should have an error");
        assertEquals(errorMessage, bookOfferings.errorMessage(), "Error message should match");
        assertNull(bookOfferings.books(), "Books list should be null");
    }
}
