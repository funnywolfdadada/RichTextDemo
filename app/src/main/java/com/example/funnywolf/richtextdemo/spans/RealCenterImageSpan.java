package com.example.funnywolf.richtextdemo.spans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

public class RealCenterImageSpan extends ImageSpan {
    public RealCenterImageSpan(Context context, int resourceId) {
        super(context, resourceId);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
//        return super.getSize(paint, text, start, end, fm);
        Drawable d = getDrawable();
        Rect rect = d.getBounds();

        float drawableHeight = rect.height();
        Paint.FontMetrics paintFm = paint.getFontMetrics();
        float textHeight = paintFm.descent - paintFm.ascent;

        if (fm != null) {
            float textCenter = (paintFm.descent + paintFm.ascent) / 2;
            fm.ascent = fm.top = (int) (textCenter - drawableHeight / 2);
            fm.descent = fm.bottom = (int) (drawableHeight + fm.ascent);
        }

        return rect.right;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
//        super.draw(canvas, text, start, end, x, top, y, bottom, paint);
        Drawable b = getDrawable();
        canvas.save();

        Paint.FontMetrics fm = paint.getFontMetrics();
        float drawableHeight = b.getBounds().height();
        float transY = y + (fm.descent + fm.ascent) / 2 - drawableHeight / 2;
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}
