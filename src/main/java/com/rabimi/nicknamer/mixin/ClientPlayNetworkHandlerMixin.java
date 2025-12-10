package com.rabimi.nicknamer.mixin;

import com.rabimi.nicknamer.NicknamerClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
    private void interceptChat(GameMessageS2CPacket packet, CallbackInfo ci) {

        String originalName = MinecraftClient.getInstance().getSession().getUsername();
        String nick = NicknamerClient.getNickname();

        String msg = packet.content().getString().replace(originalName, nick);

        ci.cancel();
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal(msg));
    }
}
