package net.skellatex.integrated_mining.content.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class IMLightStandBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF;
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty LIT;
    private static final VoxelShape SHAPE;

    public IMLightStandBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(HALF, DoubleBlockHalf.LOWER)).setValue(LIT, true)).setValue(WATERLOGGED, false));
    }

    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = (DoubleBlockHalf)state.getValue(HALF);
        if ((Boolean)state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (direction.getAxis() != Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || neighborState.is(this) && neighborState.getValue(HALF) != doubleBlockHalf) {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    public @Nullable BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        BlockState blockState = super.getStateForPlacement(blockPlaceContext);
        Level level = blockPlaceContext.getLevel();
        return blockState != null && blockPos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockPos.above()).canBeReplaced(blockPlaceContext) ? copyWaterloggedFrom(level, blockPos, blockState) : null;
    }

    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        BlockPos blockPos2 = blockPos.above();
        level.setBlock(blockPos2, copyWaterloggedFrom(level, blockPos2, (BlockState)this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER)), 3);
    }

    protected FluidState getFluidState(BlockState blockState) {
        return (Boolean)blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        if (blockState.getValue(HALF) != DoubleBlockHalf.UPPER) {
            return levelReader.getBlockState(blockPos.below()).isFaceSturdy(levelReader, blockPos.below(), Direction.UP);
        } else {
            BlockState blockState2 = levelReader.getBlockState(blockPos.below());
            return blockState2.is(this) && blockState2.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    public static BlockState copyWaterloggedFrom(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return blockState.hasProperty(BlockStateProperties.WATERLOGGED) ? (BlockState)blockState.setValue(BlockStateProperties.WATERLOGGED, levelReader.isWaterAt(blockPos)) : blockState;
    }

    public BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        if (!level.isClientSide) {
            if (player.isCreative()) {
                preventDropFromBottomPart(level, blockPos, blockState, player);
            } else {
                dropResources(blockState, level, blockPos, (BlockEntity)null, player, player.getMainHandItem());
            }
        }

        return super.playerWillDestroy(level, blockPos, blockState, player);
    }

    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
        super.playerDestroy(level, player, blockPos, Blocks.AIR.defaultBlockState(), blockEntity, itemStack);
    }

    protected static void preventDropFromBottomPart(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        DoubleBlockHalf doubleBlockHalf = (DoubleBlockHalf)blockState.getValue(HALF);
        BlockPos blockPos2;
        BlockState blockState2;
        if (doubleBlockHalf == DoubleBlockHalf.UPPER && (blockState2 = level.getBlockState(blockPos2 = blockPos.below())).is(blockState.getBlock()) && blockState2.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockState blockState3 = blockState2.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            level.setBlock(blockPos2, blockState3, 35);
            level.levelEvent(player, 2001, blockPos2, Block.getId(blockState2));
        }

    }

    protected long getSeed(BlockState blockState, BlockPos blockPos) {
        return Mth.getSeed(blockPos.getX(), blockPos.below(blockState.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), blockPos.getZ());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{HALF, WATERLOGGED, LIT});
    }

    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        boolean lit = (Boolean)blockState.getValue(LIT);
        if (lit) {
            if (itemStack.isEmpty() && blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
                return this.handleExtinguish(level, blockPos, blockState, player);
            }
        } else if (itemStack.is(ItemTags.CREEPER_IGNITERS)) {
            return this.handleInteraction(itemStack, level, blockPos, blockState, player, interactionHand);
        }

        return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    private ItemInteractionResult handleExtinguish(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        DoubleBlockHalf half = (DoubleBlockHalf)blockState.getValue(HALF);
        BlockPos affectedPos = half == DoubleBlockHalf.UPPER ? blockPos.below() : blockPos.above();
        level.setBlock(blockPos, (BlockState)blockState.setValue(LIT, false), 11);
        level.setBlock(affectedPos, (BlockState)level.getBlockState(affectedPos).setValue(LIT, false), 11);
        level.playSound((Player)null, blockPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 0.5F, 1.0F);
        level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
        return ItemInteractionResult.SUCCESS;
    }

    private ItemInteractionResult handleInteraction(ItemStack itemStack, Level level, BlockPos blockPos, BlockState blockState, Player player, InteractionHand interactionHand) {
        RandomSource randomSource = level.getRandom();
        boolean firecharge = itemStack.is(Items.FIRE_CHARGE);
        float pitch = level.getRandom().nextFloat() * 0.4F + 0.8F;
        SoundEvent soundEvent = SoundEvents.FLINTANDSTEEL_USE;
        DoubleBlockHalf half = (DoubleBlockHalf)blockState.getValue(HALF);
        BlockPos affectedPos = half == DoubleBlockHalf.UPPER ? blockPos.below() : blockPos.above();
        if (firecharge) {
            pitch = (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F + 1.0F;
            soundEvent = SoundEvents.FIRECHARGE_USE;
        }

        level.playSound((Player)null, blockPos, soundEvent, SoundSource.BLOCKS, 1.0F, pitch);
        level.setBlock(affectedPos, (BlockState)level.getBlockState(affectedPos).setValue(LIT, true), 11);
        level.setBlock(blockPos, (BlockState)blockState.setValue(BlockStateProperties.LIT, true), 11);
        level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
        if (firecharge) {
            itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(interactionHand));
        } else {
            itemStack.consume(1, player);
        }

        return ItemInteractionResult.SUCCESS;
    }

    static {
        HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        LIT = BlockStateProperties.LIT;
        SHAPE = box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)16.0F, (double)14.0F);
    }
}

