package com.sebastianamhj.schoolfinalproject.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.transform.CircleCropTransformation
import com.sebastianamhj.schoolfinalproject.R
import com.sebastianamhj.schoolfinalproject.data.entities.Game
import com.sebastianamhj.schoolfinalproject.data.entities.UserModel
import com.sebastianamhj.schoolfinalproject.data.repository.Resource
import com.sebastianamhj.schoolfinalproject.ui.components.CustomButton
import com.sebastianamhj.schoolfinalproject.ui.components.GameBox
import com.sebastianamhj.schoolfinalproject.ui.components.UserItem
import com.sebastianamhj.schoolfinalproject.ui.viewmodels.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun UserDeatilView(navController: NavController, userId: String?) {
    val mainViewModel = getViewModel<MainViewModel>()
    var detailUser by remember { mutableStateOf(UserModel()) }
    val lifeCycle = LocalLifecycleOwner.current
    if (userId != null) {
        mainViewModel.getDetailFirebaseUser(userId = userId).observe(LocalLifecycleOwner.current) {
            when (it) {
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    detailUser = it.data
                }
            }
        }
    }
    Column {
        if (userId != null) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = "Back Arrow",
                modifier = Modifier
                    .padding(top = 15.dp, start = 15.dp)
                    .clickable { navController.popBackStack() }
            )
            UserDetails(
                user = detailUser,
                navController = navController,
                mainViewModel = mainViewModel,
                lifeCycle = lifeCycle
            ) {
                Log.d(TAG, "UserDeatilView: Username Clicked")
            }
            UserLastPlayed()
        }
    }
}

@Composable
fun UserLastPlayed() {
    val games = listOf<Game>(
        Game(image = R.drawable.halo_infinite, "Halo Infinite"),
        Game(image = R.drawable.hades, "Hades"),
        Game(image = R.drawable.age_of_empires, "Age of Empires IV")
    )
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Last Played", fontSize = 22.sp, modifier = Modifier.padding(bottom = 5.dp))
        LazyColumn {
            items(items = games) { item ->
                GameBox(image = item.image, game = item.name)
            }
        }
    }

}

@Composable
fun UserDetails(
    user: UserModel,
    mainViewModel: MainViewModel,
    navController: NavController,
    lifeCycle: LifecycleOwner,
    onClick: () -> Unit
) {
    val svgLink = "https://avatars.dicebear.com/api/avataaars/:${user.name}.svg"
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .componentRegistry {
            add(SvgDecoder(LocalContext.current))
        }
        .build()
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        CompositionLocalProvider(LocalImageLoader provides imageLoader) {
            Image(
                painter = rememberImagePainter(
                    data = svgLink,
                    builder = { transformations(CircleCropTransformation()) }),
                contentDescription = "User Image",
                modifier = Modifier.size(125.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onClick.invoke() }) {
            Text(text = user.name, fontSize = 32.sp)
            if (user.status) {
                Box(
                    modifier = Modifier
                        .padding(top = 5.dp, start = 10.dp)
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF7AE015))

                )
            }
        }
        Row() {
            CustomButton(text = "Message") {

            }
            CustomButton(text = "Unfriend") {
                mainViewModel.removeFirebaseUser(user.id).observe(lifeCycle) {
                    when (it) {
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Success -> {
                            navController.navigate("MainView") {
                                popUpTo("MainView") {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}