package com.example.nabtastore

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.nabtastore.databinding.ActivityHomeBinding
import com.example.nabtastore.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class home : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var mAuth: FirebaseAuth
    lateinit var mDbRef : DatabaseReference
    lateinit var DbRef : DatabaseReference
    lateinit var Preferences: SharedPreferences
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationid = 101
    lateinit var res : String
    lateinit var musicPlayer:MediaPlayer
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel("kimo","kamal")



        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference
        DbRef = FirebaseDatabase.getInstance().getReference("accident")
        var toolbar =binding.toolbar

        setSupportActionBar(toolbar)

        Preferences =  getSharedPreferences("share_pref", Context.MODE_PRIVATE)

        mDbRef.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for (postSnapshot in snapshot.children )
                {
                    val currentuser = postSnapshot.getValue(User::class.java)

                    if (mAuth.currentUser?.uid == currentuser?.uid)
                    {
                        binding.tvPerson.text = currentuser!!.name.toString()

                    }



                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
        DbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var v= snapshot.getValue()
                res = v.toString()
                if (res =="1")
                {
                    binding.tvHello.text="Hello Mr: "
                    binding.tvPersonName.text=binding.tvPerson.text

                    binding.firebaseMessage.text = "there are an accident is happening now for mohamed ,He isn't be ok,He often has some serious injuries, you should go to his site to check on him"

                    binding.tvGps.text="click to see his location"
                    setalarm()



                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        binding.cancel.setOnClickListener {
            cancelalarm()
            DbRef.setValue(0)

        }
        binding.tvGps.setOnClickListener {
            var i = Intent(this,MapsActivity::class.java)
            startActivity(i)
        }
























    }
    private fun createNotificationChannel(Name :String , Description :String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val name :CharSequence = "foxandroid"
            val description = "Channel for alarm"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("foxandroid",name, importance)
            channel.description= description

            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)

        }
        musicPlayer = MediaPlayer.create(this
            ,R.raw.my)
        musicPlayer.isLooping=true
        musicPlayer.setVolume(100f,100f)


    }

    private fun setalarm()
    {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,notification::class.java)
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)
        alarmManager.setRepeating(
            AlarmManager.RTC,0
            ,1000,pendingIntent
        )
        Toast.makeText(this,"there are an accident...",Toast.LENGTH_LONG).show()


    }
    private fun cancelalarm()
    {
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,notification::class.java)
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)
        alarmManager.cancel(pendingIntent)

        Toast.makeText(this,"thanks for your showing...",Toast.LENGTH_LONG).show()

    }


    /*

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)


        }
    }
    private  fun sendNotification(des: String) {
        val intent = Intent(this, home::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.accident)
        val bitmaplarg =
            BitmapFactory.decodeResource(applicationContext.resources, R.drawable.accident)


        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.accident).setContentTitle("kimo").setContentText(des)
            .setLargeIcon(bitmaplarg)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationid, builder.build())
        }
    }*/




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if (item.itemId == R.id.logout)
        {

            mAuth.signOut()
            val editor: SharedPreferences.Editor = Preferences.edit()
            editor.clear()
            editor.apply()
            var i = Intent(this, sign::class.java)
            startActivity(i)
            finish()


            return true
        }
        return true
    }
}