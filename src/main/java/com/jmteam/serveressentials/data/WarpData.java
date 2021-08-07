package com.jmteam.serveressentials.data;

import net.minecraft.util.math.BlockPos;

public class WarpData {

    private BlockPos position;
    private float rotationpitch;
    private float rotationyaw;
    private int dimension;

    public WarpData(BlockPos pos, float pitch, float yaw, int dimension) {
        this.position = pos;
        this.rotationpitch = pitch;
        this.rotationyaw = yaw;
        this.dimension = dimension;
    }

    public BlockPos getPosition() {
        return position;
    }

    public float getRotationpitch() {
        return rotationpitch;
    }

    public float getRotationyaw() {
        return rotationyaw;
    }

    public int getDimension() {
        return dimension;
    }
}
