package max123456734.nullfps;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Applies a conservative client-side performance preset.
 * This intentionally avoids mixins and network packet manipulation.
 */
public final class NullFpsBoost implements ClientModInitializer {
    public static final String MOD_ID = "null_fps_boost";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            LOGGER.warn("Minecraft client was not ready; performance preset was not applied.");
            return;
        }

        // Reduce the most expensive vanilla client-side distance settings.
        // These are normal video options and can be changed again in Video Settings.
        client.options.renderDistance().setValue(Math.min(client.options.renderDistance().getValue(), 8));
        client.options.simulationDistance().setValue(Math.min(client.options.simulationDistance().getValue(), 5));
        client.options.entityDistanceScaling().setValue(Math.min(client.options.entityDistanceScaling().getValue(), 0.75));

        LOGGER.info("Null FPS Boost 26.2 loaded. Applied conservative render, simulation and entity-distance settings.");
        LOGGER.info("Ping cannot be reduced to zero by a client mod; network latency depends on your route and server.");
    }
}
