package fuzs.tinyskeletons.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.*;

public class BabySkeletonRenderState extends SkeletonRenderState {
    public ItemType offhandItemType = ItemType.NORMAL;

    public static ItemStackRenderState getOffHandItem(HumanoidRenderState humanoidRenderState) {
        return humanoidRenderState.mainArm != HumanoidArm.RIGHT ? humanoidRenderState.rightHandItemState :
                humanoidRenderState.leftHandItemState;
    }

    public static ItemType getItemType(Item item) {
        if (item instanceof TridentItem) {
            return ItemType.TRIDENT;
        } else if (item instanceof FishingRodItem || item instanceof FoodOnAStickItem) {
            return ItemType.ROD;
        } else {
            return ItemType.NORMAL;
        }
    }

    public enum ItemType {
        NORMAL,
        ROD,
        TRIDENT;

        public ItemDisplayContext getItemDisplayContext() {
            return this == TRIDENT ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.GROUND;
        }
    }
}
