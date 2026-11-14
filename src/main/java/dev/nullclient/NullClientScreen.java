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

        int panelWidth = Math.min(430, this.width - 32);
        int panelHeight = 270;
        int left = (this.width - panelWidth) / 2;
        int top = (this.height - panelHeight) / 2;
        int right = left + panelWidth;
        int bottom = top + panelHeight;

        // Meteor-inspired, original Null Client keybind panel.
        context.fill(left - 1, top - 1, right + 1, bottom + 1, 0xFF39DDEB);
        context.fill(left, top, right, bottom, 0xF0101724);
        context.fill(left, top, right, top + 3, 0xFF39DDEB);

        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal("NULL CLIENT"), this.width / 2, top + 13, 0xFF55E7F2);
        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal("KEYBINDS  •  CONTROLS  •  STATUS"), this.width / 2,
                top + 28, 0xFFB8C8D8);

        int sectionY = top + 47;
        context.fill(left + 18, sectionY, right - 18, sectionY + 17, 0xFF263747);
        context.drawTextWithShadow(textRenderer, Text.literal("FEATURE"),
                left + 27, sectionY + 5, 0xFF91A4B7);
        context.drawTextWithShadow(textRenderer, Text.literal("KEY"),
                right - 105, sectionY + 5, 0xFF91A4B7);
        context.drawTextWithShadow(textRenderer, Text.literal("STATE"),
                right - 58, sectionY + 5, 0xFF91A4B7);

        int rowY = sectionY + 25;
        int rowGap = 29;
        drawFeature(context, left, right, rowY, "Freecam",
                NullClient.getFreecamKeyName(), NullClient.isFreecamEnabled());
        drawFeature(context, left, right, rowY + rowGap, "Open / Close Menu",
                NullClient.getHudKeyName(), "OPEN");
        drawFeature(context, left, right, rowY + rowGap * 2, "Anti-Lag",
                NullClient.getAntiLagKeyName(), NullClient.isAntiLagEnabled());
        drawFeature(context, left, right, rowY + rowGap * 3, "Toggle Sprint",
                NullClient.getToggleSprintKeyName(), NullClient.isToggleSprintEnabled());
        drawFeature(context, left, right, rowY + rowGap * 4, "Toggle Sneak",
                NullClient.getToggleSneakKeyName(), NullClient.isToggleSneakEnabled());

        context.fill(left + 18, bottom - 35, right - 18, bottom - 34, 0xFF46586A);
        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal("Change keys in Options  >  Controls  >  Key Binds  >  Null Client"),
                this.width / 2, bottom - 25, 0xFF91A4B7);
        context.drawCenteredTextWithShadow(textRenderer,
                Text.literal("Press F8 or ESC to close"), this.width / 2,
                bottom - 13, 0xFF55E7F2);
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawFeature(DrawContext context, int left, int right, int y,
                             String name, String key, String state) {
        context.fill(left + 18, y - 4, right - 18, y + 19, 0x401C2A38);
        context.drawTextWithShadow(textRenderer, Text.literal(name),
                left + 27, y + 3, 0xFFEAF2FA);
        context.drawTextWithShadow(textRenderer, Text.literal(key),
                right - 105, y + 3, 0xFF55E7F2);
        context.drawTextWithShadow(textRenderer, Text.literal(state), right - 58, y + 3,
                0xFF55E58A);
    }

    private void drawFeature(DrawContext context, int left, int right, int y,
                             String name, String key, boolean enabled) {
        context.fill(left + 18, y - 4, right - 18, y + 19, 0x401C2A38);
        context.drawTextWithShadow(textRenderer, Text.literal(name),
                left + 27, y + 3, 0xFFEAF2FA);
        context.drawTextWithShadow(textRenderer, Text.literal(key),
                right - 105, y + 3, 0xFF55E7F2);
        context.drawTextWithShadow(textRenderer,
                Text.literal(enabled ? "ON" : "OFF"), right - 58, y + 3,
                enabled ? 0xFF55E58A : 0xFFFF7777);
    }
}
