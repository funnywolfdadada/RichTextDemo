package com.example.funnywolf.richtextdemo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.funnywolf.richtextdemo.R;
import com.example.funnywolf.richtextdemo.html.HtmlUtil;

public class HtmlTagHandlerFragment extends Fragment {
    private TextView textView;
    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.html_tag_handler_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.text);
        textView.setOnTouchListener(new ClickableSpanTestFragment.ClickableSpanTouchListener());
        editText = view.findViewById(R.id.edit);
        editText.setText("link: <a href=\"https://jandan.net\">jiandan</a>\n" +
                "toast: <a class=\"toast\" href=\"https://jandan.net\">jiandan</a>");
        view.findViewById(R.id.show).setOnClickListener(v -> updateText());
    }

    private void updateText() {
        textView.setText(parseRichText(editText.getText().toString()));
    }

    private CharSequence parseRichText(String text) {
        return HtmlUtil.fromHtml(text);
    }

}
