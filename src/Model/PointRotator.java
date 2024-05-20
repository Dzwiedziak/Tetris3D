package Model;

import org.ejml.simple.SimpleMatrix;

public class PointRotator {
    public static double[] rotatePoint(double[] point, double rotationX, double rotationY, double rotationZ) {
        double radRotationX = Math.toRadians(rotationX);
        double radRotationY = Math.toRadians(rotationY);
        double radRotationZ = Math.toRadians(rotationZ);


        SimpleMatrix rotationMatrixX = new SimpleMatrix(new double[][] {
                {1, 0, 0},
                {0, Math.cos(radRotationX), -Math.sin(radRotationX)},
                {0, Math.sin(radRotationX), Math.cos(radRotationX)}
        });

        SimpleMatrix rotationMatrixY = new SimpleMatrix(new double[][] {
                {Math.cos(radRotationY), 0, Math.sin(radRotationY)},
                {0, 1, 0},
                {-Math.sin(radRotationY), 0, Math.cos(radRotationY)}
        });

        SimpleMatrix rotationMatrixZ = new SimpleMatrix(new double[][] {
                {Math.cos(radRotationZ), -Math.sin(radRotationZ), 0},
                {Math.sin(radRotationZ), Math.cos(radRotationZ), 0},
                {0, 0, 1}
        });

        SimpleMatrix pointMatrix = new SimpleMatrix(new double[][] {
                {point[0]},
                {point[1]},
                {point[2]}
        });

        SimpleMatrix rotatedPointMatrix = rotationMatrixZ.mult(rotationMatrixY).mult(rotationMatrixX).mult(pointMatrix);

        return new double[] {rotatedPointMatrix.get(0, 0), rotatedPointMatrix.get(1, 0), rotatedPointMatrix.get(2,0)};
    }
}
