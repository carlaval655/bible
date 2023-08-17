package bo.edu.ucb.bible.api

import bo.edu.ucb.bible.bl.VerseBl
import bo.edu.ucb.bible.dao.jpa.Verse
import bo.edu.ucb.bible.dao.repository.VerseRepository
import bo.edu.ucb.bible.dto.ResponseServiceDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/bible")
@RestController
class BibleApi @Autowired constructor(private val verseBl: VerseBl) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(BibleApi::class.java)
    }

    @GetMapping("/verse")
    fun exchangeRate(@RequestParam book: String,
                     @RequestParam chapter: String,
                     @RequestParam verse: String): ResponseServiceDto {
        LOGGER.info("Iniciando peticion para obtener el siguiente versiculo: $book $chapter:$verse")
        val result = verseBl.getVerse(book, chapter, verse)
        return result!!
    }

}