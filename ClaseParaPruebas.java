
public class ClaseParaPruebas {

	public String ManejoParentesis(String expresion) {
		expresion = expresion.replace("(", "");
		expresion = expresion.replace(")", "");
		return expresion;
	}
}
