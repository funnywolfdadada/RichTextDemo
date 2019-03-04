package com.example.funnywolf.richtextdemo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.funnywolf.richtextdemo.R;
import com.example.funnywolf.richtextdemo.spans.BaselineImageSpan;
import com.example.funnywolf.richtextdemo.spans.CenterImageSpan;
import com.example.funnywolf.richtextdemo.spans.RealCenterImageSpan;
import com.example.funnywolf.richtextdemo.spans.RealCenterSpanWithEllipsize;
import com.example.funnywolf.richtextdemo.spans.TopImageSpan;

public class ImageSpanTestFragment extends Fragment {
    private static final String TEXT = "富强、民主、文明、和谐、自由、平等、公正、法治、爱国、敬业、诚信、友善";
    private TextView[][] mTextViews;
    private int mImageId = R.drawable.ic_smail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image_span_test_layout, null);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Context context;
        if ((context = getContext()) == null) {
            return;
        }

        mTextViews = new TextView[][] {
                {
                        v.findViewById(R.id.text0),
                        v.findViewById(R.id.text1),
                        v.findViewById(R.id.text2),
                },
                {
                        v.findViewById(R.id.text3),
                        v.findViewById(R.id.text4),
                        v.findViewById(R.id.text5),
                },
                {
                        v.findViewById(R.id.text6),
                        v.findViewById(R.id.text7),
                        v.findViewById(R.id.text8),
                },
        };

        v.findViewById(R.id.draw_test).setVisibility(View.VISIBLE);
        v.findViewById(R.id.baseline_test).setVisibility(View.VISIBLE);
        v.findViewById(R.id.ellipsize_test).setVisibility(View.VISIBLE);

        SpannableStringBuilder builder = new SpannableStringBuilder(TEXT);
        int index = 2;
        builder.setSpan(new ImageSpan(context, mImageId, DynamicDrawableSpan.ALIGN_BOTTOM),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        index += 3;
        builder.setSpan(new ImageSpan(context, mImageId, DynamicDrawableSpan.ALIGN_BASELINE),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        index += 3;
        builder.setSpan(new TopImageSpan(context, mImageId),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        index += 3;
        builder.setSpan(new CenterImageSpan(context, mImageId),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        index += 3;
        builder.setSpan(new RealCenterImageSpan(context, mImageId),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        for (TextView view: mTextViews[0]) {
            view.setText(builder);
            view.setMaxLines(1);
            view.setPadding(0, 0, 0, 0);
        }

        builder.clearSpans();
        index = 2;
        builder.setSpan(new CenterImageSpan(context, mImageId),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        index += 3;
        builder.setSpan(new BaselineImageSpan(context, mImageId, 1),
                index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        for (TextView view: mTextViews[1]) {
            view.setText(builder);
            view.setMaxLines(1);
            view.setPadding(0, 0, 0, 0);
        }

        builder.clearSpans();
        index = TEXT.indexOf('平');
        for (TextView view: mTextViews[2]) {
            builder.setSpan(new RealCenterSpanWithEllipsize(context, mImageId),
                    index, index++ + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            view.setText(builder);
            view.setMaxLines(1);
            view.setPadding(0, 0, 0, 0);
        }
    }
}
