package com.example.composenews

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composenews.ui.news.NEWS_ROUTE
import com.example.composenews.ui.news.NewsFragment
import com.example.composenews.ui.theme.ComposeNewsTheme
import com.example.composenews.widgets.CATEGORIES_ROUTE
import com.example.composenews.widgets.CategoriesContent
import com.example.composenews.widgets.DrawerBody
import com.example.composenews.widgets.DrawerHeader
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposeNewsTheme {

                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(drawerContent = {
                    Column(modifier = Modifier.fillMaxSize()) {
                        DrawerHeader()
                        DrawerBody(navController) {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    }

                }, drawerState = drawerState) {
                    Scaffold(
                        topBar = { NewsAppBar(drawerState) }
                    ) {
//
                        NavHost(
                            navController = navController,
                            startDestination = CATEGORIES_ROUTE,
                            modifier = Modifier.padding(top = it.calculateTopPadding())
                        ) {
                            composable(route = CATEGORIES_ROUTE) {
                                CategoriesContent(navController)
                            }
                            composable(
                                route = "$NEWS_ROUTE/{category}",
                                arguments = listOf(navArgument(name = "category") {
                                    type = NavType.StringType
                                })
                            ) {
                                val argument = it.arguments?.getString("category")
                                NewsFragment(argument)

                            }
                        }

                    }
                }

            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NewsAppBar(drawerState: DrawerState) {
        val scope = rememberCoroutineScope()
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.news),
                    style = TextStyle(color = Color.White, fontSize = 22.sp)
                )
            },
            modifier = Modifier.clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 26.dp,
                    bottomEnd = 26.dp
                )
            ),
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = colorResource(id = R.color.colorGreen),
                navigationIconContentColor = Color.White
            ),
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = "Icon Navigation"
                    )

                }
            }
        )
    }

// Recomposition

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun Preview() {
        ComposeNewsTheme {
            val drawerState = DrawerState(DrawerValue.Closed)
            Scaffold(
                topBar = { NewsAppBar(drawerState = drawerState) }
            ) {


            }


        }
    }
}


