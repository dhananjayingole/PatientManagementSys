package eu.tutorials.patientmanagsys.BottomScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import eu.tutorials.patientmanagsys.R
import eu.tutorials.patientmanagsys.model.Card
import eu.tutorials.patientmanagsys.navigation.Routes
import eu.tutorials.patientmanagsys.ui.theme.BlueEnd
import eu.tutorials.patientmanagsys.ui.theme.BlueStart
import eu.tutorials.patientmanagsys.ui.theme.GreenEnd
import eu.tutorials.patientmanagsys.ui.theme.GreenStart
import eu.tutorials.patientmanagsys.ui.theme.NewColor
import eu.tutorials.patientmanagsys.ui.theme.NewColor2
import eu.tutorials.patientmanagsys.ui.theme.OrangeEnd
import eu.tutorials.patientmanagsys.ui.theme.OrangeStart
import eu.tutorials.patientmanagsys.ui.theme.PurpleEnd
import eu.tutorials.patientmanagsys.ui.theme.PurpleStart

val cards = listOf(
    Card(
        cardType = "Heart",
        cardName = "Cardiologist",
        doctorName = "Dr.Nandu Jadhav",
        color = getGradient(BlueStart, BlueEnd),
    ),

    Card(
        cardType = "Brain",
        cardName = "Neurologist",
        doctorName = "Dr.Pandu Jadhav",
        color = getGradient(BlueStart, BlueEnd),
    ),

    Card(
        cardType = "Stomach",
        cardName = "Gynecologist",
        doctorName = "Dr.Krushna Nagapure",
        color = getGradient(BlueStart, BlueEnd),
    ),

    Card(
        cardType = "Skin",
        cardName = "Dermatologist",
        doctorName = "Dr.Devang Masram",
        color = getGradient(BlueStart, BlueEnd),
    ),
)
fun getGradient(
    startColor: Color,
    endColor : Color,
) : Brush {
    return Brush.horizontalGradient(
        colors = listOf(startColor,endColor)
    )
}

@Composable
fun CardSection(navController: NavController){
    LazyRow(
        modifier = Modifier.padding(vertical = 16.dp)
    ){
        items(cards.size){index ->
            CardItem(index,navController)
        }
    }
}

@Composable
fun CardItem(index: Int,navController: NavController) {
    val card = cards[index]
    var lastItemPaddingEnd = 0.dp
    if (index == cards.size - 1) {
        lastItemPaddingEnd = 16.dp
    }
    val image = when (card.cardType) {
        "Heart" -> painterResource(id = R.drawable.img_7)
        "Brain" -> painterResource(id = R.drawable.img_6)
        "Stomach" -> painterResource(id = R.drawable.img_8)
        "Skin" -> painterResource(id = R.drawable.img_9)
        else -> painterResource(id = R.drawable.img_6)
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(card.color)
            .width(250.dp)
            .height(160.dp)
            .clickable {
     navController.navigate(Routes.BookingScreen)
            }
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                painter = image,
                contentDescription = card.cardName,
                modifier = Modifier.size(80.dp)
            )

            Text(
                text = card.doctorName,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = card.cardName,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
