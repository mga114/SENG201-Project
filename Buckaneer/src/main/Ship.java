package main;

import java.util.*;

public class Ship {
	public int x;
	public int y;
	private static int money = 3000;
	private static int[] inventory = new int[7];
	
	public Ship(int startX, int startY) {
		x = startX;
		y = startY;
	}
	
	public void move(int newX, int newY, int invSpeed) {
		return;
	}
	
	public static int getMoney() {
		return money;
	}
	
	public static void changeMoney(int change) {
		money += change;
	}
	
	public static int[] getInventory() {
		return inventory;
	}
	
	public static int getInventory(int pos) {
		return inventory[pos];
	}
	
	public static void setInventory(int[] nInventory) {
		inventory = nInventory;
	}
	
	public static void setInventory(int pos, int value) {
		inventory[pos] = value;
	}
	
	public static void changeInventory(int pos, int value) {
		inventory[pos] += value;
	}
	
	public static void handlePurchase(int purchaseID) {
		System.out.println(money >= GameData.getSellingPrice(purchaseID));
		if (money >= GameData.getSellingPrice(purchaseID)) {
			changeMoney(-GameData.getSellingPrice(purchaseID));
			changeInventory(purchaseID, 1);
		}
	}
}
