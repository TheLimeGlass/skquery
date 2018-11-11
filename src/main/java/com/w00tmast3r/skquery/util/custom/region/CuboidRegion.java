package com.w00tmast3r.skquery.util.custom.region;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.util.Iterator;

public class CuboidRegion implements Iterable<Block> {

    private final Location point1;
    private final Location point2;
    private final int maxX;
    private final int maxY;
    private final int maxZ;
    private int nextX;
    private int nextY;
    private int nextZ;

    public CuboidRegion(Location point1, Location point2) {
        this.point1 = point1;
        this.point2 = point2;
        maxX = max().getBlockX();
        maxY = max().getBlockY();
        maxZ = max().getBlockZ();
        nextX = min().getBlockX();
        nextY = min().getBlockY();
        nextZ = min().getBlockZ();
    }

    @Override
    public Iterator<Block> iterator() {
        return new Iterator<Block>() {
            @Override
            public boolean hasNext() {
                return nextX != Integer.MIN_VALUE;
            }

            @Override
            public Block next() {
                if (!hasNext()) throw new java.util.NoSuchElementException();
                Block result = new Location(point1.getWorld(), nextX, nextY, nextZ).getBlock();
                forwardOne();
                forward();
                return result;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            public void forwardOne() {
                if (++nextX <= maxX) {
                    return;
                }
                nextX = min().getBlockX();

                if (++nextY <= maxY) {
                    return;
                }
                nextY = min().getBlockY();

                if (++nextZ <= maxZ) {
                    return;
                }
                nextX = Integer.MIN_VALUE;
            }

            public void forward() {
                while (hasNext() && !checkHas(new BlockVector(nextX, nextY, nextZ))) {
                    forwardOne();
                }
            }
        };
    }

    public Vector min() {
        return new Vector(Math.min(point1.getBlockX(), point2.getBlockX()), Math.min(point1.getBlockY(), point2.getBlockY()), Math.min(point1.getBlockZ(), point2.getBlockZ()));
    }

    public Vector max() {
        return new Vector(Math.max(point1.getBlockX(), point2.getBlockX()), Math.max(point1.getBlockY(), point2.getBlockY()), Math.max(point1.getBlockZ(), point2.getBlockZ()));
    }

    public boolean checkHas(Vector pt) {
        double x = pt.getX();
        double y = pt.getY();
        double z = pt.getZ();
        Vector min = min();
        Vector max = max();
        return (x >= min.getBlockX()) && (x <= max.getBlockX()) && (y >= min.getBlockY()) && (y <= max.getBlockY()) && (z >= min.getBlockZ()) && (z <= max.getBlockZ());
    }
}
