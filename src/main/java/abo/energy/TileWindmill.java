package abo.energy;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.transport.IPipeTile;
import buildcraft.core.utils.MathUtils;
import buildcraft.energy.TileEngine;
import buildcraft.transport.TileGenericPipe;

@SuppressWarnings("deprecation")
public class TileWindmill extends TileEngine {

	static final float						MAX_OUTPUT				= 1.5f;
	static final float						MIN_OUTPUT				= MAX_OUTPUT / 3;
	public float							TARGET_OUTPUT			= 0.1f;
	private float							BIOME_OUTPUT			= 0.175f;
	private float							HEIGHT_OUTPUT			= 0f;
	// final float kp = 1f;
	// final float ki = 0.05f;
	// final double eLimit = (MAX_OUTPUT - MIN_OUTPUT) / ki;
	int										burnTime				= 0;
	int										totalBurnTime			= 0;
	// double esum = 0;

	int										tickCount				= 0;

	private boolean							checkOrientation			= false;

	public static final ResourceLocation	TRUNK_BLUE_TEXTURE		= new ResourceLocation(
																			"additional-buildcraft-objects:textures/blocks/trunk_blue.png");
	public static final ResourceLocation	TRUNK_GREEN_TEXTURE		= new ResourceLocation(
																			"additional-buildcraft-objects:textures/blocks/trunk_green.png");
	public static final ResourceLocation	TRUNK_YELLOW_TEXTURE	= new ResourceLocation(
																			"additional-buildcraft-objects:textures/blocks/trunk_yellow.png");
	public static final ResourceLocation	TRUNK_RED_TEXTURE		= new ResourceLocation(
																			"additional-buildcraft-objects:textures/blocks/trunk_red.png");

	public float realCurrentOutput = 0;
	
	public TileWindmill() {
		super();
	}

	@Override
	public ResourceLocation getBaseTexture() {
		return BASE_TEXTURES[0];
	}

	@Override
	public ResourceLocation getChamberTexture() {
		return CHAMBER_TEXTURES[0];
	}
	
	

	public ResourceLocation getTrunkTexture(EnergyStage stage) {
		switch (stage) {
			case BLUE:
				return TRUNK_BLUE_TEXTURE;
			case GREEN:
				return TRUNK_GREEN_TEXTURE;
			case YELLOW:
				return TRUNK_YELLOW_TEXTURE;
			case RED:
				return TRUNK_RED_TEXTURE;
			default:
				return TRUNK_RED_TEXTURE;
		}
	}

	@Override
	public void initialize() {
		super.initialize();
		updateTargetOutputFirst();
		checkOrientation = true;
	}

	@Override
	public boolean isBurning() {
		return isRedstonePowered;
	}

	@Override
	public int maxEnergyReceived() {
		return 0;
	}

	@Override
	public int maxEnergyExtracted() {
		return getMaxEnergy()/10;
	}

	@Override
	public int getMaxEnergy() {
		return 100000;
	}

	@Override
	public int calculateCurrentOutput() {
		updateTargetOutput();
		realCurrentOutput = realCurrentOutput + (TARGET_OUTPUT - currentOutput) / 200;
		return Math.round(realCurrentOutput);
	}

	private void updateTargetOutput() {
		if (isRedstonePowered) {
			TARGET_OUTPUT = (float) (0.175f + MathUtils.clamp(BIOME_OUTPUT + HEIGHT_OUTPUT, 0.0f, 1.2f)
					+ (getWorldObj().rainingStrength / 8f)) * 10000;
		} else {
			TARGET_OUTPUT = 0;
		}
	}

	private void updateTargetOutputFirst() {
		BiomeGenBase biome = getWorldObj().getBiomeGenForCoords(xCoord, zCoord);
		if (Math.round(biome.heightVariation + 0.2f) == 1.0f) {
			BIOME_OUTPUT = 0.0f;
			HEIGHT_OUTPUT = (float) MathUtils.clamp((yCoord - 58) / 66f, 0f, 1.2f);
		} else {
			BIOME_OUTPUT = (float) MathUtils.clamp(1.2f - biome.heightVariation, 0f, 1.2f);
			float distFrom64Mod = (float) (0.2f * (-0.00077160494 * yCoord * yCoord + 0.10185 * yCoord - 2.36111111));
			HEIGHT_OUTPUT = (float) MathUtils.clamp(distFrom64Mod, 0f, 0.2f);
		}
		updateTargetOutput();
	}

	public void checkRedstonePower() {
		isRedstonePowered = canGetWind();
	}

	@Override
	protected EnergyStage computeEnergyStage() {
		double energyLevel = currentOutput;
		if (energyLevel < 375f) {
			return EnergyStage.BLUE;
		} else if (energyLevel < 750f) {
			return EnergyStage.GREEN;
		} else if (energyLevel < 1374f) {
			return EnergyStage.YELLOW;
		} else {
			return EnergyStage.RED;
		}
	}

	public void updateEntity() {
		super.updateEntity();

		if (worldObj.isRemote) {

			if (currentOutput != 0) progress += getPistonSpeed();

			if (progress > 1) {
				progressPart = 0;
				progress = 0;
			}

			return;
		}

		if (checkOrientation) {
			checkOrientation = false;

			if (!isOrientationValid()) {
				switchOrientation(true);
			}
		}

		if (!isRedstonePowered) {
			if (energy > 1) {
				energy--;
			}
		}

		updateHeatLevel();
		getEnergyStage();
		engineUpdate();

		TileEntity tile = getTileBuffer(orientation).getTile();

		if (currentOutput != 0) progress += getPistonSpeed();

		if (progress > 0.5 && progressPart == 1) {
			progressPart = 2;
			if (progressPart != 0) sendPower(); // Comment out for constant
												// power
		} else if (progress >= 1) {
			progress = 0;
			progressPart = 0;
		}

		if (isRedstonePowered && isActive()) {
			if (isPoweredTile(tile, orientation)) {
				if (getPowerToExtract() > 0) {
					progressPart = 1;
					setPumping(true);
				} else {
					//setPumping(false);
				}
			} else {
				setPumping(false);
			}
		} else {
			setPumping(false);
		}

		// Uncomment for constant power
		// if (isRedstonePowered && isActive()) {
		// sendPower();
		// } else currentOutput = 0;

		burn();
	}

	private int getPowerToExtract() {
		TileEntity tile = getTileBuffer(orientation).getTile();

		if (tile instanceof IEnergyHandler) {
			IEnergyHandler handler = (IEnergyHandler) tile;

			int minEnergy = 0;
			int maxEnergy = handler.receiveEnergy(
					orientation.getOpposite(),
					Math.round(this.energy), true);
			return extractEnergy(minEnergy * 100, maxEnergy * 100, false);
		} else if (tile instanceof IPowerReceptor) {
			PowerReceiver receptor = ((IPowerReceptor) tile)
					.getPowerReceiver(orientation.getOpposite());

			return extractEnergy((int) Math.floor(receptor.getMinEnergyReceived() * 1000),
					(int) Math.ceil(receptor.getMaxEnergyReceived() * 1000), false);
		} else {
			return 0;
		}
	}

	protected void sendPower() {
		TileEntity tile = getTileBuffer(orientation).getTile();
		if (isPoweredTile(tile, orientation)) {
			int extracted = getPowerToExtract();

			if (tile instanceof IEnergyHandler) {
				IEnergyHandler handler = (IEnergyHandler) tile;
				if (extracted > 0) {
					int neededRF = handler.receiveEnergy(
							orientation.getOpposite(),
							(int) Math.round(extracted) / 100, false);

					extractEnergy(0, neededRF / 100, true);
				}
			} else if (tile instanceof IPowerReceptor) {
				PowerReceiver receptor = ((IPowerReceptor) tile)
						.getPowerReceiver(orientation.getOpposite());

				if (extracted > 0) {
					double neededMJ = receptor.receiveEnergy(
							PowerHandler.Type.ENGINE, extracted / 1000.0,
							orientation.getOpposite());

					extractEnergy((int) Math.floor(receptor.getMinEnergyReceived() * 1000), (int) Math.ceil(neededMJ * 1000), true);
				}
			}
		}
	}

	private boolean canGetWind() {
		for (int i = -1; i < 2; i++) {
			for (int j = -2; j <= 2; j++) {
				for (int k = -2; k <= 2; k++) {
					if (getWorldObj().getBlock(xCoord + j, yCoord + i, zCoord + k).isOpaqueCube()) return false;
				}
			}
		}
		return true;
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		checkOrientation = true;
	}

	public boolean isOrientationValid() {
		if (orientation == ForgeDirection.EAST) return false;
		TileEntity tile = getTileBuffer(orientation).getTile();

		return isPoweredTile(tile, orientation);
	}

	public boolean switchOrientation(boolean preferPipe) {
		if (preferPipe && switchOrientationDo(true)) {
			return true;
		} else {
			return switchOrientationDo(false);
		}
	}

	private boolean switchOrientationDo(boolean pipesOnly) {
		for (int i = orientation.ordinal() + 1; i <= orientation.ordinal() + 6; ++i) {
			ForgeDirection o = ForgeDirection.VALID_DIRECTIONS[i % 6];
			if (o == ForgeDirection.EAST) continue;
			TileEntity tile = getTileBuffer(o).getTile();

			if ((!pipesOnly || tile instanceof IPipeTile) && isPoweredTile(tile, o)) {
				orientation = o;
				getWorldObj().markBlockForUpdate(xCoord, yCoord, zCoord);
				getWorldObj().notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
						worldObj.getBlock(xCoord, yCoord, zCoord));
				getWorldObj().notifyBlocksOfNeighborChange(xCoord + o.offsetX, yCoord + o.offsetY, zCoord + o.offsetZ,
						worldObj.getBlock(xCoord, yCoord, zCoord));

				if(tile instanceof TileGenericPipe)
				{
					((TileGenericPipe)tile).scheduleNeighborChange();
					((TileGenericPipe)tile).updateEntity();
				}

				return true;
			}
		}

		return false;
	}

	@Override
	public void burn() {
		if (burnTime > 0) {
			burnTime--;

			int output = calculateCurrentOutput();
			currentOutput = output; // Comment out for constant power
			addEnergy(output);
		}
		
		if(tickCount % 60 == 0)
		{
			checkRedstonePower();
		}

		if (burnTime == 0 && isRedstonePowered) {
			burnTime = totalBurnTime = 1200;
			updateTargetOutput();
		} else {
			if (tickCount >= 1198) {
				updateTargetOutput();

				tickCount = 0;
			} else {
				tickCount++;
			}

		}
	}

	public int getScaledBurnTime(int i) {
		return (int) (((float) burnTime / (float) totalBurnTime) * i);
	}

	/*
	 * @Override public void engineUpdate() { super.engineUpdate();
	 * 
	 * if (isRedstonePowered) { double output = getCurrentOutput();
	 * currentOutput = output; // Comment out for constant power
	 * addEnergy(output); } }
	 */

	@Override
	public float explosionRange() {
		return 1;
	}
	
	//RF
	
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract,
			boolean simulate) {
		return this.extractEnergy(0, maxExtract, !simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		if (!(from == orientation)) {
			return 0;
		}

		return energy;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return this.getMaxEnergy();
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from == orientation;
	}

}
