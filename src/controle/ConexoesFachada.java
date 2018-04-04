package controle;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import conexoes.Conexao;
import conexoes.Servidor;
import interfaces.Callback;

public class ConexoesFachada {
	private static ConexoesFachada INSTANCIA = new ConexoesFachada();
	private Servidor ouvinte;
	private List<Conexao> conexoes = new LinkedList<Conexao>();

	private ConexoesFachada() {

	}

	public static ConexoesFachada getINSTANCIA() {
		return INSTANCIA;
	}

	public boolean iniciarServidor(int porta, Callback front) {
		if (ouvinte != null) {
			ouvinte.setFlag(false);
			try {
				ouvinte = new Servidor(porta, front);
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
				ouvinte = new Servidor(porta, front);
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
		for (Conexao c : conexoes) {
			if (c.getIp().equals(ip) && c.getPorta() == porta) {
				if (c.isConnect()) {
					try {
						c.conectarEnviar(msg);
						return true;
					} catch (IOException e) {
						System.err.println("Pode ter ocorrido um erro de conexão com a máquina " + c.getIp()
								+ c.getPorta() + ". Provavelmente conexão recusa");
						e.printStackTrace();
						return false;
					}
				} else {
					System.err.println("log: " + c.getIp() + c.getPorta() + " desligado");
					return false;
				}
			}
		}
		return false;

	}

	public boolean conectar(String ip, int porta) {
		for(Conexao c: conexoes) {
			if (c.getIp().equals(ip) && c.getPorta() == porta) {
				System.out.println("Já conectado a um endereço ip");
				return false;
			}
		}
		try {
			Conexao conexao = new Conexao(ip, porta);
			conexoes.add(conexao);
			return true;
		} catch (IOException e) {
			System.out.println("Não foi possível conectar ao endereço");
			e.printStackTrace();
			return false;
		}
	}

	public Servidor getOuvinte() {
		return ouvinte;
	}

	public void setOuvinte(Servidor ouvinte) {
		this.ouvinte = ouvinte;
	}

	public Conexao getConexao(String ip, int porta) {
		for(Conexao c: conexoes) {
			System.out.println(c);
			if (c.getIp().equals(ip) && c.getPorta() == porta) {
				System.out.println("Cheguei no retorno");
				return c;
			}
		}
		return null;
	}


}
