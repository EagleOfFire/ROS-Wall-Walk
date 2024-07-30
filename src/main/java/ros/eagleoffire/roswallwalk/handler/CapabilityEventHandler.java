package ros.eagleoffire.roswallwalk.handler;

import ros.eagleoffire.roswallwalk.ROSWallWalk;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ros.eagleoffire.roswallwalk.capabilities.IWallWalkingCapability;

@Mod.EventBusSubscriber(modid = ROSWallWalk.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityEventHandler {

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IWallWalkingCapability.class);
    }
}

