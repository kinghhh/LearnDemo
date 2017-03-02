package com.example.learndemo.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.learndemo.R;

/**
 * 预览答题界面
 * 
 * @author Administrator
 *
 */
public class ImageFragment extends Fragment {
	private Bitmap bitmap;
	private int windowWidth;// 可视区宽度
	private int windowHeight;// 可视区高度
	private float initialX;// 起始位置x坐标
	private float initialY;// 起始位置y坐标
	private float baseScale;// 图片放大至边缘与屏幕边缘重合时的倍数 此变量将作为一个标准倍数
	private float baseX;// 图片缩放率为一个标准倍数时的x坐标
	private float baseY;// 图片缩放率为一个标准倍数时的y坐标
	private ImageView imageView;
	private int imageWidth;// 图片原始宽度
	private int imageHeight;// 图片原始高度
	private ValueAnimator currentAnimationX;// x轴的惯性动画;
	private ValueAnimator currentAnimationY;// y轴的惯性动画

	private int bgColor = 150;// 背景色的透明度0透明 255不透明

	private boolean isSingleTap;// 单指触屏为true
	private GestureDetector gestureScanner;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				imageWidth = ((BitmapDrawable) imageView.getDrawable()).getBitmap().getWidth();
				imageHeight = ((BitmapDrawable) imageView.getDrawable()).getBitmap().getHeight();
				baseScale = Math.min(windowWidth / (float) imageWidth, windowHeight / (float) imageHeight);
				baseX = (windowWidth - imageWidth * baseScale) / 2f;
				baseY = (windowHeight - imageHeight * baseScale) / 2f;
				setAnimation(baseX, baseY, baseScale, null, null, 0, 1);
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 
	 * @param bitmap
	 * @param x
	 *            起始位置x坐标
	 * @param y
	 *            起始位置y坐标
	 * @param width
	 *            可视区宽度
	 * @param height
	 *            可视区高度
	 */
	public ImageFragment(Bitmap bitmap, float x, float y, int width, int height) {
		this.bitmap = bitmap;
		this.initialX = x;
		this.initialY = y;
		windowWidth = width;
		windowHeight = height;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gestureScanner = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				Matrix imageMatrix = new Matrix(imageView.getImageMatrix());
				float[] values = new float[9];
				imageMatrix.getValues(values);
				if (values[2] >= 0 && distanceX < 0) {
					isSingleTap = false;
					distanceX /= 4;
				}
				if (values[5] >= 0 && distanceY < 0) {
					isSingleTap = false;
					distanceY /= 4;
				}
				if (values[2] + imageWidth * values[0] <= windowWidth && distanceX > 0) {
					isSingleTap = false;
					distanceX /= 4;
				}
				if (values[5] + imageHeight * values[0] <= windowHeight && distanceY > 0) {
					isSingleTap = false;
					distanceY /= 4;
				}

				imageMatrix.postTranslate(-distanceX, -distanceY);
				imageView.setImageMatrix(imageMatrix);
				return false;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				if (isSingleTap && (Math.abs(velocityX) > 200 || Math.abs(velocityY) > 200)) {
					setInertiaAnimation(velocityX, velocityY);
				} else {
					Matrix imageMatrix1 = imageView.getImageMatrix();
					float[] values1 = new float[9];
					imageMatrix1.getValues(values1);
					if (values1[0] < 2 * baseScale + 0.0001) {
						setAnimation(null, null, null, null, null, 1, 1);
					}
				}
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
			}

			@Override
			public boolean onDown(MotionEvent e) {
				if (currentAnimationX != null && currentAnimationX.isRunning())
					currentAnimationX.cancel();
				if (currentAnimationY != null && currentAnimationY.isRunning())
					currentAnimationY.cancel();
				isSingleTap = true;

				return true;
			}

			@Override
			public boolean onDoubleTap(MotionEvent e) {
				Matrix imageMatrix = imageView.getImageMatrix();
				float[] values = new float[9];
				imageMatrix.getValues(values);
				if (Math.abs(values[0] - baseScale) > 0.01) {
					setAnimation(baseX, baseY, baseScale, null, null, 1, 1);
				} else {
					setAnimation(null, null, 2 * baseScale, e.getX(), e.getY(), 1, 1);
				}

				return false;
			}

			@Override
			public boolean onDoubleTapEvent(MotionEvent e) {
				return false;
			}

			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				setAnimation(initialX, initialY, 1f, null, null, 1, 0);
				return false;
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_view, container, false);
		imageView = (ImageView) view.findViewById(R.id.iv_show_img);
		imageView.setImageBitmap(bitmap);
		imageView.setOnTouchListener(new View.OnTouchListener() {
			private float oriDis;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_POINTER_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN | 0x0100:
					Log.i("qwe", "ACTION_POINTER_DOWN");
					oriDis = distance(event);
					isSingleTap = false;
					break;
				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_POINTER_UP | 0x0100:
					Matrix imageMatrix1 = imageView.getImageMatrix();
					float[] values1 = new float[9];
					imageMatrix1.getValues(values1);
					if (values1[0] > 2 * baseScale) {
						PointF pointF = middle(event);
						setAnimation(null, null, null, pointF.x, pointF.y, 1, 1);
					} else {
						setAnimation(null, null, null, null, null, 1, 1);
					}
					break;
				case MotionEvent.ACTION_MOVE:
					if (event.getPointerCount() > 1) {
						PointF pointF = middle(event);
						Matrix imageMatrix = new Matrix(imageView.getImageMatrix());
						float[] values = new float[9];
						imageMatrix.getValues(values);
						float ts = distance(event) / oriDis;
						if (values[0] < baseScale / 1.5 && ts < 1) {
							ts = 1;
						}
						if (values[0] > baseScale * 2.5 && ts > 1) {
							ts = 1;
						}
						if (Math.abs(distance(event) - oriDis) > 1f) {
							imageMatrix.postScale(ts, ts, pointF.x, pointF.y);
							imageView.setImageMatrix(imageMatrix);
							oriDis = distance(event);
						}

					}
					break;

				default:
					break;
				}
				return gestureScanner.onTouchEvent(event);
			}
		});
		return view;
	}

	// 计算两个触摸点之间的距离
	private float distance(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	// 计算两个触摸点的中点
	private PointF middle(MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		return new PointF(x / 2, y / 2);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Matrix matrix = new Matrix();
		matrix.setTranslate(initialX, initialY);
		imageView.setImageMatrix(matrix);
		new MyThread().start();
	}

	class MyThread extends Thread {
		@Override
		public void run() {
			while (imageView.getDrawable() == null)
				;
			handler.sendEmptyMessage(0);
		}
	}

	private class MyAnimatorListener implements AnimatorUpdateListener {
		private float preX;
		private float preY;
		private float preS;

		private Matrix mPrimaryMatrix;

		public MyAnimatorListener() {
			mPrimaryMatrix = new Matrix(imageView.getImageMatrix());
			float[] values = new float[9];
			mPrimaryMatrix.getValues(values);
			preX = values[2];
			preY = values[5];
			preS = values[0];
		}

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			MyValue myValue = (MyValue) animation.getAnimatedValue();
			if (myValue.x != null || myValue.y != null) {
				if (myValue.x == null) {
					preX = 0f;
					myValue.x = 0f;
				}
				if (myValue.y == null) {
					preY = 0f;
					myValue.y = 0f;
				}
				mPrimaryMatrix.postTranslate(myValue.x - preX, myValue.y - preY);
				preX = myValue.x;
				preY = myValue.y;
			}
			if (myValue.s != null) {
				if (myValue.px != null && myValue.py != null) {
					mPrimaryMatrix.postScale(myValue.s / preS, myValue.s / preS, myValue.px, myValue.py);
				} else if (myValue.x != null && myValue.y != null) {
					mPrimaryMatrix.postScale(myValue.s / preS, myValue.s / preS, myValue.x, myValue.y);
				}
				preS = myValue.s;
			}
			imageView.setImageMatrix(mPrimaryMatrix);
			imageView.setBackgroundColor(Color.argb((int) (myValue.a * bgColor), 0, 0, 0));
		}
	}

	class MyValue {
		public Float x;
		public Float y;
		public Float s;
		public Float px;
		public Float py;
		public float a;

		public MyValue(Float x, Float y, Float s, Float px, Float py, float a) {
			this.x = x;
			this.y = y;
			this.s = s;
			this.px = px;
			this.py = py;
			this.a = a;
		}
	}

	class MyEvaluator implements TypeEvaluator<MyValue> {

		@Override
		public MyValue evaluate(float fraction, MyValue startValue, MyValue endValue) {
			Float x = null;
			Float y = null;
			Float s = null;
			Float px = null;
			Float py = null;
			if (startValue.x != null && endValue.x != null) {
				x = startValue.x + fraction * (endValue.x - startValue.x);
			}
			if (startValue.y != null && endValue.y != null) {
				y = startValue.y + fraction * (endValue.y - startValue.y);
			}
			if (startValue.s != null && endValue.s != null) {
				s = startValue.s + fraction * (endValue.s - startValue.s);
			}
			if (startValue.px != null && endValue.px != null) {
				px = startValue.px + fraction * (endValue.px - startValue.px);
			}
			if (startValue.py != null && endValue.py != null) {
				py = startValue.py + fraction * (endValue.py - startValue.py);
			}
			float a = startValue.a + fraction * (endValue.a - startValue.a);
			MyValue myValue = new MyValue(x, y, s, px, py, a);

			return myValue;
		}

	}

	public void setAnimation(Float endX, Float endY, Float endScale, Float px, Float py, float startAlpha,
			float endAlpha) {
		Matrix imageMatrix = imageView.getImageMatrix();
		float[] values = new float[9];
		imageMatrix.getValues(values);
		if (px == null || py == null) {
			if ((baseScale - values[0] > 0.0001 || values[0] - baseScale < 0.0001) && endAlpha != 0) {
				endX = baseX;
				endY = baseY;
				endScale = baseScale;
			}
			if (values[0] - baseScale > 0.0001) {
				float cWidth = imageWidth * values[0];
				float cHeight = imageHeight * values[0];
				if (endX == null) {
					if (cWidth >= windowWidth) {
						if (values[2] >= 0) {
							endX = 0f;
						} else if (values[2] + cWidth < windowWidth) {
							endX = windowWidth - cWidth;
						}
					} else {
						endX = (windowWidth - cWidth) / 2f;
					}
				}
				if (endY == null) {
					if (cHeight >= windowHeight) {
						if (values[5] >= 0) {
							endY = 0f;
						} else if (values[5] + cHeight < windowHeight) {
							endY = windowHeight - cHeight;
						}
					} else {
						endY = (windowHeight - cHeight) / 2f;
					}
				}
			}
		} else {
			if (values[0] > 2 * baseScale) {
				endScale = 2 * baseScale;
			}
			Matrix imageMatrixCopy = new Matrix(imageView.getImageMatrix());
			imageMatrixCopy.postScale(endScale / values[0], endScale / values[0], px, py);
			float[] valuesCopy = new float[9];
			imageMatrixCopy.getValues(valuesCopy);
			float cWidth = imageWidth * valuesCopy[0];
			float cHeight = imageHeight * valuesCopy[0];
			Matrix imageMatrixCopy2 = new Matrix(imageView.getImageMatrix());
			imageMatrixCopy2.postScale(endScale / values[0], endScale / values[0]);
			float[] valuesCopy2 = new float[9];
			imageMatrixCopy2.getValues(valuesCopy2);
			float cWidth2 = imageWidth * valuesCopy2[0];
			float cHeight2 = imageHeight * valuesCopy2[0];
			if (cWidth >= windowWidth) {
				if (valuesCopy[2] >= 0) {
					px = null;
					endX = 0f;
				} else if (valuesCopy[2] + cWidth < windowWidth || valuesCopy2[2] + cWidth2 < windowWidth) {
					px = null;
					endX = windowWidth - cWidth;
				}
				if (px != null && px > windowWidth / 2
						&& (valuesCopy[5] > 0 || valuesCopy[5] + cHeight < windowHeight)) {
					px = null;
					endX = windowWidth - cWidth;
				}
			} else {
				px = null;
				endX = (windowWidth - cWidth) / 2f;
			}
			if (cHeight >= windowHeight) {
				if (valuesCopy[5] >= 0) {
					py = null;
					endY = 0f;
				} else if (valuesCopy[5] + cHeight < windowHeight || valuesCopy2[5] + cHeight2 < windowHeight) {
					py = null;
					endY = windowHeight - cHeight;
				}
				if (py != null && py > windowHeight / 2
						&& (valuesCopy[2] > 0 || valuesCopy[2] + cWidth < windowWidth)) {
					py = null;
					endY = windowHeight - cHeight;
				}
			} else {
				py = null;
				endY = (windowHeight - cHeight) / 2f;
			}
		}
		MyValue startValue = new MyValue(values[2], values[5], values[0], px, py, startAlpha);
		MyValue endValue = new MyValue(endX, endY, endScale, px, py, endAlpha);
		ValueAnimator animator = ValueAnimator.ofObject(new MyEvaluator(), startValue, endValue);
		animator.addUpdateListener(new MyAnimatorListener());
		animator.setDuration(300);
		animator.start();
		if (endScale != null && endScale == 1) {
			animator.addListener(new Animator.AnimatorListener() {

				@Override
				public void onAnimationStart(Animator animation) {
				}

				@Override
				public void onAnimationRepeat(Animator animation) {
				}

				@Override
				public void onAnimationEnd(Animator animation) {
					if (getFragmentManager() != null)
						getFragmentManager().popBackStack();
				}

				@Override
				public void onAnimationCancel(Animator animation) {
				}
			});
		}
	}

	public void setInertiaAnimation(Float velocityX, Float velocityY) {
		ValueAnimator animatorX = ValueAnimator.ofFloat(velocityX, 0);
		animatorX.addUpdateListener(new MyInertiaXAnimatorListener());
		animatorX.setDuration((long) Math.abs(velocityX));
		ValueAnimator animatorY = ValueAnimator.ofFloat(velocityY, 0);
		animatorY.addUpdateListener(new MyInertiaYAnimatorListener());
		animatorY.setDuration((long) Math.abs(velocityY));

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(animatorX).with(animatorY);
		animatorSet.start();
	}

	private class MyInertiaXAnimatorListener implements AnimatorUpdateListener {
		private Matrix mPrimaryMatrix;

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			currentAnimationX = animation;
			mPrimaryMatrix = new Matrix(imageView.getImageMatrix());
			float x = (Float) animation.getAnimatedValue() / 100;
			float[] values = new float[9];
			mPrimaryMatrix.getValues(values);
			// 滑动到边缘时调整
			if (x > Math.abs(values[2]) && x > 0) {
				x = Math.abs(values[2]);
				animation.cancel();
			}
			if (x < -(imageWidth * values[0] - Math.abs(values[2]) - windowWidth) && x < 0) {
				x = -(imageWidth * values[0] - Math.abs(values[2]) - windowWidth);
				animation.cancel();
			}
			if (x > -0.5 && x < 0.5) {
				animation.cancel();
			}
			mPrimaryMatrix.postTranslate(x, 0);

			imageView.setImageMatrix(mPrimaryMatrix);
		}
	}

	private class MyInertiaYAnimatorListener implements AnimatorUpdateListener {
		private Matrix mPrimaryMatrix;

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			currentAnimationY = animation;
			mPrimaryMatrix = new Matrix(imageView.getImageMatrix());
			float y = (Float) animation.getAnimatedValue() / 100;
			float[] values = new float[9];
			mPrimaryMatrix.getValues(values);
			// 滑动到边缘时调整
			if (y > Math.abs(values[5]) && y > 0) {
				y = Math.abs(values[5]);
				animation.cancel();
			}
			if (y < -(imageHeight * values[0] - Math.abs(values[5]) - windowHeight) && y < 0) {
				y = -(imageHeight * values[0] - Math.abs(values[5]) - windowHeight);
				animation.cancel();
			}
			if (y > -0.5 && y < 0.5)
				animation.cancel();
			mPrimaryMatrix.postTranslate(0, y);

			imageView.setImageMatrix(mPrimaryMatrix);
		}
	}

	/**
	 * 按下返回键时 调用
	 */
	public void onBackPressed() {
		setAnimation(initialX, initialY, 1f, null, null, 1, 0);
	}
}
