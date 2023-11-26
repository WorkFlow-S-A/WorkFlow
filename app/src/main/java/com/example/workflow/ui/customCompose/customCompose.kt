package com.example.workflow.ui.customCompose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.workflow.ui.theme.GreenWorkFlow
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTextField(text: String, userText:String,onTextValueChange: (String) -> Unit){

    OutlinedTextField(
        value = userText,
        onValueChange = {onTextValueChange(it)},
        label = { Text(text = text) },
        maxLines = 1,
        modifier = Modifier.fillMaxWidth())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(text: String, passwordValue:String,onPasswordValueChange: (String) -> Unit){

    OutlinedTextField(
        value = passwordValue,
        onValueChange = {onPasswordValueChange(it)},
        label = { Text(text = text)},
        visualTransformation = PasswordVisualTransformation(),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(text: String, emailValue:String,onEmailValueChange: (String) -> Unit){
    OutlinedTextField(
        value = emailValue,
        onValueChange = {onEmailValueChange(it)},
        label = { Text(text = text)},
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CustomClickableText(text:String,modifier: Modifier,onClick: () -> Unit){
    ClickableText(
        text = AnnotatedString(text,
            spanStyle = SpanStyle(
                fontSize = 15.sp,
                textDecoration = TextDecoration.Underline
            ),
        ),
        modifier = modifier,
        onClick = {onClick()}
    )
}

@Composable
fun FilledButton(onClick: () -> Unit, text: String, modifier: Modifier){
    Button(onClick = { onClick() }, modifier = modifier, colors = ButtonDefaults.buttonColors(containerColor = GreenWorkFlow, contentColor = Color.Black), shape = ShapeDefaults.Small) {
        Text(text = text )
    }
}

