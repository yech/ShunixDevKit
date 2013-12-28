package com.shunix.shunixdevkit.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * 
 * @author Ray WANG
 * @since Dec 28th, 2013
 * @version 1.0.0
 */
public class ImageUtils {
	/**
	 * combine images, watermark image will be placed in the center of the
	 * source image
	 * 
	 * @param src
	 *            source image
	 * @param watermark
	 *            watermark image
	 * @return target iamge
	 */
	public Bitmap combineImages(Bitmap src, Bitmap watermark) {
		Bitmap newb = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(newb);
		canvas.drawBitmap(src, 0, 0, null);
		canvas.drawBitmap(watermark,
				(src.getWidth() - watermark.getWidth()) / 2,
				(src.getHeight() - watermark.getHeight()) / 2, null);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		watermark.recycle();
		watermark = null;
		return newb;
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	public static int dpToPx(Context context, int dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		int px = (int) (dp * scale + 0.5f);
		return px;
	}

	public static Drawable zoomDrawable(Context context, Drawable drawable,
			int w, int h) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap oldbmp = drawableToBitmap(drawable);
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) w / width);
		float scaleHeight = ((float) h / height);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
				matrix, true);
		return new BitmapDrawable(context.getResources(), newbmp);
	}
}
