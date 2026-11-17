// Task 3 - Product (Part A). Do not edit.
// Objects are made with new Product() and fields are set with the dot.
public class ProductTest {
    static Product notebook() {
        Product p = new Product();
        p.code = "P001";
        p.name = "Notebook";
        p.price = 59.5;
        p.stock = 20;
        p.category = "Stationery";
        return p;
    }

    public static void main(String[] args) {
        System.out.println("=== Task 3: Product ===");

        Check.test("fields have the right names and types", () -> {
            String[] names = {"code", "name", "price", "stock", "category"};
            Class<?>[] types = {String.class, String.class, double.class, int.class, String.class};
            for (int i = 0; i < names.length; i++) {
                String d = Check.fieldType(Product.class, names[i], types[i]);
                if (d != null) {
                    return d;
                }
            }
            return null;
        });

        Check.test("printInfo() output", () -> {
            Product p = notebook();
            String out = Check.captureOut(() -> p.printInfo());
            return Check.sameLines(new String[] {"P001 Notebook - 59.5 THB (stock: 20)"}, out);
        });

        Check.test("isInStock() is true only when stock > 0", () -> {
            Product p = notebook();
            String d = Check.same(true, p.isInStock());
            if (d != null) {
                return "stock 20: " + d;
            }
            p.stock = 0;
            d = Check.same(false, p.isInStock());
            return d == null ? null : "stock 0: " + d;
        });

        Check.test("sell(quantity) works only if 0 < quantity <= stock", () -> {
            Product p = notebook();
            p.sell(5);
            String d = Check.same(15, p.stock);
            if (d != null) {
                return "stock 20, sell(5): " + d;
            }
            p.sell(0);
            d = Check.same(15, p.stock);
            if (d != null) {
                return "sell(0) must not change stock: " + d;
            }
            p.sell(-3);
            d = Check.same(15, p.stock);
            if (d != null) {
                return "sell(-3) must not change stock: " + d;
            }
            p.sell(16);
            d = Check.same(15, p.stock);
            if (d != null) {
                return "stock 15, sell(16) must not change stock: " + d;
            }
            p.sell(15);
            d = Check.same(0, p.stock);
            return d == null ? null : "stock 15, sell(15): " + d;
        });

        Check.test("totalValue() returns price * stock", () -> {
            Product p = notebook();
            String d = Check.same(1190.0, p.totalValue());
            if (d != null) {
                return "59.5 x 20: " + d;
            }
            p.stock = 0;
            d = Check.same(0.0, p.totalValue());
            return d == null ? null : "stock 0: " + d;
        });

        Check.test("two products keep their own data", () -> {
            Product p1 = notebook();
            Product p2 = new Product();
            p2.code = "P002";
            p2.name = "Pen";
            p2.price = 15.0;
            p2.stock = 50;
            p2.category = "Stationery";
            p1.sell(10);
            String d = Check.same(50, p2.stock);
            if (d != null) {
                return "p1.sell(10) changed p2: p2.stock " + d;
            }
            String out = Check.captureOut(() -> p2.printInfo());
            return Check.sameLines(new String[] {"P002 Pen - 15.0 THB (stock: 50)"}, out);
        });

        Check.finish();
    }
}
