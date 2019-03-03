package com.example.funnywolf.richtextdemo.spans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

public class BaselineImageSpan extends ImageSpan {
    private int mBaselinePosition;
    public BaselineImageSpan(Context context, int resourceId, int n) {
        super(context, resourceId);
        mBaselinePosition = n;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
//        return super.getSize(paint, text, start, end, fm);
        Drawable d = getDrawable();
        Rect rect = d.getBounds();

        float drawableHeight = rect.height();

        if (fm != null) {
            switch (mBaselinePosition) {
                case 0:
                    fm.ascent = fm.top = 0;
                    break;
                case 1:
                    fm.ascent = fm.top = (int) (-drawableHeight / 2);
                    break;
                default:
                    fm.ascent = fm.top = (int) (-drawableHeight);
                    break;
            }
            fm.descent = fm.bottom = (int) (drawableHeight + fm.ascent);
        }

        return rect.right;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
//        super.draw(canvas, text, start, end, x, top, y, bottom, paint);
        Drawable b = getDrawable();
        canvas.save();

        canvas.translate(x, y);
        b.draw(canvas);
        canvas.restore();
    }
}
