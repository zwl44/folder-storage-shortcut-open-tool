package zh.dragon.zl.util;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

/**
 * @author zwl
 * @date 2024年09月13日 20:50:25
 * @packageName zh.dragon.zl.util
 * @className MinWindowUtil
 */
public class MinWindowUtil {
	private static final MenuItem SHOW_ITEM;
	private static final MenuItem EXIT_ITEM;
	private static final TrayIcon TRAY_ICON;
	private static ActionListener showListener;
	private static ActionListener exitListener;
	private static MouseAdapter mouseListener;
	private static MinWindowUtil instance;

	static {
		//执行stage.close()方法,窗口不直接退出
		Platform.setImplicitExit(false);
		//菜单项(打开)中文乱码的问题是编译器的锅,如果使用IDEA,需要在Run-Edit Configuration在LoginApplication中的VM Options中添加-Dfile.encoding=GBK
		//如果使用Eclipse,需要右键Run as-选择Run Configuration,在第二栏Arguments选项中的VM Options中添加-Dfile.encoding=GBK
		SHOW_ITEM = new MenuItem("打开主界面(Open)");
		//菜单项(退出)
		EXIT_ITEM = new MenuItem("关闭程序(Exit)");
		//此处不能选择ico格式的图片,要使用16*16的png格式的图片
		URL url = MinWindowUtil.class.getResource("/zh/dragon/zl/images/icon.png");
		Image image = Toolkit.getDefaultToolkit().getImage(url);
		//系统托盘图标
		TRAY_ICON = new TrayIcon(image);
		//初始化监听事件(空)
		showListener = e -> Platform.runLater(() -> {
		});
		exitListener = e -> {
		};
		mouseListener = new MouseAdapter() {
		};
	}

	private MinWindowUtil() {
		try {
			//检查系统是否支持托盘
			if (!SystemTray.isSupported()) {
				//系统托盘不支持
				return;
			}
			//设置图标尺寸自动适应
			TRAY_ICON.setImageAutoSize(true);
			//系统托盘
			SystemTray tray = SystemTray.getSystemTray();
			//弹出式菜单组件
			final PopupMenu popup = new PopupMenu();
			popup.add(SHOW_ITEM);
			popup.add(EXIT_ITEM);
			TRAY_ICON.setPopupMenu(popup);
			//鼠标移到系统托盘,会显示提示文本
			TRAY_ICON.setToolTip("路径记录工具");
			tray.add(TRAY_ICON);
		} catch (Exception e) {
			//系统托盘添加失败
		}
	}

	public static MinWindowUtil getInstance() {
		if (instance == null) {
			instance = new MinWindowUtil();
		}
		return instance;
	}

	/**
	 * 更改系统托盘所监听的Stage
	 */
	public void listen(Stage stage) {
		//防止报空指针异常
		if (showListener == null || exitListener == null || mouseListener == null || SHOW_ITEM == null || EXIT_ITEM == null || TRAY_ICON == null) {
			return;
		}
		//移除原来的事件
		SHOW_ITEM.removeActionListener(showListener);
		EXIT_ITEM.removeActionListener(exitListener);
		TRAY_ICON.removeMouseListener(mouseListener);
		//行为事件: 点击"打开"按钮,显示窗口
		showListener = e -> Platform.runLater(() -> showStage(stage));
		//行为事件: 点击"退出"按钮, 就退出系统
		exitListener = e -> {
			System.exit(0);
		};
		//鼠标行为事件: 单机显示stage
		mouseListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//鼠标左键
				if (e.getButton() == MouseEvent.BUTTON1) {
					showStage(stage);
				}
			}
		};
		//给菜单项添加事件
		SHOW_ITEM.addActionListener(showListener);
		EXIT_ITEM.addActionListener(exitListener);
		//给系统托盘添加鼠标响应事件
		TRAY_ICON.addMouseListener(mouseListener);
	}

	/**
	 * 关闭窗口
	 */
	public void hide(Stage stage) {
		Platform.runLater(() -> {
			//如果支持系统托盘,就隐藏到托盘,不支持就直接退出
			if (SystemTray.isSupported()) {
				//stage.hide()与stage.close()等价
				stage.hide();
			} else {
				System.exit(0);
			}
		});
	}

	/**
	 * 点击系统托盘,显示界面(并且显示在最前面,将最小化的状态设为false)
	 */
	private void showStage(Stage stage) {
		//点击系统托盘,
		Platform.runLater(() -> {
			if (stage.isIconified()) {
				stage.setIconified(false);
			}
			if (!stage.isShowing()) {
				stage.show();
			}
			stage.toFront();
		});
	}

}
