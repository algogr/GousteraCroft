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

import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.content_item_list.*


class ItemListActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)
        setSupportActionBar(toolbar)
        setTitle("Φρούτα")
        val handler = MyDBHandler(context = baseContext, version = 1, name = null, factory = null)
        val itemList = handler.getItems()
        val itemListAdapter = ItemListViewAdapter(baseContext!!, itemList)

        itemListView.adapter = itemListAdapter



    }


    inner class  ItemListViewAdapter: BaseAdapter {

        private var itemList:MutableList<Item>?
        private  var context: Context? =null
        private  val mInflator: LayoutInflater




        constructor(context: Context, itemlist: MutableList<Item>?) : super(){
            this.itemList=itemlist
            this.context=context


            this.mInflator= LayoutInflater.from(context)

        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val holder: ViewHolder
            val view: View?


            if (convertView==null){
                view= layoutInflater.inflate(R.layout.content_item_list_item,parent,false)
                holder=ViewHolder(view)
                view.tag=holder

            }
            else {

                view = convertView
                holder = view.tag as ViewHolder
            }



            holder.position=position
            holder.textView.text=itemList!![position].descr
            holder.itemId=itemList!![position].erpid

            return view
        }

        override fun getItem(position: Int): Any {

            return itemList!![position]
        }

        override fun getItemId(position: Int): Long {

            return position.toLong()
        }

        override fun getCount(): Int {

            return itemList!!.size
        }



    }

    inner class ViewHolder(view: View?){
        var textView: TextView
        var position:Int
        var itemId:Int


        init{

            this.textView=view?.findViewById<TextView>(R.id.textView30) as TextView
            this.position=-1
            this.itemId=-1


            view.setOnClickListener { v: View ->

                val supName=intent.extras.getString("suppliername")
                val supplierId=intent.extras.getInt("supplierid")
                val varId=intent.extras.getInt("varid")

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

                i.putExtra("itemname",textView.text.toString())
                i.putExtra("itemid",itemId)
                i.putExtra("varid",varId)
                i.putExtra("supplierid",supplierId)
                i.putExtra("suppliername",supName)
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
