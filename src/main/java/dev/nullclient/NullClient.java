package dev.nullclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.particle.ParticlesMode;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class NullClient implements ClientModInitializer {
    private static final KeyBinding.Category NULL_CLIENT_CATEGORY =
            KeyBinding.Category.create(Identifier.of("nullclient", "controls"));

    private static KeyBinding freecamKey;
    private static KeyBinding autoMineKey;
    private static KeyBinding hudKey;
    private static KeyBinding antiLagKey;
    private static KeyBinding toggleSprintKey;
    private static KeyBinding toggleSneakKey;

    private static boolean freecamEnabled;
    private static boolean autoMineEnabled;
    private static boolean hudEnabled = true;
    private static boolean antiLagEnabled;
    private static boolean toggleSprintEnabled;
    private static boolean toggleSneakEnabled;

    @Override
    public void onInitializeClient() {
        freecamKey = register("key.nullclient.freecam", GLFW.GLFW_KEY_F6);
        autoMineKey = register("key.nullclient.auto_mine", GLFW.GLFW_KEY_F7);
        hudKey = register("key.nullclient.hud", GLFW.GLFW_KEY_F8);
        antiLagKey = register("key.nullclient.anti_lag", GLFW.GLFW_KEY_F9);
        toggleSprintKey = register("key.nullclient.toggle_sprint", GLFW.GLFW_KEY_F10);
        toggleSneakKey = register("key.nullclient.toggle_sneak", GLFW.GLFW_KEY_F11);

        ClientTickEvents.END_CLIENT_TICK.register(NullClient::onClientTick);
        HudElementRegistry.addLast(Identifier.of("nullclient", "hud"), (drawContext, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (!hudEnabled || client.player == null || client.textRenderer == null) return;

            int y = 8;
            drawContext.drawTextWithShadow(client.textRenderer,
                    Text.literal("§bNull Client §7v1.0.1"), 8, y, 0xFFFFFF);
            y += 12;
            drawContext.drawTextWithShadow(client.textRenderer,
                    status("Freecam", freecamEnabled), 8, y, 0xFFFFFF);
            y += 12;
            drawContext.drawTextWithShadow(client.textRenderer,
                    status("Auto Mine", autoMineEnabled), 8, y, 0xFFFFFF);
            y += 12;
            drawContext.drawTextWithShadow(client.textRenderer,
                    status("Anti-Lag", antiLagEnabled), 8, y, 0xFFFFFF);
            y += 12;
            drawContext.drawTextWithShadow(client.textRenderer,
                    status("Toggle Sprint", toggleSprintEnabled), 8, y, 0xFFFFFF);
            y += 12;
            drawContext.drawTextWithShadow(client.textRenderer,
                    status("Toggle Sneak", toggleSneakEnabled), 8, y, 0xFFFFFF);
        });
    }

    private static KeyBinding register(String translationKey, int keyCode) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                translationKey,
                InputUtil.Type.KEYSYM,
                keyCode,
                NULL_CLIENT_CATEGORY
        ));
    }

    private static String status(String name, boolean enabled) {
        return "§7" + name + ": " + (enabled ? "§aON" : "§cOFF");
    }

    private static void onClientTick(MinecraftClient client) {
        while (freecamKey.wasPressed()) {
            freecamEnabled = !freecamEnabled;
            // This is local no-clip movement, not a detached camera.
            if (client.player != null) {
                client.player.noClip = freecamEnabled;
                client.player.setOnGround(false);
            }
            notify(client, "Freecam movement", freecamEnabled);
        }

        while (autoMineKey.wasPressed()) {
            autoMineEnabled = !autoMineEnabled;
            notify(client, "Auto Mine", autoMineEnabled);
        }

        while (hudKey.wasPressed()) {
            hudEnabled = !hudEnabled;
            notify(client, "HUD", hudEnabled);
        }

        while (antiLagKey.wasPressed()) {
            antiLagEnabled = !antiLagEnabled;
            applyAntiLag(client);
            notify(client, "Anti-Lag profile", antiLagEnabled);
        }

        while (toggleSprintKey.wasPressed()) {
            toggleSprintEnabled = !toggleSprintEnabled;
            notify(client, "Toggle Sprint", toggleSprintEnabled);
        }

        while (toggleSneakKey.wasPressed()) {
            toggleSneakEnabled = !toggleSneakEnabled;
            notify(client, "Toggle Sneak", toggleSneakEnabled);
        }

        if (client.player != null) {
            if (freecamEnabled) {
                client.player.noClip = true;
            }
            boolean gameplayActive = client.currentScreen == null;
            client.options.sprintKey.setPressed(toggleSprintEnabled && gameplayActive);
            client.options.sneakKey.setPressed(toggleSneakEnabled && gameplayActive);
            client.options.attackKey.setPressed(autoMineEnabled && gameplayActive);
        } else if (client.options != null) {
            client.options.sprintKey.setPressed(false);
            client.options.sneakKey.setPressed(false);
            client.options.attackKey.setPressed(false);
        }
    }

    private static void applyAntiLag(MinecraftClient client) {
        if (client.options != null) {
            client.options.getParticles().setValue(
                    antiLagEnabled ? ParticlesMode.MINIMAL : ParticlesMode.ALL
            );
        }
    }

    private static void notify(MinecraftClient client, String feature, boolean enabled) {
        if (client.player != null) {
            client.player.sendMessage(
                    Text.literal("§b[Null Client] §f" + feature + ": "
                            + (enabled ? "§aON" : "§cOFF")),
                    true
            );
        }
    }
}
