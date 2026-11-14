package dev.nullclient.mixin;

import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Keep Meteor's saved module settings and keybinds, but ignore saved "active" flags.
 * This prevents modules from silently re-enabling themselves on the next launch.
 */
@Mixin(targets = "meteordevelopment.meteorclient.systems.modules.Module")
public abstract class MeteorModuleMixin {
    @Redirect(
            method = "fromTag",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/CompoundTag;getBooleanOr(Ljava/lang/String;Z)Z"
            )
    )
    private boolean nullclient$ignoreSavedActiveState(CompoundTag tag, String key, boolean fallback) {
        if ("active".equals(key)) return false;
        return tag.getBooleanOr(key, fallback);
    }
}
