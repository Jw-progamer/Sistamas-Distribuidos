package visao;

import controle.ConexoesFachada;
import interfaces.Callback;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TelaController implements Callback {
	@FXML
	TextArea feedTxt;
	@FXML
	TextField enderecoTxt;
	@FXML
	TextField portaTxt;
	@FXML
	TextArea mensagemTxt;
	@FXML
	Button enviarBtn;
	@FXML
	TextField portaServidorTxt;
	@FXML
	CheckBox aceitandoConexaoCheck;
	@FXML
	Button conectarBtn;

	public void ligarServidor() {
		ConexoesFachada rede = ConexoesFachada.getINSTANCIA();
		if (aceitandoConexaoCheck.isSelected()) {
			System.out.println("chego aqui, caixa selecionada");
			if (portaServidorTxt.getText().equals("")) {
				alertaDeWarning("Prolema ao ligar o modo ouvinte", "Você não passou uma porta para o servidor");
			} else {
				int porta = Integer.parseInt(portaServidorTxt.getText());
				if (rede.iniciarServidor(porta, this)) {
					System.out.println("Chegei na instancia do servidor");
				} else {
					alertaDeWarning("falha ao conectar",
							"Ouve uma falar em instanmcia seu servidor. Reporte este erro ao desenvolvedor");
					aceitandoConexaoCheck.setSelected(false);
				}
			}
		} else {
			rede.desligarServidor();
			System.out.println("chego aqui caixa desligada");
		}
	}

	public void conectar() {
		ConexoesFachada rede = ConexoesFachada.getINSTANCIA();
		if (enderecoTxt.getText().equals("") || portaTxt.getText().equals("")) {
			alertaDeWarning("Erro ao conectar",
					"Você não passou um endereço ip ou porta lógico. um ou ambos estão em branco");
		} else {
			int porta = Integer.parseInt(portaTxt.getText());
			if (rede.conectar(enderecoTxt.getText(), porta)) {
				System.out.println("Cheguei aqui, conecção feita");
			} else {
				alertaDeWarning("Erro ao se conectar com outra máquina",
						"Algo falhou com a conecção. Por favor reporte esse erro ao desenvolvedor");
			}
		}
	}

	public void enviarMensagem() {
		ConexoesFachada rede = ConexoesFachada.getINSTANCIA();
		String msg = mensagemTxt.getText();
		if (rede.enviarMensagem(msg)) {
			System.out.println("Cheguei aqui, mensagem enviada");
			mensagemTxt.setText(null);
		} else {
			System.out.println("Quer dizer que eu chego é aqui?");
			alertaDeWarning("Erro ao enviar",
					"Um erro ocorreu no momento que tentou enviar a mensagem. Avise para o desenvolvedor");
		}
	}

	private void alertaDeWarning(String título, String mensagaem) {
		Alert alerta = new Alert(Alert.AlertType.WARNING);
		alerta.setTitle(título);
		alerta.setHeaderText(mensagaem);
		alerta.show();
	}

	@Override
	public void atualizarMensagens(String mensagem) {
		System.out.println("Chegei aqui, deveria ter atualizado");
		String antigo = feedTxt.getText();
		if (antigo.equals("")) {
			feedTxt.setText(mensagem);
		} else {
			String novo = antigo + "\n" + mensagem;
			feedTxt.setText(novo);
		}
	}
}
