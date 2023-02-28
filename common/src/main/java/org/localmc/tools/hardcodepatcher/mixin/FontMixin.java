package org.localmc.tools.hardcodepatcher.mixin;

import net.minecraft.client.gui.Font;
import org.localmc.tools.hardcodepatcher.ThePatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = Font.class, priority = -Integer.MAX_VALUE)
public class FontMixin {
    //GUI Transcription
    @ModifyArg(
            method = {
                    "drawShadow(Lcom/mojang/blaze3d/matrix/MatrixStack;Ljava/lang/String;FFI)I",
                    "drawShadow(Lcom/mojang/blaze3d/matrix/MatrixStack;Ljava/lang/String;FFIZ)I",
                    "draw(Lcom/mojang/blaze3d/matrix/MatrixStack;Ljava/lang/String;FFI)I"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/FontRenderer;drawInternal(Ljava/lang/String;FFILnet/minecraft/util/math/vector/Matrix4f;ZZ)I"
            ))
    private String proxy_DrawInternal(String p_228078_1_) {
        String c = ThePatcher.patch(p_228078_1_);
        c = c == null ? p_228078_1_ : c;
        return c;
    }
}
