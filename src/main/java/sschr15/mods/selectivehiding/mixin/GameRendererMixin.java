package sschr15.mods.selectivehiding.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import sschr15.mods.selectivehiding.SelectiveHidingConfig;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@ModifyExpressionValue(method = "shouldRenderBlockOutline", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;hudHidden:Z"))
	private boolean selectiveHiding$keepBlockOutlineIfConfigured(boolean original) {
		return original && !SelectiveHidingConfig.getInstance().showBlockOutline;
	}
}
