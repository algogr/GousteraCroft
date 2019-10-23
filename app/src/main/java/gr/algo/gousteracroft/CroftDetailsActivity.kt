package gr.algo.gousteracroft

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.*

import kotlinx.android.synthetic.main.activity_croft_details.*
import kotlinx.android.synthetic.main.content_croft_details.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class CroftDetailsActivity : AppCompatActivity(){

    var itemName:String?=null
    var supId:Int?=0
    var iteId:Int?=0
    var varId:Int?=0
    var varName:String?=null
    var isBio:Int?=0
    var gps:String?=""
    var village:String?=""
    var county:String?=""
    var stremma:Float?=null
    var expqty:Float?=null
    var operatorName:String?=""
    var operatorPhone:String?=""
    var comments:String?=""
    var inspectionDate:String?=""
    var supplierName:String?=""
    var id:Int?=0




    var fusedLocationClient: FusedLocationProviderClient? = null
    var mode:Int=0 //Mode 0 new Croft 1 editcroft
    var isUpdated:Int=0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_croft_details)
        setSupportActionBar(toolbar)
        setTitle("Στοιχεία κτήματος")

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        val extras: Bundle? = intent.extras

        mode = if (extras?.getInt("mode") == 1) 1 else 0


        isUpdated = if (extras?.getInt("isupdated") == 1) 1 else 0








        if (mode == 1 || isUpdated == 1) {
            id = extras?.getInt("croftid")
            itemName = extras?.getString("itemname")
            iteId = extras?.getInt("itemid")

            varId = extras?.getInt("varid")
            varName = extras?.getString("varname")
            val handler = MyDBHandler(context = baseContext, version = 1, name = null, factory = null)

            supId = extras?.getInt("supplierid")
            supplierName = extras?.getString("suppliername")
            suppText.setText(supplierName)





            isBio = extras?.getInt("isbio")
            isbioSwitch.isChecked = if (isBio == 1) true else false
            gps = extras?.getString("gps")
            gpsButton.setText(gps)
            village = extras?.getString("village")
            villageEdit.text = SpannableStringBuilder(village)
            county = extras?.getString("county")
            countyEdit.text = SpannableStringBuilder(county)
            stremma = extras?.getFloat("stremma")
            stremmaEdit.text = SpannableStringBuilder(stremma.toString())
            expqty = extras?.getFloat("expqty")
            qtyEdit.text = SpannableStringBuilder(expqty.toString())
            operatorName = extras?.getString("operatorname")
            operatorNameEdit.text = SpannableStringBuilder(operatorName)
            operatorPhone = extras?.getString("operatorphone")
            operatorPhoneEdit.text = SpannableStringBuilder(operatorPhone)
            comments = extras?.getString("comments")
            commentsEdit.text = SpannableStringBuilder(comments)
            inspectionDate = extras?.getString("inspectiondate")

            val l = {
                val answer: Long

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val l = LocalDate.parse(inspectionDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                    answer = l.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()


                } else {
                    var date = SimpleDateFormat("yyyy-MM-dd").parse(inspectionDate)
                    answer = date.time

                }
                answer
            }
            calendarView.date = l()
            Log.d("JIM-CROFITEID",iteId.toString())
            itemName = itemName ?: handler.getItemDescr(iteId!!)
            varName = varName ?: handler?.getvariationDescr(varId)?:"ΠΟΙΚΙΛΙΑ"


        }




        supplierName = extras?.getString("suppliername")
        supId = extras?.getInt("supplierid")
        itemButton.setText(itemName ?: "ΦΡΟΥΤΟ")
        variationButton.setText(varName ?: "ΠΟΙΚΙΛΙΑ")
        variationButton.setEnabled(itemName != null)
        suppText.setText(supplierName)








        itemButton.setOnClickListener {
            val i = Intent(it.context, ItemListActivity::class.java)
            createExtras(i)
            it.context.startActivity(i)
        }

        variationButton.setOnClickListener {
            val i = Intent(it.context, VariationListActivity::class.java)
            createExtras(i)
            it.context.startActivity(i)

        }


        gpsButton.setOnClickListener {
            Log.d("JIM1", "1")
            if (checkPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                if (isLocationEnabled()) {
                    Log.d("JIM1", "2")
                    fusedLocationClient?.lastLocation?.addOnSuccessListener(this,
                        { location: Location? ->
                            Log.d("JIM1-Location", location.toString())
                            if (location == null) {
                                requestNewLocationData()
                                Toast.makeText(
                                    applicationContext,
                                    "Δοκιμάστε πρώτα με το Google Maps",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else location.apply {
                                // Handle location object
                                gpsButton.setText(location?.longitude.toString() + "," + location?.latitude.toString())
                            }

                        })
                } else {
                    Toast.makeText(this, "Ενεργοποιήστε την υπηρεσίας εύρεσης τοποθεσίας", Toast.LENGTH_LONG).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }


            }
        }


            acceptButton1.setOnClickListener {

                val isBio = if (isbioSwitch.isChecked) 1 else 0

                val gps = gpsButton.text.toString()
                val village = villageEdit.text.toString()
                val county = countyEdit.text.toString()
                val stremma = stremmaEdit.text.toString().toFloatOrNull()
                val expqty = qtyEdit.text.toString().toFloatOrNull()
                val operator = operatorNameEdit.text.toString()
                val operatorphone = operatorPhoneEdit.text.toString()
                val comments = commentsEdit.text.toString()
                val insdate = {
                    var answer: String
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val current = calendarView.date.toString()
                        //val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        answer = current.format(formatter)
                        Log.d("answer", answer)

                    } else {
                        var date = calendarView.date
                        //val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
                        val formatter = SimpleDateFormat("yyyy-MM-dd")
                        answer = formatter.format(date)
                        Log.d("answer", answer)

                    }
                    answer
                }
                val handler = MyDBHandler(context = baseContext, version = 1, name = null, factory = null)
                val query: String
                val pSupid = if (handler.getSupplierErpId(supId!!) == 0) supId else handler.getSupplierErpId(supId!!)
                if (mode == 0) {


                    query =
                        " INSERT INTO croft(supplierid,iteid,variationid,isbio,gps,village,county,stremma,expectedqty,operatorname,operatorphone,comments,inspectiondate,erpupd) VALUES (" +
                                pSupid.toString() + "," + iteId.toString() + "," + varId.toString() + "," + isBio.toString() + ",'" + gps +
                                "','" + village + "','" + county + "'," + stremma.toString() + "," + expqty.toString() + ",'" + operator + "','" + operatorphone+"','"+
                                comments + "','" + insdate() + "',1)"
                } else {
                    query =
                        " UPDATE croft set iteid=" + iteId.toString() + ",variationid=" + varId.toString() + ",isbio=" + isBio.toString() + ",gps='" +
                                gps + "',village='" + village + "',county='" + county + "',stremma=" + stremma.toString() + ",expectedqty=" + expqty.toString() + ",operatorname='" +
                                operator +"',operatorphone='"+operatorphone+ "',comments='" + comments + "',inspectiondate='" + insdate() + "',erpupd=2 where id=" + id.toString()
                }
                Log.d("JIM",query)

                handler.insertUpdate(query)
                handler.close()
                val i = Intent(it.context, SupplierListActivity::class.java)
                it.context.startActivity(i)
            }

            cancelButton1.setOnClickListener({
                val i = Intent(it.context, SupplierListActivity::class.java)
                it.context.startActivity(i)

            })


        Log.d("JIM-INTEMID",iteId.toString())
    }



    private fun createExtras(i:Intent)
    {
        i.putExtra("croftid",id!!)
        i.putExtra("suppliername",suppText.text)
        i.putExtra("supplierid",supId!!)
        i.putExtra("itemname",itemButton.text)
        i.putExtra("itemid",iteId)
        i.putExtra("varid",varId)
        i.putExtra("varname",variationButton.text)
        i.putExtra("gps",gpsButton.text)
        i.putExtra("isbio",if(isbioSwitch.isChecked) 1 else 0)
        i.putExtra("village",villageEdit.text.toString())
        i.putExtra("county",countyEdit.text.toString())
        i.putExtra("stremma",stremmaEdit.text.toString().toFloatOrNull())
        i.putExtra("expqty",qtyEdit.text.toString().toFloatOrNull())
        i.putExtra("operatorname",operatorNameEdit.text.toString())
        i.putExtra("operatorphone",operatorPhoneEdit.text.toString())
        i.putExtra("comments",commentsEdit.text.toString())
        val insdate= {
            var answer: String
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = calendarView.date.toString()
                //val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss")
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                answer = current.format(formatter)
                Log.d("answer", answer)

            } else {
                var date = calendarView.date
                //val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                answer = formatter.format(date)
                Log.d("answer", answer)

            }
            answer
        }
        i.putExtra("inspectiondate",insdate())
        i.putExtra("mode",mode)

    }



    val PERMISSION_ID = 42
    private fun checkPermission(vararg perm:String) : Boolean {
        val havePermissions = perm.toList().all {
            ContextCompat.checkSelfPermission(this,it) ==
                    PackageManager.PERMISSION_GRANTED
        }
        Log.d("JIM1-HAVE PERMISSIONS",havePermissions.toString())
        if (!havePermissions) {
            if(perm.toList().any {
                    ActivityCompat.
                        shouldShowRequestPermissionRationale(this, it)}
            ) {
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Permission")
                    .setMessage("Permission needed!")
                    .setPositiveButton("OK", {id, v ->
                        ActivityCompat.requestPermissions(
                            this, perm, PERMISSION_ID)
                    })
                    .setNegativeButton("No", {id, v -> })
                    .create()
                dialog.show()
            } else {
                ActivityCompat.requestPermissions(this, perm, PERMISSION_ID)
            }
            return false
        }
        return true
    }


    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }




    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 10000
        mLocationRequest.fastestInterval = 5000
        // mLocationRequest.numUpdates = 1
        Log.d("JIMGPS","CALLBACK")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }


    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            Log.d("JIMGPS",mLastLocation.toString())
        }
    }



    }






