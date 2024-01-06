/*
 * Created by JFormDesigner on Wed Nov 15 20:03:15 CST 2023
 */

package gui;

import core.AirPolutionData;
import core.FutureWeatherData;
import core.Utils;
import core.CurrentWeatherData;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;

/**
 * @author 13943
 */
public class MainJFrame extends JFrame {

    public Utils core = new Utils();

    public CurrentWeatherData currentWeatherData = new CurrentWeatherData("Beijing");
    public FutureWeatherData futureWeatherData = new FutureWeatherData("Beijing");
    public AirPolutionData airPolutionData = new AirPolutionData("Beijing");

    public static void main(String[] args) {
        JFrame frame = new MainJFrame();
        frame.setTitle("Weather");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public MainJFrame() {
        initComponents();
        new Timer(1000, this::timeListener).start();
        try {
            changeWeatherShow();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void changeWeatherShow() throws IOException, URISyntaxException {
        this.currentWeatherData.getWeatherData();
        this.currentWeatherData.analyzeWeatherData();
        this.currentWeather.setText(this.currentWeatherData.weather);
        this.currentTemperature.setText(this.currentWeatherData.temperature + "℃");
        this.currentWind.setText("Wind: "+this.currentWeatherData.wind+ "m/s");
        this.currentHumidity.setText("Humidity: "+this.currentWeatherData.humidity + "%");
        this.currentClouds.setText("Clodus: "+this.currentWeatherData.clouds + "%");
        System.out.println("/image/" + this.currentWeatherData.iconId + "@2x.png");
        this.weatherIcon.setIcon(new ImageIcon(getClass().getResource("/image/" + this.currentWeatherData.iconId + "@2x.png")));
    }
    private void timeListener(ActionEvent e) {
        this.currentTime.setText(core.getCurrentTime());
        if(core.isIntegerHour()){

        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("localization.lang");
        titlePanel = new JPanel();
        currentPosition = new JLabel();
        currentTime = new JLabel();
        currentWeatherPanel = new JPanel();
        weatherIcon = new JLabel();
        currentTemperature = new JLabel();
        currentWind = new JLabel();
        currentWeather = new JLabel();
        currentHumidity = new JLabel();
        currentClouds = new JLabel();
        futureWeatherPanel = new JPanel();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== titlePanel ========
        {
            titlePanel.setBackground(new Color(0xff9933));
            titlePanel.setBorder(new MatteBorder(0, 0, 2, 0, Color.black));
            titlePanel.setLayout(new BorderLayout());

            //---- currentPosition ----
            currentPosition.setText(bundle.getString("currentPosition.text"));
            currentPosition.setFont(new Font("Inter", Font.BOLD, 24));
            currentPosition.setBackground(new Color(0x00ffffff, true));
            currentPosition.setBorder(new MatteBorder(0, 0, 2, 0, Color.red));
            titlePanel.add(currentPosition, BorderLayout.PAGE_END);

            //---- currentTime ----
            currentTime.setText(bundle.getString("currentTime.text"));
            currentTime.setFont(new Font("Inter", Font.BOLD, 18));
            currentTime.setBackground(new Color(0x00ffffff, true));
            currentTime.setBorder(null);
            titlePanel.add(currentTime, BorderLayout.CENTER);
        }
        contentPane.add(titlePanel, BorderLayout.NORTH);

        //======== currentWeatherPanel ========
        {
            currentWeatherPanel.setLayout(new GridLayout(0, 3));

            //---- weatherIcon ----
            weatherIcon.setIcon(new ImageIcon(getClass().getResource("/image/10d@2x.png")));
            currentWeatherPanel.add(weatherIcon);

            //---- currentTemperature ----
            currentTemperature.setBackground(new Color(0x00ffffff, true));
            currentTemperature.setText("15\u2103");
            currentTemperature.setFont(new Font("Inter", Font.BOLD, 32));
            currentTemperature.setBorder(null);
            currentWeatherPanel.add(currentTemperature);

            //---- currentWind ----
            currentWind.setBackground(new Color(0x00ffffff, true));
            currentWind.setText(" Wind: 0.2 m/s");
            currentWind.setFont(new Font("Inter", Font.PLAIN, 16));
            currentWind.setBorder(null);
            currentWeatherPanel.add(currentWind);

            //---- currentWeather ----
            currentWeather.setBackground(new Color(0x00ffffff, true));
            currentWeather.setText("Clear Sky");
            currentWeather.setFont(new Font("Inter", Font.BOLD, 24));
            currentWeather.setBorder(null);
            currentWeatherPanel.add(currentWeather);

            //---- currentHumidity ----
            currentHumidity.setBackground(new Color(0x00ffffff, true));
            currentHumidity.setText("Humidity: 56%");
            currentHumidity.setFont(new Font("Inter", Font.PLAIN, 16));
            currentHumidity.setBorder(null);
            currentWeatherPanel.add(currentHumidity);

            //---- currentClouds ----
            currentClouds.setBackground(new Color(0x00ffffff, true));
            currentClouds.setFont(new Font("Inter", Font.PLAIN, 16));
            currentClouds.setText("Clouds: 100%");
            currentClouds.setBorder(null);
            currentWeatherPanel.add(currentClouds);
        }
        contentPane.add(currentWeatherPanel, BorderLayout.WEST);

        //======== futureWeatherPanel ========
        {
            futureWeatherPanel.setLayout(new GridLayout(2, 1));
        }
        contentPane.add(futureWeatherPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel titlePanel;
    private JLabel currentPosition;
    private JLabel currentTime;
    private JPanel currentWeatherPanel;
    private JLabel weatherIcon;
    private JLabel currentTemperature;
    private JLabel currentWind;
    private JLabel currentWeather;
    private JLabel currentHumidity;
    private JLabel currentClouds;
    private JPanel futureWeatherPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
