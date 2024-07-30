package ros.eagleoffire.roswallwalk.events;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import ros.eagleoffire.roswallwalk.capabilities.IWallWalkingCapability;
import ros.eagleoffire.roswallwalk.capabilities.WallWalkingCapability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WallWalkingProvider implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<IWallWalkingCapability> WALL_WALKING_CAPABILITY = CapabilityManager.get(new CapabilityToken<IWallWalkingCapability>() {
    });
    private IWallWalkingCapability progression = null;
    private final IWallWalkingCapability instance = new WallWalkingCapability();

    @Nonnull
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == WALL_WALKING_CAPABILITY ? WALL_WALKING_CAPABILITY.orEmpty(cap, (LazyOptional<IWallWalkingCapability>) instance) : null;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("wall_walking", instance.isWallWalking());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.setWallWalking(nbt.getBoolean("wall_walking"));
    }
}
