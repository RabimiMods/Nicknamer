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
    private void onSendMessage(String message, boolean addToHistory, CallbackInfo ci) {

        String original = MinecraftClient.getInstance().getSession().getUsername();
        String nick = NicknamerClient.getNickname();

        if (nick == null || nick.isEmpty()) return;

        // /rnick でニック変更
        if (message.startsWith("/rnick ")) {
            NicknamerClient.setNickname(message.substring(7).trim());
            ci.cancel();
            return;
        }

        // 他コマンドはそのままサーバーへ
        if (message.startsWith("/")) return;

        // 自分の発言のみ置き換え
        String replaced = message.replace(original, nick);
        MinecraftClient.getInstance().player.networkHandler.sendChatMessage(replaced);
        ci.cancel();
    }
}
