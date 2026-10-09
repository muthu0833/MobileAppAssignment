package com.inila.mobileappassignment.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.inila.mobileappassignment.data.model.Lesson
import com.inila.mobileappassignment.util.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    courseId: String,
    viewModel: DetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    LaunchedEffect(courseId) {
        viewModel.loadData(courseId)
    }
    
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.course?.title ?: "Lessons") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Text("←")
                    }
                }
            )
        }
    ) { padding ->
        when (val result = uiState.lessons) {
            is Result.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is Result.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${result.message}", color = MaterialTheme.colorScheme.error)
                }
            }
            is Result.Success -> {
                Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                    uiState.course?.let {
                        Text("Progress: ${it.progress}%", style = MaterialTheme.typography.bodyLarge)
                        LinearProgressIndicator(progress = { it.progress / 100f }, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
                    }
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(result.data) { lesson ->
                            LessonItem(
                                lesson = lesson,
                                onToggle = { viewModel.toggleLessonCompletion(lesson.id, lesson.courseId, lesson.isCompleted) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LessonItem(lesson: Lesson, onToggle: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = lesson.isCompleted,
            onCheckedChange = { onToggle() }
        )
        Text(lesson.title, modifier = Modifier.padding(start = 8.dp))
    }
}
