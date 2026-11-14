package max123456734.nullfps;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Common entrypoint; client-specific code uses the dedicated client source set. */
public final class NullFpsBoost implements ModInitializer {
    public static final String MOD_ID = "null_fps_boost";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Null FPS Boost 26.2 loaded.");
        LOGGER.info("A client mod cannot guarantee 0 ms ping; latency depends on network route and server.");
    }
}
