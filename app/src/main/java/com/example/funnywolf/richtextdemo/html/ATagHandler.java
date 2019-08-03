package com.example.funnywolf.richtextdemo.html;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Toast;

import org.xml.sax.XMLReader;

public class ATagHandler implements TagHandler {
    private static final String A_PATTERN = "<\\s*[aA] (.*)>(.+)<\\s*/[aA]\\s*>";
    private static final String SELF_DEF_A_TAG = "zdl_a";
    private static final String A_REPLACE = "<" + SELF_DEF_A_TAG +" $1>$2</" + SELF_DEF_A_TAG + ">";

    @Override
    public String preProcess(String text) {
        return text.replaceAll(A_PATTERN, A_REPLACE);
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if(!SELF_DEF_A_TAG.equalsIgnoreCase(tag)) { return; }
        if (opening) {
            AttributeSpan span = new AttributeSpan();
            span.start = output.length();
            TagHandler.getAttribute(span.attrs, xmlReader);
            output.setSpan(span, span.start, span.start, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            AttributeSpan span = TagHandler.getLast(output, AttributeSpan.class);
            if (span != null) {
                span.end = output.length();
                String clazz = span.attrs.get("class");
                if (clazz == null) { clazz = ""; }
                Object realSpan;
                switch (clazz) {
                    case ToastSpan.CLASS:
                        realSpan = new ToastSpan(span);
                        break;
                    default:
                        realSpan = new URLSpan(span.attrs.get("href"));
                        break;
                }
                output.setSpan(realSpan, span.start, span.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    public static class ToastSpan extends ClickableSpan {
        public static final String CLASS = "toast";
        private final AttributeSpan attributeSpan;

        public ToastSpan(AttributeSpan attributeSpan) {
            this.attributeSpan = attributeSpan;
        }

        @Override
        public void onClick(@NonNull View widget) {
            StringBuilder builder = new StringBuilder();
            for (String key: attributeSpan.attrs.keySet()) {
                if (builder.length() > 0) {
                    builder.append(", ");
                }
                builder.append(key);
                builder.append(": ");
                builder.append(attributeSpan.attrs.get(key));
            }
            Toast.makeText(widget.getContext(), builder, Toast.LENGTH_SHORT).show();
        }
    }

}
