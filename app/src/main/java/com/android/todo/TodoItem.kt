package com.android.todo

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.android.todo.data.TodoEntity

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoItem(todo: TodoEntity, onDelete: () -> Unit, onClick: () -> Unit) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 100.dp)
            .clip(CardDefaults.outlinedShape)
            .combinedClickable(
                onLongClick = {
                    clipboardManager.setText(AnnotatedString(todo.content))
                    Toast
                        .makeText(
                            context,
                            "Copied to Clipboard 📋",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
            ) {
                onClick()
            },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = todo.content,
                modifier = Modifier.padding(12.dp).fillMaxSize().weight(0.8f),
            )
            IconButton(onClick = onDelete, modifier = Modifier.weight(0.2f).fillMaxHeight()) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Todo")
            }
        }
    }
}