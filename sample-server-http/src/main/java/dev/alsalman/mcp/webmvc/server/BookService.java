package dev.alsalman.mcp.webmvc.server;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private final List<Book> books = new ArrayList<>();

    @Tool(name="list-all-books", description = "List all books")
    public List<Book> listBooks() {
        log.info("Listing all books");
        return books;
    }

    @Tool(name="find-books-by-category", description = "Find books by category")
    public List<Book> findBooksByCategory(String category) {
        log.info("Finding books by category: {}", category);
        return books.stream()
                .filter(book -> book.categories().contains(category))
                .collect(Collectors.toList());
    }

    @Tool(name="find-books-by-author", description = "Find books by author")
    public List<Book> findBooksByAuthor(String author) {
        log.info("Finding books by author: {}", author);
        return books.stream()
                .filter(book -> book.author().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    @Tool(name="get-book-by-isbn", description = "Get book details by ISBN")
    public Book getBookByIsbn(String isbn) {
        log.info("Getting book by ISBN: {}", isbn);
        return books.stream()
                .filter(book -> book.isbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    @Tool(name="get-book-inventory-report", description = "Get inventory report of books")
    public Map<String, Integer> getBookInventoryReport() {
        log.info("Generating book inventory report");
        return books.stream()
                .collect(Collectors.toMap(
                        Book::title,
                        Book::stockQuantity
                ));
    }

    @Tool(name="get-books-by-rating", description = "Get books with rating above specified value")
    public List<Book> getBooksByRating(double minRating) {
        log.info("Finding books with rating above: {}", minRating);
        return books.stream()
                .filter(book -> book.averageRating() >= minRating)
                .sorted(Comparator.comparing(Book::averageRating).reversed())
                .collect(Collectors.toList());
    }

    @Tool(name="get-books-published-after", description = "Get books published after specified date (format: yyyy-MM-dd)")
    public List<Book> getBooksPublishedAfter(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        log.info("Finding books published after: {}", date);
        return books.stream()
                .filter(book -> book.publicationDate().isAfter(date))
                .sorted(Comparator.comparing(Book::publicationDate).reversed())
                .collect(Collectors.toList());
    }

    @Tool(name="get-category-sales-report", description = "Get sales report by category")
    public Map<String, Double> getCategorySalesReport() {
        log.info("Generating category sales report");
        Map<String, Double> report = new java.util.HashMap<>();

        books.forEach(book -> {
            book.categories().forEach(category -> {
                double sales = book.price() * book.stockQuantity();
                report.put(category, report.getOrDefault(category, 0.0) + sales);
            });
        });

        return report;
    }

    @Tool(name="get-top-rated-books", description = "Get top rated books (limited to specified count)")
    public List<Book> getTopRatedBooks(int count) {
        log.info("Getting top {} rated books", count);
        return books.stream()
                .sorted(Comparator.comparing(Book::averageRating).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Tool(name="get-sales-trend-report", description = "Get sales trend report by publication year")
    public Map<Integer, SalesTrendData> getSalesTrendReport() {
        log.info("Generating sales trend report by publication year");
        Map<Integer, SalesTrendData> report = new java.util.HashMap<>();

        books.forEach(book -> {
            int year = book.publicationDate().getYear();
            double revenue = book.price() * book.stockQuantity();
            int totalBooks = book.stockQuantity();

            if (report.containsKey(year)) {
                SalesTrendData data = report.get(year);
                data.addRevenue(revenue);
                data.addBooks(totalBooks);
                data.addTitle(book.title());
            } else {
                SalesTrendData data = new SalesTrendData(revenue, totalBooks);
                data.addTitle(book.title());
                report.put(year, data);
            }
        });

        return report;
    }

    @Tool(name="get-author-performance-report", description = "Get performance metrics for each author")
    public List<AuthorPerformance> getAuthorPerformanceReport() {
        log.info("Generating author performance report");
        Map<String, AuthorPerformance> authorMap = new java.util.HashMap<>();

        books.forEach(book -> {
            String author = book.author();
            double revenue = book.price() * book.stockQuantity();

            if (authorMap.containsKey(author)) {
                AuthorPerformance performance = authorMap.get(author);
                performance.addBook(book);
                performance.addRevenue(revenue);
            } else {
                AuthorPerformance performance = new AuthorPerformance(author);
                performance.addBook(book);
                performance.addRevenue(revenue);
                authorMap.put(author, performance);
            }
        });

        return new ArrayList<>(authorMap.values());
    }

    @Tool(name="get-book-recommendations", description = "Get book recommendations based on a category and minimum rating")
    public List<Book> getBookRecommendations(String category, double minRating) {
        log.info("Getting book recommendations for category: {} with minimum rating: {}", category, minRating);
        return books.stream()
                .filter(book -> book.categories().contains(category))
                .filter(book -> book.averageRating() >= minRating)
                .sorted(Comparator.comparing(Book::averageRating).reversed())
                .collect(Collectors.toList());
    }

    @Tool(name="get-price-range-analysis", description = "Get analysis of books within a price range")
    public PriceRangeAnalysis getPriceRangeAnalysis(double minPrice, double maxPrice) {
        log.info("Analyzing books in price range: {} to {}", minPrice, maxPrice);

        List<Book> booksInRange = books.stream()
                .filter(book -> book.price() >= minPrice && book.price() <= maxPrice)
                .collect(Collectors.toList());

        double averageRating = booksInRange.stream()
                .mapToDouble(Book::averageRating)
                .average()
                .orElse(0.0);

        double totalRevenue = booksInRange.stream()
                .mapToDouble(book -> book.price() * book.stockQuantity())
                .sum();

        Map<String, Long> categoryDistribution = booksInRange.stream()
                .flatMap(book -> book.categories().stream())
                .collect(Collectors.groupingBy(category -> category, Collectors.counting()));

        return new PriceRangeAnalysis(
            booksInRange.size(),
            averageRating,
            totalRevenue,
            categoryDistribution
        );
    }

    // Inner class for sales trend data
    public static class SalesTrendData {
        private double totalRevenue;
        private int totalBooks;
        private final List<String> titles = new ArrayList<>();

        public SalesTrendData(double totalRevenue, int totalBooks) {
            this.totalRevenue = totalRevenue;
            this.totalBooks = totalBooks;
        }

        public void addRevenue(double revenue) {
            this.totalRevenue += revenue;
        }

        public void addBooks(int books) {
            this.totalBooks += books;
        }

        public void addTitle(String title) {
            this.titles.add(title);
        }

        public double getTotalRevenue() {
            return totalRevenue;
        }

        public int getTotalBooks() {
            return totalBooks;
        }

        public List<String> getTitles() {
            return titles;
        }
    }

    // Inner class for author performance
    public static class AuthorPerformance {
        private final String author;
        private final List<Book> books = new ArrayList<>();
        private double totalRevenue = 0.0;

        public AuthorPerformance(String author) {
            this.author = author;
        }

        public void addBook(Book book) {
            this.books.add(book);
        }

        public void addRevenue(double revenue) {
            this.totalRevenue += revenue;
        }

        public String getAuthor() {
            return author;
        }

        public List<Book> getBooks() {
            return books;
        }

        public int getBookCount() {
            return books.size();
        }

        public double getTotalRevenue() {
            return totalRevenue;
        }

        public double getAverageRating() {
            return books.stream()
                    .mapToDouble(Book::averageRating)
                    .average()
                    .orElse(0.0);
        }
    }

    // Inner class for price range analysis
    public static class PriceRangeAnalysis {
        private final int bookCount;
        private final double averageRating;
        private final double totalRevenue;
        private final Map<String, Long> categoryDistribution;

        public PriceRangeAnalysis(
                int bookCount, 
                double averageRating, 
                double totalRevenue, 
                Map<String, Long> categoryDistribution) {
            this.bookCount = bookCount;
            this.averageRating = averageRating;
            this.totalRevenue = totalRevenue;
            this.categoryDistribution = categoryDistribution;
        }

        public int getBookCount() {
            return bookCount;
        }

        public double getAverageRating() {
            return averageRating;
        }

        public double getTotalRevenue() {
            return totalRevenue;
        }

        public Map<String, Long> getCategoryDistribution() {
            return categoryDistribution;
        }
    }

    // Inner class for reading trend analysis
    public static class ReadingTrendAnalysis {
        private final Map<String, Double> categoryPopularity;
        private final Map<String, Double> authorPopularity;
        private final List<Book> recommendedBooks;
        private final double averagePageCount;
        private final double averagePrice;

        public ReadingTrendAnalysis(
                Map<String, Double> categoryPopularity,
                Map<String, Double> authorPopularity,
                List<Book> recommendedBooks,
                double averagePageCount,
                double averagePrice) {
            this.categoryPopularity = categoryPopularity;
            this.authorPopularity = authorPopularity;
            this.recommendedBooks = recommendedBooks;
            this.averagePageCount = averagePageCount;
            this.averagePrice = averagePrice;
        }

        public Map<String, Double> getCategoryPopularity() {
            return categoryPopularity;
        }

        public Map<String, Double> getAuthorPopularity() {
            return authorPopularity;
        }

        public List<Book> getRecommendedBooks() {
            return recommendedBooks;
        }

        public double getAveragePageCount() {
            return averagePageCount;
        }

        public double getAveragePrice() {
            return averagePrice;
        }
    }

    @Tool(name="analyze-reading-trends", description = "Analyze reading trends and provide personalized recommendations")
    public ReadingTrendAnalysis analyzeReadingTrends(List<String> preferredCategories, double maxPrice) {
        log.info("Analyzing reading trends for categories: {} with max price: {}", preferredCategories, maxPrice);

        // Calculate category popularity based on ratings and number of reviews
        Map<String, Double> categoryPopularity = new java.util.HashMap<>();
        Map<String, Integer> categoryCount = new java.util.HashMap<>();

        books.forEach(book -> {
            book.categories().forEach(category -> {
                double popularityScore = book.averageRating() * book.numberOfReviews();
                categoryPopularity.put(category, 
                    categoryPopularity.getOrDefault(category, 0.0) + popularityScore);
                categoryCount.put(category, 
                    categoryCount.getOrDefault(category, 0) + 1);
            });
        });

        // Normalize category popularity
        categoryPopularity.forEach((category, score) -> {
            int count = categoryCount.getOrDefault(category, 1);
            categoryPopularity.put(category, score / count);
        });

        // Calculate author popularity
        Map<String, Double> authorPopularity = new java.util.HashMap<>();
        Map<String, Integer> authorCount = new java.util.HashMap<>();

        books.forEach(book -> {
            String author = book.author();
            double popularityScore = book.averageRating() * book.numberOfReviews();
            authorPopularity.put(author, 
                authorPopularity.getOrDefault(author, 0.0) + popularityScore);
            authorCount.put(author, 
                authorCount.getOrDefault(author, 0) + 1);
        });

        // Normalize author popularity
        authorPopularity.forEach((author, score) -> {
            int count = authorCount.getOrDefault(author, 1);
            authorPopularity.put(author, score / count);
        });

        // Generate personalized recommendations
        List<Book> recommendedBooks = books.stream()
            .filter(book -> book.price() <= maxPrice)
            .filter(book -> book.categories().stream()
                .anyMatch(preferredCategories::contains))
            .sorted(Comparator.comparing(book -> {
                // Calculate a recommendation score based on multiple factors
                double categoryScore = book.categories().stream()
                    .filter(preferredCategories::contains)
                    .mapToDouble(category -> categoryPopularity.getOrDefault(category, 0.0))
                    .sum();
                double authorScore = authorPopularity.getOrDefault(book.author(), 0.0);
                double ratingScore = book.averageRating() * 10; // Scale up ratings

                return -(categoryScore + authorScore + ratingScore); // Negative for descending order
            }))
            .limit(5)
            .collect(Collectors.toList());

        // Calculate average page count and price
        double averagePageCount = books.stream()
            .mapToInt(Book::pageCount)
            .average()
            .orElse(0.0);

        double averagePrice = books.stream()
            .mapToDouble(Book::price)
            .average()
            .orElse(0.0);

        return new ReadingTrendAnalysis(
            categoryPopularity,
            authorPopularity,
            recommendedBooks,
            averagePageCount,
            averagePrice
        );
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
