package main;


import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
	private  static GameLogic logic = new GameLogic();	
	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setBounds(100, 100, 1280, 720);
		window.setTitle("Buckaneer");;
		window.setResizable(false);
		window.add(logic);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
