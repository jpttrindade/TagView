package br.com.jpttrindade.tagview.widget;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements Parcelable {
	
	public int color;
	public String text;
	public boolean editable;
	public int type;
	public int id;
	public int imgID;
	
	
	public Tag(String text, int color, int imgID ,boolean editable) {
		this.color = color;
		this.text = text;
		this.editable = editable;
		this.type = -1;
		this.id = -1;
		this.imgID = imgID;
	}
	
	public void setType(int newType){
		type = newType;
	}
	
	public void setID(int id){
		this.id = id;
	}
		
	public boolean equals(Tag t) {
	/*	if(t.text == text && t.type==type && t.color == color && t.editable ==editable){
			return true;
		}*/
		
		//if(t.type==type && t.id == id){
		if(t.type==type && t.text.equals(text)){
			return true;
		}
		
		return false;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.color);
		dest.writeString(this.text);
		dest.writeByte(editable ? (byte) 1 : (byte) 0);
		dest.writeInt(this.type);
		dest.writeInt(this.id);
		dest.writeInt(this.imgID);
	}

	protected Tag(Parcel in) {
		this.color = in.readInt();
		this.text = in.readString();
		this.editable = in.readByte() != 0;
		this.type = in.readInt();
		this.id = in.readInt();
		this.imgID = in.readInt();
	}

	public static final Parcelable.Creator<Tag> CREATOR = new Parcelable.Creator<Tag>() {
		public Tag createFromParcel(Parcel source) {
			return new Tag(source);
		}

		public Tag[] newArray(int size) {
			return new Tag[size];
		}
	};
}