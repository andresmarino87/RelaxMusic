package com.relaxmusic.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.relaxmusic.R;
import com.relaxmusic.utils.Utils;
import com.relaxmusic.config.AppConstant;
import com.relaxmusic.listeners.SoundOnClickListener;
import com.relaxmusic.models.Sound;

/*
 * Author: Andrés Mariño
 * Date: 05 - 02 - 2015
 * 
 * Customize OnClickListener
 * */


public class MainActivity extends Activity {
	private Context context;
	private Drawable play;
	private Drawable stop;
	private ArrayList<Sound> mItems;
	
	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
//        SharedPreferences mPrefs = getSharedPreferences("RelaxMusicLocks", 0);
//        String mString = mPrefs.getStringSet("values", arg1).getString("tag", "default_value_if_variable_not_found");

        final RelativeLayout background=(RelativeLayout)findViewById(R.id.background);
        final RelativeLayout generalButtonsLayout=(RelativeLayout)findViewById(R.id.generalButtonsLayout);
        final TextView playButton= (TextView)findViewById(R.id.playButton);
        final TextView clearButton= (TextView)findViewById(R.id.clearButton);

        
        
        getActionBar().hide();
        loadDataFromAsset(background,generalButtonsLayout,clearButton,playButton);
        mItems = loadInitData();
        
        for(Sound item: mItems){
        	item.getTextView().bringToFront();
        	if(item.getIsSelected()){
        		item.getTextView().setBackgroundDrawable(item.getSelectedImage());
        	}else{
        		item.getTextView().setBackgroundDrawable(item.getNormalImage());
        	}
        	item.getTextView().setOnClickListener(new SoundOnClickListener(
        			context,
        			mItems,
        			item,
        			playButton,
        			stop));
        }
        
        clearButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Utils.clearAll(mItems);
			}
		});
        
        playButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				final TextView tv=(TextView)v;
				if(tv.isSelected()){
					Utils.pauseAll(mItems);
					tv.setBackgroundDrawable(play);
					tv.setSelected(false);
				}else{
					Utils.resumeAll(context,mItems);
					tv.setBackgroundDrawable(stop);					
					tv.setSelected(true);
				}
			}    	
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    		case R.id.action_settings:
    			return true;
    		default:
    			return super.onOptionsItemSelected(item);
    	}
    }
    
    //Load Data, images, etc. From Assets
    @SuppressWarnings("deprecation")
	public void loadDataFromAsset(RelativeLayout layoutTop, RelativeLayout layoutBottom,TextView clearButton, TextView playButton) {
    	Drawable rope=Utils.getDrawableFromDir(context, AppConstant.ImagesBaseDir+"Rope.png");
    	layoutTop.setBackgroundDrawable(Utils.getDrawableFromDir(context, AppConstant.ImagesBaseDir+"Background.jpg"));
    	layoutBottom.setBackgroundDrawable(Utils.getDrawableFromDir(context, AppConstant.ImagesBaseDir+"BkCloudBottom.png"));
    	clearButton.setBackgroundDrawable(Utils.getDrawableFromDir(context, AppConstant.ImagesBaseDir+"Btn_Clear_main.png"));
    	play=Utils.getDrawableFromDir(context, AppConstant.ImagesBaseDir+"Btn_Play.png");
    	stop=Utils.getDrawableFromDir(context, AppConstant.ImagesBaseDir+"Btn_Pause.png");
    	playButton.setBackgroundDrawable(play);
            
    	layoutTop.setBackgroundDrawable(Utils.getDrawableFromDir(context, AppConstant.ImagesBaseDir+"Background.jpg"));

    	ArrayList<View> images = Utils.getViewsByTag(layoutTop,getString(R.string.image_tag));
    	for(View v:images){
    		(v).setBackgroundDrawable(rope);
    	}
    }
    
    //Load initial data in Array List 
    private ArrayList<Sound> loadInitData(){
    	ArrayList<Sound> res=new ArrayList<>();
    	for(int i=2;i<11;i++){
    		res.add(new Sound("name",
    			false, 
    			Utils.getDrawableFromDir(context, AppConstant.ImagesNormalDir+"ButtonSound"+i+".png"), 
    			Utils.getDrawableFromDir(context, AppConstant.ImagesSelectedDir+"ButtonSelSound"+i+".png"), 
    			null,
    			(TextView)findViewById(AppConstant.Ids[i-2]),
    			AppConstant.Ids[i-2],
    			AppConstant.ImagesSoundsDir+"sound"+i+".ogg"));
    	}    	
    	return res;
    }  
    
    @Override
    public void onStop() {
        super.onStop();
        Utils.pauseAll(mItems);
    }
}