package ros.eagleoffire.roswallwalk.events;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import ros.eagleoffire.roswallwalk.capabilities.WallWalkingCapability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WallWalkingProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<WallWalkingCapability> WALL_WALKING_CAPABILITY = CapabilityManager.get(new CapabilityToken<WallWalkingCapability>() {});
    private WallWalkingCapability wallWalking = null;
    private final LazyOptional<WallWalkingCapability> optional = LazyOptional.of(this::createWallWalking);

    private WallWalkingCapability createWallWalking() {
        if(this.wallWalking == null){
            this.wallWalking = new WallWalkingCapability();
        }
        return this.wallWalking;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == WALL_WALKING_CAPABILITY){
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createWallWalking().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createWallWalking().loadNBTData(nbt);
    }
}
