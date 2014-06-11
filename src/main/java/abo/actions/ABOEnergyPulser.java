/** 
 * Copyright (C) 2011-2013 Flow86
 * 
 * AdditionalBuildcraftObjects is open-source.
 *
 * It is distributed under the terms of my Open Source License. 
 * It grants rights to read, modify, compile or run the code. 
 * It does *NOT* grant the right to redistribute this software or its 
 * modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 */

package abo.actions;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.mj.IBatteryObject;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler.Type;

/**
 * @author Flow86
 * 
 */
public class ABOEnergyPulser {

	private double powerReceptor;

	private boolean isActive = false;

	public ABOEnergyPulser(double receptor) {
		powerReceptor = receptor;
	}

	public void update() {
		if (isActive)
			powerReceptor += 1.0D;
	}

	public void enablePulse() {
		isActive = true;
	}

	public void disablePulse() {
		isActive = false;
	}

	public boolean isActive() {
		return isActive;
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setBoolean("isActive", isActive);
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		isActive = nbttagcompound.getBoolean("isActive");
	}
}
