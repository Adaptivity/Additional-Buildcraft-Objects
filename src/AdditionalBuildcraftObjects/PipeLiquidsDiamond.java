/** 
 * Copyright (C) 2011 Flow86
 * 
 * AdditionalBuildcraftObjects is open-source.
 *
 * It is distributed under the terms of my Open Source License. 
 * It grants rights to read, modify, compile or run the code. 
 * It does *NOT* grant the right to redistribute this software or its 
 * modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 */

package net.minecraft.src.AdditionalBuildcraftObjects;

import net.minecraft.src.buildcraft.api.Orientations;
import net.minecraft.src.buildcraft.transport.Pipe;
import net.minecraft.src.buildcraft.transport.PipeTransportLiquids;

/**
 * @author Flow86
 *
 */
public class PipeLiquidsDiamond extends Pipe {

	private static int baseTexture = 13 * 16 + 0;
	private int nextTexture = baseTexture;
	
	public PipeLiquidsDiamond(int itemID) {
		super(new PipeTransportLiquids(), new PipeLogicLiquidsDiamond(), itemID);
			
		((PipeTransportLiquids)transport).flowRate = 80;
		((PipeTransportLiquids)transport).travelDelay = 2;
	}
	
	@Override
	public int getMainBlockTexture() {
		return nextTexture;
	}
	
	@Override
	public void prepareTextureFor(Orientations connection) {
		if (connection == Orientations.Unknown) {
			nextTexture = baseTexture;
		} else {
			nextTexture = baseTexture + connection.ordinal() + 1;
		}
	}
}
