package aics.uit.fragment;

import java.io.File;

import aics.uit.CacheVariant;
import aics.uit.R;
import aics.uit.adapter.ListWordAdapter;
import aics.uit.utils.UtilsManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class FragmentLockScreenShowList extends SherlockFragment implements
		OnClickListener {
	private static final String TAG = "FragmentLockScreenLishWord";
	private View v;
	private ListView lvListWords;
	private ListWordAdapter listWordLockScreenAdapter;

	public static FragmentLockScreenShowList newInstance(int position) {
		final FragmentLockScreenShowList f = new FragmentLockScreenShowList();
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
		v = inflater.inflate(R.layout.fragment_lock_screen_list_word,
				container, false);
		lvListWords = (ListView) v.findViewById(R.id.lv_list_word_fragment);

		File fileRoot = UtilsManager.instance().getCacheFolder(
				getActivity().getApplicationContext());
		String strPath = fileRoot.getPath() + "/fileListWord.txt";
		UtilsManager.lockabulary.ReadObject(strPath);
		setAdapterListWord();

		return v;
	}

	public void setAdapterListWord() {
		listWordLockScreenAdapter = new ListWordAdapter(getActivity(),
				CacheVariant.arrayListWord, getResources());
		lvListWords.setAdapter(listWordLockScreenAdapter);
	}

	@Override
	public void onResume() {

		super.onResume();
	}

	@Override
	public void onClick(View v) {

	}
}
