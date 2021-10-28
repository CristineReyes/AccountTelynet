package com.devcristine.accounttelynet

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.lang.Exception


/**
 * Created by Cristine R.M. on 27/10/2021.
 * DevCristineAguirre
 */
class SQLiteHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION ) {


    companion object {

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "telynet.db"
        private const val TABLE_NAME = "ACCOUNT"
        private const val CODE = "code"
        private const val NAME = "name"
        private const val PHONE = "phone"
        private const val EMAIL = "email"
        private const val VISITED = "visited"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblAccount=("CREATE TABLE " + TABLE_NAME + "("
                + CODE + " INTEGER PRIMARY KEY,"
                + NAME + " TEXT,"
                + PHONE + " TEXT,"
                + EMAIL + " TEXT,"
                + VISITED + " TEXT" + ")")

        db?.execSQL(createTblAccount)

        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('201','Maria','4501145','acc1@gmail.com', 'si')")
        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('202','Mose','5212445','acc2@gmail.com', 'si')")
        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('203','Rene','452112452','acc3@gmail.com', 'no')")
        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('204','Fernanda','854552452','acc4@gmail.com', 'no')")
        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('205','Alejandra','6552345','acc5@gmail.com', 'si')")
        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('190','Teresa','450114545','acc1190@gmail.com', 'si')")
        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('320','Franciasco','521255115','acc320@gmail.com', 'si')")
        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('187','Jimena','45211245','acc187@gmail.com', 'si')")
        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('761','Maria Teresa','85455245','acc761@gmail.com', 'no')")
        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('110','Ricardo','65523455','acc110@gmail.com', 'si')")
        Log.d("tabla creda","Tabla creada")


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertDEfault(){

        val db = this.writableDatabase


        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('201','Luis','4501145','acc1@gmail.com', 'si')")
        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('202','Carlos','5212','acc2@gmail.com', 'si')")
        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('203','Renata','452112','acc3@gmail.com', 'no')")
        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('204','Teresa','854552','acc4@gmail.com', 'no')")
        db?.execSQL("insert into $TABLE_NAME($CODE, $NAME,$PHONE,$EMAIL,$VISITED) values('205','Jacob','65523','acc5@gmail.com', 'si')")
        db.close()

    }



    fun insertAccounts(account: AccountModel):Long{
        val db = this.writableDatabase

        val contentValues= ContentValues()
        contentValues.put(CODE, account.code)
        contentValues.put(NAME, account.name)
        contentValues.put(PHONE, account.phone)
        contentValues.put(EMAIL, account.email)
        contentValues.put(VISITED, account.visited)

        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success


    }


    fun getAllAccount(): ArrayList<AccountModel>{
        val accountList: ArrayList<AccountModel> = ArrayList()
        val selectQuery = "SELECT *  FROM $TABLE_NAME"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()

        }


        var code : Int
        var name: String
        var phone: String
        var email: String
        var visited: String

                if(cursor.moveToFirst()){
                    do {
                        code = cursor.getInt(cursor.getColumnIndex("code"))
                        name = cursor.getString(cursor.getColumnIndex("name"))
                        phone = cursor.getString(cursor.getColumnIndex("phone"))
                        email = cursor.getString(cursor.getColumnIndex("email"))
                        visited = cursor.getString(cursor.getColumnIndex("visited"))

                        val account = AccountModel(code=code, name=name, phone=phone, email=email, visited=visited)
                        accountList.add(account)

                    }while (cursor.moveToNext())

        }

        return accountList

    }


    fun getAllAccountVisit(): ArrayList<AccountModel>{
        val accountListVisit: ArrayList<AccountModel> = ArrayList()
        val selectQuery = "SELECT *  FROM $TABLE_NAME where $VISITED  like  '%Si%'"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()

        }


        var code : Int
        var name: String
        var phone: String
        var email: String
        var visited: String

        if(cursor.moveToFirst()){
            do {
                code = cursor.getInt(cursor.getColumnIndex("code"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                phone = cursor.getString(cursor.getColumnIndex("phone"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                visited = cursor.getString(cursor.getColumnIndex("visited"))

                val account = AccountModel(code=code, name=name, phone=phone, email=email, visited=visited)
                accountListVisit.add(account)

            }while (cursor.moveToNext())

        }

        return accountListVisit

    }

    fun getAllAccountNotVisit(): ArrayList<AccountModel>{
        val accountListnotVisit: ArrayList<AccountModel> = ArrayList()
        val selectQuery = "SELECT *  FROM $TABLE_NAME where $VISITED  like  '%No%'"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()

        }


        var code : Int
        var name: String
        var phone: String
        var email: String
        var visited: String

        if(cursor.moveToFirst()){
            do {
                code = cursor.getInt(cursor.getColumnIndex("code"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                phone = cursor.getString(cursor.getColumnIndex("phone"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                visited = cursor.getString(cursor.getColumnIndex("visited"))

                val account = AccountModel(code=code, name=name, phone=phone, email=email, visited=visited)
                accountListnotVisit.add(account)

            }while (cursor.moveToNext())

        }

        return accountListnotVisit

    }



    fun getAllAccountNambe(): ArrayList<AccountModel>{
        val accountListName: ArrayList<AccountModel> = ArrayList()
        val selectQuery = "SELECT *  FROM $TABLE_NAME ORDER BY $NAME ASC"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()

        }


        var code : Int
        var name: String
        var phone: String
        var email: String
        var visited: String

        if(cursor.moveToFirst()){
            do {
                code = cursor.getInt(cursor.getColumnIndex("code"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                phone = cursor.getString(cursor.getColumnIndex("phone"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                visited = cursor.getString(cursor.getColumnIndex("visited"))

                val account = AccountModel(code=code, name=name, phone=phone, email=email, visited=visited)
                accountListName.add(account)

            }while (cursor.moveToNext())

        }

        return accountListName

    }


    fun getAllAccountCode(): ArrayList<AccountModel>{
        val accountListCode: ArrayList<AccountModel> = ArrayList()
        val selectQuery = "SELECT *  FROM $TABLE_NAME ORDER BY $CODE ASC"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()

        }


        var code : Int
        var name: String
        var phone: String
        var email: String
        var visited: String

        if(cursor.moveToFirst()){
            do {
                code = cursor.getInt(cursor.getColumnIndex("code"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                phone = cursor.getString(cursor.getColumnIndex("phone"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                visited = cursor.getString(cursor.getColumnIndex("visited"))

                val account = AccountModel(code=code, name=name, phone=phone, email=email, visited=visited)
                accountListCode.add(account)

            }while (cursor.moveToNext())

        }

        return accountListCode

    }

    fun updateAccount(account: AccountModel): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(CODE, account.code)
        contentValues.put(NAME, account.name)
        contentValues.put(PHONE, account.phone)
        contentValues.put(EMAIL, account.email)
        contentValues.put(VISITED, account.visited)

        val success= db.update(TABLE_NAME, contentValues, "code=" +account.code, null)
        db.close()
        return success


    }


    fun deleteAccountById(code: Int): Int{
        val db= this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(CODE, code)


        val success= db.delete(TABLE_NAME, "code=$code", null)
        db.close()
        return success
    }






}