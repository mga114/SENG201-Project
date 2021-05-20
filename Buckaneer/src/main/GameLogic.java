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
	private Ship ship = new Ship(170, 460);
	private boolean moved = false;
	private static boolean paused = false;
	private boolean timerStart = false;
	private EventHandler eventHandler = new EventHandler();
	private int globalTime = 5000;
	private Image dayCounter1;
	private Image dayCounter2;
	private static char currentChar;
	private static Image eventImage = GameData.getEmpty();
	
	
	
	public GameLogic() {
		state = State.MAP;
		GameData.setPrices();
		addMouseListener(this);
		addMouseMotionListener(this);
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
		case MAP:
			g.setColor(new Color(173, 216, 230));
			g.fillRect(0, 0, 1280, 720);
			g.drawImage(background, 0, 0, this);
			g.drawImage(currentDrawPath, 0, 0, this);
			g.drawImage(shipImage, ship.x, ship.y, this);
			Image n = t.getImage("images/daysremaining.png");
			g.drawImage(n, 900, 20, this);
			g.drawImage(dayCounter1, 1190, 30, this);
			g.drawImage(dayCounter2, 1220, 30, this);
			g.drawImage(eventImage, 490, 260, this);
			g.drawImage(GameData.getInventoryButton(), 1155, 600, this);
			g.drawImage(GameData.getShopButton(), 1155, 650, this);
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
		//System.out.println(mouseX + " " + mouseY);
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
		}
	}
	
	public void mouseClickShopState(int mouseX, int mouseY) {
		System.out.println(mouseX + " " + mouseY); 
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
		if (data.getIslandButton0().pressed(mouseX, mouseY)) {
			if (targetIsland != currIsland) {
				moved = true;
				currIsland = 0;
			}
		}else if (data.getIslandButton1().pressed(mouseX, mouseY)) {
			if (targetIsland != currIsland) {
				moved = true;
				currIsland = 1;
			}
		}else if (data.getIslandButton2().pressed(mouseX, mouseY)) {
			if (targetIsland != currIsland) {
				moved = true;
				currIsland = 2;
			}
		}
		if (mouseX > 1155) {
			if (mouseY > 600 && mouseY < 650) {
				state = State.INVENTORY;
			}else if (mouseY > 650) {
				state = State.SHOP;
			}
		}
		System.out.println(mouseX + " " + mouseY);
		if(moved) {
		moveShip(data.getPathDataX01(), data.getPathDataY01());
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
			        	eventHandler.generateRandomEvent(1000);
			        	ship.x += pathDataX[i];
				        ship.y += pathDataY[i];
			        	i += 1;
			        	if (i % 5 == 0) {
			        		globalTime -= 1;
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
		setCurrentChar(e.getKeyChar());
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
}
