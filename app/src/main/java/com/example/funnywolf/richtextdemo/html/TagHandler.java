package com.example.funnywolf.richtextdemo.html;

import android.text.Html;
import android.text.Spanned;

import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.HashMap;

public interface TagHandler extends Html.TagHandler {
    String preProcess(String text);

    static <T> T getLast(Spanned text, Class<T> kind) {
        /*
         * This knows that the last returned object from getSpans()
         * will be the most recently added.
         */
        T[] objs = text.getSpans(0, text.length(), kind);

        if (objs.length == 0) {
            return null;
        } else {
            return objs[objs.length - 1];
        }
    }

    static void getAttribute(HashMap<String, String> dest, XMLReader xmlReader) {
        try {
            Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
            elementField.setAccessible(true);
            Object element = elementField.get(xmlReader);
            Field attsField = element.getClass().getDeclaredField("theAtts");
            attsField.setAccessible(true);
            Object atts = attsField.get(element);
            Field dataField = atts.getClass().getDeclaredField("data");
            dataField.setAccessible(true);
            String[] data = (String[])dataField.get(atts);
            Field lengthField = atts.getClass().getDeclaredField("length");
            lengthField.setAccessible(true);
            int len = (Integer)lengthField.get(atts);

            for(int i = 0; i < len; i++) {
                dest.put(data[i * 5 + 1], data[i * 5 + 4]);
            }
        } catch (Exception e) {
        }
    }

    class AttributeSpan {
        public HashMap<String, String> attrs = new HashMap<>();
        public int start;
        public int end;
    }

}
