package com.example.newfunctest_recycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newfunctest_recycle.models.DRContainer
import com.example.newfunctest_recycle.network.ContainerService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var idInputSlot = findViewById<EditText>(R.id.idInputSlot)
        val inputBtn = findViewById<Button>(R.id.button3)
        var count = findViewById<TextView>(R.id.count)
        var scanWord: TextView = findViewById(R.id.scanWord)
        var inputList = arrayListOf<String>()
        val clearBtn: Button = findViewById(R.id.clearBtn)


        inputBtn.setOnClickListener {
            if (
                idInputSlot.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "請輸入號碼!", Toast.LENGTH_SHORT).show()
            } else {
                var numberId = idInputSlot!!.text.toString()/*.toInt()*/
                inputList.add(idInputSlot.text.toString())
                Log.d("Main", "Input button clicked")
                getInternetConnection(numberId)

                //處理介面呈現
                idInputSlot.text.clear()
                count.text = inputList.size.toString() //要記的是list的資料筆數
                println("資料增加，總筆數: ${inputList.size}")
                count.visibility = View.VISIBLE
                scanWord.text = "已登錄容器"

            }
        }
        // 清除一筆資料
        clearBtn.setOnClickListener {
            when {
                inputList.size == 1 -> {
                    scanWord.text = "尚未掃瞄任何容器"
                    count.visibility = View.GONE
                    inputList.removeAt(0)

                }
                inputList.size > 1 -> {
                    inputList.removeAt(0)
                }
                else -> {
                    scanWord.text = "尚未掃瞄任何容器"
                    count.visibility = View.GONE
                }
            }
            println("資料減少，總筆數: ${inputList.size}")
            count.text = inputList.size.toString()
        }

        // FAB動作
        val dataSendFab: FloatingActionButton = findViewById(R.id.proceedBtn)
        dataSendFab.setOnClickListener {
            // TODO:(監控輸入欄位是否還有資料)

            if(inputList.size > 0){

                val intent = Intent(this, ResultActivity::class.java)
                intent.putStringArrayListExtra("Test",inputList)
                startActivity(intent)
            } else {
                Toast.makeText(this, "無輸入資料!", Toast.LENGTH_SHORT).show()
            }


        }


        var showText = findViewById<TextView>(R.id.showText)
        var generateBtn = findViewById<Button>(R.id.generateBtn)
        var timeNow = Date().time

        //查看驗證碼
        generateBtn.setOnClickListener {
            Log.d("MAIN", "generateBtn clicked")

            showText.text = standardAddOrderTime(timeNow)
            Log.d("MAIN", "${showText.text}")
            //showText2.text = Timenow.toString()

        }

        //
        var getPostBtn = findViewById<Button>(R.id.getPostBtn)
        getPostBtn.setOnClickListener {
            /*Log.d("MAIN", "getPostBtn clicked")
            getInternetConnection()*/
            //getList()
        }


        // USB事件動作  1.允許輸入 2.停止接收
        /*val usbDevice: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)




        private const val ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION"

        val manager = getSystemService(Context.USB_SERVICE) as UsbManager
        val deviceList = manager.getDeviceList()
        val device = deviceList.get("deviceName")
        permissionIntent = PendingIntent.getBroadcast(this, 0, Intent(ACTION_USB_PERMISSION), 0)
        val filter = IntentFilter(ACTION_USB_PERMISSION)
        registerReceiver(usbReceiver, filter)

*/

    }

    /*private fun usb() {
        val ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION"
        val manager = getSystemService(Context.USB_SERVICE) as UsbManager
        permissionIntent = PendingIntent.getBroadcast(this, 0, Intent(ACTION_USB_PERMISSION), 0)
        val filter = IntentFilter(ACTION_USB_PERMISSION)
        registerReceiver(usbReceiver, filter)

        lateinit var device: UsbDevice
        usbManager.requestPermission(device, permissionIntent)



        var usbReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                if (ACTION_USB_PERMISSION == intent.action) {
                    synchronized(this) {
                        val device: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)

                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            device?.apply {
                                //call method to set up device communication
                            }
                        } else {
                            Log.d("MAIN", "permission denied for device $device")
                        }
                    }
                }



                if (UsbManager.ACTION_USB_DEVICE_DETACHED == intent.action) {
                    val device: UsbDevice? = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE)
                    device?.apply {
                        // call your method that cleans up and closes communication with the device
                    }
                }
            }

        }
    }*/

    private fun getInternetConnection(containerStr: String){

        if(Constants.isNetworkAvailable(this)){
            Toast.makeText(
                this@MainActivity,
                "Internet connected",Toast.LENGTH_LONG
            ).show()

            val retrofit : Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service : ContainerService = retrofit
                .create(ContainerService::class.java)

            /*val listCall: Call<DataResponse> = service.getContainer(reqID(),reqTime())*/
            val listCall: Call<DRContainer> = service.readyToCleanContainer(
                standardAddOrderTime(System.currentTimeMillis()),
                Constants.apikey, containerStr)

            Log.d("Main", "listCall initiated")
            Log.d("Main",containerStr)

            listCall.enqueue(object : Callback<DRContainer>{
                override fun onResponse(
                    call: Call<DRContainer>,
                    response: Response<DRContainer>
                ) {
                    if(response!!.isSuccessful){
                        val containerList: DRContainer = response.body()!!
                        Log.d("Main","$containerList")
                        Log.d("Main","${response.code()}")
                    }else{
                        val rc = response.code()
                        when(rc){
                            400 -> {
                                Log.e("Error 400", "Bad Connection")
                            }
                            404 -> {
                                Log.e("Error 404", "Not Found")
                            }
                            401 -> {
                                Log.e("Error 401", "Header needed")
                            }
                            else ->{
                                Log.e("Error", response.message())
                            }}
                        }
                    }

                override fun onFailure(call: Call<DRContainer>, t: Throwable) {
                    Log.e("Error", t!!.message.toString())
                }
            })

        }else{
            Toast.makeText(
                this@MainActivity,
                "No internet connection available...",Toast.LENGTH_LONG
            ).show()
        }
    }


    /*private fun getList() {
        val api = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyAPI::class.java)


        GlobalScope.launch(Dispatchers.IO){
            Log.d("MAIN", "GlobalScope Launched")
            try {
                val response = api.getList().awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!

                    Toast.makeText(applicationContext,"it is successful!", Toast.LENGTH_SHORT).show()
                    //TODO:(add your customized code here)

                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext,"Something went wrong...", Toast.LENGTH_SHORT).show()
                    //display = response.code().toString()
                    Log.d("Main",e.toString())

                }
            }
        }
    }*/

    fun reqID(): String {
        val hex =
            arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
        val r = Random()
        var id = ""
        for (i in 0..9) id += hex[r.nextInt(16)].toString()
        return id
    }  // verified!

    fun reqTime(): String {
        return System.currentTimeMillis().toString()
    }  // verified!

    fun standardAddOrderTime(date: Long): String {
        val d = Date()
        d.time = d.time + 259200
        var secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

        //var base64Key = Encoders.BASE64.encode(key.getEncoded())
        return Jwts.builder().setId(reqID())
            .setIssuedAt(Date())
            .setExpiration(d)
            .claim("orderTime", date)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.layout_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.qrcode_input -> {
                //text_view.text = "Cut"
                return true
            }
            R.id.keyin_input -> {
                //text_view.text = "Copy"
                return true
            }
            R.id.other_input -> {
                //text_view.text = "Paste"
                return true
            }
            R.id.other2 -> {
                //text_view.text = "Paste"
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }





    }













