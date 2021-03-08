package org.techtown.mission10;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.view.View.GONE;
import static org.techtown.mission10.MainActivity.addedRoutines;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> implements OnExerciseItemClickListener {
    OnExerciseItemClickListener listener;
    ArrayList<exercise> items = new ArrayList<exercise>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.exercise_item, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        exercise item = items.get(position);
        holder.setItem(item);
        holder.setAddedorToAdd(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setOnItemClickListener(OnExerciseItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position, EditText Sets, EditText Reps, EditText Weight, EditText timeGap, Button addOrDelete) {
        if (listener != null) {
            listener.onItemClick(holder, view, position, Sets, Reps, Weight, timeGap, addOrDelete);
        }
    }

    @Override
    public void onButtonClick(ViewHolder holder, View view, int position, EditText Sets, EditText Reps, EditText Weight, EditText timeGap, Button addOrDelete) {
        if (listener != null) {
            listener.onButtonClick(holder, view, position, Sets, Reps, Weight, timeGap, addOrDelete);
        }
    }

    @Override
    public void onInsert(int position, EditText Target, int dataIndex) {
        if (listener != null) {
            listener.onInsert(position,Target,dataIndex);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView exer_name;
        EditText Sets;
        EditText Reps;
        EditText Weight;
        EditText timeGap;
        Button addOrDelete;
        LinearLayout details;
        ImageView icon;

        public ViewHolder(View itemView, final OnExerciseItemClickListener listener) {
            super(itemView);
            exer_name = itemView.findViewById(R.id.exer_name);
            Sets = itemView.findViewById(R.id.Sets);
            Reps = itemView.findViewById(R.id.Reps);
            Weight = itemView.findViewById(R.id.Weight);
            timeGap = itemView.findViewById(R.id.timeGap);
            addOrDelete = itemView.findViewById(R.id.addOrDelete);
            details = itemView.findViewById(R.id.details);
            icon = itemView.findViewById(R.id.icon);

            addOrDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onButtonClick(ViewHolder.this, view, position, Sets, Reps, Weight, timeGap, addOrDelete);
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position, Sets, Reps, Weight, timeGap, addOrDelete);
                    }
                }

            });
            Sets.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onInsert(position, Sets, 1);
                    }
                }
            });
            Reps.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onInsert(position, Reps, 2);
                    }
                }
            });
            Weight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onInsert(position, Weight, 3);
                    }
                }
            });
            timeGap.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onInsert(position, timeGap, 4);
                    }
                }
            });




        }
        public void setAddedorToAdd(exercise item) {
            if (!item.isAdded()) {

                itemView.findViewById(R.id.details).setVisibility(GONE);
            }
        }
        public void setItem(exercise item) {

            exer_name.setText(item.getName());
            Sets.setText(item.getSets());
            Reps.setText(item.getReps());
            Weight.setText(item.getWeight());
            timeGap.setText(item.getTimeGap());
            addOrDelete.setText(item.getButtonText());
            if (item.getImageId() != 0) {
                icon.setImageResource(item.getImageId());
            }
            item.setSets(Sets.getText().toString());
            item.setReps(Reps.getText().toString());
            item.setWeight(Weight.getText().toString());
            item.setTimeGap(timeGap.getText().toString());


        }

    }
    public void addItem(exercise item) {
        items.add(item);
    }
    public void setItems(ArrayList<exercise> items) {
        this.items = items;
    }
    public exercise getItem(int position) {
        return items.get(position);
    }
    public void setItem(int position, exercise item) {
        items.set(position, item);
    }
    public void deleteItem(int position) {
        items.remove(position);
    }
    public void eraseAll() { items.clear(); }
    public void saveToDatabase(String tableName) {

        addedRoutines.execSQL("delete from " + tableName);
        for (int i = 0; i < items.size(); i++) {
            String name = items.get(i).getName();
            int imageId = items.get(i).getImageId();
            String sets = items.get(i).getSets();
            String reps = items.get(i).getReps();
            String weight = items.get(i).getWeight();
            String timeGap = items.get(i).getTimeGap();
            String ButtonText = items.get(i).getButtonText();
            Boolean aorT = items.get(i).isAdded();
            int aorTint = aorT ? 1 : 0;
            addedRoutines.execSQL("insert into " + tableName + "(name, imageId, sets, reps, weight, timeGap, buttonText, aorT) " + " values ("
                    + "'"+name +"',"+ imageId +","+ "'"+sets +"',"+ "'"+reps +"',"+ "'"+weight +"',"+ "'"+timeGap +"',"+ "'"+ButtonText +"',"+ aorTint + ")");
        }


    }
}
