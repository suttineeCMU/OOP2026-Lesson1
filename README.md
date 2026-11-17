# Lab — Design Two Classes + Shop

In the paper exercise you designed classes on paper. Now you write them in Java.

- **Part A = Lesson 1** (Task 1–3): classes and objects. **No constructors yet** — create objects with `new Book()` and set fields with the dot.
- **Part B = Lesson 2** (Task 4–6): add constructors, then use an array of objects.

Finish Part A (all `DONE`) before you start Part B.

## How to work

1. Do the tasks **one at a time, in order**.
2. After each change, run the checklist:

   ```bash
   ./scripts/run-all.sh
   ```

3. Commit and push often:

   ```bash
   git add src
   git commit -m "Add Book fields"
   git push
   ```

4. On GitHub, open the **Actions** tab to see the same checklist. The number in GitHub Classroom (for example `4/6`) is **how many tasks are DONE**, not a score.

**Do not edit `tests/`, `scripts/` or `.github/`.** Write code only in `src/`.

Do not use `static` (except in `main`), `private`, `final`, `package`, `import`, `extends`, `ArrayList` or `Scanner`. We have not learned them yet.

### Compile and run by hand

```bash
javac -d out src/*.java      # compile all files into out/
java -cp out Main            # run the class Main
```

`out/` and `.class` files are in `.gitignore`. Do not commit them.

### Run one check

```bash
./scripts/check-hello.sh
./scripts/run-test.sh BookTest Book
./scripts/run-test.sh ProductTest Product
./scripts/run-test.sh ConstructorTest Book Product
./scripts/run-test.sh ShopTest Product Shop
./scripts/check-main.sh
```

On Windows, use **Git Bash**. If you see `Permission denied`, put `bash` in front, for example `bash scripts/run-all.sh`.

### Read the result

Each check prints one line. `[x]` means it passes. `[ ]` means it fails, and the reason comes after it:

```
[x] printInfo() output
[ ] borrow() sets available to false - expected false, got true
```

The last line is the task status:

| Status | Meaning |
| --- | --- |
| `DONE` | All checks pass. |
| `INCOMPLETE (3/6 checks)` | You started, but some checks fail or the code does not compile. Read the `[ ]` lines. |
| `NOT STARTED` | The file is still the same as the starter. |

`run-all.sh` ends with a list like this:

```
Task 1  Hello ......... DONE
Task 2  Book .......... DONE
Task 3  Product ....... INCOMPLETE (5/6 checks)
Task 4  Constructors .. NOT STARTED
...
Done 2 of 6 tasks
```

## Task list

Tick a box when the task is `DONE`.

- [ ] Task 1 — Hello
- [ ] Task 2 — Book
- [ ] Task 3 — Product
- [ ] Task 4 — Constructors
- [ ] Task 5 — Shop
- [ ] Task 6 — Main

---

## Part A — Lesson 1: Classes & Objects

### Task 1 — Hello (`src/Hello.java`)

- Write `public class Hello` with a `main` method that prints exactly `Hello, Java!`
- Compile and run it by hand:

  ```bash
  javac -d out src/Hello.java
  java -cp out Hello
  ```

- **DONE when:** it compiles and the output is exactly `Hello, Java!`

### Task 2 — Book (`src/Book.java`) and Task 3 — Product (`src/Product.java`)

Write the two classes from the paper exercise. Use these **exact** names and types.

**Do not write a constructor in Part A.** The only starting value you may give a field is `available = true`.
The tests create objects with `new Book()` / `new Product()` and set the fields with the dot.

| Class | Fields | Methods | `printInfo()` prints exactly |
| --- | --- | --- | --- |
| `Book` | `String isbn, title, author`; `int year`; `boolean available = true` | `void printInfo()`<br>`boolean isAvailable()`<br>`void borrow()` — available becomes `false`<br>`void returnBook()` — available becomes `true` | `Java Basics by Suda (2024)` |
| `Product` | `String code, name`; `double price`; `int stock`; `String category` | `void printInfo()`<br>`boolean isInStock()` — `true` when stock > 0<br>`void sell(int quantity)` — only if 0 < quantity ≤ stock<br>`double totalValue()` — price × stock | `P001 Notebook - 59.5 THB (stock: 20)` |

Example:

```java
Book b = new Book();
b.title = "Java Basics";
b.author = "Suda";
b.year = 2024;
b.printInfo();   // Java Basics by Suda (2024)
```

**DONE when** all 6 checks of the class pass:

1. It compiles, and all fields have the right names and types
2. `printInfo()` output is exact
3. – 5. The other 3 methods work (also the edge cases: `borrow()` two times, `sell()` with too many or with 0 or less)
6. Two objects keep their own data (`b1.borrow()` does not change `b2`)

---

## Part B — Lesson 2: Constructors & Arrays of Objects

### Task 4 — Constructors (`src/Book.java`, `src/Product.java`)

Add 3 constructors to each class.

- The full constructor uses `this.field = field`.
- The short constructor and the no-argument constructor **must** call `this(...)` on the first line. Do not copy the assignments again.

| Class | Full constructor | Short constructor → defaults | No-arg constructor |
| --- | --- | --- | --- |
| `Book` | `Book(String isbn, String title, String author, int year)` — available is `true` | `Book(String title, String author, int year)` → isbn `"N/A"` | `Book()` → `this("N/A", "Untitled", "Unknown", 0)` |
| `Product` | `Product(String code, String name, double price, int stock, String category)` | `Product(String code, String name, double price)` → stock `0`, category `"General"` | `Product()` → `this("P000", "Unnamed", 0.0)` |

> **Checkpoint:** Add the full constructor to `Book` first, then run `./scripts/run-all.sh`. Task 2 goes from DONE to INCOMPLETE (BookTest does not compile). Why? Add the no-arg constructor and run again.

**DONE when** both classes pass: full, short and no-arg constructors set the right values, and the file has `this(` at least 2 times. If not, the status tells you how many classes are done, for example `INCOMPLETE (1/2 classes)`.

### Task 5 — Shop with an array of Products (`src/Shop.java`)

- Fields: `String name`; `Product[] items`; `int count`
- `Shop(String name)` → `this.name = name`, `items = new Product[5]`, `count = 0`
- `boolean addProduct(Product p)` → put `p` at `items[count]`, `count++`, return `true`. Return `false` when the shop is full.
- `double totalStockValue()` → the sum of `totalValue()` of the first `count` items
- `Product findByCode(String code)` → compare with `.equals()`. Return `null` if not found.
- `void printAll()` → call `printInfo()` on each item. Loop only to `count` (no `NullPointerException`).
- `void printSummary()` → prints exactly two lines:

  ```
  Shop: <name> (<count> products)
  Total stock value: <value> THB
  ```

**DONE when** all 6 checks pass (the constructor + 5 methods).

### Task 6 — Main (`src/Main.java`)

- Create every object **with constructors** (no dot assignment):
  - `new Book("978-0001", "Java Basics", "Suda", 2024)`
  - Shop `"Campus Store"` with 3 products (any category):
    - P001 Notebook, price 59.5, stock 20
    - P002 Pen, price 15.0, stock 50
    - P003 Bag, price 250.0, stock 2
- Print the book's `printInfo()`, then P001's `printInfo()`, then the shop's `printSummary()`.

**DONE when** the output is exactly:

```
Java Basics by Suda (2024)
P001 Notebook - 59.5 THB (stock: 20)
Shop: Campus Store (3 products)
Total stock value: 2440.0 THB
```
