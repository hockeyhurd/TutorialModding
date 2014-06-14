package com.hockeyhurd.tileentity;

import net.minecraft.tileentity.TileEntity;

public class TileEntityGlowFurnace extends TileEntity {

	private String localizedName;
	
	public void setGuiDisplayName(String displayName) {
		this.localizedName = displayName;
	}

}
