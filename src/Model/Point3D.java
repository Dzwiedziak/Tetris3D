package Model;

public class Point3D {
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    private double x;

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    private double y;

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    private double z;

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    private Properties properties;

    public Point3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Point3D getShifted(Vector3D vector3D){
        Point3D point3D = new Point3D(x + vector3D.getX(), y + vector3D.getY(), z + vector3D.getZ());
        point3D.setProperties(properties);
        return point3D;
    }

    public void shift(Vector3D vector3D){
        this.x += vector3D.getX();
        this.y += vector3D.getY();
        this.z += vector3D.getZ();
    }
    public double[] getArrayed(){
        return new double[]{x, y, z};
    }
    public int[] getIntegeredArray(){
        return new int[] {
                (int)Math.round(x),
                (int)Math.round(y),
                (int)Math.round(z)
        };
    }
    public Point3D getRotated(Point3D pivot3D, EulerAngle eulerAngle){
        Vector3D vector3D = new Vector3D(-pivot3D.x, -pivot3D.y, -pivot3D.z);
        Point3D shifted = this.getShifted(vector3D);
        double[] tabPoint3DShifted = shifted.getArrayed();
        double[] point3D = PointRotator.rotatePoint(tabPoint3DShifted, eulerAngle.getAlfa(), eulerAngle.getBeta(), eulerAngle.getGamma());
        Point3D rotatedNotShifted = new Point3D(point3D[0], point3D[1], point3D[2]);
        rotatedNotShifted.setProperties(properties);
        return rotatedNotShifted.getShifted(vector3D.getNegative());
    }
    public void rotate(Point3D pivot3D, EulerAngle eulerAngle){
        Point3D newPoint = getRotated(pivot3D, eulerAngle);
        this.x = newPoint.x;
        this.y = newPoint.y;
        this.z = newPoint.z;
    }
    @Override
    public boolean equals(Object object){
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Point3D other = (Point3D) object;

        return this.x == other.x && this.y == other.y && this.z == other.z;
    }
    public void round() {
        this.x = customRound(x);
        this.y = customRound(y);
        this.z = customRound(z);
    }

    private double customRound(double value) {
        if (value % 1 == 0.5) {
            return Math.floor(value);
        } else {
            return Math.round(value);
        }
    }
}
