package fuzs.puzzlesworkshop.client.element;

import com.mojang.blaze3d.matrix.MatrixStack;
import fuzs.puzzleslib.element.extension.ElementExtension;
import fuzs.puzzleslib.element.side.IClientElement;
import fuzs.puzzlesworkshop.PuzzlesWorkshop;
import fuzs.puzzlesworkshop.element.TimeToLiveElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderNameplateEvent;

public class TimeToLiveExtension extends ElementExtension<TimeToLiveElement> implements IClientElement {

    public TimeToLiveExtension(TimeToLiveElement parent) {

        super(parent);
    }

    @Override
    public void constructClient() {

        this.addListener(this::onRenderNameplate);
    }

    private void onRenderNameplate(final RenderNameplateEvent evt) {

        if (evt.getEntity() instanceof TNTEntity) {

            int remainingLife = ((TNTEntity) evt.getEntity()).getLife();
            String time = String.format("%.2f", remainingLife / 20.0F);
            StringTextComponent nameText = new StringTextComponent(time + " s");
            int damageColor = this.getDangerColor4(remainingLife, 80, evt.getPartialTicks());
            this.renderNameTag(evt.getEntityRenderer(), damageColor, evt.getEntity(), nameText, evt.getMatrixStack(), evt.getRenderTypeBuffer(), evt.getPackedLight());
        }
    }

    private void renderNameTag(EntityRenderer<?> renderer, int damageColor, Entity entity, ITextComponent nameText, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int packedLight) {

        double distance = renderer.getDispatcher().distanceToSqr(entity);
        if (ForgeHooksClient.isNameplateInRenderDistance(entity, distance)) {

            boolean visible = !entity.isDiscrete();
            float f = entity.getBbHeight() + 0.5F;
            matrixStack.pushPose();
            matrixStack.translate(0.0D, f, 0.0D);
            matrixStack.mulPose(renderer.getDispatcher().cameraOrientation());
            matrixStack.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrixStack.last().pose();
            float backgroundOpacity = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
            int backgroundOpacityInt = (int)(backgroundOpacity * 255.0F) << 24;
            FontRenderer fontrenderer = renderer.getFont();
            float textWidth = (float)(-fontrenderer.width(nameText) / 2);
            fontrenderer.drawInBatch(nameText, textWidth, 0, 553648127, false, matrix4f, renderTypeBuffer, visible, backgroundOpacityInt, packedLight);
            if (visible) {

                fontrenderer.drawInBatch(nameText, textWidth, 0, damageColor, false, matrix4f, renderTypeBuffer, false, 0, packedLight);
            }

            matrixStack.popPose();
        }
    }

    private int getDangerColor(int life, int maxLife, float partialTicks) {

        float explosionProgress = Math.min(1.0F, (life + partialTicks) / (float) maxLife);
        float off = (0.67F + (float) Math.sin((life + partialTicks) * Math.pow(2.0F - explosionProgress, 1.2))) * 0.5F;
        float r = 1.0F;
        float g = MathHelper.clamp(explosionProgress + (explosionProgress < 0.8F ? off : 0.0F), 0.0F, 1.0F);
        float b = Math.max(0.0F, 1.0F - (1.0F - explosionProgress) * 4.0F);

        PuzzlesWorkshop.LOGGER.info("Red {}, Green {}, Blue {}", r, g, b);
        return (int) (b * 255.0F) | (int) (g * 255.0F) << 8 | (int) (r * 255.0F) << 16 | 255 << 24;
    }

    private int getDangerColor2(int life, int maxLife, float partialTicks) {

        // use sin(x**2)
        float explosionProgress = Math.min(1.0F, (life + partialTicks) / (float) maxLife);
        explosionProgress = 0.5F + (float) Math.sin(20.0 * Math.pow(explosionProgress, 2.0)) * 0.5F;
        float r = 1.0F;
        float g;
        float b;
        final float greenStart = 0.25F;
        if (explosionProgress < greenStart) {
            float totalRatio = 1.0F / greenStart;
            explosionProgress *= totalRatio;
            g = explosionProgress;
            b = 0.0F;
        } else {
            explosionProgress -= greenStart;
            float totalRatio = 1.0F / (1.0F - greenStart);
            explosionProgress *= totalRatio;
            b = explosionProgress;
            g = 1.0F;
        }
//        float off = (0.67F + (float) Math.sin((life + partialTicks) * Math.pow(2.0F - explosionProgress, 1.2))) * 0.5F;
//        g = MathHelper.clamp(explosionProgress + (explosionProgress < 0.8F ? off : 0.0F), 0.0F, 1.0F);

        PuzzlesWorkshop.LOGGER.info("Red {}, Green {}, Blue {}", r, g, b);
        return (int) (b * 255.0F) | (int) (g * 255.0F) << 8 | (int) (r * 255.0F) << 16 | 255 << 24;
    }

    private int getDangerColor4(int life, int maxLife, float partialTicks) {

        // use sin(x**2)
        float explosionProgress = Math.min(1.0F, (life + partialTicks) / (float) maxLife);
        float r = 1.0F;
        float g;
        float b;
        if (explosionProgress < 0.5F) {
            g = explosionProgress * 2.0F;
            b = 0.0F;
        } else {
            g = 1.0F;
            b = (explosionProgress - 0.5F) * 2.0F;
        }
        if (explosionProgress < 0.2F && life % 4 < 2) {
            g = 1.0F - g;
        }

        PuzzlesWorkshop.LOGGER.info("Red {}, Green {}, Blue {}", r, g, b);
        return (int) (b * 255.0F) | (int) (g * 255.0F) << 8 | (int) (r * 255.0F) << 16 | 255 << 24;
    }

    private int getDangerColor3(int life, int maxLife, float partialTicks) {

        // use sin(x**2)
        final double offset = 1.0;
        float explosionProgress = 1.0F + (float) Math.sin(-100.0 * offset * Math.pow(life + partialTicks + offset, -0.5));
        explosionProgress = MathHelper.clamp(explosionProgress, 0.0F, 2.0F);
        float r = 1.0F;
        float g;
        float b;
        if (explosionProgress < 1.0F) {
            g = explosionProgress;
            b = 0.0F;
        } else {
            g = 1.0F;
            b = explosionProgress - 1.0F;
        }
//        final float greenStart = 0.25F;
//        if (explosionProgress < greenStart) {
//            float totalRatio = 1.0F / greenStart;
//            explosionProgress *= totalRatio;
//            g = explosionProgress;
//            b = 0.0F;
//        } else {
//            explosionProgress -= greenStart;
//            float totalRatio = 1.0F / (1.0F - greenStart);
//            explosionProgress *= totalRatio;
//            b = explosionProgress;
//            g = 1.0F;
//        }
//        float off = (0.67F + (float) Math.sin((life + partialTicks) * Math.pow(2.0F - explosionProgress, 1.2))) * 0.5F;
//        g = MathHelper.clamp(explosionProgress + (explosionProgress < 0.8F ? off : 0.0F), 0.0F, 1.0F);

        PuzzlesWorkshop.LOGGER.info("Red {}, Green {}, Blue {}", r, g, b);
        return (int) (b * 255.0F) | (int) (g * 255.0F) << 8 | (int) (r * 255.0F) << 16 | 255 << 24;
    }

}
