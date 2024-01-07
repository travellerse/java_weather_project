/*
 * Created by JFormDesigner on Wed Nov 15 20:03:15 CST 2023
 */

package gui;

import core.WeatherServer;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

/**
 * @author 13943
 */
public class MainJFrame extends JFrame {

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel titlePanel;
    private JLabel currentTime;
    private JLabel currentPosition;
    private JButton refreshButton;
    private JPanel currentWeatherPanel;
    private JLabel weatherIcon;
    private JLabel currentTemperature;
    private JLabel currentWeather;
    private JLabel currentWind;
    private JLabel currentHumidity;
    private JLabel currentClouds;
    public WeatherServer weatherServer = new WeatherServer();
    private JLabel currentAQI;
    private JLabel moderatoryPolluted;
    private JPanel futureWeatherPanel;
    private JPanel datePanel;
    private JLabel dayLabel1;
    private JLabel dayLabel2;
    private JLabel dayLabel3;
    private JLabel dayLabel4;
    private JLabel dayLabel5;
    private JLabel iconLabel1;
    private JLabel iconLabel2;
    private JLabel iconLabel3;
    private JLabel iconLabel4;
    private JLabel iconLabel5;
    private JPanel lineJPanel;
    private JLabel primaryPollutants;
    public MainJFrame() {
        weatherServer.start((long) (1000));
        initComponents();
        new Timer(1000, this::timeListener).start();
        changeWeatherShow();
        changeWeatherCalendarShow();
    }

    public static void main(String[] args) {
        JFrame frame = new MainJFrame();
        frame.setTitle("Weather");
        frame.setSize(950, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void changeWeatherShow() {
        this.currentWeather.setText(this.weatherServer.currentWeatherData.weather);
        this.currentTemperature.setText(this.weatherServer.currentWeatherData.temperature + "℃");
        this.currentWind.setText("Wind: " + this.weatherServer.currentWeatherData.wind + "m/s");
        this.currentHumidity.setText("Humidity: " + this.weatherServer.currentWeatherData.humidity + "%");
        this.currentClouds.setText("Clodus: " + this.weatherServer.currentWeatherData.clouds + "%");
        System.out.println("/image/" + this.weatherServer.currentWeatherData.iconId + "@2x.png");
        this.weatherIcon.setIcon(new ImageIcon(getClass().getResource("/image/" + this.weatherServer.currentWeatherData.iconId + "@2x.png")));
        this.currentAQI.setText("AQI: " + this.weatherServer.airPolutionData.AQI);
        this.moderatoryPolluted.setText(this.weatherServer.airPolutionData.getModeratoryPolluted());
        this.primaryPollutants.setText("<html>Primary Pollutants: <br>" + this.weatherServer.airPolutionData.mainPollution+"</html>");

        System.out.println(this.weatherServer.airPolutionData.co);
        System.out.println(this.weatherServer.airPolutionData.no);
        System.out.println(this.weatherServer.airPolutionData.no2);
        System.out.println(this.weatherServer.airPolutionData.o3);
        System.out.println(this.weatherServer.airPolutionData.so2);
        System.out.println(this.weatherServer.airPolutionData.pm2_5);
        System.out.println(this.weatherServer.airPolutionData.calculateAQI());
    }

    private void changeWeatherCalendarShow() {
        this.dayLabel1.setText("<html>Today<br>" + this.weatherServer.futureWeatherData.dataSet[0].date+"</html>");
        this.dayLabel2.setText("<html>"+weatherServer.getWeekday(this.weatherServer.futureWeatherData.dataSet[1].date)+"<br>"+this.weatherServer.futureWeatherData.dataSet[1].date+"</html>");
        this.dayLabel3.setText("<html>"+weatherServer.getWeekday(this.weatherServer.futureWeatherData.dataSet[2].date)+"<br>"+this.weatherServer.futureWeatherData.dataSet[2].date+"</html>");
        this.dayLabel4.setText("<html>"+weatherServer.getWeekday(this.weatherServer.futureWeatherData.dataSet[3].date)+"<br>"+this.weatherServer.futureWeatherData.dataSet[3].date+"</html>");
        this.dayLabel5.setText("<html>"+weatherServer.getWeekday(this.weatherServer.futureWeatherData.dataSet[4].date)+"<br>"+this.weatherServer.futureWeatherData.dataSet[4].date+"</html>");

        this.iconLabel1.setIcon(new ImageIcon(getClass().getResource("/image/" + this.weatherServer.futureWeatherData.dataSet[0].iconId + "@2x.png")));
        this.iconLabel1.updateUI();
        this.iconLabel2.setIcon(new ImageIcon(getClass().getResource("/image/" + this.weatherServer.futureWeatherData.dataSet[1].iconId + "@2x.png")));
        this.iconLabel3.setIcon(new ImageIcon(getClass().getResource("/image/" + this.weatherServer.futureWeatherData.dataSet[2].iconId + "@2x.png")));
        this.iconLabel4.setIcon(new ImageIcon(getClass().getResource("/image/" + this.weatherServer.futureWeatherData.dataSet[3].iconId + "@2x.png")));
        this.iconLabel5.setIcon(new ImageIcon(getClass().getResource("/image/" + this.weatherServer.futureWeatherData.dataSet[4].iconId + "@2x.png")));
    }

    private void timeListener(ActionEvent e) {
        this.currentTime.setText(this.weatherServer.getCurrentTime());
        if (this.weatherServer.isIntegerHour()) {
            changeWeatherShow();
            changeWeatherCalendarShow();
        }
    }

    private void refreshButtonMouseClicked(MouseEvent e) {
        refreshButton.setText("Refreshing...");
        refreshButton.setEnabled(false);
        try {
            weatherServer.update();
        } catch (IOException | URISyntaxException ex) {
            JOptionPane.showMessageDialog(null,"网络已断开");
        }
        changeWeatherShow();
        changeWeatherCalendarShow();
        refreshButton.setText("Refresh");
        refreshButton.setEnabled(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        ResourceBundle bundle = ResourceBundle.getBundle("localization.lang");
        titlePanel = new JPanel();
        currentTime = new JLabel();
        currentPosition = new JLabel();
        refreshButton = new JButton();
        currentWeatherPanel = new JPanel();
        weatherIcon = new JLabel();
        currentTemperature = new JLabel();
        currentWeather = new JLabel();
        currentWind = new JLabel();
        currentHumidity = new JLabel();
        currentClouds = new JLabel();
        currentAQI = new JLabel();
        moderatoryPolluted = new JLabel();
        primaryPollutants = new JLabel();
        futureWeatherPanel = new JPanel();
        datePanel = new JPanel();
        dayLabel1 = new JLabel();
        dayLabel2 = new JLabel();
        dayLabel3 = new JLabel();
        dayLabel4 = new JLabel();
        dayLabel5 = new JLabel();
        iconLabel1 = new JLabel();
        iconLabel2 = new JLabel();
        iconLabel3 = new JLabel();
        iconLabel4 = new JLabel();
        iconLabel5 = new JLabel();
        lineJPanel = new LineJPanel(this.weatherServer.futureWeatherData.getMaxTemperature(), this.weatherServer.futureWeatherData.getMinTemperature());

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== titlePanel ========
        {
            titlePanel.setBackground(new Color(0xff9933));
            titlePanel.setBorder(new MatteBorder(0, 0, 2, 0, Color.black));
            titlePanel.setLayout(new BorderLayout());

            //---- currentTime ----
            currentTime.setText(bundle.getString("currentTime.text"));
            currentTime.setFont(new Font("Inter", Font.BOLD, 18));
            currentTime.setBackground(new Color(0x00ffffff, true));
            currentTime.setBorder(null);
            titlePanel.add(currentTime, BorderLayout.CENTER);

            //---- currentPosition ----
            currentPosition.setText(bundle.getString("currentPosition.text"));
            currentPosition.setFont(new Font("Inter", Font.BOLD, 24));
            currentPosition.setBackground(new Color(0x00ffffff, true));
            currentPosition.setBorder(new MatteBorder(0, 0, 2, 0, Color.red));
            titlePanel.add(currentPosition, BorderLayout.NORTH);

            //---- refreshButton ----
            refreshButton.setText("Refresh");
            refreshButton.setBackground(new Color(0xff9933));
            refreshButton.setBorder(null);
            refreshButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    refreshButtonMouseClicked(e);
                }
            });
            titlePanel.add(refreshButton, BorderLayout.EAST);
        }
        contentPane.add(titlePanel, BorderLayout.NORTH);

        //======== currentWeatherPanel ========
        {
            currentWeatherPanel.setBorder(new SoftRectangleBorder());
            currentWeatherPanel.setLayout(null);

            //---- weatherIcon ----
            weatherIcon.setIcon(new ImageIcon(getClass().getResource("/image/10d@2x.png")));
            currentWeatherPanel.add(weatherIcon);
            weatherIcon.setBounds(20, 0, 100, 100);

            //---- currentTemperature ----
            currentTemperature.setBackground(new Color(0x00ffffff, true));
            currentTemperature.setText("15\u2103");
            currentTemperature.setFont(new Font("Inter", Font.BOLD, 32));
            currentTemperature.setBorder(null);
            currentWeatherPanel.add(currentTemperature);
            currentTemperature.setBounds(120, 0, 100, 100);

            //---- currentWeather ----
            currentWeather.setBackground(new Color(0x00ffffff, true));
            currentWeather.setText("Clear Sky");
            currentWeather.setFont(new Font("Inter", Font.BOLD, 24));
            currentWeather.setBorder(null);
            currentWeatherPanel.add(currentWeather);
            currentWeather.setBounds(220, 0, 200, 100);

            //---- currentWind ----
            currentWind.setBackground(new Color(0x00ffffff, true));
            currentWind.setText(" Wind: 0.2 m/s");
            currentWind.setFont(new Font("Inter", Font.PLAIN, 16));
            currentWind.setBorder(null);
            currentWeatherPanel.add(currentWind);
            currentWind.setBounds(20, 100, 120, 80);

            //---- currentHumidity ----
            currentHumidity.setBackground(new Color(0x00ffffff, true));
            currentHumidity.setText("Humidity: 56%");
            currentHumidity.setFont(new Font("Inter", Font.PLAIN, 16));
            currentHumidity.setBorder(null);
            currentWeatherPanel.add(currentHumidity);
            currentHumidity.setBounds(140, 100, 120, 80);

            //---- currentClouds ----
            currentClouds.setBackground(new Color(0x00ffffff, true));
            currentClouds.setFont(new Font("Inter", Font.PLAIN, 16));
            currentClouds.setText("Clouds: 100%");
            currentClouds.setBorder(null);
            currentWeatherPanel.add(currentClouds);
            currentClouds.setBounds(260, 100, 120, 80);

            //---- currentAQI ----
            currentAQI.setBackground(new Color(0x00ffffff, true));
            currentAQI.setFont(new Font("Inter", Font.PLAIN, 18));
            currentAQI.setText("AQI: 175");
            currentAQI.setBorder(null);
            currentWeatherPanel.add(currentAQI);
            currentAQI.setBounds(20, 200, 120, 80);

            //---- moderatoryPolluted ----
            moderatoryPolluted.setBackground(new Color(0x00ffffff, true));
            moderatoryPolluted.setFont(new Font("Inter", Font.PLAIN, 18));
            moderatoryPolluted.setText("Moderatory Polluted");
            moderatoryPolluted.setBorder(null);
            currentWeatherPanel.add(moderatoryPolluted);
            moderatoryPolluted.setBounds(140, 200, 120, 80);

            //---- MainPollution ----
            primaryPollutants.setBackground(new Color(0x00ffffff, true));
            primaryPollutants.setFont(new Font("Inter", Font.PLAIN, 18));
            primaryPollutants.setText("Primary Pollutants: PM2.5");
            primaryPollutants.setBorder(null);
            currentWeatherPanel.add(primaryPollutants);
            primaryPollutants.setBounds(260, 200, 120, 80);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < currentWeatherPanel.getComponentCount(); i++) {
                    Rectangle bounds = currentWeatherPanel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = currentWeatherPanel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                currentWeatherPanel.setMinimumSize(preferredSize);
                currentWeatherPanel.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(currentWeatherPanel, BorderLayout.WEST);

        //======== futureWeatherPanel ========
        {
            futureWeatherPanel.setBorder(null);
            futureWeatherPanel.setLayout(new GridLayout(2, 1));

            //======== datePanel ========
            {
                datePanel.setBorder(new SoftRectangleBorder(Color.BLACK,new Color(0x74FF7E00,true),30,30));
                datePanel.setLayout(new GridLayout(2, 5));

                //---- dayLabel1 ----
                dayLabel1.setText(bundle.getString("dayLabel1.text"));
                datePanel.add(dayLabel1);

                //---- dayLabel2 ----
                dayLabel2.setText(bundle.getString("dayLabel2.text"));
                datePanel.add(dayLabel2);

                //---- dayLabel3 ----
                dayLabel3.setText(bundle.getString("dayLabel3.text"));
                datePanel.add(dayLabel3);

                //---- dayLabel4 ----
                dayLabel4.setText(bundle.getString("dayLabel4.text"));
                datePanel.add(dayLabel4);

                //---- dayLabel5 ----
                dayLabel5.setText(bundle.getString("dayLabel5.text"));
                datePanel.add(dayLabel5);

                //---- iconLabel1 ----
                iconLabel1.setMinimumSize(new Dimension(100, 100));
                datePanel.add(iconLabel1);

                //---- iconLabel2 ----
                iconLabel2.setMinimumSize(new Dimension(100, 100));
                datePanel.add(iconLabel2);

                //---- iconLabel3 ----
                iconLabel3.setMinimumSize(new Dimension(100, 100));
                datePanel.add(iconLabel3);

                //---- iconLabel4 ----
                iconLabel4.setMinimumSize(new Dimension(100, 100));
                datePanel.add(iconLabel4);

                //---- iconLabel5 ----
                iconLabel5.setMinimumSize(new Dimension(100, 100));
                datePanel.add(iconLabel5);
            }
            futureWeatherPanel.add(datePanel);

            //======== lineJPanel ========
            {
                lineJPanel.setLayout(null);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < lineJPanel.getComponentCount(); i++) {
                        Rectangle bounds = lineJPanel.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = lineJPanel.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    lineJPanel.setMinimumSize(preferredSize);
                    lineJPanel.setPreferredSize(preferredSize);
                }
            }
            futureWeatherPanel.add(lineJPanel);
        }
        contentPane.add(futureWeatherPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
