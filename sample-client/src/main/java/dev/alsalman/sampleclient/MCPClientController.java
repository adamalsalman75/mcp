package dev.alsalman.sampleclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MCPClientController {

    private final MCPClientService mcpClientService;

    public MCPClientController(MCPClientService mcpClientService) {
        this.mcpClientService = mcpClientService;
    }

    @GetMapping("/tools")
    public AvailableTools getAvailableTools() {
        return mcpClientService.listAvailableTools();
    }

    @GetMapping("/books")
    public BookOfferings getAllBooks() {
        return mcpClientService.listAllBooks();
    }

    @GetMapping("/books/query")
    public BookOfferings query(@RequestParam(value = "query") String query) {
        return mcpClientService.bookQuery(query);
    }

}
