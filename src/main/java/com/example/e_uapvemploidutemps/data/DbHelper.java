package com.example.e_uapvemploidutemps.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_IGNORE;

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.getSimpleName();

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "edt.db";

    public static final String TABLE_NAME = "edt";

    public static final String _ID = "_id";
    public static final String COLUMN_LOCATION = "LOCATION";
    public static final String COLUMN_UID = "UID";
    public static final String COLUMN_SUMMARRY = "SUMMARRY";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_CATEGORIES = "CATEGORIES";
    public static final String COLUMN_DTSTAMP = "DTSTAMP";
    public static final String COLUMN_DTSTART = "DTSTART";
    public static final String COLUMN_LAST_MODIFIED = "LAST_MODIFIED";
    public static final String COLUMN_DTEND = "DTEND";


    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DbHelper instance;

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_COURSE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_UID + " TEXT , " +
                COLUMN_SUMMARRY + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_CATEGORIES + " TEXT, " +
                COLUMN_DTSTAMP + " TEXT, " +
                COLUMN_DTSTART + " TEXT, " +
                COLUMN_LAST_MODIFIED + " TEXT, " +
                COLUMN_DTEND + " TEXT " +
                ");";

        db.execSQL(SQL_CREATE_COURSE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(TABLE_DROP);
        onCreate(db);
    }

    public void dropTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(TABLE_DROP);
        onCreate(db);
    }

    /**
     * Adds a new city
     *
     * @return true if the city was added to the table ; false otherwise (case when the pair (city name, country) is
     * already in the data base
     */
    public boolean addUe(UE ue) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION, ue.getLOCATION());
        values.put(COLUMN_UID, ue.getUID());
        values.put(COLUMN_SUMMARRY, ue.getSUMMARRY());
        values.put(COLUMN_DESCRIPTION, ue.getDESCRIPTION());
        values.put(COLUMN_CATEGORIES, ue.getCATEGORIES());
        values.put(COLUMN_DTSTAMP, formatdateToString(ue.getDTSTAMP()));
        values.put(COLUMN_LAST_MODIFIED, formatdateToString(ue.getLAST_MODIFIED()));
        values.put(COLUMN_DTSTART, formatdateToString(ue.getDTSTART()));
        values.put(COLUMN_DTEND, formatdateToString(ue.getDTEND()));
        long rowID = db.insertWithOnConflict(TABLE_NAME, null, values, CONFLICT_IGNORE);
        db.close(); // Closing database connection

        return (rowID != -1);
    }

    /**
     * Returns a cursor on all the cities of the data base
     */
    public Cursor fetchAllUes(String date, ArrayList<String> grps) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_DTSTART + " BETWEEN ? AND ?" + " AND " +
                COLUMN_SUMMARRY + " !=?" + " AND " + COLUMN_SUMMARRY + " !=?"
                + " AND (" + COLUMN_SUMMARRY + " LIKE ?"
                + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                + " OR " + COLUMN_SUMMARRY + " LIKE ? )";
        String[] selectionArgs = {
                date + " 00:00:00",
                date + " 23:59:59",
                "Férié", "Vacances",
                "%" + grps.get(0) + "%",
                "%" + grps.get(1) + "%",
                "%" + grps.get(2) + "%",
                "%" + grps.get(3) + "%",
                "%" + grps.get(4) + "%",
                "%" + grps.get(5) + "%",
                "%" + grps.get(6) + "%",
                "%" + grps.get(7) + "%",
                "%" + grps.get(8) + "%"

        };

        Cursor cursor = db.query(TABLE_NAME, null,
                selection, selectionArgs, null, null, COLUMN_DTSTART, null);
        Log.d(TAG, "------ fetchAllUes()");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchNextUes(boolean isUE, ArrayList<String> grps) {

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH);
        int dayOfMonth = calendar.get(java.util.Calendar.DAY_OF_MONTH);

        int hourNow = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        int minNow = calendar.get(java.util.Calendar.MINUTE);

        calendar.add(calendar.MINUTE, -15);

        int minAfter15min = calendar.get(Calendar.MINUTE);
        int hourAfter15min = calendar.get(Calendar.HOUR_OF_DAY);

        String dayString;
        String monthString;
        String hourString;
        String minString;
        String minAfter15minString;
        String hourAfter15minString;

        if (month < 10) {
            monthString = "0" + (month + 1);
        } else {
            monthString = "" + (month + 1);
        }

        if (dayOfMonth < 10) {
            dayString = "0" + dayOfMonth;
        } else {
            dayString = "" + dayOfMonth;
        }

        if (hourNow < 10) {
            hourString = "0" + hourNow;
        } else {
            hourString = "" + hourNow;
        }

        if (minNow < 10) {
            minString = "0" + minNow;
        } else {
            minString = "" + minNow;
        }

        if (minAfter15min < 10) {
            minAfter15minString = "0" + minAfter15min;
        } else {
            minAfter15minString = "" + minAfter15min;
        }

        if (hourAfter15min < 10) {
            hourAfter15minString = "0" + hourAfter15min;
        } else {
            hourAfter15minString = "" + hourAfter15min;
        }

        String firstDate = year + "-" + monthString + "-" + dayString + " " + hourString + ":" + minString + ":00";
        String SecondDate = year + "-" + monthString + "-" + dayString + " " + hourAfter15minString + ":" + minAfter15minString + ":00";

        SQLiteDatabase db = this.getReadableDatabase();
        String selection;
        String[] selectionArgs;
        Cursor cursor;
        if (isUE) {
            selection = COLUMN_DTSTART + " >= ? AND " + COLUMN_SUMMARRY + " !=?" + " AND " + COLUMN_SUMMARRY + " !=?" + " AND " + COLUMN_SUMMARRY + " NOT LIKE ?";
            selectionArgs = new String[]{SecondDate, "Férié", "Vacances", "%Annulation%"};
            cursor = db.query(TABLE_NAME, null,
                    selection, selectionArgs, COLUMN_UID, null, COLUMN_DTSTART, "3");

        } else {
            selection = COLUMN_DTSTART + " >= ? AND " + COLUMN_SUMMARRY + " !=?" +
                    " AND " + COLUMN_SUMMARRY + " !=?" + " AND " + COLUMN_SUMMARRY +
                    " NOT LIKE ?" + " AND " + COLUMN_SUMMARRY + " LIKE ?"
                    + " AND (" + COLUMN_SUMMARRY + " LIKE ?"
                    + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                    + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                    + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                    + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                    + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                    + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                    + " OR " + COLUMN_SUMMARRY + " LIKE ?"
                    + " OR " + COLUMN_SUMMARRY + " LIKE ? )";

            selectionArgs = new String[]{SecondDate
                    , "Férié"
                    , "Vacances"
                    , "%Annulation%",
                    "%Evaluation%",
                    "%" + grps.get(0) + "%",
                    "%" + grps.get(1) + "%",
                    "%" + grps.get(2) + "%",
                    "%" + grps.get(3) + "%",
                    "%" + grps.get(4) + "%",
                    "%" + grps.get(5) + "%",
                    "%" + grps.get(6) + "%",
                    "%" + grps.get(7) + "%",
                    "%" + grps.get(8) + "%"
            };
            cursor = db.query(TABLE_NAME, null,
                    selection, selectionArgs, null, null, COLUMN_DTSTART, null);

        }
        Log.d(TAG, "------ fetchAllnext()");

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public List<UE> getAllCourses(String date, ArrayList<String> grps) throws ParseException {
        List<UE> res = new ArrayList<>();
        //get database
        SQLiteDatabase db = this.getReadableDatabase();
        //fetch to a cursor
        Cursor cursor = fetchAllUes(date, grps);
        System.out.println("Nombre de résultat " + cursor.getCount());
        while (cursor.moveToNext()) {
            res.add(cursorToUe(cursor));
        }
        db.close();
        return res;
    }

    public List<UE> getNextUe(ArrayList<String> grps) throws ParseException {
        List<UE> res = new ArrayList<>();
        //get database
        SQLiteDatabase db = this.getReadableDatabase();
        //fetch to a cursor
        Cursor cursor = fetchNextUes(true, grps);
        while (cursor.moveToNext()) {
            res.add(cursorToUe(cursor));
        }
        db.close();
        return res;
    }

    public List<UE> getNextEvaluation(ArrayList<String> grps) throws ParseException {
        List<UE> res = new ArrayList<>();
        //get database
        SQLiteDatabase db = this.getReadableDatabase();
        //fetch to a cursor
        Cursor cursor = fetchNextUes(false, grps);
        while (cursor.moveToNext()) {
            res.add(cursorToUe(cursor));
        }
        db.close();
        return res;
    }

    public void deleteCourses(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, _ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }


    public void addUes(ArrayList<Map<String, String>> calendarEntries/*,int day,int month,int year*/) throws ParseException {

        UE ue;
        Date DTSTAMP = null;
        Date DTSTART = null;
        Date DTEND = null;
        Date LAST_MODIFIED = null;


        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyyMMdd'T'HHmmss'Z'");


        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        for (Map<String, String> c : calendarEntries) {

            DTSTAMP = new Date();
            DTSTART = new Date();
            DTEND = new Date();
            LAST_MODIFIED = new Date();
            try {
                DTSTAMP = dateFormat.parse(c.get("DTSTAMP"));
                DTSTART = dateFormat.parse(c.get("DTSTART"));
                DTEND = dateFormat.parse(c.get("DTEND"));
                LAST_MODIFIED = dateFormat.parse(c.get("LAST-MODIFIED"));
                String tmp = formatdateToString(DTSTART);


            } catch (ParseException e) {
                e.printStackTrace();
            }
            String dattee = DTEND.toString();
            ue = new UE(
                    c.get("LOCATION"),
                    c.get("UID"),
                    DTSTAMP,
                    c.get("SUMMARY"),
                    DTSTART,
                    c.get("DESCRIPTION"),
                    c.get("CATEGORIES"),
                    LAST_MODIFIED,
                    DTEND);
            addUe(ue);
        }
    }

    public Date changeDateFormat(Date oldDate) throws ParseException {
        SimpleDateFormat formatNew = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        String tmpDate = formatNew.format(oldDate);
        oldDate = formatNew.parse(tmpDate);
        return oldDate;

    }

    public UE cursorToUe(Cursor cursor) throws ParseException {

        String tmpString = cursor.getString(cursor.getColumnIndex(COLUMN_DTSTART)).toString();

        UE course = new UE(

                cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)),
                cursor.getString(cursor.getColumnIndex(COLUMN_UID)),
                StringToDate(cursor.getString(cursor.getColumnIndex(COLUMN_DTSTAMP))),
                cursor.getString(cursor.getColumnIndex(COLUMN_SUMMARRY)),
                StringToDate(cursor.getString(cursor.getColumnIndex(COLUMN_DTSTART))),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORIES)),

                StringToDate(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_MODIFIED))),
                StringToDate(cursor.getString(cursor.getColumnIndex(COLUMN_DTEND)))

        );
        return course;
    }

    public Date StringToDate(String dateString) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();

        try {
            date = dateFormat.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date toDate(String Str) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        Date date1 = (Date) formatter.parse(Str);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        return date1;

    }

    public String formatdateToString(Date date) throws ParseException {
        SimpleDateFormat formatNew = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String tmpDate = date != null ? formatNew.format(date) : "";
        return tmpDate;
    }

}
