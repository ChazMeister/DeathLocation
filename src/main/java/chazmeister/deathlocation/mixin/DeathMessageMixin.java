package chazmeister.deathlocation.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent.ItemStackContent;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathMessageS2CPacket.class)
public class DeathMessageMixin {
	private boolean discard = false;

	@Inject(method = "apply", at = @At("HEAD"))
	private void addDeathChatMessage(CallbackInfo info) {
		// Seems to always run twice, so discard every second time
		if (!discard) {
			MinecraftClient mc = MinecraftClient.getInstance();
			ClientPlayerEntity player = mc.player;

			// Send a message to the client on death
			player.sendMessage(
				Text.literal("Death location: ")
				.append(Text.literal("§cX: " + player.getBlockX() + " §aY: " + player.getBlockY() + " §3Z: " + player.getBlockZ())
				.setStyle(Style.EMPTY
				.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, player.getBlockX() + " " + player.getBlockY() + " " + player.getBlockZ()))
				.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to copy location"))))));
		}
		discard = !discard;
	}
}