package org.techtown.mission10;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import static org.techtown.mission10.MainActivity.addedRoutines;
import static org.techtown.mission10.MainActivity.database;

import java.util.ArrayList;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder> implements OnRoutineItemClickListener {
    ArrayList<routine> items = new ArrayList<routine>();
    OnRoutineItemClickListener listener;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.routine_item, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        routine item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(routine item) {
        items.add(item);
    }

    public void setItems(ArrayList<routine> items) {
        this.items = items;
    }

    public routine getItem(int position) {
        return items.get(position);
    }

    public routine setItem(int position, routine item) {
        return items.set(position, item);
    }

    public void setOnItemClickListener(OnRoutineItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    @Override
    public void onButtonClick(RoutineAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onButtonClick(holder, view, position);
        }
    }

    @Override
    public void onDeleteButtonClick(ViewHolder viewHolder, View view, int position) {
        if (listener != null) {
            listener.onDeleteButtonClick(viewHolder, view, position);
        }
    }

    public void deleteItem(int position) {
        items.remove(position);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView routineName;
        TextView date;
        TextView items;
        Button delete;
        Button switch2;

        public ViewHolder(View itemView, final OnRoutineItemClickListener listener) {
            super(itemView);

            routineName = itemView.findViewById(R.id.routineName);
            date = itemView.findViewById(R.id.date);
            items = itemView.findViewById(R.id.items);
            switch2 = itemView.findViewById(R.id.switch2);
            delete = itemView.findViewById(R.id.delete);

            switch2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onButtonClick(ViewHolder.this, view, position);
                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onDeleteButtonClick(ViewHolder.this, view, position);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }

                }
            });
        }

        public void setItem(routine item) {
            routineName.setText("< " + item.getRoutineName() + " >");
            date.setText(item.getDate());
            String itemString = "";
            Cursor cursoradded = addedRoutines.rawQuery("select * from " + item.getRoutineName(), null);
            int recordCount = cursoradded.getCount();

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
                itemString += "\r\n";
                itemString += Integer.toString(j + 1);
                itemString += formString(name, sets, reps, weight, timeGap);
                itemString += "\r\n";
            }
            cursoradded.close();
            items.setText(itemString);
        }

        public String formString(String name, String sets, String reps, String weight, String timeGap) {
            return ". " + name + " :  " + reps + "회,   " + sets + "세트,   " + weight + " 중량 설정,   " + timeGap + "초 휴식간격";
        }
    }
}
