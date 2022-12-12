@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.screens.registrationFlow

import android.util.Patterns
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.buttons.AuthButton
import com.puzzle.industries.budgetplan.components.inputs.EmailInput
import com.puzzle.industries.budgetplan.components.inputs.PasswordInput
import com.puzzle.industries.budgetplan.components.spacer.H_S_Space
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    heightSizeClass: WindowHeightSizeClass,
    onAuthed: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val emailPasswordSectionDisplayed = remember {
        mutableStateOf(false)
    }

    AuthResponseHandler(
        authViewModel = authViewModel,
        onAuthed = onAuthed,
        snackbarHostState = snackbarHostState
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it), contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.padding(all = MaterialTheme.spacing.large)) {

                GuideFlowComponents.InfoSection(
                    title = stringResource(id = R.string.sign_in),
                    message = stringResource(
                        id = if (!emailPasswordSectionDisplayed.value) R.string.sign_in_message
                        else R.string.sign_in_message_email
                    ),
                    note = stringResource(
                        id = if (!emailPasswordSectionDisplayed.value) R.string.continue_without_account_note
                        else R.string.note_no_email_password_account
                    )
                )

                LoginOptionsSection(
                    authViewModel = authViewModel,
                    heightSizeClass = heightSizeClass,
                    onEmailPasswordSectionToggled = { sectionVisible ->
                        emailPasswordSectionDisplayed.value = sectionVisible
                    }
                )
            }
        }
    }


}

@Composable
private fun LoginOptionsSection(
    authViewModel: AuthViewModel,
    heightSizeClass: WindowHeightSizeClass,
    onEmailPasswordSectionToggled: (Boolean) -> Unit
) {
    val heightIsSmall = heightSizeClass == WindowHeightSizeClass.Compact
    val isAuthenticating by authViewModel.isAuthenticating.collectAsState()
    val displayEmailAndPasswordSection = rememberSaveable {
        mutableStateOf(false)
    }

    BackHandler(enabled = displayEmailAndPasswordSection.value || isAuthenticating) {
        if (isAuthenticating) {
            authViewModel.cancelAuth()
        } else {
            displayEmailAndPasswordSection.value = false
            onEmailPasswordSectionToggled(false)
        }
    }
    val content: @Composable (@Composable () -> Unit) -> Unit = { spacer ->
        GoogleLoginButton(
            heightIsSmall = heightIsSmall,
            onClick = authViewModel.onLoginWithGoogle
        )
        spacer()
        FaceBookLoginButton(
            heightIsSmall = heightIsSmall,
            onClick = authViewModel.onLoginWithFacebook
        )
        spacer()
        EmailPasswordLoginButton(
            heightIsSmall = heightIsSmall,
            onClick = {
                displayEmailAndPasswordSection.value = true
                onEmailPasswordSectionToggled(true)
            }
        )
    }

    if (isAuthenticating) {
        CircularProgressIndicator()
    }

    AnimatedVisibility(visible = !isAuthenticating && !displayEmailAndPasswordSection.value) {
        Column {
            if (heightIsSmall) LandscapeLoginOptions(content = content)
            else PortraitLoginOptions(content = content)

            V_M_Space()
            ContinueWithoutAccountButton(
                heightIsSmall = heightIsSmall,
                onClick = authViewModel.onLoginWithoutAccount
            )
        }
    }

    AnimatedVisibility(visible = !isAuthenticating && displayEmailAndPasswordSection.value) {
        LoginWithEmailAndPasswordSection(authViewModel = authViewModel)
    }
}

@Composable
private fun LoginWithEmailAndPasswordSection(authViewModel: AuthViewModel) {

    val showForgotPasswordDialog = rememberSaveable {
        mutableStateOf(false)
    }
    val email by authViewModel.email.collectAsState()
    val password by authViewModel.password.collectAsState()
    val passwordRequirements by authViewModel.passwordRequirements.collectAsState()
    val requirementsMet by authViewModel.emailPasswordRequirementsMet.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 350.dp)
            .verticalScroll(state = rememberScrollState())
    ) {
        EmailInput(
            modifier = Modifier.fillMaxWidth(),
            email = email,
            imeAction = ImeAction.Next,
            onValueChange = authViewModel.onEmailChange
        )
        V_S_Space()
        PasswordInput(
            modifier = Modifier.fillMaxWidth(),
            password = password,
            passwordRequirements = passwordRequirements,
            onValueChange = authViewModel.onPasswordChange
        )
        V_S_Space()
        TextButton(onClick = { showForgotPasswordDialog.value = true }) {
            Text(text = stringResource(id = R.string.forgot_password))
        }
        V_M_Space()

        Button(enabled = requirementsMet, onClick = authViewModel.onLoginWithEmailPassword) {
            Text(text = stringResource(id = R.string.opt_continue))
        }
    }

    if (showForgotPasswordDialog.value){
        val resetPasswordEmail = rememberSaveable { mutableStateOf("") }
        val emailIsValid = rememberSaveable { mutableStateOf(false) }

        AlertDialog(
            title = { Text(text = stringResource(id = R.string.forgot_password_question)) },
            onDismissRequest = { showForgotPasswordDialog.value = false },
            text = {
                Column {
                    Text(text = stringResource(id = R.string.forgot_password_guide))
                    V_S_Space()
                    EmailInput(
                        modifier = Modifier.fillMaxWidth(),
                        email = resetPasswordEmail.value,
                        onValueChange = {
                            resetPasswordEmail.value = it
                            emailIsValid.value = Patterns.EMAIL_ADDRESS.matcher(it).matches()
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(enabled = emailIsValid.value, onClick = {
                    authViewModel.onForgotPassword(resetPasswordEmail.value)
                    showForgotPasswordDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.send_link))
                }
            },
            dismissButton = {
                TextButton(onClick = { showForgotPasswordDialog.value = false }) {
                    Text(text = stringResource(id = R.string.dismiss))
                }
            }
        )
    }
}

@Composable
private fun PortraitLoginOptions(content: @Composable (spacer: @Composable () -> Unit) -> Unit) {
    Column {
        content {
            V_S_Space()
        }
    }
}

@Composable
private fun LandscapeLoginOptions(content: @Composable (spacer: @Composable () -> Unit) -> Unit) {
    Row {
        content {
            H_S_Space()
        }
    }
}

@Composable
private fun AuthResponseHandler(
    authViewModel: AuthViewModel,
    snackbarHostState: SnackbarHostState,
    onAuthed: () -> Unit
) {
    LaunchedEffect(true) {
        authViewModel.authStateFlow.collectLatest {
            it?.let { response ->
                if (response.success) {
                    if (response.awaitingVerification){
                        snackbarHostState.showSnackbar(message = response.message, duration = SnackbarDuration.Long)
                    }
                    else{
                        onAuthed()
                    }
                } else {
                    snackbarHostState.showSnackbar(message = response.message)
                }
            }
        }
    }
}

@Composable
private fun GoogleLoginButton(heightIsSmall: Boolean, onClick: () -> Unit) {
    AuthButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.sign_in_google),
        iconId = R.drawable.ic_gmail,
        iconDesc = stringResource(id = R.string.desc_gmail_icon),
        heightIsSmall = heightIsSmall,
        onClick = onClick
    )
}

@Composable
private fun FaceBookLoginButton(heightIsSmall: Boolean, onClick: () -> Unit) {
    AuthButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.sign_in_facebook),
        iconId = R.drawable.ic_facebook,
        iconDesc = stringResource(id = R.string.desc_facebook_icon),
        heightIsSmall = heightIsSmall,
        onClick = onClick
    )
}

@Composable
private fun EmailPasswordLoginButton(
    heightIsSmall: Boolean,
    onClick: () -> Unit
) {
    AuthButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.sign_in_with_email_password),
        iconId = R.drawable.ic_mail,
        iconDesc = stringResource(id = R.string.desc_mail_icon),
        heightIsSmall = heightIsSmall,
        onClick = onClick
    )
}

@Composable
private fun ContinueWithoutAccountButton(heightIsSmall: Boolean, onClick: () -> Unit) {
    TextButton(
        modifier = if (heightIsSmall) Modifier else Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.continue_without_account))
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = stringResource(id = R.string.desc_arrow_right_icon)
            )
        }
    }
}