package main;

import java.util.ArrayList;

public class InventoryHandler {
	private static ArrayList<String[]> purchaseHistory = new ArrayList<String[]>();
	
	public static void initPurchaseHistory() {
		for (int i=0;i<7;i++) {
			purchaseHistory.add(new String[10]);
		}
	}
	
	public static void itemPurchased(int itemID, int islandNum, int price) {
		shuffle(itemID);
		String[] tempStrArray = purchaseHistory.get(itemID);
		tempStrArray[0] = "- " + Integer.toString(price) + "   " + Integer.toString(islandNum);
		purchaseHistory.set(itemID, tempStrArray);
	}
	
	public static void itemSold(int itemID, int islandNum, int price) {
		shuffle(itemID);
		String[] tempStrArray = purchaseHistory.get(itemID);
		tempStrArray[0] = "+ " + Integer.toString(price) + "   " + Integer.toString(islandNum);
		purchaseHistory.set(itemID, tempStrArray);
	}
	
	public static void shuffle(int itemID) {
		String[] tempStrArray = purchaseHistory.get(itemID);
		for (int i=tempStrArray.length-1; i >= 1; i--) {
			tempStrArray[i] = tempStrArray[i-1];
		}
		purchaseHistory.set(itemID, tempStrArray);
	}
	
	public static ArrayList<String[]> getPurchaseHistory() {
		return purchaseHistory;
	}
}
