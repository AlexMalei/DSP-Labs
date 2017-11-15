package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class Controller {
    @FXML
    LineChart chart;

    @FXML
    ToggleGroup nToggleGroup;

    @FXML
    ToggleGroup phaseToggleGroup;

    private int N;
    private double phase;

    public void performCalculation() {
        RadioButton rbPhase = (RadioButton) phaseToggleGroup.getSelectedToggle();
        RadioButton rbN = (RadioButton) nToggleGroup.getSelectedToggle();
        phase = getPhaseFromString(rbPhase.getText());
        N = Integer.parseInt(rbN.getText());
        int K = N / 4;
        double inc_m = (double)N * (1. / 4.) / 8;

        chart.getData().clear();
        setChartProperties(chart);
        XYChart.Series seriesErrorRmsA = new XYChart.Series();
        XYChart.Series seriesErrorRmsB = new XYChart.Series();
        XYChart.Series seriesErrorAmplitude = new XYChart.Series();

        for (int M = K - 1; M < 5 * N; M += inc_m) {

            double rmsA = calculateRmsA(M);
            double rmsB = calculateRmsB(M);
            double amplitude = getAmplitudeFourierTransform(M);

            double errorRmsA = 0.707 - rmsA;
            double errorRmsB = 0.707 - rmsB;
            double errorAmplitude = 1 - amplitude;

            seriesErrorRmsA.getData().add(new XYChart.Data<>(String.valueOf(M), errorRmsA));
            seriesErrorRmsB.getData().add(new XYChart.Data<>(String.valueOf(M), errorRmsB));
            seriesErrorAmplitude.getData().add(new XYChart.Data<>(String.valueOf(M), errorAmplitude));

        }
        seriesErrorRmsA.setName("Root-mean-square value variant A");
        seriesErrorRmsB.setName("Root-mean-square value variant B");
        seriesErrorAmplitude.setName("Amplitude error");
        chart.getData().addAll(seriesErrorRmsA, seriesErrorRmsB, seriesErrorAmplitude);
    }

    private double getAmplitudeFourierTransform(int M) {
        double As = 0;
        double Ac = 0;

        for (int n = 0; n < M; n++)
        {
            As += getFunctionValue(n) * Math.sin(2 * Math.PI * n / M);
            Ac += getFunctionValue(n) * Math.cos(2 * Math.PI * n / M);
        }
        Ac *= 2.0 / M;
        As *= 2.0 / M;

        return Math.sqrt(Ac * Ac + As * As);
    }

    private double calculateRmsA(int M) {
        double sum = 0;
        for (int n = 0; n < M; n++) {
            sum += Math.pow(getFunctionValue(n), 2);
        }
        return Math.sqrt(sum / (M + 1));
    }

    private double calculateRmsB(int M) {
        double leftSum = 0;
        double rightSum = 0;
        double funcValue = 0;
        for (int n = 0; n < M; n++) {
            funcValue = getFunctionValue(n);
            leftSum += Math.pow(funcValue, 2);
            rightSum += funcValue;
        }
        return Math.sqrt(leftSum / (M + 1) - Math.pow(rightSum / (M + 1), 2));
    }

    private double getFunctionValue(int n) {
        return Math.sin(2 * Math.PI * n / N + phase);
    }


    private void setChartProperties(LineChart chart) {
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        for (Node n : chart.lookupAll(".chart-series-line")) {
            n.setStyle("-fx-stroke: 1px;");
            n.setStyle("-fx-effect: null;");
        }
        for (Node n : chart.lookupAll(".default-color0.chart-line-symbol")) {
            n.setVisible(false);
        }
        for (Node n : chart.lookupAll(".default-color1.chart-line-symbol")) {
            n.setVisible(false);
        }
        for (Node n : chart.lookupAll(".default-color2.chart-line-symbol")) {
            n.setVisible(false);
        }

    }

    private double getPhaseFromString(String text) {
        switch (text) {
            case "0":
                return 0;
            case "3π/4":
                return 3 * Math.PI / 4;
        }
        return 0;
    }
}
