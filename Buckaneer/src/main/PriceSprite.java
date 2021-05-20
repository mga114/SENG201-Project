package main;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class PriceSprite {
	
	private Image sprite;
	private int x;
	private int y;
	private static Toolkit t = Toolkit.getDefaultToolkit();
	
	public PriceSprite(Image nSprite, int nx, int ny) {
		sprite = nSprite;
		x = nx;
		y = ny;
	}
	
	public static ArrayList<PriceSprite> constructShopText(){
		ArrayList<PriceSprite> returning = new ArrayList<PriceSprite>();
		//inventory and selling and buying
		for(int i=0; i<Ship.getInventory().length; i++) {
			Image tempSprite = t.getImage("images/text/n" + Integer.toString(Ship.getInventory(i)) + ".png");
			returning.add(new PriceSprite(tempSprite, 400, 260 + (53 * i)));
			//selling
			int price = GameData.getSellPrice(GameLogic.getCurrentIsland(), i);
			tempSprite = t.getImage("images/text/n" + Integer.toString((int) Math.floor(price / (100))) + ".png");
			returning.add(new PriceSprite(tempSprite, 460, 260 + (53 * i)));
			int midPrice = (int) Math.floor((price - (Math.floor(price/100) * 100)) / 10);
			tempSprite = t.getImage("images/text/n" + Integer.toString(midPrice) + ".png");
			returning.add(new PriceSprite(tempSprite, 475, 260 + (53 * i)));
			tempSprite = t.getImage("images/text/n" + Integer.toString((int) (price % 10)) + ".png");
			returning.add(new PriceSprite(tempSprite, 490, 260 + (53 * i)));
			//buying
			price = GameData.getBuyPrice(GameLogic.getCurrentIsland(), i);
			tempSprite = t.getImage("images/text/n" + Integer.toString((int) Math.floor(price / (100))) + ".png");
			returning.add(new PriceSprite(tempSprite, 650, 260 + (53 * i)));
			midPrice = (int) Math.floor((price - (Math.floor(price/100) * 100)) / 10);
			tempSprite = t.getImage("images/text/n" + Integer.toString(midPrice) + ".png");
			returning.add(new PriceSprite(tempSprite, 665, 260 + (53 * i)));
			tempSprite = t.getImage("images/text/n" + Integer.toString((int) (price % 10)) + ".png");
			returning.add(new PriceSprite(tempSprite, 680, 260 + (53 * i)));
		}
		//hull display
		Image tempSprite = t.getImage("images/text/n" + Integer.toString((int) Math.floor(Ship.getHull() / 100)) + ".png");
		returning.add(new PriceSprite(tempSprite, 540, 140));
		int middleInt = (int) Math.floor((Ship.getHull() - (Math.floor(Ship.getHull()/100) * 100)) / 10);
		tempSprite = t.getImage("images/text/n" + Integer.toString(middleInt) + ".png");
		returning.add(new PriceSprite(tempSprite, 555, 140));
		tempSprite = t.getImage("images/text/n" + Integer.toString(Ship.getHull()% 10) + ".png");
		returning.add(new PriceSprite(tempSprite, 570, 140));
		//money display
		returning.add(new PriceSprite(t.getImage("images/text/$.png"), 715, 110));
		int[] money = Ship.moneyArray();
		for (int i=0; i < money.length; i++) {
			tempSprite = t.getImage("images/text/" + Integer.toString(money[i]) + ".png");
			returning.add(new PriceSprite(tempSprite, 745 + (i * 30), 115));
		}
		//finish and pay display
		//Ship.setRepairPrice();
		String rP = Integer.toString(Ship.getRepairPrice());
		
		while (rP.length() < 4) {
			rP = "0" + rP;
		}
		
		for(int i=0; i < 4; i++) {
			tempSprite = t.getImage("images/text/n" + rP.charAt(i) + ".png");
			returning.add(new PriceSprite(tempSprite, 850 + (15 * i), 580));
		}
		return returning;
	}
	
	public Image getImage() {
		return sprite;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
