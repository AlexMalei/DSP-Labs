package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class Controller {

    @FXML
    private LineChart chart2a;

    @FXML
    private LineChart chart2b;

    @FXML
    private LineChart chart2c;

    @FXML
    private LineChart chart3;

    @FXML
    private LineChart chart4;

    @FXML
    private ToggleGroup phase1;

    @FXML
    private ToggleGroup frequency1;

    @FXML
    private ToggleGroup amplitude1;

    @FXML
    private ToggleGroup n1;

    @FXML
    private ToggleGroup n2;

    @FXML
    private ToggleGroup n3;

    @FXML
    private ToggleGroup n4;

    @FXML
    private ToggleGroup n5;


    public void task2aStart() {
        RadioButton rbPhase = (RadioButton) phase1.getSelectedToggle();
        RadioButton rbN1 = (RadioButton) n1.getSelectedToggle();
        double phase = getPhaseFromString(rbPhase.getText());
        double amplitude = 6;
        double frequency = 3;
        double N = Double.parseDouble(rbN1.getText());
        performCalculation(chart2a, phase, amplitude, frequency, N);
    }

    public void task2bStart() {
        RadioButton rbFrequency = (RadioButton) frequency1.getSelectedToggle();
        RadioButton rbN2 = (RadioButton) n2.getSelectedToggle();

        double phase = Math.PI / 4;
        double amplitude = 8;
        double frequency = Double.parseDouble(rbFrequency.getText());
        double N = Double.parseDouble(rbN2.getText());

        performCalculation(chart2b, phase, amplitude, frequency, N);
    }

    public void task2cStart() {
        RadioButton rbAmplitude = (RadioButton) amplitude1.getSelectedToggle();
        RadioButton rbN3 = (RadioButton) n3.getSelectedToggle();

        double phase = Math.PI / 4;
        double amplitude = Double.parseDouble(rbAmplitude.getText());
        double frequency = 5;
        double N = Double.parseDouble(rbN3.getText());

        performCalculation(chart2c, phase, amplitude, frequency, N);
    }

    public void task3Start() {
        RadioButton rbN4 = (RadioButton) n4.getSelectedToggle();
        double[] phase = {Math.PI / 6, Math.PI / 2, Math.PI / 3, Math.PI / 9, 0};
        double[] amplitude = {6, 6, 6, 6, 6};
        double[] frequency = {1, 2, 3, 4, 5};
        double N = Double.parseDouble(rbN4.getText());
        chart3.getData().clear();
        XYChart.Series series = new XYChart.Series();

        for (int n = 0; n <= 2 * N; n++) {
            double funcValue = 0;

            for (int k = 0; k < 5; k++) {
                funcValue += getFunctionResult(phase[k], amplitude[k], frequency[k], N, n);
            }
            series.getData().add(new XYChart.Data<>(String.valueOf(n), funcValue));
        }
        series.setName("x(n) = Σ (A[j] * sin( 2 * π * f[j] * n / N + phi[j] ))");
        chart3.getData().add(series);
        setChartProperties(chart3);
    }

    public void task4Start() {
        RadioButton rbN5 = (RadioButton) n5.getSelectedToggle();
        double[] phase = {Math.PI / 6, Math.PI / 2, Math.PI / 3, Math.PI / 9, 0};
        double[] amplitude = {6, 6, 6, 6, 6};
        double[] frequency = {1, 2, 3, 4, 5};
        double N = Double.parseDouble(rbN5.getText());
        chart4.getData().clear();
        XYChart.Series series = new XYChart.Series();
        double[] multipliers = {0.20 / (N / frequency[0]), 0.20 / (N / frequency[1]), 0.20 / (N / frequency[2]), 0.20 / (N / frequency[3]), 0.20 / (N / frequency[4])};
        for (int n = 0; n <= 2 * N; n++) {
            double funcValue = 0;
            for (int k = 0; k < 5; k++) {
                funcValue += getFunctionResult(phase[k], amplitude[k], frequency[k], N, n);
            }
            series.getData().add(new XYChart.Data<>(String.valueOf(n), funcValue));
            incrementParams(phase, multipliers);
            incrementParams(amplitude, multipliers);
            incrementParams(frequency, multipliers);

        }
        series.setName("x(n) = Σ (A[j] * sin( 2 * π * f[j] * n / N + phi[j] ))");
        chart4.getData().add(series);
        setChartProperties(chart4);
    }

    private void incrementParams(double[] array, double[] multipliers) {
        for (int i = 0; i < array.length; i++) {
            array[i] += array[i] * multipliers[i];
        }
    }

    private void performCalculation(LineChart chart, double phase, double amplitude, double frequency, double N) {
        chart.getData().clear();
        XYChart.Series series = new XYChart.Series();

        for (int n = 0; n <= N; n++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(n), getFunctionResult(phase, amplitude, frequency, N, n)));
        }
        series.setName(String.format("x(n) = %s * sin( 2 * π * %s * n / N + %s )", amplitude, frequency, phase));
        chart.getData().add(series);
        setChartProperties(chart);
    }


    private double getFunctionResult(double phase, double amplitude, double frequency, double N, double n) {
        return amplitude * Math.sin(2 * Math.PI * frequency * n / N + phase);
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
            case "2π":
                return Math.PI * 2;
            case "π/6":
                return Math.PI / 6;
            case "π/2":
                return Math.PI / 2;
            case "0":
                return 0;
            case "3π/4":
                return 3 * Math.PI / 4;
        }
        return 0;
    }


}
