package com.sebastianamhj.schoolfinalproject.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(10.dp, 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { onClick.invoke() }, colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(
                    0xFF363636
                )
            )
        ) {
            Text(text = text)
        }
    }
}