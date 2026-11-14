package dev.nullclient.mixin;

import dev.nullclient.NullClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Keeps the render camera at Null Client's independent freecam position.
 * Camera.update still supplies the player's current look direction, while
 * this injection replaces the camera position after vanilla camera setup.
 */
@Mixin(Camera.class)
abstract class CameraMixin {
    @Shadow protected abstract void setPos(double x, double y, double z);

    @Inject(method = "update", at = @At("TAIL"))
    private void nullclient$detachCamera(World area, Entity focusedEntity,
                                         boolean thirdPerson, boolean inverseView,
                                         float tickProgress, CallbackInfo ci) {
        if (!NullClient.isFreecamEnabled()) return;

        Vec3d position = NullClient.getFreecamPosition();
        if (position != null) {
            setPos(position.x, position.y, position.z);
        }
    }
}

/**
 * Stops the real player from walking/falling while the render camera is free.
 * Targeting ClientPlayerEntity directly avoids affecting any other living entity.
 */
@Mixin(ClientPlayerEntity.class)
abstract class ClientPlayerEntityMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    private void nullclient$freezePlayerDuringFreecam(CallbackInfo ci) {
        if (NullClient.isFreecamEnabled()) {
            ci.cancel();
        }
    }
}
