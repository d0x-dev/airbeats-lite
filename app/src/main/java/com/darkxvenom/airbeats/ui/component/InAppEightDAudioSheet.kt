package com.darkxvenom.airbeats.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.darkxvenom.airbeats.LocalPlayerConnection
import com.darkxvenom.airbeats.R
import com.darkxvenom.airbeats.constants.EightDAudioEnabledKey
import com.darkxvenom.airbeats.constants.EightDAudioLevelKey
import com.darkxvenom.airbeats.utils.rememberPreference

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InAppEightDAudioSheet(
    onDismiss: () -> Unit,
) {
    val playerConnection = LocalPlayerConnection.current

    var eightDEnabled by rememberPreference(EightDAudioEnabledKey, defaultValue = false)
    var eightDLevel by rememberPreference(EightDAudioLevelKey, defaultValue = 8)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.volume_up),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.eight_d_audio),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.eight_d_audio_desc),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = eightDEnabled,
                    onCheckedChange = { isChecked ->
                        eightDEnabled = isChecked
                        playerConnection?.service?.setEightDAudioEnabled(isChecked)
                    }
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "${stringResource(R.string.eight_d_audio_level)}: ${eightDLevel}D",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Slider(
                value = eightDLevel.toFloat(),
                onValueChange = { value ->
                    val lvl = value.toInt().coerceIn(1, 16)
                    eightDLevel = lvl
                    playerConnection?.service?.setEightDAudioLevel(lvl)
                },
                valueRange = 1f..16f,
                steps = 14,
                enabled = eightDEnabled,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
