package com.foolself.root.qknote;

/**
 * Created by root on 16-4-15.
 */

import android.app.Activity;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

public class NoteListFragment extends ListFragment
{
    private Callbacks mCallbacks;
    public SimpleAdapter simpleAdapter;
    public ArrayList data;

    // 定义一个回调接口，该Fragment所在Activity需要实现该接口
    // 该Fragment将通过该接口与它所在的Activity交互
    public interface Callbacks
    {
        public void onItemSelected(String id);
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        return view;
    }

    // 当该Fragment被添加、显示到Activity时，回调该方法
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // 如果Activity没有实现Callbacks接口，抛出异常
        if (!(activity instanceof Callbacks))
        {
            throw new IllegalStateException(
                    "NoteListFragment所在的Activity必须实现Callbacks接口!");
        }
        // 把该Activity当成Callbacks对象
        mCallbacks = (Callbacks)activity;
    }
    // 当该Fragment从它所属的Activity中被删除时回调该方法
    @Override
    public void onDetach()
    {
        super.onDetach();
        // 将mCallbacks赋为null。
        mCallbacks = null;
    }
    // 当用户单击某列表项时激发该回调方法
    @Override
    public void onListItemClick(ListView listView
            , View view, int position, long id)
    {
        super.onListItemClick(listView, view, position, id);
        Object o = this.getListAdapter().getItem(position);
        Map<String, Object> map = (Map<String, Object>) o;
        mCallbacks.onItemSelected(map.get("_id").toString());
    }
    public void setActivateOnItemClick(boolean activateOnItemClick)
    {
        getListView().setChoiceMode(
                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
                        : ListView.CHOICE_MODE_NONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        NoteDB update_db = new NoteDB(getActivity(), "notesdb.db", 1);
        Cursor cursor = update_db.getReadableDatabase().rawQuery("select * from notes order by _id desc", null);
        data = MainActivity.converCursorToList(cursor);
        simpleAdapter = new SimpleAdapter(getActivity(),
                data,
                R.layout.fragment_list_item,
                new String[] {NoteDB.ATTR, NoteDB.TIME, NoteDB.CONTENT},
                new int[] {R.id.attr, R.id.time, R.id.content});
        simpleAdapter.notifyDataSetChanged();
        setListAdapter(simpleAdapter);
        Log.i("data count", String.valueOf(data.size()));
        Log.i("Adapter count", String.valueOf(simpleAdapter.getCount()));
    }
}