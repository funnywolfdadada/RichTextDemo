package com.example.funnywolf.richtextdemo.html;

import android.text.Editable;

import org.xml.sax.XMLReader;

public class LineBreakTagHandler implements TagHandler {
    @Override
    public String preProcess(String text) {
        return text.replaceAll("\n", "<br>");
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

    }
}
