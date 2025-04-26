package dev.alsalman.sampleclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class MCPClientController {

    private final MCPClientService mcpClientService;

    public MCPClientController(MCPClientService mcpClientService) {
        this.mcpClientService = mcpClientService;
    }

    @GetMapping("/tools")
    public String getAvailableTools() {
        return mcpClientService.listAvailableTools();
    }

    @GetMapping("/courses")
    public String getCourses() {
        return mcpClientService.listCourses();
    }
}