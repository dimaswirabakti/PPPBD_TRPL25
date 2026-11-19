package com.example.pppbdcompose

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).noteDao()

    // READ: Flow dari Room diubah menjadi StateFlow untuk UI
    val notes: StateFlow<List<Note>> = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    // CREATE
    fun addNote(title: String, content: String) {
        val titleText = title.trim()
        if (titleText.isEmpty()) return
        viewModelScope.launch {
            dao.insert(Note(title = titleText, note = content.trim()))
        }
    }

    // UPDATE
    fun updateNote(existing: Note, newTitle: String, newContent: String) {
        val titleText = newTitle.trim()
        if (titleText.isEmpty()) return
        viewModelScope.launch {
            dao.update(existing.copy(title = titleText, note = newContent.trim()))
        }
    }

    // DELETE
    fun deleteNote(note: Note) {
        viewModelScope.launch { dao.delete(note) }
    }
}
