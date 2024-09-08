package zh.dragon.zl.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import zh.dragon.zl.entry.FileInformation;
import zh.dragon.zl.util.BackGroundUtil;

import java.io.File;
import java.util.List;

/**
 * @author zwl
 * @date 2024年09月07日 01:37:26
 * @packageName zh.dragon.zl.controller
 * @className PathInformationEditingViewController
 */
public class PathInformationEditingViewController {

	/**
	 * 表格控制器
	 */
	public FileInformationManagementInterfaceViewController fileInformationManagementInterfaceViewController;
	@FXML
	private TextField textField;
	@FXML
	private Label label;
	@FXML
	private ImageView imageView;

	@FXML
	public void initialize() {
		BackGroundUtil.addBackGroundImage("/zh/dragon/zl/images/back1.jpg", (Pane) textField.getParent());
		Image image = new Image("/zh/dragon/zl/images/upload.jpg");
		imageView.setImage(image);
		// 创建一个 Tooltip，并为按钮设置提示信息
		Tooltip tooltip = new Tooltip("上传文件到此，自动覆盖下方路径");
		Tooltip.install(imageView, tooltip);
		// 点击图片打开文件选择器
		imageView.setOnMouseClicked(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("选择文件");

			// 设置文件过滤器，可以选择所有类型或特定类型
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("所有文件", "*.*"));
			File selectedFile = fileChooser.showOpenDialog(textField.getScene().getWindow());

			// 如果选择了文件
			if (selectedFile != null) {
				String filePath = selectedFile.getAbsolutePath(); // 获取文件路径
				label.setText(filePath);
			}
		});


		// 拖拽文件进入时改变样式
		imageView.setOnDragOver(event -> {
			if (event.getGestureSource() != imageView && event.getDragboard().hasFiles()) {
				event.acceptTransferModes(TransferMode.COPY);
			}
			event.consume();
		});

		// 拖拽文件放入图片区域时
		imageView.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasFiles()) {
				success = true;
				// 获取拖拽的文件
				List<File> files = db.getFiles();
				String filePath = files.get(0).getAbsolutePath(); // 获取文件路径
				label.setText(filePath);
			}
			event.setDropCompleted(success);
			event.consume();
		});
	}

	@FXML
	private void submitTitleInformation() {
		FileInformation fileInformation = new FileInformation();
		fileInformation.setName(textField.getText());
		fileInformation.setPath(label.getText());
		fileInformationManagementInterfaceViewController.addOrUpdateTitleInformation(fileInformation);
		closeStage();
	}


	@FXML
	private void closeStage() {
		Stage stage = (Stage) textField.getScene().getWindow();
		stage.close();
	}


	/**
	 * @param fileInformation 修改路径信息
	 * @author zwl
	 * @date 2024/9/7 17:43
	 */
	public void updatePathInformation(FileInformation fileInformation) {
		textField.setText(fileInformation.getName());
		label.setText(fileInformation.getPath());
	}

	/**
	 * @author zwl
	 * @date 2024/9/7 17:45
	 * 保存路径信息
	 */
	private void saveInformation(FileInformation fileInformation) {

	}

}
