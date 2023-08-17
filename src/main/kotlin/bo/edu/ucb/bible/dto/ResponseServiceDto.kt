package bo.edu.ucb.bible.dto

data class VerseDto(
        val book_id: String,
        val book_name: String,
        val chapter: Int,
        val verse: Int,
        val text: String
)

data class ResponseServiceDto(
        val reference: String,
        val verses: List<VerseDto>,
        val text: String,
        val translation_id: String,
        val translation_name: String,
        val translation_note: String
)
