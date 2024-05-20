package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TetrisMap {
    public BlocksList getMovingBlocksList() {
        return movingBlocksList;
    }

    private BlocksList movingBlocksList;
    private Point3D movingBlocksRotationCenter;

    public BlocksList getStaticBlocksList() {
        return staticBlocksList;
    }

    private BlocksList staticBlocksList;

    public int getHeight() {
        return height;
    }

    private final int height;

    public int getWidth() {
        return width;
    }

    private final int width;

    public int getDepth() {
        return depth;
    }

    private final int depth;
    private final PositionStrategy x;
    private final PositionStrategy y;
    private final PositionStrategy z;

    public double getScore() {
        return score;
    }

    private double score;

    TetrominoFileReader tetrominoFileReader;
    public TetrisMap(int height, int width, int depth, PositionStrategy x, PositionStrategy y, PositionStrategy z){
        this.tetrominoFileReader = TetrominoFileReader.GetInstance();
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.x = x;
        this.y = y;
        this.z = z;
        this.staticBlocksList = new BlocksList(new ArrayList<>());
    }
    public void generateTetromino(){
        Random random = new Random();
        int randomFileIndex = random.nextInt(tetrominoFileReader.CountTetrominos());
        BlocksList newPoints = new BlocksList(tetrominoFileReader.ReadTetrominoAsList(randomFileIndex));

        Properties properties = new Properties();
        properties.setColor(Colors.getRandomColor());
        for(Point3D point3D : newPoints){
            point3D.setProperties(properties);
        }
        movingBlocksList = newPoints;

        double max_x = 0;
        double max_y = 0;
        double max_z = 0;

        for (Point3D point3D : movingBlocksList) {
            max_x = Math.max(max_x, point3D.getX());
            max_y = Math.max(max_y, point3D.getY());
            max_z = Math.max(max_z, point3D.getZ());
        }

        double xshift = 0;
        double yshift = 0;
        double zshift = 0;

        if (x.equals(PositionStrategy.MIDDLE)) {
            xshift = (width - max_x) / 2;
        }
        if (y.equals(PositionStrategy.MIDDLE)) {
            yshift = (height - max_y) / 2;
        }
        if (z.equals(PositionStrategy.MIDDLE)) {
            zshift = (depth - max_z) / 2;
        }

        for (Point3D point3D : movingBlocksList) {
            point3D.shift(new Vector3D((int)xshift, (int)(yshift), (int)(zshift)));
        }
        Point3D massCenter = massCenter();
        massCenter.round();
        this.movingBlocksRotationCenter = massCenter;
    }
    public void addMovingToStaticBlocks(){
        staticBlocksList.addAll(movingBlocksList);
    }
    public boolean checkCollisionWithBorder(List<Point3D> point3DList){
        for(Point3D point3D : point3DList){
            if(point3D.getX() >= width || point3D.getX() < 0
                    || point3D.getY() >= height || point3D.getY() < 0
                    || point3D.getZ() >= depth || point3D.getZ() < 0){
                return true;
            }
        }
        return false;
    }
    public boolean checkCollisionWithStatic(BlocksList point3DList){
        List<Point3D> list = staticBlocksList.getIntersections(point3DList);
        return !list.isEmpty();
    }
    public boolean checkCollisionMovingWithBorder(){
        return checkCollisionWithBorder(movingBlocksList);
    }
    public boolean checkCollisionMovingWithStatic(){
        List<Point3D> list = movingBlocksList.getIntersections(staticBlocksList);
        return !list.isEmpty();
    }
    public List<Point3D> getMovedTetromino(Vector3D vector3D){
        List<Point3D> point3DList = new ArrayList<>();
        for(Point3D point3D : movingBlocksList){
            point3DList.add(point3D.getShifted(vector3D));
        }
        return point3DList;
    }
    public List<Point3D> getRotatedTetromino(EulerAngle eulerAngle, Point3D pivot){
        List<Point3D> point3DList = new ArrayList<>();
        for(Point3D point3D : movingBlocksList){
            point3DList.add(point3D.getRotated(pivot, eulerAngle));
        }
        return point3DList;
    }
    public List<Point3D> getRotatedTetromino(EulerAngle eulerAngle){
        List<Point3D> point3DList = new ArrayList<>();

        Point3D pivot = movingBlocksRotationCenter;
        for(Point3D point3D : movingBlocksList){
            point3DList.add(point3D.getRotated(pivot, eulerAngle));
        }
        return point3DList;
    }

    public void recalculateMassCenter(){
        Point3D massCenter = massCenter();
        this.movingBlocksRotationCenter = massCenter;
    }

    public void setMovingBlocksList(BlocksList point3DList) {
        this.movingBlocksList = point3DList;
    }
    public Point3D massCenter(){
        double sum_x = 0.0;
        double sum_y = 0.0;
        double sum_z = 0.0;

        int numberOfPoints = movingBlocksList.size();

        for (Point3D point3D : movingBlocksList) {
            sum_x += point3D.getX();
            sum_y += point3D.getY();
            sum_z += point3D.getZ();
        }

        double center_x = sum_x / numberOfPoints;
        double center_y = sum_y / numberOfPoints;
        double center_z = sum_z / numberOfPoints;

        return new Point3D(center_x, center_y, center_z);
    }
    public void setStaticBlocksList(BlocksList point3DList) {
        this.staticBlocksList = point3DList;
    }
    public void removeFull(){
        for(int y = 0; y < height ; y++){
            boolean full = isFullStatic(y);
            if(full) {
                for (int x = 0; x < width; x++) {
                    for (int z = 0; z < depth; z++) {
                        staticBlocksList.remove(new Point3D(x, y, z));
                    }
                }
                BlocksList blocksList = new BlocksList(new ArrayList<Point3D>());
                for (int biggerY = y + 1; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        for (int z = 0; z < depth; z++) {
                            blocksList.add(new Point3D(x, y, z));
                        }
                    }
                }
                List<Point3D> intersections = staticBlocksList.getIntersections(blocksList);
                for(Point3D point3D : intersections){
                    point3D.shift(new Vector3D(0, 1, 0));
                }
                staticBlocksList.sort(staticBlocksList.comparator());
                removeFull();
                score += width * depth;
                break;
            }
        }
    }
    public boolean isFullStatic(double y){
        for(int x = 0 ; x < width ; x++){
            for(int z = 0; z < depth; z++){
                if(!staticBlocksList.contains(new Point3D(x, y , z))){
                    return false;
                }
            }
        }
        return true;
    }
}
