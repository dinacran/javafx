package com.newapp.services;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighlightService  {
        private static final String[] KEYWORDS = new String[] {
            "true", "false", "null"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String STRING_PATTERN = "\"([^\"\\\\]*(\\\\.[^\"\\\\]*)*)\"";
    private static final String NUMBER_PATTERN = "\\b\\d+(\\.\\d+)?([eE][+-]?\\d+)?\\b";
    private static final String BOOLEAN_PATTERN = KEYWORD_PATTERN;
    private static final String NULL_PATTERN = KEYWORD_PATTERN;   
    private static final String BRACE_PATTERN = "[\\{\\}\\[\\]]";
    private static final String COMMA_PATTERN = ",";
    private static final String COLON_PATTERN = ":";
    private static final String JSONKEYSTRING = "\"([^\"]|\\.)*\"\\s*:";


    private static Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<JSONKEYSTRING>" + JSONKEYSTRING + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<NUMBER>" + NUMBER_PATTERN + ")"
                    + "|(?<BOOLEAN>" + BOOLEAN_PATTERN + ")"
                    + "|(?<NULL>" + NULL_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<COMMA>" + COMMA_PATTERN + ")"
                    + "|(?<COLON>" + COLON_PATTERN + ")"
    );

    public static  StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        StyleSpansBuilder<Collection<String>> builder = new StyleSpansBuilder<>();
        int lastKwEnd = 0;
        while (matcher.find()) {
            String styleClass =
                matcher.group("JSONKEYSTRING") != null ? "jsonKey" :
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("STRING") != null ? "string" :
                                    matcher.group("NUMBER") != null ? "number" :
                                            matcher.group("BOOLEAN") != null ? "boolean" :
                                                    matcher.group("NULL") != null ? "null" :
                                                            matcher.group("BRACE") != null ? "brace" :
                                                                    matcher.group("COMMA") != null ? "comma" :
                                                                            matcher.group("COLON") != null ? "colon" : null;

            assert styleClass != null;

            builder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            builder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        builder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return builder.create();
    }

    public static  StyleSpans<Collection<String>> highlightSearchText(String content, String searchText) {

        String escapedText = Pattern.quote(searchText);
        String SEARCHTEXT = "\"(" + escapedText + ")\"";

        Pattern SEARCHPATTTERN = Pattern.compile(
                "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                        + "|(?<SEARCHTEXT>" + SEARCHTEXT + ")"
                        + "|(?<JSONKEYSTRING>" + JSONKEYSTRING + ")"
                        + "|(?<STRING>" + STRING_PATTERN + ")"
                        + "|(?<NUMBER>" + NUMBER_PATTERN + ")"
                        + "|(?<BOOLEAN>" + BOOLEAN_PATTERN + ")"
                        + "|(?<NULL>" + NULL_PATTERN + ")"
                        + "|(?<BRACE>" + BRACE_PATTERN + ")"
                        + "|(?<COMMA>" + COMMA_PATTERN + ")"
                        + "|(?<COLON>" + COLON_PATTERN + ")",
                        Pattern.CASE_INSENSITIVE
        );

        Matcher matcher = SEARCHPATTTERN.matcher(content);
        StyleSpansBuilder<Collection<String>> builder = new StyleSpansBuilder<>();
        int lastKwEnd = 0;
        while (matcher.find()) {
            String styleClass =
                        matcher.group("JSONKEYSTRING") != null ? "jsonKey" :
                                matcher.group("KEYWORD") != null ? "keyword" :
                                        matcher.group("STRING") != null ? "string" :
                                                matcher.group("NUMBER") != null ? "number" :
                                                        matcher.group("BOOLEAN") != null ? "boolean" :
                                                                matcher.group("NULL") != null ? "null" :
                                                                        matcher.group("BRACE") != null ? "brace" :
                                                                                matcher.group("COMMA") != null ? "comma" :
                                                                                        matcher.group("COLON") != null ? "colon" :
                                                                                                matcher.group("SEARCHTEXT") != null ? "highlight" :
                                                                                        null;

            assert styleClass != null;

            builder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            builder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        builder.add(Collections.emptyList(), content.length() - lastKwEnd);
        return builder.create();
    }

}
