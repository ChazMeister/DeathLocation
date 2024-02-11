package chazmeister.deathlocation.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public class DeathMessageMixin {
	@Inject(method = "init()V", at = @At("HEAD"))
	private void addDeathChatMessage(CallbackInfo info) {

		MinecraftClient mc = MinecraftClient.getInstance();
		
		ChatHud c = mc.inGameHud.getChatHud();
		c.addMessage(Text.literal("Death location: §cX: " + mc.player.getBlockX() + " §aY: " + mc.player.getBlockY() + " §3Z: " + mc.player.getBlockZ()));
	}
}