package br.com.jpttrindade.tagview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by JP on 05/12/2016.
 */

public interface ITagview {
    ArrayList<Tag> getAll();
    boolean addTag(Tag newTAg);
    void setOnTagClickListener(OnTagClickListener onTagClickListener);
    void setTagSpanSizeLookup(TagView.TagSpanSizeLookup spanSizeLookup);

    int getCount();
    void removeAll();
    void remove(Tag tag);
    void remove(int position);
}
