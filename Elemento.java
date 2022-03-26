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
 * Clase de interface Elemento 
 */

interface Elemento{
	
	/**
	 * Determina si la clase es un token o no
	 * @return boolean
	 */
	public boolean isToken();

	/**
	 * Para realizar la conversion de elemento a lista
	 * @return null si es un token, la misma lista si es lista
	 */
	public Lista toLista();

}