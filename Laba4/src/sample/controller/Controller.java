package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import sample.model.AntialiasingCore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Controller {


    @FXML
    TextField txtFldB1;

    @FXML
    TextField txtFldB2;

    @FXML
    private LineChart chartOriginalSignal;

    @FXML
    private LineChart chartParabola4thDegree;

    @FXML
    private LineChart chartParabola4thDegreeSpectr;

    @FXML
    private LineChart chartMedianFilter;

    @FXML
    private LineChart chartMedianAveraging;

    @FXML
    private ToggleGroup nToggle;

    private double[] originalDiscreteValues;

    private static Random random = new Random();




    public void buildOriginalSignal() {
        RadioButton rbN = (RadioButton) nToggle.getSelectedToggle();
        int N = Integer.parseInt(rbN.getText());
        originalDiscreteValues = new double[N];
        int B1 = Integer.parseInt(txtFldB1.getText());
        int B2 = Integer.parseInt(txtFldB2.getText());
        double signalValue;
        double noiseValue;

        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < N; i++) {
            noiseValue = 0;
            for (int j = 50; j <= 70; j++) {
                noiseValue += Math.pow(-1, getRandomValue()) * B2 * Math.sin(2 * Math.PI * i * j / N);
            }
            signalValue = B1 * Math.sin(2 * Math.PI * i / N) + noiseValue;
            originalDiscreteValues[i] = signalValue;
            series.getData().add(new XYChart.Data<>(String.valueOf(i), signalValue));
        }

        chartOriginalSignal.getData().clear();
        setChartProperties(chartOriginalSignal);
        chartOriginalSignal.getData().add(series);
    }

    public void buildAntaliasingParabola(){
        AntialiasingCore antialiasingCore = new AntialiasingCore();
        double[] antialiasingValues = antialiasingCore.fourthDegreeParabolaAntialiasing(originalDiscreteValues);
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < antialiasingValues.length; i++){
            series.getData().add(new XYChart.Data<>(String.valueOf(i), antialiasingValues[i]));
        }
        chartParabola4thDegree.getData().clear();
        setChartProperties(chartParabola4thDegree);
        chartParabola4thDegree.getData().add(series);
    }

    public void buildAntialiasingMedianFilter(){
        AntialiasingCore antialiasingCore = new AntialiasingCore();
        double[] antialiasingValues = antialiasingCore.medianFilterAntialiasing(originalDiscreteValues);
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < antialiasingValues.length; i++){
            series.getData().add(new XYChart.Data<>(String.valueOf(i), antialiasingValues[i]));
        }

        chartMedianFilter.getData().clear();
        setChartProperties(chartMedianFilter);
        chartMedianFilter.getData().add(series);
    }

    public void buildAntialiasingMedianAveraging(){
        AntialiasingCore antialiasingCore = new AntialiasingCore();
        double[] antialiasingValues = antialiasingCore.medianAveragingAntialiasing(originalDiscreteValues);
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < antialiasingValues.length; i++){
            series.getData().add(new XYChart.Data<>(String.valueOf(i), antialiasingValues[i]));
        }

        chartMedianAveraging.getData().clear();
        setChartProperties(chartMedianAveraging);
        chartMedianAveraging.getData().add(series);
    }

    private static double getRandomValue() {
        return (random.nextDouble() > 0.5f) ? 1f : 0f;
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

}
