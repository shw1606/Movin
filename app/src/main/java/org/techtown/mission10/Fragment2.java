package org.techtown.mission10;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static org.techtown.mission10.MainActivity.addedRoutines;
import static org.techtown.mission10.MainActivity.database;

public class Fragment2 extends Fragment {
	private static final String TAG = "Fragment2";
	RecyclerView addedToRoutineView;
	RecyclerView toAddRoutineView;
	ExerciseAdapter addedAdapter;
	ExerciseAdapter toAddAdapter;
	Spinner spinner;
	Button saveButton;
	String selectedRoutine = "myRoutine1";
	FragmentCallback callback;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof FragmentCallback) {
			callback = (FragmentCallback) context;
		} else {
			Log.d(TAG, "Activity is not FragmentCallback.");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

		addedToRoutineView = rootView.findViewById(R.id.addedToRoutine);
		toAddRoutineView = rootView.findViewById(R.id.toAddRoutine);
		spinner = rootView.findViewById(R.id.spinner);
		saveButton = rootView.findViewById(R.id.saveButton);


		Cursor cursor1 = addedRoutines.rawQuery("select * from sqlite_master WHERE type='table' AND name != 'android_metadata' AND name != 'sqlite_sequence';", null);
		int tableCount = cursor1.getCount();
		final String[] items = new String[tableCount];
		for (int i = 0; i < tableCount; i++) {
			cursor1.moveToNext();
			String name = cursor1.getString(1);
			System.out.println(name);
			items[i] = name;
		}
		cursor1.close();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this.getContext(), android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				selectedRoutine = items[i];
				Cursor cursoradded = addedRoutines.rawQuery("select * from " + selectedRoutine, null);
				int recordCount = cursoradded.getCount();
				addedAdapter.eraseAll();
				for (int j = 0; j < recordCount; j++) {
					cursoradded.moveToNext();
					String name = cursoradded.getString(1);
					int imageId = cursoradded.getInt(2);
					String sets = cursoradded.getString(3);
					String reps = cursoradded.getString(4);
					String weight = cursoradded.getString(5);
					String timeGap = cursoradded.getString(6);
					String ButtonText = cursoradded.getString(7);
					int aorTint = cursoradded.getInt(8);
					Boolean aorT = aorTint == 0 ? false : true;
					addedAdapter.addItem(new exercise(name, imageId, sets, reps, weight, timeGap, ButtonText, true));
				}
				cursoradded.close();
				addedToRoutineView.setAdapter(addedAdapter);

			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				selectedRoutine = "";
			}
		});

		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (selectedRoutine != "") {
					addedAdapter.saveToDatabase(selectedRoutine);
					Toast.makeText(getContext(), "저장되었습니다.", Toast.LENGTH_LONG).show();
				}
				else {
					Toast.makeText(getContext(), "먼저 루틴을 선택하세요.", Toast.LENGTH_LONG).show();
				}
			}
		});

		GridLayoutManager toAddlayoutManager = new GridLayoutManager(this.getContext(), 6);
		LinearLayoutManager addedlayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
		addedToRoutineView.setLayoutManager(addedlayoutManager);
		toAddRoutineView.setLayoutManager(toAddlayoutManager);
		addedAdapter = new ExerciseAdapter();
		toAddAdapter = new ExerciseAdapter();

		Cursor cursor = database.rawQuery("select * from toAddexercises", null);
		int recordCount = cursor.getCount();
		Toast.makeText(getContext(), Integer.toString(recordCount), Toast.LENGTH_LONG);
		for (int i = 0; i < recordCount; i++) {
			cursor.moveToNext();
			String name = cursor.getString(1);
			int imageId = cursor.getInt(2);
			String sets = cursor.getString(3);
			String reps = cursor.getString(4);
			String weight = cursor.getString(5);
			String timeGap = cursor.getString(6);
			String ButtonText = cursor.getString(7);
			int aorTint = cursor.getInt(8);
			Boolean aorT = aorTint == 0 ? false : true;
			toAddAdapter.addItem(new exercise(name, imageId, sets, reps, weight, timeGap, ButtonText, aorT));
		}
		cursor.close();// 되는지 안되는지 아직 아무것도 모름. 그냥 나이브하게 생각나는대로 적어본 것.
		//String name, String imageId, String sets, String reps, String weight, String timeGap
//		toAddAdapter.addItem(new exercise("푸시업", R.drawable.pushup, "5", "15", "0", "60", "+", false));
//		toAddAdapter.addItem(new exercise("턱걸이", R.drawable.pullup, "5", "15", "0", "60", "+", false));
//		toAddAdapter.addItem(new exercise("싯업", R.drawable.situp, "5", "15", "0", "60", "+", false));
//		toAddAdapter.addItem(new exercise("딥스", R.drawable.defaultpicture, "5", "15", "0", "60", "+", false));
//		toAddAdapter.addItem(new exercise("스쿼트", R.drawable.squat, "5", "15", "0", "60", "+", false));
//		toAddAdapter.addItem(new exercise("데드리프트",R.drawable.defaultpicture, "5", "15", "0", "60", "+", false));
//		toAddAdapter.addItem(new exercise("벤치프레스", R.drawable.defaultpicture, "5", "15", "0", "60", "+", false));
//		toAddAdapter.addItem(new exercise("레그익스텐션", R.drawable.defaultpicture, "5", "15", "0", "60", "+", false));
//		toAddAdapter.addItem(new exercise("허정훈", R.drawable.defaultpicture, "5", "15", "0", "60", "+", false));
//		toAddAdapter.addItem(new exercise("권영진", R.drawable.defaultpicture, "5", "15", "0", "60", "+", false));
		if (selectedRoutine != "") {
			Cursor cursoradded = addedRoutines.rawQuery("select * from " + selectedRoutine, null);
			recordCount = cursoradded.getCount();
			for (int i = 0; i < recordCount; i++) {
				cursoradded.moveToNext();
				String name = cursoradded.getString(1);
				int imageId = cursoradded.getInt(2);
				String sets = cursoradded.getString(3);
				String reps = cursoradded.getString(4);
				String weight = cursoradded.getString(5);
				String timeGap = cursoradded.getString(6);
				String ButtonText = cursoradded.getString(7);
				int aorTint = cursoradded.getInt(8);
				Boolean aorT = aorTint == 0 ? false : true;
				addedAdapter.addItem(new exercise(name, imageId, sets, reps, weight, timeGap, ButtonText, true));
			}
			cursoradded.close();
		}

		toAddRoutineView.setAdapter(toAddAdapter);
		addedToRoutineView.setAdapter(addedAdapter);

		toAddAdapter.setOnItemClickListener(new OnExerciseItemClickListener() {
			@Override
			public void onItemClick(ExerciseAdapter.ViewHolder holder, View view, int position, EditText Sets, EditText Reps, EditText Weight, EditText timeGap, Button addOrDelete) {
				if (selectedRoutine == "") {
					Toast.makeText(getContext(), "먼저 루틴을 선택하세요.", Toast.LENGTH_LONG).show();
				}
				else {
					exercise selecteditem = toAddAdapter.getItem(position);
					exercise addingitem = new exercise(selecteditem.getName(), selecteditem.getImageId(), Sets.getText().toString(),
							Reps.getText().toString(), Weight.getText().toString(), timeGap.getText().toString(), "-", true);
					addedAdapter.addItem(addingitem);
					addedToRoutineView.setAdapter(addedAdapter);
				}
			}

			@Override
			public void onButtonClick(ExerciseAdapter.ViewHolder holder, View view, int position, EditText Sets, EditText Reps, EditText Weight, EditText timeGap, Button addOrDelete) {
			}

			@Override
			public void onInsert(int position, EditText Target, int dataIndex) {

			}
		});
		addedAdapter.setOnItemClickListener(new OnExerciseItemClickListener() {
			@Override
			public void onItemClick(ExerciseAdapter.ViewHolder holder, View view, int position, EditText Sets, EditText Reps, EditText Weight, EditText timeGap, Button addOrDelete) {
//				exercise item = toAddAdapter.getItem(position);
//				addedAdapter.addItem(item);
			}

			@Override
			public void onButtonClick(ExerciseAdapter.ViewHolder holder, View view, int position, EditText Sets, EditText Reps, EditText Weight, EditText timeGap, Button addOrDelete) {
				addedAdapter.deleteItem(position);
				addedToRoutineView.setAdapter(addedAdapter);
			}

			@Override
			public void onInsert(int position, EditText Target, int dataIndex) {
				exercise item = addedAdapter.getItem(position);
				switch(dataIndex) {
					case 1: item.setSets(Target.getText().toString());
						break;
					case 2: item.setReps(Target.getText().toString());
						break;
					case 3: item.setWeight(Target.getText().toString());
						break;
					case 4: item.setTimeGap(Target.getText().toString());
						break;
					default:
						break;
				}
			}
		});
		return rootView;
	}

	
	
}
