package com.v2ray.ang.ui.ads

import android.view.View
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView
import com.v2ray.ang.BuildConfig
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.MobileAds

const val VEER_PAID_MIN_BYTES: Long = 10L * 1024 * 1024 * 1024

@Composable
fun VeerAdBanner() {
    val widthDp = LocalConfiguration.current.screenWidthDp
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { ctx ->
            try {
                val banner = BannerAdView(ctx)
                banner.setAdUnitId(BuildConfig.YANDEX_BANNER_ID)
                banner.setAdSize(BannerAdSize.stickySize(ctx, widthDp))
                MobileAds.initialize(ctx.applicationContext) {
                    try {
                        banner.loadAd(AdRequest.Builder().build())
                    } catch (_: Throwable) {
                    }
                }
                banner as View
            } catch (_: Throwable) {
                View(ctx)
            }
        }
    )
}
