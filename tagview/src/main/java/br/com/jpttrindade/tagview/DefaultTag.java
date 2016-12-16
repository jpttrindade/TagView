package br.com.jpttrindade.tagview;

import android.os.Parcel;
import android.os.Parcelable;

public class DefaultTag implements Parcelable {
	
	public int color;
	public String text;
	public boolean editable;
	public int type;
	public int id;
	public int imgID;

	public DefaultTag(String text, int color) {
		this(text, color, false);
	}

	public DefaultTag(String text, int color, boolean editable) {
		this.color = color;
		this.text = text;
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

	protected DefaultTag(Parcel in) {
		this.color = in.readInt();
		this.text = in.readString();
		this.imgID = in.readInt();
		this.editable = in.readByte() != 0;
	}

	public static final Parcelable.Creator<DefaultTag> CREATOR = new Parcelable.Creator<DefaultTag>() {
		public DefaultTag createFromParcel(Parcel source) {
			return new DefaultTag(source);
		}

		public DefaultTag[] newArray(int size) {
			return new DefaultTag[size];
		}
	};

	public boolean equals(DefaultTag t) {
		if(t.type==type && t.text.equals(text)){
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DefaultTag) {
			DefaultTag tag = (DefaultTag) obj;
			return tag.equals(this);
		}
		return super.equals(obj);
	}
}