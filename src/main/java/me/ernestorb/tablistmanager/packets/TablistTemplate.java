package me.ernestorb.tablistmanager.packets;

public class TablistTemplate {

    private String header="";
    private String footer="";
    private PlaceholderCallback placeholderCallback;


    public TablistTemplate(PlaceholderCallback placeholderCallback) {
        this.placeholderCallback = placeholderCallback;
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

    PlaceholderCallback getPlaceholderCallback() {
        return placeholderCallback;
    }
}
