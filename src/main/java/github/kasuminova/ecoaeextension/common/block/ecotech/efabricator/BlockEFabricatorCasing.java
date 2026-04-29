package github.kasuminova.ecoaeextension.common.block.ecotech.efabricator;

import github.kasuminova.ecoaeextension.ECOAEExtension;

public class BlockEFabricatorCasing extends BlockEFabricator {

    public static final BlockEFabricatorCasing INSTANCE = new BlockEFabricatorCasing();

    protected BlockEFabricatorCasing() {
        this.setBlockName(ECOAEExtension.MOD_ID + '.' + "efabricator_casing");
    }

}
