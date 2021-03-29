package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.notes.adapter.MainAdapter;
import com.example.notes.db.MyDbManager;


public class MainActivity extends AppCompatActivity {
    private MyDbManager myDbManager;
    private EditText title_id, desc_id;
    private RecyclerView rView;
    private MainAdapter mainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.head_menu, menu);
        MenuItem item = menu.findItem(R.id.search_id);
        SearchView search = (SearchView) item.getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainAdapter.updateAdapter(myDbManager.getDb(newText));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        myDbManager = new MyDbManager(this);
        title_id = findViewById(R.id.title_id);
        desc_id = findViewById(R.id.desc_id);
        rView = findViewById(R.id.rView);
        mainAdapter = new MainAdapter(this);
        rView.setLayoutManager(new LinearLayoutManager(this));
        getIth().attachToRecyclerView(rView);
        rView.setAdapter(mainAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        myDbManager.openDb();
        mainAdapter.updateAdapter(myDbManager.getDb(""));

    }

    public void onClickAdd(View view) {
        Intent add = new Intent(MainActivity.this, EditActivity.class);
        startActivity(add);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        myDbManager.closeDb();
    }

    private ItemTouchHelper getIth(){
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mainAdapter.removeItem(viewHolder.getAdapterPosition(), myDbManager);

            }
        });
    }

}