package controle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Principal extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane principal = FXMLLoader.load(getClass().getResource("/asserts/ScreenVm.fxml"));
		Scene root = new Scene(principal);
		root.getStylesheets().add("/asserts/style.css");
		primaryStage.setScene(root);
		primaryStage.setTitle("Sistemas Distribuidos");
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
			Platform.exit();
			System.exit(0);
			}
		});
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
