package fuzs.tinyskeletons.entity;

import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public interface ISkullCarryingMob {
    default boolean renderCarryingSkull() {
        return !this.getSkullItem().isEmpty();
    }

    ItemStack getSkullItem();

    default boolean isOnlyCarryingSkull(LivingEntity entity, Hand hand) {
        final Hand otherHand = hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
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
