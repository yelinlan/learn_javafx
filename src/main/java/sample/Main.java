package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("================start================");
		//1.scene
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root);
		Controller controller = fxmlLoader.getController();
		controller.circleRadiusBind(scene);
		controller.move(scene);
		//4.canvas
		FXMLLoader fxmlLoader2 = new FXMLLoader();
		fxmlLoader2.setLocation(getClass().getResource("sample2.fxml"));
		Parent root2 = fxmlLoader2.load();
		Scene scene2 = new Scene(root2);
		Controller2 controller2 = fxmlLoader2.getController();

		//3.scene放入缓存
		CommonCache.stage = primaryStage;
		CommonCache.sceneMap.put("1", scene);
		CommonCache.sceneMap.put("2", scene2);


		//启动
		shutDownWindow(primaryStage);
		primaryStage.setScene(scene2);
		primaryStage.setTitle("大风车");
		primaryStage.getIcons().add(new Image("images/zhihu.jpg"));
		primaryStage.show();
	}

	private void shutDownWindow(Stage primaryStage) {
		Platform.setImplicitExit(false);
		primaryStage.setOnCloseRequest(p -> {
			p.consume();
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("女仆喵的疑问");
			alert.setHeaderText(null);
			alert.setContentText("喵？主人要走了吗？");
			if (alert.showAndWait().get() == ButtonType.OK) {
				Controller2.running=false;
				Platform.exit();
			}
		});
	}


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() {
		System.out.println("================init================");
	}

	@Override
	public void stop() {
		System.out.println("================stop================");
	}
}
