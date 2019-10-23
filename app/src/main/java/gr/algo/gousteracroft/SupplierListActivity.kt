package gr.algo.gousteracroft

import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.*
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_supplier_list.*
import kotlinx.android.synthetic.main.activity_supplier_list.toolbar

import java.util.*
import kotlin.collections.ArrayList

class SupplierListActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supplier_list)
        setSupportActionBar(toolbar)
        setTitle("Παραγωγοί")
        val listview = findViewById<ListView>(R.id.listView)

        //Find View By Id For SearchView
        val searchView = findViewById<SearchView>(R.id.searchView) as SearchView
        val handler=MyDBHandler(context = this.baseContext,version = 1,name=null,factory = null)


        val supplierList:MutableList<Supplier>? = handler.getSuppliers()
        val supplierlistAdapter=SupplierListViewAdapter(this@SupplierListActivity,supplierList)

        listview.adapter=supplierlistAdapter


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                val text = newText


                /*Call filter Method Created in Custom Adapter
                    This Method Filter ListView According to Search Keyword
                 */
                supplierlistAdapter.filter(text)
                return false
            }
        })



        fab.setOnClickListener { view ->
            val i = Intent(view.context,SupplierActivity::class.java)

            view.context.startActivity(i)
        }
    }



    inner class SupplierListViewAdapter: BaseAdapter {

        private var SupplierList:MutableList<Supplier>?
        private  var context: Context? =null
        private  val mInflator: LayoutInflater
        private var hashMapTexts: HashMap<String, String>
        private  var tempNameVersionList:ArrayList<Supplier>




        constructor(context: Context, supplierlist: MutableList<Supplier>?) : super(){
            this.SupplierList=supplierlist
            this.tempNameVersionList = ArrayList(SupplierList)
            this.context=context
            this.hashMapTexts= HashMap()
            this.mInflator= LayoutInflater.from(context)
            for ( supplier in SupplierList!!) {

                this.hashMapTexts.put(supplier.name,"")

            }
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val holder: SupplierListActivity.ViewHolder
            val view: View


            if (convertView==null){
                view= layoutInflater.inflate(R.layout.content_supplierlist_item,parent,false)
                holder= SupplierListActivity.ViewHolder(view)
                view.tag=holder

            }
            else {

                view = convertView
                holder = view.tag as SupplierListActivity.ViewHolder
            }


            holder.position=position
            holder.textView.text=SupplierList!![position].name
            holder.supplierId=SupplierList!![position].id
            holder.name=SupplierList!![position].name
            holder.district=SupplierList!![position].district
            holder.address=SupplierList!![position].address
            holder.city=SupplierList!![position].city
            holder.erpId=SupplierList!![position].erpid
            holder.code=SupplierList!![position].code
            holder.afm=SupplierList!![position].afm
            holder.phone1=SupplierList!![position].phone1
            holder.phone2=SupplierList!![position].phone2
            holder.phone3=SupplierList!![position].phone3



            return view
        }

        override fun getItem(position: Int): Any {
            return SupplierList!![position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return SupplierList!!.size
        }





        fun filter(text: String?) {


            //Our Search text
            val text = text!!.toLowerCase(Locale.getDefault())

            SupplierList?.clear()

            //Here We Clear Both ArrayList because We update according to Search query.



            if (text.length == 0) {

                /*If Search query is Empty than we add all temp data into our main ArrayList

                We store Value in temp in Starting of Program.

                */

                SupplierList?.addAll(tempNameVersionList)


            } else {


                for (i in 0..tempNameVersionList.size - 1) {

                    /*
                    If our Search query is not empty than we Check Our search keyword in Temp ArrayList.
                    if our Search Keyword in Temp ArrayList than we add to our Main ArrayList
                    */

                    if (tempNameVersionList.get(i).name!!.toLowerCase(Locale.getDefault()).contains(text)) {


                        SupplierList?.add(tempNameVersionList.get(i))


                    }



                }
            }

            //This is to notify that data change in Adapter and Reflect the changes.
            notifyDataSetChanged()


        }


    }



    private class ViewHolder(view: View?){
        var textView: TextView
        var position:Int
        var supplierId:Int
        var district:String?
        var address:String?
        var city:String?
        var name:String?
        var code:String?
        var phone1:String?
        var phone2:String?
        var phone3:String?
        var afm:String?
        var erpId:Int?




        init{

            this.textView=view?.findViewById<TextView>(R.id.textView3) as TextView

            this.position=-1
            this.supplierId=-1
            this.district=""
            this.address=""
            this.name=""
            this.city=""
            this.code=""
            this.phone1=""
            this.phone2=""
            this.phone3=""
            this.afm=""
            this.erpId=-1



            view.setOnClickListener { v: View ->

                val i = Intent(v.context,CroftActivity::class.java)
                i.putExtra("code",code)
                i.putExtra("name",name)
                i.putExtra("afm",afm)
                i.putExtra("phone1",phone1)
                i.putExtra("phone2",phone2)
                i.putExtra("phone3",phone3)
                i.putExtra("address",address)
                i.putExtra("district",district)
                i.putExtra("city",city)
                i.putExtra("erpid",erpId!!)
                i.putExtra("supplierid",supplierId)



                v.context.startActivity(i)


            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}
