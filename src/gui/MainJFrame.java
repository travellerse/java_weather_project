/*
 * Created by JFormDesigner on Wed Nov 15 20:03:15 CST 2023
 */

package gui;

import javax.swing.plaf.metal.*;
import javax.swing.table.*;
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

    public static void main(String[] args) throws IOException, URISyntaxException {
        JFrame frame = new MainJFrame();
        frame.setTitle("Weather");
        frame.setSize(720, 280);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public MainJFrame() throws IOException, URISyntaxException {
        initComponents();
        new Timer(1000, this::timeListener).start();
        try {
            changeWeatherShow();
            changeWeatherCalendarShow();
            paintBrokenLine(panel2.getGraphics(), futureWeatherData.getMaxTemperature(), futureWeatherData.getMinTemperature());
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
        try{
            this.weatherIcon.setIcon(new ImageIcon(getClass().getResource("/image/" + this.currentWeatherData.iconId + "@2x.png")));
        }
        catch (Exception e){
            core.downloadIcon(this.currentWeatherData.iconId);
            this.weatherIcon.setIcon(new ImageIcon(getClass().getResource("/image/" + this.currentWeatherData.iconId + "@2x.png")));
        }

        airPolutionData.getWeatherData();
        airPolutionData.analyzeWeatherData();
        System.out.println(airPolutionData.co);
        System.out.println(airPolutionData.no);
        System.out.println(airPolutionData.no2);
        System.out.println(airPolutionData.o3);
        System.out.println(airPolutionData.so2);
        System.out.println(airPolutionData.pm2_5);
        System.out.println(airPolutionData.calculateAQI());
    }

    private void changeWeatherCalendarShow() throws IOException, URISyntaxException {
        this.futureWeatherData.getWeatherData();
        this.futureWeatherData.analyzeWeatherData();
        this.dayLabel1.setText("Today\n"+futureWeatherData.dataSet[0].date);
        this.dayLabel2.setText(futureWeatherData.dataSet[1].date);
        this.dayLabel3.setText(futureWeatherData.dataSet[2].date);
        this.dayLabel4.setText(futureWeatherData.dataSet[3].date);
        this.dayLabel5.setText(futureWeatherData.dataSet[4].date);
        for(int i = 0; i < 5; i++){
            core.downloadIcon(futureWeatherData.dataSet[i].iconId);
        }
        this.iconLabel1.setIcon(new ImageIcon(getClass().getResource("/image/" + futureWeatherData.dataSet[0].iconId + "@2x.png")));
        this.iconLabel2.setIcon(new ImageIcon(getClass().getResource("/image/" + futureWeatherData.dataSet[1].iconId + "@2x.png")));
        this.iconLabel3.setIcon(new ImageIcon(getClass().getResource("/image/" + futureWeatherData.dataSet[2].iconId + "@2x.png")));
        this.iconLabel4.setIcon(new ImageIcon(getClass().getResource("/image/" + futureWeatherData.dataSet[3].iconId + "@2x.png")));
        this.iconLabel5.setIcon(new ImageIcon(getClass().getResource("/image/" + futureWeatherData.dataSet[4].iconId + "@2x.png")));
    }

    private void paintBrokenLine(Graphics g, int[] maxTemperature,int[] minTemperature) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2.0f));
        System.out.println(Arrays.toString(maxTemperature));
        System.out.println(Arrays.toString(minTemperature));
        for(int i = 0; i < 4; i++){
            g2d.drawLine(100 + i * 100, 100 - maxTemperature[i], 100 + (i + 1) * 100, 100 - maxTemperature[i + 1]);
            g2d.drawLine(100 + i * 100, 100 - minTemperature[i], 100 + (i + 1) * 100, 100 - minTemperature[i + 1]);
        }
    }

    private void timeListener(ActionEvent e) {
        this.currentTime.setText(core.getCurrentTime());
        if(core.isIntegerHour()){
            try {
                changeWeatherShow();
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void refreshButtonMouseClicked(MouseEvent e) {
        refreshButton.setText("Refreshing...");
        refreshButton.setEnabled(false);
        try {
            changeWeatherShow();
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
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
        panel2 = new JPanel();

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
            currentWeatherPanel.setBorder(null);
            currentWeatherPanel.setLayout(null);

            //---- weatherIcon ----
            weatherIcon.setIcon(new ImageIcon(getClass().getResource("/image/10d@2x.png")));
            currentWeatherPanel.add(weatherIcon);
            weatherIcon.setBounds(0, 0, 100, 100);

            //---- currentTemperature ----
            currentTemperature.setBackground(new Color(0x00ffffff, true));
            currentTemperature.setText("15\u2103");
            currentTemperature.setFont(new Font("Inter", Font.BOLD, 32));
            currentTemperature.setBorder(null);
            currentWeatherPanel.add(currentTemperature);
            currentTemperature.setBounds(100, 0, 100, 100);

            //---- currentWeather ----
            currentWeather.setBackground(new Color(0x00ffffff, true));
            currentWeather.setText("Clear Sky");
            currentWeather.setFont(new Font("Inter", Font.BOLD, 24));
            currentWeather.setBorder(null);
            currentWeatherPanel.add(currentWeather);
            currentWeather.setBounds(200, 0, currentWeather.getPreferredSize().width, 100);

            //---- currentWind ----
            currentWind.setBackground(new Color(0x00ffffff, true));
            currentWind.setText(" Wind: 0.2 m/s");
            currentWind.setFont(new Font("Inter", Font.PLAIN, 16));
            currentWind.setBorder(null);
            currentWeatherPanel.add(currentWind);
            currentWind.setBounds(0, 100, 120, 80);

            //---- currentHumidity ----
            currentHumidity.setBackground(new Color(0x00ffffff, true));
            currentHumidity.setText("Humidity: 56%");
            currentHumidity.setFont(new Font("Inter", Font.PLAIN, 16));
            currentHumidity.setBorder(null);
            currentWeatherPanel.add(currentHumidity);
            currentHumidity.setBounds(120, 100, 120, 80);

            //---- currentClouds ----
            currentClouds.setBackground(new Color(0x00ffffff, true));
            currentClouds.setFont(new Font("Inter", Font.PLAIN, 16));
            currentClouds.setText("Clouds: 100%");
            currentClouds.setBorder(null);
            currentWeatherPanel.add(currentClouds);
            currentClouds.setBounds(240, 100, 120, 80);

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

            //======== panel2 ========
            {
                panel2.setLayout(null);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel2.getComponentCount(); i++) {
                        Rectangle bounds = panel2.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel2.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel2.setMinimumSize(preferredSize);
                    panel2.setPreferredSize(preferredSize);
                }
            }
            futureWeatherPanel.add(panel2);
        }
        contentPane.add(futureWeatherPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

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
    private JPanel panel2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
