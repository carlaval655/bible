package bo.edu.ucb.bible.dto

data class RequestServiceDto (
    val book: String,
    val chapter: String,
    val verse: String,
)