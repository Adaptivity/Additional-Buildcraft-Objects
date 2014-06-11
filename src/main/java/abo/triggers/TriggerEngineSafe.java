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

package abo.triggers;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import abo.ItemIconProvider;
import buildcraft.api.gates.ITrigger;
import buildcraft.api.gates.ITriggerParameter;
import buildcraft.energy.TileEngine;
import buildcraft.energy.TileEngine.EnergyStage;
import buildcraft.transport.IPipeTrigger;
import buildcraft.transport.Pipe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Flow86
 * 
 */
public class TriggerEngineSafe extends ABOTrigger implements IPipeTrigger {

	public TriggerEngineSafe(int id) {
		super("enginesafe");
	}

	private boolean checkEngine(TileEntity tile) {
		if (tile instanceof TileEngine) {
			TileEngine engine = (TileEngine) tile;

			if (engine.getEnergyStage() != EnergyStage.BLUE && engine.getEnergyStage() != EnergyStage.GREEN)
				return false;

			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "Engine Safe";
	}

	@Override
	public boolean isTriggerActive(Pipe pipe, ITriggerParameter parameter) {
		boolean result = true;

		for (ForgeDirection o : ForgeDirection.VALID_DIRECTIONS) {

			TileEntity entity = pipe.container.getTile(o);
			if (entity instanceof TileEngine) {
				if (!checkEngine(entity))
					result = false;
			}
		}

		return result;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconIndex() {
		return ItemIconProvider.TriggerEngineSafe;
	}

	@Override
	public ITrigger rotateLeft() {
		return this;
	}
}
