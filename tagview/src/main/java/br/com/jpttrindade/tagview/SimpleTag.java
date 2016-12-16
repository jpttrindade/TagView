package br.com.jpttrindade.tagview;

/**
 * Created by jpttrindade on 16/12/16.
 */

public class SimpleTag extends DefaultTag {
    public SimpleTag(String text, int color) {
        this(text, color, false);
    }

    public SimpleTag(String text, int color, boolean editable) {
        super(text, color, editable);
    }
}
