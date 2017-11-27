package sample.model;

public class AmplitudeComponent {
    private double Acos;
    private double Asin;

    public AmplitudeComponent() {
    }

    public AmplitudeComponent(double acos, double asin) {
        Acos = acos;
        Asin = asin;
    }

    public double getAcos() {
        return Acos;
    }

    public void setAcos(double acos) {
        Acos = acos;
    }

    public double getAsin() {
        return Asin;
    }

    public void setAsin(double asin) {
        Asin = asin;
    }

    public double getAmplitude(){
        return Math.sqrt(getSquare(Acos) + getSquare(Asin));
    }

    public double getPhase(){
        return Math.atan(Asin/Acos);
    }

    private double getSquare(double number){
        return Math.pow(number, 2);
    }
}
