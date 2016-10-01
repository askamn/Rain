package com.revenant.rain.Systems;

import java.util.Random;

public class Screen {
	public int width;
	public int height;
	
	public static final int MAP_SIZE = 64;
	public static final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public static final int TILE_SIZE = 16;
	public static final int TILE_SIZE_MASK = 4;
	
	public int[] pixels;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	
	public Random random = new Random();
	
	public Screen(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		pixels = new int[width * height];
		
		for( int i = 0; i < MAP_SIZE; ++i )
		{
			tiles[i] = random.nextInt(0xffffff);
		}
		
		for( int j = 1; j < MAP_SIZE; ++j )
		{
			for( int i = 0; i < MAP_SIZE; ++i )
			{
				tiles[ i + j * MAP_SIZE ] = tiles[i];
			}
		}
	}
	
	public void clear()
	{
		for(int i = 0; i < pixels.length; ++i) pixels[i] = 0x000000;
	}
	
	public void render(int xOffset, int yOffset)
	{
		int tileIndex;
		int xx, yy;
		
		for( int y = 0; y < height; ++y ) 
		{
			yy = y + yOffset;
			for( int x = 0; x < width; ++x )
			{
				xx = x + xOffset;
				
				tileIndex = ( (xx >> TILE_SIZE_MASK) & MAP_SIZE_MASK ) + ( ((yy >> TILE_SIZE_MASK) & MAP_SIZE_MASK ) * MAP_SIZE );
				pixels[x + y * width] = tiles[tileIndex];//0xff00ff;
			}
		}
	}
}
