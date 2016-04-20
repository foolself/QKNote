package com.foolself.root.qknote;

import java.io.Serializable;

/**
 * Created by root on 16-4-18.
 */
public class Note implements Serializable
{
    public String id;
    public String attr;
    public String time;
    public String content;

    public Note(String id, String attr, String time, String content)
    {
        this.id = id;
        this.attr = attr;
        this.time = time;
        this.content = content;
    }

    @Override
    public String toString()
    {
        return content.substring(0, 20);
    }
}
