package visao;

import java.net.URL;
import java.util.ResourceBundle;

import controle.ConexoesFachada;
import interfaces.Callback;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TelaController implements Callback, Initializable{
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
	Button conectarNovo;
	@FXML
	TextField portaServidorTxt;
	@FXML
	TextField enderecoTxtNovo;
	@FXML
	TextField portaTxtNovo;
	@FXML
	CheckBox aceitandoConexaoCheck;
	@FXML
	ListView<String> listaConexao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	 listaConexao.setItems(FXCollections.observableArrayList());	
	}

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

	public void conectar() throws Exception{
		ConexoesFachada rede = ConexoesFachada.getINSTANCIA();
		if (enderecoTxtNovo.getText().equals("") || portaTxtNovo.getText().equals("")) {
			alertaDeWarning("Erro ao conectar",
					"Você não passou um endereço ip ou porta lógico. um ou ambos estão em branco");
		} else {
			String endereco = enderecoTxtNovo.getText();
			int porta = Integer.parseInt(portaTxtNovo.getText());
			if (rede.conectar(endereco, porta)) {
				System.out.println("Cheguei aqui, conecção feita");
				enderecoTxtNovo.setText(null);
				portaTxtNovo.setText(null);
				String teste = rede.getConexao(endereco, porta).toString();
				System.out.println(teste);
				listaConexao.getItems().add(teste);
				
			} else {
				alertaDeWarning("Erro ao se conectar com outra máquina",
						"Algo falhou com a conecção. Por favor reporte esse erro ao desenvolvedor");
			}
		}
	}

	public void enviarMensagem() {
		ConexoesFachada rede = ConexoesFachada.getINSTANCIA();
		String msg = mensagemTxt.getText();
		String endereco = enderecoTxt.getText();
		int porta = Integer.parseInt(portaTxt.getText());
		if (rede.enviarMensagem(msg, endereco, porta)) {
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
