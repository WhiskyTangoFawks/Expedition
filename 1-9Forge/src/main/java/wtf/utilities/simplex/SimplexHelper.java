package wtf.utilities.simplex;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SimplexHelper{

	public final String name;
	public Simplex simplex = null;
	
	public SimplexHelper(String simplexName) {
		name = simplexName;
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public SimplexHelper(String simplexName, boolean boo) {
		name = simplexName;
	}
	

	//I could make a simplex wrapper, to hold the simplex, and control the interface for it.
	//Then, it's a simple matter of having it register itself as a listener
	//It can simply never be null
	//and I can put the get simplex noise value behind a try catch, which actually initiates the real simplex
	
	//So- there is not "one world"
	
	public double get2DNoise(World world, BlockPos pos){
		return get2DNoise(world, pos.getX(), pos.getZ());
	}
	
	public double get2DNoise(World world, double x, double z){
		try {
			return simplex.noise(x, z)*0.5+0.5;
		}
		catch (NullPointerException e){
			
			int seed = (int) world.getSeed();
			simplex = new Simplex(seed * this.hashCode());
			return simplex.noise(x, z)*0.5+0.5;
		}
	}
	
	public double get2DRandom(World world, BlockPos pos){
		return get2DNoise(world, pos.getX()*1000, pos.getZ()*1000);
	}
	
	public double get3DRandom(World world, BlockPos pos){
		return get3DNoise(world, pos.getX()*1000, pos.getY()*1000, pos.getZ()*1000);
	}
	
	public double get3DNoise(World world, BlockPos pos){
		return get3DNoise(world, pos.getX(), pos.getY(), pos.getZ());
	}
	
	
	
	public double get3DNoiseShifted(World world, BlockPos pos, int shift){
		return get3DNoise(world, pos.getX()+shift, pos.getY()+shift, pos.getZ()+shift);
	}
	
	public double get3DNoiseScaled(World world, BlockPos pos, double multiplier) {
		return get3DNoise(world, pos.getX()*multiplier, pos.getY()*multiplier, pos.getZ()*multiplier);
	}

	public double get3DNoise(World world, double d, double y, double f){
		try {
			return simplex.noise(d, y, f)*0.5+0.5;
		}
		catch (NullPointerException e){
			int seed = (int) world.getSeed();
			simplex = new Simplex(seed * this.hashCode());
			return simplex.noise(d, y, f)*0.5+0.5;
		}
	}
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event){
		if (!event.getWorld().isRemote){
			System.out.println("Clearing world variable for simplex " + this.name);
			simplex = null;
		}
		MinecraftForge.EVENT_BUS.unregister(this);
	}


	
}
