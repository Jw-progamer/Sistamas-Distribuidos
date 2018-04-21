package controle;

import java.util.Scanner;

public class Teste{
public static void main(String[] args) {
	TesteIntermediario intermedio = new TesteIntermediario();
	Scanner teclado = new Scanner(System.in);
	while(true) {
		String msg = null;
		String ip = null;
		System.out.println("Digite uma mensagem:");
		msg = teclado.nextLine();
		System.out.println("Digite uma mensagem:");
		ip = teclado.nextLine();
		intermedio.enviar(msg, ip);
	}
}


}
