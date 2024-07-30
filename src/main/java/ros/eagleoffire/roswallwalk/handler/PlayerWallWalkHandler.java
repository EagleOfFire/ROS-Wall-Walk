package ros.eagleoffire.roswallwalk.handler;

import ros.eagleoffire.roswallwalk.ROSWallWalk;
import ros.eagleoffire.roswallwalk.events.WallWalkingProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ROSWallWalk.MODID)
public class PlayerWallWalkHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        player.getCapability(WallWalkingProvider.WALL_WALKING_CAPABILITY).ifPresent(capability -> {
            if (capability.isWallWalking()) {
                BlockPos pos = player.blockPosition();
                if (player.level().getBlockState(pos).getBlock() != Blocks.AIR) {
                    Vec3 motion = player.getDeltaMovement();
                    player.setDeltaMovement(new Vec3(motion.x, 0.0, motion.z));
                    player.fallDistance = 0.0F;
                    player.getAbilities().setFlyingSpeed(0.05F);
                }
            }
        });
    }
}

