import java.util.HashMap;
import java.util.ArrayList;

/*
 * Universidad del Valle de Guatemala
 * Proyecto de Algoritmos y Estructuras de Datos
 * Seccion 20, 2022
 * Interprete de Lisp en Java
 * 
 * @author Andrea Ximena Ramirez Recinos 21874
 * @author Adrian Ricardo Flores Trujillo 21500
 * @author Sebastian José Solorzano Pérez 21826
 * @version 22/03/2022
 * 
 * Clase Parse para determinar si la sintaxis de las instrucciones es correcta
 */

class Parse{

	/** Metodo para transformar las lineas de codigo en listas 
	 * @param a, expresion a evaluar de las lineas de codigo
	 * @return Lista
	 */
	public Lista toLista(String a){
		boolean modo = false, activo = false, flag = true, activo2 = false;
		String inst = "", exp = "";
		int cont = 0, index = 0, cont2 = 0, index2 = 0;
		a += " ";
		ArrayList<Elemento> elems = new ArrayList<Elemento>();
		for(int i = 0; i<a.length(); i++){
			if(Character.compare(a.charAt(i), "[".charAt(0)) == 0){
				activo2=true;
			}
			if(activo2){
				if(Character.compare(a.charAt(i), "[".charAt(0)) == 0){
					index2 = i;
				} else if(Character.compare(a.charAt(i), "]".charAt(0)) == 0){
					elems.add(new Token(a.substring(index2, i+1)));
					activo2 = false;
				}
			} else if(flag && Character.compare(a.charAt(i), "(".charAt(0)) == 0){
				cont++;
				if(cont == 1){
					index = i;
				}
				activo = true;
			} else if(flag && Character.compare(a.charAt(i), ")".charAt(0)) == 0){
				cont--;
				if(cont == 0){
					inst = a.substring(index, i+1);
					flag = false;
					activo = false;
				}
			} else if(Character.compare(a.charAt(i), "(".charAt(0)) == 0){
				cont++;
				if(cont == 1){
					index = i;
				}
				activo = true;
			} else if(Character.compare(a.charAt(i), ")".charAt(0)) == 0){
				cont--;
				if(cont == 0){
					//System.out.println(a.substring(index+1, i));
					elems.add(toLista(a.substring(index+1, i)));
					activo = false;
				}
			} else if(!activo && !activo2){
				if(!Character.isWhitespace(a.charAt(i))){
					if(Character.compare(a.charAt(i), '"') == 0){
						elems.add(new Token(a.substring(i, a.lastIndexOf('"')+1)));
						i = a.lastIndexOf('"')+1;
						exp = "";
					} else {
						exp += a.charAt(i);
					}
				} else {
					if(!exp.equals("")){
						if(flag){
							inst = exp;
							flag = false;
					
						} else {
							elems.add(new Token(exp));
						}
						exp = "";
					}
				} 
			}
		}
		return new Lista(new Token(inst), elems);

	}

	/** Establece que instruccion se esta pasando para aplicarla en el Main
	 * @param l, linea de codigo en forma de lista
	 * @param eval, objeto de tipo eval
	 * @return String
	 */
	public String verifyLInst(Lista l, Evaluate eval){
		switch(l.getInst().toLowerCase()){

			case "list":
				if(evaluarLista(l)){
					return "list";
				}
				break;
		
			case "quote":
				return "quote";

			case "atom":
				return "atom";

			case "listp":
				return "listp";

			case "setq":
				if(verSetq(l)){
					return "setq";
				}
				break;

			case "+":
			case "-":
			case "/":
			case "*":
				return "a";

			case "print":
				if(verPrint(l)){
					return "print";
				}
				break;

			case "defun":
				if(verDefun(l)){
					return "defun";
				}
				break;

			case "cond":
				if(verCond(l)){
					return "cond";
				}
				break;

			case "equal":
				if(verEqual(l)){
					return "equal";
				}
				break;

			default:
				if(eval.getFunciones().containsKey(l.getInst().toLowerCase())){
					return l.getInst().toLowerCase();
				}
				return "aksf";
		}
		return "anf";
	}

	/** Determina si la sintaxis de Setq es la correcta 
	 * @param l, linea de codigo en forma de lista
	 * @return boolean
	 */
	public boolean verSetq(Lista l){
		if(l.getElemAt(0).isToken()){
			if(l.getElemAt(1).isToken()){
				return true;
			} else {
				if(verifyLInst(l.getElemAt(1).toLista(), new Evaluate()).equals("a")){
					return true;
				}
				System.out.println("Valor invalido");
				return false;
			}
		}
		System.out.println("Nombre de variable invalido");
		return false;
	}

	/** Determina si la sintaxis de print es la correcta 
	 * @param l, linea de codigo en forma de lista
	 * @return boolean
	 */
	public boolean verPrint(Lista l){
		if(l.getElemAt(0).isToken() && l.getElems().size() == 1){
			String temp = l.getElemAt(0).toString();

			if(temp.charAt(0) == '"' && temp.charAt(temp.length()-1) == '"'){
				return true;
			} else{
				return true;
			}

		}
		System.out.println("????");
		return true;
	}

	/** Determina si la sintaxis de defun es la correcta 
	 * @param l, linea de codigo en forma de lista
	 * @return boolean
	 */
	public boolean verDefun(Lista l){
		if(l.getElemAt(0).isToken() && l.getElemAt(1).isToken()){
			if(l.getElemAt(1).toString().charAt(0) == '[' && l.getElemAt(1).toString().charAt(l.getElemAt(1).toString().length()-1) == ']'){
				if(l.getElems().size() > 2){
					boolean flag = true;
					for(int i = 2; i<l.getElems().size(); i++){
						if(l.getElemAt(i).isToken()){
							return false;
						}
						return true;
					}
				} else {
					return false;
				}
			}
		} else {
			System.out.println("asfbnakf");
			return false;
		}
		return false;
	}

	/** Determina si la sintaxis de list es la correcta 
	 * @param l linea de codigo en forma de lista
	 * @return boolean
	 */
	public boolean evaluarLista(Lista l){
		if(l.getElemAt(0).isToken()){
			String evaluacionSintaxis = l.getElemAt(0).toString();
			if(evaluacionSintaxis.charAt(0) == '\'' || evaluacionSintaxis.charAt(0) == ' '){
				return true;
			}
		}
		System.out.println("Ha ocurrido un Error");
		return false;
	}

	/**
	 * Determina si la sintaxis de cond es correcta
	 * @param l linea de codigo en forma de lista
	 * @return boolean
	 */ 
	public boolean verCond(Lista l){
		if(!l.getElemAt(0).isToken()){
			if(l.getElemAt(0).toLista().getInst().charAt(0) == '(' && l.getElemAt(0).toLista().getInst().charAt(l.getElemAt(0).toLista().getInst().length() - 1) == ')'){
				if(!l.getElemAt(0).toLista().getElemAt(0).isToken()){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Determina si la sintaxis de Equal es correcta
	 * @param l linea de codigo en forma de lista
	 * @return boolean
	 */ 
	public boolean verEqual(Lista l){
		if(l.getElems().size() == 2){
			return true;
		} else {
			return false;
		}
	}

}

