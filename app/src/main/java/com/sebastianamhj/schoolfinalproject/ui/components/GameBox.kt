package com.sebastianamhj.schoolfinalproject.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sebastianamhj.schoolfinalproject.R

@Composable
fun GameBox(image: Int, game: String) {
    Card(modifier = Modifier.padding(40.dp, 20.dp), shape = RoundedCornerShape(10)) {
        Column() {
            Image(
                painter = painterResource(id = image),
                contentDescription = "Game image"
            )
            Row(modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .padding(10.dp)) {
                Text(text = game, fontSize = 18.sp)
            }
        }

    }
}