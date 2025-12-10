package com.rabimi.nicknamer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class NicknamerClient implements ClientModInitializer {

    private static String nickname = "Rabimi";  // ← 好きな名前に書き換えられる！

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                client.player.setCustomName(net.minecraft.text.Text.literal(nickname));
                client.player.setCustomNameVisible(true);
            }
        });
    }

    public static void setNickname(String newName) {
        nickname = newName;
    }
}
