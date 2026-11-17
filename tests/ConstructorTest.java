// Task 4 - Constructors (Part B). Do not edit.
// Constructors are found with reflection, so this test compiles
// even when a constructor is missing, and it can tell you which one.
public class ConstructorTest {
    static final Class<?> S = String.class;

    static String book(Book b, String isbn, String title, String author, int year) {
        String d = Check.same(isbn, b.isbn);
        if (d != null) return "isbn " + d;
        d = Check.same(title, b.title);
        if (d != null) return "title " + d;
        d = Check.same(author, b.author);
        if (d != null) return "author " + d;
        d = Check.same(year, b.year);
        if (d != null) return "year " + d;
        d = Check.same(true, b.available);
        return d == null ? null : "available " + d;
    }

    static String product(Product p, String code, String name, double price, int stock, String category) {
        String d = Check.same(code, p.code);
        if (d != null) return "code " + d;
        d = Check.same(name, p.name);
        if (d != null) return "name " + d;
        d = Check.same(price, p.price);
        if (d != null) return "price " + d;
        d = Check.same(stock, p.stock);
        if (d != null) return "stock " + d;
        d = Check.same(category, p.category);
        return d == null ? null : "category " + d;
    }

    static String usesThis(String file) {
        int n = Check.count(Check.sourceOf(file), "\\bthis\\s*\\(");
        return n >= 2 ? null : "found this( " + n + " time(s), need at least 2";
    }

    public static void main(String[] args) {
        System.out.println("=== Task 4: Constructors ===");

        Check.group("Book");
        Check.test("Book(isbn, title, author, year)", () -> {
            Book b = Check.create(Book.class, new Class<?>[] {S, S, S, int.class},
                    "978-0001", "Java Basics", "Suda", 2024);
            return book(b, "978-0001", "Java Basics", "Suda", 2024);
        });
        Check.test("Book(title, author, year) - isbn is \"N/A\"", () -> {
            Book b = Check.create(Book.class, new Class<?>[] {S, S, int.class}, "OOP Fun", "Anan", 2023);
            return book(b, "N/A", "OOP Fun", "Anan", 2023);
        });
        Check.test("Book() - \"N/A\", \"Untitled\", \"Unknown\", 0", () -> {
            Book b = Check.create(Book.class, new Class<?>[] {});
            return book(b, "N/A", "Untitled", "Unknown", 0);
        });
        Check.test("src/Book.java uses this(...) at least 2 times", () -> usesThis("src/Book.java"));

        Check.group("Product");
        Check.test("Product(code, name, price, stock, category)", () -> {
            Product p = Check.create(Product.class, new Class<?>[] {S, S, double.class, int.class, S},
                    "P001", "Notebook", 59.5, 20, "Stationery");
            return product(p, "P001", "Notebook", 59.5, 20, "Stationery");
        });
        Check.test("Product(code, name, price) - stock 0, \"General\"", () -> {
            Product p = Check.create(Product.class, new Class<?>[] {S, S, double.class}, "P002", "Pen", 15.0);
            return product(p, "P002", "Pen", 15.0, 0, "General");
        });
        Check.test("Product() - \"P000\", \"Unnamed\", 0.0", () -> {
            Product p = Check.create(Product.class, new Class<?>[] {});
            return product(p, "P000", "Unnamed", 0.0, 0, "General");
        });
        Check.test("src/Product.java uses this(...) at least 2 times", () -> usesThis("src/Product.java"));

        Check.finish();
    }
}
