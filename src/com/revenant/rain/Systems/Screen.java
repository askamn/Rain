package com.revenant.rain.Systems;

public class Screen {
	public int width;
	public int height;
	
	public int[] pixels;
	
	public int counter;
	public int time;
	
	public Screen(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		pixels = new int[width * height];
	}
	
	public void clear()
	{
		for(int i = 0; i < pixels.length; ++i) pixels[i] = 0x000000;
	}
	
	public void render()
	{
		counter++;
		if( counter % 2 == 0 ) time++;
		
		for( int y = 0; y < height; ++y ) 
		{
			for( int x = 0; x < width; ++x )
			{
				pixels[x + y * width] = 0x502864;//0xff00ff;
				/*pixels[time + time * width] = 0x502864;
				pixels[time+1 + time * width] = 0x502864;
				pixels[time + (time+1) * width] = 0x502864;
				pixels[time+1 + (time+1)* width] = 0x502864;*/
			}
		}
	}
}
