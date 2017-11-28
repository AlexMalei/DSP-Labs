package sample.model;

import javafx.scene.chart.XYChart;

import java.util.Random;

public class PolyharmonicSignal {
    private int N;
    private double[] discreteValues;
    private static Random random = new Random();
    private int B1;
    private int B2;


    public int getN() {
        return N;
    }


    public double getDiscreteValue(int index) {
        return discreteValues[index];
    }

    public double[] getDiscreteValue() {
        return discreteValues;
    }

    public PolyharmonicSignal(double[] discreteValues){
        this.discreteValues = discreteValues;
        this.N = discreteValues.length;
    }

    public PolyharmonicSignal(int N, int B1, int B2) {
        this.N = N;
        discreteValues = new double[N];
        this.B1 = B1;
        this.B2 = B2;
    }


    public void formSignal() {
        double signalValue;
        double noiseValue;
        for (int i = 0; i < N; i++) {
            noiseValue = 0;
            for (int j = 50; j <= 70; j++) {
                noiseValue += Math.pow(-1, getRandomValue()) * B2 * Math.sin(2 * Math.PI * i * j / N);
            }
            signalValue = B1 * Math.sin(2 * Math.PI * i / N) + noiseValue;
            discreteValues[i] = signalValue;
        }

    }

    private static double getRandomValue() {
        return (random.nextDouble() > 0.5f) ? 1f : 0f;
    }


    public Harmonic[] formHarmonics() {
        Harmonic[] harmonics = new Harmonic[N / 2];
        double sumAcos;
        double sumAsin;
        for (int j = 1; j <= N / 2; j++) {
            sumAcos = 0;
            sumAsin = 0;
            for (int i = 0; i < N; i++) {
                sumAcos += discreteValues[i] * Math.cos(2 * Math.PI * j * i / N);
                sumAsin += discreteValues[i] * Math.sin(2 * Math.PI * j * i / N);
            }
            sumAcos = sumAcos * 2 / N;
            sumAsin = sumAsin * 2 / N;
            harmonics[j - 1] = new Harmonic(sumAcos, sumAsin);
        }
        return harmonics;
    }

    public void buildGraph(XYChart chart) {
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < N; i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i), getDiscreteValue(i)));
        }
        chart.getData().add(series);
    }




}
