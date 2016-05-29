package com.idgi.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
/*
Common utility that image-related issues might find helpful
 */
public class ImageUtility {

	/* Taken from http://stackoverflow.com/questions/3035692/how-to-convert-a-drawable-to-a-bitmap
	 * 27/05/2016 */
	public static Bitmap drawableToBitmap (Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable)drawable).getBitmap();
		}

		int width = drawable.getIntrinsicWidth();
		width = width > 0 ? width : 1;
		int height = drawable.getIntrinsicHeight();
		height = height > 0 ? height : 1;

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

	/* Taken from http://stackoverflow.com/questions/15440647/scaled-bitmap-maintaining-aspect-ratio
	 * 27/05/2016 */
	public static Bitmap scaleBitmapAndKeepRatio(Bitmap target, int height, int width)
	{
		Matrix m = new Matrix();
		m.setRectToRect(new RectF(0, 0, target.getWidth(), target.getHeight()), new RectF(0, 0, width, height), Matrix.ScaleToFit.CENTER);
		return Bitmap.createBitmap(target, 0, 0, target.getWidth(), target.getHeight(), m, true);
	}
}
