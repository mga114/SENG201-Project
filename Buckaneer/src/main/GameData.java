package main;

import java.awt.Image;
import java.awt.Toolkit;

public class GameData {
	private static Toolkit t = Toolkit.getDefaultToolkit();
	private Image path01 = t.getImage("images/path1.png");
	private Image path12 = t.getImage("images/path2.png");
	private Image path02 = t.getImage("images/path3.png");
	private static Image empty = t.getImage("images/empty.png");
	private Button islandButton0 = new Button(10, 150, 370, 500);
	private Button islandButton1 = new Button(440, 570, 290, 370);
	private Button islandButton2 = new Button(330, 580, 500, 640);
	private static Image pirateEventMessage1 = t.getImage("images/piratemessage.jpg");
	private static Image pirateEventMessage2 = t.getImage("images/piratemessage2.jpg");
	private static Image pirateEventMessage3 = t.getImage("images/piratemessage3.jpg");
	private static Image weatherEventMessage = t.getImage("images/badweather.jpg");
	private static Image abandonedShipMessage = t.getImage("images/abandondedship.jpg");
	private static Image noMoneyMessage = t.getImage("images/nomoney.jpg");
	private static Image noSpaceMessage = t.getImage("images/bootytoobig.jpg");
	private static Image noItemsMessage = t.getImage("images/noitems.jpg");
	private static Image repairError = t.getImage("images/repairerror.jpg");
	private static Image shopButton = t.getImage("images/shopbutton.jpg");
	private static Image inventoryButton = t.getImage("images/inventory.jpg");
	private static Image priceInfoButton = t.getImage("images/priceinfobutton.jpg");
	private static Image gameOverMoney = t.getImage("images/gameovernomoney.png");
	private static Image gameOverPirates = t.getImage("images/gameoverpirates.png");
	private static int[][] sellingData;
	private static int[][] buyingData;
	
	
	public static void setPrices() {
		sellingData = Rand.generatePrices();
		buyingData = Rand.modifyPrices(sellingData, 0.3);
	}
	
	public static int getSellPrice(int island, int item) {
		return sellingData[island][item];
	}
	
	public static int getBuyPrice(int island, int item) {
		return buyingData[island][item];
	}
	
	
	public Image getPath01() {
		return path01;
	}
	
	public Image getPath12() {
		return path12;
	}
	
	public Image getPath02() {
		return path02;
	}
	
	public static Image getEmpty() {
		return empty;
	}
	
	public Button getIslandButton0() {
		return islandButton0;
	}
	
	public Button getIslandButton1() {
		return islandButton1;
	}
	
	public Button getIslandButton2() {
		return islandButton2;
	}

	public static Image getPirateEventMessage1() {
		return pirateEventMessage1;
	}

	public static Image getPirateEventMessage2() {
		return pirateEventMessage2;
	}

	public static Image getPirateEventMessage3() {
		return pirateEventMessage3;
	}

	public static Image getWeatherEventMessage() {
		return weatherEventMessage;
	}

	public static Image getAbandonedShipMessage() {
		return abandonedShipMessage;
	}


	public static Image getNoMoneyMessage() {
		return noMoneyMessage;
	}

	public static Image getNoSpaceMessage() {
		return noSpaceMessage;
	}

	public static Image getNoItemsMessage() {
		return noItemsMessage;
	}

	public static Image getRepairError() {
		return repairError;
	}

	public static Image getShopButton() {
		return shopButton;
	}

	public static Image getInventoryButton() {
		return inventoryButton;
	}

	public static Image getPriceInfoButton() {
		return priceInfoButton;
	}

	public static Image getGameOverMoney() {
		return gameOverMoney;
	}

	public static Image getGameOverPirates() {
		return gameOverPirates;
	}
}
