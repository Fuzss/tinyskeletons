package fuzs.tinyskeletons.world.entity.monster;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;

public interface SkullCarryingMob {
    default boolean renderCarryingSkull() {
        return !this.getSkullItem().isEmpty();
    }

    ItemStack getSkullItem();

    default boolean isOnlyCarryingSkull(LivingEntity entity, InteractionHand hand) {
        final InteractionHand otherHand = hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        return this.isSkullItem(entity.getItemInHand(hand)) && entity.getItemInHand(otherHand).isEmpty();
    }

    default boolean isSkullItem(ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            if (itemStack.getItem() instanceof BlockItem) {
                BlockItem blockItem = (BlockItem) itemStack.getItem();
                return blockItem.getBlock() instanceof AbstractSkullBlock;
            }
        }
        return false;
    }
}
