package com.example.fabexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FabExampleActivity extends AppCompatActivity {

    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_example);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myListView = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        myListView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListItem();
                Snackbar.make(view, "Item added to list", Snackbar.LENGTH_LONG)
                        .setAction("Undo", undoOnClickListener).show();
            }
        });
    }


    View.OnClickListener undoOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            listItems.remove(listItems.size() - 1);
            adapter.notifyDataSetChanged();
            Snackbar.make(view,"Item removed",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }
    };

    private void addListItem() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.US);
        listItems.add(dateFormat.format(new Date()));
        adapter.notifyDataSetChanged();
    }

}
