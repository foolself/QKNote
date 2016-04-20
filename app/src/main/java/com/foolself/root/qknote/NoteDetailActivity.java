package com.foolself.root.qknote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Created by root on 16-4-16.
 */
public class NoteDetailActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            NoteDetailFragment fragment = new NoteDetailFragment();
            Bundle arguments = new Bundle();
            arguments.putSerializable("note", getIntent().getSerializableExtra("note"));

            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.note_detail_container, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
