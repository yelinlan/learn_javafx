package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller3 {

	@FXML
	public Canvas canvas;
	@FXML
	public Button button;
	@FXML
	public Button button2;

	private double x;
	private double y;
	private GraphicsContext graphics;

	public void initialize() {
		graphics = canvas.getGraphicsContext2D();
	}

	public void onAction(ActionEvent actionEvent) {
		//切换场景
		CommonCache.stage.setScene(CommonCache.sceneMap.get("1"));


	}


	public void onAction2(ActionEvent actionEvent) {
		WritableImage writableImage = canvas.snapshot(null, null);
		BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("保存canvas图片");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", ".png"));
		File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
		if (file != null) {
			try {
				ImageIO.write(bufferedImage, "PNG", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void onMousePressed(MouseEvent mouseEvent) {
		x = mouseEvent.getX();
		y = mouseEvent.getY();
	}

	public void onMouseDragged(MouseEvent mouseEvent) {
		graphics.strokeLine(x, y, mouseEvent.getX(), mouseEvent.getY());
		x = mouseEvent.getX();
		y = mouseEvent.getY();
	}

}
