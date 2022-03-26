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
 * Clase para objeto Lista
 */

class Lista implements Elemento{
	Token instruccion;
	ArrayList<Elemento> elems = new ArrayList<Elemento>();

	public Lista(Token instruccion, Elemento temp){
		this.instruccion = instruccion;
		elems.add(temp);
	}

	public Lista(Token instruccion, ArrayList<Elemento> elems){
		this.instruccion = instruccion;
		this.elems = elems;
	}

	/**
	 * Devuelve los elementos asociados a la lista
	 * @return arrayList con los elementos de la lista
	 */
	public ArrayList<Elemento> getElems(){
		return elems;
	}

	/**
	 * Devuelve el elemento en el indice indicado
	 * @param index indice de busqueda
	 * @return el elemento en el indice
	 */
	public Elemento getElemAt(int index){
		return elems.get(index);
	}

	/**
	 * Metodo para agregar un elemento al arraylist
	 * @param elem el elemento a agregar
	 */
	public void addElem(Elemento elem){
		elems.add(elem);
	}

	@Override
	public boolean isToken(){
		return false;
	}

	/**
	 * Devuelve la instruccion asociada a la lista
	 * @return un string con la instruccion
	 */
	public String getInst(){
		return instruccion.toString();
	}

	/**
	 * Devuelve el mismo objeto para convertir de Elemento a Lista
	 * @return esta lista
	 */
	public Lista toLista(){
		return this;
	}

	@Override
	public String toString(){
		String elem = "";
		for(Elemento e : elems){
			elem += e.toString() + " ";
		}
		return "( " + instruccion + " " + elem + ")";
	}
}