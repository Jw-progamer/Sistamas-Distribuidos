package controle;

import interfaces.CallbackInterface;
import interfaces.Log;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ScreenVmController implements Log, CallbackInterface {

	@FXML
	ListView<String> listConexao;
	@FXML
	TextField txtProcesso;
	@FXML
	TextField txtMemoria;
	@FXML
	TextField txtCpu;
	@FXML
	TextField txtProcessoVirtual;
	@FXML
	TextField txtMemoriaVirtual;
	@FXML
	TextField txtCpuVirtual;
	
	@Override
	public void atualizarMensagens(String Mensagem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toLog(String msg) {
		// TODO Auto-generated method stub
		
	}

}
