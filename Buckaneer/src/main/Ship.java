package main;


public class Ship {
	public int x;
	public int y;
	private static int money = 5000;
	private static int hull = 100;
	private static int repairPrice = 0;
	private static int[] inventory = new int[7];
	
	public Ship(int startX, int startY) {
		x = startX;
		y = startY;
	}
	
	public static void setRepairPrice() {
		repairPrice = ((100 - hull)*30);
	}
	
	public static int getRepairPrice() {
		return repairPrice;
	}
	
	public static int getMoney() {
		return money;
	}
	
	public static void changeMoney(int change) {
		money += change;
	}
	
	public static int min(int i1, int i2) {
		if (i1 > i2) {
			return i2;
		}
		return i1;
	}
	
	public static int getHull() {
		return hull;
	}
	
	public static void setHull(int newHull) {
		hull = newHull;
	}
	
	public static void changeHull(int nHull) {
		hull += nHull;
	}
	
	public static int[] moneyArray() {
		String intStr = Integer.toString(min(money, 99999999));
		int[] temp = new int[intStr.length()];
		for (int i = 0; i < intStr.length(); i++) {
		    temp[i] = intStr.charAt(i) - '0';
		}
		int[] returning = new int[8];
		for (int i = 0; i < temp.length; i++) {
			returning[i + (returning.length - temp.length)] = temp[i];
		}
		return returning;
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
	
	public static void handleSelling(int sellingID) {
		if (getInventory(sellingID) != 0) {
			changeMoney(GameData.getSellPrice(GameLogic.getCurrentIsland(), sellingID));
			changeInventory(sellingID, -1);
		}else {
			GameLogic.setEventImage(GameData.getNoItemsMessage());
		}
	}
	
	public static void handlePurchase(int purchaseID) {
		if (money >= GameData.getBuyPrice(GameLogic.getCurrentIsland(), purchaseID)) {
			if (getInventory(purchaseID) < 9) {
			changeMoney(-GameData.getBuyPrice(GameLogic.getCurrentIsland(), purchaseID));
			changeInventory(purchaseID, 1);
			}else {
				GameLogic.setEventImage(GameData.getNoSpaceMessage());
			}
			
		}else {
			GameLogic.setEventImage(GameData.getNoMoneyMessage());
		}
	}
}
