package github.kasuminova.ecoaeextension.common.block.ecotech.ecalculator;

import github.kasuminova.ecoaeextension.ECOAEExtension;
import net.minecraft.util.ResourceLocation;

public class BlockECalculatorCasing extends BlockECalculator {

    public static final BlockECalculatorCasing INSTANCE = new BlockECalculatorCasing();

    protected BlockECalculatorCasing() {
        this.setBlockName(ECOAEExtension.MOD_ID + '.' + "ecalculator_casing");
    }

}
