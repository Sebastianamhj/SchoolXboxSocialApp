package com.sebastianamhj.schoolfinalproject.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.transform.CircleCropTransformation

@Composable
fun UserItem(name: String, lastOnline: String, status: Boolean, onClick: () -> Unit) {

    val svgLink = "https://avatars.dicebear.com/api/avataaars/:$name.svg"

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .componentRegistry {
            add(SvgDecoder(LocalContext.current))
        }
        .build()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp)
        ) {
            CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                Image(
                    painter = rememberImagePainter(
                        data = svgLink,
                        builder = { transformations(CircleCropTransformation()) }),
                    contentDescription = "User Image",
                    modifier = Modifier.size(50.dp)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(text = name, fontSize = 18.sp, modifier = Modifier.padding(bottom = 2.dp))
                if (status) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF7AE015))
                    )
                } else {
                    Text(
                        text = "Last seen online ${getLastOnline(lastOnline = lastOnline.toInt())} ago",
                        color = Color(
                            0xFF585858
                        ),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

fun getLastOnline(lastOnline: Int): String {
    if (lastOnline >= 24) {
        return "${lastOnline / 24}d"
    }
    return "${lastOnline}h"
}