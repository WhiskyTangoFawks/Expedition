package wtf.blocks;

import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import wtf.Core;

public class BlockFoxfire extends AbstractActiveablePlant{

	protected static final AxisAlignedBB hangingBB = new AxisAlignedBB(0.2F, 0.0F, 0.2F, 0.8F, 0.8F, 0.8F);
	protected static final AxisAlignedBB sittingBB = new AxisAlignedBB(0.2F, 0.0F, 0.2F, 0.8F, 0.8F, 0.8F);

	public static final PropertyBool HANGING = PropertyBool.create("hanging");
	
	private static final int lightCutoff = 7;

	public BlockFoxfire()
	{
		this.setTickRandomly(true);
		
		this.setDefaultState(this.blockState.getBaseState().withProperty(HANGING, false).withProperty(ACTIVE, false));
		this.setCreativeTab(Core.wtfTab);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return true;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		
		IBlockState other = worldIn.getBlockState(pos);
		if (other.getBlock() != this)
		{
			return other.getLightValue(worldIn, pos);
		}
		//Update lit value for this position
		if (state.getValue(ACTIVE)){
			return 6;
		}
		return 0;
	}
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (!canBlockStay(world, pos, state)){
			world.destroyBlock(pos, true);
		}
		if (world.getLight(pos) < lightCutoff){
			if(!state.getValue(ACTIVE)){
				world.setBlockState(pos, state.withProperty(ACTIVE, true));
			}
			else {
				world.scheduleBlockUpdate(pos, this, 50, 10);
			}
		}
		else if (state.getValue(ACTIVE)){
			world.setBlockState(pos, state.withProperty(ACTIVE, false));
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		world.scheduleBlockUpdate(pos, this, 20, 20);
		return true;
	}

	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entityIn)
	{
		world.scheduleBlockUpdate(pos, this, 20, 20);
	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		updateTick(world, pos, state, random);
		//Try to spread
		if (random.nextInt(250) == 0)
		{
			//System.out.println("trying to spread");
			int count = 0;
			ArrayList<BlockPos> arraylist = new ArrayList<BlockPos>();
			for (int xloop = -2; xloop < 3; xloop++){
				for (int zloop = -2; zloop < 3; zloop++){
					for (int yloop = -2; yloop < 4 ; yloop++){
						BlockPos checkPos = new BlockPos(pos.getX()+xloop, pos.getY()+yloop, pos.getZ()+zloop);
						if (world.getBlockState(checkPos).getBlock() instanceof BlockFoxfire){
							count++;
						}
						if (world.getBlockState(checkPos).getMaterial() == Material.WOOD){
							if (world.isAirBlock(checkPos.up())){
								arraylist.add(checkPos.up());
							}
							if (world.isAirBlock(checkPos.down())){
								arraylist.add(checkPos.down());
							}	
						}
					}
				}
			}
			
			if (count < 5){
				
				if  (arraylist.size() > 0){
					BlockPos spawnPos = arraylist.get(random.nextInt(arraylist.size()));
					world.setBlockState(spawnPos, this.getDefaultState(), 3);
				}
			}
		}
	}


	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
	{
		if (state.getValue(HANGING)){			
			return  world.getBlockState(pos.up()).getMaterial() == Material.WOOD;
		}
		else {
			return  world.getBlockState(pos.down()).getMaterial() == Material.WOOD;
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		if (state.getValue(HANGING)){
			return hangingBB;
		}
		else {
			return sittingBB;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random)
	{
		if (state.getValue(ACTIVE) && random.nextInt(15) == 0){

			world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + random.nextFloat(), pos.getY() + 0.5F, pos.getZ() + random.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {HANGING, ACTIVE});
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		worldIn.scheduleBlockUpdate(pos, this, 100, 10);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		switch (meta){
		case 0:
			return getDefaultState();
		case 1: 
			return getDefaultState().withProperty(HANGING, true);
		case 2:
			return getDefaultState().withProperty(ACTIVE, true);
		case 3:
			return getDefaultState().withProperty(ACTIVE, true).withProperty(HANGING, true);
		default :
			return null;
		}

	}

	@Override
	public int getMetaFromState(IBlockState state) {

		if (!state.getValue(HANGING)){
			if (!state.getValue(ACTIVE)){
				return 0;
			}
			else { //not hanging, is lit
				return 2;
			}
		}
		else { //is hanging
			if (!state.getValue(ACTIVE)){ //hanging and not lit
				return 3;
			}
			else { //hanging and lit
				return 4;
			}
		}

	}

	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}

}
