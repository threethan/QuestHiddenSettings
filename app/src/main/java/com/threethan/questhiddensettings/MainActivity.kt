package com.threethan.questhiddensettings

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun settingsActivity(intentName: String) {
            val intent = Intent()
            intent.component =
                ComponentName("com.android.settings", "com.android.settings.Settings\$$intentName")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        val dnsBtn = findViewById<Button>(R.id.dnsBtn)
        val datetimeBtn = findViewById<Button>(R.id.datetimeBtn)
        val appsBtn = findViewById<Button>(R.id.appsBtn)
        val accessibilityBtn = findViewById<Button>(R.id.accessiblityBtn)
        val infoBtn = findViewById<Button>(R.id.infoBtn)


        dnsBtn.setOnClickListener {
            val items = arrayOf<CharSequence>("AdGuard DNS (Blocks Ads, Recommended)", "Cloudflare DNS (Fast)", "Quad9 (Private)", "Google DNS")
            val hostnames = arrayOf<CharSequence>("dns.adguard-dns.com", "one.one.one.one", "dns11.quad9.net", "dns.google")

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Select a DNS provider")
            builder.setItems(items) { _, item ->
                val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("label",hostnames[item])
                clipBoard.setPrimaryClip(clipData)

                // Show instructions
                val builder2 = AlertDialog.Builder(this)
                builder2.setTitle("Copied DNS Provider!")
                builder2.setMessage(
                    "1. Click next to open network settings\n" +
                    "2. Scroll down, press Advanced, then Private DNS\n" +
                    "3. Select Private DNS provider hostname. Delete all text that may be there, then press and hold to paste \""+hostnames[item]+"\"\n" +
                    "4. Press OK and enjoy!\n\n" +
                    "WARNING: Depending on your chosen DNS, you may need to turn this off to sync with your phone"
                )
                builder2.setPositiveButton("Next") { _, _ ->
                    settingsActivity("NetworkDashboardActivity")
                }
                builder2.create().show()
                }
            builder.setNegativeButton("Cancel") { _, _ -> }
            builder.create().show()
        }

        datetimeBtn.setOnClickListener {
            settingsActivity("DateTimeSettingsActivity")
        }

        appsBtn.setOnClickListener {
            val intent = Intent()
            intent.component =
                ComponentName("com.android.settings", "com.android.settings.applications.ManageApplications")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        accessibilityBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Notice!")
            builder.setMessage(
                "Many of the settings here don't work.\n"+
                "You can enable mono audio or adjust balance,\n"+
                "enable bold or high-contrast text system wide,\n"+
                "or adjust text size without being forced to restart.\n\n"+
                "Use the Quest's accessibility for working color filters."
            )
            builder.setPositiveButton("Continue") { _, _ ->
                settingsActivity("AccessibilitySettingsActivity")
            }
            builder.setNegativeButton("Cancel") { _, _ -> }
            builder.create().show()
        }

        devBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Notice!")
            builder.setMessage(
                "Many of the settings in here don't work or could potentially harm your device, \n"+
                "Do NOT continue unless you know what you are doing. \n"+
                "Button not working? \n"+
                "Try going to Device Info and pressing on the build number 10 times.\n\n"+
                "Use the Quest's developer settings for stuff like unlocking the bootloader."
            )
            builder.setPositiveButton("Continue") { _, _ ->
                settingsActivity("DevelopmentSettingsDashboardActivity")
            }
            builder.setNegativeButton("Cancel") { _, _ -> }
            builder.create().show()
        }

        infoBtn.setOnClickListener {
            settingsActivity("MyDeviceInfoActivity")
        }

    }
}

