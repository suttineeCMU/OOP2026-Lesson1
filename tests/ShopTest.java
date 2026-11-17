// Task 5 - Shop (Part B). Do not edit.
// Products are made with new Product() and the dot, so this test needs only Task 3.
public class ShopTest {
    static Product product(String code, String name, double price, int stock) {
        Product p = new Product();
        p.code = code;
        p.name = name;
        p.price = price;
        p.stock = stock;
        p.category = "General";
        return p;
    }

    static Shop campusStore() {
        Shop s = new Shop("Campus Store");
        s.addProduct(product("P001", "Notebook", 59.5, 20));
        s.addProduct(product("P002", "Pen", 15.0, 50));
        s.addProduct(product("P003", "Bag", 250.0, 2));
        return s;
    }

    public static void main(String[] args) {
        System.out.println("=== Task 5: Shop ===");

        Check.test("Shop(name) sets name, items = new Product[5], count = 0", () -> {
            Shop s = new Shop("Campus Store");
            String d = Check.same("Campus Store", s.name);
            if (d != null) return "name " + d;
            if (s.items == null) return "items is null - create the array in the constructor";
            d = Check.same(5, s.items.length);
            if (d != null) return "items.length " + d;
            d = Check.same(0, s.count);
            return d == null ? null : "count " + d;
        });

        Check.test("addProduct() returns true 5 times, then false when full", () -> {
            Shop s = new Shop("Small");
            for (int i = 1; i <= 5; i++) {
                String d = Check.same(true, s.addProduct(product("P00" + i, "Item" + i, 10.0, 1)));
                if (d != null) return "addProduct() call " + i + ": " + d;
            }
            String d = Check.same(false, s.addProduct(product("P006", "Extra", 10.0, 1)));
            if (d != null) return "addProduct() call 6 (shop is full): " + d;
            d = Check.same(5, s.count);
            if (d != null) return "count after 6 calls " + d;
            if (s.items[0] == null || !"P001".equals(s.items[0].code)) {
                return "the first product should be in items[0]";
            }
            return null;
        });

        Check.test("totalStockValue() adds totalValue() of the first count items", () -> {
            String d = Check.same(0.0, new Shop("Empty").totalStockValue());
            if (d != null) return "empty shop: " + d;
            d = Check.same(2440.0, campusStore().totalStockValue());
            return d == null ? null : "Campus Store: " + d;
        });

        Check.test("findByCode() uses .equals() and returns null if not found", () -> {
            Shop s = campusStore();
            Product p = s.findByCode(new String("P002"));
            if (p == null) return "findByCode(\"P002\") returned null - compare Strings with .equals(), not ==";
            String d = Check.same("Pen", p.name);
            if (d != null) return "findByCode(\"P002\").name " + d;
            Product none = s.findByCode("P999");
            return none == null ? null : "findByCode(\"P999\") should return null";
        });

        Check.test("printAll() prints each product and stops at count", () -> {
            Shop s = new Shop("Two Items");
            s.addProduct(product("P001", "Notebook", 59.5, 20));
            s.addProduct(product("P002", "Pen", 15.0, 50));
            String out;
            try {
                out = Check.captureOut(() -> s.printAll());
            } catch (NullPointerException e) {
                return "threw NullPointerException - loop only to count, not items.length";
            }
            return Check.sameLines(new String[] {
                "P001 Notebook - 59.5 THB (stock: 20)",
                "P002 Pen - 15.0 THB (stock: 50)"
            }, out);
        });

        Check.test("printSummary() prints exactly 2 lines", () -> {
            Shop s = campusStore();
            String out = Check.captureOut(() -> s.printSummary());
            return Check.sameLines(new String[] {
                "Shop: Campus Store (3 products)",
                "Total stock value: 2440.0 THB"
            }, out);
        });

        Check.finish();
    }
}
