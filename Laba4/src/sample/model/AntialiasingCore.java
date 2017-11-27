package sample.model;

import java.util.Arrays;

public class AntialiasingCore {
    private int windowSizeMedianFilter = 5;
    private int windowSizeMedianAveraging = 9;

    private int countRemovingElements = 2;


    public double[] medianAveragingAntialiasing(double[] values) {
        int offset = (windowSizeMedianAveraging - 1) / 2;
        double[] antailiasingValues = new double[values.length];
        double[] subarray; // change first and last K elements

        double sum;
        for (int i = offset; i < (values.length - offset); i++) {
            subarray = Arrays.copyOfRange(values, i - offset, i + offset);
            Arrays.sort(subarray);
            subarray = Arrays.copyOfRange(subarray, countRemovingElements, subarray.length - countRemovingElements);
            sum = 0;
            for (double value : subarray) {
                sum += value;
            }
            antailiasingValues[i] = (1.0 / (windowSizeMedianAveraging - 2 * countRemovingElements)) * sum;
        }
        return antailiasingValues;
    }

    public double[] medianFilterAntialiasing(double[] values) {
        int offset = (windowSizeMedianFilter - 1) / 2;
        double[] antailiasingValues = new double[values.length];
        double[] subarray;
        for (int i = offset; i < (values.length - offset); i++) {
            subarray = Arrays.copyOfRange(values, i - offset, i + offset);
            Arrays.sort(subarray);
            antailiasingValues[i] = subarray[offset];
        }
        return antailiasingValues;
    }

    public double[] fourthDegreeParabolaAntialiasing(double[] values) {
        double[] antialiasingValues = new double[values.length];
        double multiplier = 1 / 2431f;
        for (int i = 0; i < values.length; i++) {
            antialiasingValues[i] = multiplier * (110 * getOrZero(values, i - 6)
                    - 198 * getOrZero(values, i - 5)
                    - 135 * getOrZero(values, i - 4)
                    + 110 * getOrZero(values, i - 3)
                    + 390 * getOrZero(values, i - 2)
                    + 600 * getOrZero(values, i - 1)
                    + 677 * getOrZero(values, i)
                    + 600 * getOrZero(values, i + 1)
                    + 390 * getOrZero(values, i + 2)
                    + 110 * getOrZero(values, i + 3)
                    - 135 * getOrZero(values, i + 4)
                    - 198 * getOrZero(values, i + 5)
                    + 110 * getOrZero(values, i + 6));
        }
        return antialiasingValues;
    }


    private double getOrZero(double[] values, int i) {
        return (i > values.length - 1 || i < 0) ? 0 : values[i];
    }

}
