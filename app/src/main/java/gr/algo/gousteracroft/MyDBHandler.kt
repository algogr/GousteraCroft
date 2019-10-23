package gr.algo.gousteracroft

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class MyDBHandler(context: Context,name:String?,factory:SQLiteDatabase.CursorFactory?,
                  version: Int):SQLiteOpenHelper(context,DATABASE_NAME,factory,DATABASE_VERSION)
{

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS supplier")
        db.execSQL("DROP TABLE IF EXISTS croft")
        db.execSQL("DROP TABLE IF EXISTS item")
        db.execSQL("DROP TABLE IF EXISTS variation")
        createTables(db)
        insertSampleData(db)





    }







    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO()


    }



    fun createTables(db:SQLiteDatabase){
        val CREATE_SUPPLIER:String="CREATE TABLE supplier (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                "code TEXT,name TEXT,afm TEXT, phone1 TEXT,phone2 TEXT,phone3 TEXT,address TEXT,district TEXT,city TEXT,erpid INTEGER,erpupd INTEGER)"

        val CREATE_CROFT:String ="CREATE TABLE croft (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,supplierid	INTEGER NOT NULL,iteid	INTEGER,variationid	INTEGER,isbio	INTEGER,gps	TEXT,"+
                "village	TEXT, county	TEXT,stremma	NUMERIC,expectedqty	NUMERIC,operatorname TEXT,operatorphone TEXT,comments TEXT," +
                "inspectiondate TEXT,erpid INTEGER,erpupd INTEGER);"

        val CREATE_ITEM:String="CREATE TABLE item (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,description TEXT,erpid INTEGER)"



        val CREATE_VARIATION:String="CREATE TABLE variation (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,iteid INTEGER,description TEXT,erpid INTEGER);"


        val CREATE_SETTINGS:String="CREATE TABLE settings (serveraddress TEXT,serverport TEXT)"





        db.execSQL(CREATE_SUPPLIER)
        db.execSQL(CREATE_CROFT)
        db.execSQL(CREATE_ITEM)
        db.execSQL(CREATE_VARIATION)
        db.execSQL(CREATE_SETTINGS)


    }





    fun insertSampleData(db:SQLiteDatabase){
        var query=" INSERT INTO supplier(id,code,name,afm,phone1,phone2,phone3,address,district,city,erpid,erpupd) VALUES (1,'0001','ΠΑΡΑΣΧΟΥ ΔΗΜΗΤΡΗΣ','027445258','2510600096','2510222075','6947613893','" +
                "ΤΕΝΕΔΟΥ72','ΚΗΠΟΥΠΟΛΗ','ΚΑΒΑΛΑ',5,0)"
        db.execSQL(query)

        query=" INSERT INTO supplier(id,code,name,afm,phone1,phone2,phone3,address,district,city,erpid,erpupd) VALUES (2,'0002','ΠΑΠΑΔΟΠΟΥΛΟΣ ΓΙΩΡΓΟΣ','012345678','2516000096','2510223075','6947613894','" +
                "ΒΕΝΙΖΕΟΛΟΥ 58','ΚΕΝΤΡΟ ','ΚΑΒΑΛΑ',6,0)"
        db.execSQL(query)

        query=" INSERT INTO supplier(id,code,name,afm,phone1,phone2,phone3,address,district,city,erpid,erpupd) VALUES (3,'0002','ΠΑΣΧΑΛΙΔΗΣ ΘΑΝΑΣΗΣ','082059240','2516247248','2510837693','6947613891','" +
                "ΜΕΡΑΡΧΙΑΣ 69','ΚΕΝΤΡΟ ','ΚΑΒΑΛΑ',7,0)"
        db.execSQL(query)

        query=" INSERT INTO croft(supplierid,iteid,variationid,isbio,gps,village,county,stremma,expectedqty,operatorname,operatorphone,comments,inspectiondate,erpid,erpupd) VALUES (5,1,1,0,'456.98,8779.25','ΧΡΥΣΟΧΩΡΙ','" +
                "ΚΑΒΑΛΑΣ',58,1530,'ΓΕΩΡΓΙΑΔΗΣ','6947613893','ΕΙΝΑΙ ΠΟΛΥ ΚΑΛΟ','2019-01-01',1,0)"
        db.execSQL(query)
        query=" INSERT INTO croft(supplierid,iteid,variationid,isbio,gps,village,county,stremma,expectedqty,operatorname,operatorphone,comments,inspectiondate,erpid,erpupd) VALUES (5,1,2,0,'469.98,6779.25','ΑΓΙΑΣΜΑ','" +
                "ΚΑΒΑΛΑΣ',20,586,'ΠΑΠΑΝΙΚΟΛΟΑΥ','6947613892','ΤΕΛΕΙΟ','2019-10-01',2,0)"
        db.execSQL(query)
        query=" INSERT INTO croft(supplierid,iteid,variationid,isbio,gps,village,county,stremma,expectedqty,operatorname,operatorphone,comments,inspectiondate,erpid,erpupd) VALUES (6,2,1,0,'1456.98,9779.25','ΔΟΞΑΤΟ','" +
                "ΔΡΑΜΑΣ',63,1800,'ΓΕΩΡΓΑΚΟΠΟΥΛΟΣ','6947613890','ΑΘΛΙΟ ΠΟΛΥ ΚΑΛΟ','2019-05-01',3,0)"
        db.execSQL(query)

        query=" INSERT INTO ITEM(id,description,erpid) VALUES (1,'ΑΚΤΙΝΙΔΙΟ',1)"
        db.execSQL(query)
        query=" INSERT INTO ITEM(id,description,erpid) VALUES (2,'ΡΟΔΙ',2)"
        db.execSQL(query)
        query=" INSERT INTO variation(id,iteid,description,erpid) VALUES (1,1,'HAYWORTH',1)"
        db.execSQL(query)
        query=" INSERT INTO variation(id,iteid,description,erpid) VALUES (2,1,'ΑΠΛΟ',2)"
        db.execSQL(query)
        query=" INSERT INTO variation(id,iteid,description,erpid) VALUES (3,2,'TADE',3)"
        db.execSQL(query)
        query=" INSERT INTO variation(id,iteid,description,erpid) VALUES (4,2,'DEINA',4)"
        db.execSQL(query)
        query="INSERT INTO settings(serveraddress,serverport) VALUES ('192.168.0.11',8080)"
        db.execSQL(query)




    }


    fun insertUpdate(query:String){

        val db=this.writableDatabase
        db.execSQL(query)
        db.close()
    }

    fun getSupplierName(supplierId:Int):String{
        val query="SELECT name from supplier where id="+supplierId.toString()
        val db=this.writableDatabase
        val cursor=db.rawQuery(query,null)
        cursor.moveToPosition(0)
        val name=cursor.getString(0)
        cursor.close()
        db.close()
        return name


    }

    fun getSettings(field:Int):String{
        val query="SELECT serveraddress,serverport from settings"
        val db=this.writableDatabase
        val cursor=db.rawQuery(query,null)
        cursor.moveToPosition(0)
        var answer:String=""
        when (field){
            1->answer=cursor.getString(0)
            2->answer=cursor.getInt(1).toString()

        }
        cursor.close()
        db.close()
        return answer
    }

    fun getSupplierErpId(supplierId:Int):Int{

        val query="SELECT erpid from supplier where id="+supplierId.toString()
        val db=this.writableDatabase
        val cursor=db.rawQuery(query,null)
        cursor.moveToPosition(0)
        val erpId=cursor.getInt(0)
        cursor.close()
        db.close()
        return erpId

    }


    fun getItemErpId(itemId:Int):Int{

        val query="SELECT erpid from item where id="+itemId.toString()
        val db=this.writableDatabase
        val cursor=db.rawQuery(query,null)
        cursor.moveToPosition(0)
        val erpId=cursor.getInt(0)
        cursor.close()
        db.close()
        return erpId

    }


    fun getVariationErpId(itemId:Int):Int{

        val query="SELECT erpid from variation where id="+itemId.toString()
        val db=this.writableDatabase
        val cursor=db.rawQuery(query,null)
        cursor.moveToPosition(0)
        val erpId=cursor.getInt(0)
        cursor.close()
        db.close()
        return erpId

    }





    fun getItemDescr(itemId:Int):String{
        val query="SELECT description from item where erpid="+itemId.toString()
        val db=this.writableDatabase
        val cursor=db.rawQuery(query,null)
        cursor.moveToPosition(0)
        val description=cursor.getString(0)
        cursor.close()
        db.close()
        return description


    }



    fun getvariationDescr(varId:Int?):String{
        val query="SELECT description from variation where erpid="+varId.toString()
        val db=this.writableDatabase
        val cursor=db.rawQuery(query,null)
        var answer:String="ΠΟΙΚΙΛΙΑ"
        if (cursor.moveToFirst()) {
            answer = cursor.getString(0)
        }
        cursor.close()
        db.close()
        return answer

    }



    fun getSuppliers():MutableList<Supplier>{
        val query="SELECT id,code,name,afm,phone1,phone2,phone3,address,district,city,erpid FROM Supplier order by name"

        val db=this.writableDatabase
        val cursor=db.rawQuery(query,null)

        var supplierList= mutableListOf<Supplier>()

        val i=cursor.count-1
        for (j in 0..i)
        {
            cursor.moveToPosition(j)




            val id= Integer.parseInt(cursor.getString(0))
            val code = cursor.getString(1)
            val name = cursor.getString(2)
            val afm = cursor.getString(3)
            val phone1 = cursor.getString(4)
            val phone2 = cursor.getString(5)
            val phone3 = cursor.getString(6)
            val address = cursor.getString(7)
            val district = cursor.getString(8)
            val city = cursor.getString(9)
            val erpid=Integer.parseInt(cursor.getString(10))

            val supplier=Supplier(id,code,name,afm,phone1,phone2,phone3,address ,district,city,erpid)

            supplierList.add(j,supplier)


        }
        cursor.close()
        db.close()
        return  supplierList


    }
    fun croftBySupplier(supplierId:Int):MutableList<Croft> {
        val query =
            "SELECT id,supplierid,iteid,variationid,isbio,gps,village,county,stremma,expectedqty,operatorname,operatorphone,comments,inspectiondate,erpid FROM croft where supplierid=" + supplierId + " order by expectedqty"

        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        var croftList = mutableListOf<Croft>()

        val i = cursor.count - 1
        for (j in 0..i) {
            cursor.moveToPosition(j)
            val id= Integer.parseInt(cursor.getString(0))
            val supplierId= Integer.parseInt(cursor.getString(1))
            val iteid=Integer.parseInt(cursor.getString(2))
            val variationid=Integer.parseInt(cursor.getString(3))
            val isbio=Integer.parseInt(cursor.getString(4))
            val gps=cursor.getString(5)
            val village=cursor.getString(6)
            val county=cursor.getString(7)
            val stremma=cursor.getFloat(8)
            val expectedqty=cursor.getFloat(9)
            val operatorname=cursor.getString(10)
            val operatorphone=cursor.getString(11)
            val comments=cursor.getString(12)
            val inspectiondate=cursor.getString(13)
            val erpId=cursor.getInt(14)

            val croft=Croft(id,supplierId,iteid,variationid,isbio,gps,village,county, stremma,expectedqty,operatorname,operatorphone,comments,inspectiondate,erpId)

            croftList.add(croft)
       }

        cursor.close()
        db.close()
        return croftList
    }





    fun variationByItem(iteId:Int):MutableList<Variation> {
        val query =
            "SELECT id,iteid,description,erpid from variation where iteid not in ((select erpid from item where id=" + iteId + ")) order by description"
        Log.d("JIM",query)
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        var variationList = mutableListOf<Variation>()

        val i = cursor.count - 1
        for (j in 0..i) {
            cursor.moveToPosition(j)
            val id= Integer.parseInt(cursor.getString(0))
            val iteId= Integer.parseInt(cursor.getString(1))
            val description=cursor.getString(2)
            val erpId=cursor.getInt(3)

            val variation=Variation(id,iteId,description,erpId)

            variationList.add(variation)








        }

        cursor.close()
        db.close()
        return variationList
    }


    fun getVariations():MutableList<Variation> {
        val query =
            "SELECT id,iteid,description,erpid from variation order by description"

        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        var variationList = mutableListOf<Variation>()

        val i = cursor.count - 1
        for (j in 0..i) {
            cursor.moveToPosition(j)
            val id= Integer.parseInt(cursor.getString(0))
//            val iteId= Integer.parseInt(cursor.getString(1))
            val description=cursor.getString(2)
            val erpId=cursor.getInt(3)

            val variation=Variation(id,0,description,erpId)

            variationList.add(variation)








        }

        cursor.close()
        db.close()
        return variationList
    }




    fun getItems():MutableList<Item> {
        val query =
            "SELECT id,description,erpid from item order by description"

        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        var itemList = mutableListOf<Item>()

        val i = cursor.count - 1
        for (j in 0..i) {
            cursor.moveToPosition(j)
            val id= Integer.parseInt(cursor.getString(0))
            val description=cursor.getString(1)
            val erpId=cursor.getInt(2)

            val item=Item(id,description,erpId)

            itemList.add(item)








        }

        cursor.close()
        db.close()
        return itemList
    }






    companion object {
        private val DATABASE_VERSION=1
        private val DATABASE_NAME="goustera.db"
    }



}