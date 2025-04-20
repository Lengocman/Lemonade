package com.example.lemonade

import androidx.compose.ui.Alignment
import androidx.compose.foundation.BorderStroke
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Composable
fun LemonadeApp() {
    var currentStep by remember { mutableIntStateOf(1) }
    var squeezeCount by remember { mutableIntStateOf(0) }
    var squeezeRequired by remember { mutableIntStateOf(Random.nextInt(2, 5)) }


    val imageRes = when (currentStep) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        4 -> R.drawable.lemon_restart
        else -> R.drawable.lemon_tree
    }

    val textRes = when (currentStep) {
        1 -> stringResource(R.string.tap_lemon_tree)
        2 -> stringResource(R.string.tap_lemon)
        3 -> stringResource(R.string.tap_lemonade)
        4 -> stringResource(R.string.tap_empty_glass)
        else -> stringResource(R.string.tap_lemon_tree)
    }

    val imageDescription = when (currentStep) {
        1 -> stringResource(R.string.lemon_tree_description)
        2 -> stringResource(R.string.lemon_description)
        3 -> stringResource(R.string.lemonade_description)
        4 -> stringResource(R.string.empty_glass_description)
        else -> stringResource(R.string.lemon_tree_description)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LemonTextAndImage(
            text = textRes,
            imageRes = imageRes,
            imageDescription = imageDescription,
            onImageClick = {
                when (currentStep) {
                    1 -> {
                        currentStep = 2
                        squeezeRequired = Random.nextInt(2, 5)
                        squeezeCount = 0
                    }
                    2 -> {
                        squeezeCount++
                        if (squeezeCount >= squeezeRequired) {
                            currentStep = 3
                        }
                    }
                    3 -> currentStep = 4
                    4 -> currentStep = 1
                }
            }
        )
    }
}

@Composable
fun LemonTextAndImage(
    text: String,
    imageRes: Int,
    imageDescription: String,
    onImageClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = imageDescription,
            modifier = Modifier
                .size(200.dp)
                .border(BorderStroke(2.dp, Color(105, 205, 216)), shape = RoundedCornerShape(4.dp))
                .clickable { onImageClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadePreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}
