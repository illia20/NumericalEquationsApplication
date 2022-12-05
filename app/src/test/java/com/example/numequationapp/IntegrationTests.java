package com.example.numequationapp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

public class IntegrationTests {
    SQLiteDatabase mDb = Mockito.mock(SQLiteDatabase.class);
    Cursor cursor = Mockito.mock(Cursor.class);

    @Test
    public void testReadDB(){
        String selectSQL = "SELECT * FROM equations";
        Mockito.when(mDb.rawQuery(selectSQL, null)).thenReturn(cursor);
        Mockito.when(cursor.getCount()).thenReturn(3);
        Assert.assertEquals(3, cursor.getCount());
    }

    @Test
    public void testWriteDB(){
        String equation = "x^2 - 3";
        ContentValues cv = new ContentValues();
        cv.put("equation", equation);
        Mockito.when(mDb.insert("equations", null, cv)).thenReturn(1l);
        Assert.assertEquals(1, mDb.insert("equations", null, cv));
    }
}
