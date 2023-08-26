package sschr15.mods.selectivehiding;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class SelectiveHidingConfigScreen extends Screen {
    private final Screen parent;
    private boolean isRemoving = false;

    public SelectiveHidingConfigScreen(Screen parent) {
        super(Text.translatableWithFallback("sh.title", "Selective Hiding"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int w = 140;
        int h = 20;
        int x1 = width / 2 - 150;
        int x2 = width / 2 + 10;
        int dy = 25;

        int y = 50;

        addDrawableChild(new Checkbox(x1, y, "toasts", "Toasts"));
        addDrawableChild(new Checkbox(x2, y, "hand", "Hand"));
        y += dy;
        addDrawableChild(new Checkbox(x1, y, "blockOutline", "Block Outline"));
        addDrawableChild(new Checkbox(x2, y, "chat", "Chat"));
        y += dy;
        addDrawableChild(new Checkbox(x1, y, "hotbar", "Hotbar"));
        addDrawableChild(new Checkbox(x2, y, "crosshair", "Crosshair"));
        y += dy;
        addDrawableChild(new Checkbox(x1, y, "bossbar", "Bossbar"));
        addDrawableChild(new Checkbox(x2, y, "statusBars", "Status Bars"));
        y += dy;
        addDrawableChild(new Checkbox(x1, y, "mountHealth", "Mounted Entity Health"));
        addDrawableChild(new Checkbox(x2, y, "experienceBar", "Experience Bar"));
        y += dy;
        addDrawableChild(new Checkbox(x1, y, "heldItem", "Held Item"));
        addDrawableChild(new Checkbox(x2, y, "overlayMessage", "Action Bar"));
        y += dy;
        addDrawableChild(new Checkbox(x1, y, "title", "Title"));
        addDrawableChild(new Checkbox(x2, y, "subtitles", "Subtitles"));
        y += dy;
        addDrawableChild(new Checkbox(x1, y, "scoreboardSidebar", "Scoreboard"));
        addDrawableChild(new Checkbox(x2, y, "playerList", "Player List"));
        y += dy;
        addDrawableChild(new Checkbox(x1, y, "autosaveIcon", "Autosave Icon"));
        addDrawableChild(new Checkbox(x2, y, "labels", "Name Labels"));

        y += dy;
        addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), button -> removed()).positionAndSize(x1, y, w + w + 20, h).build());

        if (y > height - 50) {
            for (var child : children()) {
                if (child instanceof Widget widget) {
                    widget.setY(widget.getY() - 45);
                }
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
    }

    @Override
    public void removed() {
        // not sure what the "proper" way is to return to the parent
        if (isRemoving) return;
        isRemoving = true;
        SelectiveHidingConfig.getInstance().save();
        MinecraftClient.getInstance().setScreen(parent);
    }

    private static Text getCfg(String key, String fallback) {
        return Text.translatableWithFallback("sh.config." + key, fallback);
    }

    private static class Checkbox extends CheckboxWidget {
        private final MethodHandle setter;

        private static boolean isChecked(String key) {
            try {
                String fieldName = "show" + key.substring(0, 1).toUpperCase() + key.substring(1);
                return (boolean) MethodHandles.lookup()
                        .findGetter(SelectiveHidingConfig.class, fieldName, boolean.class)
                        .invokeExact(SelectiveHidingConfig.getInstance());
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }

        public Checkbox(int x, int y, String key, String fallback) {
            super(x, y, 190, 20, getCfg(key, fallback), isChecked(key));
            try {
                String fieldName = "show" + key.substring(0, 1).toUpperCase() + key.substring(1);
                setter = MethodHandles.lookup()
                        .findSetter(SelectiveHidingConfig.class, fieldName, boolean.class)
                        .bindTo(SelectiveHidingConfig.getInstance());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void onPress() {
            super.onPress();
            try {
                setter.invokeExact(isChecked());
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }
    }
}
