package com.example.lab12_optiuneab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class CustomGraphView extends View {

    private float[] values;
    private String graphType = "pie";
    private Paint paint;

    private final int[] COLORS = {
            Color.rgb(255, 0, 0),      // Roșu
            Color.rgb(0, 255, 0),      // Verde
            Color.rgb(0, 0, 255),      // Albastru
            Color.rgb(255, 255, 0),    // Galben
            Color.rgb(255, 0, 255),    // Magenta
            Color.rgb(0, 255, 255),    // Cyan
            Color.rgb(128, 0, 0),      // Maro
            Color.rgb(0, 128, 0),      // Verde închis
            Color.rgb(0, 0, 128),      // Albastru închis
            Color.rgb(128, 128, 0),    // Olive
            Color.rgb(128, 0, 128),    // Violet
            Color.rgb(0, 128, 128)     // Teal
    };

    public CustomGraphView(Context context) {
        super(context);
        init();
    }

    public CustomGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setData(float[] values, String graphType) {
        this.values = values;
        this.graphType = graphType;
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (values == null || values.length == 0) {
            return;
        }

        switch (graphType) {
            case "pie":
                drawPieChart(canvas);
                break;
            case "column":
                drawColumnChart(canvas);
                break;
            case "bar":
                drawBarChart(canvas);
                break;
        }
    }

    private void drawPieChart(Canvas canvas) {
        float total = 0;
        for (float value : values) {
            total += value;
        }

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        float radius = Math.min(centerX, centerY) * 0.8f;

        RectF oval = new RectF(
                centerX - radius,
                centerY - radius,
                centerX + radius,
                centerY + radius
        );

        float startAngle = 0;
        for (int i = 0; i < values.length; i++) {
            float sweepAngle = (values[i] / total) * 360;

            paint.setColor(COLORS[i]);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawArc(oval, startAngle, sweepAngle, true, paint);

            startAngle += sweepAngle;
        }
    }

    private void drawColumnChart(Canvas canvas) {
        if (values == null || values.length == 0) {
            return;
        }

        float maxValue = values[0];
        for (float value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        float padding = 50;
        float chartWidth = getWidth() - 2 * padding;
        float chartHeight = getHeight() - 2 * padding;
        float bottom = getHeight() - padding;

        float columnWidth = chartWidth / (values.length * 2);

        for (int i = 0; i < values.length; i++) {
            float left = padding + i * (columnWidth * 2);
            float columnHeight = (values[i] / maxValue) * chartHeight;
            float top = bottom - columnHeight;

            paint.setColor(COLORS[i]);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawRect(left, top, left + columnWidth, bottom, paint);
        }
    }

    private void drawBarChart(Canvas canvas) {
        if (values == null || values.length == 0) {
            return;
        }

        float maxValue = values[0];
        for (float value : values) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        float padding = 50;
        float chartWidth = getWidth() - 2 * padding;
        float chartHeight = getHeight() - 2 * padding;

        float barHeight = chartHeight / (values.length * 2);

        for (int i = 0; i < values.length; i++) {
            float top = padding + i * (barHeight * 2);
            float barWidth = (values[i] / maxValue) * chartWidth;

            paint.setColor(COLORS[i]);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawRect(padding, top, padding + barWidth, top + barHeight, paint);
        }
    }
}