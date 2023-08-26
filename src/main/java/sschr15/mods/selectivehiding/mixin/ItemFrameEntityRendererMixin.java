package sschr15.mods.selectivehiding.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import sschr15.mods.selectivehiding.SelectiveHidingConfig;

@Mixin(ItemFrameEntityRenderer.class)
public class ItemFrameEntityRendererMixin {
    @Redirect(method = "hasLabel(Lnet/minecraft/entity/decoration/ItemFrameEntity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;isHudEnabled()Z"))
    private boolean selectiveHiding$keepLabelsIfConfigured() {
        return MinecraftClient.isHudEnabled() || SelectiveHidingConfig.getInstance().showLabels;
    }
}
