package org.techtown.mission10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static org.techtown.mission10.MainActivity.addedRoutines;


public class ExerciseActivity extends AppCompatActivity {
    TextView RoutineData;
    TextView thisTurn;
    Button button;
    Button mainButton;
    public static final String KEY_EXERCISE_DATA = "Routine Name";
    private static String selectedRoutine = "";
    int index = 0;
    int recordCount = 0;
    String[][] routineTable;
    int[][] countingTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Toast.makeText(getApplicationContext(), "최초 실행", Toast.LENGTH_LONG).show();
        RoutineData = findViewById(R.id.RoutineData);
        thisTurn = findViewById(R.id.thisTurn);
        thisTurn.setTextColor(Color.parseColor("#40C2BB"));
        button = findViewById(R.id.button);
        mainButton = findViewById(R.id.counter);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trigger();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("status", "중단");
                setResult(RESULT_CANCELED, intent);

                finish();
            }
        });
        Intent intent = getIntent();
        processIntent(intent);

        String itemString = selectedRoutine;
        Cursor cursoradded = addedRoutines.rawQuery("select * from " + selectedRoutine, null);
        recordCount = cursoradded.getCount();
        routineTable = new String[recordCount][6];
        countingTable = new int[recordCount][6];

        for (int j = 0; j < recordCount; j++) {
            cursoradded.moveToNext();
            String name = cursoradded.getString(1);
            routineTable[j][0] = name;
            int imageId = cursoradded.getInt(2);
            routineTable[j][1] = Integer.toString(imageId);
            String sets = cursoradded.getString(3);
            routineTable[j][2] = sets;
            countingTable[j][2] = 0;
            String reps = cursoradded.getString(4);
            routineTable[j][3] = reps;
            countingTable[j][3] = 0;
            String weight = cursoradded.getString(5);
            routineTable[j][4] = weight;
            countingTable[j][4] =0;
            String timeGap = cursoradded.getString(6);
            routineTable[j][5] = timeGap;
            countingTable[j][5] = 0;
            itemString += "\r\n";
            itemString += Integer.toString(j + 1);
            itemString += formString(name, sets, reps, weight, timeGap);
            itemString += "\r\n";
        }
        cursoradded.close();
        RoutineData.setText(itemString);
    }

    public String formString(String name, String sets, String reps, String weight, String timeGap) {
        return ". " + name + ": " + reps + "회, " + sets + "세트, " + weight + "kg, " + timeGap + "초 간격";
    }

    public String formString_forGo(String name, String sets, String reps, String weight, String timeGap) {
        return "Go: " + name + "\r\n" +
                reps + "회" + "\r\n" +
                sets + "세트" + "\r\n" +
                weight + "kg" + "\r\n" +
                timeGap + "초 휴식간격";
    }

    private void processIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            ExerciseData data = bundle.getParcelable(KEY_EXERCISE_DATA);
            if (intent != null) {
                selectedRoutine = data.routineName;
            }
        }
    }

    private void Trigger() {
        String inputString = "";
        if (index == 0) {
            index++;
            inputString = formString_forGo(routineTable[index-1][0],routineTable[index-1][2],routineTable[index-1][3],routineTable[index-1][4],routineTable[index-1][5])
                    .replace(". ", "Go: ");
            thisTurn.setText(inputString);

            mainButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_shape_green));
            mainButton.setText(Integer.toString(countingTable[index-1][3]) + " reps" + "\r\n" +
                    countingTable[index-1][2] + " sets" + "\r\n" + "Going");

        } else if (countingTable[index-1][3] + 1 == Integer.parseInt(routineTable[index-1][3]) && countingTable[index-1][2] < Integer.parseInt(routineTable[index-1][2]) - 1) {
            // 현재 카운팅테이블의 횟수가 루틴테이블의 횟수보다 1적고,
            // 현재 카운팅테이블의 세트수가 루틴테이블의 세트수보다 2이상 적을 때: 1세트 끝나고, 아직 싸이클이 다 끝나지 않았을 때: 휴식 갭.
            countingTable[index-1][3]++;
            countingTable[index-1][2]++;
            mainButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_shape_red));
            mainButton.setText(Integer.toString(countingTable[index-1][3]) + " reps" + "\r\n" +
                    countingTable[index-1][2] + " sets" + "\r\n" + "Done");
            countingTable[index-1][3] = 0;
        } else if (countingTable[index-1][3] + 1 == Integer.parseInt(routineTable[index-1][3]) && countingTable[index-1][2] == Integer.parseInt(routineTable[index-1][2]) - 1) {
            // 현재 카운팅테이블의 횟수가 루틴테이블의 횟수보다 1적고,
            // 현재 카운팅테이블의 세트수가 루틴테이블의 세트수보다 1 적을 때: 한 싸이클 완전히 끝났을 때.
            // 횟수를 달리하기 위해 같은 종목 같은 휴식갭의 운동을 다른 싸이클로 따로 작성한 경우를 어떻게 해야할 지 생각해야 함.
            // 일단은 똑같이 휴식이라고 보고, 빨간불로 바꾸고 텍스트만 달리 해놓았다.
            countingTable[index-1][3]++;
            countingTable[index-1][2]++;
            mainButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_shape_red));
            mainButton.setText(routineTable[index-1][0] + "끝남");
            index++;
            if (index <= recordCount) {
                inputString = formString_forGo(routineTable[index-1][0],routineTable[index-1][2],routineTable[index-1][3],routineTable[index-1][4],routineTable[index-1][5])
                        .replace(". ", "Go: ");
                thisTurn.setText(inputString);
            } else {
                thisTurn.setText("끝났습니다!");
            }
        } else if (countingTable[index-1][2] < Integer.parseInt(routineTable[index-1][2]) && countingTable[index-1][3] < Integer.parseInt(routineTable[index-1][3])) {
            // 현재 카운팅테이블의 횟수와 세트수가 루틴테이블의 횟수와 세트수보다 적을 때: 운동 중 일 때
            countingTable[index-1][3]++;
            mainButton.setText(Integer.toString(countingTable[index-1][3]) + " reps" + "\r\n" +
                    countingTable[index-1][2] + " sets" + "\r\n" + "Going");
            mainButton.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_shape_green));
        }

        if (index > recordCount) {
            Intent intent = new Intent();
            intent.putExtra("status", "완료");
            setResult(RESULT_OK, intent);

            finish();
        }
    }
}