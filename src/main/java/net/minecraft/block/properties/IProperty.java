package net.minecraft.block.properties;

import java.util.Collection;

public interface IProperty<T extends Comparable<T>> {
    String getName();
    Class<T> getValueClass();
    Collection<T> getAllowedValues();
}
