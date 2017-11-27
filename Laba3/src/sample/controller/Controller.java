package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import sample.model.Harmonic;
import sample.model.PolyharmonicSignal;
import sample.model.Signal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    LineChart chartTestSignal;

    @FXML
    BarChart chartAmplitudeSpecter;

    @FXML
    BarChart chartPhaseSpecter;

    @FXML
    LineChart chartPolyharmonicSignal;

    @FXML
    BarChart chartAmplitudeSpecterPolyharmonic;

    @FXML
    BarChart chartPhaseSpecterPolyharmonic;


    @FXML
    ToggleGroup nToggle;

    @FXML
    ToggleGroup nPolyharmonicToggle;

    @FXML
    ToggleGroup nFilter;

    @FXML
    LineChart chartFilterSignal;

    public void buildPolyharmonicSignalAndFilter() {
        setChartProperties(chartFilterSignal);
        chartFilterSignal.getData().clear();

        RadioButton rbN = (RadioButton) nFilter.getSelectedToggle();
        int N = Integer.parseInt(rbN.getText());

        PolyharmonicSignal polyharmonicSignal = new PolyharmonicSignal(N);
        polyharmonicSignal.formSignal();
        polyharmonicSignal.buildGraph(chartFilterSignal);

        Harmonic[] harmonics = polyharmonicSignal.formHarmonics();
        List<Harmonic> harmonicsList = Arrays.asList(harmonics);
        for (Harmonic harmonic : harmonicsList){
            if (harmonic.getAmplitude() < 1){
                harmonic.setAcos(0.0);
                harmonic.setAsin(0.0);
            }

        }
        harmonics = harmonicsList.toArray(new Harmonic[harmonicsList.size()]);

        PolyharmonicSignal restoredSignal = new PolyharmonicSignal(harmonics.length * 2);
        restoredSignal.restoreFromHarmonics(harmonics);
        restoredSignal.buildGraph(chartFilterSignal);
    }

    public void buildPolyharmonicSignal() {
        setChartProperties(chartPolyharmonicSignal);
        chartPolyharmonicSignal.getData().clear();

        RadioButton rbN = (RadioButton) nPolyharmonicToggle.getSelectedToggle();
        int N = Integer.parseInt(rbN.getText());

        PolyharmonicSignal polyharmonicSignal = new PolyharmonicSignal(N);
        polyharmonicSignal.formSignal();
        polyharmonicSignal.buildGraph(chartPolyharmonicSignal);

        Harmonic[] harmonics = polyharmonicSignal.formHarmonics();
        buildAmplitudeSpecter(harmonics, chartAmplitudeSpecterPolyharmonic);
        buildPhaseSpecter(harmonics, chartPhaseSpecterPolyharmonic);

        PolyharmonicSignal restoredSignal = new PolyharmonicSignal(harmonics.length * 2);
        restoredSignal.restoreFromHarmonics(harmonics);
        restoredSignal.buildGraph(chartPolyharmonicSignal);
        restoredSignal.restoreFromHarmonicsWithoutPhase(harmonics);
        restoredSignal.buildGraph(chartPolyharmonicSignal);
    }

    public void buildGraphs() {
        setChartProperties(chartTestSignal);
        setChartProperties(chartAmplitudeSpecter);
        setChartProperties(chartPhaseSpecter);
        chartTestSignal.getData().clear();
        chartPhaseSpecter.getData().clear();
        chartAmplitudeSpecter.getData().clear();

        RadioButton rbN = (RadioButton) nToggle.getSelectedToggle();
        int N = Integer.parseInt(rbN.getText());

        Signal signal = new Signal(N);
        signal.formSignal();
        signal.buildGraph(chartTestSignal);

        Harmonic[] harmonics = signal.formHarmonics();
        buildAmplitudeSpecter(harmonics, chartAmplitudeSpecter);
        buildPhaseSpecter(harmonics, chartPhaseSpecter);

        Signal restoredSignal = new Signal(harmonics.length * 2);
        restoredSignal.restoreFromHarmonics(harmonics);
        restoredSignal.buildGraph(chartTestSignal);
    }

    private void buildPhaseSpecter(Harmonic[] harmonics, XYChart chart) {
        XYChart.Series series = new XYChart.Series();
        for (int j = 0; j < harmonics.length; j++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(j), harmonics[j].getPhase()));
        }
        chart.getData().clear();
        setChartProperties(chart);
        chart.getData().add(series);
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
