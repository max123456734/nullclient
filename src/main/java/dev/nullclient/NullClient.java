package dev.nullclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.particle.ParticlesMode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class NullClient implements ClientModInitializer {
    private static final KeyBinding.Category NULL_CLIENT_CATEGORY =
            KeyBinding.Category.create(Identifier.of("nullclient", "controls"));

    private static KeyBinding freecamKey;
    private static KeyBinding hudKey;
    private static KeyBinding antiLagKey;
    private static KeyBinding toggleSprintKey;
    private static KeyBinding toggleSneakKey;

    private static boolean freecamEnabled;
    private static boolean antiLagEnabled;
    private static boolean toggleSprintEnabled;
    private static boolean toggleSneakEnabled;
    private static Vec3d freecamPosition;

    @Override
    public void onInitializeClient() {
        freecamKey = register("key.nullclient.freecam", GLFW.GLFW_KEY_F6);
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
        while (freecamKey.wasPressed()) {
            toggleFreecam(client);
        }

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

        if (client.player != null) {
            if (freecamEnabled && client.currentScreen == null) {
                updateFreecam(client);
            }
            boolean gameplayActive = client.currentScreen == null;
            client.options.sprintKey.setPressed(toggleSprintEnabled && gameplayActive);
            client.options.sneakKey.setPressed(toggleSneakEnabled && gameplayActive);
        } else if (client.options != null) {
            freecamPosition = null;
            freecamEnabled = false;
            client.options.sprintKey.setPressed(false);
            client.options.sneakKey.setPressed(false);
        }
    }

    private static void updateFreecam(MinecraftClient client) {
        if (client.player == null) return;
        if (freecamPosition == null) freecamPosition = client.player.getEyePos();

        Vec3d look = client.player.getRotationVec(1.0F);
        Vec3d forward = new Vec3d(look.x, 0.0, look.z);
        if (forward.lengthSquared() > 1.0E-6) forward = forward.normalize();
        Vec3d right = new Vec3d(forward.z, 0.0, -forward.x);
        Vec3d movement = Vec3d.ZERO;

        if (client.options.forwardKey.isPressed()) movement = movement.add(forward);
        if (client.options.backKey.isPressed()) movement = movement.subtract(forward);
        if (client.options.rightKey.isPressed()) movement = movement.add(right);
        if (client.options.leftKey.isPressed()) movement = movement.subtract(right);
        if (client.options.jumpKey.isPressed()) movement = movement.add(0.0, 1.0, 0.0);
        if (client.options.sneakKey.isPressed() && !toggleSneakEnabled) movement = movement.add(0.0, -1.0, 0.0);

        if (movement.lengthSquared() > 1.0E-6) {
            double speed = client.options.sprintKey.isPressed() ? 1.2 : 0.45;
            freecamPosition = freecamPosition.add(movement.normalize().multiply(speed));
        }
    }

    // These same actions are called by the clickable Null Client screen and keybinds.
    public static void toggleFreecam(MinecraftClient client) {
        freecamEnabled = !freecamEnabled;
        if (freecamEnabled && client.player != null) {
            freecamPosition = client.player.getEyePos();
        } else {
            freecamPosition = null;
        }
        notify(client, "Freecam", freecamEnabled);
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

    public static boolean isFreecamEnabled() {
        return freecamEnabled;
    }

    public static Vec3d getFreecamPosition() {
        return freecamPosition;
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


    public static String getFreecamKeyName() {
        return freecamKey.getBoundKeyLocalizedText().getString();
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
