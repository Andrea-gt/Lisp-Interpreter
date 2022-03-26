import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

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
 * Clase para evaluar expresiones como listas 
 */

class Evaluate{

	HashMap<String, Float> vars;
	HashMap<String, ArrayList<Elemento>> funciones;
	HashMap<String, String> param;

	public Evaluate(){
		vars = new HashMap<String, Float>();
		funciones = new HashMap<String, ArrayList<Elemento>>();
		param = new HashMap<String, String>();
	}

	/**Metodo para para el establecimiento de variables
	 * @param l linea de codigo vuelta lista
	 * @return void
	 */

	public void setq(Lista l){
		try{
			int val = Integer.parseInt(l.getElemAt(1).toString());
			vars.put(l.getElemAt(0).toString(), Float.parseFloat(l.getElemAt(1).toString()));
		} catch(Exception e){
			if(l.getElemAt(1).isToken()){
				if(searchVar(l.getElemAt(1).toString()) != null){
					vars.put(l.getElemAt(0).toString(), searchVar(l.getElemAt(1).toString()));
				} else if(searchParam(l.getElemAt(1).toString()) != null){
					vars.put(l.getElemAt(0).toString(), Float.parseFloat(searchParam(l.getElemAt(1).toString())));
				} 
				else {
					System.out.println("??");
				}
			} 
			else {
				vars.put(l.getElemAt(0).toString(), operacionAritmetica(l.getElemAt(1).toLista()));
			}
		}

	}

	/**Metodo para obtener variables
	 * @return HashMap<String, Integer>
	 */
	public HashMap<String, Float> getVars(){
		return vars;
	}

	/**Metodo para evaluar la existencia de variables
	 * @param k, expresion a evaluar
	 * @return null si no hay varibles, key k si hay variables
	 */
	public Float searchVar(String k){
		if(vars.containsKey(k)){
			return vars.get(k);
		} 
		else {
			return null;
		}
	}

	/**Metodo para evaluar la existencia de parametros
	 * @param k, expresion a evaluar
	 * @return null si no hay varibles, key k si hay variables
	 */
	public String searchParam(String k){
		if(param.containsKey(k)){
			return param.get(k);
		} else {
			return null;
		}
	}

	/**Metodo para evaluar operaciones aritmeticas
	 * @param l, linea de codigo en forma de lista
	 * @return total de la operacion aritmetica a evaluar
	 */
	public float operacionAritmetica(Lista l){
		ArrayList<Float> operandos = new ArrayList<Float>();
		float tot = 0;
		for(Elemento e : l.getElems()){
			if(e.isToken()){
				try{
					operandos.add(Float.parseFloat(e.toString()));
				} catch(Exception err){
					if(searchVar(e.toString())!=null){
						operandos.add(searchVar(e.toString()).floatValue());
					} else if(searchParam(e.toString())!=null){
						try{
							operandos.add(Float.parseFloat(searchParam(e.toString())));
						} catch(Exception err2){
							System.out.println(e.toString() + " no se ha encontrado dentro de las variables");
						}
						
					}
					
				}
				
			} else{
				Float valor = operacionAritmetica(e.toLista());
				operandos.add(valor.floatValue());
			}
		}

		switch(l.getInst()){

			case "+":
			for(float i : operandos){
				tot += i;
			}
			break;

			case "-":
			for(int i = 0; i<operandos.size(); i++){
				if(i==0){
					tot+=operandos.get(i);
				} 
				else {
					tot -= operandos.get(i);
				}
			}
			break;

			case "*":
			tot = operandos.get(0);

			for(int i = 1; i<operandos.size(); i++){
				tot *= operandos.get(i);
			}
			break;

			case "/":
			tot = operandos.get(0);

			for(int i = 1; i<operandos.size(); i++){
				tot /= operandos.get(i);
			}

		}
		return tot;
	}

	/**Metodo para mostrar cosas en pantallas con "print"
	 * @param l, expresion como una lista
	 * @return String
	 */
	public String print(Lista l){
		String temp = l.getElemAt(0).toString();
		if(searchVar(temp)!=null){
			return Float.toString(searchVar(temp));
		} else if(searchParam(temp)!=null){
			return searchParam(temp);
		} else{
			return temp.substring(1, temp.length()-1);
		}
	}

	/**Metodo para volver la lista en una lista LISP
	 * @param l, expresion como una lista
	 * @return String
	 */
	public String lista(Lista l){
		String evaluacionSintaxis = l.toString();
		evaluacionSintaxis = evaluacionSintaxis.replace("list ", "");
		evaluacionSintaxis = evaluacionSintaxis.replace("\'", "");
		return evaluacionSintaxis;
	}

	/**Metodo para llevar a cabo la instruccion defun
	 * @param l, expresion como una lista
	 * @return void
	 */
	public void defun(Lista l){
		ArrayList<Elemento> temp = new ArrayList<Elemento>();
		for(int i = 1; i<l.getElems().size(); i++){
			if(i==1){
				temp.add(l.getElemAt(i));
			} else{
				temp.add(l.getElemAt(i).toLista());
			}
			
		}
		funciones.put(l.getElemAt(0).toString(), temp);
	}

	/**
	 * Método para asignar un nuevo parametro en la funcion
	 * @param k la llave del parametro
	 * @param elem el elemento a guardar
	 */
	private void putParam(String k, String elem){
		param.put(k, elem);
	}

	/**
	 * Método para ejecutar una funcion
	 * @param k la llave para llamar la funcion
	 * @param params los parametros a utilizar
	 * @return un string con el valor final evaluado
	 */
	public String execFunc(String k, ArrayList<String> params){

		Parse parser = new Parse();
		Evaluate eval = new Evaluate();
		ArrayList<String> funcParams = getParams(new Token(funciones.get(k).get(0).toString()));

		//Asigna los parametros a los valores que se le pasaron a la funcion
		for(int i = 0; i<funcParams.size(); i++){
			try{
				if(params.get(i).charAt(0) == '(' && params.get(i).charAt(params.get(i).length()-1) == ')'){
					Lista b = parser.toLista(params.get(i).substring(1, params.get(i).length()-1));
					if(parser.verifyLInst(b, this).equals("a")){
						eval.putParam(funcParams.get(i), Float.toString(operacionAritmetica(b)));
					} else if(parser.verifyLInst(b, this).equals(b.getInst().toLowerCase())){
						eval.putParam(funcParams.get(i), execFunc(b.getInst(), getParams(new Token(b.getElemAt(0).toString()))));
					}
				} else{
					eval.putParam(funcParams.get(i), params.get(i));
				}
				
			} catch(NullPointerException e){
				System.out.println("No se ha encontrado el valor para el parametro " + funcParams.get(i));
				return " ";
			}
		}

		//Consigue las instrucciones asociadas a la instruccion
		ArrayList<Lista> instrucciones = new ArrayList<Lista>();
		for(int i = 1; i<funciones.get(k).size(); i++){
			instrucciones.add(funciones.get(k).get(i).toLista());
		}

		//Evalua las instrucciones
		for(Lista l : instrucciones){
			switch(parser.verifyLInst(l, this)){
				case "setq":
					eval.setq(l);
					System.out.println(eval.getVars());
					break;

				case "a":
					return Float.toString(eval.operacionAritmetica(l));

				case "print":
					System.out.println(eval.print(l));
					break;

				case "cond":
					return eval.cond(l);

				default:
					if(funciones.containsKey(l.getInst())){
						return execFunc(l.getInst(), getParams(new Token(l.getElemAt(0).toString())));
					}
			}
		}
		return " ";
	}

	/**
	 * Metodo para conseguir una lista de parametros dado [param1, param2, paramN]
	 * @param t los parametros en forma de token
	 * @return ArrayList con los parametros en forma de string
	 */
	public ArrayList<String> getParams(Token t){
		ArrayList<String> temp = new ArrayList<String>();
		String params = t.toString().substring(1, t.toString().length()-1);
		temp = new ArrayList<>(Arrays.asList(params.split(",")));
		
		return temp;
	}

	/**
	 * Devuelve las funciones almacenadas dentro de la instancia
	 * @return las funciones almacenadas
	 */
	public HashMap<String, ArrayList<Elemento>> getFunciones(){
		return funciones;
	}

	/**
	 * Metodo para evaluar una operacion condicional simple como (= a b) o (< a b)
	 * @param l la lista a evaluar
	 * @param eval la instancia de Evaluate en la que se buscarán las variables
	 * @return el resultado de la evaluacion (true o false)
	 */
	public boolean evalCond(Lista l, Evaluate eval){
		ArrayList<Float> operandos = new ArrayList<Float>();
		//Convierte las variables a valores evaluables
		for(Elemento e : l.getElems()){
			if(e.isToken()){
				try{
					operandos.add(Float.parseFloat(e.toString()));
				} catch(Exception err){
					if(searchVar(e.toString())!=null){
						operandos.add(eval.searchVar(e.toString()).floatValue());
					} else if(searchParam(e.toString())!=null){
						try{
							operandos.add(Float.parseFloat(eval.searchParam(e.toString())));
						} catch(Exception err2){
							System.out.println("No se ha encontrado la variable " + e.toString());
						}
						
					}
					
				}
				
			} else{
				Float valor = operacionAritmetica(e.toLista());
				operandos.add(valor.floatValue());
			}
		}

		switch(l.getInst()){

			case "=":
				return Math.round(operandos.get(0)) == Math.round(operandos.get(1));
			case "<":
				return Math.round(operandos.get(0)) < Math.round(operandos.get(1));
			case ">":
				return Math.round(operandos.get(0)) > Math.round(operandos.get(1));
		}
		return false;
	}

	/**
	 * Metodo para evaluar si un elemento es un atom o no
	 * @param l lista a evaluar
	 * @return T o NIL
	 */
	public String atom(Lista l){
		if(l.getElemAt(0).isToken()){
			return "T";
		}
		return "NIL";
	}

	/**
	 * Metodo para evaluar si un elemento es una lista o no
	 * @param l lista a evaluar
	 * @return T o NIL
	 */
	public String listp(Lista l){
		if(l.getElemAt(0).isToken()){
			return "NIL";
		}
		return "T";
	}

	/**
	 * Metodo para evaluar si los dos primeros argumentos son iguales
	 * @param l la lista a evaluar
	 * @return true o false dependiendo del resultado de la evaluacion
	 */ 
	public boolean equal(Lista l){
		return l.getElemAt(0).toString().equals(l.getElemAt(1).toString());
	}

	/**
	 * Metodo para evaluar condicionales
	 * @param l la lista a evaluar
	 * @return un string con el resultado
	 */
	public String cond(Lista l){
		Parse parser = new Parse();
		boolean temp = false;
		for(Elemento h : l.getElems()){
			//Si no se ha cumplido ninguna condicion, realiza lo que está en "t"
			if(h.toLista().getInst().equals("t") && !temp){
				
				for(Elemento f : h.toLista().getElems()){
					switch(parser.verifyLInst(f.toLista(), this)){
						case "setq":
							setq(f.toLista());
							System.out.println(getVars());
							temp = true;
							break;

						case "a":
							System.out.println(operacionAritmetica(f.toLista()));
							temp = true;
							break;

						case "print":
							System.out.println(print(f.toLista()));
							temp = true;
							break;

						case "cond":
							cond(f.toLista());
							temp = true;
							break;

						default:
							if(funciones.containsKey(f.toLista().getInst())){
								execFunc(f.toLista().getInst(), getParams(new Token(f.toLista().getElemAt(0).toString())));
								temp = true;
							}
					}
				}
			//Evalua cada condicion
			} else if(evalCond(parser.toLista(h.toLista().getInst().substring(1, h.toLista().getInst().length())), this)){
				for(Elemento f : h.toLista().getElems()){
					switch(parser.verifyLInst(f.toLista(), this)){
						case "setq":
							setq(f.toLista());
							System.out.println(getVars());
							temp = true;
							break;

						case "a":
							System.out.println(operacionAritmetica(f.toLista()));
							temp = true;
							break;

						case "print":
							System.out.println(print(f.toLista()));
							temp = true;
							break;

						case "cond":
							cond(f.toLista());
							temp = true;
							break;

						default:
							if(funciones.containsKey(f.toLista().getInst())){
								execFunc(f.toLista().getInst(), getParams(new Token(f.toLista().getElemAt(0).toString())));
								temp = true;
							}
					}
				}
			}
		}
		return " ";
	}
}