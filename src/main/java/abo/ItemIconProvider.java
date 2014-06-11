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

package abo;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import buildcraft.api.core.IIconProvider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author Flow86
 * 
 */
public class ItemIconProvider implements IIconProvider {

	public static final int TriggerEngineSafe = 0;
	public static final int ActionSwitchOnPipe = 1;
	public static final int ActionToggleOnPipe = 2;
	public static final int ActionToggleOffPipe = 3;

	public static final int MAX = 4;

	@SideOnly(Side.CLIENT)
	private IIcon[] _icons;

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int iconIndex) {
		return _icons[iconIndex];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		_icons = new IIcon[MAX];

		_icons[TriggerEngineSafe] = iconRegister.registerIcon("additional-buildcraft-objects:triggers/TriggerEngineSafe");
		_icons[ActionSwitchOnPipe] = iconRegister.registerIcon("additional-buildcraft-objects:actions/ActionSwitchOnPipe");
		_icons[ActionToggleOnPipe] = iconRegister.registerIcon("additional-buildcraft-objects:actions/ActionToggleOnPipe");
		_icons[ActionToggleOffPipe] = iconRegister.registerIcon("additional-buildcraft-objects:actions/ActionToggleOffPipe");
	}
}
