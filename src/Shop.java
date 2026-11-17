// Task 5 (Part B - Lesson 2): a shop with an array of Products
// A Shop holds up to 5 Product objects in an array.
// Write your code BELOW each TODO. Replace every ___ with real code.
public class Shop {
    // TODO 1: fields - String name; Product[] items; int count
   

    // TODO 2: Constructor  this.name = name, items = new Product[5], count = 0
    

    // TODO 3: boolean addProduct(Product p)  put p at items[count], count++, return true
    //                                        return false when the shop is full
    //     boolean addProduct(Product p) {
    //         if (count == items.length) {   // no free slot left
    //             return ___;
    //         }
    //         items[count] = p;              // put p in the next free slot
    //         count++;
    //         return ___;
    //     }

    // TODO 4: double totalStockValue()       sum of totalValue() of the first count items
    //     double totalStockValue() {
    //         double sum = 0;
    //         for (int i = 0; i < count; i++) {
    //             sum = sum + items[i].___();
    //         }
    //         return sum;
    //     }

    // TODO 5: Product findByCode(String code) compare with .equals(), return null if not found
    //     Product findByCode(String code) {
    //         for (int i = 0; i < count; i++) {
    //             if (items[i].code.equals(___)) {   // NOT ==
    //                 return items[i];
    //             }
    //         }
    //         return ___;                            // nothing matched
    //     }

    // TODO 6: void printAll()                call printInfo() on each item - loop only to count
    //     Use the same for loop as TODO 4. Inside it, call printInfo() on items[i].
    //     Loop to count, NOT items.length (empty slots are null -> NullPointerException).

    // TODO 7: void printSummary()            prints exactly two lines:
    //                                        Shop: <name> (<count> products)
    //                                        Total stock value: <value> THB
    //     Hint: for <value>, call your own method totalStockValue().
}
