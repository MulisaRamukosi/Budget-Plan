package com.puzzle.industries.budgetplan.screens.registrationFlow

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.buttons.AuthButton
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.AuthViewModel

@Composable
fun AuthScreen(authViewModel: AuthViewModel, onAuthed: () -> Unit) {

    AuthResponseHandler(authViewModel = authViewModel, onAuthed = onAuthed)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.padding(all = MaterialTheme.spacing.large)) {

            GuideFlowComponents.InfoSection(
                title = stringResource(id = R.string.sign_in),
                message = stringResource(id = R.string.sign_in_message),
                note = stringResource(id = R.string.continue_without_account_note)
            )

            GoogleLoginButton(onClick = authViewModel.onLoginWithGoogle)
            V_S_Space()
            FaceBookLoginButton(onClick = authViewModel.onLoginWithFacebook)
            V_S_Space()
            EmailPasswordLoginButton(onClick = authViewModel.onLoginWithEmailPassword)

            V_M_Space()
            ContinueWithoutAccountButton(onClick = authViewModel.onLoginWithoutAccount)
        }
    }
}

@Composable
private fun AuthResponseHandler(authViewModel: AuthViewModel, onAuthed: () -> Unit) {
    val authResponse by authViewModel.authStateFlow.collectAsState(initial = null)

    authResponse?.let { response ->
        val exception = response.exception
        if (exception != null){
            val message = exception.message
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
        }
        else{
            onAuthed()
        }
    }
}

@Composable
private fun GoogleLoginButton(onClick: () -> Unit) {

    AuthButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.sign_in_google),
        iconId = R.drawable.ic_gmail,
        iconDesc = stringResource(id = R.string.desc_gmail_icon),
        onClick = onClick
    )
}

@Composable
private fun FaceBookLoginButton(onClick: () -> Unit) {
    AuthButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.sign_in_facebook),
        iconId = R.drawable.ic_facebook,
        iconDesc = stringResource(id = R.string.desc_facebook_icon),
        onClick = onClick
    )
}

@Composable
private fun EmailPasswordLoginButton(onClick: (email: String, password: String) -> Unit) {
    AuthButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.sign_in_with_email_password),
        iconId = R.drawable.ic_mail,
        iconDesc = stringResource(id = R.string.desc_mail_icon)
    ) {

    }
}

@Composable
private fun ContinueWithoutAccountButton(onClick: () -> Unit) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
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

@Composable
@Preview(showBackground = true)
private fun PreviewAuthScreen() {
    BudgetPlanTheme(dynamicColor = false) {
        AuthScreen(hiltViewModel()) {

        }
    }
}