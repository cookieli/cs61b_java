package synthesizer;
import org.junit.Test;
import org.junit.Assert.*;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for(int i = 0; i < 10; i++) {
            arb.enqueue(i);
        }
        assertEquals(10, arb.fillCount());
        assertEquals((Integer) 0,arb.dequeue());
        assertEquals((Integer)1,arb.dequeue());
        assertEquals(8,arb.fillCount());
        assertEquals((Integer)2, arb.peek());
        assertEquals(8,arb.fillCount());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
