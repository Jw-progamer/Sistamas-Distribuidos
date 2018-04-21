package controle;

import interfaces.CallbackInterface;

public class TesteIntermediario implements CallbackInterface{
	ConexoesFachada fachada = ConexoesFachada.getINSTANCIA();
	public TesteIntermediario() {
		fachada.iniciarServidor(5000, this);
	}
	
	public void enviar(String msg, String ip) {
		fachada.enviarMensagem(msg, ip);
	}
	
	@Override
	public void atualizarMensagens(String Mensagem) {
		System.out.println("Chegou Mensagem:" + Mensagem);		
	}
	

}
