package com.example.movieapp.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.movieapp.model.Movie
import com.example.movieapp.model.getMovies

@Preview
@Composable
fun MovieRow(movie: Movie = getMovies()[0], onItemClickListener: (String) -> Unit = {}){

var expanded by remember {
    mutableStateOf(false)
}

    Card(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        //.height(130.dp)
        .shadow(6.dp, RoundedCornerShape(16.dp))
        .clickable() {
            onItemClickListener(movie.id)
        },
        shape = RoundedCornerShape(16.dp)
    )
    {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .size(120.dp),
                shape = CircleShape,
                shadowElevation = 5.dp
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = movie.images[1], // to call images from the API using coil
                        builder = {  // for image design
                            crossfade(true)
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop, // Ensures the image fills the Surface
                    modifier = Modifier.fillMaxSize() // Ensures the Image fills the Surface
                )

//                Icon(imageVector = Icons.Default.AccountBox,
//                    contentDescription = "Movie Image")
            }

            Column(modifier = Modifier.padding(4.dp)) {
                Text(text = movie.title,
                    style = MaterialTheme.typography.headlineMedium)
                Text(text = "Director: ${movie.director}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(2.dp))
                Text(text = "genre: ${movie.genre}",
                    style = MaterialTheme.typography.bodyMedium)

                Icon(imageVector =
                if (expanded){
                    Divider(modifier = Modifier.padding(2.dp))
                    Icons.Filled.KeyboardArrowUp
                } //Change arrow direction
                else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Down Arrow",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            expanded = !expanded
                        },
                    tint = Color.DarkGray)

                     AnimatedVisibility(visible = expanded) { //expandable card fun.
                                Column {
                                    Text(buildAnnotatedString { // to make changes for individual letter or words
                                        withStyle(style = SpanStyle(color = Color.Black, fontSize = 13.sp)) {
                                            append("Plot: ")
                                        }
                                        withStyle(style = SpanStyle(color = Color.DarkGray,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Light)) {
                                            append(movie.plot)
                                        }
                                    }, modifier = Modifier.padding(6.dp))
                                    Divider(modifier = Modifier.padding(3.dp))
                                    Text(text = "Actors: ${movie.actors}", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(2.dp))

                                    Text(text = "Rating: ${movie.rating}", style = MaterialTheme.typography.bodyMedium)
                                }
                            }//Animated end
                } //column end
            }
        }
    }//fun end