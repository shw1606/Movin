package org.techtown.mission10;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import static org.techtown.mission10.MainActivity.addedRoutines;
import static org.techtown.mission10.MainActivity.database;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Fragment1 extends Fragment {
	private static final String TAG = "Fragment1";
	public static final int ROUTINE_START = 101;
	public static final String KEY_EXERCISE_DATA = "Routine Name";
	private static String selectedRoutine = "";
	RecyclerView recyclerView;
	Button StartButton;
	FragmentCallback callback;
	RoutineAdapter adapter;
	Button Add;
	EditText newRoutineName;
	int tableCount;
	String[] items;


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
		final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);


		recyclerView = rootView.findViewById(R.id.recyclerView);
		StartButton = rootView.findViewById(R.id.Start);
		newRoutineName = rootView.findViewById(R.id.newRoutineName);
		if (selectedRoutine != null) {
			StartButton.setText(selectedRoutine + " Start!");
		}

		Add = rootView.findViewById(R.id.Add);
		Add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String newRN = newRoutineName.getText().toString();
				if (newRN.contentEquals("")) {
					Toast.makeText(getContext(), "이름을 입력하세요.", Toast.LENGTH_LONG).show();
					return;
				}
				for (int i = 0; i < tableCount; i++) {
					if (items[i].contentEquals(newRN)) {
						Toast.makeText(getContext(), "이미 해당 이름의 루틴이 있습니다.", Toast.LENGTH_LONG).show();
						return;
					}
				}

				adapter.addItem(new routine(newRN, "임의 날짜"));
				recyclerView.setAdapter(adapter);
				addedRoutines.execSQL("create table if not exists " + newRN + "("
						+ "_id integer PRIMARY KEY autoincrement, "//0
						+ " name text, "//1
						+ " imageId integer, "//2
						+ " sets text, "//3
						+ " reps text, "//4
						+ " weight text, "//5
						+ " timeGap text, "//6
						+ " buttonText text, "//7
						+ " aorT integer)");
				Toast.makeText(getContext(), "추가되었습니다.", Toast.LENGTH_LONG).show();


			}
		});


		LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);
		adapter = new RoutineAdapter();
		Cursor cursor1 = addedRoutines.rawQuery("select * from sqlite_master WHERE type='table' AND name != 'android_metadata' AND name != 'sqlite_sequence';", null);
		tableCount = cursor1.getCount();
		items = new String[tableCount];
		for (int i = 0; i < tableCount; i++) {
			cursor1.moveToNext();
			String name = cursor1.getString(1);
			items[i] = name;
			adapter.addItem(new routine(name, "임의 날짜"));
		}
		cursor1.close();
		recyclerView.setAdapter(adapter);

		adapter.setOnItemClickListener(new OnRoutineItemClickListener() {
			@Override
			public void onItemClick(RoutineAdapter.ViewHolder holder, View view, int position) {
				if (holder.items.isShown())	holder.items.setVisibility(View.GONE);
				else holder.items.setVisibility(View.VISIBLE);
			}

			@Override
			public void onButtonClick(RoutineAdapter.ViewHolder holder, View view, int position) {
				selectedRoutine = holder.routineName.getText().toString();
				StartButton.setText(selectedRoutine + " Start!");
			}

			@Override
			public void onDeleteButtonClick(RoutineAdapter.ViewHolder viewHolder, View view, int position) {
				if (adapter.getItemCount() == 1) {
					Toast.makeText(getContext(), "최소 하나는 있어야죠...", Toast.LENGTH_LONG).show();
					return;
				}
				adapter.deleteItem(position);
				recyclerView.setAdapter(adapter);
				addedRoutines.execSQL("drop table " + viewHolder.routineName.getText().toString().replace("< ", "").replace(" >", ""));
			}
		});

		StartButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (selectedRoutine == "") {
					Toast.makeText(getContext(), "먼저 루틴을 선택해주세요." , Toast.LENGTH_LONG).show();
					return;
				}
				Intent intent = new Intent(getContext(), ExerciseActivity.class);
				ExerciseData data = new ExerciseData(selectedRoutine.replace("< ", "").replace(" >", ""));
				intent.putExtra(KEY_EXERCISE_DATA, data);
				startActivityForResult(intent, ROUTINE_START);
			}
		});

		return rootView;
	}
}
