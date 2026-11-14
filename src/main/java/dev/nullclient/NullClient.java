package dev.nullclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
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

    private static KeyBinding hudKey;
    private static KeyBinding antiLagKey;
    private static KeyBinding toggleSprintKey;
    private static KeyBinding toggleSneakKey;

    private static boolean antiLagEnabled;
    private static boolean toggleSprintEnabled;
    private static boolean toggleSneakEnabled;

    @Override
    public void onInitializeClient() {
        hudKey = register("key.nullclient.hud", GLFW.GLFW_KEY_F8);
        antiLagKey = register("key.nullclient.anti_lag", GLFW.GLFW_KEY_F9);
        toggleSprintKey = register("key.nullclient.toggle_sprint", GLFW.GLFW_KEY_F10);
        toggleSneakKey = register("key.nullclient.toggle_sneak", GLFW.GLFW_KEY_F11);

        ClientTickEvents.END_CLIENT_TICK.register(NullClient::onClientTick);
    }

    private static KeyBinding register(String translationKey, int keyCode) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                translationKey,
                InputUtil.Type.KEYSYM,
                keyCode,
                NULL_CLIENT_CATEGORY
        ));
    }

    private static void onClientTick(MinecraftClient client) {
        while (hudKey.wasPressed()) {
            if (client.currentScreen instanceof NullClientScreen) {
                client.setScreen(null);
            } else if (client.currentScreen == null) {
                client.setScreen(new NullClientScreen());
            }
        }

        while (antiLagKey.wasPressed()) {
            toggleAntiLag(client);
        }

        while (toggleSprintKey.wasPressed()) {
            toggleSprint();
            notify(client, "Toggle Sprint", toggleSprintEnabled);
        }

        while (toggleSneakKey.wasPressed()) {
            toggleSneak();
            notify(client, "Toggle Sneak", toggleSneakEnabled);
        }

        if (client.player != null && client.options != null) {
            boolean gameplayActive = client.currentScreen == null;
            client.options.sprintKey.setPressed(toggleSprintEnabled && gameplayActive);
            client.options.sneakKey.setPressed(toggleSneakEnabled && gameplayActive);
        } else if (client.options != null) {
            client.options.sprintKey.setPressed(false);
            client.options.sneakKey.setPressed(false);
        }
    }

    public static void toggleAntiLag(MinecraftClient client) {
        antiLagEnabled = !antiLagEnabled;
        applyAntiLag(client);
        notify(client, "Anti-Lag profile", antiLagEnabled);
    }

    public static void toggleSprint() {
        toggleSprintEnabled = !toggleSprintEnabled;
    }

    public static void toggleSneak() {
        toggleSneakEnabled = !toggleSneakEnabled;
    }

    public static boolean isAntiLagEnabled() {
        return antiLagEnabled;
    }

    public static boolean isToggleSprintEnabled() {
        return toggleSprintEnabled;
    }

    public static boolean isToggleSneakEnabled() {
        return toggleSneakEnabled;
    }

    public static String getHudKeyName() {
        return hudKey.getBoundKeyLocalizedText().getString();
    }

    public static String getAntiLagKeyName() {
        return antiLagKey.getBoundKeyLocalizedText().getString();
    }

    public static String getToggleSprintKeyName() {
        return toggleSprintKey.getBoundKeyLocalizedText().getString();
    }

    public static String getToggleSneakKeyName() {
        return toggleSneakKey.getBoundKeyLocalizedText().getString();
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
