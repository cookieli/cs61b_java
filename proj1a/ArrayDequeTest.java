public class ArrayDequeTest {
    public static boolean checkEmpty(boolean expected, boolean actual) {
        if (expected != actual) {
            System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }
    /* Utility method for printing out empty checks. */
    public static boolean checkSize(int expected, int actual) {
        if (expected != actual) {
            System.out.println("size() returned " + actual + ", but expected: " + expected);
            return false;
        }
        return true;
    }
    public static void printTestStatus(boolean passed) {
        if (passed) {
            System.out.println("Test passed!\n");
        } else {
            System.out.println("Test failed!\n");
        }
    }
    public static void addIsEmptySizeTest() {
        System.out.println("Running add/isEmpty/Size test");
        ArrayDeque<String> ad1 = new ArrayDeque<>();
        boolean passed = checkEmpty(true, ad1.isEmpty());
        ad1.addFirst("front");
        passed = checkSize(1, ad1.size());
        passed = checkEmpty(false, ad1.isEmpty()) && passed;
        ad1.addLast("middle");
        passed = checkSize(2, ad1.size()) && passed;
        ad1.addLast("back");
        passed = checkSize(3, ad1.size()) && passed;
        System.out.println("Printing out deque");
        ad1.printDeque();

        printTestStatus(passed);
    }
    public static void addRemoveTest() {
        System.out.println("Running add/remove test.");
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        boolean passed = checkEmpty(true, ad1.isEmpty());
        ad1.addFirst(10);
        passed = checkEmpty(false, ad1.isEmpty()) && passed;
        ad1.removeFirst();
        passed = checkEmpty(true, ad1.isEmpty()) && passed;
        printTestStatus(passed);
    }
    public static void main(String[] args) {
        System.out.println("Running tests");
        addIsEmptySizeTest();
        addRemoveTest();
    }

}