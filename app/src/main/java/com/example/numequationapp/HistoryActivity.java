package com.example.numequationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Equation> equations_history = new ArrayList<Equation>();
    DatabaseHelper mDBHelper;
    SQLiteDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        getHistory();

        recyclerView = (RecyclerView)findViewById(R.id.history_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EquationAdapter.OnEquationClickListener onEquationClickListener = new EquationAdapter.OnEquationClickListener() {
            @Override
            public void onEquationClick(Equation equation) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(Equation.class.getSimpleName(), equation);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        };

        EquationAdapter adapter = new EquationAdapter(this, equations_history, onEquationClickListener);

        recyclerView.setAdapter(adapter);
    }

    private void getHistory(){
        Cursor cursor = mDb.rawQuery("SELECT * FROM equations ORDER BY id DESC", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Equation equation = new Equation(cursor.getInt(0), cursor.getString(1));
            equations_history.add(equation);
            cursor.moveToNext();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDb.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        Intent intent;
        switch(id){
            case R.id.history_button:
                intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                return true;
            case R.id.manual_button:
                intent = new Intent(this, ManualActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}