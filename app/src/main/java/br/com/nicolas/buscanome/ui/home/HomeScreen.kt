package br.com.nicolas.buscanome.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.nicolas.buscanome.R
import br.com.nicolas.buscanome.data.mapper.Name
import br.com.nicolas.buscanome.data.mapper.PopularName
import br.com.nicolas.buscanome.ui.theme.Background
import br.com.nicolas.buscanome.utils.DataEvent
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsState()
    var name by remember {
        mutableStateOf("")
    }
    //Scaffold(bottomBar = { AdvertView() }) {
    Box {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(155.dp)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 30.dp, bottomStart = 30.dp
                    )
                ),
            painter = painterResource(id = R.drawable.circle_background),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = modifier.padding(
                start = 15.dp,
                end = 15.dp,
                top = 10.dp
            )
        ) {
            SearchName(onValueChange = {
                name = it
                if (name.length >= 4) viewModel.onEvent(DataEvent.Search(name)) else viewModel.onEvent(
                    DataEvent.Popular
                )
            })
            Spacer(modifier = Modifier.size(16.dp))
            if (state.loading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
            if (!state.error.equals("")) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.error.toString(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            AnimatedVisibility(visible = state.showPopular) {
                Text(
                    text = "Nomes Populares",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            AnimatedVisibility(visible = state.showNames, /* modifier = Modifier.padding(it) */) {
                state.successNames?.let { NamesContent(state = it) }
            }
            AnimatedVisibility(visible = state.showPopular, /* modifier = Modifier.padding(it) */) {
                PopularContent(state = state.successPopular)
            }
        }
    }
    // }
}

@SuppressLint("VisibleForTests")
@Composable
fun AdvertView(modifier: Modifier = Modifier) {
    val currentWidth = LocalConfiguration.current.screenWidthDp
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context,
                        currentWidth
                    )
                )
                adUnitId = context.getString(R.string.ad_mob_key_id)
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AdView() {
    AdvertView()
}

@Composable
fun SearchName(onValueChange: (String) -> Unit = {}) {

    var name by remember { mutableStateOf("") }

    Text(
        text = stringResource(id = R.string.title),
        style = MaterialTheme.typography.h5,
        color = Color.White
    )
    Spacer(modifier = Modifier.size(10.dp))
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = name,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search, contentDescription = ""
            )
        },
        onValueChange = {
            name = it
            onValueChange(it)
        },
        singleLine = true,
        label = {
            Text(
                text = stringResource(id = R.string.field_name),
                style = MaterialTheme.typography.body2
            )
        },
        placeholder = { Text(text = stringResource(id = R.string.field_name_example)) },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
fun NamesContent(state: List<Name>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(
            8.dp
        )
    ) {
        items(state) { items ->
            CardNames(name = items)
        }
    }
}

@Composable
fun PopularContent(state: List<PopularName>?) {
    val popular = state ?: emptyList()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(
            8.dp
        )
    ) {
        items(popular) { popular ->
            CardPopularNames(popularName = popular)
        }
    }
}

@Composable
fun CardNames(name: Name) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Background,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(15.dp)
    ) {
        Row {
            Text(
                text = "Periodo: ${
                    name.period?.replace("[", "")?.replace("]", "")?.replace(",", "-")
                } ", color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "freq: ${name.freq.toString()}",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CardPopularNames(popularName: PopularName) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Background,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(15.dp)
    ) {
        Row {
            Text(
                text = popularName.rank.toString(),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = "Nome: ${popularName.name.toString()}", color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "freq: ${popularName.freq.toString()}",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}