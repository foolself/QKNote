package com.foolself.root.qknote;

/**
 * Created by root on 16-4-15.
 */

import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteDetailFragment extends Fragment
{
    Note note;
    NoteDB dbHelper;
    private EditText ed_content;
    private TextView tv_time;
    private RadioGroup radioGroup;
    private RadioButton rb_yellow;
    private RadioButton rb_orange;
    private RadioButton rb_red;
    private RadioButton rb_green;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("note")) {
            note = (Note)getArguments().getSerializable("note");
        }
    }
    // 重写该方法，该方法返回的View将作为Fragment显示的组件
    @Override
    public View onCreateView(LayoutInflater inflater
            , ViewGroup container, Bundle savedInstanceState)
    {
        // 加载/res/layout/目录下的fragment_Note_detail.xml布局文件
        View rootView = inflater.inflate(R.layout.fragment_note_detail,
                container, false);
        ed_content = (EditText) rootView.findViewById(R.id.note_content);
        tv_time = (TextView) rootView.findViewById(R.id.note_time);
        ed_content.setText(note.content);
        ed_content.setSelection(note.content.length());
        tv_time.setText(note.time);

        radioGroup = (RadioGroup) rootView.findViewById(R.id.mRadioGroup);
        rb_yellow = (RadioButton) rootView.findViewById(R.id.yellow);
        rb_orange = (RadioButton) rootView.findViewById(R.id.orange);
        rb_red = (RadioButton) rootView.findViewById(R.id.red);
        rb_green = (RadioButton) rootView.findViewById(R.id.green);
        rb_yellow.setChecked(false);
        rb_orange.setChecked(false);
        rb_red.setChecked(false);
        rb_green.setChecked(false);
        switch (note.attr) {
            case "1":
                rb_yellow.setChecked(true);
                break;
            case "2":
                rb_orange.setChecked(true);
                break;
            case "3":
                rb_red.setChecked(true);
                break;
            case "4":
                rb_green.setChecked(true);
                break;
            default:
                rb_yellow.setChecked(true);
                break;
        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper = new NoteDB(this.getActivity(), "notesdb.db", 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String new_content = ed_content.getText().toString();
        String new_attr = "1";
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.yellow:
                new_attr = "1";
                break;
            case R.id.orange:
                new_attr = "2";
                break;
            case R.id.red:
                new_attr = "3";
                break;
            case R.id.green:
                new_attr = "4";
                break;
        }

        if (isEmpty(new_content)) {
            db.delete(NoteDB.TABLE_NAME, "_id=?", new String[]{note.id});
            return;
        }
        if (note.id.equals("0")) {
            if (!new_content.equals("")) {
                ContentValues cv = new ContentValues();
                cv.put(NoteDB.ATTR, new_attr);
                cv.put(NoteDB.TIME, getTime());
                cv.put(NoteDB.CONTENT, new_content);
                db.insert(NoteDB.TABLE_NAME, null, cv);
            }
            return;
        }
        else {
            if (!new_content.equals(note.content) || !note.attr.equals(new_attr)) {
                ContentValues cv = new ContentValues();
                cv.put(NoteDB.CONTENT, new_content);
                cv.put(NoteDB.ATTR, new_attr);
                db.update(NoteDB.TABLE_NAME, cv, "_id=?", new String[]{note.id});
            }
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    private boolean isEmpty(String new_content) {
        if (new_content.trim().isEmpty())
            return true;
        return false;
    }

    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }
}