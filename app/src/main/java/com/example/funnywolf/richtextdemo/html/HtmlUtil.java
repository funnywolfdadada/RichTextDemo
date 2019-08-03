package com.example.funnywolf.richtextdemo.html;

import android.text.Editable;
import android.text.Html;
import android.text.Spanned;

import org.xml.sax.XMLReader;

public class HtmlUtil {
    private static final TagHandler[] TAG_HANDLERS = {
            new ATagHandler(),
            new LineBreakTagHandler(),
    };

    private static TagHandler TAG_HANDLER = new TagHandler() {
        @Override
        public String preProcess(String text) {
            for (TagHandler handler : TAG_HANDLERS) {
                text = handler.preProcess(text);
            }
            return text;
        }

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            for (TagHandler handler : TAG_HANDLERS) {
                handler.handleTag(opening, tag, output, xmlReader);
            }
        }
    };

    public static Spanned fromHtml(String html) {
        return Html.fromHtml(TAG_HANDLER.preProcess(html), null, TAG_HANDLER);
    }

}
