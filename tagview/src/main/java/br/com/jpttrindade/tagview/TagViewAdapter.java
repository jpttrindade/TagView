package br.com.jpttrindade.tagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TagViewAdapter extends RecyclerView.Adapter<TagViewAdapter.TagItemViewHolder> implements View.OnClickListener{

	private final TagView mTagView;
	private ArrayList<DefaultTag> mDataset;

	private OnTagClickListener mOnTagClickListener;

	private int mImageId;
	private int mCardViewHeight;
	private int mCardViewMargins;
	private float mCardViewCornerRadius;
	private Context mContext;
	private DisplayMetrics mDisplayMetrics;
	private boolean mTextViewBold;
	private int mTextViewPadding;
	int mTextViewLeftPadding;
	private int mTextViewTopPadding;
	int mTextViewRightPadding;
	private int mTextViewBottomPadding;
	float mTextViewTextSize;
	int mTextViewTextSizeTypedValue;
	private int mTextViewTextColor;


	public TagViewAdapter(Context context, TagView tagView,TypedArray typedArray, OnTagClickListener onClickListener) {
		this(context, tagView, typedArray,new ArrayList<DefaultTag>(), onClickListener);
	}

	public TagViewAdapter(Context context, TagView tagView, TypedArray typedArray, ArrayList<DefaultTag> tags, OnTagClickListener onClickListener) {
		mDataset = tags;
		this.mOnTagClickListener = onClickListener;
		mContext = context;
		mTagView = tagView;
		mDisplayMetrics = mContext.getResources().getDisplayMetrics();
		getAttrs(typedArray);
	}

	private void getAttrs(TypedArray typedArray) {


		try {
			mCardViewHeight = (int) typedArray.getDimension(R.styleable.TagView_tag_height,
					TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,LinearLayout.LayoutParams.WRAP_CONTENT, mDisplayMetrics));
			mCardViewCornerRadius = typedArray.getDimension(R.styleable.TagView_tag_cornerRadius,
					(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mDisplayMetrics));
			mCardViewMargins = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, mDisplayMetrics);


			mTextViewBold = typedArray.getBoolean(R.styleable.TagView_tag_textBold, true);
			mTextViewPadding = typedArray.getDimensionPixelSize(R.styleable.TagView_tag_textPadding,
					(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, mDisplayMetrics));
			mTextViewLeftPadding = typedArray.getDimensionPixelSize(R.styleable.TagView_tag_textLeftPadding,
					mTextViewPadding + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, mDisplayMetrics));
			mTextViewTopPadding = typedArray.getDimensionPixelSize(R.styleable.TagView_tag_textTopPadding,
					mTextViewPadding);
			mTextViewRightPadding =  typedArray.getDimensionPixelSize(R.styleable.TagView_tag_textRightPadding,
					mTextViewPadding);
			mTextViewBottomPadding = typedArray.getDimensionPixelSize(R.styleable.TagView_tag_textBottomPadding,
					mTextViewPadding);
			mTextViewTextSize = typedArray.getDimensionPixelSize(R.styleable.TagView_tag_textSize,
					(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, mDisplayMetrics));
			mTextViewTextSizeTypedValue = TypedValue.COMPLEX_UNIT_PX;
			mTextViewTextColor = typedArray.getColor(R.styleable.TagView_tag_textColor, Color.WHITE);

			mImageId = typedArray.getResourceId(R.styleable.TagView_tag_closeSrc, R.drawable.ic_close_circle_white_48dp);


		} finally {
			typedArray.recycle();
		}

	}

	@Override
	public int getItemCount() {
		return mDataset.size();
	}

	@Override
	public void onBindViewHolder(TagItemViewHolder holder, int position) {
		DefaultTag tag = mDataset.get(position);
		holder.mTextView.setText(tag.text);
		holder.mTextView.setTag(tag);
		if (tag instanceof SimpleTag) {
			holder.mImageButton.setVisibility(View.GONE);
		} else {
			holder.mImageButton.setTag(tag);
			holder.mImageButton.setVisibility(View.VISIBLE);
		}
		holder.container.setTag(tag);
		holder.container.setCardBackgroundColor(tag.color);
		//holder.mTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, tag.imgID, 0);
	}

	@Override
	public TagItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		LayoutInflater inflater = LayoutInflater.from(mContext);

		View root;

		if (mTagView.layoutType == TagView.GRID) {
			root = inflater.inflate(R.layout.tagview_item_grid, parent, false);
		} else {
			root = inflater.inflate(R.layout.tagview_item, parent, false);
		}


		TagItemViewHolder vh = new TagItemViewHolder(root);

		return vh;
	}

	public void removeAll() {
		mDataset.clear();
		notifyDataSetChanged();
	}

	public void removeTag(DefaultTag tag) {
		mDataset.remove(tag);
		notifyDataSetChanged();
	}

	public void removeTag(int position) {
		mDataset.remove(position);
		notifyItemRemoved(position);
	}

	public DefaultTag getTag(int position) {
		return mDataset.get(position);
	}

	public class TagItemViewHolder extends RecyclerView.ViewHolder {
		//private View divider_tag;
		public TextView mTextView;
		public ImageView mImageButton;
		public int position;
		public CardView container;

		public TagItemViewHolder(View v) {
			super(v);
			container =  (CardView) v.findViewById(R.id.card_view);

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,mCardViewHeight);
			layoutParams.setMargins(mCardViewMargins, 0, mCardViewMargins, 0);
			container.setLayoutParams(layoutParams);
			container.setRadius(mCardViewCornerRadius);
			container.setPreventCornerOverlap(false);

//			divider_tag = v.findViewById(R.id.divider_tag);
//			divider_tag.setBackgroundColor(mDividerColor);
//			divider_tag.setAlpha(Color.alpha(mDividerColor));

			mTextView = (TextView)v.findViewById(R.id.tv_tag);
			//mTextView.setMaxWidth(mTextViewMaxWidth);

			if (mTextViewBold) mTextView.setTypeface(null, Typeface.BOLD);
			mTextView.setPadding(mTextViewLeftPadding,mTextViewTopPadding,mTextViewRightPadding,mTextViewBottomPadding);
			mTextView.setGravity(Gravity.CENTER);
			mTextView.setTextColor(mTextViewTextColor);
			mTextView.setTextSize(mTextViewTextSizeTypedValue, mTextViewTextSize);
			mImageButton = (ImageView) v.findViewById(R.id.iv_excluir_tag);

			mImageButton.setImageResource(mImageId);


			//mTextView.setOnClickListener(TagViewAdapter.this);
			mImageButton.setOnClickListener(TagViewAdapter.this);
			container.setOnClickListener(TagViewAdapter.this);

			DefaultTag tag = mDataset.get(position);
			container.setTag(tag);
			mTextView.setTag(tag);
			mImageButton.setTag(tag);

		}
	}

	@Override
	public void onClick(View v) {
		DefaultTag tag = (DefaultTag) v.getTag();
		int position = getTagPosition(tag);

		if(v instanceof ImageView){
			mOnTagClickListener.onTagClick(tag, position, TagView.ONCLICK_REMOVE);
		} else{
			if (tag.editable) {
				mOnTagClickListener.onTagClick(tag, position, TagView.ONCLICK_EDIT);
			} else {
				mOnTagClickListener.onTagClick(tag, position, TagView.ONCLICK_REMOVE);
			}
		}
	}

	private int getTagPosition(DefaultTag tag) {
		int position;
		DefaultTag t;
		for(int i = 0; i<mDataset.size(); i++){
			t = mDataset.get(i);
			if(tag.equals((DefaultTag)t)){
				position = i;
				return position;
			}
		}
		return -1;
	}


	public void addTag(DefaultTag newTag) {
		mDataset.add(newTag);

		notifyItemInserted(getItemCount());

	}

	public int removeTag(DefaultTag tag, int position) {
		mDataset.remove(position);
		notifyItemRemoved(position);
		//	notifyDataSetChanged();
		return mDataset.size();
	}

	public boolean contains(DefaultTag newTag) {
		return mDataset.contains(newTag);
	}

	public ArrayList<DefaultTag> getDataSet() {
		return mDataset;
	}

}
