package conexoes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import interfaces.Callback;

public class Servidor implements Runnable {
	private int porta;
	private ServerSocket entrada;
	private Socket cliente;
	private Callback feed;
	private boolean flag;

	public Servidor(int porta, Callback feed) throws IOException {
		this.porta = porta;
		this.feed = feed;
		this.flag = true;
		this.entrada = new ServerSocket(porta);
	}
	

	public Servidor(int porta) throws IOException {
		this.porta = porta;
		this.feed = null;
		this.flag = true;
		this.entrada = new ServerSocket(porta);
	}

	public void loopServidor() throws IOException {
		byte[] buffer = new byte[1024];
		cliente = entrada.accept();
		int estadoBruto = 0;
		while (flag) {
			if (cliente.isConnected() && estadoBruto != -1) {
				if ((estadoBruto = cliente.getInputStream().read(buffer)) != 0) {
					String msg = new String(buffer, "UTF-8");
					System.out.println(msg);
					feed.atualizarMensagens(msg);
					buffer = new byte[1024];
				} else {
					continue;
				}
			} else {
				flag = false;
				System.out.println("O computador "+ cliente.getInetAddress().getHostAddress()+ " desconectou. Emcerrando servidor");
			}
		}
		cliente.close();

	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public ServerSocket getEntrada() {
		return entrada;
	}

	public void setEntrada(ServerSocket entrada) {
		this.entrada = entrada;
	}

	public Socket getCliente() {
		return cliente;
	}

	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public void run() {
		try {
			loopServidor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
