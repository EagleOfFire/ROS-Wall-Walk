package ros.eagleoffire.roswallwalk.handler;

import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.crafting.conditions.TrueCondition;
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
        if (player.level().isClientSide) return; // Skip client-side processing if not needed

        player.getCapability(WallWalkingProvider.WALL_WALKING_CAPABILITY).ifPresent(capability -> {
            if (capability.isWallWalking()) {
                BlockPos pos = player.blockPosition();
                BlockState stateBelow = player.level().getBlockState(pos.below());
                BlockState stateAbove = player.level().getBlockState(pos.above());
                BlockState stateNorth = player.level().getBlockState(pos.north());
                BlockState stateSouth = player.level().getBlockState(pos.south());
                BlockState stateWest = player.level().getBlockState(pos.west());
                BlockState stateEast = player.level().getBlockState(pos.east());

                // Check if player is near a wall or ceiling and adjust accordingly
                if (isValidWallSurface(stateBelow, player) || isValidWallSurface(stateAbove, player) ||
                        isValidWallSurface(stateNorth, player) || isValidWallSurface(stateSouth, player) ||
                        isValidWallSurface(stateWest, player) || isValidWallSurface(stateEast, player)) {

                    // For wall walking, adjust movement and gravity
                    Vec3 motion = player.getDeltaMovement();
                    if (isTouchingWall(player)) {
                        Vec3 wallNormal = getWallNormal(player);

                        if (wallNormal != null) {
                            Vec3 movementInput = new Vec3(player.xxa, 0, player.zza).normalize();
                            Vec3 wallMovement = movementInput.subtract(wallNormal.scale(movementInput.dot(wallNormal)));

                            player.setDeltaMovement(wallMovement.scale(0.1)); // Adjust the scaling factor as needed
                            player.fallDistance = 0.0F;
                            player.setNoGravity(true);
                        }
                    } else if (isTouchingCeiling(player)) {
                        player.setDeltaMovement(new Vec3(motion.x, motion.y, motion.z));
                        player.fallDistance = 0.0F;
                        player.setNoGravity(true);
                        // Additional logic to orient player to the ceiling
                    } else {
                        player.setNoGravity(false);
                    }
                } else {
                    player.setNoGravity(false); // Re-enable gravity if not wall walking
                }
            } else {
                player.setNoGravity(false); // Ensure gravity is enabled if wall walking is off
            }
        });
    }

    private static boolean isValidWallSurface(BlockState state, Player player) {
        Block block = state.getBlock();
        return !state.isAir() && state.isSolidRender(player.level(), player.blockPosition());
    }


    private static boolean isTouchingWall(Player player) {
        // Get the direction the player is facing
        Vec3 lookVec = player.getLookAngle();

        // Define the distance to check for a wall
        double checkDistance = 0.5; // Adjust as necessary

        // Perform a ray trace from the player's eyes to check for walls
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 checkPosition = eyePosition.add(lookVec.scale(checkDistance));

        // Get the block the player is looking at
        BlockHitResult hitResult = player.level().clip(new ClipContext(
                eyePosition,
                checkPosition,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        // Determine if the block is not air (indicating a wall)
        return hitResult.getType() == HitResult.Type.BLOCK &&
                player.level().getBlockState(hitResult.getBlockPos()).getBlock() != Blocks.AIR;
    }


    private static boolean isTouchingCeiling(Player player) {
        // Get the player's current position
        Vec3 playerPos = player.position();

        // Define the distance to check for a ceiling above the player
        double checkDistance = 0.5; // Adjust as necessary

        // Determine the position to check directly above the player
        Vec3 checkPosition = playerPos.add(0, checkDistance, 0);

        // Perform a ray trace upward from the player's current position
        BlockHitResult hitResult = player.level().clip(new ClipContext(
                playerPos,
                checkPosition,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        // Check if the hit result indicates a block above the player (not air)
        return hitResult.getType() == HitResult.Type.BLOCK &&
                player.level().getBlockState(hitResult.getBlockPos()).getBlock() != Blocks.AIR;
    }

    private static Vec3 getWallNormal(Player player) {
        // Determine the normal vector based on the direction the player is facing
        switch (player.getDirection()) {
            case NORTH:
                return new Vec3(0, 0, -1);
            case SOUTH:
                return new Vec3(0, 0, 1);
            case WEST:
                return new Vec3(-1, 0, 0);
            case EAST:
                return new Vec3(1, 0, 0);
            default:
                return null; // Should not occur, but included for completeness
        }
    }
}


