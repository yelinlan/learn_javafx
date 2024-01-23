package com.yll;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fans extends JFrame implements Runnable, ListSelectionListener {
	private JList radiusList, leafList, colorList;
	private JLabel radiusBar, leafsBar;
	private JTextField radiusField, leafsField;

	//可以用container调用ContentPane()，再添加
	JScrollPane scrollPane1, scrollPane2, scrollPane3;
	private static int[] a;
	private static int[] b;
	private static double theta = 0;
	private final String radiusName[] = {"50", "100", "150", "200", "250", "300", "350", "400"};
	private final String leafName[] = {"3", "4", "5", "6", "7", "8", "9", "10", "17"};
	private final String colorNames[] = {"黑色", "蓝色", "蓝绿色", "深灰色", "灰色", "绿色", "浅灰色", "紫红色", "橙色", "粉红色", "红色", "白色",
			"黄色"};

	private final int radius[] = {50, 100, 150, 200, 250, 300, 350, 400};
	private final int leaf[] = {3, 4, 5, 6, 7, 8, 9, 10, 17};
	private final Color colors[] = {Color.BLACK, Color.BLUE, Color.CYAN, Color.darkGray, Color.GRAY, Color.GREEN,
			Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
	Color color = new Color(255, 0, 0);
	int speed = 300;
	int leafs;
	double r;
	double l;//叶的一边
	double rx = 300;
	double ry = 300;

	Fans(int leafs, double r, double rx, double ry) {  //构造方法
		super("大风车");
		this.leafs = leafs;
		this.r = r;
		this.rx = rx;
		this.ry = ry;
		init();
		createScrollPane();       /*通过下拉表选择半径，叶数，颜色*/
		createTextField();           /*通过文本框输入半径，叶数*/
		super.setLayout(null);
		setSize(600, 600);                                    /*设置窗口*/
		setLocation(450, 0);
		setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void createScrollPane() {          /*通过下拉表选择半径，叶数，颜色*/
		radiusList = new JList(radiusName);
		//radiusList.setVisibleRowCount(5);
		//radiusList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//radiusList.setBounds();
		scrollPane1 = new JScrollPane(radiusList);
		scrollPane1.setBounds(100, 10, 150, 100);
		super.add(scrollPane1);//container.add(scrollPane);
		radiusList.addListSelectionListener(this);

		leafList = new JList(leafName);                          /*选择叶数*/
		scrollPane2 = new JScrollPane(leafList);
		scrollPane2.setBounds(300, 10, 150, 100);
		super.add(scrollPane2);
		leafList.addListSelectionListener(this);

		colorList = new JList(colorNames);                   /*选择颜色*/
		scrollPane3 = new JScrollPane(colorList);
		scrollPane3.setBounds(500, 10, 150, 100);
		super.add(scrollPane3);
		colorList.addListSelectionListener(this);
	}

	void createTextField() {                           /*通过文本框输入半径，叶数*/
		radiusBar = new JLabel("半径:");
		radiusBar.setBounds(5, 480, 80, 20);
		radiusField = new JTextField(25);
		radiusField.setBounds(90, 480, 100, 20);
		leafsBar = new JLabel("叶片数:");
		leafsBar.setBounds(5, 525, 80, 20);
		leafsField = new JTextField("");
		leafsField.setBounds(90, 525, 100, 20);
		super.add(radiusBar);
		super.add(radiusField);
		super.add(leafsBar);
		super.add(leafsField);
		radiusField.addActionListener(new radiusListener());
		leafsField.addActionListener(new leafListener());
	}

	//
	class leafListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {     //接口ActionListener
			try {
				if (Integer.parseInt(leafsField.getText()) > 2) {
					leafs = Integer.parseInt(leafsField.getText());
					init();
				} else {
					System.out.println("请重新输入！");
				}
			} catch (Exception n) {
				System.out.println("请重新输入！");
			}
		}
	}

	//
	class radiusListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {     //接口ActionListener
			try {
				if (Integer.parseInt(radiusField.getText()) > 0) {
					r = Integer.parseInt(radiusField.getText());
					l = r * Math.cos(Math.PI / leafs);
				}
			} catch (Exception m) {
				System.out.println("请重新输入！");
			}
		}
	}

	public void valueChanged(ListSelectionEvent event)   //接口ListSelectionListener
	{
		if (radiusList.getSelectedIndex() != -1) {
			r = radius[radiusList.getSelectedIndex()];
			l = r * Math.cos(Math.PI / leafs);
		}
		if (leafList.getSelectedIndex() != -1) {
			leafs = leaf[leafList.getSelectedIndex()];
			init();
		}
		if (colorList.getSelectedIndex() != -1) {
			color = colors[colorList.getSelectedIndex()];
		}
	}

	void init() {                                             //画风扇
		a = new int[leafs * 3];
		b = new int[leafs * 3];
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

	public void paint(Graphics g) {                             //画图
		super.paint(g);
		g.setColor(color);
		g.fillPolygon(a, b, leafs * 3);
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
			while (true) {
				Thread.sleep(speed);
				rotate(theta);
				this.repaint();
				theta = theta + Math.PI / 180;
			}
		} catch (Exception e) {}
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);//设置窗口的外观和感觉为Java默认
		Thread fans = new Thread(new Fans(3, 200, 300, 300));         //线程
		fans.start();
	}

}