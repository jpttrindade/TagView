package br.com.jpttrindade.tagview;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class TagView extends RecyclerView implements ITagview{

	public static final int ONCLICK_DEFAULT = 0;
	public static final int ONCLICK_EDIT = 1;
	public static final int ONCLICK_REMOVE = 2;

	private OnTagClickListener mOnTagClickListener;

	private TagViewAdapter mAdapter;

	private AttributeSet mAttributeSet;

	private TypedArray typedArray;

	public TagView(Context context) {
		super(context);
		init();
	}

	public TagView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mAttributeSet = attrs;
		init();
	}

	public TagView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mAttributeSet = attrs;
		init();
	}

	private void init() {		
		typedArray = getContext().getTheme().obtainStyledAttributes(mAttributeSet, R.styleable.TagView, 0,0);

		LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
		layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		setLayoutManager(layoutManager);

		setAdapter(new TagViewAdapter(getContext(),typedArray, new OnTagClickListener() {
			@Override
			public void onTagClick(Tag tag, int position, int clickType) {
				switch(clickType){
				case ONCLICK_REMOVE:
					removeTag(tag, position);
					break;
				case ONCLICK_EDIT:
					onEditTag(tag,position);
					break;
				case ONCLICK_DEFAULT:
					//nothing
					break;
				}
			}
		}));		
	}

	private void onEditTag(Tag tag, int position) {
		getLayoutManager().scrollToPosition(position);
		mAdapter.removeTag(tag, position);
		if (mOnTagClickListener != null) {
			mOnTagClickListener.onTagClick(tag, position, TagView.ONCLICK_EDIT);
		}
	}

	@Override
	public boolean addTag(Tag newTag){
		boolean contains = mAdapter.contains(newTag);
		if(!contains){
			int total = getLayoutManager().getItemCount();
			mAdapter.addTag(newTag);
			getLayoutManager().scrollToPosition(total);
		}
		return contains;
	}

	private void removeTag(Tag tag, int position){
		getLayoutManager().scrollToPosition(position);
		mAdapter.removeTag(tag, position);
		if (mOnTagClickListener != null) {
			mOnTagClickListener.onTagClick(tag, position, TagView.ONCLICK_REMOVE);
		}
	}

	@Override
	public ArrayList<Tag> getAll(){
		return mAdapter.getDataSet();
	}

	@Override
	public int getCount(){
		return mAdapter.getItemCount();
	}

	@Override
	public void removeAll() {
		mAdapter.removeAll();
	}

	@Override
	public void remove(Tag tag) {
		mAdapter.removeTag(tag);
	}

	@Override
	public void remove(int position) {
		mAdapter.removeTag(position);
	}

	@Override
	public void setAdapter(Adapter adapter) {
		super.setAdapter(adapter);
		mAdapter = (TagViewAdapter) adapter;
	}

	@Override
	public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
		this.mOnTagClickListener = onTagClickListener;
	}
}
