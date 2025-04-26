package dev.alsalman.mcp.stdio;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseService.class);
    private final List<Course> courses = new ArrayList<>();

    @Tool(name="adam-courses",description = "list all courses Adam offers")
    public List<Course> listCourses() {
        log.info("Listing all courses");
        return courses;
    }

    @PostConstruct
    void init() {
        courses.addAll(
                List.of(
                    new Course("Java 101", "Learn the basics of Java"),
                    new Course("Spring Boot 101", "Learn how to use Spring Boot")
                )
        );
    }

}
