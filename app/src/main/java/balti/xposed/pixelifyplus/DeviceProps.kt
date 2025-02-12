package balti.xposed.pixelifyplus

/**
 * Build values taken from:
 * Pixel 6:
 * https://github.com/DotOS/android_frameworks_base/blob/dot12/core/java/com/android/internal/util/custom/PixelPropsUtils.java
 * Pixel 2:
 * https://gist.github.com/markstachowski/4069f15c5c989827b9e64c0aec045434
 * Pixel 5a:
 * https://github.com/LineageOS/android_device_google_barbet/blob/lineage-18.1/lineage_barbet.mk
 * All other pixels:
 * https://github.com/orgs/Pixel-Props/repositories
 * Also from
 * https://github.com/DotOS/android_frameworks_base/commit/3f7ea7d070017ed1f38035333f084865865698b2
 *
 * Features taken from:
 * https://t.me/PixelProps
 * https://github.com/DotOS/android_vendor_dot/blob/dot12/prebuilt/common/etc/pixel_2016_exclusive.xml
 * https://github.com/ayush5harma/PixelFeatureDrops/tree/master/system/etc/sysconfig
 */
object DeviceProps {

    /**
     * Class to store different feature flags for different pixels.
     * @param displayName String to show to user to customize flag selection. Example "Pixel 2020"
     * Also note that these display names are what is actually stored in shared preferences.
     * The actual feature flags are then derived from the display names.
     * @param featureFlags List of actual features spoofed to Google Photos for that particular [displayName].
     * Example, for [displayName] = "Pixel 2020", [featureFlags] = listOf("com.google.android.feature.PIXEL_2020_EXPERIENCE")
     */
    class Features(
        val displayName: String,
        val featureFlags: List<String>,
    ){
        constructor(displayName: String, vararg featureFlags: String) : this(
            displayName,
            featureFlags.toList()
        )

        constructor(displayName: String, singleFeature: String) : this(
            displayName,
            listOf(singleFeature)
        )
    }

    /**
     * List of all possible feature flags.
     * CHRONOLOGY IS IMPORTANT. Elements are arranged in accordance of device release date.
     */
    val allFeatures = listOf(

        Features("Pixel 2016", // Pixel XL
            "com.google.android.apps.photos.NEXUS_PRELOAD",
            "com.google.android.apps.photos.nexus_preload",
            "com.google.android.feature.PIXEL_EXPERIENCE",
            "com.google.android.apps.photos.PIXEL_PRELOAD",
            "com.google.android.apps.photos.PIXEL_2016_PRELOAD",
        ),

        Features("Pixel 2017", // Pixel 2
            "com.google.android.feature.PIXEL_2017_EXPERIENCE",
            "com.google.android.apps.photos.PIXEL_2017_PRELOAD"
        ),

        Features("Pixel 2018", // Pixel 3 XL
            "com.google.android.feature.PIXEL_2018_EXPERIENCE",
            "com.google.android.apps.photos.PIXEL_2018_PRELOAD"
        ),

        Features("Pixel 2019 mid-year", // Pixel 3a XL
            "com.google.android.feature.PIXEL_2019_MIDYEAR_EXPERIENCE",
            "com.google.android.apps.photos.PIXEL_2019_MIDYEAR_PRELOAD",
        ),

        Features("Pixel 2019", // Pixel 4 XL
            "com.google.android.feature.PIXEL_2019_EXPERIENCE",
            "com.google.android.apps.photos.PIXEL_2019_PRELOAD",
        ),

        Features("Pixel 2020 mid-year", // Pixel 4a
            "com.google.android.feature.PIXEL_2020_MIDYEAR_EXPERIENCE",
            "com.google.android.apps.photos.PIXEL_2020_MIDYEAR_PRELOAD",
        ),

        Features("Pixel 2020", // Pixel 5
            "com.google.android.feature.PIXEL_2020_EXPERIENCE",
            "com.google.android.apps.photos.PIXEL_2020_PRELOAD",
        ),

        Features("Pixel 2021 mid-year", // Pixel 5a
            "com.google.android.feature.PIXEL_2021_MIDYEAR_EXPERIENCE",
            "com.google.android.apps.photos.PIXEL_2021_MIDYEAR_PRELOAD",
        ),

        Features("Pixel 2021", // Pixel 6 Pro
            "com.google.android.feature.PIXEL_2021_EXPERIENCE",
            "com.google.android.apps.photos.PIXEL_2021_PRELOAD",
        ),
    )

    /**
     * Example if [featureLevel] = "Pixel 2020", return will have
     * list of all elements from [allFeatures] from "Pixel 2016" i.e. index = 0,
     * to "Pixel 2020" i.e index = 6, both inclusive.
     */
    private fun getFeaturesUpTo(featureLevel: String): List<Features> {
        val allFeatureDisplayNames = allFeatures.map { it.displayName }
        val levelIndex = allFeatureDisplayNames.indexOf(featureLevel)
        return if (levelIndex == -1) listOf()
        else {
            allFeatures.withIndex().filter { it.index <= levelIndex }.map { it.value }
        }
    }

    fun getGamePackages(): List<String> {
        return listOf("com.tencent.ig",
            "com.pubg.krmobile",
            "com.vng.pubgmobile",
            "com.rekoo.pubgm",
            "com.pubg.imobile",
            "com.pubg.newstate",
            "com.gameloft.android.ANMP.GloftA9HM",
            "com.activision.callofduty.shooter",
            "com.madfingergames.deadtrigger2",
            "com.riotgames.league.wildrift",
            "com.mobile.legends",
            "com.ngame.allstar.eu",
            "com.pearlabyss.blackdesertm.gl",
            "com.epicgames.fortnite")
    }

    fun getDevices(): List<String> {
        return listOf("OnePlus 7 Pro",
            "OnePlus 8",
            "Sony Xperia 5 II",
            "Xiaomi Mi 11 Ultra",
            "Mi 10 Pro",
            "Samsung Galaxy S9 Plus",
            "Samsung Galaxy S20 Ultra")
    }

    /**
     * Class to contain device names and their respective build properties.
     * @param deviceName Actual device names, example "Pixel 4a".
     * @param props Contains the device properties to spoof.
     * @param featureLevelName Points to the features expected to be spoofed from [allFeatures],
     * from "Pixel 2016" up to this level.
     */
    data class DeviceEntries(
        val deviceName: String,
        val props: HashMap<String, String>,
        val featureLevelName: String,
    )

    /**
     * List of all devices and their build props and their required feature levels.
     */
    val allDevices = listOf(

        DeviceEntries("None", hashMapOf(), "None"),

        DeviceEntries(
            "Pixel XL", hashMapOf(
                Pair("BRAND", "google"),
                Pair("MANUFACTURER", "Google"),
                Pair("DEVICE", "marlin"),
                Pair("PRODUCT", "marlin"),
                Pair("MODEL", "Pixel XL"),
                Pair("FINGERPRINT", "google/marlin/marlin:10/QP1A.191005.007.A3/5972272:user/release-keys"),
            ),
            "Pixel 2016",
        ),

        DeviceEntries(
            "Pixel 2", hashMapOf(
                Pair("BRAND", "google"),
                Pair("MANUFACTURER", "Google"),
                Pair("DEVICE", "walleye"),
                Pair("PRODUCT", "walleye"),
                Pair("MODEL", "Pixel 2"),
                Pair("FINGERPRINT", "google/walleye/walleye:8.1.0/OPM1.171019.021/4565141:user/release-keys"),
            ),
            "Pixel 2017",
        ),

        DeviceEntries(
            "Pixel 3 XL", hashMapOf(
                Pair("BRAND", "google"),
                Pair("MANUFACTURER", "Google"),
                Pair("DEVICE", "crosshatch"),
                Pair("PRODUCT", "crosshatch"),
                Pair("MODEL", "Pixel 3 XL"),
                Pair("FINGERPRINT", "google/crosshatch/crosshatch:11/RQ3A.211001.001/7641976:user/release-keys"),
            ),
            "Pixel 2018",
        ),

        DeviceEntries(
            "Pixel 3a XL", hashMapOf(
                Pair("BRAND", "google"),
                Pair("MANUFACTURER", "Google"),
                Pair("DEVICE", "bonito"),
                Pair("PRODUCT", "bonito"),
                Pair("MODEL", "Pixel 3a XL"),
                Pair("FINGERPRINT", "google/bonito/bonito:11/RQ3A.211001.001/7641976:user/release-keys"),
            ),
            "Pixel 2019 mid-year",
        ),

        DeviceEntries(
            "Pixel 4 XL", hashMapOf(
                Pair("BRAND", "google"),
                Pair("MANUFACTURER", "Google"),
                Pair("DEVICE", "coral"),
                Pair("PRODUCT", "coral"),
                Pair("MODEL", "Pixel 4 XL"),
                Pair("FINGERPRINT", "google/coral/coral:12/SP1A.211105.002/7743617:user/release-keys"),
            ),
            "Pixel 2019",
        ),

        DeviceEntries(
            "Pixel 4a", hashMapOf(
                Pair("BRAND", "google"),
                Pair("MANUFACTURER", "Google"),
                Pair("DEVICE", "sunfish"),
                Pair("PRODUCT", "sunfish"),
                Pair("MODEL", "Pixel 4a"),
                Pair("FINGERPRINT", "google/sunfish/sunfish:11/RQ3A.211001.001/7641976:user/release-keys"),
            ),
            "Pixel 2020 mid-year",
        ),

        DeviceEntries(
            "Pixel 5", hashMapOf(
                Pair("BRAND", "google"),
                Pair("MANUFACTURER", "Google"),
                Pair("DEVICE", "redfin"),
                Pair("PRODUCT", "redfin"),
                Pair("MODEL", "Pixel 5"),
                Pair("FINGERPRINT", "google/redfin/redfin:12/SP1A.211105.003/7757856:user/release-keys"),
            ),
            "Pixel 2020",
        ),

        DeviceEntries(
            "Pixel 5a", hashMapOf(
                Pair("BRAND", "google"),
                Pair("MANUFACTURER", "Google"),
                Pair("DEVICE", "barbet"),
                Pair("PRODUCT", "barbet"),
                Pair("MODEL", "Pixel 5a"),
                Pair("FINGERPRINT", "google/barbet/barbet:11/RD2A.211001.002/7644766:user/release-keys"),
            ),
            "Pixel 2021 mid-year",
        ),

        DeviceEntries(
            "Pixel 6 Pro", hashMapOf(
                Pair("BRAND", "google"),
                Pair("MANUFACTURER", "Google"),
                Pair("DEVICE", "raven"),
                Pair("PRODUCT", "raven"),
                Pair("MODEL", "Pixel 6 Pro"),
                Pair("FINGERPRINT", "google/raven/raven:12/SD1A.210817.036/7805805:user/release-keys"),
            ),
            "Pixel 2021",
        ),
    )

    /**
     * List of all devices which supports 90-120 and their build props.
     */
    val gameDevices = listOf(

        DeviceEntries("None", hashMapOf(), "None"),

        DeviceEntries(
            "OnePlus 7 Pro", hashMapOf(
                Pair("MODEL", "GM1917"),
            ),
            "",
        ),

        DeviceEntries(
            "OnePlus 8", hashMapOf(
                Pair("MODEL", "IN2013"),
            ),
            "",
        ),

        DeviceEntries(
            "Sony Xperia 5 II", hashMapOf(
                Pair("MODEL", "SO-52A"),
            ),
            "",
        ),

        DeviceEntries(
            "Xiaomi Mi 11 Ultra", hashMapOf(
                Pair("MODEL", "M2102K1C"),
            ),
            "",
        ),

        DeviceEntries(
            "Mi 10 Pro", hashMapOf(
                Pair("MODEL", "Mi 10 Pro"),
            ),
            "",
        ),

        DeviceEntries(
            "Samsung Galaxy S9 Plus", hashMapOf(
                Pair("MODEL", "SM-G965F"),
            ),
            "",
        ),

        DeviceEntries(
            "Samsung Galaxy S20 Ultra", hashMapOf(
                Pair("MODEL", "SM-G9880"),
            ),
            "",
        ),
    )

    /**
     * Get instance of [DeviceEntries] from a supplied [deviceName].
     */
    fun getDeviceProps(deviceName: String?) = allDevices.find { it.deviceName == deviceName }

    /**
     * Get instance of [DeviceEntries] from a supplied [deviceName].
     */
    fun getDevicePropsGames(deviceName: String?) = gameDevices.find { it.deviceName == deviceName }

    /**
     * Call [getFeaturesUpTo] using a device name rather than feature level.
     * Used in spinner in main activity.
     */
    fun getFeaturesUpToFromDeviceName(deviceName: String?): Set<String>{
        return getDeviceProps(deviceName)?.let {
            getFeaturesUpTo(it.featureLevelName).map { it.displayName }.toSet()
        }?: setOf()
    }

    /**
     * Default name of device to spoof.
     */
    val defaultDeviceName = "Pixel 5"
    val defaultDeviceNameGame = "None"

    /**
     * Default feature level to spoof up to. Corresponds to what is expected for the device in [defaultDeviceName].
     */
    val defaultFeatures = getFeaturesUpTo("Pixel 2020")

}