package com.bergerkiller.bukkit.mw;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MWPlayerListener extends PlayerListener {
	
	public static boolean isSolid(Block b, BlockFace direction) {
		int maxwidth = 10;
		while (true) {
			int id = b.getTypeId();
			if (id == 0) return false;
			if (id != 9 && id != 8) return true;
			b = b.getRelative(direction);
			--maxwidth;
			if (maxwidth <= 0) return false;
		}
	}
	
	@Override
	public void onPlayerMove(PlayerMoveEvent event) {
		Block b = event.getTo().getBlock();
		if (MyWorlds.useWaterTeleport && b.getTypeId() == 9) {
			if (b.getRelative(BlockFace.UP).getTypeId() == 9 ||
					b.getRelative(BlockFace.DOWN).getTypeId() == 9) {
				boolean allow = false;
				if (b.getRelative(BlockFace.NORTH).getType() == Material.AIR ||
						b.getRelative(BlockFace.SOUTH).getType() == Material.AIR) {
					if (isSolid(b, BlockFace.WEST) && isSolid(b, BlockFace.EAST)) {
						allow = true;
					}
				} else if (b.getRelative(BlockFace.EAST).getType() == Material.AIR ||
						b.getRelative(BlockFace.WEST).getType() == Material.AIR) {
					if (isSolid(b, BlockFace.NORTH) && isSolid(b, BlockFace.SOUTH)) {
						allow = true;
					}
				}
				if (allow)
					Portal.handlePortalEnter(event.getPlayer());
			}
		}
	}
}
