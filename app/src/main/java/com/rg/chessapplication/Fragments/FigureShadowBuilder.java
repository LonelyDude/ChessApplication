package com.rg.chessapplication.Fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by HP PC on 03.01.2017.
 */

public class FigureShadowBuilder extends View.DragShadowBuilder {
    private BitmapDrawable drawable;
    private ImageView imageView;

    public FigureShadowBuilder(View view) {
        super(view);
        imageView = (ImageView) view;
        drawable = (BitmapDrawable) imageView.getDrawable();
    }
    @Override
    public void onDrawShadow(Canvas canvas) {
        drawable.draw(canvas);
    }
}
