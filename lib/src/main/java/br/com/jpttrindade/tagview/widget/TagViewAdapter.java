package br.com.jpttrindade.tagview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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

import br.com.jpttrindade.tagview.R;

public class TagViewAdapter extends RecyclerView.Adapter<TagViewAdapter.TagItemViewHolder> implements View.OnClickListener{

	private ArrayList<Tag> mDataset;

	private OnTagClickListener mOnTagClickListener;

	private int mImageId;
	private int mCardViewHeight;
	private int mCardViewMargins;

	private int mImageSize;

	private float mCardViewCornerRadius;

	private Context mContext;

	private DisplayMetrics mDisplayMetrics;

	private int mDividerWidth;
	private int mDividerTopMargin;
	private int mDividerLeftMargin;
	private int mDividerRightMargin;
	private int mDividerBottomMargin;
	private int mDividerMargins;
	
	
	private int mImagePadding;
	private int mImageLeftPadding;
	private int mImageRightPadding;
	private int mImageTopPadding;
	private int mImageBottomPadding;

	private int mTextViewPadding;
	private int mTextViewLeftPadding;
	private int mTextViewTopPadding;
	private int mTextViewRightPadding;
	private int mTextViewBottomPadding;
	private float mTextViewTextSize;
	private int mTextViewTextSizeTypedValue;
	private int mTextViewTextColor;
	
	private int mImageLeftMargin;
	private int mImageRightMargin;
	private int mImageTopMargin;
	private int mImageBottomMargin;
	private int mImageMargins;

	private int mDividerColor;
	
	private int mTextViewDrawablePadding;


	public TagViewAdapter(Context context,TypedArray typedArray, OnTagClickListener onClickListener) {
		this(context, typedArray,new ArrayList<Tag>(), onClickListener);
	}

	public TagViewAdapter(Context context,TypedArray typedArray,ArrayList<Tag> tags, OnTagClickListener onClickListener) {
		mDataset = tags;
		this.mOnTagClickListener = onClickListener;
		mContext = context;
		mDisplayMetrics = mContext.getResources().getDisplayMetrics();
		getAttrs(typedArray);
	}

	private void getAttrs(TypedArray typedArray) {


		try {
			mCardViewHeight = (int) typedArray.getDimension(R.styleable.TagView_tag_height,
					TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,25, mDisplayMetrics));
			
			mCardViewCornerRadius = typedArray.getDimension(R.styleable.TagView_tag_cornerRadius,
					(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, mDisplayMetrics));
			
			
			mCardViewMargins = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.5f, mDisplayMetrics);
			
			mTextViewPadding = (int) typedArray.getDimensionPixelSize(R.styleable.TagView_tag_textPadding,
					(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, mDisplayMetrics));
			mTextViewLeftPadding = (int) typedArray.getDimensionPixelSize(R.styleable.TagView_tag_textLeftPadding,
					mTextViewPadding);
			mTextViewTopPadding = (int) typedArray.getDimensionPixelSize(R.styleable.TagView_tag_textTopPadding,
					mTextViewPadding);
			mTextViewRightPadding = (int) typedArray.getDimensionPixelSize(R.styleable.TagView_tag_textRightPadding,
					mTextViewPadding);
			mTextViewBottomPadding = (int) typedArray.getDimensionPixelSize(R.styleable.TagView_tag_textBottomPadding,
					mTextViewPadding);
			mTextViewDrawablePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, mDisplayMetrics);
			
			mImageId = typedArray.getResourceId(R.styleable.TagView_tag_closeSrc, android.R.drawable.ic_delete);
			mImageSize = typedArray.getDimensionPixelSize(R.styleable.TagView_tag_closeSize, RecyclerView.LayoutParams.MATCH_PARENT);
			
			mImagePadding  = typedArray.getDimensionPixelSize(R.styleable.TagView_tag_closePadding, 
					(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, mDisplayMetrics));
			
			
			mImageLeftPadding= typedArray.getDimensionPixelSize(R.styleable.TagView_tag_closeLeftPadding, mImagePadding);
			mImageRightPadding= typedArray.getDimensionPixelSize(R.styleable.TagView_tag_closeRightPadding,  mImagePadding);
			mImageTopPadding=typedArray.getDimensionPixelSize(R.styleable.TagView_tag_closeTopPadding,  mImagePadding);
			mImageBottomPadding=typedArray.getDimensionPixelSize(R.styleable.TagView_tag_closeBottomPadding,  mImagePadding);
			
			mImageMargins = typedArray.getDimensionPixelSize(R.styleable.TagView_tag_closeMargins, 0);
			mImageLeftMargin= typedArray.getDimensionPixelSize(R.styleable.TagView_tag_closeLeftMargin, mImageMargins);
			mImageRightMargin= typedArray.getDimensionPixelSize(R.styleable.TagView_tag_closeRightMargin,  mImageMargins);
			mImageTopMargin=typedArray.getDimensionPixelSize(R.styleable.TagView_tag_closeTopMargin,  mImageMargins);
			mImageBottomMargin=typedArray.getDimensionPixelSize(R.styleable.TagView_tag_closeBottomMargin,  mImageMargins);
			
			
			mTextViewTextSize = typedArray.getDimensionPixelSize(R.styleable.TagView_tag_textSize,
					(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10, mDisplayMetrics));
			
			mTextViewTextSizeTypedValue = TypedValue.COMPLEX_UNIT_PX;

			mTextViewTextColor = typedArray.getColor(R.styleable.TagView_tag_textColor, Color.BLACK);
			
			mDividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1 , mDisplayMetrics);
			
			mDividerMargins = typedArray.getDimensionPixelSize(R.styleable.TagView_tag_dividerMargins,
					(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0 , mDisplayMetrics));
			mDividerLeftMargin = typedArray.getDimensionPixelSize(R.styleable.TagView_tag_dividerLeftMargin, mDividerMargins);
			mDividerRightMargin = typedArray.getDimensionPixelSize(R.styleable.TagView_tag_dividerRightMargin, mDividerMargins);
			mDividerTopMargin =  typedArray.getDimensionPixelSize(R.styleable.TagView_tag_dividerTopMargin, mDividerMargins);
			mDividerBottomMargin =  typedArray.getDimensionPixelSize(R.styleable.TagView_tag_dividerBottomMargin, mDividerMargins);
			mDividerColor = typedArray.getColor(R.styleable.TagView_tag_dividerColor, Color.LTGRAY);
		
			
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
		Tag tag = mDataset.get(position); 
		holder.mTextView.setText(tag.text);
		holder.mTextView.setTag(tag);
		holder.mImageButton.setTag(tag);
		holder.container.setTag(tag);
		holder.container.setCardBackgroundColor(tag.color);
		holder.mTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, tag.imgID, 0);

	}

	@Override
	public TagItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		LayoutInflater inflater = LayoutInflater.from(mContext);
		View root = inflater.inflate(R.layout.tagview_item, parent, false);
//
//		RelativeLayout rl1 = new RelativeLayout(cardView.getContext());
//		rl1.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT));
//		TextView tv_tag = (TextView) root.findViewById(R.id.tv_tag);
//		tv_tag.setPadding(mTextViewLeftPadding,mTextViewTopPadding,mTextViewRightPadding,mTextViewBottomPadding);
//		tv_tag.setGravity(Gravity.CENTER);
//		tv_tag.setTextColor(mTextViewTextColor);
//		tv_tag.setTextSize(mTextViewTextSizeTypedValue, mTextViewTextSize);
//		tv_tag.setCompoundDrawablePadding(mTextViewDrawablePadding);
//
//		RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.MATCH_PARENT);
//		layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//		layoutParams2.addRule(RelativeLayout.CENTER_VERTICAL);
//		tv_tag.setLayoutParams(layoutParams2);
//
//		View divider = root.findViewById(R.id.divider_tag);
//		RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(mDividerWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
//		layoutParams3.addRule(RelativeLayout.RIGHT_OF, R.id.tv_tag);
//		divider.setLayoutParams(layoutParams3);
//		divider.setBackgroundColor(mDividerColor);
//		divider.setAlpha(Color.alpha(mDividerColor));
//		layoutParams3.setMargins(mDividerLeftMargin, mDividerTopMargin, mDividerRightMargin, mDividerBottomMargin);
//
//
//		ImageView ib_excluir = (ImageView) root.findViewById(R.id.iv_excluir_tag);
//
//		RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(mImageSize, mImageSize);
//		layoutParams4.addRule(RelativeLayout.RIGHT_OF, R.id.divider_tag);
//		layoutParams4.addRule(RelativeLayout.CENTER_VERTICAL);
//		layoutParams4.setMargins(mImageLeftMargin, mImageTopMargin, mImageRightMargin, mImageBottomMargin);
//		ib_excluir.setLayoutParams(layoutParams4);
//		ib_excluir.setImageResource(mImageId);
//		ib_excluir.setPadding(mImageLeftPadding,mImageTopPadding, mImageRightPadding, mImageBottomPadding);
//		ib_excluir.setScaleType(ScaleType.FIT_CENTER);



		TagItemViewHolder vh = new TagItemViewHolder(root);

		return vh;
	}

	public class TagItemViewHolder extends RecyclerView.ViewHolder {
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



			mTextView = (TextView)v.findViewById(R.id.tv_tag);

			mTextView.setPadding(mTextViewLeftPadding,mTextViewTopPadding,mTextViewRightPadding,mTextViewBottomPadding);
			mTextView.setGravity(Gravity.CENTER);
			mTextView.setTextColor(mTextViewTextColor);
			mTextView.setTextSize(mTextViewTextSizeTypedValue, mTextViewTextSize);
			mTextView.setCompoundDrawablePadding(mTextViewDrawablePadding);
			mImageButton = (ImageView) v.findViewById(R.id.iv_excluir_tag);

			//mTextView.setOnClickListener(TagViewAdapter.this);
			mImageButton.setOnClickListener(TagViewAdapter.this);
			container.setOnClickListener(TagViewAdapter.this);

			Tag tag = mDataset.get(position);
			container.setTag(tag);
			mTextView.setTag(tag);
			mImageButton.setTag(tag);
			
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();

		Tag tag = (Tag) v.getTag();
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

	private int getTagPosition(Tag tag) {
		int position;
		Tag t;
		for(int i = 0; i<mDataset.size(); i++){
			t = mDataset.get(i);
			if(tag.equals((Tag)t)){
				position = i;
				return position;
			}
		}
		return -1;
	}

	public void setOnTagClickListener(OnTagClickListener mOnTagClickListener) {
		this.mOnTagClickListener = mOnTagClickListener;
	}

	public void setTagTextSize(int size){
		mTextViewTextSize = size;
		mTextViewTextSizeTypedValue = TypedValue.COMPLEX_UNIT_SP;
	}
	
	public void setTagTextSize(int unit, int size){
		mTextViewTextSize = size;
		mTextViewTextSizeTypedValue = unit;
	}

	public void setTagTextColor(int color) {
		mTextViewTextColor = color;
	}
	
	public OnTagClickListener getOnTagClickListener() {
		return mOnTagClickListener;
	}

	public void addTag(Tag newTag) {
		mDataset.add(newTag);	
		
		notifyItemInserted(getItemCount());

	}


	public int removeTag(Tag tag, int position) {
		mDataset.remove(position);
		notifyItemRemoved(position);
		//	notifyDataSetChanged();
		return mDataset.size();
	}
	
	
	public int removeTag(Tag tag){
		return removeTag(tag,getTagPosition(tag));
	}

	public void onEditTag(Tag tag) {
		// TODO Auto-generated method stub

	}

	public boolean contains(Tag newTag) {
		return mDataset.contains(newTag);
	}

	public ArrayList<Tag> getDataSet() {
		return mDataset;
	}



	





}
