package main;


public class Ship {
	public int x;
	public int y;
	private static int money = 5000;
	private static int hull = 100;
	private static int repairPrice = 0;
	private static int[] inventory = new int[7];
	private static int repairModifier = 0;
	private static int eventChance = 2000;
	private static int selectedDays = 2000;
	private static int fightingChance = 3;
	private static double sellingModifier = 0;
	
	public Ship(int startX, int startY) {
		x = startX;
		y = startY;
	}
	
	public static void setRepairPrice() {
		repairPrice = ((100 - hull)*(30 - repairModifier));
	}
	
	public static int getRepairPrice() {
		return repairPrice;
	}
	
	public static int getProfit() {
		return Math.max(0, money-5000);
	}
	
	public static void setRepairModifier(int modifier) {
		repairModifier = modifier;
	}
	
	public static void setFightingChance(int chance) {
		fightingChance = chance;
	}
	
	public static int getFightingChance() {
		return fightingChance;
	}
	
	public static void setEventChance(int chance) {
		eventChance = chance;
	}
	
	public static int getEventChance() {
		return eventChance;
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
	
	public static boolean inventoryEmpty() {
		for (int i=0; i < inventory.length; i++) {
			if (inventory[i] > 0) {
				return false;
			}
		}
		return true;
	}
	
	public static void handleSelling(int sellingID) {
		if (getInventory(sellingID) != 0) {
			int cost = GameData.getSellPrice(GameLogic.getCurrentIsland(), sellingID);
			changeMoney(cost);
			changeInventory(sellingID, -1);
			InventoryHandler.itemSold(sellingID, GameLogic.getCurrentIsland(), cost);
		}else {
			GameLogic.setEventImage(GameData.getNoItemsMessage());
		}
	}
	
	public static void handlePurchase(int purchaseID) {
		if (money >= GameData.getBuyPrice(GameLogic.getCurrentIsland(), purchaseID)) {
			if (getInventory(purchaseID) < 9) {
			int cost = GameData.getBuyPrice(GameLogic.getCurrentIsland(), purchaseID);
			changeMoney(-cost);
			changeInventory(purchaseID, 1);
			InventoryHandler.itemPurchased(purchaseID, GameLogic.getCurrentIsland(), cost);
			}else {
				GameLogic.setEventImage(GameData.getNoSpaceMessage());
			}
			
		}else {
			GameLogic.setEventImage(GameData.getNoMoneyMessage());
		}
	}
	
	public static void handleRepair() {
		System.out.println(GameLogic.getGlobalTime());
		if(GameLogic.getGlobalTime() == 0) {
			GameLogic.state = State.GAMEOVER;
			GameLogic.setGameOverImage(GameData.getGameOverGood());
		}else if(money >= repairPrice) {
			hull = 100;
			money -= repairPrice;
			GameLogic.state = State.MAP;
		}else {
			if (inventoryEmpty()) {
				GameLogic.state = State.GAMEOVER;
				GameLogic.setGameOverImage(GameData.getGameOverMoney());
			}else {
				GameLogic.setEventImage(GameData.getRepairError());
			}
		}
	}
	
	public static int inventoryWorth() {
		int worth = 0;
		int anchorPrices[] = { 150, 200, 300, 400, 500, 600, 800 };
		for (int i=0; i < inventory.length; i++) {
			worth += inventory[i] * anchorPrices[i];
		}
		return worth;
	}

	public static int getSelectedDays() {
		return selectedDays;
	}

	public static void setSelectedDays(int selectedDays) {
		Ship.selectedDays = selectedDays;
	}

	public static double getSellingModifier() {
		return sellingModifier;
	}

	public static void setSellingModifier(double sellingModifier) {
		Ship.sellingModifier = sellingModifier;
	}
}
