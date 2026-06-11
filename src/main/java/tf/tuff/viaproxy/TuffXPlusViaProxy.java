package tf.tuff.viaproxy;

import net.raphimc.viaproxy.plugins.ViaProxyPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class TuffXPlusViaProxy extends ViaProxyPlugin {

    private static final Logger LOGGER = Logger.getLogger("TuffXPlusViaProxy");

    @Override
    public void onEnable() {
        ensureDefaultConfig();
        LOGGER.info("Enabled " + getName() + " v" + getVersion());
    }

    @Override
    public void onDisable() {
        LOGGER.info("Disabled " + getName());
    }

    private void ensureDefaultConfig() {
        final Path dataPath = getDataFolder().toPath();
        final Path configPath = dataPath.resolve("config.yml");

        try {
            Files.createDirectories(dataPath);
            if (Files.exists(configPath)) return;

            try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.yml")) {
                if (input == null) return;
                Files.copy(input, configPath);
            }
        } catch (IOException e) {
            LOGGER.warning("Failed to copy default config.yml to " + configPath + ": " + e.getMessage());
        }
    }
}
