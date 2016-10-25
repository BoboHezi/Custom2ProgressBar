package bobo.custom2progressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class CustomProgress2Bar extends View {

	private int backColor;
	private int secondProgressColor;
	private int progressColor;
	private int textColor;
	private float backHeight;
	private float secondProgressHeight;
	private float progressheight;
	private float textHeight;
	private int max;
	private boolean isTextDisplay;

	private Paint paint;
	private int progress;
	private int secondProgress;

	public CustomProgress2Bar(Context context) {
		this(context, null);
	}

	public CustomProgress2Bar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomProgress2Bar(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		paint = new Paint();
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.myprogress2bar);
		backColor = ta.getColor(R.styleable.myprogress2bar_backcolor,
				Color.GRAY);
		backHeight = ta.getDimension(R.styleable.myprogress2bar_backheight, 5);
		secondProgressColor = ta.getColor(
				R.styleable.myprogress2bar_secondprogresscolor, Color.GREEN);
		secondProgressHeight = ta.getDimension(
				R.styleable.myprogress2bar_secondprogressheight, 5);
		progressColor = ta.getColor(R.styleable.myprogress2bar_progresscolor,
				Color.RED);
		progressheight = ta.getDimension(
				R.styleable.myprogress2bar_progressheight, 5);
		isTextDisplay = ta.getBoolean(R.styleable.myprogress2bar_istextdisplay,
				true);
		textColor = ta.getColor(R.styleable.myprogress2bar_textcolor,
				Color.BLACK);
		textHeight = ta.getDimension(R.styleable.myprogress2bar_textheight, 30);
		max = ta.getInt(R.styleable.myprogress2bar_MAX, 100);
		ta.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int realWidth = getMeasuredWidth() - getPaddingLeft()
				- getPaddingRight();
		// draw progress
		float endX_first = getProgress() * 1.0f / max * realWidth;
		if (progress > 0) {
			paint.setColor(progressColor);
			paint.setStrokeWidth(progressheight);
			canvas.drawLine(0, 10, endX_first, 10, paint);
			canvas.drawCircle(endX_first, 10, 8, paint);
		}

		// draw secondprogress
		float endX_second = getSecondProgress() * 1.0f / max * realWidth;
		if (progress < secondProgress && secondProgress > 0) {
			paint.setColor(secondProgressColor);
			paint.setStrokeWidth(secondProgressHeight);
			canvas.drawLine(endX_first, 10, endX_second, 10, paint);
		}

		// draw back
		if (progress < max && secondProgress < max) {
			paint.setColor(backColor);
			paint.setStrokeWidth(backHeight);
			canvas.drawLine(endX_second, 10, realWidth, 10, paint);
		}

		// draw text
		if (isTextDisplay) {
			String text = getProgress() + "%";
			paint.setColor(textColor);
			paint.setTextSize(textHeight);
			int textWidth = (int) paint.measureText(text);
			int y = (int) (-(paint.descent() + paint.ascent()) / 2) + 30;
			canvas.drawText(text, realWidth - textWidth, y, paint);
		}
	}

	public synchronized int getMax() {
		return max;
	}

	public synchronized void setMax(int max) {
		this.max = max;
	}

	public synchronized int getProgress() {
		return progress;
	}

	public synchronized void setProgress(int progress) {
		if (progress < 0) {
			throw new IllegalArgumentException("max not less than 0");
		}
		if (progress > max)
			progress = max;
		if (progress <= max) {
			this.progress = progress;
			postInvalidate();
		}
	}

	public synchronized int getSecondProgress() {
		return secondProgress;
	}

	public synchronized void setSecondProgress(int secondProgress) {
		if (secondProgress >= 100)
			secondProgress = 100;
		this.secondProgress = secondProgress;
		postInvalidate();
	}

}
