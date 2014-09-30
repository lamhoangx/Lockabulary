package aics.uit.fragment;

import java.io.File;
import java.util.ArrayList;

import aics.uit.CacheVariant;
import aics.uit.R;
import aics.uit.activity.MainActivity;
import aics.uit.adapter.ListWordAdapter;
import aics.uit.item.ItemWord;
import aics.uit.lockscreen.LockScreenAppActivity;
import aics.uit.utils.UtilsManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class FragmentListWord extends SherlockFragment implements
		OnClickListener {
	private static final String TAG = "FragmentHome";
	private View v;
	public static Context activity;
	public static FragmentListWord fragmentListWord;
	public ListView lvListWord;
	public ListWordAdapter listWordAdapter;
	private Button btnNewListWord;
	private View footerView;

	public static FragmentListWord newInstance(int position, Context ac) {
		final FragmentListWord f = new FragmentListWord();
		final Bundle args = new Bundle();
		args.putInt("pos", position);
		f.setArguments(args);
		activity = ac;
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fragmentListWord = this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_list_word, container, false);
		lvListWord = (ListView) v.findViewById(R.id.lv_list_word);

		// lvListWord.addFooterView(btnNewListWord);
		footerView = inflater.inflate(R.layout.footer_new_list, null);
		lvListWord.addFooterView(footerView);
		File fileRoot = UtilsManager.instance().getCacheFolder(
				getActivity().getApplicationContext());
		String strPath = fileRoot.getPath() + "/fileListWord.txt";
		UtilsManager.lockabulary.ReadObject(strPath);

		FragmentListWord.fragmentListWord.setAdapterListWord();

		btnNewListWord = (Button) v.findViewById(R.id.btnNewListWord);
		btnNewListWord.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CacheVariant.arrayListWord = new ArrayList<ItemWord>();
				LockScreenAppActivity.saveNumberLife(10, getActivity());
				FragmentListWord.fragmentListWord.setAdapterListWord();
				MainActivity.mainActivity.pager.setCurrentItem(0);
			}
		});
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public void setAdapterListWord() {
		listWordAdapter = new ListWordAdapter(getActivity(),
				CacheVariant.arrayListWord, getResources());
		lvListWord.setAdapter(listWordAdapter);
	}
}
