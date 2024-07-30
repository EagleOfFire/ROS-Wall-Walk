package ros.eagleoffire.roswallwalk.capabilities;

import net.minecraft.nbt.CompoundTag;

public class WallWalkingCapability {
    private boolean wallWalking = false;

    public boolean isWallWalking() {
        return wallWalking;
    }

    public void setWallWalking(boolean wallWalking) {
        this.wallWalking = wallWalking;
    }

    public void copyFrom(WallWalkingCapability source){
        this.wallWalking = source.wallWalking;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putBoolean("wall_walking", wallWalking);
    }

    public void loadNBTData(CompoundTag nbt){
        wallWalking = nbt.getBoolean("wall_walking");
    }
}
