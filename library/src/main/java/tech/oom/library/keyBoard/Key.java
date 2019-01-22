package tech.oom.library.keyBoard;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;


import tech.oom.library.sound.SoundPlayUtils;


/**
 * Created by issuser on 2018/3/8 0008.
 */

public abstract class Key {
    private static final String TAG = "Key";
    private RectF rectF;
    private boolean isPressed;
    private Paint keyPaint;
    private int keyCode;
    private boolean isHasText = true;
    private TextPaint fingerPaint;
    private String fingerText;
    private PointF fingerPointF;
    private PointF circleCenterPointF;
    private float radius;
    private Paint circlePaint;
    private boolean showCircleAndFinger;
    private int circlePaintColor = Color.RED;
    private int playId;

    public Key(float left, float top, float right, float bottom) {

        rectF = new RectF(left, top, right, bottom);
        keyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        keyPaint.setFilterBitmap(true);
        keyPaint.setDither(true);
    }

    public final void drawKey(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        Bitmap originalBitmap = null;
        if (isPressed) {
            originalBitmap = getPressedBitmap();
        } else {
            originalBitmap = getUnPressedBitmap();
        }
        Matrix matrix = new Matrix();
        Bitmap bitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), matrix, true);
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, null, rectF, keyPaint);
        }
        String textToDraw = getTextToDraw();
        PointF textPoint = getTextPoint();
        if (!TextUtils.isEmpty(textToDraw) && getKeyTextPaint() != null && textPoint != null && isHasText) {
            canvas.drawText(textToDraw, textPoint.x, textPoint.y, getKeyTextPaint());
        }

        if (circleCenterPointF != null && circlePaint != null && showCircleAndFinger) {
            circlePaint.setColor(circlePaintColor);
            canvas.drawCircle(circleCenterPointF.x, circleCenterPointF.y, radius, circlePaint);
        }

        if (!TextUtils.isEmpty(fingerText) && fingerPointF != null && fingerPaint != null && showCircleAndFinger) {
            canvas.drawText(fingerText, fingerPointF.x, fingerPointF.y, fingerPaint);
        }

    }

    protected abstract Paint getKeyTextPaint();

    protected abstract Bitmap getUnPressedBitmap();

    protected abstract Bitmap getPressedBitmap();

    protected abstract String getTextToDraw();

    protected abstract PointF getTextPoint();

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public RectF getRectF() {
        return rectF;
    }

    public boolean isHasText() {
        return isHasText;
    }

    public void setHasText(boolean hasText) {
        isHasText = hasText;
    }


    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public Paint getKeyPaint() {
        return keyPaint;
    }

    public void setKeyPaint(Paint keyPaint) {
        this.keyPaint = keyPaint;
    }

    public String getFingerText() {
        return fingerText;
    }

    public void setFingerText(String fingerText) {
        this.fingerText = fingerText;
    }

    public PointF getFingerPointF() {
        return fingerPointF;
    }

    public void setFingerPointF(PointF fingerPointF) {
        this.fingerPointF = fingerPointF;
    }

    public PointF getCircleCenterPointF() {
        return circleCenterPointF;
    }

    public void setCircleCenterPointF(PointF circleCenterPointF) {
        this.circleCenterPointF = circleCenterPointF;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setCirclePaint(Paint circlePaint) {
        this.circlePaint = circlePaint;
    }

    public Paint getCirclePaint() {
        return circlePaint;
    }

    public TextPaint getFingerPaint() {
        return fingerPaint;
    }

    public void setFingerPaint(TextPaint fingerPaint) {
        this.fingerPaint = fingerPaint;
    }

    public boolean isShowCircleAndFinger() {
        return showCircleAndFinger;
    }

    public void setShowCircleAndFinger(boolean showCircleAndFinger) {
        this.showCircleAndFinger = showCircleAndFinger;
    }

    public int getCirclePaintColor() {
        return circlePaintColor;
    }

    public void setCirclePaintColor(int circlePaintColor) {
        this.circlePaintColor = circlePaintColor;
    }
}
