package sschr15.mods.selectivehiding;

import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class SelectiveHidingConfig {
    private static final Path CONFIG_PATH = QuiltLoader.getConfigDir().resolve("selectivehiding.cfg");
    private static SelectiveHidingConfig INSTANCE;

    // ToastManager
    public boolean showToasts = false;

    // GameRenderer
    public boolean showHand = false;
    public boolean showBlockOutline = false;

    // ChatHud
    public boolean showChat = false;
//    public boolean showChatHistoryWhenOpened = false;

    // InGameHud
    public boolean showHotbar = false;
    public boolean showCrosshair = false;
    public boolean showBossbar = false;
    public boolean showStatusBars = false;
    public boolean showMountHealth = false;
    public boolean showExperienceBar = false;
    public boolean showHeldItem = false;
    public boolean showOverlayMessage = false;
    public boolean showTitle = false;
    public boolean showSubtitles = false;
    public boolean showScoreboardSidebar = false;
    public boolean showPlayerList = false;
    public boolean showAutosaveIcon = false; // wait, this is actually in java edition?

    // LivingEntityRenderer and ItemFrameEntityRenderer
    public boolean showLabels = false;

    private SelectiveHidingConfig() {
        if (Files.isRegularFile(CONFIG_PATH)) {
            Properties properties = new Properties();
            try {
                properties.load(Files.newInputStream(CONFIG_PATH));
            } catch (Exception e) {
                throw new RuntimeException("Failed to load config", e);
            }

            showToasts = Boolean.parseBoolean(properties.getProperty("showToasts", "false"));
            showHand = Boolean.parseBoolean(properties.getProperty("showHand", "false"));
            showBlockOutline = Boolean.parseBoolean(properties.getProperty("showBlockOutline", "false"));
            showChat = Boolean.parseBoolean(properties.getProperty("showChat", "false"));
//            showChatHistoryWhenOpened = Boolean.parseBoolean(properties.getProperty("showChatHistoryWhenOpened", "false"));
            showHotbar = Boolean.parseBoolean(properties.getProperty("showHotbar", "false"));
            showCrosshair = Boolean.parseBoolean(properties.getProperty("showCrosshair", "false"));
            showBossbar = Boolean.parseBoolean(properties.getProperty("showBossbar", "false"));
            showStatusBars = Boolean.parseBoolean(properties.getProperty("showStatusBars", "false"));
            showMountHealth = Boolean.parseBoolean(properties.getProperty("showMountHealth", "false"));
            showExperienceBar = Boolean.parseBoolean(properties.getProperty("showExperienceBar", "false"));
            showHeldItem = Boolean.parseBoolean(properties.getProperty("showHeldItem", "false"));
            showOverlayMessage = Boolean.parseBoolean(properties.getProperty("showOverlayMessage", "false"));
            showTitle = Boolean.parseBoolean(properties.getProperty("showTitle", "false"));
            showSubtitles = Boolean.parseBoolean(properties.getProperty("showSubtitles", "false"));
            showScoreboardSidebar = Boolean.parseBoolean(properties.getProperty("showScoreboardSidebar", "false"));
            showAutosaveIcon = Boolean.parseBoolean(properties.getProperty("showAutosaveIcon", "false"));
            showLabels = Boolean.parseBoolean(properties.getProperty("showLabels", "false"));
        }
    }

    public static SelectiveHidingConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SelectiveHidingConfig();
        }
        return INSTANCE;
    }

    public void save() {
        Properties properties = new Properties();
        properties.setProperty("showToasts", Boolean.toString(showToasts));
        properties.setProperty("showHand", Boolean.toString(showHand));
        properties.setProperty("showBlockOutline", Boolean.toString(showBlockOutline));
        properties.setProperty("showChat", Boolean.toString(showChat));
//        properties.setProperty("showChatHistoryWhenOpened", Boolean.toString(showChatHistoryWhenOpened));
        properties.setProperty("showHotbar", Boolean.toString(showHotbar));
        properties.setProperty("showCrosshair", Boolean.toString(showCrosshair));
        properties.setProperty("showBossbar", Boolean.toString(showBossbar));
        properties.setProperty("showStatusBars", Boolean.toString(showStatusBars));
        properties.setProperty("showMountHealth", Boolean.toString(showMountHealth));
        properties.setProperty("showExperienceBar", Boolean.toString(showExperienceBar));
        properties.setProperty("showHeldItem", Boolean.toString(showHeldItem));
        properties.setProperty("showOverlayMessage", Boolean.toString(showOverlayMessage));
        properties.setProperty("showTitle", Boolean.toString(showTitle));
        properties.setProperty("showSubtitles", Boolean.toString(showSubtitles));
        properties.setProperty("showScoreboardSidebar", Boolean.toString(showScoreboardSidebar));
        properties.setProperty("showAutosaveIcon", Boolean.toString(showAutosaveIcon));
        properties.setProperty("showLabels", Boolean.toString(showLabels));

        try {
            properties.store(Files.newOutputStream(CONFIG_PATH), "Selective Hiding Config");
        } catch (Exception e) {
            throw new RuntimeException("Failed to save config", e);
        }
    }
}
