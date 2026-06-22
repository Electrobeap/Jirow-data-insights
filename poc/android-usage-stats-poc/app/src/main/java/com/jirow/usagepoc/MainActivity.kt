package com.jirow.usagepoc

import android.app.Activity
import android.app.AppOpsManager
import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.app.usage.UsageStatsManager
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.concurrent.thread

class MainActivity : Activity() {
    private lateinit var permissionStatus: TextView
    private lateinit var resultText: TextView
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createContentView())
    }

    override fun onResume() {
        super.onResume()
        updatePermissionStatus()
    }

    private fun createContentView(): View {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 40, 32, 24)
            setBackgroundColor(0xFFF7F8FA.toInt())
        }

        val title = TextView(this).apply {
            text = "Jirow Data Usage PoC"
            textSize = 24f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(0xFF101828.toInt())
        }
        root.addView(title)

        val subtitle = TextView(this).apply {
            text = "Validates UsageStatsManager and NetworkStatsManager access on Android 11-15."
            textSize = 14f
            setTextColor(0xFF475467.toInt())
            setPadding(0, 8, 0, 24)
        }
        root.addView(subtitle)

        permissionStatus = TextView(this).apply {
            textSize = 15f
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 0, 0, 20)
        }
        root.addView(permissionStatus)

        val permissionButton = Button(this).apply {
            text = "Open Usage Access Settings"
            setOnClickListener { openUsageAccessSettings() }
        }
        root.addView(permissionButton)

        val refreshButton = Button(this).apply {
            text = "Refresh Usage Data"
            setOnClickListener { refreshUsageData() }
        }
        root.addView(refreshButton)

        resultText = TextView(this).apply {
            text = "Grant Usage Access, then refresh."
            textSize = 14f
            setTextColor(0xFF101828.toInt())
            setPadding(0, 24, 0, 24)
        }

        val scrollView = ScrollView(this).apply {
            addView(resultText)
        }
        root.addView(
            scrollView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        return root
    }

    private fun updatePermissionStatus() {
        val granted = hasUsageAccess()
        permissionStatus.text = if (granted) {
            "Usage Access: granted"
        } else {
            "Usage Access: not granted"
        }
        permissionStatus.setTextColor(if (granted) 0xFF027A48.toInt() else 0xFFB42318.toInt())
    }

    private fun openUsageAccessSettings() {
        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
    }

    private fun refreshUsageData() {
        updatePermissionStatus()
        if (!hasUsageAccess()) {
            resultText.text = "Usage Access is required before NetworkStatsManager can query device or app usage."
            return
        }

        resultText.text = "Loading usage data..."
        thread(name = "usage-stats-reader") {
            val report = runCatching { buildUsageReport() }
                .getOrElse { error -> "Unable to read usage data:\n${error.javaClass.simpleName}: ${error.message}" }

            runOnUiThread {
                updatePermissionStatus()
                resultText.text = report
            }
        }
    }

    private fun hasUsageAccess(): Boolean {
        val appOps = getSystemService(AppOpsManager::class.java)
        val mode = appOps.unsafeCheckOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun buildUsageReport(): String {
        val endTime = System.currentTimeMillis()
        val startTime = Calendar.getInstance().apply {
            timeInMillis = endTime
            add(Calendar.DAY_OF_YEAR, -30)
        }.timeInMillis

        val networkStatsManager = getSystemService(NetworkStatsManager::class.java)
        val packageLabels = loadPackageLabelsByUid()
        val usageStats = loadUsageStatsPackageCount(startTime, endTime)

        val mobileTotal = queryTotalBytes(
            networkStatsManager,
            ConnectivityManager.TYPE_MOBILE,
            startTime,
            endTime
        )
        val wifiTotal = queryTotalBytes(
            networkStatsManager,
            ConnectivityManager.TYPE_WIFI,
            startTime,
            endTime
        )
        val topApps = queryTopApps(networkStatsManager, packageLabels, startTime, endTime)

        return buildString {
            appendLine("Period")
            appendLine("${dateFormat.format(Date(startTime))} to ${dateFormat.format(Date(endTime))}")
            appendLine()
            appendLine("Permission")
            appendLine("Usage Access: granted")
            appendLine("UsageStats packages returned: $usageStats")
            appendLine()
            appendLine("Total data usage")
            appendLine("Mobile: ${formatBytes(mobileTotal)}")
            appendLine("WiFi: ${formatBytes(wifiTotal)}")
            appendLine("Combined: ${formatBytes(mobileTotal + wifiTotal)}")
            appendLine()
            appendLine("Top $TOP_APP_LIMIT data-consuming applications")
            if (topApps.isEmpty()) {
                appendLine("No app-level network usage was returned for this period.")
            } else {
                topApps.take(TOP_APP_LIMIT).forEachIndexed { index, app ->
                    appendLine("${index + 1}. ${app.label} - ${formatBytes(app.totalBytes)}")
                    appendLine("   Mobile: ${formatBytes(app.mobileBytes)} | WiFi: ${formatBytes(app.wifiBytes)}")
                    appendLine("   UID: ${app.uid} | Packages: ${app.packages}")
                }
            }
        }
    }

    private fun queryTotalBytes(
        manager: NetworkStatsManager,
        networkType: Int,
        startTime: Long,
        endTime: Long
    ): Long {
        val bucket = manager.querySummaryForDevice(networkType, null, startTime, endTime)
        return (bucket?.rxBytes ?: 0L) + (bucket?.txBytes ?: 0L)
    }

    private fun queryTopApps(
        manager: NetworkStatsManager,
        packageLabels: Map<Int, PackageLabel>,
        startTime: Long,
        endTime: Long
    ): List<AppNetworkUsage> {
        val usageByUid = mutableMapOf<Int, MutableNetworkUsage>()
        accumulateNetworkUsage(manager, ConnectivityManager.TYPE_MOBILE, startTime, endTime) { uid, bytes ->
            usageByUid.getOrPut(uid) { MutableNetworkUsage(uid) }.mobileBytes += bytes
        }
        accumulateNetworkUsage(manager, ConnectivityManager.TYPE_WIFI, startTime, endTime) { uid, bytes ->
            usageByUid.getOrPut(uid) { MutableNetworkUsage(uid) }.wifiBytes += bytes
        }

        return usageByUid.values
            .filter { it.totalBytes > 0L && it.uid > 0 }
            .map { usage ->
                val packageLabel = packageLabels[usage.uid]
                AppNetworkUsage(
                    uid = usage.uid,
                    label = packageLabel?.label ?: "UID ${usage.uid}",
                    packages = packageLabel?.packages?.joinToString(", ") ?: "Unknown package",
                    mobileBytes = usage.mobileBytes,
                    wifiBytes = usage.wifiBytes
                )
            }
            .sortedByDescending { it.totalBytes }
    }

    private fun accumulateNetworkUsage(
        manager: NetworkStatsManager,
        networkType: Int,
        startTime: Long,
        endTime: Long,
        onBucket: (uid: Int, bytes: Long) -> Unit
    ) {
        var stats: NetworkStats? = null
        try {
            stats = manager.querySummary(networkType, null, startTime, endTime)
            val bucket = NetworkStats.Bucket()
            while (stats.hasNextBucket()) {
                stats.getNextBucket(bucket)
                val bytes = bucket.rxBytes + bucket.txBytes
                if (bytes > 0L) {
                    onBucket(bucket.uid, bytes)
                }
            }
        } finally {
            stats?.close()
        }
    }

    private fun loadPackageLabelsByUid(): Map<Int, PackageLabel> {
        val packageManager = packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val byUid = linkedMapOf<Int, MutableList<ApplicationInfo>>()
        apps.forEach { appInfo ->
            byUid.getOrPut(appInfo.uid) { mutableListOf() }.add(appInfo)
        }

        return byUid.mapValues { (_, appInfos) ->
            val primary = appInfos.first()
            PackageLabel(
                label = primary.loadLabel(packageManager).toString(),
                packages = appInfos.map { it.packageName }.sorted()
            )
        }
    }

    private fun loadUsageStatsPackageCount(startTime: Long, endTime: Long): Int {
        val usageStatsManager = getSystemService(UsageStatsManager::class.java)
        return usageStatsManager
            .queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime)
            ?.map { it.packageName }
            ?.distinct()
            ?.size ?: 0
    }

    private fun formatBytes(bytes: Long): String {
        val decimalFormat = DecimalFormat("#,##0.#")
        val kb = 1024.0
        val mb = kb * 1024.0
        val gb = mb * 1024.0
        return when {
            bytes >= gb -> "${decimalFormat.format(bytes / gb)} GB"
            bytes >= mb -> "${decimalFormat.format(bytes / mb)} MB"
            bytes >= kb -> "${decimalFormat.format(bytes / kb)} KB"
            else -> "$bytes B"
        }
    }

    private companion object {
        const val TOP_APP_LIMIT = 10
    }
}

data class PackageLabel(
    val label: String,
    val packages: List<String>
)

data class MutableNetworkUsage(
    val uid: Int,
    var mobileBytes: Long = 0L,
    var wifiBytes: Long = 0L
) {
    val totalBytes: Long
        get() = mobileBytes + wifiBytes
}

data class AppNetworkUsage(
    val uid: Int,
    val label: String,
    val packages: String,
    val mobileBytes: Long,
    val wifiBytes: Long
) {
    val totalBytes: Long
        get() = mobileBytes + wifiBytes
}
