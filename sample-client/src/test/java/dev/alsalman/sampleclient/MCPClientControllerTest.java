package dev.alsalman.sampleclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MCPClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MCPClientService mcpClientService;

    @InjectMocks
    private MCPClientController mcpClientController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mcpClientController).build();
    }

    @Test
    void testGetAvailableTools() throws Exception {
        // Create test data
        List<AvailableTools.Tool> tools = Arrays.asList(
                new AvailableTools.Tool("tool1", "Description 1"),
                new AvailableTools.Tool("tool2", "Description 2")
        );
        AvailableTools availableTools = AvailableTools.of(tools);

        // Configure the mock service
        when(mcpClientService.listAvailableTools()).thenReturn(availableTools);

        // Perform the request and verify the response
        mockMvc.perform(get("/tools"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tools").isArray())
                .andExpect(jsonPath("$.tools.length()").value(2))
                .andExpect(jsonPath("$.tools[0].name").value("tool1"))
                .andExpect(jsonPath("$.tools[0].description").value("Description 1"))
                .andExpect(jsonPath("$.tools[1].name").value("tool2"))
                .andExpect(jsonPath("$.tools[1].description").value("Description 2"))
                .andExpect(jsonPath("$.errorMessage").doesNotExist());
    }

    @Test
    void testGetAvailableToolsError() throws Exception {
        // Create test data
        AvailableTools availableTools = AvailableTools.error("Test error message");

        // Configure the mock service
        when(mcpClientService.listAvailableTools()).thenReturn(availableTools);

        // Perform the request and verify the response
        mockMvc.perform(get("/tools"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tools").doesNotExist())
                .andExpect(jsonPath("$.errorMessage").value("Test error message"));
    }

    @Test
    void testGetBooks() throws Exception {
        // Create test data
        List<BookOfferings.Book> books = Arrays.asList(
                new BookOfferings.Book("Book 1", "Description 1", "type1"),
                new BookOfferings.Book("Book 2", "Description 2", "type2")
        );
        BookOfferings bookOfferings = BookOfferings.of(books);

        // Configure the mock service
        when(mcpClientService.listAllBooks()).thenReturn(bookOfferings);

        // Perform the request and verify the response
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.books.length()").value(2))
                .andExpect(jsonPath("$.books[0].title").value("Book 1"))
                .andExpect(jsonPath("$.books[0].description").value("Description 1"))
                .andExpect(jsonPath("$.books[0].type").value("type1"))
                .andExpect(jsonPath("$.books[1].title").value("Book 2"))
                .andExpect(jsonPath("$.books[1].description").value("Description 2"))
                .andExpect(jsonPath("$.books[1].type").value("type2"))
                .andExpect(jsonPath("$.errorMessage").doesNotExist());
    }

    @Test
    void testGetBooksError() throws Exception {
        // Create test data
        BookOfferings bookOfferings = BookOfferings.error("Test error message");

        // Configure the mock service
        when(mcpClientService.listAllBooks()).thenReturn(bookOfferings);

        // Perform the request and verify the response
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books").doesNotExist())
                .andExpect(jsonPath("$.errorMessage").value("Test error message"));
    }


    @Test
    void testBookQueryWithDefaultQuery() throws Exception {
        // Create test data
        List<BookOfferings.Book> books = Arrays.asList(
                new BookOfferings.Book("Book 1", "Description 1", "type1"),
                new BookOfferings.Book("Book 2", "Description 2", "type2")
        );
        BookOfferings bookOfferings = BookOfferings.of(books);

        // Configure the mock service
        when(mcpClientService.bookQuery("Return all books with a max price of 50.0")).thenReturn(bookOfferings);

        // Perform the request and verify the response
        mockMvc.perform(get("/books/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.books.length()").value(2))
                .andExpect(jsonPath("$.books[0].title").value("Book 1"))
                .andExpect(jsonPath("$.books[0].description").value("Description 1"))
                .andExpect(jsonPath("$.books[0].type").value("type1"))
                .andExpect(jsonPath("$.books[1].title").value("Book 2"))
                .andExpect(jsonPath("$.books[1].description").value("Description 2"))
                .andExpect(jsonPath("$.books[1].type").value("type2"))
                .andExpect(jsonPath("$.errorMessage").doesNotExist());
    }

    @Test
    void testBookQueryWithCustomQuery() throws Exception {
        // Create test data
        List<BookOfferings.Book> books = Arrays.asList(
                new BookOfferings.Book("Custom Book", "Custom Description", "custom-type")
        );
        BookOfferings bookOfferings = BookOfferings.of(books);

        // Configure the mock service
        String customQuery = "Return all books with a max price of 30.0";
        when(mcpClientService.bookQuery(customQuery)).thenReturn(bookOfferings);

        // Perform the request and verify the response
        mockMvc.perform(get("/books/query").param("query", customQuery))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.books.length()").value(1))
                .andExpect(jsonPath("$.books[0].title").value("Custom Book"))
                .andExpect(jsonPath("$.books[0].description").value("Custom Description"))
                .andExpect(jsonPath("$.books[0].type").value("custom-type"))
                .andExpect(jsonPath("$.errorMessage").doesNotExist());
    }

    @Test
    void testBookQueryError() throws Exception {
        // Create test data
        BookOfferings bookOfferings = BookOfferings.error("Test error message");

        // Configure the mock service
        when(mcpClientService.bookQuery("Return all books with a max price of 50.0")).thenReturn(bookOfferings);

        // Perform the request and verify the response
        mockMvc.perform(get("/books/query"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books").doesNotExist())
                .andExpect(jsonPath("$.errorMessage").value("Test error message"));
    }
}
