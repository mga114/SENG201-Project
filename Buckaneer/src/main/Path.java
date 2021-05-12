package main;

import java.awt.Color;

public class Path {
	int[] xPoints;
	int[] yPoints;
	Color col = Color.gray;
	String label;
	
	public Path() {
		return;
	}
	
	public Path(int[] pXPoints, int[] pYPoints, String pLabel) {
		xPoints = pXPoints;
		yPoints = pYPoints;
		label = pLabel;
	}
	
}
