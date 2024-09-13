package zh.dragon.zl;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import zh.dragon.zl.util.MinWindowUtil;


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
		// 设置应用程序图标
		Image icon = new Image(getClass().getResourceAsStream("/zh/dragon/zl/images/icon.png"));
		primaryStage.getIcons().add(icon);
		MinWindowUtil.getInstance().listen(primaryStage);
		BorderPane borderPane = FXMLLoader.load(getClass().getResource("controller/MainInterfaceView.fxml"));
		BorderPane fileInformationManagementPane = FXMLLoader.load(getClass().getResource("controller/FileInformationManagementInterfaceView.fxml"));
		borderPane.setCenter(fileInformationManagementPane);
		AnchorPane anchorPanePane = FXMLLoader.load(getClass().getResource("controller/CommonView.fxml"));
		borderPane.setRight(anchorPanePane);
		primaryStage.setTitle("路径记录工具");
		primaryStage.setScene(new Scene(borderPane, 1100, 600));
		primaryStage.show();
	}


}
