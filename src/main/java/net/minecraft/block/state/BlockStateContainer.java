package net.minecraft.block.state;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

import java.util.Collection;
import java.util.Map;

public class BlockStateContainer {

    private final Block block;
    private final ImmutableMap<IProperty<?>, Comparable<?>> properties;
    private final IBlockState baseState;

    public BlockStateContainer(Block block, IProperty<?>... properties) {
        this.block = block;
        ImmutableMap.Builder<IProperty<?>, Comparable<?>> builder = ImmutableMap.builder();
        for (IProperty<?> prop : properties) {
            Collection<?> allowedValues = prop.getAllowedValues();
            builder.put(prop, (Comparable<?>) allowedValues.iterator().next());
        }
        this.properties = builder.build();
        this.baseState = new BlockStateBase(block, this.properties);
    }

    public Block getBlock() {
        return block;
    }

    public Collection<IProperty<?>> getProperties() {
        return properties.keySet();
    }

    public IBlockState getBaseState() {
        return baseState;
    }

    public <T extends Comparable<T>> T getValue(IProperty<T> property) {
        @SuppressWarnings("unchecked")
        T result = (T) properties.get(property);
        return result;
    }

    private static class BlockStateBase implements IBlockState {
        private final Block block;
        private final ImmutableMap<IProperty<?>, Comparable<?>> properties;

        BlockStateBase(Block block, ImmutableMap<IProperty<?>, Comparable<?>> properties) {
            this.block = block;
            this.properties = properties;
        }

        @Override
        public Collection<IProperty<?>> getPropertyNames() {
            return properties.keySet();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T extends Comparable<T>> T getValue(IProperty<T> property) {
            Comparable<?> value = properties.get(property);
            if (value == null) {
                throw new IllegalArgumentException("Property " + property.getName() + " not found on block " + block);
            }
            return (T) value;
        }

        @Override
        public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value) {
            ImmutableMap.Builder<IProperty<?>, Comparable<?>> builder = ImmutableMap.builder();
            for (Map.Entry<IProperty<?>, Comparable<?>> entry : properties.entrySet()) {
                if (entry.getKey().equals(property)) {
                    builder.put(entry.getKey(), value);
                } else {
                    builder.put(entry.getKey(), entry.getValue());
                }
            }
            return new BlockStateBase(block, builder.build());
        }

        @Override
        public ImmutableMap<IProperty<?>, Comparable<?>> getProperties() {
            return properties;
        }

        @Override
        public Block getBlock() {
            return block;
        }
    }
}
