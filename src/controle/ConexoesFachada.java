package controle;

import java.io.IOException;
import java.net.Socket;
import conexoes.Conexao;
import conexoes.Hud;
import conexoes.Ouvinte;
import interfaces.CallbackInterface;

public class ConexoesFachada {
	private static ConexoesFachada INSTANCIA = new ConexoesFachada();
	private Hud ouvinte;

	private ConexoesFachada() {

	}

	public static ConexoesFachada getINSTANCIA() {
		return INSTANCIA;
	}

	public boolean iniciarServidor(int porta, CallbackInterface front) {
		if (ouvinte != null) {
			ouvinte.setFlag(false);
			try {
				ouvinte = new Hud(porta, front);
				Thread ouvir = new Thread(ouvinte);
				ouvir.start();
				return true;
			} catch (IOException e) {
				System.err.println("ocorreu um erro na tentaiva de instancia um novo servidor"
						+ " quando um servidor já estava estanciado");
				e.printStackTrace();
				return false;
			}
		} else {
			try {
				ouvinte = new Hud(porta, front);
				Thread ouvir = new Thread(ouvinte);
				ouvir.start();
				System.out.println("Servidor iniciado");
				return true;
			} catch (IOException e) {
				System.err.println("ocorreu um erro na tentaiva de instancia um novo servidor" + " do zero.");
				e.printStackTrace();
				return false;
			}
		}
	}

	public void desligarServidor() {
		ouvinte.setFlag(false);
		ouvinte = null;
	}

	public boolean enviarMensagem(String msg, String ip, int porta) {
		for (Ouvinte c : ouvinte.getOuvintes()) {
			if (c.getConexao().getIp().equals(ip) && c.getConexao().getPorta() == porta) {
				if (c.getConexao().isConnect()) {
					try {
						c.getConexao().conectarEnviar(msg);
						return true;
					} catch (IOException e) {
						System.err.println("Pode ter ocorrido um erro de conexão com a máquina " + c.getConexao().getIp()
								+ c.getConexao().getPorta() + ". Provavelmente conexão recusa");
						e.printStackTrace();
						return false;
					}
				} else {
					System.err.println("log: " + c.getConexao().getIp() + c.getConexao().getPorta() + " desligado");
					return false;
				}
			}
		}
		return false;

	}

	public boolean conectar(String ip, int porta) {
		for(Ouvinte c: ouvinte.getOuvintes()) {
			if (c.getConexao().getIp().equals(ip) && c.getConexao().getPorta() == porta) {
				System.out.println("Já conectado a um endereço ip");
				return false;
			}
		}
		try {
			Socket conexao = new Socket(ip, porta);
			ouvinte.conexaoManual(conexao);
			return true;
		} catch (IOException e) {
			System.out.println("Não foi possível conectar ao endereço");
			e.printStackTrace();
			return false;
		}
	}

	public Hud getOuvinte() {
		return ouvinte;
	}

	public void setOuvinte(Hud ouvinte) {
		this.ouvinte = ouvinte;
	}

	public Conexao getConexao(String ip, int porta) {
		for(Ouvinte c: ouvinte.getOuvintes()) {
			System.out.println(c);
			if (c.getConexao().getIp().equals(ip) && c.getConexao().getPorta() == porta) {
				System.out.println("Cheguei no retorno");
				return c.getConexao();
			}
		}
		return null;
	}


}
