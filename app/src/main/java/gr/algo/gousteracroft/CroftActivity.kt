package gr.algo.gousteracroft

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View

import kotlinx.android.synthetic.main.activity_croft_activity.*
import kotlinx.android.synthetic.main.activity_croft_activity.fab
import kotlinx.android.synthetic.main.activity_croft_activity.toolbar
import kotlinx.android.synthetic.main.activity_item_list.*

class CroftActivity : FragmentActivity() {

    var supplierId:Int=-1
    var district:String?=""
    var address:String?=""
    var city:String?=""
    var name:String?=""
    var code:String?=""
    var phone1:String?=""
    var phone2:String?=""
    var phone3:String?=""
    var afm:String?=""
    var erpId:Int=-1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_croft_activity)
        toolbar.setTitle("Στοιχεία Τιμολόγησης")

        //setSupportActionBar(toolbar)
        val extras= intent.extras
        supplierId=extras.getInt("supplierid")
        code=extras.getString("code")
        name=extras.getString("name")
        afm=extras.getString("afm")
        phone1=extras.getString("phone1")
        phone2=extras.getString("phone2")
        phone3=extras.getString("phone3")
        address=extras.getString("address")
        district=extras.getString("district")
        city=extras.getString("city")
        erpId=extras.getInt("erpid")


        fab.setOnClickListener {
                v: View ->

            val i = Intent(v.context,CroftDetailsActivity::class.java)
            i.putExtra("suppliername",name)
            i.putExtra("supplierid",supplierId)
            i.putExtra("mode",0)
            v.context.startActivity(i)
        }


        fab1.setOnClickListener{
            v:View->
            val i=Intent(v.context,SupplierActivity::class.java)
            i.putExtra("mode",1)
            i.putExtra("supplierid",supplierId)
            i.putExtra("name",name)
            i.putExtra("afm",afm)
            i.putExtra("phone1",phone1)
            i.putExtra("phone2",phone2)
            i.putExtra("phone3",phone3)
            i.putExtra("address",address)
            i.putExtra("district",district)
            i.putExtra("city",city)
            v.context.startActivity(i)

        }




    }


}
