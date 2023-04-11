package com.viavilab.androiddrawing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
 import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.viavilab.androiddrawing.ColorPickerDialog.OnColorSelectedListener;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Drawing_Activity extends Activity{

	ViewPager viewpager;
	TextView txtstep;
	ImageView imageView;
	Button btnback,btnnext;
	RelativeLayout reldraw;
	ImageView imgcolorpick,imgpencil,imgstroke,imgeraser,imgclearr,imgsave;
	private static int TOTAL_IMAGES;
	private int currentPosition = 0;
	protected Dialog dialog;
	protected View layout;
	protected int progress;
	protected float stroke = 6;
	private String fileName;
	PaintView pv;
	int count=0;
	String get1;
	private AdView mAdView;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
 		setContentView(R.layout.drawing_activity);
 
		mAdView = (AdView) findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());

		btnback=(Button)findViewById(R.id.button_prev);
		btnnext=(Button)findViewById(R.id.button_next);
		reldraw=(RelativeLayout)findViewById(R.id.rell);
		viewpager=(ViewPager)findViewById(R.id.view_pagercake);
		txtstep=(TextView)findViewById(R.id.textView_stepsize);

		Intent i=getIntent();
		get1=i.getExtras().getString("Image_Array");
		//Log.e("get1---", get1);

		imgcolorpick=(ImageView)findViewById(R.id.imageView2_color);
		imgstroke=(ImageView)findViewById(R.id.imageView4_stroke);
		imgclearr=(ImageView)findViewById(R.id.imageView5_clear);
		imgeraser=(ImageView)findViewById(R.id.imageView3_eraser);
		imgpencil=(ImageView)findViewById(R.id.imageView1_pencil);
		imgsave=(ImageView)findViewById(R.id.imageView1_save);

		imgpencil.setImageResource(R.drawable.brush_active_btn); //click of pencil

		this.pv = new PaintView(this);
		this.pv.togglePencil(true);
		reldraw .addView(pv);

		imgcolorpick.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showColorPickerDialogDemo();
				//Toast.makeText(getApplicationContext(), "hiiii", Toast.LENGTH_SHORT).show();
			}
		});

		imgstroke.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				strokeDialog();
			}
		});

		imgclearr.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Drawing_Activity.this.pv.clear();
			}
		});

		imgeraser.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Drawing_Activity.this.pv.togglePencil(false);
				imgeraser.setImageResource(R.drawable.erase_active_btn);
				imgpencil.setImageResource(R.drawable.brush_btn);
			}
		});

		imgpencil.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Drawing_Activity.this.pv.togglePencil(true);
				imgpencil.setImageResource(R.drawable.brush_active_btn);
				imgeraser.setImageResource(R.drawable.erase_btn);
			}
		});
		imgsave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Drawing_Activity.this.save();
			}
		});

		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				txtstep.setText(String.valueOf(arg0)+"/"+TOTAL_IMAGES);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		ImagePagerAdapter adapter = new ImagePagerAdapter();
		viewpager.setAdapter(adapter);
		if(get1.equals("image_car"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_car.length - 1);
		}
		else if(get1.equals("image_house"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_house.length - 1);
		}
		else if(get1.equals("image_spider"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_spider.length - 1);
		}
		else if(get1.equals("image_gun"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_gun.length - 1);
		}
		else if(get1.equals("image_boat"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_boat.length - 1);
		}
		else if(get1.equals("image_bird"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_bird.length - 1);
		}
		else if(get1.equals("image_elephant"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_elephant.length - 1);
		}
		else if(get1.equals("image_flower"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_flower.length - 1);
		}
		else if(get1.equals("image_frog"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_frog.length - 1);
		}
		else if(get1.equals("image_horse"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_horse.length - 1);
		}
		else if(get1.equals("image_lion"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_lion.length - 1);
		}
		else if(get1.equals("image_mickey_mouse"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_mickey_mouse.length - 1);
		}
		else if(get1.equals("image_dog"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_dog.length - 1);
		}
		else if(get1.equals("image_turtle"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_turtle.length - 1);
		}
		else if(get1.equals("image_princess"))
		{
			TOTAL_IMAGES = (Image_Arrays.image_princess.length - 1);
		}
		else
		{
			TOTAL_IMAGES = (Image_Arrays.image_car.length - 1);
		}
		txtstep.setText(String.valueOf(0)+"/"+TOTAL_IMAGES);
		btnback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				currentPosition = viewpager.getCurrentItem();

				int positionToMoveTo = currentPosition;
				positionToMoveTo--;
				if (positionToMoveTo < 0) {
					positionToMoveTo = TOTAL_IMAGES;
				}
				viewpager.setCurrentItem(positionToMoveTo);
			}
		});
		btnnext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				currentPosition = viewpager.getCurrentItem();

				int positionToMoveTo = currentPosition;
				positionToMoveTo++;
				if (currentPosition == TOTAL_IMAGES) {
					positionToMoveTo = 0;
				}
				viewpager.setCurrentItem(positionToMoveTo);

			}
		});

	}
	protected int getItem(int i) {
		// TODO Auto-generated method stub
		return 0;
	}
	private class ImagePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			if(get1.equals("image_car"))
			{
				return Image_Arrays.image_car.length;
			}
			else if(get1.equals("image_house"))
			{
				return Image_Arrays.image_house.length;
			}
			else if(get1.equals("image_spider"))
			{
				return Image_Arrays.image_spider.length;
			}
			else if(get1.equals("image_gun"))
			{
				return Image_Arrays.image_gun.length;
			}
			else if(get1.equals("image_boat"))
			{
				return Image_Arrays.image_boat.length;
			}
			else if(get1.equals("image_bird"))
			{
				return Image_Arrays.image_bird.length;
			}
			else if(get1.equals("image_elephant"))
			{
				return Image_Arrays.image_elephant.length;
			}
			else if(get1.equals("image_flower"))
			{
				return Image_Arrays.image_flower.length;
			}
			else if(get1.equals("image_frog"))
			{
				return Image_Arrays.image_frog.length;
			}
			else if(get1.equals("image_horse"))
			{
				return Image_Arrays.image_horse.length;
			}
			else if(get1.equals("image_lion"))
			{
				return Image_Arrays.image_lion.length;
			}
			else if(get1.equals("image_mickey_mouse"))
			{
				return Image_Arrays.image_mickey_mouse.length;
			}
			else if(get1.equals("image_dog"))
			{
				return Image_Arrays.image_dog.length;
			}
			else if(get1.equals("image_turtle"))
			{
				return Image_Arrays.image_turtle.length;
			}
			else if(get1.equals("image_princess"))
			{
				return Image_Arrays.image_princess.length;
			}
			else
			{
				return Image_Arrays.image_car.length;

			}

		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((ImageView) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Context context = Drawing_Activity.this;

			imageView = new ImageView(context);

			imageView.setScaleType(ImageView.ScaleType.FIT_XY);

			if(get1.equals("image_car"))
			{
				imageView.setImageResource(Image_Arrays.image_car[position]);
			}
			else if(get1.equals("image_house"))
			{
				imageView.setImageResource(Image_Arrays.image_house[position]);
			}
			else if(get1.equals("image_spider"))
			{
				imageView.setImageResource(Image_Arrays.image_spider[position]);
			}
			else if(get1.equals("image_gun"))
			{
				imageView.setImageResource(Image_Arrays.image_gun[position]);
			}
			else if(get1.equals("image_boat"))
			{
				imageView.setImageResource(Image_Arrays.image_boat[position]);
			}
			else if(get1.equals("image_bird"))
			{
				imageView.setImageResource(Image_Arrays.image_bird[position]);
			}
			else if(get1.equals("image_elephant"))
			{
				imageView.setImageResource(Image_Arrays.image_elephant[position]);
			}
			else if(get1.equals("image_flower"))
			{
				imageView.setImageResource(Image_Arrays.image_flower[position]);
			}
			else if(get1.equals("image_frog"))
			{
				imageView.setImageResource(Image_Arrays.image_frog[position]);
			}
			else if(get1.equals("image_horse"))
			{
				imageView.setImageResource(Image_Arrays.image_horse[position]);
			}
			else if(get1.equals("image_lion"))
			{
				imageView.setImageResource(Image_Arrays.image_lion[position]);
			}
			else if(get1.equals("image_mickey_mouse"))
			{
				imageView.setImageResource(Image_Arrays.image_mickey_mouse[position]);
			}
			else if(get1.equals("image_dog"))
			{
				imageView.setImageResource(Image_Arrays.image_dog[position]);
			}
			else if(get1.equals("image_turtle"))
			{
				imageView.setImageResource(Image_Arrays.image_turtle[position]);
			}
			else if(get1.equals("image_princess"))
			{
				imageView.setImageResource(Image_Arrays.image_princess[position]);
			}
			else
			{
				imageView.setImageResource(Image_Arrays.image_car[position]);
			}

			((ViewPager) container).addView(imageView, 0);

			return imageView;

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((ImageView) object);
		}
	}

	private void showColorPickerDialogDemo() {

		int initialColor = Color.WHITE;

		ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, initialColor, new OnColorSelectedListener() {

			@Override
			public void onColorSelected(int color) {

				Drawing_Activity.this.pv.setColor(color);
			}

		});
		colorPickerDialog.show();
	}

	private class PaintView extends View {

		private Paint paint;
		private Bitmap bmp;
		private Paint bmpPaint;
		private Canvas canvas;
		private Context context;
		private float mX, mY;
		private Path path;
		private static final float TOUCH_TOLERANCE = 0.8f;
		private int colour;
		private Bitmap bgImage; // image that gets loaded
		protected Boolean pencil;

		private PaintView(Context c) {
			super(c);

			setDrawingCacheEnabled(true); // to save images

			this.context = c;
			this.colour = Color.BLACK;
			this.path = new Path();
			this.bmpPaint = new Paint();
			this.paint = new Paint();
			this.paint.setAntiAlias(true);
			this.paint.setDither(true);
			this.paint.setColor(this.colour);
			this.paint.setStyle(Paint.Style.STROKE);
			this.paint.setStrokeJoin(Paint.Join.ROUND);
			this.paint.setStrokeCap(Paint.Cap.ROUND);
			this.paint.setStrokeWidth(3);
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			this.bgImage = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			this.bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
			this.canvas = new Canvas(this.bmp);
			bgImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.inner_bg).copy(Bitmap.Config.ARGB_8888, true);

		}

		private void touchStart(float x, float y) {
			this.path.reset();
			this.path.moveTo(x, y);
			this.mX = x;
			this.mY = y;
		}

		private void touchUp() {
			this.path.lineTo(mX, mY);
			// commit the path to our offscreen
			this.canvas.drawPath(this.path, paint);
			// kill this so we don't double draw
			this.path.reset();
		}

		private void touchMove(float x, float y) {
			float dx = Math.abs(x - this.mX);
			float dy = Math.abs(y - this.mY);
			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
				// draws a quadratic curve
				this.path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
				mX = x;
				mY = y;
			}
		}

		@Override
		public boolean onTouchEvent(MotionEvent e) {
			float x = e.getX();
			float y = e.getY();

			switch (e.getAction()) {
			case MotionEvent.ACTION_DOWN:
				this.touchStart(x, y);
				this.touchMove(x + 0.8f, y + 0.8f);
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				this.touchMove(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				this.touchUp();

				invalidate();
				break;
			}
			return true;
		}

		// Called on invalidate();
		@Override
		protected void onDraw (Canvas canvas) {
			canvas.drawColor(Color.WHITE);
			canvas.drawBitmap(this.bgImage, 0, 0, this.bmpPaint);

			canvas.drawBitmap(this.bmp, 0, 0, this.bmpPaint);

			canvas.drawPath(this.path, this.paint);

		}

		/*
		 * Menu called methods
		 */
		protected void togglePencil(Boolean b) {
			if (b) { // set pencil
				paint.setXfermode(null);
				this.pencil = true;

			} else { // set eraser
				paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
				this.pencil = false;
			}
			//Draw_Dog.this.setTitle();

		}

		public void setColor(int c) {
			this.paint.setColor(c);
			this.colour = c;
		}

		protected int getColor() {
			return this.colour;
		}

		protected void clear() {
			this.path = new Path(); // empty path
			this.canvas.drawColor(Color.WHITE);
			if (this.bgImage != null) {
				this.canvas.drawBitmap(this.bgImage, 0, 0, null);
			}
			this.invalidate();
		}
	}
	public void strokeDialog() {

		this.dialog = new Dialog(this);
		this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		this.layout = inflater.inflate(R.layout.stroke_dialog,
				(ViewGroup) findViewById(R.id.dialog_root_element));

		SeekBar dialogSeekBar = (SeekBar) layout
				.findViewById(R.id.dialog_seekbar);

		dialogSeekBar.setThumbOffset(convertDipToPixels(9.5f));
		dialogSeekBar.setProgress((int) this.stroke * 2);

		this.setTextView(this.layout, String.valueOf(Math.round(this.stroke)));

		dialogSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// herp
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// derp
			}

			@Override
			public void onProgressChanged(SeekBar seekBark, int progress,
					boolean fromUser) {
				Drawing_Activity.this.progress = progress / 2;
				Drawing_Activity.this
				.setTextView(Drawing_Activity.this.layout, "" + Drawing_Activity.this.progress);

				Button b = (Button) Drawing_Activity.this.layout
						.findViewById(R.id.dialog_button);
				b.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Drawing_Activity.this.stroke = Drawing_Activity.this.progress;
						Drawing_Activity.this.pv.paint.setStrokeWidth(Drawing_Activity.this.stroke);
						Drawing_Activity.this.dialog.dismiss();
					}
				});
			}
		});

		dialog.setContentView(layout);
		dialog.show();
	}
	protected void setTextView(View layout, String s) {
		TextView text = (TextView) layout.findViewById(R.id.stroke_text);
		text.setText(s);
	}

	private int convertDipToPixels(float dip) {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		float density = metrics.density;
		return (int) (dip * density);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{

		//  Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
		case R.id.Share:
			reldraw.setDrawingCacheEnabled(true);
			String path = Environment.getExternalStorageDirectory().toString();
			OutputStream fOut = null;
			File file = new File(path,
					"Android/data/com.viavilab.androiddrawing;/cache/share_cache.jpg");
			file.getParentFile().mkdirs();

			try {
				file.createNewFile();
			} catch (Exception e) {
				//Log.e("draw_save", e.toString());
			}

			try {
				fOut = new FileOutputStream(file);
			} catch (Exception e) {
				//Log.e("draw_save1", e.toString());
			}

			if (this.reldraw.getDrawingCache() == null) {
				//Log.e("lal", "tis null");
			}

			this.reldraw.getDrawingCache()
			.compress(Bitmap.CompressFormat.JPEG, 85, fOut);

			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
				//Log.e("draw_save1", e.toString());
			}

			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("image/jpeg");
			share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));

			startActivity(Intent.createChooser(share, "Share Image"));
			return true;

		case R.id.RateApp:
			final String appName = getPackageName();
			try {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("market://details?id=" + appName)));
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("http://play.google.com/store/apps/details?id="
								+ appName)));
			}
			return true;

		case R.id.About:
			Intent intentabout=new Intent(getApplicationContext(),AboutActivity.class);
			startActivity(intentabout);

		}

		return(super.onOptionsItemSelected(item));
	}

	public void save() { // called on save menu

		reldraw.setDrawingCacheEnabled(true);
		String path = Environment.getExternalStorageDirectory().toString();
		OutputStream fOut = null;
		File file = new File(path,
				getString(R.string.app_name)+"/"+"AD_"+System.currentTimeMillis()+".jpg");
		Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
		file.getParentFile().mkdirs();

		try {
			file.createNewFile();
		} catch (Exception e) {
			Log.e("draw_save", e.toString());
		}

		try {
			fOut = new FileOutputStream(file);
		} catch (Exception e) {
			Log.e("draw_save1", e.toString());
		}

		if (this.reldraw.getDrawingCache() == null) {
			Log.e("lal", "tis null");
		}

		this.reldraw.getDrawingCache()
		.compress(Bitmap.CompressFormat.JPEG, 85, fOut);

		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			Log.e("draw_save1", e.toString());
		}
	}
 }
