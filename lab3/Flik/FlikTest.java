import static org.junit.Assert.*;
import org.junit.Test;
public class FlikTest{
    @Test
    public void TestEqual() {
        for(int j = 129; j < 200; j++){
            assertTrue(Flik.isSameNumber(j, j));
            assertTrue("Flik is wrong", Flik.isSameNumber(j, j));
            System.out.println(j);
        }
    }
}