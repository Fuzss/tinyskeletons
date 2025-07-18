package fuzs.tinyskeletons.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.world.item.ItemStack;

public class BabyWitherSkeletonRenderState extends SkeletonRenderState {
    public boolean renderCarryingSkull;
    public boolean isDancing;
    public ItemStack skullItem = ItemStack.EMPTY;
}
