package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
	
	private TicToc clock;
	
	public Main() {
		 clock = new TicToc();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/main.fxml"));
		fxmlLoader.setController(clock);
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Tic Toc");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("png/icon.png")));
		clock.loaderPane("fxml/tic.fxml");
		primaryStage.show();
		clock.playTic();
	}
}