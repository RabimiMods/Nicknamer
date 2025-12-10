package com.rabimi.nicknamer.mixin;

import com.rabimi.nicknamer.NicknamerClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {

    @Inject(method = "sendMessage", at = @At("HEAD"), cancellable = true)
    private void modifyOutgoingMessage(String message, boolean addToHistory, CallbackInfo ci) {

        String original = MinecraftClient.getInstance().getSession().getUsername();
        String nick = NicknamerClient.getNickname();

        message = message.replace(original, nick);

        MinecraftClient.getInstance().player.networkHandler.sendChatMessage(message, addToHistory);
        ci.cancel();
    }
}
