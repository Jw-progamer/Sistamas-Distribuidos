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

	public Conexao(Socket conexao) {
		this.conexao = conexao;
		this.ip = conexao.getInetAddress().getHostAddress();
		this.porta = conexao.getPort();
	}

	public void conectarEnviar(String msg) throws IOException {
		if (conexao.isConnected()) {
			enviar(msg);
		} else {
			System.err.println("computador " + conexao.getInetAddress().getHostAddress() + " Encerrou");
		}
	}

	public String conectarReceber() throws IOException {
		if (conexao.isConnected()) {
			return receber();
		} else {
			System.err.println("computador " + conexao.getInetAddress().getHostAddress() + " Encerrou");
			return null;
		}
	}

	private String receber() throws IOException {
		byte[] buffer = new byte[1024];
		int estadoBruto = 0;
		if (conexao.isConnected() && estadoBruto != -1) {
			if ((estadoBruto = conexao.getInputStream().read(buffer)) != 0) {
				String msg = new String(buffer, "UTF-8");
				System.out.println(msg);
				return msg;
			} else {
				System.out.println(
						"O computador " + conexao.getInetAddress().getHostAddress() + " desconectou. Encerrando porta");
				return null;
			}
		}
		return null;
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

	public boolean isConnect() {
		return conexao.isConnected();
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

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "endereço ip: " + ip + "; Porta de conexao: " + porta;
	}
}
