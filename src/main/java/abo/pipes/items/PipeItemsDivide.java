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

package abo.pipes.items;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;
import abo.PipeIcons;
import abo.pipes.ABOPipe;
import buildcraft.core.ItemWrench;
import buildcraft.transport.PipeTransportItems;
import buildcraft.transport.TransportConstants;
import buildcraft.transport.TravelingItem;
import buildcraft.transport.pipes.events.PipeEventItem;

/**
 * @author Flow86
 * 
 */
public class PipeItemsDivide extends ABOPipe<PipeTransportItems> {

	private byte desiredSize = 1;
	
	public PipeItemsDivide(Item itemID) {
		super(new PipeTransportItems(), itemID);
	}

	@Override
	public int getIconIndex(ForgeDirection direction) {
		return PipeIcons.PipeItemsDivide.ordinal();
	}

	public void eventHandler(PipeEventItem.Entered event) {
		TravelingItem item = event.item;
		ItemStack stack = item.getItemStack();
		while(stack.stackSize > desiredSize)
		{
			ItemStack newStack = stack.splitStack(desiredSize);
			TravelingItem newItem = copyTravelingItem(item, newStack);
			newItem.getExtraData().setBoolean("DONT MERGE ME", true);
			if (transport.canReceivePipeObjects(item.input, newItem))
			{
				newItem.input = newItem.output;
			}
			transport.injectItem(newItem, item.input);
			readjustSpeed(newItem, 5);
		}
		if(stack.stackSize < desiredSize && stack.stackSize > 0)
		{
			item.blacklist.add(item.input);
			item.toCenter = true;
			item.input = transport.resolveDestination(item);
			
			if (!container.getWorldObj().isRemote) {
				item.output = item.input.getOpposite();
			}
			transport.items.unscheduleRemoval(item);
		}else if(stack.stackSize <= 0)
		{
			transport.items.scheduleRemoval(item);
			return;
		}
		readjustSpeed(item, 5);
	}
	
	private TravelingItem copyTravelingItem(TravelingItem item, ItemStack newStack)
	{
		TravelingItem newItem = TravelingItem.make();
		newItem.xCoord = item.xCoord;
		newItem.yCoord = item.yCoord;
		newItem.zCoord = item.zCoord;
		newItem.setSpeed(item.getSpeed());
		newItem.toCenter = item.toCenter;
		newItem.input = item.input;
		newItem.output = item.output;
		newItem.color = item.color == null ? null : item.color;

		if(item.hasExtraData())
		{
			try {
				Field field = newItem.getClass().getDeclaredField("extraData");
				field.setAccessible(true);
				//NBTTagCompound nbt = (NBTTagCompound) field.get(newItem);
				field.set(newItem, item.getExtraData().copy());
				field.setAccessible(false);
			} catch (Exception e){}
			
		}
		newItem.setItemStack(newStack);
		return newItem;
	}

	@Override
	public boolean blockActivated(EntityPlayer entityplayer) {
		if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof ItemWrench)
		{
			incrementMeta();
			if(!container.getWorldObj().isRemote) entityplayer.addChatComponentMessage(new ChatComponentText("Set the desired stack size to "+desiredSize+"."));
			return true;
		}
		return false;
	}
	
	private void incrementMeta()
	{
		desiredSize++;
		if(desiredSize > 8) desiredSize = 1;
	}
	
	public void readjustSpeed(TravelingItem item, int errorMargin) {
		item.setSpeed(Math.min(Math.max(TransportConstants.PIPE_NORMAL_SPEED, item.getSpeed()) * 2f, TransportConstants.PIPE_NORMAL_SPEED * 20F) + (new Random().nextInt(errorMargin) / 1000) - (new Random().nextInt(errorMargin) / 1000));
	}

public void eventHandler(PipeEventItem.FindDest event) {
		
		List<ForgeDirection> result = event.destinations;
		TravelingItem item = event.item;
		List<ForgeDirection> list = new LinkedList<ForgeDirection>();

		if (transport.canReceivePipeObjects(item.input, item))
		{
			list.add(item.input);
			result.clear();
			result.addAll(list);
		}

		
	}
}
