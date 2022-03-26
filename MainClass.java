import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class MainClass {

	public static void main(String[] args) {
		FileReader reader = new FileReader();
		ArrayList<String> CodeLines = new ArrayList<String>();
		Scanner sn = new Scanner(System.in);
		Evaluate eval = new Evaluate();
		Parse parser = new Parse();
		
		System.out.println("Bienvenido al interprete de Lisp");
		System.out.println("Por favor, ingese el nombre de su archivo (ej:Ejemplo.lisp)");
		
		String file_name = sn.nextLine();

		if(file_name.equals("prueba")){
			while(true){
				System.out.println();
				String linea = sn.nextLine();
				linea = linea.substring(1, linea.length()-1);

				Lista inst = parser.toLista(linea);
				System.out.println();

				switch(parser.verifyLInst(inst, eval)){
					case "setq":
						eval.setq(inst);
						System.out.println(eval.getVars());
						break;

					case "a":
						System.out.println(eval.operacionAritmetica(inst));
						break;

					case "print":
						System.out.println(eval.print(inst));
						break;

					case "list":
						System.out.println(eval.lista(inst));
						break;

					case "defun":
						eval.defun(inst);
						break;
					
					case "quote":
						System.out.println(linea.replace("quote", ""));
						break;

					case "cond":
						eval.cond(inst);
						break;

					default:
						if(eval.getFunciones().containsKey(inst.getInst())){
							System.out.println(eval.execFunc(inst.getInst(), eval.getParams(new Token(inst.getElemAt(0).toString()))));
						} else {
							System.out.println("Error en la instruccion");
						}
				}

			}
		}
		
		//Lectura de lineas de codigo de archivo Lisp
		try {

			CodeLines = reader.readingFile(file_name);
			for(String s : CodeLines){

				System.out.println("\n(" + s + ")\n");

				Lista inst = parser.toLista(s);

				switch(parser.verifyLInst(inst, eval)){
					case "setq":
						eval.setq(inst);
						System.out.println(eval.getVars());
						break;

					case "a":
						System.out.println(eval.operacionAritmetica(inst));
						break;

					case "print":
						System.out.println(eval.print(inst));
						break;

					case "list":
						System.out.println(eval.lista(inst));
						break;

					case "defun":
						eval.defun(inst);
						break;
					
					case "quote":
						System.out.println(s.replace("quote", ""));
						break;

					case "cond":
						eval.cond(inst);
						break;
					
					case "atom":
						eval.atom(inst);
						break;

					default:
						if(eval.getFunciones().containsKey(inst.getInst())){
							System.out.println(eval.execFunc(inst.getInst(), eval.getParams(new Token(inst.getElemAt(0).toString()))));
						} else {
							System.out.println("Error en la instruccion");
						}
				}
			}

		} 

		catch (FileNotFoundException e) {
			System.out.println("No se ha encontrado el archivo");

		}
	}
}
