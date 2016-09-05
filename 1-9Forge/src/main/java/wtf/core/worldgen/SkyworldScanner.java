package wtf.core.worldgen;


import net.minecraft.world.chunk.Chunk;

public class SkyworldScanner extends WorldScanner{
	
	
	@Override
	public int scanForSurface(Chunk chunk, int x, int y, int z) {
		y=255;
		while (isAirAndCheck(chunk, x,y,z) && y > 10)
		{
			y--;
		}
		while (!isSurfaceAndCheck(chunk, x, y, z) && y > 10){
			y--;
		}
		return y;
	}

}
