package dev.alsalman.sampleclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
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
    void testGetCourses() throws Exception {
        // Create test data
        List<AdamOfferings.Book> books = Arrays.asList(
                new AdamOfferings.Book("Book 1", "Description 1", "type1"),
                new AdamOfferings.Book("Book 2", "Description 2", "type2")
        );
        AdamOfferings adamOfferings = AdamOfferings.of(books);

        // Configure the mock service
        when(mcpClientService.adam()).thenReturn(adamOfferings);

        // Perform the request and verify the response
        mockMvc.perform(get("/adam"))
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
    void testGetCoursesError() throws Exception {
        // Create test data
        AdamOfferings adamOfferings = AdamOfferings.error("Test error message");

        // Configure the mock service
        when(mcpClientService.adam()).thenReturn(adamOfferings);

        // Perform the request and verify the response
        mockMvc.perform(get("/adam"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books").doesNotExist())
                .andExpect(jsonPath("$.errorMessage").value("Test error message"));
    }

    @Test
    void testGetGithubMetrics() throws Exception {
        // Create test data
        GitHubMetricsResponse response = GitHubMetricsResponse.of(
                10, 20, 5, "2020-01-01T00:00:00Z", "2023-05-15T12:30:45Z"
        );

        // Configure the mock service
        when(mcpClientService.gitlabMetrics()).thenReturn(response);

        // Perform the request and verify the response
        mockMvc.perform(get("/github"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicRepositories").value(10))
                .andExpect(jsonPath("$.followers").value(20))
                .andExpect(jsonPath("$.following").value(5))
                .andExpect(jsonPath("$.accountCreatedAt").value("2020-01-01T00:00:00Z"))
                .andExpect(jsonPath("$.accountUpdatedAt").value("2023-05-15T12:30:45Z"))
                .andExpect(jsonPath("$.errorMessage").doesNotExist());
    }

    @Test
    void testGetGithubMetricsError() throws Exception {
        // Create test data
        GitHubMetricsResponse response = GitHubMetricsResponse.error("Test error message");

        // Configure the mock service
        when(mcpClientService.gitlabMetrics()).thenReturn(response);

        // Perform the request and verify the response
        mockMvc.perform(get("/github"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicRepositories").value(0))
                .andExpect(jsonPath("$.followers").value(0))
                .andExpect(jsonPath("$.following").value(0))
                .andExpect(jsonPath("$.accountCreatedAt").doesNotExist())
                .andExpect(jsonPath("$.accountUpdatedAt").doesNotExist())
                .andExpect(jsonPath("$.errorMessage").value("Test error message"));
    }
}
