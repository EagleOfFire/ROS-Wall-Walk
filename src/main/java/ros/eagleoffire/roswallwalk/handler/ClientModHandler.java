package ros.eagleoffire.roswallwalk.handler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import ros.eagleoffire.roswallwalk.ROSWallWalk;
import ros.eagleoffire.roswallwalk.client.KeyBindings;

@Mod.EventBusSubscriber(modid = ROSWallWalk.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){

    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.INSTANCE.WALL_WALK_KEY);
    }
}