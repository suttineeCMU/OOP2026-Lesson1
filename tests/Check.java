// Helper for all tests and scripts. Do not edit.
public class Check {
    // One check. Return null when it passes, or a short reason when it fails.
    interface Body {
        String run() throws Exception;
    }

    static int checks = 0;
    static int passed = 0;
    static int groups = 0;
    static int groupsDone = 0;
    static boolean groupOk = true;
    static String unit = "classes";

    static void check(String name, boolean ok, String detail) {
        checks++;
        if (ok) {
            passed++;
            System.out.println("[x] " + name);
        } else {
            groupOk = false;
            System.out.println("[ ] " + name + (detail == null ? "" : " - " + detail));
        }
    }

    // Runs one check. An exception is a failed check, not a crash.
    static void test(String name, Body body) {
        String detail;
        try {
            detail = body.run();
        } catch (Throwable e) {
            if (e instanceof java.lang.reflect.InvocationTargetException && e.getCause() != null) {
                e = e.getCause();
            }
            detail = e.getClass().getName().startsWith("java.lang.RuntimeException")
                    ? e.getMessage()
                    : "threw " + e.getClass().getSimpleName() + (e.getMessage() == null ? "" : ": " + e.getMessage());
        }
        check(name, detail == null, detail);
    }

    // Starts a group of checks (for example one class). finish() then counts groups.
    static void group(String name) {
        endGroup();
        groups++;
        groupOk = true;
        System.out.println("--- " + name + " ---");
    }

    static void endGroup() {
        if (groups > 0 && groupOk) {
            groupsDone++;
        }
        groupOk = false;
    }

    static boolean near(double a, double b) {
        return Math.abs(a - b) < 0.001;
    }

    // Returns null when equal, or "expected X, got Y".
    static String same(Object expected, Object got) {
        boolean ok;
        if (expected instanceof Double && got instanceof Double) {
            ok = near((Double) expected, (Double) got);
        } else {
            ok = expected == null ? got == null : expected.equals(got);
        }
        return ok ? null : "expected " + show(expected) + ", got " + show(got);
    }

    static String show(Object o) {
        return o instanceof String ? "\"" + o + "\"" : String.valueOf(o);
    }

    // Returns null when the field exists with this type.
    static String fieldType(Class<?> c, String name, Class<?> type) {
        try {
            Class<?> got = c.getDeclaredField(name).getType();
            return got == type ? null
                    : "field " + name + " should be " + type.getSimpleName() + ", not " + got.getSimpleName();
        } catch (NoSuchFieldException e) {
            return "field " + name + " not found";
        }
    }

    // Calls a constructor by its parameter types (so a test compiles even when it is missing).
    static <T> T create(Class<T> c, Class<?>[] types, Object... args) throws Exception {
        java.lang.reflect.Constructor<T> k;
        try {
            k = c.getDeclaredConstructor(types);
        } catch (NoSuchMethodException e) {
            String list = "";
            for (int i = 0; i < types.length; i++) {
                list += (i > 0 ? ", " : "") + types[i].getSimpleName();
            }
            throw new RuntimeException("constructor " + c.getSimpleName() + "(" + list + ") not found");
        }
        k.setAccessible(true);
        return k.newInstance(args);
    }

    // Runs code and returns what it printed. System.out is always restored.
    static String captureOut(Runnable code) {
        java.io.PrintStream original = System.out;
        java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(buffer, true));
        try {
            code.run();
        } finally {
            System.out.flush();
            System.setOut(original);
        }
        return buffer.toString().replace("\r\n", "\n");
    }

    // Trimmed lines, without empty lines at the end.
    static String[] lines(String text) {
        String[] raw = text.replace("\r\n", "\n").split("\n", -1);
        int n = raw.length;
        while (n > 0 && raw[n - 1].trim().isEmpty()) {
            n--;
        }
        String[] result = new String[n];
        for (int i = 0; i < n; i++) {
            result[i] = raw[i].trim();
        }
        return result;
    }

    // Returns null when the output has exactly these lines.
    static String sameLines(String[] expected, String output) {
        String[] got = lines(output);
        for (int i = 0; i < expected.length; i++) {
            if (i >= got.length) {
                return "line " + (i + 1) + ": expected \"" + expected[i] + "\", got nothing";
            }
            if (!expected[i].equals(got[i])) {
                return "line " + (i + 1) + ": expected \"" + expected[i] + "\", got \"" + got[i] + "\"";
            }
        }
        if (got.length > expected.length) {
            return "extra line " + (expected.length + 1) + ": \"" + got[expected.length] + "\"";
        }
        return null;
    }

    // Source code of a file without comments.
    static String sourceOf(String path) {
        try {
            String text = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path)), "UTF-8");
            text = text.replaceAll("(?s)/\\*.*?\\*/", " ");
            text = text.replaceAll("//[^\r\n]*", " ");
            return text;
        } catch (java.io.IOException e) {
            return "";
        }
    }

    static int count(String text, String regex) {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(regex).matcher(text);
        int n = 0;
        while (m.find()) {
            n++;
        }
        return n;
    }

    // Prints the task status as the last line. Exit code 0 only for DONE.
    static void finish() {
        if (groups > 0) {
            endGroup();
        }
        System.out.println();
        if (checks > 0 && passed == checks) {
            System.out.println("DONE");
            System.exit(0);
        }
        if (groups > 0) {
            System.out.println("INCOMPLETE (" + groupsDone + "/" + groups + " " + unit + ")");
        } else {
            System.out.println("INCOMPLETE (" + passed + "/" + checks + " checks)");
        }
        System.exit(1);
    }

    // Used by the scripts:
    //   java Check --started <regex> <file...>     exit 0 if any file (without comments) matches
    //   java Check --compare <outputFile> <line...> compare program output line by line
    public static void main(String[] args) throws Exception {
        if (args.length >= 2 && args[0].equals("--started")) {
            for (int i = 2; i < args.length; i++) {
                if (count(sourceOf(args[i]), args[1]) > 0) {
                    System.exit(0);
                }
            }
            System.exit(1);
        }
        if (args.length >= 2 && args[0].equals("--compare")) {
            String output = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(args[1])), "UTF-8");
            String[] got = lines(output);
            int n = args.length - 2;
            for (int i = 0; i < n; i++) {
                String want = args[i + 2];
                String have = i < got.length ? got[i] : null;
                check("line " + (i + 1) + ": " + want, want.equals(have),
                        "expected \"" + want + "\", got " + (have == null ? "nothing" : "\"" + have + "\""));
            }
            check("no extra lines", got.length <= n,
                    got.length > n ? "got extra line \"" + got[n] + "\"" : null);
            finish();
        }
        System.out.println("Usage: java Check --started <regex> <file...> | --compare <file> <line...>");
        System.exit(2);
    }
}
