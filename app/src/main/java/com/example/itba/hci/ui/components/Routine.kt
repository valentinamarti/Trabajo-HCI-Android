package com.example.itba.hci.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.itba.hci.R
import com.example.itba.hci.remote.model.RemoteAction
import com.example.itba.hci.ui.RoutineViewModel
import com.example.itba.hci.ui.getViewModelFactory



data class Routine(
    val name: String,
    val meta: Meta,
    val actions: List<Action>
)

data class Meta(
    val description: String,
    val color: Color
)

data class Action(
    val actionName: String,
    val params: List<Param>
)

data class Param(
    val value: String
)


@Composable
fun RoutineView(navController: NavController, viewModel: RoutineViewModel = viewModel(factory = getViewModelFactory()), routineId: String) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getRoutine(routineId)
    }

    val routine = uiState.currentRoutine!!

    Surface(
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.navigate("routine_screen") }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
            Row {

                if (routine != null) {
                    Text(
                        text = routine.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(30.dp)
                    )
                }


            }
            CustomDivider()
            Row {
                Text(
                    text = routine.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(26.dp)
                )
            }
            CustomDivider()

            EventContainer(routine = routine)

            CustomDivider()

            ColorSelector(routine)
        }
    }
}


@Composable
fun CustomDivider() {
    Divider(
        color = Color(0xFF8D8888), 
        modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
    )
}


@Composable
fun EventContainer(routine: com.example.itba.hci.model.Routine) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 20.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFF5F5F5)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.routines_actions),
                        style = MaterialTheme.typography.bodyLarge
                    )

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            routine.actions.forEachIndexed { index, action ->
                EventItem(action = action, index = index)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun EventItem(action: RemoteAction, index: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFD5D5D5), shape = RoundedCornerShape(4.dp))
            .padding(vertical = 8.dp, horizontal = 18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = action.actionName ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.weight(1f))


        }

    }
}


@Composable
fun ColorSelector(routine: com.example.itba.hci.model.Routine) {
    val selectedColor = routine.color

    val colorOptions = listOf(
        Color(0xFFFCD59D),
        Color(0xFFBFE3FF),
        Color(0xFFE8C3FF),
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 18.dp)
    ) {
        Text(
            text = stringResource(id = R.string.color_title),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(end = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            colorOptions.forEach { option ->
                val isSelected = selectedColor.primary == option.toString()
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(color = option)
                        .border(
                            width = if (isSelected) 2.dp else 0.dp,
                            color = if (isSelected) Color.Black else Color.Transparent,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun RoutinePreview() {
//    val routine = Routine(
//        name = "Routine 1",
//        meta = Meta(description = "Description", color = Color(0xFFFCD59D)),
//        actions = listOf(
//            Action(actionName = "Action 1", params = listOf(Param("Param 1"))),
//            Action(actionName = "Action 2", params = listOf(Param("Param 2"))),
//            Action(actionName = "Action 3", params = listOf(Param("Param 3"))),
//            Action(actionName = "Action 4", params = listOf(Param("Param 4")))
//        )
//    )
//    HomeDomeTheme {
//        RoutineView(routine)
//    }
//}
