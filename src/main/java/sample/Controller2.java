package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Controller2 {

	public static final String DEFAULT = "-fx-border-color: null;";
	private static double[] a;
	private static double[] b;
	private static double theta = 0;
	public static boolean running = true;
	private static final Map<String, Color> colorMap = new TreeMap<>();
	private static final Map<String, Integer> radiusMap = new TreeMap<>();
	private static final Map<String, Integer> leafMap = new TreeMap<>();
	Color color = Color.color(1, 0, 0);
	private static int speed = 20;
	int leafs;
	double r;
	double l;//叶的一边
	double rx = 300;
	double ry = 300;
	private GraphicsContext g;


	static {
		colorMap.put("黑色", Color.BLACK);
		colorMap.put("蓝色", Color.BLUE);
		colorMap.put("蓝绿色", Color.CYAN);
		colorMap.put("深灰色", Color.DARKGRAY);
		colorMap.put("灰色", Color.GRAY);
		colorMap.put("绿色", Color.GREEN);
		colorMap.put("浅灰色", Color.LIGHTGRAY);
		colorMap.put("紫红色", Color.MAGENTA);
		colorMap.put("橙色", Color.ORANGE);
		colorMap.put("粉红色", Color.PINK);
		colorMap.put("红色", Color.RED);
		colorMap.put("白色", Color.WHITE);
		colorMap.put("黄色", Color.YELLOW);
		for (int i = 1; i < 20; i++) {
			radiusMap.put(String.valueOf(i * 50), i * 50);
			leafMap.put(String.valueOf(i + 2), i + 2);
		}
	}

	@FXML
	private ListView<String> colorList;

	@FXML
	private ListView<String> leafList;

	@FXML
	private ListView<String> radiusList;

	@FXML
	private TextField leafsField;

	@FXML
	private TextField radiusField;

	@FXML
	public Canvas canvas;

	@FXML
	private Button start;

	@FXML
	private Button stop;

	@FXML
	private Button add;

	@FXML
	private Button minus;

	public void initialize() {
		g = canvas.getGraphicsContext2D();
		fans(3, 200, canvas.getHeight() / 2, canvas.getWidth() / 2);
		colorList.getItems().addAll(colorMap.keySet());
		addColorListener(colorList);
		leafList.getItems().addAll(leafMap.keySet().stream().sorted(Comparator.comparingInt(Integer::parseInt))
				.collect(Collectors.toList()));
		addLeafListener(leafList);
		radiusList.getItems().addAll(radiusMap.keySet().stream().sorted(Comparator.comparingInt(Integer::parseInt))
				.collect(Collectors.toList()));
		addRadiusListener(radiusList);
		leafsField.textProperty().addListener((observable, oldValue, newValue) -> leafText());
		radiusField.textProperty().addListener((observable, oldValue, newValue) -> radiusText());
		new Thread(this::run).start();
	}

	private void paint() {
		g.setFill(color);
		g.fillPolygon(a, b, leafs * 3);
	}

	private void addColorListener(ListView<String> colorList) {
		colorList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				color = colorMap.get(newValue);
			}
		});
	}

	private void addLeafListener(ListView<String> colorList) {
		colorList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				leafs = leafMap.get(newValue);
				init();
			}
		});
	}

	private void addRadiusListener(ListView<String> colorList) {
		colorList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				r = radiusMap.get(newValue);
				l = r * Math.cos(Math.PI / leafs);
			}
		});
	}

	void leafText() {
		if (leafsField.getText() == null) {
			leafsField.setStyle(DEFAULT);
		}
		try {
			int leafInput = Integer.parseInt(leafsField.getText());
			if (leafInput > 2) {
				leafs = leafInput;
				init();
				leafsField.setStyle(DEFAULT);
			} else {
				throw new RuntimeException();
			}
		} catch (Exception n) {
			leafsField.setStyle("-fx-border-color: red;");
		}
	}

	void radiusText() {
		if (radiusField.getText() == null) {
			radiusField.setStyle(DEFAULT);
		}
		try {
			int radiusInput = Integer.parseInt(radiusField.getText());
			if (radiusInput > 0) {
				r = radiusInput;
				l = r * Math.cos(Math.PI / leafs);
				radiusField.setStyle(DEFAULT);
			}
		} catch (Exception m) {
			radiusField.setStyle("-fx-border-color: red;");
		}
	}

	void init() {                                             //画风扇
		a = new double[leafs * 3];
		b = new double[leafs * 3];
		l = r * Math.cos(Math.PI / leafs);
		double j = 0;
		for (int i = 0; i < leafs; i++) {
			a[3 * i] = (int) (rx + r * Math.cos(j));
			b[3 * i] = (int) (ry + r * Math.sin(j));
			a[3 * i + 1] = (int) (rx + l * Math.cos(j - Math.PI / leafs));
			b[3 * i + 1] = (int) (ry + l * Math.sin(j - Math.PI / leafs));
			a[3 * i + 2] = (int) rx;
			b[3 * i + 2] = (int) ry;
			j = j + 2 * Math.PI / leafs;
		}
	}

	void rotate(double theta) {                                 //旋转
		for (int i = 0; i < leafs; i++) {
			a[3 * i] = (int) (rx + r * Math.cos(theta + i * 2 * Math.PI / leafs));
			b[3 * i] = (int) (ry + r * Math.sin(theta + i * 2 * Math.PI / leafs));
			a[3 * i + 1] = (int) (rx + l * Math.cos(theta + i * 2 * Math.PI / leafs - Math.PI / leafs));
			b[3 * i + 1] = (int) (ry + l * Math.sin(theta + i * 2 * Math.PI / leafs - Math.PI / leafs));
		}
	}

	public void run() {                                        //run方法
		try {
			while (running) {
				Thread.sleep(speed);
				g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				rotate(theta);
				paint();
				theta = theta + Math.PI / 180;
			}
		} catch (Exception ignored) {}
	}

	public void fans(int leafs, double r, double rx, double ry) {  //构造方法
		this.leafs = leafs;
		this.r = r;
		this.rx = rx;
		this.ry = ry;
		init();
	}

	@FXML
	void clickStart(MouseEvent event) {
		if (!running) {
			running = true;
			start.setText("停止");
			new Thread(this::run).start();
		} else {
			running = false;
			start.setText("启动");
		}
	}

	@FXML
	void addClick(MouseEvent event) {
		if (speed - 1 == 0) {
			add.setStyle("-fx-border-color: red;");
		} else {
			add.setStyle(DEFAULT);
			speed -= 1;
		}
		System.out.println(speed);
	}

	@FXML
	void minusClick(MouseEvent event) {
		if (speed - 1 != 0) {
			add.setStyle(DEFAULT);
		}
		speed += 1;
		System.out.println(speed);
	}

}
