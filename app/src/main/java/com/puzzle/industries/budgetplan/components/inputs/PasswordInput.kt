@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.components.inputs

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.models.PasswordRequirement
import com.puzzle.industries.budgetplan.util.configs.TextFieldsConfig

@Composable
fun PasswordInput(
    modifier: Modifier,
    password: String,
    passwordRequirements: List<PasswordRequirement>,
    maxCharCount: Int = TextFieldsConfig.SINGLE_LINE_TEXT_MAX_CHAR,
    imeAction: ImeAction = ImeAction.Default,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val passwordVisibility: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = password,
        onValueChange = { if (it.length <= maxCharCount) onValueChange(it) },
        singleLine = true,
        label = { Text(text = stringResource(id = R.string.password)) },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        supportingText = {
            val requirementsText = passwordRequirements.map { passwordRequirement ->
                stringResource(id = passwordRequirement.stringId, *passwordRequirement.formatArgs)
            }.joinToString(separator = "\n")
            Text(
                text = requirementsText,
                style = MaterialTheme.typography.labelSmall
            )
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                var passwordToggleIcon = Icons.Rounded.VisibilityOff
                var passwordToggleDesc = stringResource(id = R.string.password_visibility_off)

                if(passwordVisibility.value){
                    passwordToggleIcon = Icons.Rounded.Visibility
                    passwordToggleDesc = stringResource(id = R.string.password_visibility_on)
                }

                Icon(imageVector = passwordToggleIcon, contentDescription = passwordToggleDesc)
            }
        }
    )
}