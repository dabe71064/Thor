package com.valhalla.thor.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

val apksMetadataGenerator = ApksMetadataGenerator.getInstance()

class ApksMetadataGenerator {

    @Serializable
    data class ApksMetadata(
        @SerialName("info_version") val infoVersion: Int = 1,
        @SerialName("package_name") val packageName: String,
        @SerialName("display_name") val displayName: String,
        @SerialName("version_name") val versionName: String,
        @SerialName("version_code") val versionCode: Int,
        @SerialName("min_sdk") val minSdkVersion: Int,
        @SerialName("target_sdk") val targetSdkVersion: Int,
    )

    companion object {
        var INSTANCE: ApksMetadataGenerator? = null

        fun getInstance() = INSTANCE ?: synchronized(this) {
            val instance = ApksMetadataGenerator()
            INSTANCE = instance
            instance
        }
    }

    fun generateJson(appInfo: AppInfo) = Json.encodeToString(
        ApksMetadata(
            packageName = appInfo.packageName,
            displayName = appInfo.appName ?: "",
            versionName = appInfo.versionName ?: "",
            versionCode = appInfo.versionCode,
            minSdkVersion = appInfo.minSdk,
            targetSdkVersion = appInfo.targetSdk
        )
    )

    fun generateJson(appInfo: AppInfo, targetFile: File){
        targetFile.writeText(generateJson(appInfo))
    }

}