package main;

import java.awt.Graphics;
import java.awt.Image;

public class Object extends GameLogic{
	private Image sprite;
	private int x;
	private int y;
	
	
	public Object(Image imgSprite, int posX, int posY) {
		sprite = imgSprite;
		x = posX;
		y = posY;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int newX) {
		x = newX;
	}
	
	public void setY(int newY) {
		y = newY;
	}
}
