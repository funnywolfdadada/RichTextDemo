package com.example.funnywolf.richtextdemo.spans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

public class CenterImageSpan extends ImageSpan {
    public CenterImageSpan(Context context, int resourceId) {
        super(context, resourceId);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return super.getSize(paint, text, start, end, fm);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
//        super.draw(canvas, text, start, end, x, top, y, bottom, paint);
        Drawable b = getDrawable();
        canvas.save();

        Paint.FontMetrics fm = paint.getFontMetrics();
        float drawableHeight = b.getBounds().height();
        float textHeight = fm.descent - fm.ascent;
        // 先对其到 descent
//        float transY = y + fm.descent;
//        // 再居中
//        transY -= (textHeight - drawableHeight) / 2;
//        transY -= drawableHeight;
        // 化简下就是
        float transY = y + (fm.descent + fm.ascent) / 2 - drawableHeight / 2;
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}
