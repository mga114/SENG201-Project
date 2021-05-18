package main;

public class Button {
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	
	public Button(int x1, int x2, int y1, int y2) {
		minX = x1;
		maxX = x2;
		minY = y1;
		maxY = y2;
		
	}
	
	public boolean pressed(int mouseX, int mouseY) {
		return (mouseX > minX && mouseX < maxX && mouseY > minY && mouseY < maxY);
	}
	
}
