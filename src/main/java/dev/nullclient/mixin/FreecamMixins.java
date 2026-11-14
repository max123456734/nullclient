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

@Mixin(Camera.class)
abstract class CameraMixin {
    @Shadow protected abstract void setPos(double x, double y, double z);

    @Inject(method = "update", at = @At("TAIL"))
    private void nullclient$moveDetachedCamera(World area, Entity focusedEntity,
                                                boolean thirdPerson, boolean inverseView,
                                                float tickProgress, CallbackInfo ci) {
        if (!NullClient.isFreecamEnabled()) return;
        Vec3d position = NullClient.getFreecamPosition();
        if (position != null) {
            setPos(position.x, position.y, position.z);
        }
    }
}

@Mixin(ClientPlayerEntity.class)
abstract class ClientPlayerEntityMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    private void nullclient$freezePlayerWhileFreecam(CallbackInfo ci) {
        if (NullClient.isFreecamEnabled()) {
            ci.cancel();
        }
    }
}
