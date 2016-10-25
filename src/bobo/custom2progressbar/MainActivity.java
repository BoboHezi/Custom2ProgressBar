package bobo.custom2progressbar;

import java.util.Random;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {

	CustomProgress2Bar progress2Bar01;
	private static final int MSG_UPDATE = 0;
	private Handler handler1 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Random ran = new Random();
			int progress = progress2Bar01.getProgress();
			int secondprogress = progress2Bar01.getSecondProgress();
			progress2Bar01.setProgress(++progress);
			progress2Bar01.setSecondProgress(secondprogress + 2);

			if (progress >= 100) {
				handler1.removeMessages(MSG_UPDATE);
				return;
			}
			handler1.sendEmptyMessageDelayed(MSG_UPDATE,
					ran.nextInt(400) % (301));
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		progress2Bar01 = (CustomProgress2Bar) findViewById(R.id.progressbar01);
		handler1.sendEmptyMessage(MSG_UPDATE);
	}

}
