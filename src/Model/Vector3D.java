package Model;

public class Vector3D {
    double x;
    double y;
    double z;
    public Vector3D(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3D getNegative(){
        return new Vector3D(-x, -y, -z);
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
