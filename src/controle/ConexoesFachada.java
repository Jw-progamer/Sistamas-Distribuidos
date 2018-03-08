package controle;

import java.io.IOException;
import conexoes.Conexao;
import conexoes.Servidor;
import interfaces.Callback;

public class ConexoesFachada {
	private static ConexoesFachada INSTANCIA = new ConexoesFachada();
	private Servidor ouvinte;
	private Conexao conexao;

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
	
	public boolean enviarMensagem(String msg) {
		if (conexao != null) {
			try {
				conexao.conectarEnviar(msg);
				return true;
			} catch (IOException e) {
				System.err.println("Pode ter ocorrido um erro de conexão");
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public boolean conectar(String ip, int porta) {
		if(conexao != null) {
			System.out.println("Já conectado a um endereço ip");
			return false;
		}else {
			try {
				conexao = new Conexao(ip,porta);
				return true;
			} catch (IOException e) {
				System.out.println("Não foi possível conectar ao endereço");
				e.printStackTrace();
				return false;
			}
		}
	}

	public Servidor getOuvinte() {
		return ouvinte;
	}

	public void setOuvinte(Servidor ouvinte) {
		this.ouvinte = ouvinte;
	}

	public Conexao getConexao() {
		return conexao;
	}

	public void setConexao(Conexao conexao) {
		this.conexao = conexao;
	}

}
