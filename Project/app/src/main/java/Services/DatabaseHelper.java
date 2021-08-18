package Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context)
    {
        super(context, "CartDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("Create Table UserItems(name TEXT , url TEXT primary key, quantity TEXT,amount TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists UserItems");
    }

    //CRUD OPERATIONS

    public Boolean insertCartItem(String name, String url, String quantity, String amount){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("url",url);
        contentValues.put("quantity",quantity);
        contentValues.put("amount",amount);

        long result=DB.insert("UserItems",null,contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Boolean updateCartItem(String name, String url, String quantity, String amount){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("quantity",quantity);
        contentValues.put("amount",amount);

        Cursor cursor=DB.rawQuery("Select * from UserItems where url=?", new String[]{url});
        if(cursor.getCount()>0) {
            long result = DB.update("UserItems", contentValues, "url=?", new String[]{url});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }



    public Boolean deleteCartItem(String url){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from UserItems where url=?", new String[]{url});
        if(cursor.getCount()>0) {
            long result = DB.delete("UserItems", "url=?", new String[]{url});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public Cursor getCartItems(){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from UserItems", null);
        return cursor;
    }
    public  Cursor getCartItem(String url){
        SQLiteDatabase DB=this.getWritableDatabase();

        Cursor cursor=DB.rawQuery("Select * from UserItems where url=?", new String[]{url});
        return cursor;
    }

    public  void clearCart(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from UserItems");
        db.close();
    }
}
