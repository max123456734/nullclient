package dev.nullclient.mixin;

import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Restore Meteor's settings and keybinds, but never restore saved active flags.
 * Module.fromTag is called after Meteor disables modules during config loading.
 */
@Mixin(targets = "meteordevelopment.meteorclient.systems.modules.Module")
public abstract class MeteorModuleMixin {
    @Redirect(
            method = "fromTag",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/NbtCompound;getBooleanOr(Ljava/lang/String;Z)Z"
            )
    )
    private boolean nullclient$ignoreSavedActiveState(NbtCompound tag, String key, boolean fallback) {
        if ("active".equals(key)) return false;
        if ("chatFeedback".equals(key) && !tag.contains(key)) return true;
        return tag.getBoolean(key).orElse(fallback);
    }
}
