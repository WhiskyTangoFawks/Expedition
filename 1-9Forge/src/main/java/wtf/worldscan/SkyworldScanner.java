package wtf.worldscan;


import net.minecraft.world.chunk.Chunk;
import wtf.worldgen.caves.CaveBiomeGenMethods;

public class SkyworldScanner extends WorldScanner{
	
	
	@Override
	public int scanForSurface(Chunk chunk,CaveBiomeGenMethods gen, int x, int y, int z) {
		y=255;
		while (isAirAndCheck(chunk, gen, x,y,z) && y > 10)
		{
			y--;
		}
		while (!isSurfaceAndCheck(chunk, gen, x, y, z) && y > 10){
			y--;
		}
		return y;
	}

}
