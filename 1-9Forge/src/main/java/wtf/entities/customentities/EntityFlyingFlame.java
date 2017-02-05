package wtf.entities.customentities;

import java.util.Random;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFlyingFlame extends EntityBat{

	private static Random random = new Random();
	
	private float flameSize;

	public EntityFlyingFlame(World worldIn) {
		super(worldIn);
		this.flameSize = 0.25F;
		this.enablePersistence();
		
	}
	
 
	
    @Override
	protected void applyEntityAttributes()
    {
    	super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
       // this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
        
    }
	
    @Override
	public float getBrightness(float partialTicks)
    {
        return 1.0F;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return flameOn;
    }
	
    @Override
	public float getRenderSizeModifier()
    {
        return 0.1F;
    }
    
    public float getFlameSize(){
    	return flameSize ;
    }
    
    int count = 0;
    boolean flameOn = true;
    @Override
	
    public void onUpdate()
    {
       super.onUpdate();
       
        	count++;
        	if (count > 30){
        		
        		float light = this.worldObj.getLightBrightness(new BlockPos(this.posX, this.posY, this.posZ));
        		flameSize = 1.5F-light;
        		flameOn = !flameOn;
        		count = flameOn ? 0-random.nextInt(100)-100 : 0-random.nextInt(30);
        	}
        }
           
    @Override
	protected float getSoundPitch()
    {
    	return 1F;
    }

  
}
