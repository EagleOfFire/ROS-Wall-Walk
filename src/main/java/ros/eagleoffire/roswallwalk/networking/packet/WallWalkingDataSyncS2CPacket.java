package ros.eagleoffire.roswallwalk.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import ros.eagleoffire.roswallwalk.client.ClientWallWalkingData;

import java.util.function.Supplier;

public class WallWalkingDataSyncS2CPacket {
    private final boolean wallWalking;

    public WallWalkingDataSyncS2CPacket(boolean wallWalking) {
        this.wallWalking = wallWalking;
    }

    public WallWalkingDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.wallWalking = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(wallWalking);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE CLIENT!
            ClientWallWalkingData.set(wallWalking);
        });
        return true;
    }
}
