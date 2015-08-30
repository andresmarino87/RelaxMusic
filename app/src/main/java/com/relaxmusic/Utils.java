package com.relaxmusic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/*
 * Author: Andrés Mariño
 * Date: 05 - 02 - 2015
 * 
 * */

public class Utils {

	static public void stopPlayer(MediaPlayer player){
		if(player!=null){
			player.stop();
			player.release();
			player = null;
		}
		return;
	}
	
	static public Drawable getDrawableFromDir(Context context, String dir){
		Drawable res=null;
        try {
            InputStream ims = context.getAssets().open(dir);
            res = Drawable.createFromStream(ims, null);
        }
        catch(IOException ex) {
        	Log.e(AppConstant.AppName,"Error loading image "+ex);
        }
		return res;
	}
	
    public static ArrayList<View> getViewsByTag(ViewGroup root, String tag){
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            } 

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }

        }
        return views;
    }
    
    //Clear all sounds
    @SuppressWarnings("deprecation")
	static public void clearAll(ArrayList<SoundItem> items){
    	for(SoundItem i:items){
    		if(i.getIsSelected()){
	    		i.setIsSelected(false);
	    		i.getTextView().setSelected(false);
	    		i.getTextView().setBackgroundDrawable(i.getNormalImage());
	    		i.getTextView().clearAnimation();
				Utils.stopPlayer(i.getMediaPlayer());
    		}    		
    	}
    	return;
    }
    
    //Pause Player
	static public void pauseAll(ArrayList<SoundItem> items){
    	for(SoundItem i:items){
    		if(i.getIsSelected()){

    			i.getMediaPlayer().pause();
    			i.getTextView().clearAnimation();

    		}    		
    	}
    	return;
    }
	
    //Pause Player
	static public void resumeAll(Context context, ArrayList<SoundItem> items){
    	for(SoundItem i:items){
    		if(i.getIsSelected()){
				i.getMediaPlayer().start();
				starAnimationTV(context,i.getTextView());
    		}    		
    	}
    	return;
    }
	
	static public boolean hasReachLimit(ArrayList<SoundItem> items){
		boolean res=false;
		int counter=0;
		for(SoundItem i:items){
    		if(i.getIsSelected()){
    			counter++;
    		}    		
    		if(counter>=AppConstant.MaxSoundsNumber){
    			res=true;
    			break;
    		}
    	}
		return res;
	}
	
	static public void starAnimationTV(Context context,TextView tv){
		Animation in = AnimationUtils.loadAnimation(context,R.animator.selected_sound);
		in.reset();  // reset initialization state
		in.setRepeatMode(Animation.RESTART);
		tv.startAnimation(in);

		
	}
}
