package dev.alsalman.mcp.webmvc.server;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Date;

@RestController
public class BookSseController {

    private final SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

    @GetMapping(value = "/mcp/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getBookEvents() {
        return emitter;
    }

    @GetMapping("/trigger-event")
    public String triggerEvent() {
        try {
            emitter.send(SseEmitter.event()
                    .name("test-event")
                    .data("This is a test event at " + new Date()));
            return "Event sent";
        } catch (IOException e) {
            return "Error sending event: " + e.getMessage();
        }
    }

    // Method to send events to clients
    public void sendBookEvent(Book book) {
        try {
            emitter.send(SseEmitter.event()
                    .name("book-event")
                    .data(book));
        } catch (IOException e) {
            // Handle exceptions
        }
    }
}