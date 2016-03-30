package com.connorlinfoot.mctools.Listeners;

import com.connorlinfoot.mctools.MCTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerRender {
	public static Map<UUID, String> aboveHeadCache = new HashMap<>();
	public static boolean glow = false;

	@SubscribeEvent
	public void render(RenderPlayerEvent.Pre event) {
		EntityPlayer entityPlayer = event.getEntityPlayer();
		if( MCTools.getMcTools().getConfigHandler().isSwapHands() )
			event.getEntityPlayer().setPrimaryHand(event.getEntityPlayer().getPrimaryHand().opposite());

		if (MCTools.getMcTools().getConfigHandler().isGlowPlayers() || glow)
			event.getEntityPlayer().setGlowing(true);
		else if( event.getEntityPlayer().isGlowing() )
			event.getEntityPlayer().setGlowing(false);

		if (entityPlayer.getUniqueID().toString().equals("ad8fefaa-8351-454b-b739-a4eaa872173f")) { // Connor Linfoot ;D
			Minecraft.getMinecraft().theWorld.spawnParticle(EnumParticleTypes.REDSTONE, entityPlayer.posX, entityPlayer.posY + 2, entityPlayer.posZ, 0.0D, 0.0D, 0.0D);
			Minecraft.getMinecraft().theWorld.spawnParticle(EnumParticleTypes.REDSTONE, entityPlayer.posX, entityPlayer.posY + 2, entityPlayer.posZ, 0.0D, 0.0D, 0.0D);
			Minecraft.getMinecraft().theWorld.spawnParticle(EnumParticleTypes.REDSTONE, entityPlayer.posX, entityPlayer.posY + 2, entityPlayer.posZ, 0.0D, 0.0D, 0.0D);
			Minecraft.getMinecraft().theWorld.spawnParticle(EnumParticleTypes.REDSTONE, entityPlayer.posX, entityPlayer.posY + 2, entityPlayer.posZ, 0.0D, 0.0D, 0.0D);
		}

		String s;
		if (event.getEntityPlayer().getUniqueID().equals(MCTools.UUID)) return;
		if (!event.getEntityPlayer().isSneaking() && !event.getEntityPlayer().isInvisible() && (s = aboveHeadCache.get(event.getEntityPlayer().getUniqueID())) != null) {
			double offset = 0.3;
			Scoreboard scoreboard = event.getEntityPlayer().getWorldScoreboard();
			ScoreObjective scoreObjective = scoreboard.getObjectiveInDisplaySlot(2);

			if (scoreObjective != null && scoreObjective.getName() != null && !scoreObjective.getName().equals("")) {
				offset *= 2;
			}

			renderName(event.getRenderer(), s, event.getEntityPlayer(), event.getX(), event.getY() + offset, event.getZ());
		}
	}

	public void renderName(RenderPlayer renderer, String str, EntityPlayer entityIn, double x, double y, double z) {
		FontRenderer fontrenderer = renderer.getFontRendererFromRenderManager();
		float f = 1.6F;
		float f1 = 0.016666668F * f;
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x + 0.0F, (float) y + entityIn.height + 0.5F, (float) z);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-renderer.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(renderer.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(-f1, -f1, f1);
		GlStateManager.disableLighting();
		GlStateManager.depthMask(false);
		GlStateManager.disableDepth();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer worldrenderer = tessellator.getBuffer();
		int i = 0;

		int j = fontrenderer.getStringWidth(str) / 2;
		GlStateManager.disableTexture2D();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos((double) (-j - 1), (double) (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		worldrenderer.pos((double) (-j - 1), (double) (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		worldrenderer.pos((double) (j + 1), (double) (8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		worldrenderer.pos((double) (j + 1), (double) (-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127);
		GlStateManager.enableDepth();
		GlStateManager.depthMask(true);
		fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}

}