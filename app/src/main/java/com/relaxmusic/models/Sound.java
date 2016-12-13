package com.relaxmusic.models;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.widget.TextView;

/*
 * Author: Andrés Mariño
 * Date: 05 - 02 - 2015
 * 
 * */

//Simple class for and Item
public class Sound {
	private CharSequence name;
	private boolean isSelected;
	private Drawable normalImage;
	private Drawable selectedImage;
	private MediaPlayer player;
	private TextView tv;
	private int id;
	private String soundName;
	
	public Sound(
		CharSequence name,
		boolean isSelected,
		Drawable normalImage,
		Drawable selectedImage,
		MediaPlayer player,
		TextView tv,
		int id,
		String soundName){
		this.name=name;
		this.isSelected=isSelected;
		this.normalImage=normalImage;
		this.selectedImage=selectedImage;
		this.player=player;		
		this.tv=tv;		
		this.id=id;		
		this.soundName=soundName;
	}
	
	
	//Setters
	public void setName(CharSequence name){
		this.name=name;
	}

	public void setIsSelected(boolean isSelected){
		this.isSelected=isSelected;
	}

	public void setNormalImage(Drawable normalImage){
		this.normalImage=normalImage;
	}

	public void setSelectedImage(Drawable selectedImage){
		this.selectedImage=selectedImage;
	}

	public void setMediaPlayer(MediaPlayer player){
		this.player=player;
	}

	public void setTV(TextView tv){
		this.tv=tv;
	}
	
	public void setId(int id){
		this.id=id;
	}

	public void setSound(String sound){
		this.soundName=sound;
	}

	//Getters
	public CharSequence getName(){
		return this.name;
	}

	public boolean getIsSelected(){
		return this.isSelected;
	}

	public Drawable getNormalImage(){
		return this.normalImage;
	}

	public Drawable getSelectedImage(){
		return this.selectedImage;
	}

	public MediaPlayer getMediaPlayer(){
		return this.player;
	}
	
	public TextView getTextView(){
		return this.tv;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getSound(){
		return this.soundName;
	}
}
