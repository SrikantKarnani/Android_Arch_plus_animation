package com.example.anew.features.deeplink_tester

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.CATEGORY_BROWSABLE
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.anew.R
import com.example.anew.databinding.FragmentDeeplinkTesterBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.NumberFormatException


/**
 * A simple [Fragment] subclass.
 * Use the [DeeplinkTesterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class DeeplinkTesterFragment : Fragment(R.layout.fragment_deeplink_tester) {

    lateinit var binding: FragmentDeeplinkTesterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDeeplinkTesterBinding.bind(view)
        binding.btnTry.setOnClickListener {
            activity?.let {
                val url = Uri.parse(binding.tiel.text.toString())
                val browserIntent = Intent()
                    .setAction(Intent.ACTION_VIEW)
                    .addCategory(Intent.CATEGORY_BROWSABLE)
                    .setData(url)
                var possibleBrowsers = it.packageManager.queryIntentActivities(
                    browserIntent,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
                if (possibleBrowsers.size == 0) {
                    possibleBrowsers = it.packageManager.queryIntentActivities(
                        browserIntent,
                        PackageManager.MATCH_ALL
                    )
                }
                if (possibleBrowsers.size > 0) {
                    val browserIntent2 = Intent(Intent.ACTION_VIEW, url);
                    startActivity(browserIntent2);
                    // Only browser apps are available, or a browser is the default app for this intent
                }
            }
        }
    }
}