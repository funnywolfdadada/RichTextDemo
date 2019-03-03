package com.example.funnywolf.richtextdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.example.funnywolf.richtextdemo.spans.BaselineImageSpan;
import com.example.funnywolf.richtextdemo.spans.CenterImageSpan;
import com.example.funnywolf.richtextdemo.spans.RealCenterImageSpan;
import com.example.funnywolf.richtextdemo.spans.RealCenterSpanWithEllipsize;
import com.example.funnywolf.richtextdemo.spans.TopImageSpan;

public class MainActivity extends AppCompatActivity {
    private static final String TEXT = "富强、民主、文明、和谐、自由、平等、公正、法治、爱国、敬业、诚信、友善";
    private TextView[][] mTextViews;
    private int mImageId = R.drawable.ic_smail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViews = new TextView[][] {
                {
                        findViewById(R.id.text0),
                        findViewById(R.id.text1),
                        findViewById(R.id.text2),
                },
                {
                        findViewById(R.id.text3),
                        findViewById(R.id.text4),
                        findViewById(R.id.text5),
                },
                {
                        findViewById(R.id.text6),
                        findViewById(R.id.text7),
                        findViewById(R.id.text8),
                },
        };

        SpannableStringBuilder builder = new SpannableStringBuilder(TEXT);
        int index = 2;
        builder.setSpan(new ImageSpan(this, mImageId, DynamicDrawableSpan.ALIGN_BOTTOM),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        index += 3;
        builder.setSpan(new ImageSpan(this, mImageId, DynamicDrawableSpan.ALIGN_BASELINE),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        index += 3;
        builder.setSpan(new TopImageSpan(this, mImageId),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        index += 3;
        builder.setSpan(new CenterImageSpan(this, mImageId),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        index += 3;
        builder.setSpan(new RealCenterImageSpan(this, mImageId),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        for (TextView view: mTextViews[0]) {
            view.setText(builder);
            view.setMaxLines(1);
            view.setPadding(0, 0, 0, 0);
        }

        builder.clearSpans();
        index = 2;
        builder.setSpan(new CenterImageSpan(this, mImageId),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        index += 3;
        builder.setSpan(new BaselineImageSpan(this, mImageId, 1),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        for (TextView view: mTextViews[1]) {
            view.setText(builder);
            view.setMaxLines(1);
            view.setPadding(0, 0, 0, 0);
        }

        builder.clearSpans();
        index = TEXT.indexOf('平');
        for (TextView view: mTextViews[2]) {
            builder.setSpan(new RealCenterSpanWithEllipsize(this, mImageId),
                    index, index++ + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            view.setText(builder);
            view.setMaxLines(1);
            view.setPadding(0, 0, 0, 0);
        }
    }
}
