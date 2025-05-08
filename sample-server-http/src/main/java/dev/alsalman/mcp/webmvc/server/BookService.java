package dev.alsalman.mcp.webmvc.server;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private final List<Book> books = new ArrayList<>();

    @Tool(name = "list-all-books", description = "List all books")
    public List<Book> listBooks() {
        log.info("Listing all books");
        return books;
    }

    @PostConstruct
    void init() {
        books.addAll(List.of(
                new Book(
                        "Art of Java",
                        "Comprehensive guide to Java programming",
                        "Programming",
                        "James Gosling",
                        LocalDate.of(2019, 5, 15),
                        "Tech Publishing",
                        "978-1234567890",
                        450,
                        List.of("Programming", "Java", "Software Development"),
                        49.99,
                        120,
                        4.7,
                        230
                ),
                new Book(
                        "Art of MCP",
                        "Learn how to use Model Context Protocol",
                        "Programming",
                        "Adam Al-Salman",
                        LocalDate.of(2023, 2, 10),
                        "Spring Publications",
                        "978-0987654321",
                        320,
                        List.of("Programming", "Spring", "MCP", "API"),
                        39.99,
                        85,
                        4.9,
                        42
                ),
                new Book(
                        "Data Science Fundamentals",
                        "Introduction to data science concepts and techniques",
                        "Data Science",
                        "Sarah Johnson",
                        LocalDate.of(2021, 8, 22),
                        "Data Press",
                        "978-5678901234",
                        380,
                        List.of("Data Science", "Machine Learning", "Statistics"),
                        54.99,
                        95,
                        4.5,
                        178
                ),
                new Book(
                        "Cloud Architecture Patterns",
                        "Best practices for cloud-based applications",
                        "Cloud Computing",
                        "Michael Chen",
                        LocalDate.of(2022, 3, 17),
                        "Cloud Books",
                        "978-2345678901",
                        290,
                        List.of("Cloud Computing", "Architecture", "DevOps"),
                        44.99,
                        65,
                        4.3,
                        112
                ),
                new Book(
                        "Agile Project Management",
                        "Guide to implementing agile methodologies",
                        "Project Management",
                        "Emily Davis",
                        LocalDate.of(2020, 11, 5),
                        "Agile Press",
                        "978-3456789012",
                        275,
                        List.of("Project Management", "Agile", "Scrum"),
                        34.99,
                        110,
                        4.6,
                        195
                ),
                new Book(
                        "Cybersecurity Essentials",
                        "Fundamentals of protecting digital assets",
                        "Security",
                        "Robert Smith",
                        LocalDate.of(2022, 9, 30),
                        "Security Publications",
                        "978-4567890123",
                        410,
                        List.of("Cybersecurity", "Network Security", "Ethical Hacking"),
                        59.99,
                        75,
                        4.8,
                        88
                ),
                new Book(
                        "Blockchain Revolution",
                        "How blockchain technology is changing business",
                        "Blockchain",
                        "Lisa Wong",
                        LocalDate.of(2021, 4, 12),
                        "Crypto Books",
                        "978-5678901234",
                        340,
                        List.of("Blockchain", "Cryptocurrency", "Fintech"),
                        49.99,
                        60,
                        4.2,
                        105
                ),
                new Book(
                        "UX Design Principles",
                        "Creating effective user experiences",
                        "Design",
                        "David Miller",
                        LocalDate.of(2020, 7, 8),
                        "Design House",
                        "978-6789012345",
                        295,
                        List.of("UX Design", "UI", "Web Design"),
                        39.99,
                        90,
                        4.4,
                        152
                ),
                new Book(
                        "Artificial Intelligence Ethics",
                        "Ethical considerations in AI development",
                        "AI",
                        "Priya Patel",
                        LocalDate.of(2023, 1, 20),
                        "AI Publications",
                        "978-7890123456",
                        320,
                        List.of("AI", "Ethics", "Technology"),
                        44.99,
                        70,
                        4.7,
                        65
                ),
                new Book(
                        "DevOps Handbook",
                        "Implementing DevOps in your organization",
                        "DevOps",
                        "Thomas Anderson",
                        LocalDate.of(2021, 10, 15),
                        "Tech Operations",
                        "978-8901234567",
                        375,
                        List.of("DevOps", "CI/CD", "Automation"),
                        52.99,
                        85,
                        4.6,
                        130
                )
        ));
    }
}
