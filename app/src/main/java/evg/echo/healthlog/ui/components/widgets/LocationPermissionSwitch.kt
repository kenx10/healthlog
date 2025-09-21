package evg.echo.healthlog.ui.components.widgets

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import evg.echo.healthlog.R
import evg.echo.healthlog.services.UserService
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LocationPermissionSwitch(
    modifier: Modifier,
    userService: UserService = koinInject<UserService>()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var granted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var allowed by remember {
        mutableStateOf(false)
    }

    val permissionLauncher =
        permissionLauncher(stringResource(R.string.permission_location_decs)) {
            granted = true
        }

    scope.launch {
        allowed = userService.useLocation()
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            modifier = Modifier.width(50.dp),
            checked = granted && allowed,
            onCheckedChange = {
                if (it) {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }

                scope.launch {
                    userService.setUseLocation(it)
                    allowed = it
                }
            }
        )
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.permission_location)
        )
        Spacer(modifier = Modifier.padding(start = 8.dp))
        IconButton(
            modifier = Modifier.width(50.dp),
            onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", "evg.echo.healthlog", null)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            }
        ) {
            Icon(Icons.Filled.Settings, "Settings")
        }
    }
}


@Composable
fun permissionLauncher(
    notGrantedMsg: String,
    grantedCallback: () -> Unit
): ManagedActivityResultLauncher<String, Boolean> {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(stringResource(R.string.permission)) },
            text = { Text(notGrantedMsg) },
            confirmButton = {
                Button(onClick = {
                    showDialog = false

                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", "evg.echo.healthlog", null)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(intent)
                }) {
                    Text(stringResource(R.string.to_settings))
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(stringResource(R.string.close))
                }
            }
        )
    }

    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            showDialog = true
        } else {
            grantedCallback()
        }
    }
}
