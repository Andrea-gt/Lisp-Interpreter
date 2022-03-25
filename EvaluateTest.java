import java.util.ArrayList;
import java.util.HashMap;
import junit.framework.TestCase;

public class EvaluateTest extends TestCase {
    
    public EvaluateTest(String testName) {
        super(testName);
    }
    
    /**
     * Test of operacionAritmetica method, of class Evaluate.
     */
    public void testOperacionAritmetica() {
        System.out.print("Operaciones Aritmeticas");
        Parse parse = new Parse();
        Lista l = parse.toLista("+ 1 2");
        Evaluate instance = new Evaluate();
        int expResult = 3;
        int result = instance.operacionAritmetica(l);
        assertEquals(expResult, result);
        System.out.print("\nResultado: " + result);
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of lista method, of class Evaluate.
     */
    public void testLista() {
        System.out.println("lista");
        Parse parse = new Parse();
        Lista l = parse.toLista("+ 1 2");
        Evaluate instance = new Evaluate();
        String expResult = "+ 1 2";
        String result = instance.lista(l);
        assertEquals(expResult, result);
        System.out.print("\nResultado: " + result);
        fail("The test case is a prototype.");
    }
}
