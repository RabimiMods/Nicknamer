package com.rabimi.nicknamer;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class NicknamerConfigScreen extends Screen {

    private TextFieldWidget field;

    public NicknamerConfigScreen() {
        super(Text.literal("Nicknamer Settings"));
    }

    @Override
    protected void init() {
        field = new TextFieldWidget(textRenderer, width / 2 - 100, height / 2 - 10, 200, 20, Text.literal("Nickname"));
        field.setText(NicknamerClient.getNickname());
        addSelectableChild(field);

        addDrawableChild(ButtonWidget.builder(Text.literal("保存"), (btn) -> {
            NicknamerClient.setNickname(field.getText());
            close();
        }).dimensions(width / 2 - 40, height / 2 + 20, 80, 20).build());
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(textRenderer, "ニックネーム設定", width / 2, height / 2 - 40, 0xFFFFFF);
        super.render(ctx, mouseX, mouseY, delta);
    }
}
