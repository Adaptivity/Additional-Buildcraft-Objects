package abo.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import abo.ABO;
import abo.energy.TileWaterwheel;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderWaterwheel extends TileEntitySpecialRenderer {
	
	IModelCustom waterwheelModel;
	ResourceLocation texture;
	

	public RenderWaterwheel() {
		waterwheelModel = AdvancedModelLoader.loadModel(new ResourceLocation("additional-buildcraft-objects:models/waterwheel/waterwheel.obj"));
		texture = new ResourceLocation("additional-buildcraft-objects:models/waterwheel/waterwheel.png");
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z,
			float partialTick) {
		if(!(tileentity instanceof TileWaterwheel)) return;
		
		TileWaterwheel tile = (TileWaterwheel)tileentity;
		
		GL11.glPushMatrix();

		GL11.glTranslated(x + 0.5D,y + 0.5D,z);
		GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
		
		int xCoord = tile.xCoord;
		int yCoord = tile.yCoord;
		int zCoord = tile.zCoord;
		
		float progress = tile.animProgress;
		
		float rotateAngleZ = 0;
		float rotateAngleY = 0;
		float rotateAngleX = 0;
		
		boolean inDist = true;
		try {
			
			if (!(xCoord == -1 && yCoord == -1 && zCoord == -1)) {
				EntityClientPlayerMP player = FMLClientHandler.instance().getClientPlayerEntity();
				double dist = Math.sqrt((xCoord - player.posX) * (xCoord - player.posX) + (yCoord - player.posY)
						* (yCoord - player.posY) + (zCoord - player.posZ) * (zCoord - player.posZ));
				if (dist > ABO.windmillAnimDist) inDist = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ABO.windmillAnimations && inDist) {
			float step = progress * 360;
			rotateAngleY = step;
			rotateAngleY = step;
		}
		
		GL11.glPushMatrix();
		
		if (rotateAngleZ != 0.0F)
        {
            GL11.glRotatef(rotateAngleZ, 0.0F, 0.0F, 1.0F);
        }

        if (rotateAngleY != 0.0F)
        {
            GL11.glRotatef(rotateAngleY, 0.0F, 1.0F, 0.0F);
        }

        if (rotateAngleX != 0.0F)
        {
            GL11.glRotatef(rotateAngleX, 1.0F, 0.0F, 0.0F);
        }
		
		bindTexture(texture);
		waterwheelModel.renderAll();
		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

}