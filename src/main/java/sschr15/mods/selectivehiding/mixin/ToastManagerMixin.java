package sschr15.mods.selectivehiding.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import sschr15.mods.selectivehiding.SelectiveHidingConfig;

@Mixin(ToastManager.class)
public class ToastManagerMixin {
    @ModifyExpressionValue(method = "draw", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;hudHidden:Z"))
    private boolean selectiveHiding$keepToastsIfConfigured(boolean original) {
        return original && !SelectiveHidingConfig.getInstance().showToasts;
    }
}
