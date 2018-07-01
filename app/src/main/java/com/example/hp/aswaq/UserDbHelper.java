package com.example.hp.aswaq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hp on 8/13/2017.
 */

public class UserDbHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME="Cart_Product";
    private static final  int DATABASE_VERSION=1;
    private static final String CREATE_DATABASE= "CREATE TABLE "
            + UserData.NewUserData.TABLE_NAME + " ( "
            + UserData.NewUserData.product_id +" TEXT,"
            + UserData.NewUserData.product_name +" TEXT,"
            +UserData.NewUserData.product_img + " TEXT,"
            + UserData.NewUserData.product_price +" TEXT,"
            + UserData.NewUserData.product_sale +" TEXT,"
            + UserData.NewUserData.product_stock +" TEXT,"
            + UserData.NewUserData.product_count +" TEXT,"
            + UserData.NewUserData.product_subtotal +" TEXT);";



    public UserDbHelper(Context context)
    {
    super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_DATABASE);

    }

    public boolean AddInfo(String id,String name, String img,String price,String sale,String stock,String count,String subtotal)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(UserData.NewUserData.product_name,name);
        contentValues.put(UserData.NewUserData.product_img,img);
        contentValues.put(UserData.NewUserData.product_price,price);
        contentValues.put(UserData.NewUserData.product_sale,sale);
        contentValues.put(UserData.NewUserData.product_id,id);
        contentValues.put(UserData.NewUserData.product_stock,stock);
        contentValues.put(UserData.NewUserData.product_count,count);
        contentValues.put(UserData.NewUserData.product_subtotal,subtotal);
        db.insert(UserData.NewUserData.TABLE_NAME, null, contentValues);

        return true;
    }


    public Cursor GetData(SQLiteDatabase db)
    {
         db = this.getReadableDatabase();
         String[] projections = {UserData.NewUserData.product_id,UserData.NewUserData.product_name, UserData.NewUserData.product_img, UserData.NewUserData.product_price, UserData.NewUserData.product_sale, UserData.NewUserData.product_stock,
         UserData.NewUserData.product_count,UserData.NewUserData.product_subtotal};
         Cursor cursor =   db.query(UserData.NewUserData.TABLE_NAME,projections,null,null,null,null,null);
        return cursor;
    }
    public boolean updateInfo(String id,String name, String img,String price,String sale,String stock,String count,String subtotal) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserData.NewUserData.product_name,name);
        values.put(UserData.NewUserData.product_img,img);
        values.put(UserData.NewUserData.product_price,price);
        values.put(UserData.NewUserData.product_sale,sale);
        values.put(UserData.NewUserData.product_id,id);
        values.put(UserData.NewUserData.product_stock,stock);
        values.put(UserData.NewUserData.product_count,count);
        values.put(UserData.NewUserData.product_subtotal,subtotal);
        db.execSQL("update " + UserData.NewUserData.TABLE_NAME + " set " +
                UserData.NewUserData.product_id + " = '" + id + "', " +
                UserData.NewUserData.product_name + " = '" + name + "', " +
                UserData.NewUserData.product_img + " = '" + img + "'," +
                UserData.NewUserData.product_price + " = '" + price + "'," +
                UserData.NewUserData.product_sale + " = '" + sale +"'," +
                UserData.NewUserData.product_count + " = '" + count +"'," +
                UserData.NewUserData.product_subtotal + " = '" + subtotal +"'," +
                UserData.NewUserData.product_stock + " = '" + stock +
                "' where " + UserData.NewUserData.product_name + " = '" + name + "'");
        db.close();
        return true;
    }
    public boolean deleteinformation(String id,String name,String img,String pric,String sale,String stock,String count,String subtotal){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String selection =UserData.NewUserData.product_name+" LIKE ?";
//        String[] seletion_args={name};
//        db.delete(UserData.NewUserData.TABLE_NAME,selection,seletion_args);
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+UserData.NewUserData.TABLE_NAME+" WHERE "+
                UserData.NewUserData.product_id+"='"+id+"'" +" AND "+
                UserData.NewUserData.product_name+"='"+name+"'" +" AND "+
                UserData.NewUserData.product_img+"='"+img+"'"+" AND "+
                UserData.NewUserData.product_price+"='"+pric+"'"+" AND "+
                UserData.NewUserData.product_stock+"='"+stock+"'"+" AND "+
                UserData.NewUserData.product_count+"='"+count+"'"+" AND "+
                UserData.NewUserData.product_subtotal+"='"+subtotal+"'"+" AND "+
                UserData.NewUserData.product_sale+"='"+sale+"'");

        db.close();
        return true;

    }
    // Returns total price of items in shopping cart
    public String getTotalCartPrice() {

        String SQLQuery = "SELECT SUM("+UserData.NewUserData.product_subtotal +") FROM "+ UserData.NewUserData.TABLE_NAME ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(SQLQuery, null);
        c.moveToFirst();
           String priceTotal = c.getString(0);

        return priceTotal;
    }
    public String getcount(String name) {

        String SQLQuery = "SELECT "+UserData.NewUserData.product_count +" FROM "+ UserData.NewUserData.TABLE_NAME  + " WHERE " + UserData.NewUserData.product_name +
                " = " + "'" + name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(SQLQuery, null);
        c.moveToFirst();
        String count = c.getString(0);

        return count;
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
