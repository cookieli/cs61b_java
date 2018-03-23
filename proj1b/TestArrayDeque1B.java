import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDeque1B {
    @Test
    public void testDeque1B() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> sad  = new StudentArrayDeque<>();
        for(int i = 0 ; i < 10; i++) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if(numberBetweenZeroAndOne > 0.5) {
                ads.addFirst(i);
                sad.addFirst(i);
            } else {
                ads.addLast(i);
                sad.addLast(i);
            }
        }
        OperationSequence fs = new OperationSequence();
        for(int i = 0; i < 10; i++) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if(numberBetweenZeroAndOne > 0.5) {
                DequeOperation sadOp1 = new DequeOperation("removeFirst");
                fs.addOperation(sadOp1);
                assertEquals(fs.toString(),ads.removeFirst(),sad.removeFirst());
            } else {
                DequeOperation sadOp2 = new DequeOperation("removeLast");
                fs.addOperation(sadOp2);
                assertEquals(fs.toString(),ads.removeLast(), sad.removeLast());
            }
        }
    }
}