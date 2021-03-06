package br.com.jpttrindade.tagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import java.util.ArrayList;

public class TagView extends RecyclerView implements ITagview{

	public static final int ONCLICK_DEFAULT = 0;
	public static final int ONCLICK_EDIT = 1;
	public static final int ONCLICK_REMOVE = 2;

	static final int GRID = 1;

	private static int mMaxSpan = 500;

	private OnTagClickListener mOnTagClickListener;

	private TagViewAdapter mAdapter;

	private AttributeSet mAttributeSet;

	private TypedArray typedArray;

	private int mGridSpans;
	private TextView mTextView;
	private float mDensity;


	int layoutType;

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
		if (!isInEditMode()) {
			mDensity = getResources().getDisplayMetrics().density;

			typedArray = getContext().getTheme().obtainStyledAttributes(mAttributeSet, R.styleable.TagView, 0, 0);

			layoutType = typedArray.getInt(R.styleable.TagView_layout_type, 0);
			mGridSpans = typedArray.getInt(R.styleable.TagView_grid_spans, 1);

			setLayoutManager();

			mAdapter = new TagViewAdapter(getContext(), this, typedArray, new OnTagClickListener() {
				@Override
				public boolean onTagClick(DefaultTag tag, int position, int clickType) {

					if (mOnTagClickListener != null) {
						if (mOnTagClickListener.onTagClick(tag, position, clickType)) {
							// nothing
						} else {
							switch (clickType) {
								case ONCLICK_REMOVE:
									removeTag(tag, position);
									break;
								case ONCLICK_EDIT:
									onEditTag(tag, position);
									break;
								case ONCLICK_DEFAULT:
									//nothing
									break;
							}
						}
					}
					return true;
				}
			});
			setAdapter(mAdapter);

			mTextView = new TextView(getContext());
			mTextView.setTypeface(null, Typeface.BOLD);
			mTextView.setGravity(Gravity.CENTER);
			mTextView.setTextSize(mAdapter.mTextViewTextSizeTypedValue, mAdapter.mTextViewTextSize);
			mTextView.setPadding(mAdapter.mTextViewLeftPadding, 0, mAdapter.mTextViewRightPadding, 0);
		}
	}

	@Override
	protected void onMeasure(int widthSpec, int heightSpec) {
		super.onMeasure(widthSpec, heightSpec);
		if (layoutType == GRID) {
			mMaxSpan = (int) (getMeasuredWidth() / getResources().getDisplayMetrics().density);
			if (mMaxSpan == 0) {
				mMaxSpan = 1;
			}
			((GridLayoutManager) getLayoutManager()).setSpanCount(mMaxSpan);
		}
	}

	private void setLayoutManager() {

		final LayoutManager layoutManager;
		switch (layoutType) {
			case GRID:
				layoutManager = new GridLayoutManager(getContext(), mMaxSpan);

				((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
					@Override
					public int getSpanSize(int position) {
						String text = mAdapter.getTag(position).text;
						mTextView.setText(text);
						Rect bounds = new Rect();
						TextPaint textPaint = mTextView.getPaint();
						textPaint.getTextBounds(text, 0, text.length(), bounds);
						int textViewWidth = bounds.width();
						int itemSpan = (int)(textViewWidth/mDensity)+ 2/*leftPadding*/ + 2/*rightPadding*/+18 /*closeImagemWidth*/ + 18 /*margins*/  ;

						int spansPerItem = mMaxSpan/mGridSpans;

						if (mGridSpans == 1) {
							itemSpan =  itemSpan>mMaxSpan? mMaxSpan: itemSpan;
						} else {

							if (mAdapter.getItemCount() == 1) {
								if (itemSpan <= spansPerItem) {
									itemSpan = spansPerItem;
								} else {
									itemSpan =  itemSpan>mMaxSpan? mMaxSpan: itemSpan;
								}
							} else {
								itemSpan = spansPerItem;
							}
						}

						return itemSpan;
					}
				});

				break;
			default:
				layoutManager = new LinearLayoutManager(getContext());
				((LinearLayoutManager)layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);


		}

		setLayoutManager(layoutManager);
	}


	private void onEditTag(DefaultTag tag, int position) {
		getLayoutManager().scrollToPosition(position);
		mAdapter.removeTag(tag, position);
//		if (mOnTagClickListener != null) {
//			mOnTagClickListener.onTagClick(tag, position, TagView.ONCLICK_EDIT);
//		}
	}

	@Override
	public boolean addTag(DefaultTag newTag){
		boolean contains = mAdapter.contains(newTag);
		if(!contains){
			int total = getLayoutManager().getItemCount();
			mAdapter.addTag(newTag);
			getLayoutManager().scrollToPosition(total);
		}
		return contains;
	}

	private void removeTag(DefaultTag tag, int position){
		getLayoutManager().scrollToPosition(position);
		mAdapter.removeTag(tag, position);
//		if (mOnTagClickListener != null) {
//			mOnTagClickListener.onTagClick(tag, position, TagView.ONCLICK_REMOVE);
//		}
	}

	@Override
	public ArrayList<DefaultTag> getAll(){
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
	public void remove(DefaultTag tag) {
		mAdapter.removeTag(tag);
	}

	@Override
	public void remove(int position) {
		mAdapter.removeTag(position);
	}

	@Override
	public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
		this.mOnTagClickListener = onTagClickListener;
	}

}
