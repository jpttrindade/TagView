package br.com.jpttrindade.tagview;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements Parcelable {
	
	public int color;
	public String text;
	public boolean editable;
	public int type;
	public int id;
	public int imgID;

	public Tag(String text, int color) {
		this(text, color, false);
	}

	public Tag (String text, int color, boolean editable) {
		this(text, color, 0, editable);
	}

	public Tag(String text, int color, int imgID ,boolean editable) {
		this.color = color;
		this.text = text;
		this.imgID = imgID;
		this.editable = editable;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.color);
		dest.writeString(this.text);
		dest.writeInt(this.imgID);
		dest.writeByte(editable ? (byte) 1 : (byte) 0);
	}

	protected Tag(Parcel in) {
		this.color = in.readInt();
		this.text = in.readString();
		this.imgID = in.readInt();
		this.editable = in.readByte() != 0;
	}

	public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>() {
		public Tag createFromParcel(Parcel source) {
			return new Tag(source);
		}

		public Tag[] newArray(int size) {
			return new Tag[size];
		}
	};

	public boolean equals(Tag t) {
		if(t.type==type && t.text.equals(text)){
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tag) {
			Tag tag = (Tag) obj;
			return tag.equals(this);
		}
		return super.equals(obj);
	}
}