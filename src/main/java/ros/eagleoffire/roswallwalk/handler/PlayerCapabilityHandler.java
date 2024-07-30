package ros.eagleoffire.roswallwalk.handler;

import ros.eagleoffire.roswallwalk.ROSWallWalk;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ros.eagleoffire.roswallwalk.events.WallWalkingProvider;

@Mod.EventBusSubscriber(modid = ROSWallWalk.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerCapabilityHandler {

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Player> event) {
        event.addCapability(new ResourceLocation(ROSWallWalk.MODID, "wall_walking"), new WallWalkingProvider());
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        event.getOriginal().getCapability(WallWalkingProvider.WALL_WALKING_CAPABILITY).ifPresent(oldCap -> {
            event.getEntity().getCapability(WallWalkingProvider.WALL_WALKING_CAPABILITY).ifPresent(newCap -> {
                newCap.setWallWalking(oldCap.isWallWalking());
            });
        });
    }
}
