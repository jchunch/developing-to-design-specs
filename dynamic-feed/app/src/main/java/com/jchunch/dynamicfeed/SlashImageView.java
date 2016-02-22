package com.jchunch.dynamicfeed;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by John C. Hunchar on 2/21/16.
 */
public class SlashImageView extends ImageView {
    private static final String TAG = SlashImageView.class.getName();

    private static final float DEFAULT_VALUE_ANGLE_DEG = 0f;
    private static final int DEFAULT_VALUE_COLOR = android.R.color.transparent;

    private float mSlashAngle;
    private int mSlashColor;

    public SlashImageView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public SlashImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public SlashImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SlashImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Always call this like a constructor
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        // Set the default attributes
        mSlashAngle = DEFAULT_VALUE_ANGLE_DEG;
        mSlashColor = ContextCompat.getColor(context, DEFAULT_VALUE_COLOR);

        // Load in any set attributes
        if (attrs != null) {
            final TypedArray a = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.SlashImageView,
                    defStyleAttr,
                    defStyleRes);

            mSlashAngle = a.getFloat(R.styleable.SlashImageView_slashAngle,
                    DEFAULT_VALUE_ANGLE_DEG);

            mSlashColor = a.getColor(R.styleable.SlashImageView_slashFill,
                    ContextCompat.getColor(getContext(), DEFAULT_VALUE_COLOR));

            a.recycle();
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Get view dimensions
        float height = getHeight();
        float width = getWidth();

        // Get slash dimensions
        float slashHeight = (float) Math.tan(mSlashAngle * Math.PI / 180.0) * width;

        // Define slash perimeted
        Path slash = new Path();
        slash.moveTo(width, height);
        slash.lineTo(width, height - slashHeight);
        slash.lineTo(0, height);
        slash.close();

        // Set slash fill
        Paint fill = new Paint();
        fill.setColor(mSlashColor);

        // Draw slash
        canvas.drawPath(slash, fill);
    }
}
