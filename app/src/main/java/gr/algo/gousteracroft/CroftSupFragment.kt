package gr.algo.gousteracroft

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.croft_supplier_fragment.*

class CroftSupFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?{

        val view= inflater.inflate(R.layout.croft_supplier_fragment, container, false)
        return view


    }


    override fun onResume() {
        super.onResume()
        val t =this.activity as CroftActivity
        textName.setText(t.name)
        textAfm.setText(t.afm)
        textPhone1.setText(t.phone1)
        textPhone2.setText(t.phone2)
        textPhone3.setText(t.phone3)
        textAddress.setText(t.address)
        textDistrict.setText(t.district)
        textCity.setText(t.city)

    }
}