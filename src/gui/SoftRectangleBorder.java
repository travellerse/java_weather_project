package gui;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.io.Serializable;

public class SoftRectangleBorder extends AbstractBorder implements Serializable {

    private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(0x0000000, true); // 默认背景颜色
    private static final int DEFAULT_ARC_WIDTH = 30;
    private static final int DEFAULT_ARC_HEIGHT = 30;
    private static final int BORDER_DISTANCE = 5; // 距离窗口的距离

    private final Color borderColor;
    private final Color backgroundColor;
    private final int arcWidth;
    private final int arcHeight;

    public SoftRectangleBorder() {
        this(DEFAULT_BORDER_COLOR, DEFAULT_BACKGROUND_COLOR, DEFAULT_ARC_WIDTH, DEFAULT_ARC_HEIGHT);
    }

    public SoftRectangleBorder(Color borderColor, Color backgroundColor, int arcWidth, int arcHeight) {
        this.borderColor = borderColor;
        this.backgroundColor = backgroundColor;
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();

        // 设置边框颜色
        g2d.setColor(borderColor);

        // 设置抗锯齿和渲染质量
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // 绘制圆角矩形边框，添加与窗口的距离
        g2d.drawRoundRect(x + BORDER_DISTANCE, y + BORDER_DISTANCE, width - 2 * BORDER_DISTANCE - 1, height - 2 * BORDER_DISTANCE - 1, arcWidth, arcHeight);

        // 填充矩形区域的背景颜色，确保只填充在圆角矩形的内部
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(x + BORDER_DISTANCE, y + BORDER_DISTANCE, width - 2 * BORDER_DISTANCE - 1, height - 2 * BORDER_DISTANCE - 1, arcWidth, arcHeight);

        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        return computeInsets(insets);
    }

    private Insets computeInsets(Insets insets) {
        insets.left = arcWidth + BORDER_DISTANCE;
        insets.top = arcHeight + BORDER_DISTANCE;
        insets.right = arcWidth + BORDER_DISTANCE;
        insets.bottom = arcHeight + BORDER_DISTANCE;
        return insets;
    }
}
