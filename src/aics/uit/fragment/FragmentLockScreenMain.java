package aics.uit.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import aics.uit.CacheVariant;
import aics.uit.R;
import aics.uit.activity.LockScreenActivity;
import aics.uit.item.ItemWord;
import aics.uit.lockscreen.LockScreenAppActivity;
import aics.uit.utils.UtilsManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class FragmentLockScreenMain extends SherlockFragment implements
		OnClickListener {
	private static final String TAG = "FragmentLockScreen";
	private View v;
	public ArrayList<ItemWord> itemWords;

	private Button btnUnlock, btnOver;
	private TextView tvWord, tvTimerWaitting;
	private EditText edInputMean;
	public static FragmentLockScreenMain lockScreenAppActivity;
	int iCheck = 0;

	public static FragmentLockScreenMain newInstance(int position) {
		final FragmentLockScreenMain f = new FragmentLockScreenMain();
		final Bundle args = new Bundle();
		args.putInt("pos", position);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_lock_screen, container, false);

		lockScreenAppActivity = this;
		edInputMean = (EditText) v.findViewById(R.id.edLoginInputMean);
		tvWord = (TextView) v.findViewById(R.id.tvLoginWord);
		tvTimerWaitting = (TextView) v.findViewById(R.id.tvTimerWait);
		btnUnlock = (Button) v.findViewById(R.id.btnSubmit);
		btnOver = (Button) v.findViewById(R.id.btnOver);

		itemWords = new ArrayList<ItemWord>();
		File fileRoot = getCacheFolder(getActivity().getBaseContext());
		String strPath = fileRoot.getPath() + "/fileListWord.txt";
		ReadObject(strPath);

		String str = "";

		if (CacheVariant.arrayListWord.size() > 0) {
			iCheck = getWord(CacheVariant.arrayListWord);
			str = CacheVariant.arrayListWord.get(iCheck).getWord();
		}

		tvWord.setText(str);

		int iNumberLife = getNumberLife(getActivity().getBaseContext());
		btnOver.setText("Over (" + iNumberLife + ")");

		if (getActivity().getIntent() != null
				&& getActivity().getIntent().hasExtra("kill")
				&& getActivity().getIntent().getExtras().getInt("kill") == 1) {
			finish();
		}

		btnUnlock.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int iPass = 0;
				if (CacheVariant.arrayListWord.size() > 0
						&& edInputMean
								.getText()
								.toString()
								.equals(CacheVariant.arrayListWord.get(iCheck)
										.getMean())) {
					iPass = CacheVariant.arrayListWord.get(iCheck).getPass() + 1;
					CacheVariant.arrayListWord.get(iCheck).setPass(iPass);
					finish();
				} else if (CacheVariant.arrayListWord.size() == 0) {
					finish();
				} else {
					if (iPass > 0)
						iPass = CacheVariant.arrayListWord.get(iCheck)
								.getPass() - 1;
					CacheVariant.arrayListWord.get(iCheck).setPass(iPass);
					Toast.makeText(getActivity().getBaseContext(),
							"Wrong, please input again!", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		btnOver.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int iTempNow = getNumberLife(getActivity().getBaseContext());
				if (iTempNow > 0) {
					LockScreenAppActivity.saveNumberLife(iTempNow - 1,
							getActivity().getBaseContext());
					edInputMean.setText(CacheVariant.arrayListWord.get(iCheck)
							.getMean());
					new CountDownTimer(1000, 1000) {

						@Override
						public void onTick(long millisUntilFinished) {

						}

						@Override
						public void onFinish() {
							finish();
						}
					}.start();

				} else {
					edInputMean.setText(CacheVariant.arrayListWord.get(iCheck)
							.getMean());
					new CountDownTimer(10000, 1000) {

						@Override
						public void onTick(long millisUntilFinished) {

							int iTimerNowRun = (int) millisUntilFinished / 1000;
							tvTimerWaitting.setText("Watting for "
									+ iTimerNowRun + " second to unlock.");

						}

						@Override
						public void onFinish() {
							finish();
						}
					}.start();
				}

			}
		});

		return v;
	}

	public int getWord(ArrayList<ItemWord> itemWord) {
		int iSizeList = itemWord.size();

		int arr[] = new int[iSizeList];
		Integer in[] = new Integer[iSizeList];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
			in[i] = new Integer(arr[i]);
		}

		List randomList = get(Arrays.asList(in), 1);

		return (Integer) randomList.get(0);
	}

	public static List get(List numbers, int noOfRandomNumbers) {
		List list = new ArrayList(numbers);
		List randomList = new ArrayList();

		Random rd = new Random();
		for (int i = 0; i < noOfRandomNumbers; i++) {
			int index = (int) (rd.nextDouble() * list.size());
			randomList.add(list.get(index));
			list.remove(index);
		}
		return randomList;
	}

	public static void saveNumberLife(int accessToken, Context context) {
		SharedPreferences saveToken = context.getSharedPreferences("myData",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = saveToken.edit();
		editor.putInt("accessToken", accessToken);
		editor.commit();
	}

	public void finish() {
		File fileRoot = getCacheFolder(getActivity().getBaseContext());
		String strPath = fileRoot.getPath() + "/fileListWord.txt";
		WriteObject(CacheVariant.arrayListWord, strPath);

		LockScreenActivity.lockScreenActivity.finishActivity();
	}

	public void WriteObject(ArrayList<ItemWord> itemWord, String strFileName) {
		ObjectOutputStream oos = null;
		FileOutputStream fout;
		try {
			fout = new FileOutputStream(strFileName);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(itemWord);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void ReadObject(String strFileName) {
		ObjectInputStream obInputStream = null;
		FileInputStream streamIn;

		try {
			streamIn = new FileInputStream(strFileName);
			obInputStream = new ObjectInputStream(streamIn);
			ArrayList<ItemWord> itemWordTemp = (ArrayList<ItemWord>) obInputStream
					.readObject();
			CacheVariant.arrayListWord = itemWordTemp;
			Log.i("", "Read object finish");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public File addFordel(File fileRoot, String fordelName) {
		File fileBase = new File(fileRoot, fordelName);
		if (!fileBase.isDirectory()) {
			fileBase.mkdirs();
		}
		return fileBase;
	}

	public File getCacheFolder(Context context) {
		File cacheDir = null;
		// File pathCacheDir = context.getCacheDir();
		// cacheDir = addFordel(pathCacheDir, "Apollo");
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(Environment.getExternalStorageDirectory(),
					"Lockabulary");
			if (!cacheDir.isDirectory()) {
				cacheDir.mkdirs();
			}
		}

		if (!cacheDir.isDirectory()) {
			cacheDir = context.getCacheDir(); // get system cache folder
		}

		return cacheDir;
	}

	public static int getNumberLife(Context context) {
		SharedPreferences getToken = context.getSharedPreferences("myData",
				Context.MODE_PRIVATE);
		return getToken.getInt("accessToken", 0);
	}

	public void onDestroy() {
		// k1.reenableKeyguard();

		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
