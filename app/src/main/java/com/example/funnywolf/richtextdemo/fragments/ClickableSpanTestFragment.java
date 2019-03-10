package com.example.funnywolf.richtextdemo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        TextView textView = view.findViewById(R.id.text);
        final CharSequence text = textView.getText();
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        for (int i = 0; i < text.length(); i++) {
            final char c = text.charAt(i);
            builder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Log.d(TAG, "onClick: " + c);
                }
            }, i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(builder);
    }


}
