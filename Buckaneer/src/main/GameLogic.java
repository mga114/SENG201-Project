package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameLogic extends JPanel implements MouseListener, MouseMotionListener{
	public State state;
	private int currIsland;
	private int targetIsland;
	private Toolkit t = Toolkit.getDefaultToolkit();
	private GameData data = new GameData();
	private Image currentDrawPath = data.getEmpty();
	private Image shipImage = t.getImage("images/player.png");
	private Ship ship = new Ship(170, 460);
	private boolean moved = false;
	private boolean paused = false;
	private boolean timerStart = false;
	
	
	
	public GameLogic() {
		state = State.MAP;
		addMouseListener(this);
		addMouseMotionListener(this);
		currIsland = 0;
		targetIsland = -1;
		//setFocusable(true);
	}
	
	public void paint(Graphics g) {
		switch(state) {
		case MAP:
			g.setColor(new Color(173, 216, 230));
			g.fillRect(0, 0, 1280, 720);
			Image background = t.getImage("images/background.png");
			g.drawImage(background, 0, 0, this);
			g.drawImage(currentDrawPath, 0, 0, this);
			g.drawImage(shipImage, ship.x, ship.y, this);
			g.dispose();
			break;
		}
	}
	@Override
	public void mouseClicked(MouseEvent m) {
		if(m.getButton() == MouseEvent.BUTTON3) {
			if (paused) {
				paused = false;
			}else {
				paused = true;
			}
		}else {
			System.out.println("x="+m.getX()+" y="+m.getY());
		}
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
		
		if (moved && !timerStart) {
			timerStart = true;
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				int[] pathDataX = data.getPathDataX01();
				int[] pathDataY = data.getPathDataY01();
				int i = 0;
			    @Override
			    public void run() {
			    	System.out.println(i);
			        if (!paused) {
			        	ship.x += pathDataX[i];
				        ship.y += pathDataY[i];
			        	i += 1;
			        }
			        if (i >= pathDataX.length) {
			        	timer.cancel();
			        	moved = false;
			        	timerStart = false;
						handleMouseMoveEmpty();
			        }
			        repaint();
			    }
	
			}, 10, 10);
		}
		repaint();
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
		if (data.getIslandButton0().pressed(mouseX, mouseY)) {
			mouseOverIsland0();
		}else if (data.getIslandButton1().pressed(mouseX, mouseY)) {
			mouseOverIsland1();
		}else if (data.getIslandButton2().pressed(mouseX, mouseY)) {
			mouseOverIsland2();
		}else {
			handleMouseMoveEmpty();
		}
		repaint();
	}
	
	public void handleMouseMoveEmpty() {
		if(!moved) {
			targetIsland = -1;
			currentDrawPath = data.getEmpty();
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
	
}
