package dev.alsalman.sampleclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MCPClientController {

    private final MCPClientService mcpClientService;

    public MCPClientController(MCPClientService mcpClientService) {
        this.mcpClientService = mcpClientService;
    }

    @GetMapping("/tools")
    public String getAvailableTools() {
        return mcpClientService.listAvailableTools();
    }

    @GetMapping("/adam")
    public String getCourses() {
        return mcpClientService.adam();
    }
}