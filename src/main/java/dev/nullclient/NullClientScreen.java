package dev.nullclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class NullClientScreen extends Screen {
    private static final int PANEL_WIDTH = 430;
    private static final int PANEL_HEIGHT = 285;

    public NullClientScreen() {
        super(Text.literal("Null Client"));
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);

        int panelWidth = Math.min(PANEL_WIDTH, this.width - 24);
        int panelHeight = Math.min(PANEL_HEIGHT, this.height - 24);
        int left = (this.width - panelWidth) / 2;
        int top = (this.height - panelHeight) / 2;
        int right = left + panelWidth;
        int bottom = top + panelHeight;

        context.fill(left - 1, top - 1, right + 1, bottom + 1, 0xFF39DDEB);
        context.fill(left, top, right, bottom, 0xF0101724);
        context.fill(left, top, right, top + 3, 0xFF39DDEB);

        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal("NULL CLIENT"), this.width / 2, top + 13, 0xFF55E7F2);
        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal("CLICK A MODULE TO TOGGLE IT"), this.width / 2,
                top + 29, 0xFFB8C8D8);

        int sectionY = top + 49;
        context.fill(left + 18, sectionY, right - 18, sectionY + 17, 0xFF263747);
        context.drawTextWithShadow(textRenderer, Text.literal("MODULE"),
                left + 27, sectionY + 5, 0xFF91A4B7);
        context.drawTextWithShadow(textRenderer, Text.literal("KEY"),
                right - 105, sectionY + 5, 0xFF91A4B7);
        context.drawTextWithShadow(textRenderer, Text.literal("STATE"),
                right - 58, sectionY + 5, 0xFF91A4B7);

        int rowY = sectionY + 25;
        int rowGap = 31;
        drawFeature(context, left, right, rowY, mouseX, mouseY, "Freecam",
                NullClient.getFreecamKeyName(), NullClient.isFreecamEnabled());
        drawFeature(context, left, right, rowY + rowGap, mouseX, mouseY, "Anti-Lag",
                NullClient.getAntiLagKeyName(), NullClient.isAntiLagEnabled());
        drawFeature(context, left, right, rowY + rowGap * 2, mouseX, mouseY, "Toggle Sprint",
                NullClient.getToggleSprintKeyName(), NullClient.isToggleSprintEnabled());
        drawFeature(context, left, right, rowY + rowGap * 3, mouseX, mouseY, "Toggle Sneak",
                NullClient.getToggleSneakKeyName(), NullClient.isToggleSneakEnabled());

        int infoY = bottom - 43;
        context.fill(left + 18, infoY, right - 18, infoY + 1, 0xFF46586A);
        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal("METEOR CLICK GUI: PRESS RIGHT SHIFT"),
                this.width / 2, infoY + 8, 0xFF55E7F2);
        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal("Click a row to toggle • ESC to close"),
                this.width / 2, infoY + 22, 0xFF91A4B7);
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawFeature(DrawContext context, int left, int right, int y,
                             int mouseX, int mouseY, String name, String key, boolean enabled) {
        boolean hovered = mouseX >= left + 18 && mouseX <= right - 18
                && mouseY >= y - 4 && mouseY <= y + 19;
        int background = hovered ? 0xFF344B5E : 0xFF1C2A38;
        context.fill(left + 18, y - 4, right - 18, y + 19, background);
        context.drawTextWithShadow(textRenderer, Text.literal(name),
                left + 27, y + 3, 0xFFEAF2FA);
        context.drawTextWithShadow(textRenderer, Text.literal(key),
                right - 105, y + 3, 0xFF55E7F2);
        context.drawTextWithShadow(textRenderer,
                Text.literal(enabled ? "ON" : "OFF"), right - 58, y + 3,
                enabled ? 0xFF55E58A : 0xFFFF7777);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);

        int panelWidth = Math.min(PANEL_WIDTH, this.width - 24);
        int panelHeight = Math.min(PANEL_HEIGHT, this.height - 24);
        int left = (this.width - panelWidth) / 2;
        int top = (this.height - panelHeight) / 2;
        int right = left + panelWidth;
        int sectionY = top + 49;
        int firstRowY = sectionY + 25;
        int rowGap = 31;

        if (mouseX < left + 18 || mouseX > right - 18) return super.mouseClicked(mouseX, mouseY, button);

        for (int i = 0; i < 4; i++) {
            int y = firstRowY + rowGap * i;
            if (mouseY >= y - 4 && mouseY <= y + 19) {
                MinecraftClient client = MinecraftClient.getInstance();
                switch (i) {
                    case 0 -> NullClient.toggleFreecam(client);
                    case 1 -> NullClient.toggleAntiLag(client);
                    case 2 -> {
                        NullClient.toggleSprint();
                        client.player.sendMessage(Text.literal("§b[Null Client] §fToggle Sprint: "
                                + (NullClient.isToggleSprintEnabled() ? "§aON" : "§cOFF")), true);
                    }
                    case 3 -> {
                        NullClient.toggleSneak();
                        client.player.sendMessage(Text.literal("§b[Null Client] §fToggle Sneak: "
                                + (NullClient.isToggleSneakEnabled() ? "§aON" : "§cOFF")), true);
                    }
                    default -> { }
                }
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
