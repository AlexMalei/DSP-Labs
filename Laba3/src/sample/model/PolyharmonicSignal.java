package sample.model;

import javafx.scene.chart.XYChart;

import java.util.Random;

public class PolyharmonicSignal {
    private int N;
    private double[] discreteValues;
    private int[] amplitudesForRandom = {2, 3, 5, 9, 10, 12, 15};
    private double[] phasesForRandom = {Math.PI / 6, Math.PI / 4, Math.PI / 3, Math.PI / 2, 3 * Math.PI / 4, Math.PI};

    public int getN() {
        return N;
    }


    public double getDiscreteValue(int index) {
        return discreteValues[index];
    }


    public PolyharmonicSignal(int N) {
        this.N = N;
        discreteValues = new double[N];
    }


    public void formSignal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 30; j++) {
                discreteValues[i] += getRandomAmplitude() * Math.cos(2 * Math.PI * i * j / N - getRandomPhase());
            }

        }
    }

    private double getRandomPhase() {
        Random random = new Random();
        return phasesForRandom[random.nextInt(5)];
    }

    private double getRandomAmplitude() {
        Random random = new Random();
        return amplitudesForRandom[random.nextInt(6)];
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
            discreteValues[i] = harmonics[0].getAmplitude() / 2;
            for (int j = 1; j < harmonics.length; j++) {
                discreteValues[i] += harmonics[j].getAmplitude() * Math.cos(2 * Math.PI * j * i / N - harmonics[j].getPhase());
            }
        }
    }

    public void restoreFromHarmonicsWithoutPhase(Harmonic[] harmonics) {
        for (int i = 0; i < N; i++) {
            discreteValues[i] = harmonics[0].getAmplitude() / 2;
            for (int j = 1; j < harmonics.length; j++) {
                discreteValues[i] += harmonics[j].getAmplitude() * Math.cos(2 * Math.PI * j * i / N);
            }
        }
    }
}
