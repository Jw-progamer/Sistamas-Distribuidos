package conexoes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {
	private int porta;
	private ServerSocket entrada;
	private Socket cliente;
	private boolean flag;

	public Servidor(int porta) throws IOException {
		this.porta = porta;
		this.flag = true;
		this.entrada = new ServerSocket(porta);
	}

	public void loopServidor() throws IOException {
		byte[] buffer = new byte[1024];
		cliente = entrada.accept();
		while (flag) {
			if (cliente.getInputStream().read(buffer) != 0) {
				String msg = new String(buffer, "UTF-8");
				System.out.println(msg);
			} else {
				continue;
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
