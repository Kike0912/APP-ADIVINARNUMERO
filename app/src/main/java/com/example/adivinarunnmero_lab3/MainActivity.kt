package com.example.adivinarunnmero_lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adivinarunnmero_lab3.ui.theme.AdivinarUnNúmeroLab3Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdivinarUnNúmeroLab3Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GuessNumberGame()
                }
            }
        }
    }
}

@Composable
fun GuessNumberGame() {
    var numberToGuess by remember { mutableStateOf(Random.nextInt(0, 101)) }
    var userGuess by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var attemptsLeft by remember { mutableStateOf(3) }
    var gameOver by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF89CFF0), Color(0xFFE0F7FA))
                )
            )
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .heightIn(min = 600.dp)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "🎯 Adivina el Número",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF01579B),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Tienes $attemptsLeft intentos",
                    fontSize = 18.sp,
                    color = Color(0xFF0277BD)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = userGuess,
                    onValueChange = { userGuess = it },
                    label = { Text("Ingresa un número del 0 al 100") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                Button(
                    onClick = {
                        if (!gameOver) {
                            val guess = userGuess.toIntOrNull()
                            if (guess != null) {
                                if (guess < 0 || guess > 100) {
                                    message = "⚠️ El número debe estar entre 0 y 100."
                                } else if (guess == numberToGuess) {
                                    message = "🎉 ¡Correcto! El número era $numberToGuess"
                                    gameOver = true
                                } else {
                                    attemptsLeft--
                                    if (attemptsLeft == 0) {
                                        message = "😢 ¡Perdiste! El número era $numberToGuess"
                                        gameOver = true
                                    } else {
                                        message = if (guess < numberToGuess)
                                            "🔻 Demasiado bajo. Intentos restantes: $attemptsLeft"
                                        else
                                            "🔺 Demasiado alto. Intentos restantes: $attemptsLeft"
                                    }
                                }
                            } else {
                                message = "⚠️ Ingresa un número válido."
                            }

                            userGuess = ""
                        }
                    },
                    enabled = !gameOver,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0288D1),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(0.5f)
                        .height(50.dp)
                ) {
                    Text("Adivinar", fontSize = 18.sp)
                }

                AnimatedVisibility(visible = message.isNotEmpty(), enter = fadeIn(), exit = fadeOut()) {
                    Text(
                        text = message,
                        fontSize = 18.sp,
                        color = Color(0xFF004D40),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }

                AnimatedVisibility(visible = gameOver, enter = fadeIn()) {
                    Button(
                        onClick = {
                            numberToGuess = Random.nextInt(0, 101)
                            attemptsLeft = 3
                            gameOver = false
                            message = ""
                            userGuess = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF43A047),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth(0.5f)
                            .height(50.dp)
                    ) {
                        Text("Jugar de nuevo", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
