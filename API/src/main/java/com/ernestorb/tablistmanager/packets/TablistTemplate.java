package com.ernestorb.tablistmanager.packets;

import java.util.List;

/**
 * Defines the header and the footer shown in the tablist.
 */
public class TablistTemplate {

    private String header = "";
    private String footer = "";
    private final PlaceholderCallback placeholderCallback;


    public TablistTemplate(PlaceholderCallback placeholderCallback) {
        this.placeholderCallback = placeholderCallback;
    }

    public void setHeader(List<String> stringList) {
        this.header = reduce(stringList);
    }

    public void setFooter(List<String> stringList) {
        this.footer = reduce(stringList);
    }

    public void setHeader(String newHeader) {
        this.header = newHeader;
    }

    public void setFooter(String newFooter) {
        this.footer = newFooter;
    }

    public String getFooter() {
        return footer;
    }

    public String getHeader() {
        return header;
    }

    public String appendFooter(String toAppend) {
        this.footer += toAppend;
        return this.footer;
    }

    public String appendHeader(String toAppend) {
        this.header += toAppend;
        return this.header;
    }

    public String replaceFooter(String pattern, String value) {
        this.footer = this.footer.replaceAll(pattern, value);
        return this.footer;
    }

    public String replaceHeader(String pattern, String value) {
        this.header = this.header.replaceAll(pattern, value);
        return this.header;
    }

    public void replace(String pattern, String value) {
        replaceFooter(pattern, value);
        replaceHeader(pattern, value);
    }

    private String reduce(List<String> stringList) {
        return stringList.stream().reduce((acum, actual) -> acum + "\n" + actual).orElse("");
    }

    public static TablistTemplate fromLists(List<String> header, List<String> footer) {
        var template = new TablistTemplate((_a, _b) -> {
        });
        template.setHeader(header);
        template.setFooter(footer);
        return template;
    }

    public static TablistTemplate empty() {
        return new TablistTemplate((_a, _b) -> {
        });
    }

    PlaceholderCallback getPlaceholderCallback() {
        return placeholderCallback;
    }
}
