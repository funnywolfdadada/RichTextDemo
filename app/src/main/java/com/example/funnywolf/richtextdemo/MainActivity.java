package com.example.funnywolf.richtextdemo;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.example.funnywolf.richtextdemo.fragments.ClickableSpanTestFragment;
import com.example.funnywolf.richtextdemo.fragments.ImageSpanTestFragment;
import com.example.funnywolf.richtextdemo.spans.BaselineImageSpan;
import com.example.funnywolf.richtextdemo.spans.CenterImageSpan;
import com.example.funnywolf.richtextdemo.spans.RealCenterImageSpan;
import com.example.funnywolf.richtextdemo.spans.RealCenterSpanWithEllipsize;
import com.example.funnywolf.richtextdemo.spans.TopImageSpan;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                //.add(R.id.fragment_content, new ImageSpanTestFragment())
                .add(R.id.fragment_content, new ClickableSpanTestFragment())
                .commit();
    }
}
