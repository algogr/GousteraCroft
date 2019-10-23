package gr.algo.gousteracroft

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_variation_list.*
import kotlinx.android.synthetic.main.content_croft_details.*
import kotlinx.android.synthetic.main.content_variation_list.*

class VariationListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_variation_list)
        setSupportActionBar(toolbar)
        setTitle("Ποικιλίες")
        val iteId=intent.extras.getInt("itemid")
        val handler = MyDBHandler(context = baseContext, version = 1, name = null, factory = null)
        val variationList = handler.getVariations()
        val variationListAdapter = VariationListViewAdapter(baseContext!!, variationList)

        variationListView.adapter = variationListAdapter



    }

    inner class  VariationListViewAdapter: BaseAdapter {

        private var variationList:MutableList<Variation>?
        private  var context: Context? =null
        private  val mInflator: LayoutInflater




        constructor(context: Context, variationlist: MutableList<Variation>?) : super(){
            this.variationList=variationlist
            this.context=context


            this.mInflator= LayoutInflater.from(context)

        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val holder: ViewHolder
            val view: View?


            if (convertView==null){
                view= layoutInflater.inflate(R.layout.content_variationlist_item,parent,false)
                holder=ViewHolder(view)
                view.tag=holder

            }
            else {

                view = convertView
                holder = view.tag as ViewHolder
            }



            holder.position=position
            holder.textView.text=variationList!![position].description
            holder.varId=variationList!![position].erpId

            return view
        }

        override fun getItem(position: Int): Any {

            return variationList!![position]
        }

        override fun getItemId(position: Int): Long {

            return position.toLong()
        }

        override fun getCount(): Int {

            return variationList!!.size
        }



    }

    inner class ViewHolder(view: View?){
        var textView: TextView
        var position:Int
        var varId:Int


        init{

            this.textView=view?.findViewById<TextView>(R.id.textView40) as TextView
            this.position=-1
            this.varId=-1

            view.setOnClickListener { v: View ->

                val supplierName=intent.extras.getString("suppliername")
                val supplierId=intent.extras.getInt("supplierid")
                val iteId=intent.extras.getInt("itemid")
                val itemName=intent.extras.getString("itemname")

                val gps=intent.extras.getString("gps")
                val isBio:Int?=intent.extras.getInt("isbio")
                val village=intent.extras.getString("village")
                val county=intent.extras.getString("county")
                val stremma:Float=intent.extras.getFloat("stremma")
                val expQty:Float=intent.extras.getFloat("expqty")
                val operatorName:String=intent.extras.getString("operatorname")
                val operatorPhone:String=intent.extras.getString("operatorphone")
                val comments:String=intent.extras.getString("comments")
                val insdate=intent.extras.getString("inspectiondate")
                val croftId=intent.extras.getInt("croftid")
                val mode=intent.extras.getInt("mode")

                val i = Intent(v.context,CroftDetailsActivity::class.java)




                i.putExtra("varname",textView.text.toString())
                i.putExtra("varid",varId)
                i.putExtra("suppliername",supplierName)
                i.putExtra("supplierid",supplierId)
                i.putExtra("itemid",iteId)
                i.putExtra("itemname",itemName)
                i.putExtra("gps",gps)
                i.putExtra("isbio",isBio)
                i.putExtra("village",village)
                i.putExtra("county",county)
                i.putExtra("stremma",stremma)
                i.putExtra("expqty",expQty)
                i.putExtra("operatorname",operatorName)
                i.putExtra("operatorphone",operatorPhone)
                i.putExtra("comments",comments)
                i.putExtra("inspectiondate",insdate)
                i.putExtra("isupdated",1)
                i.putExtra("croftid",croftId)
                i.putExtra("mode",mode)









                v.context.startActivity(i)

            }
        }
    }

}
