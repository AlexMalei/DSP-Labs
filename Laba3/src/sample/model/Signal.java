package sample.model;

import javafx.scene.chart.XYChart;

public class Signal {

    private int N;
    private double[] discreteValues;

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public double getDiscreteValue(int index) {
        return discreteValues[index];
    }


    public Signal(int N) {
        this.N = N;
        discreteValues = new double[N];
    }

    public void formSignal() {
        for (int i = 0; i < N; i++) {
            discreteValues[i] = 100 * Math.cos(2 * Math.PI * 20 * i / (double) N - Math.PI / 4);
        }
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

    public void restoreFromHarmonics(Harmonic[] harmonics) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < harmonics.length; j++) {
                discreteValues[i] += harmonics[j].getAmplitude() * Math.cos(2 * Math.PI * j * i / N - harmonics[j].getPhase());
            }
        }
    }
}
