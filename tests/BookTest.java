// Task 2 - Book (Part A). Do not edit.
// Objects are made with new Book() and fields are set with the dot.
public class BookTest {
    static Book javaBasics() {
        Book b = new Book();
        b.isbn = "978-0001";
        b.title = "Java Basics";
        b.author = "Suda";
        b.year = 2024;
        b.available = true;
        return b;
    }

    public static void main(String[] args) {
        System.out.println("=== Task 2: Book ===");

        Check.test("fields have the right names and types", () -> {
            String[] names = {"isbn", "title", "author", "year", "available"};
            Class<?>[] types = {String.class, String.class, String.class, int.class, boolean.class};
            for (int i = 0; i < names.length; i++) {
                String d = Check.fieldType(Book.class, names[i], types[i]);
                if (d != null) {
                    return d;
                }
            }
            return null;
        });

        Check.test("printInfo() output", () -> {
            Book b = javaBasics();
            String out = Check.captureOut(() -> b.printInfo());
            return Check.sameLines(new String[] {"Java Basics by Suda (2024)"}, out);
        });

        Check.test("isAvailable() returns the available field", () -> {
            Book b = new Book();
            String d = Check.same(true, b.isAvailable());
            if (d != null) {
                return "a new Book() should be available (available = true): " + d;
            }
            b.available = false;
            d = Check.same(false, b.isAvailable());
            return d == null ? null : "after available = false: " + d;
        });

        Check.test("borrow() sets available to false", () -> {
            Book b = javaBasics();
            b.borrow();
            String d = Check.same(false, b.available);
            if (d != null) {
                return d;
            }
            b.borrow();
            d = Check.same(false, b.available);
            return d == null ? null : "after borrow() two times: " + d;
        });

        Check.test("returnBook() sets available to true", () -> {
            Book b = javaBasics();
            b.available = false;
            b.returnBook();
            return Check.same(true, b.available);
        });

        Check.test("two books keep their own data", () -> {
            Book b1 = javaBasics();
            Book b2 = new Book();
            b2.isbn = "978-0002";
            b2.title = "OOP Fun";
            b2.author = "Anan";
            b2.year = 2023;
            b2.available = true;
            b1.borrow();
            String d = Check.same(true, b2.available);
            if (d != null) {
                return "b1.borrow() changed b2: b2.available " + d;
            }
            String out = Check.captureOut(() -> b2.printInfo());
            return Check.sameLines(new String[] {"OOP Fun by Anan (2023)"}, out);
        });

        Check.finish();
    }
}
