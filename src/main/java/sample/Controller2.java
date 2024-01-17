package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller2 {

	@FXML
	public Button button;
	@FXML
	public Label label;

	public void onAction(ActionEvent actionEvent) {
		//切换场景
		CommonCache.stage.setScene(CommonCache.sceneMap.get("1"));


	}
}
