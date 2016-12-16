package br.com.jpttrindade.tagview;

import java.util.ArrayList;

/**
 * Created by JP on 05/12/2016.
 */

public interface ITagview {
    ArrayList<DefaultTag> getAll();
    boolean addTag(DefaultTag newTAg);
    void setOnTagClickListener(OnTagClickListener onTagClickListener);

    int getCount();
    void removeAll();
    void remove(DefaultTag tag);
    void remove(int position);
}
