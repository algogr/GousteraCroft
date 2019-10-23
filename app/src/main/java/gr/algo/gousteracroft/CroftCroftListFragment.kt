package gr.algo.gousteracroft

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.croft_croftlist_fragment.*

class CroftCroftListFragment: Fragment() {


        var supId: Int=0
        lateinit var supName:String
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?{





            val view = inflater.inflate(R.layout.croft_croftlist_fragment, container, false)
            val listCroft=view.findViewById<ListView>(R.id.listCroft)


        return view
    }


    override fun onResume() {
        super.onResume()
        val t = this.activity as CroftActivity
        val erpId = t.erpId
        supId = t.supplierId
        supName=t.name!!
        val handler = MyDBHandler(context = this.context!!, version = 1, name = null, factory = null)

        val croftList = {
            val list:MutableList<Croft>
            if (erpId>0){
                list=handler.croftBySupplier(erpId)
            }
            else{
                list=handler.croftBySupplier(supId)
            }
            list
        }
        val croftListAdapter = CroftListViewAdapter(this.context!!, croftList())

        listCroft.adapter = croftListAdapter

    }

    inner class  CroftListViewAdapter:BaseAdapter{

        private var croftList:MutableList<Croft>?
        private  var context: Context? =null
        private  val mInflator: LayoutInflater




        constructor(context: Context, croftlist: MutableList<Croft>?) : super(){
            this.croftList=croftlist
            this.context=context


            this.mInflator= LayoutInflater.from(context)

        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val holder: ViewHolder
            val view: View?


            if (convertView==null){
                view= layoutInflater.inflate(R.layout.croft_croftlist_item,parent,false)
                holder=ViewHolder(view)
                view.tag=holder

            }
            else {

                view = convertView
                holder = view.tag as ViewHolder
            }



            holder.position=position
            holder.textView.text=croftList!![position].village+"-"+croftList!![position].county+"-"+croftList!![position].stremma.toString()+" στρέμματα"
            holder.textView1.text=croftList!![position].operatorName+"-"+croftList!![position].operatorPhone
            holder.erpId=croftList!![position].erpId
            holder.croftId=croftList!![position].id
            holder.supplierId=croftList!![position].suppplierId
            holder.itemId=croftList!![position].iteid
            holder.varId=croftList!![position].variationid
            holder.isBio=croftList!![position].isBio
            holder.gps=croftList!![position].gps
            holder.village=croftList!![position].village
            holder.county=croftList!![position].county
            holder.stremma=croftList!![position].stremma
            holder.expqty=croftList!![position].expetedqty
            holder.operatorName=croftList!![position].operatorName
            holder.operatorPhone=croftList!![position].operatorPhone
            holder.comments=croftList!![position].comments
            holder.inspectionDate=croftList!![position].inspectionDate
            holder.supplierName=supName

            return view
        }

        override fun getItem(position: Int): Any {

            return croftList!![position]
        }

        override fun getItemId(position: Int): Long {

            return position.toLong()
        }

        override fun getCount(): Int {

            return croftList!!.size
        }



    }

    private class ViewHolder(view: View?){
        var textView: TextView
        var textView1: TextView
        var position:Int=-1
        var erpId:Int=-1
        var croftId:Int=-1
        var supplierId:Int=-1
        var itemId:Int=-1
        var varId:Int=-1
        var isBio:Int=-1
        var gps:String=""
        var village=""
        var county:String=""
        var stremma:Float?=null
        var expqty:Float?= null
        var operatorName:String=""
        var operatorPhone:String=""
        var comments:String=""
        var inspectionDate:String=""
        var supplierName:String=""




        init{

            this.textView=view?.findViewById<TextView>(R.id.textView20) as TextView
            this.textView1=view?.findViewById<TextView>(R.id.textView21) as TextView



            view.setOnClickListener { v: View  ->
                val i = Intent(v.context,CroftDetailsActivity::class.java)
                i.putExtra("croftid",croftId)
                i.putExtra("erpid",erpId)
                i.putExtra("supplierid",supplierId)
                i.putExtra("suppliername",supplierName)
                i.putExtra("itemid",itemId)
                i.putExtra("varid",varId)
                i.putExtra("isbio",isBio)
                i.putExtra("gps",gps)
                i.putExtra("village",village)
                i.putExtra("county",county)
                i.putExtra("stremma",stremma)
                i.putExtra("expqty",expqty)
                i.putExtra("operatorname",operatorName)
                i.putExtra("operatorphone",operatorPhone)
                i.putExtra("comments",comments)
                i.putExtra("inspectiondate",inspectionDate)
                i.putExtra("mode",1)
                v.context.startActivity(i)
            }
        }
    }

}