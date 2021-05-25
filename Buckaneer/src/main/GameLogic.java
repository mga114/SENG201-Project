package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameLogic extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	public static State state;
	private static int currIsland;
	private int targetIsland;
	private Toolkit t = Toolkit.getDefaultToolkit();
	private static GameData data = new GameData();
	private Image currentDrawPath = GameData.getEmpty();
	private Image shipImage = t.getImage("images/player.png");
	private static Image gameOverImage = GameData.getEmpty();
	private Ship ship = new Ship(170, 460);
	private boolean moved = false;
	private static boolean paused = false;
	private boolean timerStart = false;
	private EventHandler eventHandler = new EventHandler();
	private static int globalTime = 2000;
	private Image dayCounter1;
	private Image dayCounter2;
	private static char currentChar;
	private static Image eventImage = GameData.getEmpty();
	
	
	
	public GameLogic() {
		state = State.MENU;
		addMouseListener(this);
		addMouseMotionListener(this);
		Path.constructPathMatrix();
		InventoryHandler.initPurchaseHistory();
		addKeyListener(this);
		setFocusable(true);
		timeChanged();
		currIsland = 0;
		targetIsland = -1;
		//setFocusable(true);
	}
	
	public void paint(Graphics g) {
		Image background = t.getImage("images/background.png");
		switch(state) {
		case MENU:
			g.drawImage(t.getImage("images/mainmenu.jpg"), 0, 0, this);
			ArrayList<PriceSprite> mSprites = PriceSprite.drawShipName();
			for (PriceSprite sprite : mSprites) {
				g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
			}
			g.dispose();
			break;
		case MAP:
			g.setColor(new Color(173, 216, 230));
			g.fillRect(0, 0, 1280, 720);
			g.drawImage(background, 0, 0, this);
			g.drawImage(currentDrawPath, 0, 0, this);
			//g.drawImage(t.getImage("images/path3.png"), 0, 0, this);
			g.drawImage(shipImage, ship.x, ship.y, this);
			Image n = t.getImage("images/daysremaining.png");
			//g.drawImage(data.getPath34(), 0, 0, this);
			g.drawImage(n, 900, 20, this);
			g.drawImage(dayCounter1, 1190, 30, this);
			g.drawImage(dayCounter2, 1220, 30, this);
			g.drawImage(eventImage, 490, 260, this);
			g.drawImage(GameData.getInventoryButton(), 1155, 600, this);
			g.drawImage(GameData.getShopButton(), 1155, 650, this);
			g.drawImage(GameData.getPriceInfoButton(), 1155, 550, this);
			g.dispose();
			break;
		case SHOP:
			Ship.setRepairPrice();
			g.setColor(new Color(173, 216, 230));
			g.fillRect(0, 0, 1280, 720);
			g.drawImage(background, 0, 0, this);
			g.drawImage(t.getImage("images/shop (2).jpg"), 240, 60, this);
			ArrayList<PriceSprite> priceSprites = PriceSprite.constructShopText();
			for (PriceSprite sprite : priceSprites) {
				g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
			}
			g.drawImage(eventImage, 490, 260, this);
			g.dispose();
			break;
		case INVENTORY:
			g.setColor(new Color(173, 216, 230));
			g.fillRect(0, 0, 1280, 720);
			g.drawImage(background, 0, 0, this);
			g.drawImage(t.getImage("images/inventoryMenu.jpg"), 240, 60, this);
			ArrayList<PriceSprite> invSprites = PriceSprite.constructInventoryText();
			for (PriceSprite sprite : invSprites) {
				g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
			}
			g.dispose();
			break;
		case PRICEINFO:
			g.setColor(new Color(173, 216, 230));
			g.fillRect(0, 0, 1280, 720);
			g.drawImage(background, 0, 0, this);
			g.drawImage(t.getImage("images/priceinfo.jpg"), 240, 60, this);
			ArrayList<PriceSprite> pISprites = PriceSprite.constructPriceInfoText();
			for (PriceSprite sprite : pISprites) {
				g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
			}
			g.dispose();
			break;
		case GAMEOVER:
			g.drawImage(t.getImage("images/gameover.jpg"), 0, -20, this);
			g.drawImage(gameOverImage, 0, 0, this);
			ArrayList<PriceSprite> gOSprites = PriceSprite.getGameOverText();
			for(PriceSprite sprite : gOSprites) {
				g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
			}
			g.dispose();
			break;
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent m) {
	}

	@Override
	public void mouseEntered(MouseEvent m) {}

	@Override
	public void mouseExited(MouseEvent m) {}

	@Override
	public void mousePressed(MouseEvent m) {
		int mouseX = m.getX();
		int mouseY = m.getY();
		System.out.println("x="+mouseX + " y=" + mouseY);
		switch (state) {
		case MAP:
			mouseClickMapState(mouseX, mouseY);
			break;
		case SHOP:
			if (eventImage == GameData.getEmpty()) {
			mouseClickShopState(mouseX, mouseY);
			}else {
				eventImage = GameData.getEmpty();
			}
			repaint();
			break;
		case INVENTORY:
			if (mouseX > 754 && mouseX < 1000 && mouseY > 539 && mouseY < 618) {
				state = State.MAP;
				repaint();
			}
			break;
		case PRICEINFO:
			if (mouseX > 754 && mouseX < 1000 && mouseY > 539 && mouseY < 618) {
				state = State.MAP;
				repaint();
			}
			break;
		case MENU:
			handleClickMenuState(mouseX, mouseY);
			repaint();
			break;
		}
	}
	
	public void handleClickMenuState(int mouseX, int mouseY) {
		if(mouseX > 500 && mouseX < 780 && mouseY > 535 && mouseY < 660) {
			state = State.MAP;
			GameData.setPrices();
		}
		if (mouseY > 235 && mouseY < 385) {
			if(mouseX > 93 && mouseX < 340) {
				Ship.setRepairModifier(0);
				Ship.setEventChance(2000);
				Ship.setFightingChance(3);
				Ship.setSellingModifier(0);
				GameData.setPrices();
			}else if (mouseX > 373 && mouseX < 600) {
				Ship.setRepairModifier(15);
				Ship.setEventChance(1250);
				Ship.setFightingChance(3);
				Ship.setSellingModifier(0);
				GameData.setPrices();
			}else if (mouseX > 633 && mouseX < 848) {
				Ship.setRepairModifier(-10);
				Ship.setEventChance(2000);
				Ship.setFightingChance(2);
				Ship.setSellingModifier(0);
				GameData.setPrices();
			}else if (mouseX > 873 && mouseX < 1111) {
				Ship.setRepairModifier(0);
				Ship.setEventChance(2000);
				Ship.setFightingChance(5);
				Ship.setSellingModifier(0.3);
				GameData.setPrices();
			}
		}
		if(mouseY > 430 && mouseY < 520) {
			if(mouseX > 400 && mouseX < 490) {
				globalTime = 2000;
				Ship.setSelectedDays(2000);
			}else if (mouseX > 532 && mouseX < 630) {
				globalTime = 3000;
				Ship.setSelectedDays(3000);
			}else if (mouseX > 660 && mouseX < 750) {
				globalTime = 4000;
				Ship.setSelectedDays(4000);
			}else if (mouseX > 790 && mouseX < 880) {
				globalTime = 5000;
				Ship.setSelectedDays(5000);
			}
			timeChanged();
		}
	}
	
	public void mouseClickShopState(int mouseX, int mouseY) {
		//System.out.println(mouseX + " " + mouseY); 
		//35 between top and bottom, 20 between buttons
		int buttonNumClicked = -1;
		if (mouseY > 253 && mouseY < 609) {
			if (mouseX > 304 && mouseX < 373) {
				for (int i=0; i < 7; i++) {
					if(mouseY > (253 + i * 53) && mouseY < (288 + i * 53)) {
						buttonNumClicked = i;
					}
				}
				if (buttonNumClicked != -1) {
					Ship.handleSelling(buttonNumClicked);
				}
			}else if (mouseX > 564 && mouseX < 633) {
				for (int i=0; i < 7; i++) {
					if(mouseY > (253 + i * 53) && mouseY < (288 + i * 53)) {
						buttonNumClicked = i;
					}
				}
				if (buttonNumClicked != -1) {
					Ship.handlePurchase(buttonNumClicked);
				}
			}
		}
		if (mouseX > 754 && mouseX < 1000 && mouseY > 539 && mouseY < 618) {
			Ship.handleRepair();
		}
	}
	
	public void mouseClickMapState(int mouseX, int mouseY) {
		int[] xPath = new int[0];
		int[] yPath = new int[0];
		if (data.getIslandButton0().pressed(mouseX, mouseY)) {
			if (targetIsland != currIsland) {
				moved = true;
				Ship.setEventModifier(mouseY);
				xPath = Path.getXPath(currIsland, targetIsland);
				yPath = Path.getYPath(currIsland, targetIsland);
				currIsland = 0;
			}
		}else if (data.getIslandButton1().pressed(mouseX, mouseY)) {
			if (targetIsland != currIsland) {
				moved = true;
				xPath = Path.getXPath(currIsland, targetIsland);
				yPath = Path.getYPath(currIsland, targetIsland);
				currIsland = 1;
			}
		}else if (data.getIslandButton2().pressed(mouseX, mouseY)) {
			if (targetIsland != currIsland) {
				moved = true;
				xPath = Path.getXPath(currIsland, targetIsland);
				yPath = Path.getYPath(currIsland, targetIsland);
				currIsland = 2;
			}
		}else if (data.getIslandButton3().pressed(mouseX, mouseY)) {
			if (targetIsland != currIsland) {
				moved = true;
				xPath = Path.getXPath(currIsland, targetIsland);
				yPath = Path.getYPath(currIsland, targetIsland);
				currIsland = 3;
			}
		}else if (data.getIslandButton4().pressed(mouseX, mouseY)) {
			if (targetIsland != currIsland) {
				moved = true;
				xPath = Path.getXPath(currIsland, targetIsland);
				yPath = Path.getYPath(currIsland, targetIsland);
				currIsland = 4;
			}
		}
		if (mouseX > 1155) {
			if(mouseY > 550 && mouseY<600) {
				state = State.PRICEINFO;
			}else if (mouseY > 600 && mouseY < 650) {
				state = State.INVENTORY;
			}else if (mouseY > 650) {
				state = State.SHOP;
			}
		}
		//System.out.println(mouseX + " " + mouseY);
		if(moved) {
			moveShip(xPath, yPath);
		}
		repaint();
	}
	
	public void moveShip(int[] pathDataX, int[] pathDataY) {
		if (moved && !timerStart) {
			timerStart = true;
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				int i = 0;
			    @Override
			    public void run() {
			    	repaint();
			        if (!paused) {
			        	eventHandler.generateRandomEvent(Ship.getEventChance());
			        	ship.x += pathDataX[i];
				        ship.y += pathDataY[i];
			        	i += 1;
			        	if (i % 5 == 0) {
			        		globalTime = Math.max(0, globalTime-1);
			        		timeChanged();
			        	}
			        }
			        if (i >= pathDataX.length) {
			        	timer.cancel();
			        	moved = false;
			        	timerStart = false;
						handleMouseMoveEmpty();
						Ship.setRepairPrice();
						state = State.SHOP;
			        }
			        repaint();
			    }
	
			}, 10, 10);
		}
	}

	@Override
	public void mouseReleased(MouseEvent m) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent m) {
		int mouseX = m.getX();
		int mouseY = m.getY();
		//System.out.println(mouseX + " " + mouseY);
		switch (state) {
		case MAP:
			if (data.getIslandButton0().pressed(mouseX, mouseY)) {
				mouseOverIsland0();
			}else if (data.getIslandButton1().pressed(mouseX, mouseY)) {
				mouseOverIsland1();
			}else if (data.getIslandButton2().pressed(mouseX, mouseY)) {
				mouseOverIsland2();
			}else if(data.getIslandButton3().pressed(mouseX, mouseY)) {
				mouseOverIsland3();
			}else if(data.getIslandButton4().pressed(mouseX, mouseY)) {
				mouseOverIsland4();
			}else {
				handleMouseMoveEmpty();
			}
			break;
		}
		repaint();
	}
	
	public void handleMouseMoveEmpty() {
		if(!moved) {
			targetIsland = -1;
			currentDrawPath = GameData.getEmpty();
		}
	}
	
	public void mouseOverIsland0() {
		if (!moved) {
			targetIsland = 0;
			if (currIsland == 1) {
				currentDrawPath = data.getPath01();
			} else if (currIsland == 2) {
				currentDrawPath = data.getPath02();
			} else if (currIsland == 3) {
				currentDrawPath = data.getPath03();
			} else if (currIsland == 4) {
				currentDrawPath = data.getPath04();
			}
		}
	}
	
	public void mouseOverIsland1() {
		if (!moved) {
			targetIsland = 1;
			if (currIsland == 0) {
				currentDrawPath = data.getPath01();
			} else if (currIsland == 2) {
				currentDrawPath = data.getPath12();
			} else if(currIsland == 3) {
				currentDrawPath = data.getPath13();
			} else if (currIsland == 4) {
				currentDrawPath = data.getPath14();
			}
		}
	}
	
	public void mouseOverIsland2() {
		if(!moved) {
			targetIsland = 2;
			if (currIsland == 0) {
				currentDrawPath = data.getPath02();
			} else if (currIsland == 1) {
				currentDrawPath = data.getPath12();
			} else if (currIsland == 3) {
				currentDrawPath = data.getPath23();
			} else if (currIsland == 4) {
				currentDrawPath = data.getPath24();
			}
		}
	}
	
	public void mouseOverIsland3() {
		if(!moved) {
			targetIsland = 3;
			if(currIsland == 0) {
				currentDrawPath = data.getPath03();
			}else if (currIsland == 1) {
				currentDrawPath = data.getPath13();
			}else if (currIsland == 2) {
				currentDrawPath = data.getPath23();
			}else if (currIsland == 4) {
				currentDrawPath = data.getPath34();
			}
		}
	}
	
	public void mouseOverIsland4() {
		if(!moved) {
			targetIsland = 4;
			if(currIsland == 0) {
				currentDrawPath = data.getPath04();
			}else if (currIsland == 1) {
				currentDrawPath = data.getPath14();
			}else if (currIsland == 2) {
				currentDrawPath = data.getPath24();
			}else if (currIsland == 3) {
				currentDrawPath = data.getPath34();
			}
		}
	}
	
	public void timeChanged() {
		int daysLeft = (int) Math.ceil(globalTime / 100);
		int day1 = daysLeft % 10;
		int day2 = (int) Math.floor(daysLeft / 10);
		dayCounter1 = t.getImage("images/text/" + Integer.toString(day2) + ".png");
		dayCounter2 = t.getImage("images/text/" + Integer.toString(day1) + ".png");
	}
	
	public static void setPaused(boolean pausedVal) {
		paused = pausedVal;
	}
	
	public static boolean getPaused() {
		return paused;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(state) {
		case MAP:
			setCurrentChar(e.getKeyChar());
			break;
		case MENU:
			KeyboardInterface.handleInput(e.getKeyChar());
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		repaint();
	}
	

	public static char getCurrentChar() {
		return currentChar;
	}

	public static void setCurrentChar(char currentChar) {
		GameLogic.currentChar = currentChar;
	}

	public static void setEventImage(Image eventImage) {
		GameLogic.eventImage = eventImage;
	}
	
	public static int getCurrentIsland() {
		return currIsland;
	}

	public static void setGameOverImage(Image ngameOverImage) {
		gameOverImage = ngameOverImage;
	}
	
	public static int getGlobalTime() {
		return globalTime;
	}
}
