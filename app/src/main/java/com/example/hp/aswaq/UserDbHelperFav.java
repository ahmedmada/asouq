package com.example.hp.aswaq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelperFav extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME="Fav_Product";
    private static final  int DATABASE_VERSION=1;
    private static final String CREATE_DATABASE= "CREATE TABLE " + UserData.NewUserData.FavTable_NAME + " ( "+  UserData.NewUserData.Fav_name
            +" TEXT,"+UserData.NewUserData.Fav_img
            + " TEXT," + UserData.NewUserData.Fav_price +" TEXT,"+ UserData.NewUserData.Fav_sale +" TEXT);";



    public UserDbHelperFav(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_DATABASE);

    }

    public boolean AddInfo(String name, String img,String price,String sale)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(UserData.NewUserData.Fav_name,name);
        contentValues.put(UserData.NewUserData.Fav_img,img);
        contentValues.put(UserData.NewUserData.Fav_price,price);
        contentValues.put(UserData.NewUserData.Fav_sale,sale);
        // contentValues.put(UserData.NewUserData.product_count,count);
        db.insert(UserData.NewUserData.FavTable_NAME, null, contentValues);

        return true;
    }


    public Cursor GetData(SQLiteDatabase db)
    {
        db = this.getReadableDatabase();
        String[] projections = {UserData.NewUserData.Fav_name, UserData.NewUserData.Fav_img,
                UserData.NewUserData.Fav_price, UserData.NewUserData.Fav_sale};
        Cursor cursor =   db.query(UserData.NewUserData.FavTable_NAME,projections,null,null,null,null,null);
        return cursor;
    }
    public boolean updateInfo(String name, String img,String price,String sale) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserData.NewUserData.Fav_name,name);
        values.put(UserData.NewUserData.Fav_img,img);
        values.put(UserData.NewUserData.Fav_price,price);
        values.put(UserData.NewUserData.Fav_sale,sale);
        // values.put(UserData.NewUserData.product_count,count);
        db.execSQL("update " + UserData.NewUserData.FavTable_NAME + " set " +
                UserData.NewUserData.Fav_name + " = '" + name + "', " +
                UserData.NewUserData.Fav_img + " = '" + img + "'," +
                UserData.NewUserData.Fav_price + " = '" + price + "'," +
                UserData.NewUserData.Fav_sale + " = '" + sale +
                "' where " + UserData.NewUserData.Fav_name + " = '" + name + "'");
        db.close();
        return true;
    }
    public boolean deleteinformation(String name,String img,String pric,String sale){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String selection =UserData.NewUserData.product_name+" LIKE ?";
//        String[] seletion_args={name};
//        db.delete(UserData.NewUserData.TABLE_NAME,selection,seletion_args);
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+UserData.NewUserData.FavTable_NAME+" WHERE "+UserData.NewUserData.Fav_name+"='"+name+"'" +" AND "+
                UserData.NewUserData.Fav_img+"='"+img+"'"+" AND "+UserData.NewUserData.Fav_price+"='"+pric+"'"+" AND "+UserData.NewUserData.Fav_sale+"='"+sale+"'");

        db.close();
        return true;

    }
    public void deleteItem(String item) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "id=?";
        String whereArgs[] = {item};
        db.delete(UserData.NewUserData.TABLE_NAME, whereClause, whereArgs);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
