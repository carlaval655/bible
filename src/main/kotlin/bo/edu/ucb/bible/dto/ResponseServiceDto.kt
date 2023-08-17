package bo.edu.ucb.bible.dto

data class ResponseServiceDto (
    val book_name: String,
        val chapter: String,
        val verse: String,
        val text: String,
) {
}