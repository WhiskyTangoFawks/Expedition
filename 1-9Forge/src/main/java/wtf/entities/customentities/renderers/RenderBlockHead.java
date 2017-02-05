package wtf.entities.customentities.renderers;


import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import wtf.entities.customentities.EntityBlockHead;

public class RenderBlockHead extends RenderLivingBase<EntityBlockHead>{
	 protected ResourceLocation TEXTURE;

	   /** this field is used to indicate the 3-pixel wide arms */
	    private final boolean smallArms;

	    public RenderBlockHead(RenderManager renderManager)
	    {
	        this(renderManager, false);
	    }

	    public RenderBlockHead(RenderManager renderManager, boolean useSmallArms)
	    {
	        super(renderManager, new ModelPlayer(0.0F, useSmallArms), 0.5F);
	        TEXTURE = new ResourceLocation("textures/entity/steve.png");
	        this.smallArms = useSmallArms;
	        this.addLayer(new LayerBipedArmor(this));
	        this.addLayer(new LayerHeldItem(this));
	        this.addLayer(new LayerArrow(this));
	        //this.addLayer(new LayerDeadmau5Head(this));
	        //this.addLayer(new LayerCape(this));
	        this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
	        //this.addLayer(new LayerElytra(this));
	    }

	    @Override
		public ModelPlayer getMainModel()
	    {
	        return (ModelPlayer)super.getMainModel();
	    }

	    /**
	     * Renders the desired {@code T} type Entity.
	     */
	    @Override
		public void doRender(EntityBlockHead entity, double x, double y, double z, float entityYaw, float partialTicks)
	    {
	        //if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderPlayerEvent.Pre(entity, this, partialTicks, x, y, z))) return;
	        //if (!entity.isUser() || this.renderManager.renderViewEntity == entity)
	        //{
	            double d0 = y;

	            //if (entity.isSneaking() && !(entity instanceof EntityPlayerSP))
	            //{
	                //d0 = y - 0.125D;
	            //}

	            //this.setModelVisibilities(entity);
	            GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
	            super.doRender(entity, x, d0, z, entityYaw, partialTicks);
	            GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
	        //}
	        //net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderPlayerEvent.Post(entity, this, partialTicks, x, y, z));
	    }
/*
	    private void setModelVisibilities(EntityBlockHead entity)
	    {
	        ModelPlayer modelplayer = this.getMainModel();


	            ItemStack itemstack = entity.getHeldItemMainhand();
	            ItemStack itemstack1 = entity.getHeldItemOffhand();
	            modelplayer.setInvisible(true);
	            modelplayer.bipedHeadwear.showModel = entity.isWearing(EnumPlayerModelParts.HAT);
	            modelplayer.bipedBodyWear.showModel = entity.isWearing(EnumPlayerModelParts.JACKET);
	            modelplayer.bipedLeftLegwear.showModel = entity.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
	            modelplayer.bipedRightLegwear.showModel = entity.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
	            modelplayer.bipedLeftArmwear.showModel = entity.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
	            modelplayer.bipedRightArmwear.showModel = entity.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
	            modelplayer.isSneak = entity.isSneaking();
	            ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
	            ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;

	            if (itemstack != null)
	            {
	                modelbiped$armpose = ModelBiped.ArmPose.ITEM;

	                if (entity.getItemInUseCount() > 0)
	                {
	                    EnumAction enumaction = itemstack.getItemUseAction();

	                    if (enumaction == EnumAction.BLOCK)
	                    {
	                        modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
	                    }
	                    else if (enumaction == EnumAction.BOW)
	                    {
	                        modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
	                    }
	                }
	            }

	            if (itemstack1 != null)
	            {
	                modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;

	                if (entity.getItemInUseCount() > 0)
	                {
	                    EnumAction enumaction1 = itemstack1.getItemUseAction();

	                    if (enumaction1 == EnumAction.BLOCK)
	                    {
	                        modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
	                    }
	                    // FORGE: fix MC-88356 allow offhand to use bow and arrow animation
	                    else if (enumaction1 == EnumAction.BOW)
	                    {
	                        modelbiped$armpose1 = ModelBiped.ArmPose.BOW_AND_ARROW;
	                    }
	                }
	            }

	            if (entity.getPrimaryHand() == EnumHandSide.RIGHT)
	            {
	                modelplayer.rightArmPose = modelbiped$armpose;
	                modelplayer.leftArmPose = modelbiped$armpose1;
	            }
	            else
	            {
	                modelplayer.rightArmPose = modelbiped$armpose1;
	                modelplayer.leftArmPose = modelbiped$armpose;
	            }
	        
	    }
*/
	    /**
	     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	     */
	    @Override
		protected ResourceLocation getEntityTexture(EntityBlockHead entity)
	    {
	        return TEXTURE;
	    }

	    @Override
		public void transformHeldFull3DItemLayer()
	    {
	        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
	    }

	    /**
	     * Allows the render to do state modifications necessary before the model is rendered.
	     */
	    @Override
		protected void preRenderCallback(EntityBlockHead entitylivingbaseIn, float partialTickTime)
	    {
	        float f = 0.9375F;
	        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
	    }



	    public void renderRightArm(EntityBlockHead clientPlayer)
	    {
	        float f = 1.0F;
	        GlStateManager.color(1.0F, 1.0F, 1.0F);
	        float f1 = 0.0625F;
	        ModelPlayer modelplayer = this.getMainModel();
	        //this.setModelVisibilities(clientPlayer);
	        GlStateManager.enableBlend();
	        modelplayer.swingProgress = 0.0F;
	        modelplayer.isSneak = false;
	        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
	        modelplayer.bipedRightArm.rotateAngleX = 0.0F;
	        modelplayer.bipedRightArm.render(0.0625F);
	        modelplayer.bipedRightArmwear.rotateAngleX = 0.0F;
	        modelplayer.bipedRightArmwear.render(0.0625F);
	        GlStateManager.disableBlend();
	    }

	    public void renderLeftArm(EntityBlockHead clientPlayer)
	    {
	        float f = 1.0F;
	        GlStateManager.color(1.0F, 1.0F, 1.0F);
	        float f1 = 0.0625F;
	        ModelPlayer modelplayer = this.getMainModel();
	        //this.setModelVisibilities(clientPlayer);
	        GlStateManager.enableBlend();
	        modelplayer.isSneak = false;
	        modelplayer.swingProgress = 0.0F;
	        modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, clientPlayer);
	        modelplayer.bipedLeftArm.rotateAngleX = 0.0F;
	        modelplayer.bipedLeftArm.render(0.0625F);
	        modelplayer.bipedLeftArmwear.rotateAngleX = 0.0F;
	        modelplayer.bipedLeftArmwear.render(0.0625F);
	        GlStateManager.disableBlend();
	    }

	    /**
	     * Sets a simple glTranslate on a LivingEntity.
	     */
	    @Override
		protected void renderLivingAt(EntityBlockHead entityLivingBaseIn, double x, double y, double z)
	    {
	        //if (entityLivingBaseIn.isEntityAlive() && entityLivingBaseIn.isPlayerSleeping())
	        //{
	         //   super.renderLivingAt(entityLivingBaseIn, x + (double)entityLivingBaseIn.renderOffsetX, y + (double)entityLivingBaseIn.renderOffsetY, z + (double)entityLivingBaseIn.renderOffsetZ);
	        //}
	        //else
	        //{
	            super.renderLivingAt(entityLivingBaseIn, x, y, z);
	        //}
	    }

	    @Override
		protected void rotateCorpse(EntityBlockHead entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks)
	    {
	       
	       if (entityLiving.isElytraFlying())
	        {
	            super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
	            float f = entityLiving.getTicksElytraFlying() + partialTicks;
	            float f1 = MathHelper.clamp_float(f * f / 100.0F, 0.0F, 1.0F);
	            GlStateManager.rotate(f1 * (-90.0F - entityLiving.rotationPitch), 1.0F, 0.0F, 0.0F);
	            Vec3d vec3d = entityLiving.getLook(partialTicks);
	            double d0 = entityLiving.motionX * entityLiving.motionX + entityLiving.motionZ * entityLiving.motionZ;
	            double d1 = vec3d.xCoord * vec3d.xCoord + vec3d.zCoord * vec3d.zCoord;

	            if (d0 > 0.0D && d1 > 0.0D)
	            {
	                double d2 = (entityLiving.motionX * vec3d.xCoord + entityLiving.motionZ * vec3d.zCoord) / (Math.sqrt(d0) * Math.sqrt(d1));
	                double d3 = entityLiving.motionX * vec3d.zCoord - entityLiving.motionZ * vec3d.xCoord;
	                GlStateManager.rotate((float)(Math.signum(d3) * Math.acos(d2)) * 180.0F / (float)Math.PI, 0.0F, 1.0F, 0.0F);
	            }
	        }
	        else
	        {
	            super.rotateCorpse(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
	        }
	    }

	    @Override
		public void renderName(EntityBlockHead entity, double x, double y, double z)
	    {
	      
	    }
}
