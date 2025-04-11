package com.example.calculadoraohms.items

import androidx.compose.foundation.background
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
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Calculadora de Resistencia", style = MaterialTheme.typography.titleLarge)

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                DropdownSelectorSimple("Banda 1", colores, banda1) { banda1 = it }
                DropdownSelectorSimple("Banda 2", colores, banda2) { banda2 = it }
                DropdownSelectorSimple("Multiplicador", multiplicadores, banda3) { banda3 = it }
                DropdownSelectorSimple("Tolerancia", tolerancias, tolerancia) { tolerancia = it }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Resultado:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = resultado,
                    style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
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

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(label, style = MaterialTheme.typography.labelLarge)
        Box(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                .clickable { expandido = true }
                .padding(12.dp)
        ) {
            Text(seleccionado)
        }
        DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
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
    }
}

fun calcularResistenciaSimple(b1: String, b2: String, b3: String, tol: String): String {
    val valores = mapOf(
        "Negro" to 0, "Marrón" to 1, "Rojo" to 2, "Naranja" to 3, "Amarillo" to 4,
        "Verde" to 5, "Azul" to 6, "Violeta" to 7, "Gris" to 8, "Blanco" to 9
    )

    val multiplicador = mapOf(
        "Amarillo" to 10000.0,
        "Verde" to 100000.0,
        "Azul" to 1000000.0,
        "Violeta" to 10000000.0,
        "Gris" to 100000000.0,
        "Blanco" to 1000000000.0
    )

    val toleranciaTexto = mapOf(
        "Dorado" to "±5%",
        "Plateado" to "±10%",
        "Ninguno" to "±20%"
    )

    val num1 = valores[b1] ?: 0
    val num2 = valores[b2] ?: 0
    val multi = multiplicador[b3] ?: 1.0
    val resistencia = ((num1 * 10) + num2) * multi



    return "${toleranciaTexto[tol]}"
}
