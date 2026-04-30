package github.kasuminova.ecoaeextension.common.tile.ecotech;

import javax.annotation.Nullable;

public interface EPart<C> {

    void setController(final C controller);

    @Nullable
    C getController();

    default boolean isAssembled() {
        return getController() != null;
    }

    void onAssembled();

    void onDisassembled();

}
