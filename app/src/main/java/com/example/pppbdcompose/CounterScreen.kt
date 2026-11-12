package com.example.pppbdcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pppbdcompose.ui.theme.PPPBDComposeTheme

@Composable
fun CounterApp(
    modifier: Modifier = Modifier,
    viewModel: CounterViewModel = viewModel()
) {
    val counter by viewModel.counter
    val nameInput by viewModel.nameInput
    val savedName by viewModel.savedName

    CounterScreen(
        counter = counter,
        nameInput = nameInput,
        savedName = savedName,
        onIncrement = { viewModel.increment() },
        onDecrement = { viewModel.decrement() },
        onReset = { viewModel.reset() },
        onNameChange = { viewModel.onNameChange(it) },
        onSave = { viewModel.saveName() },
        modifier = modifier
    )
}

@Composable
fun CounterScreen(
    counter: Int,
    nameInput: String,
    savedName: String,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onReset: () -> Unit,
    onNameChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Counter App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        CounterDisplay(
            value = counter,
            savedName = savedName,
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CounterButtons(
            onDecrement = onDecrement,
            onIncrement = onIncrement,
            onReset = onReset
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = nameInput,
            onValueChange = onNameChange,
            label = { Text("Nama") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3))
        ) {
            Text("SIMPAN")
        }
    }
}

@Composable
fun CounterDisplay(
    value: Int,
    savedName: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            color = Color(0xFFF5F5F5),
            shape = RoundedCornerShape(16.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Nilai Counter",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value.toString(),
                fontSize = 120.sp,
                fontWeight = FontWeight.Bold
            )
            if (savedName.isNotEmpty()) {
                Text(
                    text = savedName,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun CounterButtons(
    onDecrement: () -> Unit = {},
    onIncrement: () -> Unit = {},
    onReset: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onDecrement,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336))
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_remove),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Text("KURANG")
            }
            Button(
                onClick = onIncrement,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50))
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                Text("TAMBAH")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onReset,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3))
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_refresh),
                contentDescription = null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.width(ButtonDefaults.IconSpacing))
            Text("RESET")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CounterScreenPreview() {
    PPPBDComposeTheme {
        CounterScreen(
            counter = 7,
            nameInput = "Nama Contoh",
            savedName = "Nama Contoh",
            onIncrement = {},
            onDecrement = {},
            onReset = {},
            onNameChange = {},
            onSave = {}
        )
    }
}
