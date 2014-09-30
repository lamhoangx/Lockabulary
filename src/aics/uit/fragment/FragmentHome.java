package aics.uit.fragment;

import java.io.File;
import java.util.ArrayList;

import aics.uit.CacheVariant;
import aics.uit.R;
import aics.uit.activity.LockScreenActivity;
import aics.uit.activity.MainActivity;
import aics.uit.item.ItemWord;
import aics.uit.lockscreen.LockScreenAppActivity;
import aics.uit.utils.UtilsManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class FragmentHome extends SherlockFragment implements OnClickListener {
	private static final String TAG = "FragmentHome";
	private View v;
	private EditText edInputWord, edInputMean;
	private Button btnAdd, btnTest;

	public static FragmentHome newInstance(int position) {
		final FragmentHome f = new FragmentHome();
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
		v = inflater.inflate(R.layout.fragment_home, container, false);
		edInputMean = (EditText) v.findViewById(R.id.edInputMean);
		edInputWord = (EditText) v.findViewById(R.id.edInputWord);

		btnAdd = (Button) v.findViewById(R.id.btnAdd);
		
		
		
		btnAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!edInputMean.getText().toString().equals("")
						&& !edInputWord.getText().toString().equals("")) {
					ItemWord itemWord = new ItemWord();
					itemWord.setId(CacheVariant.iNumberOrder);
					itemWord.setMean(edInputMean.getText().toString());
					itemWord.setWord(edInputWord.getText().toString());
					itemWord.setPass(0);
					CacheVariant.iNumberOrder++;
					CacheVariant.arrayListWord.add(itemWord);

					FragmentListWord.fragmentListWord.setAdapterListWord();

					edInputMean.setText("");
					edInputWord.setText("");
					File fileRoot = UtilsManager.instance().getCacheFolder(
							getActivity().getApplicationContext());
					String strPath = fileRoot.getPath() + "/fileListWord.txt";
					UtilsManager.lockabulary.WriteObject(
							CacheVariant.arrayListWord, strPath);

				} else {
					Toast.makeText(getActivity().getBaseContext(),
							"Input error, check again!", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

		return v;
	}

	@Override
	public void onResume() {

		super.onResume();
	}

	@Override
	public void onClick(View v) {

	}

}
