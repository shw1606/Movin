package org.techtown.mission10;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback {

    public static SQLiteDatabase database;
    public static SQLiteDatabase addedRoutines;
    Toolbar toolbar;

    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        getSupportActionBar().setTitle("Movin");
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();

        database = openOrCreateDatabase("toAddData", MODE_NO_LOCALIZED_COLLATORS, null);
        addedRoutines = openOrCreateDatabase("addedData", MODE_NO_LOCALIZED_COLLATORS, null);


        database.execSQL("create table if not exists " + "toAddexercises" + "("
                + "_id integer PRIMARY KEY autoincrement, "//0
                + " name text, "//1
                + " imageId integer, "//2
                + " sets text, "//3
                + " reps text, "//4
                + " weight text, "//5
                + " timeGap text, "//6
                + " buttonText text, "//7
                + " aorT integer)");//8
        database.execSQL("delete from toAddexercises");
        database.execSQL("insert into " + "toAddexercises" + "(name, imageId, sets, reps, weight, timeGap, buttonText, aorT) " + " values "
                + "( '푸시업',"+ R.drawable.pushup + ", '5', '15', '0', '60', '+', 0)");
        database.execSQL("insert into " + "toAddexercises" + "(name, imageId, sets, reps, weight, timeGap, buttonText, aorT) " + " values "
                + "(\"턱걸이\"," + R.drawable.pullup + ", \"5\", \"15\", \"0\", \"60\", \"+\", 0)");
        database.execSQL("insert into " + "toAddexercises" + "(name, imageId, sets, reps, weight, timeGap, buttonText, aorT) " + " values "
                + "(\"싯업\"," + R.drawable.situp + ", \"5\", \"15\", \"0\", \"60\", \"+\", 0)");
        database.execSQL("insert into " + "toAddexercises" + "(name, imageId, sets, reps, weight, timeGap, buttonText, aorT) " + " values "
                + "( \"딥스\"," + R.drawable.defaultpicture + ", \"5\", \"15\", \"0\", \"60\", \"+\", 0)");
        database.execSQL("insert into " + "toAddexercises" + "(name, imageId, sets, reps, weight, timeGap, buttonText, aorT) " + " values "
                + "( \"스쿼트\"," + R.drawable.squat + ", \"5\", \"15\", \"0\", \"60\", \"+\", 0)");
        database.execSQL("insert into " + "toAddexercises" + "(name, imageId, sets, reps, weight, timeGap, buttonText, aorT) " + " values "
                + "(\"데드리프트\"," +R.drawable.defaultpicture + ", \"5\", \"15\", \"0\", \"60\", \"+\", 0)");
        database.execSQL("insert into " + "toAddexercises" + "(name, imageId, sets, reps, weight, timeGap, buttonText, aorT) " + " values "
                + "(\"벤치프레스\"," + R.drawable.defaultpicture + ", \"5\", \"15\", \"0\", \"60\", \"+\", 0)");
        database.execSQL("insert into " + "toAddexercises" + "(name, imageId, sets, reps, weight, timeGap, buttonText, aorT) " + " values "
                + "( \"레그익스텐션\"," + R.drawable.defaultpicture + ", \"5\", \"15\", \"0\", \"60\", \"+\", 0)");
        database.execSQL("insert into " + "toAddexercises" + "(name, imageId, sets, reps, weight, timeGap, buttonText, aorT) " + " values "
                + "(\"허정훈\"," + R.drawable.defaultpicture + ", \"5\", \"15\", \"0\", \"60\", \"+\", 0)");
        database.execSQL("insert into " + "toAddexercises" + "(name, imageId, sets, reps, weight, timeGap, buttonText, aorT) " + " values "
                + "( \"권영진\"," + R.drawable.defaultpicture + ", \"5\", \"15\", \"0\", \"60\", \"+\", 0)");

        addedRoutines.execSQL("create table if not exists " + "myRoutine1" + "("
                + "_id integer PRIMARY KEY autoincrement, "//0
                + " name text, "//1
                + " imageId integer, "//2
                + " sets text, "//3
                + " reps text, "//4
                + " weight text, "//5
                + " timeGap text, "//6
                + " buttonText text, "//7
                + " aorT integer)");
        //addedRoutines.execSQL("delete from myRoutine1");
        //addedRoutines.execSQL("insert into " + "myRoutine1" + "(name, imageId, sets, reps, weight, timeGap, buttonText, aorT) " + " values "
          //      + "( \"푸시업\"," + R.drawable.pushup+ ", \"5\", \"15\", \"0\", \"60\", \"+\", 0)");
        addedRoutines.execSQL("create table if not exists " + "myRoutine2" + "("
                + "_id integer PRIMARY KEY autoincrement, "//0
                + " name text, "//1
                + " imageId integer, "//2
                + " sets text, "//3
                + " reps text, "//4
                + " weight text, "//5
                + " timeGap text, "//6
                + " buttonText text, "//7
                + " aorT integer)");
        addedRoutines.execSQL("create table if not exists " + "myRoutine3" + "("
                + "_id integer PRIMARY KEY autoincrement, "//0
                + " name text, "//1
                + " imageId integer, "//2
                + " sets text, "//3
                + " reps text, "//4
                + " weight text, "//5
                + " timeGap text, "//6
                + " buttonText text, "//7
                + " aorT integer)");
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        Toast.makeText(getApplicationContext(), "루틴 선택 및 운동 시작", Toast.LENGTH_LONG).show();


                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment1).commit();

                        toolbar.setTitle("Movin");

                        return true;
                    case R.id.tab2:
                        Toast.makeText(getApplicationContext(), "루틴 설정", Toast.LENGTH_LONG).show();


                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment2).commit();

                        toolbar.setTitle("Movin");

                        return true;
                    case R.id.tab3:
                        Toast.makeText(getApplicationContext(), "프로필 미완", Toast.LENGTH_LONG).show();


                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment3).commit();

                        toolbar.setTitle("Movin");

                        return true;
                }

                return false;
            }
        });

    }


    @Override
    public void onFragmentSelected(int position, Bundle bundle) {
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        Fragment curFragment = null;
        if (position == 0) {
            curFragment = new Fragment1();
            toolbar.setTitle("첫번째 화면");
            bottomNavigation.setSelectedItemId(R.id.tab1);
        } else if (position == 1) {
            curFragment = new Fragment2();
            toolbar.setTitle("두번째 화면");
            bottomNavigation.setSelectedItemId(R.id.tab2);
        } else if (position == 2) {
            curFragment = new Fragment3();
            toolbar.setTitle("세번째 화면");
            bottomNavigation.setSelectedItemId(R.id.tab3);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_0) {
            onFragmentSelected(0, null);
        } else if (id == R.id.nav_1) {
            onFragmentSelected(1, null);
        } else if (id == R.id.nav_2) {
            onFragmentSelected(2, null);
        } else if (id == R.id.nav_user_setting) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
