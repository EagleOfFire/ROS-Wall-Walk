package ros.eagleoffire.roswallwalk.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;
import ros.eagleoffire.roswallwalk.events.WallWalkingProvider;
import ros.eagleoffire.roswallwalk.networking.ModMessages;

import java.util.function.Supplier;

public class WallWalkingDataSyncC2SPacket {

    public WallWalkingDataSyncC2SPacket() {

    }

    public WallWalkingDataSyncC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();
                player.getCapability(WallWalkingProvider.WALL_WALKING_CAPABILITY).ifPresent(wallWalking -> {
                    wallWalking.setWallWalking(!wallWalking.isWallWalking());
                    ModMessages.sendToPlayer(new WallWalkingDataSyncS2CPacket(wallWalking.isWallWalking()), player);
                    String message = "Wall walking toggled: " + wallWalking.isWallWalking();
                    player.sendSystemMessage(Component.literal(message));
                });
        });
        return true;
    }

    private boolean hasWaterAroundThem(ServerPlayer player, ServerLevel level, int size) {
        return level.getBlockStates(player.getBoundingBox().inflate(size))
                .filter(state -> state.is(Blocks.WATER)).toArray().length > 0;
    }
}
