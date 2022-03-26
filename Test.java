import junit.framework.TestCase;

public class Test extends TestCase{
	private Evaluate eval;
	private Parse parser = new Parse();
	private ClaseParaPruebas manejoParentesis = new ClaseParaPruebas();
	
	public void Inicializador() {
		eval = new Evaluate();
	}
	
	public void testSuma() {
		Inicializador();
		String suma = "(+ 3 2)";
		suma = manejoParentesis.ManejoParentesis(suma);
		Lista lista = parser.toLista(suma);
		System.out.println(lista);
		assertEquals((float)5.0, eval.operacionAritmetica(lista));
	}
	
	public void testResta() {
		Inicializador();
		String resta = "(- 3 2)";
		resta = manejoParentesis.ManejoParentesis(resta);
		Lista lista = parser.toLista(resta);
		System.out.println(lista);
		assertEquals((float)1.0, eval.operacionAritmetica(lista));
	}
	
	public void testMultiplicacion() {
		Inicializador();
		String multiplicacion = "(* 3 2)";
		multiplicacion = manejoParentesis.ManejoParentesis(multiplicacion);
		Lista lista = parser.toLista(multiplicacion);
		System.out.println(lista);
		assertEquals((float)6.0, eval.operacionAritmetica(lista));
	}
	
	public void testDivision() {
		Inicializador();
		String division = "(/ 3 2)";
		division = manejoParentesis.ManejoParentesis(division);
		Lista lista = parser.toLista(division);
		System.out.println(lista);
		assertEquals((float)1.5, eval.operacionAritmetica(lista));
	}
	
	public void testLista() {
		Inicializador();
		String list = "(list 'A 'B 'C)";
		list = manejoParentesis.ManejoParentesis(list);
		Lista lista = parser.toLista(list);
		System.out.println(lista);
		assertEquals("( A B C )", eval.lista(lista));
	}
	
	public void testQuote() {
		Inicializador();
		String quote = "(quote (+ 1 2)";
		quote = manejoParentesis.ManejoParentesis(quote);
		assertEquals(" + 1 2", quote.replace("quote", ""));
	}
	
	public void testSetQ() {
		Inicializador();
		String setq = "(setq num 3)";
		setq = manejoParentesis.ManejoParentesis(setq);
		Lista lista = parser.toLista(setq);
		eval.setq(lista);
		assertEquals(eval.searchVar("num"), 3f);
	}
	
	public void testPrint() {
		Inicializador();
		String print = "(print \"Hola Mundo\")";
		print = manejoParentesis.ManejoParentesis(print);
		Lista lista = parser.toLista(print);
		assertEquals("Hola Mundo",eval.print(lista));
	}
	
}