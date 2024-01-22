package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Circle;

public class Controller {

	@FXML
	public Circle circle;
	@FXML
	public TextField textField;
	@FXML
	public Button button;
	@FXML
	public Button button2;

	public void onDragOver(DragEvent dragEvent) {
		dragEvent.acceptTransferModes(TransferMode.ANY);
	}

	public void onDragDropped(DragEvent dragEvent) {
		if (dragEvent.getDragboard().hasFiles()) {
			textField.setText(dragEvent.getDragboard().getFiles().get(0).getAbsolutePath());
		}
	}

	public void initialize() {
		//标签绑定完成后 会执行这个
	}

	public void circleRadiusBind(Scene scene) {
		//绑定半径属性 可以随绑定的属性值一起变动
		circle.radiusProperty().bind(scene.widthProperty().divide(6));
	}

	public void move(Scene scene) {
		//上下左右移动
		scene.setOnKeyPressed(p -> {
			if (p.getCode().equals(KeyCode.DOWN)) {
				circle.setCenterY(circle.getCenterY() + 5);
			} else if (p.getCode().equals(KeyCode.UP)) {
				circle.setCenterY(circle.getCenterY() - 5);
			} else if (p.getCode().equals(KeyCode.LEFT)) {
				circle.setCenterX(circle.getCenterX() - 5);
			} else if (p.getCode().equals(KeyCode.RIGHT)) {
				circle.setCenterX(circle.getCenterX() + 5);
			}
		});
	}

	public void onAction(ActionEvent actionEvent) {
		CommonCache.stage.setScene(CommonCache.sceneMap.get("2"));
	}

	public void onAction2(ActionEvent actionEvent) {
		CommonCache.stage.setScene(CommonCache.sceneMap.get("2"));
	}

	public void onAction3(ActionEvent actionEvent) {
		CommonCache.stage.setScene(CommonCache.sceneMap.get("4"));
	}
}
