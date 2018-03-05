package controle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Principal extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		HBox principal = (HBox) FXMLLoader.load(getClass().getResource("/asserts/Tela.fxml"));
		Scene root = new Scene(principal);
		primaryStage.setScene(root);
		primaryStage.setTitle("Sistemas Distribuidos");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
