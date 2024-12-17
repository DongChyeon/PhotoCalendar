package com.dongchyeon.calendar.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest

@Composable
internal fun DayBackgroundImage(
    imageUrl: String,
    imageShape: Shape = RoundedCornerShape(8.dp)
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.clip(
            shape = imageShape,
        )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context).data(imageUrl).build(),
            contentDescription = "image",
            onState = { state ->
                if (state is AsyncImagePainter.State.Success) {
                    Log.d("HorizontalCalendar", "Success")
                } else if (state is AsyncImagePainter.State.Error) {
                    Log.d("HorizontalCalendar", "Error :${state.result}")
                } else {
                    Log.d("HorizontalCalendar", "Loading")
                }
            },
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White.copy(0.3f)
                )
        )
    }
}