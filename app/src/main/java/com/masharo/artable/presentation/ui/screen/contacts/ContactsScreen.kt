package com.masharo.artable.presentation.ui.screen.contacts

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masharo.artable.R
import com.masharo.artable.presentation.ui.screen.core.ARTableButtonCard
import com.masharo.artable.presentation.ui.theme.ARTableTheme
import com.masharo.artable.presentation.ui.theme.ARTableThemeState

@Composable
fun ContactsScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isButtonBack: Boolean = false
) {
    BusinessCardBody(
        modifier = modifier,
        name = stringResource(R.string.name),
        profession = stringResource(R.string.profesion),
        onClick = onClick,
        isButtonBack = isButtonBack
    )
}

@Composable
fun BusinessCardBody(
    modifier: Modifier = Modifier,
    name: String,
    profession: String,
    onClick: () -> Unit,
    isButtonBack: Boolean
) {
    Column(
        modifier = modifier
            .background(
                color = ARTableThemeState.colors.background
            )
            .fillMaxHeight()
            .verticalScroll(
                state = rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        Person(
            name = name,
            profession = profession
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        ARTableButtonCard(
            onClick = onClick,
            text =  if (isButtonBack) stringResource(R.string.contacts_button_back)
            else stringResource(R.string.button_start)
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        MyContacts()
    }
}

@Composable
private fun MyContacts(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                bottom = 30.dp
            ),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactHabit(
            image = R.drawable.ic_baseline_phone_24,
            data = R.string.phone_number
        )
        ContactHabit(
            image = R.drawable.ic_baseline_tg_24,
            data = R.string.tg
        )
        ContactHabit(
            image = R.drawable.ic_baseline_mail_24,
            data = R.string.mail
        )
        ContactHabit(
            image = R.drawable.ic_baseline_share_24,
            data = R.string.site
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    color = ARTableThemeState.colors.thirdBackgroundColor
                )
        )
        Text(
            modifier = Modifier
                .padding(
                    vertical = 20.dp
                ),
            text = stringResource(R.string.copyright),
            color = ARTableThemeState.colors.onBackgroundColor,
            style = ARTableThemeState.typography.middleText
        )
    }
}

@Composable
fun Person(
    name: String,
    profession: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.80f)
                .padding(0.dp),
            painter = painterResource(
                id = R.drawable.logo
            ),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(
                    bottom = 9.dp
                ),
            text = name,
            style = ARTableThemeState.typography.bigText,
            fontSize = 45.sp,
            color = ARTableThemeState.colors.onBackgroundColor,
            fontWeight = FontWeight.W400
        )
        Text(
            text = profession,
            color = ARTableThemeState.colors.thirdBackgroundColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ContactHabit(
    @DrawableRes image: Int,
    @StringRes data: Int
) {
    Column {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    color = ARTableThemeState.colors.thirdBackgroundColor
                )
        )

        Row(
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 60.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null
            )
            Spacer(
                modifier = Modifier
                    .width(18.dp)
            )
            Text(
                text = stringResource(data),
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactsScreenPreview() {
    ARTableTheme {
        ContactsScreen(
            onClick = {}
        )
    }
}