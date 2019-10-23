package gr.algo.gousteracroft

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.os.Debug
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.Toast


import kotlinx.android.synthetic.main.activity_upload_db.*
import kotlinx.android.synthetic.main.content_upload_db.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

operator fun JSONArray.iterator(): Iterator<JSONObject> =
        (0 until length()).asSequence().map { get(it) as JSONObject }.iterator()
class UploadDB : AppCompatActivity() {
    lateinit var toastMsg:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_db)
        setSupportActionBar(toolbar)
        setTitle("Επικοινωνία με ERP")

        button.setOnClickListener {
            toastMsg="Η αποστολή ξεκίνησε"
            if (isNetworkAvailable()) {
                 UploadFilesTask(this).execute(1)
                Toast.makeText(this,toastMsg,Toast.LENGTH_LONG).show()

            }

            else
                Toast.makeText(baseContext,"Δεν υπάρχει δίκτυο",Toast.LENGTH_LONG).show()



        }
        button2.setOnClickListener {
            if (isNetworkAvailable()) {
                val r=DownloadFilesTask(this).execute(1)

                /*
                when (r) {

                    0->{Toast.makeText(baseContext,"O server δεν είναο διαθέσιμος ",Toast.LENGTH_LONG).show()}
                    1->{Toast.makeText(baseContext,"Η αποστολή ολοκληρώθηκε επιτυχώς ",Toast.LENGTH_LONG).show()}


                }
                  */

                }

            else
                Toast.makeText(baseContext,"Δεν υπάρχει δίκτυο",Toast.LENGTH_LONG).show()



        }


    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }


    companion object {
        val TAG = "MainActivity"
    }



}