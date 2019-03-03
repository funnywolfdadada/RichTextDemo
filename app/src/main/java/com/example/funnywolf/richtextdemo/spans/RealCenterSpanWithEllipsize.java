package com.example.funnywolf.richtextdemo.spans;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.style.ImageSpan;

public class RealCenterSpanWithEllipsize extends ImageSpan {
    public RealCenterSpanWithEllipsize(Context context, int resourceId) {
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

    private static final String ELLIPSIZE = "…";

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
//        super.draw(canvas, text, start, end, x, top, y, bottom, paint);
        if (TextUtils.isEmpty(text)) {
            return;
        }
        // TextView 在 ellipsize 后的字符都置为 BOM（Byte Order Mark），Big-Endian 时为 '\uFEFF'，Little-Endian 时为 '\uFFFE'
        // 这些字符不影响显示，但影响长度判断，需要去掉
        text = text.toString().replace("\uFEFF", "").replace("\uFFFE", "");
        if (start >= text.length()) {
            return;
        }
        if (end > text.length()) {
            end = text.length();
        }
        String subText = text.subSequence(start, end).toString();
        // 当该 Span 刚好在 ELLIPSIZE 的位置时，需要把 ELLIPSIZE 画上
        if (ELLIPSIZE.equals(subText)) {
            canvas.drawText(text, start, end, x, y, paint);
            return;
        }
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
