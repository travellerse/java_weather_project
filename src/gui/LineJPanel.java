package gui;

import java.awt.*;

public class LineJPanel extends javax.swing.JPanel {
    //绘制折线图
    public int[] maxTemperature;
    public int[] minTemperature;
    private int offsetX = 50;
    private int offsetY = -30;


    public LineJPanel(int[] maxTemperature, int[] minTemperature) {
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        int numDays = maxTemperature.length;

        // Calculate the width of each bar
        int barWidth = width / numDays;

        // Calculate the scaling factor for temperature values to fit within the panel height
        int maxTempValue = getMaxValue(maxTemperature);
        int minTempValue = getMinValue(minTemperature);
        int tempRange = maxTempValue - minTempValue;

        double scale = (double) height / tempRange * 0.5;

        // Set up fonts
        Font font = new Font("H4", Font.BOLD, 18);
        g.setFont(font);

        // Draw the temperature lines
        for (int i = 0; i < numDays - 1; i++) {
            int x1 = i * barWidth + offsetX;  // Add the X offset
            int x2 = (i + 1) * barWidth + offsetX;  // Add the X offset
            int y1_max = height - (int) ((maxTemperature[i] - minTempValue) * scale) + offsetY;  // Add the Y offset
            int y2_max = height - (int) ((maxTemperature[i + 1] - minTempValue) * scale) + offsetY;  // Add the Y offset
            int y1_min = height - (int) ((minTemperature[i] - minTempValue) * scale) + offsetY;  // Add the Y offset
            int y2_min = height - (int) ((minTemperature[i + 1] - minTempValue) * scale) + offsetY;  // Add the Y offset

            // Draw filled area between max and min lines
            g.setColor(new Color(255, 120, 14)); // Use any color you like
            g.fillPolygon(new int[]{x1, x1, x2, x2}, new int[]{y1_max, y1_min, y2_min, y2_max}, 4);

            // Draw max and min temperature lines
            g.setColor(Color.BLACK); // Use any color you like
            g.drawLine(x1, y1_max, x2, y2_max);
            g.drawLine(x1, y1_min, x2, y2_min);

            // Draw temperature labels
            g.drawString(Integer.toString(maxTemperature[i]) + "°", x1, y1_max - 5);
            g.drawString(Integer.toString(minTemperature[i]) + "°", x1, y1_min + 15);
        }

        // Draw the last day's labels
        g.drawString(Integer.toString(maxTemperature[numDays - 1]) + "°", (numDays - 1) * barWidth, height - (int) ((maxTemperature[numDays - 1] - minTempValue) * scale) - 5);
        g.drawString(Integer.toString(minTemperature[numDays - 1]) + "°", (numDays - 1) * barWidth, height - (int) ((minTemperature[numDays - 1] - minTempValue) * scale) + 15);
    }

    // Helper method to find the maximum value in an array
    private int getMaxValue(int[] array) {
        int max = array[0];
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    // Helper method to find the minimum value in an array
    private int getMinValue(int[] array) {
        int min = array[0];
        for (int value : array) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }
}
