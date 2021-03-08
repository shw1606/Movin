package org.techtown.mission10;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public interface OnExerciseItemClickListener {
    public void onItemClick(ExerciseAdapter.ViewHolder holder, View view, int position, EditText Sets, EditText Reps, EditText Weight, EditText timeGap, Button addOrDelete);
    public void onButtonClick(ExerciseAdapter.ViewHolder holder, View view, int position, EditText Sets, EditText Reps, EditText Weight, EditText timeGap, Button addOrDelete);
    public void onInsert(int position, EditText Target, int dataIndex);
}
