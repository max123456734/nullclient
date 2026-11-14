package max123456734.nullfps;

import net.fabricmc.api.ClientModInitializer;

/** Client-only entrypoint using the Minecraft 26.2 split source-set layout. */
public final class NullFpsBoostClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NullFpsBoost.LOGGER.info("Null FPS Boost client module initialized for Minecraft 26.2.");
        NullFpsBoost.LOGGER.info("For higher FPS, lower render distance, simulation distance, particles and entity distance in Video Settings.");
    }
}
