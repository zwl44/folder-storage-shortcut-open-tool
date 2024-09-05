package zh.dragon.zl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * @author zwl
 * @date 2024年09月05日 00:30:36
 * @packageName zh.dragon.zl
 * @className MainApp
 */
public class MainApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Label label = new Label("Hello, JavaFX!");
		StackPane root = new StackPane();
		root.getChildren().add(label);

		Scene scene = new Scene(root, 300, 200);

		primaryStage.setTitle("JavaFX with Maven");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
