package com.example.aplikacjagraficzna;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {

    private List<Path> paths; // przechowuje ścieżki linii
    private List<Integer> colors; // przechowuje kolory linii

    private PathMeasure pathMeasure;
    private float previousX, previousY;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        paths = new ArrayList<>();
        colors = new ArrayList<>();
        pathMeasure = new PathMeasure();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startNewLine(x, y);
                previousX = x;
                previousY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                drawLine(x, y);
                previousX = x;
                previousY = y;
                break;
            case MotionEvent.ACTION_UP:
                finishLine(x,y);
                break;
        }

        return true;
    }

    public void startNewLine(float x, float y) {
        Path path = new Path();
        path.moveTo(x, y);
        paths.add(path);
        colors.add(getPaintColor()); // Dodaj aktualny kolor do listy
    }


    public void drawLine(float x, float y) {
        Path currentPath = getCurrentPath();
        currentPath.lineTo(x, y);
        invalidate();
    }

    public void finishLine(float x, float y) {
        startNewLine(x,y);
    }

    public void clearCanvas() {
        paths.clear();
        colors.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < paths.size(); i++) {
            Path path = paths.get(i);
            int color = colors.get(i);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setColor(color);

            canvas.drawPath(path, paint);

            // Rysowanie kółka na początku i końcu linii
            float[] points = calculateCirclePoints(path);
            float startX = points[0];
            float startY = points[1];
            float endX = points[2];
            float endY = points[3];

            canvas.drawCircle(startX, startY, 4, paint);
            canvas.drawCircle(endX, endY, 4, paint);
        }


    }

    private float[] calculateCirclePoints(Path path) {
        float[] points = new float[4];
        float[] pathPoints = new float[2];

        pathMeasure.setPath(path, false);

        // Początek linii
        pathMeasure.getPosTan(0, pathPoints, null);
        points[0] = pathPoints[0];
        points[1] = pathPoints[1];

        // Koniec linii
        pathMeasure.getPosTan(pathMeasure.getLength(), pathPoints, null);
        points[2] = pathPoints[0];
        points[3] = pathPoints[1];

        return points;
    }

    private Path getCurrentPath() {
        if (paths.isEmpty()) {
            return null;
        }

        return paths.get(paths.size() - 1);
    }

    private int getPaintColor() {
        if (colors.isEmpty()) {
            return Color.RED; // Domyślny kolor
        }

        return colors.get(colors.size() - 1);
    }

    public void setPaintColor(int color) {
        Path currentPath = getCurrentPath();
        if (currentPath != null) {
            Paint currentPaint = new Paint();
            currentPaint.setAntiAlias(true);
            currentPaint.setStrokeWidth(5);
            currentPaint.setStyle(Paint.Style.STROKE);
            currentPaint.setStrokeJoin(Paint.Join.ROUND);
            currentPaint.setStrokeCap(Paint.Cap.ROUND);
            currentPaint.setColor(color);
            paths.set(paths.size() - 1, currentPath); // Zaktualizuj aktualną ścieżkę na liście
            colors.set(colors.size() - 1, color); // Zaktualizuj kolor na liście (opcjonalne)
            invalidate(); // Odświeżenie widoku DrawingView, aby zobaczyć zmianę koloru
        }
    }

}
