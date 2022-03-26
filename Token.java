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
 * Clase de objeto Token
 */

class Token implements Elemento{
	String contenido;

	public Token(String contenido){
		this.contenido = contenido;
	}

	/**
	 * Verifica si el token es un numero
	 * @return boolean
	 */
	public boolean isNum(){
		try{
			Integer.parseInt(contenido);
			return true;
		} catch(Exception e){
			return false;
		}
	}

	@Override
	public String toString(){
		return contenido;
	}

	@Override
	public boolean isToken(){
		return true;
	}

	@Override
	public Lista toLista(){
		return null;
	}
}