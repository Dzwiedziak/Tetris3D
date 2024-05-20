package Model;

import java.util.Comparator;
import java.util.List;

public class BlocksList extends CustomList<Point3D>{
    @Override
    public Comparator<Point3D> comparator() {
        return Comparator.comparingDouble(Point3D::getY)
                .thenComparingDouble(Point3D::getX)
                .thenComparingDouble(Point3D::getZ);
    }
    public BlocksList(List<Point3D> point3DList){
        super(point3DList);
    }
}
