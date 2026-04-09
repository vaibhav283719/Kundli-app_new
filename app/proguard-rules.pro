# Add project specific ProGuard rules here.

# Keep Room entities
-keep class com.kundliapp.data.database.entities.** { *; }

# Keep AdMob classes
-keep class com.google.android.gms.ads.** { *; }

# Keep domain models
-keep class com.kundliapp.domain.models.** { *; }

# Kotlin
-keep class kotlin.** { *; }
-keepclassmembers class **$WhenMappings { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Room
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**
