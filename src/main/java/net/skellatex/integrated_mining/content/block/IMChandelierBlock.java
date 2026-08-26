package net.skellatex.integrated_mining.content.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IMChandelierBlock extends Block {
    private static final VoxelShape SHAPE = box((double)0.0F, (double)4.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F);

    public IMChandelierBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return Block.canSupportCenter(levelReader, blockPos.above(), Direction.DOWN);
    }
}
