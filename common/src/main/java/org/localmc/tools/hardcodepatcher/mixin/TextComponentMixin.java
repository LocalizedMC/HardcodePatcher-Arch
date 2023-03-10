package org.localmc.tools.hardcodepatcher.mixin;

import net.minecraft.network.chat.TextComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(TextComponent.class)
public abstract class TextComponentMixin {
    /*
    @Accessor("siblings")
    abstract List<Component> getSiblings();
    @Inject(method = "getSiblings", at = @At("HEAD"), cancellable = true)
    private void proxy_getSiblings(CallbackInfoReturnable<List<Component>> cir) {
        List<Component> list = this.getSiblings();
        if (!list.isEmpty()) {
            list.replaceAll(component -> {
                if (component instanceof TextComponent) {
                    return new TextComponent(ThePatcher.patch(component.getContents()));
                } else return component;
            });
        }
        cir.setReturnValue(list);
    }
    */

    @Shadow
    @Final
    protected List<Text> siblings;

    @Shadow
    public abstract IFormattableTextComponent copy();

    @Shadow public abstract Style getStyle();

    @ModifyArg(
            method = "getVisualOrderText",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/text/LanguageMap;getVisualOrder(Lnet/minecraft/util/text/ITextProperties;)Lnet/minecraft/util/IReorderingProcessor;"
            )
    )
    private ITextProperties proxy_getVisualOrder(ITextProperties p_128116_) {
        if (p_128116_ instanceof TextComponent) {
            String c = ThePatcher.patch(p_128116_.getString());
            return new StringTextComponent(c).setStyle(this.getStyle());
        }
        return p_128116_;
    }

    @Inject(
            method = "append",
            at = @At("HEAD"),
            cancellable = true
    )
    private void proxy_append(ITextComponent p_230529_1_, CallbackInfoReturnable<IFormattableTextComponent> cir) {
        String c = ThePatcher.patch(p_230529_1_.getString());
        this.siblings.add(new StringTextComponent(c).setStyle(this.getStyle()));
        cir.setReturnValue(this.copy());
    }

}
