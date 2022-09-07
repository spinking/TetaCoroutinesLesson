package studio.eyesthetics.tetaarchitecturelesson.ui.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import studio.eyesthetics.tetaarchitecturelesson.data.database.entities.NewsEntity

@Composable
fun NewsItem(
    item: NewsEntity
) {
    ConstraintLayout(Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp, horizontal = 20.dp)
    ) {
        val (image, background, title, description) = createRefs()
        val guideline = createGuidelineFromTop(0.6f)
        
        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(300.dp).constrainAs(image) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.6f))
            .constrainAs(background) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(guideline)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )

        Text(
            text = item.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(guideline, margin = 8.dp)
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = item.description,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.constrainAs(description) {
                start.linkTo(parent.start, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(title.bottom, margin = 8.dp)
                width = Dimension.fillToConstraints
            },
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}