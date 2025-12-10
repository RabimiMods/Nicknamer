package com.rabimi.nicknamer.mixin;

import com.rabimi.nicknamer.NicknamerClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {

    @Inject(method = "getPlayerName", at = @At("HEAD"), cancellable = true)
    private void onGetPlayerName(PlayerListEntry entry, CallbackInfoReturnable<Text> cir) {

        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // 自分だけ置き換え
        if (!entry.getProfile().getId().equals(client.player.getUuid())) return;

        String nick = NicknamerClient.getNickname();
        if (nick == null || nick.isEmpty()) return;

        cir.setReturnValue(Text.literal(nick));
    }
}
