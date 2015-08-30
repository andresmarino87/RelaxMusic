package com.relaxmusic;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Author: Andrés Mariño
 * Date: 05 - 02 - 2015
 * 
 * Customize OnClickListener
 * */

public class SoundOnClickListener implements OnClickListener{
	Context context;
	ArrayList<SoundItem> items;

	MediaPlayer player;
	SoundItem item;
	TextView playButton;
	Drawable stop;
	
	public SoundOnClickListener(Context context, 
		ArrayList<SoundItem> items,
		SoundItem item,
		TextView tv,
		Drawable stop){
		this.context=context;
		this.items=items;
		this.item=item;
		this.playButton=tv;
		this.stop=stop;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		final TextView text=(TextView)v; 
		if(!text.isSelected()){				
			if(!Utils.hasReachLimit(items)){
				text.setSelected(true);
				if(item.getSelectedImage()!=null){
					text.setBackgroundDrawable(item.getSelectedImage());
				}
				Utils.starAnimationTV(context,text);
				player = new MediaPlayer();
				try {
					AssetFileDescriptor descriptor;
					descriptor = context.getAssets().openFd(item.getSound());
					long start = descriptor.getStartOffset();
					long end = descriptor.getLength();
					player.setDataSource(descriptor.getFileDescriptor(), start, end);
					player.prepare();
					player.setVolume(1.0f, 1.0f);
					player.setLooping(true);
					item.setMediaPlayer(player);
					item.setIsSelected(true);
					Utils.resumeAll(context,items);
					playButton.setSelected(true);
					playButton.setBackgroundDrawable(stop);
				} catch (IllegalArgumentException e) {
					Log.e(AppConstant.AppName,"Error SoundOnClickListener "+e);
				} catch (IllegalStateException e) {
					Log.e(AppConstant.AppName,"Error SoundOnClickListener "+e);
				} catch (IOException e) {
					Log.e(AppConstant.AppName,"Error SoundOnClickListener "+e);
				}
			}else{
				Toast.makeText(context, R.string.no_more_than_3, Toast.LENGTH_SHORT).show();
			}
		}else{
			item.setIsSelected(false);
			text.setSelected(false);
			text.clearAnimation();
			Utils.stopPlayer(item.getMediaPlayer());
			if(item.getNormalImage()!=null){
				text.setBackgroundDrawable(item.getNormalImage());
			}
		}
	}
}
