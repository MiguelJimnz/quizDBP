package com.example.calculadoraohms.items

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CalculadoraOHM() {
    val colores = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
    val multiplicadores = listOf("Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
    val tolerancias = listOf("Dorado", "Plateado", "Ninguno")

    var banda1 by remember { mutableStateOf(colores[0]) }
    var banda2 by remember { mutableStateOf(colores[0]) }
    var banda3 by remember { mutableStateOf(multiplicadores[0]) }
    var tolerancia by remember { mutableStateOf(tolerancias[0]) }

    val resultado = calcularResistenciaSimple(banda1, banda2, banda3, tolerancia)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        contentAlignment = Alignment.TopCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEADCA5)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)


        ) {
            Column(modifier = Modifier.padding(16.dp) ) {
                Text(
                    "Calculadora de Código de Colores",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF1565C0),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Divider(color = Color(0xFF032617), thickness = 1.dp)

                DropdownSelectorSimple("Banda 1", colores, banda1) { banda1 = it }
                DropdownSelectorSimple("Banda 2", colores, banda2) { banda2 = it }
                DropdownSelectorSimple("Multiplicador", multiplicadores, banda3) { banda3 = it }
                DropdownSelectorSimple("Tolerancia", tolerancias, tolerancia) { tolerancia = it }

                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BandaColor(banda1)
                    BandaColor(banda2)
                    BandaColor(banda3)
                    BandaColor(tolerancia)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Resultado ->")
                Text(

                    text = resultado,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF22577A)
                    )
                )

            }
        }
    }
}

@Composable
fun DropdownSelectorSimple(
    label: String,
    opciones: List<String>,
    seleccionado: String,
    onSeleccionado: (String) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(vertical = 8.dp )) {
        Text(label, style = MaterialTheme.typography.labelLarge)

        Box {
            OutlinedTextField(
                value = seleccionado,
                onValueChange = {},
                readOnly = true,
                label = { Text(label) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Desplegar"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandido = true },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF57CC99),
                    unfocusedBorderColor = Color(0xFFB5EAEA),
                    cursorColor = Color(0xFF38A3A5),
                    focusedLabelColor = Color(0xFF22577A)
                )
            )

            DropdownMenu(
                expanded = expandido,
                onDismissRequest = { expandido = false },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            onSeleccionado(opcion)
                            expandido = false
                        }
                    )

                }

            }
            Box(modifier = Modifier
                .matchParentSize()
                .clickable { expandido = true }
            )
        }
    }
}




@Composable
fun BandaColor(color: String) {
    val colorMap = mapOf(
        "Negro" to Color.Black,
        "Marrón" to Color(0xFF8B4513),
        "Rojo" to Color.Red,
        "Naranja" to Color(0xFFFFA500),
        "Amarillo" to Color.Yellow,
        "Verde" to Color.Green,
        "Azul" to Color.Blue,
        "Violeta" to Color(0xFF8A2BE2),
        "Gris" to Color.Gray,
        "Blanco" to Color.White,
        "Dorado" to Color(0xFFFFD700),
        "Plateado" to Color.LightGray,
        "Ninguno" to Color.Transparent
    )

    Box(
        modifier = Modifier
            .size(width = 24.dp, height = 40.dp)
            .background(colorMap[color] ?: Color.Transparent, shape = RoundedCornerShape(4.dp))
            .border(1.dp, Color.Black, shape = RoundedCornerShape(4.dp))
    )
}

fun calcularResistenciaSimple(b1: String, b2: String, b3: String, tol: String): String {
    val valores = mapOf(
        "Negro" to 0, "Marrón" to 1, "Rojo" to 2, "Naranja" to 3, "Amarillo" to 4,
        "Verde" to 5, "Azul" to 6, "Violeta" to 7, "Gris" to 8, "Blanco" to 9
    )

    val multiplicador = mapOf(
        "Amarillo" to 1e4, "Verde" to 1e5, "Azul" to 1e6,
        "Violeta" to 1e7, "Gris" to 1e8, "Blanco" to 1e9
    )

    val toleranciaTexto = mapOf(
        "Dorado" to "±5%", "Plateado" to "±10%", "Ninguno" to "±20%"
    )

    val num1 = valores[b1] ?: 0
    val num2 = valores[b2] ?: 0
    val multi = multiplicador[b3] ?: 1.0
    val resistencia = ((num1 * 10) + num2) * multi




    return " ${resistencia} ${toleranciaTexto[tol]}"
}

