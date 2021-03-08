package org.techtown.mission10;

import android.view.View;

public interface OnRoutineItemClickListener {
    public void onItemClick(RoutineAdapter.ViewHolder holder, View view, int position);
    public void onButtonClick(RoutineAdapter.ViewHolder holder, View view, int position);

    public void onDeleteButtonClick(RoutineAdapter.ViewHolder viewHolder, View view, int position);
}
