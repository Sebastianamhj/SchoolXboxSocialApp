package com.sebastianamhj.schoolfinalproject.ui.main

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.transform.CircleCropTransformation
import com.sebastianamhj.schoolfinalproject.R
import com.sebastianamhj.schoolfinalproject.data.entities.UserModel
import com.sebastianamhj.schoolfinalproject.data.repository.Resource
import com.sebastianamhj.schoolfinalproject.ui.components.UserItem
import com.sebastianamhj.schoolfinalproject.ui.viewmodels.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MainView(navController: NavController) {
    val mainViewModel = getViewModel<MainViewModel>()
    var users by remember { mutableStateOf(listOfNotNull<UserModel>()) }
    var mainUser by remember { mutableStateOf(UserModel()) }
    mainViewModel.mainFirebaseUser.observe(LocalLifecycleOwner.current) {
        when (it) {
            is Resource.Error -> {

            }
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                mainUser = it.data
            }
        }
    }
    mainViewModel.firebaseUsers.observe(LocalLifecycleOwner.current) {
        when (it) {
            is Resource.Error -> {

            }
            is Resource.Loading -> {

            }
            is Resource.Success -> {
                users = it.data
            }
        }
    }

    Column {
        //SearchFriendBar()
        MainUserBar(mainUser = mainUser)
        DisplayUserList(userList = users, navController = navController)
    }
}

@Composable
fun MainUserBar(mainUser: UserModel) {
    val svgLink = "https://avatars.dicebear.com/api/avataaars/:${mainUser.name}.svg"
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .componentRegistry {
            add(SvgDecoder(LocalContext.current))
        }
        .build()
    Column() {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row() {
                CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                    Image(
                        painter = rememberImagePainter(
                            data = svgLink,
                            builder = { transformations(CircleCropTransformation()) }),
                        contentDescription = "User Image",
                        modifier = Modifier.size(75.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .requiredHeight(75.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = mainUser.name, fontSize = 22.sp)
                }
            }
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_settings_24),
                contentDescription = "Settings Icon",
                modifier = Modifier.padding(end = 10.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(2.dp)
                .background(Color(0xFF444444))
        ) {

        }
    }
}

@Composable
fun SearchFriendBar() {
    var searchQuery by remember { mutableStateOf("") }
    Row(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 10.dp)) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                disabledTextColor = Color.Transparent,
                backgroundColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            maxLines = 1,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = "Search Icon"
                )
            },
            shape = CircleShape,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(0.dp)
        )
    }
}

@Composable
fun DisplayUserList(userList: List<UserModel>, navController: NavController) {
    LazyColumn {
        items(items = userList) { item ->
            UserItem(
                name = item.name,
                lastOnline = item.lastOnline,
                status = item.status,
                onClick = {
                    Log.d(TAG, "DisplayUserList: User Clicked!")
                    navController.navigate("UserDetailView/${item.id}")
                }
            )
        }
    }
}