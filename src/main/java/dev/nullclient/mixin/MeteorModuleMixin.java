package dev.nullclient.mixin;

import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Restore Meteor module settings and keybinds without restoring saved active states.
 * Meteor's module loader disables all modules before reading this configuration.
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
        return tag.getBooleanOr(key, fallback);
    }
}
