package com.rabimi.nicknamer;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class NicknamerClient implements ClientModInitializer {

    // 最初は空文字、自由に設定可能
    private static String nickname = "";

    @Override
    public void onInitializeClient() {

        // コマンド登録
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            registerCommands(dispatcher);
        });
    }

    private void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {

        dispatcher.register(
            ClientCommandManager.literal("rnick")
                .then(ClientCommandManager.argument("name", StringArgumentType.string())
                    .executes(context -> {
                        String newName = StringArgumentType.getString(context, "name");
                        setNickname(newName);
                        context.getSource().sendFeedback(Text.literal("ニックネームを " + newName + " に変更したよ！"));
                        return 1;
                    })
                )
                .then(ClientCommandManager.literal("gui")
                    .executes(context -> {
                        MinecraftClient.getInstance().setScreen(new NicknamerConfigScreen());
                        return 1;
                    })
                )
        );
    }

    public static void setNickname(String newName) {
        if (newName == null) newName = "";
        nickname = newName;
    }

    public static String getNickname() {
        return nickname;
    }

    public static boolean hasNickname() {
        return nickname != null && !nickname.isEmpty();
    }
}
