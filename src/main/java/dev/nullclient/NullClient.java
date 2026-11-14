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
    private static KeyBinding freecamKey;
    private static KeyBinding autoMineKey;
    private static KeyBinding hudKey;
    private static KeyBinding antiLagKey;

    private static boolean freecamEnabled;
    private static boolean autoMineEnabled;
    private static boolean hudEnabled = true;
    private static boolean antiLagEnabled;

    @Override
    public void onInitializeClient() {
        freecamKey = register("key.nullclient.freecam", GLFW.GLFW_KEY_F6);
        autoMineKey = register("key.nullclient.auto_mine", GLFW.GLFW_KEY_F7);
        hudKey = register("key.nullclient.hud", GLFW.GLFW_KEY_F8);
        antiLagKey = register("key.nullclient.anti_lag", GLFW.GLFW_KEY_F9);

        ClientTickEvents.END_CLIENT_TICK.register(NullClient::onClientTick);
        HudElementRegistry.addLast(Identifier.of("nullclient", "hud"), (drawContext, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (!hudEnabled || client.player == null || client.textRenderer == null) return;

            int y = 8;
            drawContext.drawTextWithShadow(client.textRenderer,
                    Text.literal("§bNull Client §7v1.0.0"), 8, y, 0xFFFFFF);
            y += 12;
            drawContext.drawTextWithShadow(client.textRenderer,
                    status("Freecam", freecamEnabled), 8, y, 0xFFFFFF);
            y += 12;
            drawContext.drawTextWithShadow(client.textRenderer,
                    status("Auto Mine", autoMineEnabled), 8, y, 0xFFFFFF);
            y += 12;
            drawContext.drawTextWithShadow(client.textRenderer,
                    status("Anti-Lag", antiLagEnabled), 8, y, 0xFFFFFF);
        });
    }

    private static KeyBinding register(String translationKey, int keyCode) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                translationKey,
                InputUtil.Type.KEYSYM,
                keyCode,
                KeyBinding.Category.create(Identifier.of("nullclient", "main"))
        ));
    }

    private static String status(String name, boolean enabled) {
        return "§7" + name + ": " + (enabled ? "§aON" : "§cOFF");
    }

    private static void onClientTick(MinecraftClient client) {
        while (freecamKey.wasPressed()) {
            freecamEnabled = !freecamEnabled;
            // This is a local no-clip movement mode, not a detached camera.
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

        if (client.player != null) {
            if (freecamEnabled) {
                client.player.noClip = true;
            }
            if (autoMineEnabled && client.currentScreen == null) {
                client.options.attackKey.setPressed(true);
            } else {
                client.options.attackKey.setPressed(false);
            }
        }
    }

    private static void applyAntiLag(MinecraftClient client) {
        // Conservative client-side profile: reduce particles to the minimum.
        if (client.options != null) {
            client.options.getParticles().setValue(
                    antiLagEnabled
                            ? ParticlesMode.MINIMAL
                            : ParticlesMode.ALL
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
