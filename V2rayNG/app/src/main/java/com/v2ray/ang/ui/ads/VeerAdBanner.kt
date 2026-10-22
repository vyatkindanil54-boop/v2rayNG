package com.v2ray.ang.ui.ads

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.v2ray.ang.BuildConfig
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.MobileAds

const val VEER_PAID_MIN_BYTES: Long = 10L * 1024 * 1024 * 1024

private var adsInitialized = false

private fun initAds(context: Context) {
    if (adsInitialized) return
    adsInitialized = true
    MobileAds.initialize(context.applicationContext) { }
}

@Composable
fun VeerAdBanner() {
    val context = LocalContext.current
    val widthDp = LocalConfiguration.current.screenWidthDp
    LaunchedEffect(Unit) { initAds(context) }
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { ctx ->
            BannerAdView(ctx).apply {
                setAdUnitId(BuildConfig.YANDEX_BANNER_ID)
                setAdSize(BannerAdSize.stickySize(ctx, widthDp))
                loadAd(AdRequest.Builder().build())
            }
        },
        onRelease = { it.destroy() }
    )
}
