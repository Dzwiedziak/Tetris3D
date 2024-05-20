package Model;

public class EulerAngle {
    private double alfa;
    private double beta;
    private double gamma;
    public EulerAngle(double alfa, double beta, double gamma){
        this.alfa = alfa;
        this.beta = beta;
        this.gamma = gamma;
    }

    public double getAlfa() {
        return alfa;
    }

    public double getBeta() {
        return beta;
    }

    public double getGamma() {
        return gamma;
    }
}
