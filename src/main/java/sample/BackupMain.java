//package sample;
//
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.TransferMode;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.stage.Stage;
//
//
//public class BackupMain extends Application {
//
//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		System.out.println("================start================");
//		Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//
//		//1.btn
//		Button button = new Button("获取最新页面");
//		button.setLayoutX(100);
//		button.setLayoutY(100);
//		AnchorPane pane = new AnchorPane();
//		//2.circle
//				Circle circle = new Circle();
//				circle.setCenterX(250);
//				circle.setCenterY(250);
//				circle.setRadius(100);
//				circle.setStroke(Color.BLACK);
//				circle.setFill(Color.WHITE);
//		//3.text
//				TextField textField = new TextField();
//				textField.setLayoutX(200);
//				textField.setLayoutY(100);
//				textField.setText("主人~，这里可以放文件哦~");
//				textField.setOnDragOver(p -> p.acceptTransferModes(TransferMode.ANY));
//				textField.setOnDragDropped(p -> {
//					if (p.getDragboard().hasFiles()) {
//						textField.setText(p.getDragboard().getFiles().get(0).getAbsolutePath());
//					}
//				});
//		//4.pane
//				pane.getChildren().addAll(button, circle, textField);
//		//5.scene
//		Scene scene = new Scene(root, 500, 500);
//		//绑定半径属性 可以随绑定的属性值一起变动
//				circle.radiusProperty().bind(scene.widthProperty().divide(6));
//		//上下左右移动
//				scene.setOnKeyPressed(p -> {
//					if (p.getCode().equals(KeyCode.DOWN)) {
//						circle.setCenterY(circle.getCenterY() - 5);
//					} else if (p.getCode().equals(KeyCode.UP)) {
//						circle.setCenterY(circle.getCenterY() + 5);
//					} else if (p.getCode().equals(KeyCode.LEFT)) {
//						circle.setCenterX(circle.getCenterX() - 5);
//					} else if (p.getCode().equals(KeyCode.RIGHT)) {
//						circle.setCenterX(circle.getCenterX() + 5);
//					}
//				});
//
//
//				Button button2 = new Button("返回主页");
//				button2.setLayoutX(100);
//				button2.setLayoutY(100);
//				AnchorPane pane2 = new AnchorPane();
//				pane2.getChildren().add(button2);
//				pane2.getChildren().add(new Label("主人~，暂时没有找到可用的资源，喵~"));
//				Scene scene2 = new Scene(pane2, 500, 500);
//
//				//切换场景
//				button.setOnAction(p -> primaryStage.setScene(scene2));
//				button2.setOnAction(p -> primaryStage.setScene(scene));
//
//
//		shutDownWindow(primaryStage);
//		primaryStage.setScene(scene);
//		primaryStage.setTitle("知乎热点");
//		primaryStage.getIcons().add(new Image("images/zhihu.jpg"));
//		primaryStage.show();
//	}
//
//	private void shutDownWindow(Stage primaryStage) {
//		Platform.setImplicitExit(false);
//		primaryStage.setOnCloseRequest(p -> {
//			p.consume();
//			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//			alert.setTitle("女仆喵的疑问");
//			alert.setHeaderText(null);
//			alert.setContentText("喵？主人要走了吗？");
//			if (alert.showAndWait().get() == ButtonType.OK) {
//				Platform.exit();
//			}
//		});
//	}
//
//
//	public static void main(String[] args) {
//		launch(args);
//	}
//
//	@Override
//	public void init() {
//		System.out.println("================init================");
//	}
//
//	@Override
//	public void stop() {
//		System.out.println("================stop================");
//	}
//}
