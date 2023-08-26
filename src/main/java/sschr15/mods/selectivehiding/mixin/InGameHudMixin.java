package sschr15.mods.selectivehiding.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.*;
import net.minecraft.entity.JumpingMount;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import sschr15.mods.selectivehiding.SelectiveHidingConfig;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Shadow
	protected abstract void renderCrosshair(GuiGraphics graphics);

	@Shadow
	protected abstract void renderStatusBars(GuiGraphics graphics);

	@Shadow
	protected abstract void renderMountHealth(GuiGraphics graphics);

	@Shadow
	public abstract void renderMountJumpBar(JumpingMount mount, GuiGraphics graphics, int x);

	@Shadow
	public abstract void renderExperienceBar(GuiGraphics graphics, int x);

	@Shadow
	public abstract void renderHeldItemTooltip(GuiGraphics graphics);

	@Shadow
	protected abstract void renderScoreboardSidebar(GuiGraphics graphics, ScoreboardObjective objective);

	@Shadow
	protected abstract void renderAutosaveIndicator(GuiGraphics graphics);

	@ModifyExpressionValue(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;hudHidden:Z", ordinal = 0))
	private boolean selectiveHiding$keepHotbarIfConfigured(boolean original) {
		return original && !SelectiveHidingConfig.getInstance().showHotbar;
	}

	@ModifyExpressionValue(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;hudHidden:Z", ordinal = 1))
	private boolean selectiveHiding$keepConfiguredOptions(boolean original, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		hudHidden.set(original);
		return original && !(
			SelectiveHidingConfig.getInstance().showCrosshair ||
			SelectiveHidingConfig.getInstance().showBossbar ||
			SelectiveHidingConfig.getInstance().showStatusBars ||
			SelectiveHidingConfig.getInstance().showMountHealth ||
			SelectiveHidingConfig.getInstance().showExperienceBar ||
			SelectiveHidingConfig.getInstance().showHeldItem
		);
	}

	// renderCrosshair
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderCrosshair(Lnet/minecraft/client/gui/GuiGraphics;)V"))
	private void selectiveHiding$renderCrosshairIfConfigured(InGameHud hud, GuiGraphics graphics, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (!hudHidden.get() || SelectiveHidingConfig.getInstance().showCrosshair) {
			renderCrosshair(graphics);
		}
	}

	// bossBarHud.render
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/BossBarHud;render(Lnet/minecraft/client/gui/GuiGraphics;)V"))
	private void selectiveHiding$renderBossBarIfConfigured(BossBarHud instance, GuiGraphics graphics, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (!hudHidden.get() || SelectiveHidingConfig.getInstance().showBossbar) {
			instance.render(graphics);
		}
	}

	// renderStatusBars
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderStatusBars(Lnet/minecraft/client/gui/GuiGraphics;)V"))
	private void selectiveHiding$renderStatusBarsIfConfigured(InGameHud hud, GuiGraphics graphics, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (!hudHidden.get() || SelectiveHidingConfig.getInstance().showStatusBars) {
			renderStatusBars(graphics);
		}
	}

	// renderMountHealth
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderMountHealth(Lnet/minecraft/client/gui/GuiGraphics;)V"))
	private void selectiveHiding$renderMountHealthIfConfigured(InGameHud hud, GuiGraphics graphics, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (!hudHidden.get() || SelectiveHidingConfig.getInstance().showMountHealth) {
			renderMountHealth(graphics);
		}
	}

	// renderMountJumpBar
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderMountJumpBar(Lnet/minecraft/entity/JumpingMount;Lnet/minecraft/client/gui/GuiGraphics;I)V"))
	private void selectiveHiding$renderMountJumpBarIfConfigured(InGameHud hud, JumpingMount mount, GuiGraphics graphics, int x, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (!hudHidden.get() || SelectiveHidingConfig.getInstance().showExperienceBar) {
			renderMountJumpBar(mount, graphics, x);
		}
	}

	// renderExperienceBar
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderExperienceBar(Lnet/minecraft/client/gui/GuiGraphics;I)V"))
	private void selectiveHiding$renderExperienceBarIfConfigured(InGameHud hud, GuiGraphics graphics, int x, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (!hudHidden.get() || SelectiveHidingConfig.getInstance().showExperienceBar) {
			renderExperienceBar(graphics, x);
		}
	}

	// renderHeldItemTooltip
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHeldItemTooltip(Lnet/minecraft/client/gui/GuiGraphics;)V"))
	private void selectiveHiding$renderHeldItemTooltipIfConfigured(InGameHud hud, GuiGraphics graphics, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (!hudHidden.get() || SelectiveHidingConfig.getInstance().showHeldItem) {
			renderHeldItemTooltip(graphics);
		}
	}

	// spectatorHud.renderTooltip
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SpectatorHud;renderTooltip(Lnet/minecraft/client/gui/GuiGraphics;)V"))
	private void selectiveHiding$renderSpectatorTooltipIfConfigured(SpectatorHud spectatorHud, GuiGraphics graphics, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (!hudHidden.get() || SelectiveHidingConfig.getInstance().showHeldItem) {
			spectatorHud.renderTooltip(graphics);
		}
	}

	@ModifyExpressionValue(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;hudHidden:Z", ordinal = 2))
	private boolean selectiveHiding$keepConfiguredMessages(boolean original, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		hudHidden.set(original);
		return original && !(
			SelectiveHidingConfig.getInstance().showOverlayMessage ||
			SelectiveHidingConfig.getInstance().showTitle ||
			SelectiveHidingConfig.getInstance().showSubtitles ||
			SelectiveHidingConfig.getInstance().showScoreboardSidebar ||
			SelectiveHidingConfig.getInstance().showPlayerList ||
			SelectiveHidingConfig.getInstance().showAutosaveIcon
		);
	}

	// overlay message handling (slightly hacky)
	@ModifyExpressionValue(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/hud/InGameHud;overlayRemaining:I"))
	private int selectiveHiding$keepOverlayMessageIfConfigured(int original, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (hudHidden.get() && !SelectiveHidingConfig.getInstance().showOverlayMessage) {
			return 0;
		}
		return original;
	}

	// title handling (similarly hacky)
	@ModifyExpressionValue(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/hud/InGameHud;title:Lnet/minecraft/text/Text;"))
	private Text selectiveHiding$keepTitleIfConfigured(Text original, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (hudHidden.get() && !SelectiveHidingConfig.getInstance().showTitle) {
			return null;
		}
		return original;
	}

	// subtitlesHud.render
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/SubtitlesHud;render(Lnet/minecraft/client/gui/GuiGraphics;)V"))
	private void selectiveHiding$renderSubtitlesIfConfigured(SubtitlesHud instance, GuiGraphics graphics, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (!hudHidden.get() || SelectiveHidingConfig.getInstance().showSubtitles) {
			instance.render(graphics);
		}
	}

	// renderScoreboardSidebar
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderScoreboardSidebar(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"))
	private void selectiveHiding$renderScoreboardSidebarIfConfigured(InGameHud hud, GuiGraphics graphics, ScoreboardObjective objective, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (!hudHidden.get() || SelectiveHidingConfig.getInstance().showScoreboardSidebar) {
			renderScoreboardSidebar(graphics, objective);
		}
	}

	// chatHud.render
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;render(Lnet/minecraft/client/gui/GuiGraphics;III)V"))
	private void selectiveHiding$renderChatIfConfigured(ChatHud chatHud, GuiGraphics graphics, int tickDelta, int x, int y, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (!hudHidden.get() || SelectiveHidingConfig.getInstance().showChat) {
			chatHud.render(graphics, tickDelta, x, y);
		}
	}

	// playerListHud.render
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/PlayerListHud;render(Lnet/minecraft/client/gui/GuiGraphics;ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"))
	private void selectiveHiding$renderPlayerListIfConfigured(PlayerListHud playerListHud, GuiGraphics graphics, int scaledWindowWidth, Scoreboard scoreboard, ScoreboardObjective objective, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (!hudHidden.get() || SelectiveHidingConfig.getInstance().showPlayerList) {
			playerListHud.render(graphics, scaledWindowWidth, scoreboard, objective);
		}
	}

	// renderAutosaveIndicator
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderAutosaveIndicator(Lnet/minecraft/client/gui/GuiGraphics;)V"))
	private void selectiveHiding$renderAutosaveIndicatorIfConfigured(InGameHud hud, GuiGraphics graphics, @Share("sh$hudHidden") LocalBooleanRef hudHidden) {
		if (!hudHidden.get() || SelectiveHidingConfig.getInstance().showAutosaveIcon) {
			renderAutosaveIndicator(graphics);
		}
	}

	@ModifyExpressionValue(method = "renderCrosshair", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;hudHidden:Z"))
	private boolean selectiveHiding$keepCrosshairIfConfigured(boolean original) {
		return original && !SelectiveHidingConfig.getInstance().showCrosshair;
	}
}
