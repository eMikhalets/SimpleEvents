package com.emikhalets.simpleevents.core.ui.extentions

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Main Theme Screen",
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4
)
annotation class ScreenPreview

@Preview(
    name = "Main Theme Box",
    showBackground = true,
    showSystemUi = false
)
annotation class BoxPreview
