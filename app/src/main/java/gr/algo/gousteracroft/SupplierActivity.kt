package gr.algo.gousteracroft

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_supplier.*
import kotlinx.android.synthetic.main.content_supplier.*

class SupplierActivity : AppCompatActivity() {

    var mode:Int=0    //New supplier 0 - Edit supplier 1
    var supplierId=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Στοιχεία Τιμολόγησης Παραγωγού")
        setContentView(R.layout.activity_supplier)
        setSupportActionBar(toolbar)
        if (intent.extras?.getInt("mode")!=null) mode=1 else mode=0
        if (mode==1)
        {
            nameEdit.text=SpannableStringBuilder(intent.extras.getString("name"))
            afmEdit.text=SpannableStringBuilder(intent.extras.getString("afm"))
            phone1Edit.text=SpannableStringBuilder(intent.extras.getString("phone1"))
            phone2Edit.text=SpannableStringBuilder(intent.extras.getString("phone2"))
            phone3Edit.text=SpannableStringBuilder(intent.extras.getString("phone3"))
            addressEdit.text=SpannableStringBuilder(intent.extras.getString("address"))
            districtEdit.text=SpannableStringBuilder(intent.extras.getString("district"))
            cityEdit.text=SpannableStringBuilder(intent.extras.getString("city"))


        }



        acceptButton3.setOnClickListener{
            val name=nameEdit.text.toString()
            val afm=afmEdit.text.toString()
            val phone1=phone1Edit.text.toString()
            val phone2=phone2Edit.text.toString()
            val phone3=phone3Edit.text.toString()
            val address=addressEdit.text.toString()
            val district= districtEdit.text.toString()
            val city=cityEdit.text.toString()



            val query:String
            if (!isValidName(name)) return@setOnClickListener
            if (mode==0){
                query=" INSERT INTO supplier(name,afm,phone1,phone2,phone3,address,district,city,erpid,erpupd) VALUES ('"+name+"','"+afm+"','"+phone1+"','"+phone2+
                    "','"+phone3+"','"+address+"','"+district+"','"+city+"',0,1)"}
            else
            {
                query="update supplier set name='"+name+"',afm='"+afm+"',phone1='"+phone1+"',phone1='"+phone1+"',phone2='"+phone2+"',phone3='"+phone3+"',address='"+address+
                "',district='"+district+"',city='"+city+"',erpupd=2 WHERE id="+intent.extras.getInt("supplierid").toString()
            }

            val handler = MyDBHandler(context = baseContext, version = 1, name = null, factory = null)
            handler.insertUpdate(query)
            handler.close()
            val i = Intent(it.context,SupplierListActivity::class.java)
            it.context.startActivity(i)

        }

        cancelButton2.setOnClickListener({

            val i = Intent(it.context,MainActivity::class.java)
            it.context.startActivity(i)

        })
    }



    private fun isValidName(name:String?):Boolean{
        val nameValidator=fun(value:String?):Boolean {
            if (value.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "Η επωνυμία είναι κενή", Toast.LENGTH_SHORT).show()
                return false
            }
            if (value.length < 2) {
                Toast.makeText(applicationContext, "Η επωνυμία είναι πολύ μικρή", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }
            return nameValidator(name)
        }

    }

