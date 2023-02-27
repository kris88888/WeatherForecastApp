package com.examples.weatherforecast.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object AppTheme {
    val typography: AppTypography = AppTypography
    val colors: AppColors = AppColors
    val dimens: Dimens = Dimens
}

object AppTypography {
 val caption: TextStyle = TextStyle(
     fontSize = 13.sp,
     fontWeight = FontWeight.Bold
 )
    val h4:TextStyle = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 48.sp,
        letterSpacing = 0.sp
    )
    val subtitle1: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    )
}

object AppColors {
    val menuItemColor: Color = Color.Black
    val DarkGrey: Color = Color.DarkGray
    val Blue: Color = Color.Blue.copy(alpha = 0.7f)
    val greyBackground: Color = Color(0xFFEEF1EF)
    val yellow = Color(0xFFFFC107)
}

object Dimens {
    val dimen16 = 16.dp
    val dimen2 = 2.dp
    val dimen24 = 24.dp
    val dimen4 = 4.dp
    val dimen12 = 12.dp
    val dimen80 = 80.dp
    val dimen6 = 6.dp
    val dimen48 = 48.dp
    val dimen15 = 15.dp
    val dimen30 = 30.dp
    val dimen35 = 35.dp
}