package controle;

import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import conexoes.Conexao;
import conexoes.Servidor;

public class TesteConsole {

	public static void main(String[] args) {
		Servidor feed = null;
		try {
			feed = new Servidor(4040);
			Scanner scanner = new Scanner(System.in);

			Thread servidorOuvir = new Thread(feed);
			servidorOuvir.start();

			String ip = JOptionPane.showInputDialog("Digite o endereço ip que desja se conectar.");
			String strPorta = JOptionPane.showInputDialog("Digite a porta em que " + ip + " estar escutando.");

			if (ip.equals("") || strPorta.equals("")) {
				System.out.println("ip e/ou porta passados em branco. encerrando programa");
				System.exit(0);
			}
			Conexao cliente = new Conexao(ip, Integer.parseInt(strPorta));
			while (true) {
				System.out.println("Digite uma mensagem para enviar:");
				String msg = scanner.nextLine();
				cliente.conectarEnviar(msg);
			}

		} catch (IOException e) {
			e.printStackTrace();
			feed.setFlag(false);
		}
	}

}
