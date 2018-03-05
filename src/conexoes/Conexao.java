package conexoes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Conexao {
	private Socket conexao;
	private String ip;
	private int porta;

	public Conexao(String ip, int porta) throws UnknownHostException, IOException {
		this.ip = ip;
		this.porta = porta;
		this.conexao = new Socket(ip, porta);
	}

	public void conectarEnviar(String msg) throws IOException {
		if (conexao.isConnected()) {
			enviar(msg);
		} else {
			System.out.println("computador " + conexao.getInetAddress().getHostAddress() + " Encerrou");
		}
	}

	private void enviar(String msg) throws IOException {
		byte[] bmsg;
		try {
			bmsg = msg.getBytes("UTF-8");
			conexao.getOutputStream().write(bmsg);
			conexao.getOutputStream().flush();
		} catch (UnsupportedEncodingException e) {
			// TODO: handle exception
		}
	}

	public Socket getConexao() {
		return conexao;
	}

	public void setConexao(Socket conexao) {
		this.conexao = conexao;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}


}
