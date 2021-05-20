package main;

import java.awt.event.KeyEvent;
import java.util.Random;


public class EventHandler {
	
	private Random random = new Random();
	private int numberPressed;
	
	
	public void generateRandomEvent(int chance) {
		int randEvent = random.nextInt(chance);
		if (randEvent == 1) {
			pirateEvent();
		}else if (randEvent == 2) {
			weatherEvent();
		}else if (randEvent == 3) {
			abandonedShipEvent();
		}
	}
	
	
	public void pirateEvent() {
		GameLogic.setPaused(true);
		GameLogic.setEventImage(GameData.getPirateEventMessage1());
		int randSuccess = random.nextInt(5);
		boolean keyValid = false;
		while(!keyValid) {
			keyValid = validKey(GameLogic.getCurrentChar());
		}
		//RANDOM CHANGE ME PLS DADDY
		if (random.nextInt(Ship.getFightingChance()) == 0) {
			GameLogic.setEventImage(GameData.getPirateEventMessage3());
		}else if (numberPressed == (randSuccess + 1)){
			GameLogic.setEventImage(GameData.getPirateEventMessage3());
		}else {
			if (Ship.inventoryWorth() >= 1000) {
				Ship.setInventory(new int[7]);
				GameLogic.setEventImage(GameData.getPirateEventMessage2());
			}else {
				GameLogic.setEventImage(GameData.getPirateEventMessage2());
				endEvent();
				GameLogic.state = State.GAMEOVER;
				GameLogic.setGameOverImage(GameData.getGameOverPirates());
			}
			
		}
		endEvent();
	}
	
	
	public void weatherEvent() {
		GameLogic.setPaused(true);
		GameLogic.setEventImage(GameData.getWeatherEventMessage());
		Ship.changeHull(-random.nextInt(30));
		endEvent();
	}
	
	public void abandonedShipEvent() {
		GameLogic.setPaused(true);
		GameLogic.setEventImage(GameData.getAbandonedShipMessage());
		Ship.changeMoney(random.nextInt(500));
		endEvent();
	}
	
	public void endEvent() {
		while(GameLogic.getCurrentChar() != KeyEvent.VK_SPACE) {
			System.out.println(GameLogic.getCurrentChar());
		}
		GameLogic.setCurrentChar(KeyEvent.CHAR_UNDEFINED);
		GameLogic.setEventImage(GameData.getEmpty());
		GameLogic.setPaused(false);
	}
	
	public boolean validKey(char cChar) {
		System.out.println("sticu");
		//System.out.println(cChar);
		if (cChar == KeyEvent.VK_1) {
			numberPressed = 1;
			return true;
		}else if (cChar == KeyEvent.VK_2) {
			numberPressed = 2;
			return true;
		}else if (cChar == KeyEvent.VK_3) {
			numberPressed = 3;
			return true;
		}else if (cChar == KeyEvent.VK_4) {
			numberPressed = 4;
			return true;
		}else if(cChar == KeyEvent.VK_5) {
			numberPressed = 5;
			return true;
		}else if (cChar == KeyEvent.VK_6) {
			numberPressed = 6;
			return true;
		}
		return false;
	}
}
