package me.liuyun.bjutlgn.ui

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.artitk.licensefragment.model.CustomUI
import com.artitk.licensefragment.model.License
import com.artitk.licensefragment.model.LicenseID
import com.artitk.licensefragment.model.LicenseType
import com.artitk.licensefragment.support.v4.RecyclerViewLicenseFragment
import me.liuyun.bjutlgn.R
import me.liuyun.bjutlgn.databinding.ActivityLicenseBinding

class LicenseActivity : AppCompatActivity() {
    val binding: ActivityLicenseBinding by lazy { DataBindingUtil.setContentView<ActivityLicenseBinding>(this, R.layout.activity_license) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        val licenses = arrayListOf(
                License(this, "Android Support Library", LicenseType.APACHE_LICENSE_20, "2005-2013", "The Android Open Source Project"),
                License(this, "RxJava", LicenseType.APACHE_LICENSE_20, "2016-present", "RxJava Contributors"),
                License(this, "RxAndroid", LicenseType.APACHE_LICENSE_20, "2015", "The RxAndroid authors"),
                License(this, "Butter Knife", LicenseType.APACHE_LICENSE_20, "2013", "Jake Wharton"),
                License(this, "RingProgressBar", LicenseType.APACHE_LICENSE_20, "2017", "HotBitmapGG"),
                License(this, "Rebound", LicenseType.BSD_2_CLAUSE, "2013", "Facebook, Inc."),
                License(this, "ORMLite", R.raw.isc, "", "")
        )

        val fragment = RecyclerViewLicenseFragment.newInstance()
                .addCustomLicense(licenses)
                .addLicense(intArrayOf(LicenseID.OKHTTP, LicenseID.RETROFIT, LicenseID.LICENSE_FRAGMENT))
                .setCustomUI(CustomUI()
                        .setTitleTextColor(resources.getColor(R.color.colorAccent, theme))
                        .setLicenseBackgroundColor(resources.getColor(R.color.background_grey, theme))
                        .setLicenseTextColor(Color.DKGRAY))

        supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()
    }
}
