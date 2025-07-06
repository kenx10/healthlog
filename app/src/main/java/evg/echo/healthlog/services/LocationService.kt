package evg.echo.healthlog.services

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class LocationService(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val userService: UserService
) {
    private val dbScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @SuppressLint("MissingPermission")
    fun setLocation(callback: (Location) -> Unit) {
        dbScope.launch {
            if (userService.useLocation()) {
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    callback(it)
                }
            }
        }
    }
}