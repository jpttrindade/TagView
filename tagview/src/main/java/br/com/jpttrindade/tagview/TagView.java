package br.com.jpttrindade.tagview;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class TagView extends RecyclerView implements ITagview{

	public static final int ONCLICK_DEFAULT = 0;
	public static final int ONCLICK_EDIT = 1;
	public static final int ONCLICK_REMOVE = 2;
	private static final int GRID = 1;

	private OnTagClickListener mOnTagClickListener;

	private TagViewAdapter mAdapter;

	private AttributeSet mAttributeSet;

	private TypedArray typedArray;
	private int mLayoutType;
	private int mGridSpans;
	private TagSpanSizeLookup mTagSpanSizeLookup;

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

		mLayoutType = typedArray.getInt(R.styleable.TagView_layout_type, 0);
		mGridSpans = typedArray.getInt(R.styleable.TagView_grid_spans, 3);
		mTagSpanSizeLookup = new TagSpanSizeLookup() {
			@Override
			public int getSpanSize(int textSize, int position, int spans) {
				return 1;
			}
		};

		setLayoutManager();

		setAdapter(new TagViewAdapter(getContext(), typedArray, new OnTagClickListener() {
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


	private void setLayoutManager() {

		LayoutManager layoutManager;
		switch (mLayoutType) {
			case GRID:
				layoutManager = new GridLayoutManager(getContext(), mGridSpans);

				((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
					@Override
					public int getSpanSize(int position) {
						int lenght = mAdapter.getTag(position).text.length();

						return mTagSpanSizeLookup.getSpanSize(lenght, position, mGridSpans);
					}
				});

				break;
			default:
				layoutManager = new LinearLayoutManager(getContext());
				((LinearLayoutManager)layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);

		}

		setLayoutManager(layoutManager);
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

	@Override
	public void setTagSpanSizeLookup(final TagSpanSizeLookup spanSizeLookup) {

		mTagSpanSizeLookup = spanSizeLookup;
	}

	public interface TagSpanSizeLookup {
		int getSpanSize(int textSize, int position, int spans);
	}
}
