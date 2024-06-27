package cpw.mods.forge.serverpacklocator;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.function.Predicate;

public class ModAccessor {
    private static final Logger LOG = LoggerFactory.getLogger(ModAccessor.class);

    private static String statusLine = "ServerPack: unknown";
    @Nullable
    private static volatile Predicate<UUID> allowListStrategy;
    private static volatile boolean logIps = true;

    public static void setStatusLine(final String statusLine) {
        ModAccessor.statusLine = statusLine;
    }

    public static String getStatusLine() {
        return statusLine;
    }

    public static boolean isWhiteListed(UUID uuid) {
        var strategy = allowListStrategy;
        if (strategy == null) {
            LOG.info("Rejecting whitelist check for {}, since the utility mod has not yet loaded.", uuid);
            return false;
        }
        return strategy.test(uuid);
    }

    public static void setAllowListStrategy(@Nullable Predicate<UUID> allowListStrategy) {
        ModAccessor.allowListStrategy = allowListStrategy;
    }

    public static boolean isLogIps() {
        return logIps;
    }

    public static void setLogIps(boolean logIps) {
        ModAccessor.logIps = logIps;
    }
}
