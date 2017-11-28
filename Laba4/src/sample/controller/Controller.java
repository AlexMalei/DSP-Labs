package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import sample.model.AntialiasingCore;
import sample.model.Harmonic;
import sample.model.PolyharmonicSignal;

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
    private BarChart chartOrginalSpectr;

    @FXML
    private LineChart chartParabola4thDegree;

    @FXML
    private BarChart chartParabola4thDegreeSpectr;

    @FXML
    private LineChart chartMedianFilter;
    @FXML
    private BarChart chartMedianFilterSpectr;

    @FXML
    private LineChart chartMedianAveraging;
    @FXML
    private BarChart chartMedianAveragingSpectr;

    @FXML
    private ToggleGroup nToggle;

    PolyharmonicSignal polyharmonicSignal;






    public void buildOriginalSignal() {
        setChartProperties(chartOriginalSignal);
        chartOriginalSignal.getData().clear();

        RadioButton rbN = (RadioButton) nToggle.getSelectedToggle();
        int N = Integer.parseInt(rbN.getText());
        int B1 = Integer.parseInt(txtFldB1.getText());
        int B2 = Integer.parseInt(txtFldB2.getText());

        polyharmonicSignal = new PolyharmonicSignal(N, B1, B2);
        polyharmonicSignal.formSignal();
        polyharmonicSignal.buildGraph(chartOriginalSignal);
        Harmonic[] harmonics = polyharmonicSignal.formHarmonics();
        buildAmplitudeSpecter(harmonics, chartOrginalSpectr);

    }

    private void buildAmplitudeSpecter(Harmonic[] harmonics, XYChart chart) {
        XYChart.Series series = new XYChart.Series();
        for (int j = 0; j < harmonics.length; j++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(j), harmonics[j].getAmplitude()));
        }
        chart.getData().clear();
        setChartProperties(chart);
        chart.getData().add(series);
    }

    public void buildAntaliasingParabola(){
        chartParabola4thDegree.getData().clear();
        chartParabola4thDegreeSpectr.getData().clear();
        setChartProperties(chartParabola4thDegree);
        setChartProperties(chartParabola4thDegreeSpectr);

        AntialiasingCore antialiasingCore = new AntialiasingCore();
        double[] antialiasingValues = antialiasingCore.fourthDegreeParabolaAntialiasing(polyharmonicSignal.getDiscreteValue());

        PolyharmonicSignal antialiasingSignal = new PolyharmonicSignal(antialiasingValues);
        antialiasingSignal.buildGraph(chartParabola4thDegree);
        Harmonic[] harmonics = antialiasingSignal.formHarmonics();
        buildAmplitudeSpecter(harmonics, chartParabola4thDegreeSpectr);
    }

    public void buildAntialiasingMedianFilter(){
        chartMedianFilter.getData().clear();
        chartMedianFilterSpectr.getData().clear();
        setChartProperties(chartMedianFilter);
        setChartProperties(chartMedianFilterSpectr);

        AntialiasingCore antialiasingCore = new AntialiasingCore();
        double[] antialiasingValues = antialiasingCore.medianFilterAntialiasing(polyharmonicSignal.getDiscreteValue());

        PolyharmonicSignal antialiasingSignal = new PolyharmonicSignal(antialiasingValues);
        antialiasingSignal.buildGraph(chartMedianFilter);
        Harmonic[] harmonics = antialiasingSignal.formHarmonics();
        buildAmplitudeSpecter(harmonics, chartMedianFilterSpectr);
    }

    public void buildAntialiasingMedianAveraging(){
        chartMedianAveraging.getData().clear();
        chartMedianAveragingSpectr.getData().clear();
        setChartProperties(chartMedianAveraging);
        setChartProperties(chartMedianAveragingSpectr);

        AntialiasingCore antialiasingCore = new AntialiasingCore();
        double[] antialiasingValues = antialiasingCore.medianAveragingAntialiasing(polyharmonicSignal.getDiscreteValue());

        PolyharmonicSignal antialiasingSignal = new PolyharmonicSignal(antialiasingValues);
        antialiasingSignal.buildGraph(chartMedianAveraging);
        Harmonic[] harmonics = antialiasingSignal.formHarmonics();
        buildAmplitudeSpecter(harmonics, chartMedianAveragingSpectr);
    }



    private void setChartProperties(XYChart chart) {
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
