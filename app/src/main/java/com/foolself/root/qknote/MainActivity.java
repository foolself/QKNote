package com.foolself.root.qknote;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NoteListFragment.Callbacks {
    NoteDB dbHelper;
    NoteListFragment myListFragment;
    Cursor cursor;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // database init
        dbHelper = new NoteDB(this, "notesdb.db", 1);

        myListFragment = new NoteListFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.container, myListFragment).commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
                Bundle bundle = new Bundle();
                Note note = new Note("0", "1", "", "");
                bundle.putSerializable("note", note);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public static ArrayList<Map<String, Object>> converCursorToList(Cursor cursor) {
        ArrayList<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            map.put(NoteDB.ID, cursor.getString(0));
            int attr = R.drawable.shape_yellow;
            switch (cursor.getString(1)){
                case "1":
                    attr = R.drawable.shape_yellow;
                    break;
                case "2":
                    attr = R.drawable.shape_orange;
                    break;
                case "3":
                    attr = R.drawable.shape_red;
                    break;
                case "4":
                    attr = R.drawable.shape_green;
                    break;
            }
            map.put(NoteDB.ATTR, attr);
            map.put(NoteDB.TIME, cursor.getString(2));
            map.put(NoteDB.CONTENT, cursor.getString(3));
            lists.add(map);
        }
        return lists;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(String id) {
        cursor = dbHelper.getReadableDatabase().rawQuery("select * from notes where _id=?", new String[] {id});
        if (cursor.moveToFirst()) {
            Intent detailIntent = new Intent(this, NoteDetailActivity.class);
            Bundle bundle = new Bundle();
            Note note = new Note(cursor.getString(cursor.getColumnIndex(NoteDB.ID)),
                    cursor.getString(cursor.getColumnIndex(NoteDB.ATTR)),
                    cursor.getString(cursor.getColumnIndex(NoteDB.TIME)),
                    cursor.getString(cursor.getColumnIndex(NoteDB.CONTENT)));
            bundle.putSerializable("note", note);
            detailIntent.putExtras(bundle);
            startActivity(detailIntent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
