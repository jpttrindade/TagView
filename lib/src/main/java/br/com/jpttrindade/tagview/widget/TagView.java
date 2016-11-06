package br.com.jpttrindade.tagview.widget;

import java.util.ArrayList;

import br.com.jpttrindade.tagview.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TagView extends RecyclerView{

	protected static final long CLICK_DURATION_MS = 200;
	protected static final int SCROLL_THRESHOLD = 100;
	public static final int ONCLICK_DEFAULT = 0;
	public static final int ONCLICK_EDIT = 1;
	public static final int ONCLICK_REMOVE = 2;

	protected boolean isOnClick;
	protected float mDownY;
	protected float mDownX;

	private OnTagClickListener mOnTagClickListener;
	private OnTagEditListener mOnTagEditListener;

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

		mOnTagEditListener = new OnTagEditListener() {
			@Override
			public void onTagEdit(Tag tag, int position) {
				removeTag(tag, position);
			}
		};

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


	public boolean addTag(Tag newTag){

		boolean contains = mAdapter.contains(newTag);

		if(!contains){
			int total = getLayoutManager().getItemCount();
			mAdapter.addTag(newTag);
			((LinearLayoutManager)getLayoutManager()).scrollToPosition(total);
		}
		
		return contains;
	}

	public ArrayList<Tag> getAll(){
			
		return mAdapter.getDataSet();
	}

	public int removeTag(Tag tag, int position){
		getLayoutManager().scrollToPosition(position);
		return mAdapter.removeTag(tag, position);	
	}

	public int removeTag(Tag tag){
		return mAdapter.removeTag(tag);

	}

	public int getCount(){
		return mAdapter.getItemCount();
	}

	public void onEditTag(Tag tag, int position){
		mOnTagEditListener.onTagEdit(tag, position);
	}


	@Override
	public void setAdapter(Adapter adapter) {
		super.setAdapter(adapter);

		mAdapter = (TagViewAdapter) adapter;

		mOnTagClickListener = mAdapter.getOnTagClickListener();
		setOnTagClickListener(mOnTagClickListener);

	}

	public void setOnTagEditListener(OnTagEditListener mOnTagEditListener) {
		this.mOnTagEditListener = mOnTagEditListener;
	}

	public void setOnTagClickListener(OnTagClickListener onTagClickListener){
		this.mOnTagClickListener = onTagClickListener;

//		super.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				//Log.d("DEBUG",""+ event.getAction());
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					mDownX = event.getX();
//					mDownY = event.getY();
//					isOnClick = true;
//					break;
//
//				case MotionEvent.ACTION_UP:
//					if (isOnClick) {
//						long clickDuration = event.getEventTime() - event.getDownTime();
//						v.performClick();
//
//						if(clickDuration < CLICK_DURATION_MS) {
//							TagView.this.mOnTagClickListener.onTagClick((Tag)v.getTag(), -1, TagView.ONCLICK_DEFAULT);
//						}
//
//					}
//					break;
//
//				case MotionEvent.ACTION_MOVE:
//					if (isOnClick && (Math.abs(mDownX - event.getX()) > SCROLL_THRESHOLD  || Math.abs(mDownY - event.getY()) > SCROLL_THRESHOLD)) {
//						isOnClick = false;
//					}
//					break;
//				}
//
//				return false;
//			}
//		});

		mAdapter.setOnTagClickListener(mOnTagClickListener);
	}


	public AttributeSet getAttributeSet() {
		return mAttributeSet;
	}


	public void setTagTextSize(int size){
		mAdapter.setTagTextSize(size);
	}

	public void setTagTextSize(int unit, int size){
		mAdapter.setTagTextSize(unit, size);
	}

	public void setTagTextColor(int color){
		mAdapter.setTagTextColor(color);
	}

	@Deprecated
	@Override
	public void setOnTouchListener(OnTouchListener l) {
		//invalid
		throw new UnsupportedOperationException("not supported");
	}



}
