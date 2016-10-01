package com.revenant.rain.Systems;

import java.util.Random;

public class Screen {
	public int width;
	public int height;
	
	public int[] pixels;
	public int[] tiles = new int[64 * 64];
	
	public Random random = new Random();
	
	public Screen(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		pixels = new int[width * height];
		
		for( int i = 0; i < tiles.length; ++i )
		{
			tiles[i] = random.nextInt(0xffffff);
		}
	}
	
	public void clear()
	{
		for(int i = 0; i < pixels.length; ++i) pixels[i] = 0x000000;
	}
	
	public void render()
	{
		int tileIndex;
		
		for( int y = 0; y < height; ++y ) 
		{
			for( int x = 0; x < width; ++x )
			{
				tileIndex = (x >> 4) + ( (y >> 4) << 6 );
				pixels[x + y * width] = tiles[tileIndex];//0xff00ff;
			}
		}
	}
}
