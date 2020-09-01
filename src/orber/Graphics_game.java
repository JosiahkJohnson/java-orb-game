package orber;
//0.0.1

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Graphics_game 
{
	public static void main(String[] args)
	{
		Game f = new Game();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//makes the game fullscreen
		//Chan
		//http://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		f.setUndecorated(true);
		//Dennis
		//http://stackoverflow.com/questions/1984071/how-to-hide-cursor-in-a-swing-application
		f.setCursor(f.getToolkit().createCustomCursor(
	            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
	            "null"));
		//game starts
		f.setVisible(true);	
		f.setup();
		f.instructions();
		f.play();
		f.gameOver();
		//game ends
		//camickr wvdz
		//http://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
		f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
	}
}

class Game extends JFrame implements KeyListener
{
	private Image raster;
	private Graphics rasterGraphics;
	private boolean up, down, left, right, loop = true, enter = true;
	private Player player1 = new Player();
	//http://www.javaworld.com/article/2076869/learn-java/drawing-text-is-easy-with-three-java-classes.html
	private Font scoreFont = new Font("Dialog", Font.PLAIN, 24);
	private Font gameOverFont = new Font("Dialog", Font.PLAIN, 72);
	//the player's score
	private int score = 0;
	
	//also helps with fullscreen
	//Todd Sundsted
	//http://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
	static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int)dim.getWidth(), HEIGHT = (int)dim.getHeight();
	
	public void setup()
	{
		raster = this.createImage(WIDTH, HEIGHT);
		rasterGraphics = raster.getGraphics();
	}
	
	//plays a simple animation describing how to play the game.
	public void instructions()
	{
		this.addKeyListener(this);
		resetPlayer();
		orbInstructions();
		resetPlayer();
		enter = true;
		try{Thread.sleep(1000);}catch(Exception e){}
		upgradeOrbInstructions();
		resetPlayer();
		enter = true;
		try{Thread.sleep(1000);}catch(Exception e){}
		deleteOrbInstructions();
		resetPlayer();
		enter = true;
		try{Thread.sleep(1000);}catch(Exception e){}
	}
	//The main enemy of the game, the player is instructed here to avoid them
	public void orbInstructions()
	{
		rasterGraphics.setColor(Color.cyan);
		rasterGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		ArrayList <Orb> orbsStep1 = new ArrayList<Orb>();
			orbsStep1.add (new Orb(WIDTH/2, HEIGHT/2, 0, 0));
			orbsStep1.add (new Orb(WIDTH/3, HEIGHT/2, 0, 0));
		
		String instructions = "Avoid blue orbs; they reduce your size and multiplier.";
		String escape = "Hit enter to skip instruction screens.";
		while (enter && player1.getLocationX() < WIDTH*.67f)
		{
			drawBackground();
			drawBoarder();
			rasterGraphics.setFont(scoreFont);
			rasterGraphics.drawString(instructions, (WIDTH/2)-(instructions.length()*5), (HEIGHT/2)-100);
			rasterGraphics.drawString(escape, (WIDTH/2)-instructions.length()*3, (HEIGHT/2)+100);
			long time = System.currentTimeMillis();
			player1.draw(rasterGraphics, orbsStep1);
			for (Orb j : orbsStep1)
			{
				j.draw(rasterGraphics, orbsStep1);
			}
			long deltaTime = System.currentTimeMillis() - time;
			try{Thread.sleep(20-deltaTime);}catch(Exception e){}
		}
	}
	//These are represented as green orbs in game. Player is encouraged here to collect them
	public void upgradeOrbInstructions()
	{
		rasterGraphics.setColor(Color.cyan);
		rasterGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		ArrayList <Orb> orbsStep2 = new ArrayList<Orb>();
			orbsStep2.add (new Upgrade(WIDTH/2, HEIGHT/2, 0, 0));
			orbsStep2.add (new Upgrade(WIDTH/3, HEIGHT/2, 0, 0));
		
		String instructions = "By contrast, green orbs will increase your size and multiplier.";
		while (enter && player1.getLocationX() < WIDTH*.67f)
		{
			drawBackground();
			drawBoarder();
			rasterGraphics.setFont(scoreFont);
			rasterGraphics.drawString(instructions, (WIDTH/2)-(instructions.length()*5), (HEIGHT/2)-100);
			long time = System.currentTimeMillis();
			player1.draw(rasterGraphics, orbsStep2);
			for (Orb j : orbsStep2)
			{
				j.draw(rasterGraphics, orbsStep2);
			}
			long deltaTime = System.currentTimeMillis() - time;
			try{Thread.sleep(20-deltaTime);}catch(Exception e){}
		}
	}
	//represented as white orbs in game, here the player is informed on their function.
	public void deleteOrbInstructions()
	{
		rasterGraphics.setColor(Color.cyan);
		rasterGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		ArrayList <Orb> orbsStep3 = new ArrayList<Orb>();
			orbsStep3.add (new Delete(WIDTH/3, HEIGHT/2, 0, 0));
			orbsStep3.add (new Orb (WIDTH/2, HEIGHT/2, 0, 0));
			
		String instructions = "White orbs allow you to remove blue orbs from the screen.";
		String escape = "Hit esc to exit game early.";
		while (enter && player1.getLocationX() < WIDTH*.67f)
		{
			drawBackground();
			drawBoarder();
			rasterGraphics.setFont(scoreFont);
			rasterGraphics.drawString(instructions, (WIDTH/2)-(instructions.length()*5), (HEIGHT/2)-100);
			rasterGraphics.drawString(escape, (WIDTH/2)-instructions.length()*3, (HEIGHT/2)+100);
			long time = System.currentTimeMillis();
			player1.draw(rasterGraphics, orbsStep3);
			for (Orb j : orbsStep3)
			{
				j.draw(rasterGraphics, orbsStep3);
			}
			long deltaTime = System.currentTimeMillis() - time;
			try{Thread.sleep(20-deltaTime);}catch(Exception e){}
		}
	}
	//resets the player back to base state, size, and location.
	public void resetPlayer()
	{
		player1.setLocationX((int)(WIDTH*.25f));
		player1.setLocationY(HEIGHT/2);
		player1.setSize(40);
		player1.setImmune(false);
		player1.setImmuneTimer(0);
		player1.setMultiplier(3);
		player1.setSpeed(10);
	}
	//The main game in the program
	public void play()
	{
		this.addKeyListener(this);
		rasterGraphics.setColor(Color.cyan);
		rasterGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Array that is used to store all the orbs flying around the screen, initially starts out as two
		ArrayList <Orb> orbs = new ArrayList<Orb>();
			for (int i = 0; i < 2; i++)
				orbs.add(new Orb((int)Math.ceil(Math.random()*(WIDTH-51)), (int)Math.ceil(Math.random()*(HEIGHT-51)), randomSpeed(), randomSpeed()));
		//total elapsed game time in miliseconds
		long playTime = 0;
		//helps make the game harder as it goes on by adding more time between the delete orbs appearing
		int progression = 15000;
		//time until the next upgrade orb
		long nextUpgrade = (long)Math.ceil(Math.random()*10000);
		//time until the next delete orb
		long nextDelete = (long)Math.ceil(Math.random()*progression);
		//puts the next blue orb on the screen, every one second of play. Also handles increasing the player's score every second they survive.
		int eventTimer = 0;
		//main loop
		while(loop)
		{
			long time = System.currentTimeMillis();
			//handles player movement and interaction with the other objects
			player1.momentumChange(up, down, left, right);
			player1.draw(rasterGraphics, orbs);
			for (Orb i : orbs)
			{
				i.draw(rasterGraphics, orbs);
			}
			drawBoarder();
			try{Thread.sleep(10);}catch(Exception e){}
			drawBackground();
			//prints the player's score, multiplier, and if applicable, the player's immunity timer.
			printText(player1);
			//ends the game if the player's size gets too small from taking hits.
			if (player1.getSize() < 20)
				break;
			//that event timer variable described earlier going into use every 1000 miliseconds.
			if(eventTimer > 1000)
			{
				orbs.add(new Orb((int)Math.ceil(Math.random()*(WIDTH-51)), (int)Math.ceil(Math.random()*(HEIGHT-51)), randomSpeed(), randomSpeed()));
				//resets the event timer
				eventTimer = 0;
				//adds player score
				score += player1.getMultiplier();
			}
			//puts the next green orb on the screen. Randomly between 0-10 seconds.
			if(playTime > nextUpgrade)
			{
				orbs.add(new Upgrade((int)Math.ceil(Math.random()*(WIDTH-51)), (int)Math.ceil(Math.random()*(HEIGHT-51)), randomSpeed(), randomSpeed()));
				nextUpgrade += (long)Math.ceil(Math.random()*10000);
			}
			//Puts the next white orb on the screen and causes a chance of the next one taking longer to appear.
			if(playTime > nextDelete)
			{
				orbs.add(new Delete((int)Math.ceil(Math.random()*(WIDTH-51)), (int)Math.ceil(Math.random()*(HEIGHT-51)), randomSpeed(), randomSpeed()));
				nextDelete += (long)Math.ceil(Math.random()*progression);
				progression += (int)Math.ceil(Math.random()*5500);
			}
			long deltaTime = System.currentTimeMillis() - time;
			try{Thread.sleep(10-deltaTime);}catch(Exception e){}
			eventTimer += deltaTime;
			playTime += deltaTime;
			//makes ajustments to the player's immune timer and overall immunity if needed.
			if(player1.isImmune() == true)
			{
				//if time is up, it removes it
				if(player1.getImmuneTimer() < 0)
				{
					player1.setImmune(false);
					player1.setSpeed(10);
				}
				//lowers the immunity timer
				player1.setImmuneTimer(player1.getImmuneTimer()- (int)deltaTime);
			}	
		}
	}
	//used once the player loses
	public void gameOver()
	{
		String gameOver = "Final score: " + score;
		this.addKeyListener(this);
		rasterGraphics.setColor(Color.cyan);
		rasterGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		//loops untill the player hits the esc or enter keys.
		while(loop && enter)
		{
			drawBackground();
			drawBoarder();
			rasterGraphics.setColor(Color.black);
			rasterGraphics.setFont(gameOverFont);
			rasterGraphics.drawString(gameOver, WIDTH/2-(gameOver.length()*18), HEIGHT/2);
			//this is to keep the computer from thinking too hard during this stage as not much is going on
			try{Thread.sleep(500);}catch(Exception e){}
		}
	}

	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_UP)
    	{
			up = true;
     	}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			down = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			left = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			right = true;
		}
	}
	public void keyReleased(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			up = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			right = false;
		}
		if (e.getKeyCode() == 27)
		{
			loop = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			enter = false;
		}
	}
	
	//not used
	public void keyTyped(KeyEvent e) {}
	//used to make the newly created orbs have a random speed and direction.
	public int randomSpeed()
	{
		return (int)((Math.round(Math.random()) * 2 - 1) * Math.ceil(Math.random()*3));
	}
	
	public void drawBackground()
	{
		getGraphics().drawImage(raster, 0, 0, WIDTH, HEIGHT, null);
	}
	//draws a small gray boarder around the edges of the screen.
	public void drawBoarder()
	{
		rasterGraphics.setColor(Color.darkGray);
		rasterGraphics.fillRect(0, 0, 5, HEIGHT);
		rasterGraphics.fillRect(0, (HEIGHT-5), WIDTH, 5);
		rasterGraphics.fillRect((WIDTH - 5), 0, 5, HEIGHT);
		rasterGraphics.fillRect(0, 0, WIDTH, 5);
	}
	//score/info board
	public void printText(Player player1)
	{
		//erase
		rasterGraphics.setColor(Color.cyan);
		rasterGraphics.fillRect(25, 20, 2000, 50);
		//print score and multiplier to screen
		rasterGraphics.setColor(Color.black);
		rasterGraphics.setFont(scoreFont);
		rasterGraphics.drawString("Multiplier: X" + player1.getMultiplier(), 30, 40);
		rasterGraphics.drawString("Score: " + score, 500, 40);
		//if the player is immune from hitting a delete orb, this will appear.
		if(player1.isImmune())
			rasterGraphics.drawString("Immunity: " + (player1.getImmuneTimer() + 1) / 1000 + " seconds left", 970, 40);
	}
}
//the protagonist
class Player
{
	private int locationX = 500, locationY = 500;
	private int size = 40;
	private int multiplier = 3;
	private boolean immune = false;
	private int speed = 10;
	private int immuneTimer = 0;
	private int playerVup = 0;
	private int playerVdown = 0;
	private int playerVleft = 0;
	private int playerVright = 0;
	
	//used again because I found it to be very useful for screen size calculations
	static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int WIDTH = (int)dim.getWidth(), HEIGHT = (int)dim.getHeight();
	
	//adjusts momentum based on the player's key press and max speed, lowers it if key isn't pressed.
	
	public void momentumChange(boolean up, boolean down, boolean left, boolean right)
	{
		if (up == true) // && locationY > 5)
		{	if (playerVup < speed)
				playerVup++;
		}
		else
			if (playerVup > 0)
				playerVup--;
		if (down == true) // && locationY > HEIGHT - 5 - getSize())
		{	if (playerVdown < speed)
				playerVdown++;
		}
		else
			if (playerVdown > 0)
				playerVdown--;
		if (left == true) // && locationX > 5)
		{	if (playerVleft < speed)
				playerVleft++;
		}
		else
			if (playerVleft > 0)
				playerVleft--;
		if (right == true) // && locationX > WIDTH - 5 - getSize())
		{	if (playerVright < speed)
				playerVright++;
		}
		else
			if (playerVright > 0)
				playerVright--;
	}
	
	//draws the player and handles collision with the edges and orbs
	
	public void draw(Graphics g, ArrayList<Orb> orbs)
	{
		g.setColor(Color.cyan);
		g.fillRect(locationX, locationY, getSize(), getSize());
		//handles player collision with orbs
		checkCollision(orbs, g);
		if(locationY > 5)
			locationY -= playerVup;
		if(locationY < HEIGHT - 5 - getSize())
			locationY += playerVdown;
		if(locationX > 0)
			locationX -= playerVleft;
		if(locationX < WIDTH - 5 - getSize())
			locationX += playerVright;
		//player is green when he is immune to the blue orbs.
		if(immuneTimer > 2000)
			g.setColor(Color.green);
		//player is orange if the immunity is about to run out, ~2 seconds warning
		else if(immuneTimer > 0)
			g.setColor(Color.orange);
		//If the player is yellow, the next hit he take that reduces his size will end the game.
		else if(getSize() <= 20)
			g.setColor(Color.yellow);
		//His normal color is red
		else
			g.setColor(Color.red);
		g.fillRect(locationX, locationY, getSize(), getSize());
	}
	
	//I added a bit of a buffer here because playtesting found it to be quite hard to avoid faster moving orbs that move several pixals a tick
	public void checkCollision(ArrayList<Orb> orbs, Graphics g)
	{
		int i = 0;
		while (i < orbs.size())
		{
			//get the distance x and y between this object and the other object
			float xDistance	= (locationX+size/2) - (orbs.get(i).getPosition().getX()+orbs.get(i).getRADIUS());
			float yDistance = (locationY+size/2) - (orbs.get(i).getPosition().getY()+orbs.get(i).getRADIUS());
			
			float distanceSquared = (xDistance * xDistance) + (yDistance * yDistance);
			
			float sumSize = getSize()/2 + orbs.get(i).getRADIUS();
			float sumSizeSquared = sumSize * sumSize;
			
			//checking collision
			if(distanceSquared + 100 < sumSizeSquared)
			{
				g.setColor(Color.cyan);
				g.fillOval((int)orbs.get(i).getPosition().getX()-2, (int)orbs.get(i).getPosition().getY()-2, (orbs.get(i).getRADIUS()*2)+5, (orbs.get(i).getRADIUS()*2)+5);
				//if it hits a normal orb
				if (orbs.get(i).getType() == 0 && isImmune() == false)
				{
					setSize(getSize()-10);
					setMultiplier(getMultiplier() - 1);
				}
				//if it hits an upgrade orb
				else if (orbs.get(i).getType() == 1)
				{
					setSize(getSize()+10);
					setMultiplier(getMultiplier() + 1);
				}
				//if it hits a delete orb
				else if (orbs.get(i).getType() == 2)
				{
					//while hitting the delete orb, if the player size is too small
					if (getSize() < 50)
						setSize(50);
					setImmune(true);
					//giving the player a boost to speed
					setSpeed(15);
					//setting the timer, or increasing it if the player is already under the effect as to not waste a possably rare orb
					immuneTimer += 5000;
				}
				//always removes the orb the player comes in contact with
				orbs.remove(i);
			}
			i++;
		}
	}
	
	//fun with private variables
	public int getLocationY() 
	{
		return locationY;
	}
	public void setLocationY(int locationY) 
	{
		this.locationY = locationY;
	}
	public int getLocationX() 
	{
		return locationX;
	}
	public void setLocationX(int locationX) 
	{
		this.locationX = locationX;
	}
	public int getSize() 
	{
		return size;
	}
	public void setSize(int size) 
	{
		this.size = size;
	}
	public int getMultiplier() 
	{
		return multiplier;
	}
	public void setMultiplier(int multiplier)
	{
		this.multiplier = multiplier;
	}
	public boolean isImmune() 
	{
		return immune;
	}
	public void setImmune(boolean immune) 
	{
		this.immune = immune;
	}
	public int getSpeed()
	{
		return speed;
	}
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	public int getImmuneTimer() 
	{
		return immuneTimer;
	}
	public void setImmuneTimer(int immuneTimer) 
	{
		this.immuneTimer = immuneTimer;
	}
}
//antagonist
class Orb
{
	private final int RADIUS = 25;
	private Vector2D position, velocity;
	private short type = 0;
	
	//again, very usefull for screen size calculations
	static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	Orb(int initialLocationX, int initialLocationY, int speedX, int speedY)
	{
		setPosition(new Vector2D());
		velocity = new Vector2D();
		getPosition().set(initialLocationX, initialLocationY);
		velocity.set(speedX, speedY);
	}
	//handles drawing the orbs to the screen as well as collision with other orbs
	public void draw(Graphics g, ArrayList<Orb> orbs)
	{
		//erase then check boarders
		g.setColor(Color.cyan);
		g.fillOval((int)getPosition().getX()-2, (int)getPosition().getY()-2, (getRADIUS()*2)+5, (getRADIUS()*2)+5);
		//checking for collision with other orbs
		for(Orb b : orbs)
		{
			if (b != this && this.checkCollision(b))
				this.bounce(b);
		}
		if (getPosition().getX()+(RADIUS*2) > (int)dim.getWidth()-5)
			velocity.setX(velocity.getX() * -1);
		else if (getPosition().getX() < 5)
			velocity.setX(velocity.getX() * -1);
		if (getPosition().getY()+(RADIUS*2) > (int)dim.getHeight()-5)
			velocity.setY(velocity.getY() * -1);
		else if (getPosition().getY() < 5)
			velocity.setY(velocity.getY() * -1);
		//move
		setPosition(getPosition().add(velocity));
		//draw
		setColor(g);
		g.fillOval((int)getPosition().getX(), (int)getPosition().getY(), getRADIUS()*2, getRADIUS()*2);
	}
	//check collision
	public boolean checkCollision(Orb other)
	{
		//get the distance x and y between this object and the other object
		float xDistance	= getPosition().getX() - other.getPosition().getX();
		float yDistance = getPosition().getY() - other.getPosition().getY();
		
		float distanceSquared = (xDistance * xDistance) + (yDistance * yDistance);
		
		float sumRadius = RADIUS + other.RADIUS;
		float sumRadiusSquared = sumRadius * sumRadius;
		
		//checking collision
		if(distanceSquared <= sumRadiusSquared)
			return true;
		else
			return false;
	}
	//resolve collision
	public void bounce(Orb other)
	{
		// get the mtd
	    Vector2D delta = getPosition().subtract(other.getPosition());	   
	    float d = delta.getLength();
	    // minimum translation distance to push balls apart after intersecting
	    Vector2D mtd = delta.multiply(((RADIUS + other.RADIUS)-d)/d); 

	    // resolve intersection --
	    // inverse mass quantities
	    float im1 = 1 / 1; //If the balls have different masses you can use this
	    float im2 = 1 / 1; //my balls however all have the same mass

	    // push-pull them apart based off their mass
	    setPosition(getPosition().add(mtd.multiply(im1 / (im1 + im2))));
	    other.setPosition(other.getPosition().subtract(mtd.multiply(im2 / (im1 + im2))));

	    // impact speed
	    Vector2D v = (this.velocity.subtract(other.velocity));
	    float vn = v.dot(mtd.normalize());

	    // sphere intersecting but moving away from each other already
	    if (vn > 0.0f) return;

	    // collision impulse
	    float i = (-(1.0f + 0.99f) * vn) / (im1 + im2);
	    Vector2D impulse = mtd.multiply(i);

	    // change in momentum
	    this.velocity = this.velocity.add(impulse.multiply(im1));
	    other.velocity = other.velocity.subtract(impulse.multiply(im2));
	}
	//more fun with private variables
	public void setColor(Graphics g)
	{
		g.setColor(Color.blue);
	}
	
	public int getRADIUS() 
	{
		return RADIUS;
	}
	public Vector2D getPosition() 
	{
		return position;
	}
	public void setPosition(Vector2D position) 
	{
		this.position = position;
	}
	public short getType()
	{
		return type;
	}
	public void setType(short type)
	{
		this.type = type;
	}
}
//helps the player by increasing size and multiplier
class Upgrade extends Orb
{	
	private short type = 1;
	
	Upgrade(int initialLocationX, int initialLocationY, int speedX, int speedY) 
	{
		super(initialLocationX, initialLocationY, speedX, speedY);
	}
	
	public void setColor(Graphics g)
	{
		g.setColor(Color.green);
	}
	
	public short getType()
	{
		return type;
	}
	public void setType(short type)
	{
		this.type = type;
	}
}
//helps player by allowing him to remove blue orbs
class Delete extends Orb
{
	private short type = 2;
	
	Delete(int initialLocationX, int initialLocationY, int speedX, int speedY)
	{
		super(initialLocationX, initialLocationY, speedX, speedY);
	}
	
	public void setColor(Graphics g)
	{
		g.setColor(Color.white);
	}
	
	public short getType()
	{
		return type;
	}
	public void setType(short type)
	{
		this.type = type;
	}
}