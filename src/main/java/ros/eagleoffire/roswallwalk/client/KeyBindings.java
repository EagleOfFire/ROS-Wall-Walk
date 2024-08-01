package ros.eagleoffire.roswallwalk.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraftforge.client.settings.KeyConflictContext;
import ros.eagleoffire.roswallwalk.ROSWallWalk;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ROSWallWalk.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class KeyBindings {
    public static final KeyBindings INSTANCE = new KeyBindings();

    private KeyBindings(){}

    private static final String CATEGORY = "key.categories." + ROSWallWalk.MODID;

    public final KeyMapping WALL_WALK_KEY = new KeyMapping(
            "key." + ROSWallWalk.MODID + ".wall_walk",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_G,-1),
            CATEGORY
    );
}
