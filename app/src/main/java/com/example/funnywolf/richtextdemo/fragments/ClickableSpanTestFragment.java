package com.example.funnywolf.richtextdemo.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextLinks;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funnywolf.richtextdemo.R;

public class ClickableSpanTestFragment extends Fragment {
    private static final String TAG = "ClickableSpanTestFragme";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.clickable_span_test_layout, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView[] textViews = {
                view.findViewById(R.id.text0),
                view.findViewById(R.id.text1),
                view.findViewById(R.id.text2),
        };
        final CharSequence text = textViews[0].getText();
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        for (int i = 0; i < text.length(); i++) {
            final char c = text.charAt(i);
            builder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    if (widget.getContext() == null) {
                        return;
                    }
                    Toast.makeText(widget.getContext(), c + "", Toast.LENGTH_SHORT).show();
                }
            }, i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textViews[0].setMovementMethod(LinkMovementMethod.getInstance());
        textViews[0].setText(builder);
        textViews[1].setMovementMethod(LinkMovementMethod.getInstance());
        textViews[1].setText(builder);

        textViews[2].setOnTouchListener(new ClickableSpanTouchListener());
        textViews[2].setText(builder);
    }

    public static class ClickableSpanTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (!(v instanceof TextView)) {
                return false;
            }
            TextView widget = (TextView) v;
            CharSequence text = widget.getText();
            if (!(text instanceof Spanned)) {
                return false;
            }
            int action = event.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
                int index = getTouchedIndex(widget, event);
                ClickableSpan link = getClickableSpanByIndex(widget, index);
                if (link != null) {
                    if (action == MotionEvent.ACTION_UP) {
                        link.onClick(widget);
                    }
                    return true;
                }
            }
            return false;
        }

        public static ClickableSpan getClickableSpanByIndex(TextView widget, int index) {
            if (widget == null || index < 0) {
                return null;
            }
            CharSequence charSequence = widget.getText();
            if (!(charSequence instanceof Spanned)) {
                return null;
            }
            Spanned buffer = (Spanned) charSequence;
            // end 应该是 index + 1，如果也是 index，得到的结果会往左偏
            ClickableSpan[] links = buffer.getSpans(index, index + 1, ClickableSpan.class);
            if (links != null && links.length > 0) {
                return links[0];
            }
            return null;
        }

        public static int getTouchedIndex(TextView widget, MotionEvent event) {
            if (widget == null || event == null) {
                return -1;
            }
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            // 根据 y 得到对应的行 line
            int line = layout.getLineForVertical(y);
            // 判断得到的 line 是否正确
            if (x < layout.getLineLeft(line) || x > layout.getLineRight(line)
                    || y < layout.getLineTop(line) || y > layout.getLineBottom(line)) {
                return -1;
            }
            // 根据 line 和 x 得到对应的下标
            int index = layout.getOffsetForHorizontal(line, x);
            // 这里考虑省略号的问题，得到真实显示的字符串的长度，超过就返回 -1
            int showedCount = widget.getText().length() - layout.getEllipsisCount(line);
            if (index > showedCount) {
                return -1;
            }
            // getOffsetForHorizontal 获得的下标会往右偏
            // 获得下标处字符左边的左边，如果大于点击的 x，就可能点的是前一个字符
            if (layout.getPrimaryHorizontal(index) > x) {
                index -= 1;
            }
            return index;
        }
    }

}
