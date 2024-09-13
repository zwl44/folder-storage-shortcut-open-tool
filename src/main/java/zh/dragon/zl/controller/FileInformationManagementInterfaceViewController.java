package zh.dragon.zl.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import zh.dragon.zl.MainApp;
import zh.dragon.zl.entry.FileInformation;
import zh.dragon.zl.util.BackGroundUtil;
import zh.dragon.zl.util.JsonUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zwl
 * @date 2024年09月05日 23:24:49
 * @packageName zh.dragon.zl.controller
 * @className FileInformationManagementInterfaceViewController
 * 主界面控制类
 */
public class FileInformationManagementInterfaceViewController {
	private final ObservableList<FileInformation> fileInfoList = FXCollections.observableArrayList();
	@FXML
	private BorderPane borderPane;
	@FXML
	private TableView<FileInformation> tableView;
	@FXML
	private TableColumn<FileInformation, Integer> idColumn;
	@FXML
	private TableColumn<FileInformation, String> nameColumn;
	@FXML
	private TableColumn<FileInformation, String> pathColumn;

	@FXML
	public void initialize() {
		BackGroundUtil.addBackGroundImage("/zh/dragon/zl/images/back.jpg", borderPane);


		idColumn.setCellFactory(new Callback<TableColumn<FileInformation, Integer>, TableCell<FileInformation, Integer>>() {
			@Override
			public TableCell<FileInformation, Integer> call(TableColumn<FileInformation, Integer> param) {
				return new TableCell() {
					@Override
					protected void updateItem(Object item, boolean empty) {
						super.updateItem(item, empty);
						this.setText(null);
						this.setGraphic(null);
						if (!empty) {
							int rowIndex = this.getIndex() + 1;
							this.setText(String.valueOf(rowIndex));
						}
					}

				};
			}
		});

		// Set up the columns
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		pathColumn.setCellValueFactory(new PropertyValueFactory<>("path"));

		//saveTableData();
		List<FileInformation> res = JsonUtil.loadFromJson(getClass().getResourceAsStream("/zh/dragon/zl/json/data.json"));
		//List<FileInformation> res = JsonUtil.loadFromJson("data/data.json");
		ObservableList<FileInformation> observableList = FXCollections.observableList(res);

		tableView.setItems(observableList);

		//监听双击
		// 监听表格的行双击事件
		tableView.setRowFactory(tv -> {
			TableRow<FileInformation> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					FileInformation rowData = row.getItem();
					openFileOrFolder(rowData.getPath());
				}
			});
			return row;
		});
	}


	/**
	 * 打开文件或者文件夹
	 *
	 * @author zwl
	 * @date 2024/9/7 17:59
	 */
	@FXML
	public void openWindowOrFiles() {
		openFileOrFolder(tableView.getSelectionModel().getSelectedItem().getPath());
	}

	// 打开文件或文件夹的方法
	private void openFileOrFolder(String path) {
		File file = new File(path);
		if (file.exists()) {
			try {
				// 获取当前操作系统的Desktop对象，并打开文件/文件夹
				Desktop desktop = Desktop.getDesktop();
				desktop.open(file);
			} catch (IOException e) {
				showAlert("无法打开文件或文件夹: " + e.getMessage());
			}
		} else {
			showAlert("文件或文件夹不存在: " + path);
		}
	}

	// 显示提示框
	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("错误");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	/**
	 * 新增路径信息
	 *
	 * @author zwl
	 * @date 2024/9/7 17:59
	 */
	@FXML
	public void addPathInformation() {
		popWindows();
	}

	/**
	 * 修改路径信息
	 *
	 * @author zwl
	 * @date 2024/9/7 17:59
	 */
	@FXML
	public void updatePathInformation() {
		PathInformationEditingViewController pathInformationEditingViewController = popWindows();
		FileInformation selectedItem = tableView.getSelectionModel().getSelectedItem();
		pathInformationEditingViewController.updatePathInformation(selectedItem);
	}

	@FXML
	private void deletePathInformation() {
		int index = tableView.getSelectionModel().getSelectedIndex();
		tableView.getItems().remove(index);
		saveTableData();
	}

	/**
	 * 添加表格数据 并且保存到json
	 *
	 * @author zwl
	 * @date 2024/9/7 18:38
	 */
	public void addOrUpdateTitleInformation(FileInformation fileInformation) {
		ObservableList<FileInformation> tableViewItems = tableView.getItems();
		AtomicBoolean isCz = new AtomicBoolean(false);
		tableViewItems.forEach((e) -> {
			if (e.getName().equals(fileInformation.getName())) {
				isCz.set(true);
				e.setPath(fileInformation.getPath());
				tableView.refresh();
			}
		});
		if (!isCz.get()) {
			tableViewItems.add(fileInformation);
		}
		saveTableData();
	}


	/**
	 * 保存数据
	 *
	 * @author zwl
	 * @date 2024/7/14 16:19
	 */
	private void saveTableData() {
		ObservableList<FileInformation> items = tableView.getItems();
		JsonUtil.saveToJson("data/data.json", items);
		//JsonUtil.saveToJson("/zh/dragon/zl/json/data.json", items);
	}

	private PathInformationEditingViewController popWindows() {
		// Load the fxml file and create a new stage for the popup dialog.
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("controller/PathInformationEditingView.fxml"));
		//获取弹窗的控制器
		Pane page = null;
		try {
			page = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PathInformationEditingViewController pathInformationEditingViewController = loader.getController();
		pathInformationEditingViewController.fileInformationManagementInterfaceViewController = this;
		// Create the dialog Stage.
		Stage dialogStage = new Stage();
		dialogStage.setTitle("New PathInformation");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(tableView.getScene().getWindow());
		assert page != null;
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);
		dialogStage.show();
		return pathInformationEditingViewController;
	}
}
