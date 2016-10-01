package com.revenant.rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.revenant.rain.Systems.Screen;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private static int width = 500;
	private static int height = ( width / 16 ) * 9;
	private static int scale = 3;
	
	private boolean running = false;
	private Thread thread;
	private JFrame frame;
	
	public BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	private Screen screen;
	
	public Game()
	{
		screen = new Screen(width, height);
		
		frame = new JFrame();
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("Rain");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null); /* Centers the window */
		game.frame.setVisible(true);
		
		//JOptionPane.showMessageDialog(game.frame, "Hi");
		
		game.start();
	}
	
	public synchronized void start()
	{
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	public synchronized void stop()
	{
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		long lastTime = System.nanoTime();
		final double ns = 1000000000 / 60.0;
		double delta = 0;
		
		while(running)
		{
			long now = System.nanoTime();
			delta += ( now - lastTime ) / ns;
			lastTime = now;
	
			while( delta >= 1 )
			{	
				this.update();
				delta--;
			}
			
			this.render();
		}
		
		this.stop();
	}
	
	public void render()
	{
		screen.clear();
		screen.render();

		BufferStrategy bs = getBufferStrategy(); // A function from Canvas class
		
		// No BufferStrategy? Create a new one.
		if( bs == null ) 
		{
			createBufferStrategy(3); // 3 for triple buffering
			return;
		}
		
		Graphics gfx = bs.getDrawGraphics(); // Create a graphics context
		
		// Do graphics related stuff
		//gfx.setColor(new Color(80, 40, 100));
		
		gfx.setColor(Color.BLACK);
		gfx.fillRect(0, 0, getWidth(), getHeight());
		for( int i = 0; i < pixels.length; ++i )
		{
			pixels[i] = screen.pixels[i];
		} 
		
		gfx.drawImage(image, getWidth() / 2 - ( (getWidth() / 2)/2 ), getHeight() / 2 - ( (getHeight() / 2)/2 ), getWidth() / 2, getHeight() / 2, null);
		
		gfx.dispose();
		
		// Show the available buffer (out of our 3 buffers, that's why its called TripleBuffering)
		bs.show();
	}
	
	public void update()
	{
		
	}
}
