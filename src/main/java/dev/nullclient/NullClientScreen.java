package dev.nullclient;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class NullClientScreen extends Screen {
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

        int panelWidth = Math.min(360, this.width - 32);
        int panelHeight = 204;
        int left = (this.width - panelWidth) / 2;
        int top = (this.height - panelHeight) / 2;
        int right = left + panelWidth;
        int bottom = top + panelHeight;

        context.fill(left - 1, top - 1, right + 1, bottom + 1, 0xFF39DDEB);
        context.fill(left, top, right, bottom, 0xF0101724);
        context.fill(left, top, right, top + 2, 0xFF39DDEB);

        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal("NULL UNSTABLE SMP"), this.width / 2, top + 14, 0xFF55E7F2);
        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal("NULL CLIENT  •  MOD MENU"), this.width / 2, top + 29, 0xFFB8C8D8);
        context.fill(left + 18, top + 46, right - 18, top + 47, 0xFF46586A);

        int rowY = top + 58;
        drawFeature(context, left, right, rowY, "Freecam", "F6",
                NullClient.isFreecamEnabled());
        drawFeature(context, left, right, rowY + 25, "HUD / Close Menu", "F8", true);
        drawFeature(context, left, right, rowY + 50, "Anti-Lag", "F9",
                NullClient.isAntiLagEnabled());
        drawFeature(context, left, right, rowY + 75, "Toggle Sprint", "F10",
                NullClient.isToggleSprintEnabled());
        drawFeature(context, left, right, rowY + 100, "Toggle Sneak", "F11",
                NullClient.isToggleSneakEnabled());

        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal("Press F8 or ESC to close"), this.width / 2, bottom - 17, 0xFF91A4B7);
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawFeature(DrawContext context, int left, int right, int y,
                             String name, String key, boolean enabled) {
        context.drawTextWithShadow(textRenderer, Text.literal(name), left + 22, y, 0xFFEAF2FA);
        context.drawTextWithShadow(textRenderer, Text.literal(key), right - 54, y, 0xFF55E7F2);
        context.drawTextWithShadow(textRenderer,
                Text.literal(enabled ? "ON" : "OFF"), right - 28, y,
                enabled ? 0xFF55E58A : 0xFFFF7777);
    }
}
