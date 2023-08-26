package sschr15.mods.selectivehiding.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import sschr15.mods.selectivehiding.SelectiveHidingConfig;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @Redirect(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;isHudEnabled()Z"))
    private boolean selectiveHiding$keepLabelsIfConfigured() {
        return MinecraftClient.isHudEnabled() || SelectiveHidingConfig.getInstance().showLabels;
    }
}
