package com.idgi.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;

/**
 * Created by Jonathan Krän on 04/05/2016.
 */
public class TransitionFactory {

	public static TransitionDrawable createDrawableCrossFade(Drawable from, Drawable to, int duration) {
		Drawable drawables[] = {from, to};
		TransitionDrawable transition = new TransitionDrawable(drawables);
		return transition;
	}
}
