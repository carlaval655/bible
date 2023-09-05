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

    @GetMapping("/request")
    fun request(): List<Verse> {
        LOGGER.info("Iniciando peticion para obtener todas las solicitudes")
        val result = verseBl.getAllRequests()
        return result!!
    }

    @GetMapping("/request/id")
    fun requestById(@RequestParam codigo: Long): Verse {
        LOGGER.info("Iniciando peticion para obtener la solicitud con id: $codigo")
        val result = verseBl.getVerseById(codigo)
        return result!!
    }

    @DeleteMapping("/request")
    fun deleteRequest(@RequestParam id: Long): String {
        LOGGER.info("Iniciando peticion para eliminar la solicitud con id: $id")
        val result = verseBl.deleteRequest(id)
        return result!!
    }

    @PutMapping("/request")
    fun updateRequest(@RequestParam id: Long, book: String, chapter: String, verse: String): ResponseServiceDto {
        LOGGER.info("Iniciando peticion para actualizar la solicitud con id: $id")
        val result = verseBl.updateRequest(id, book, chapter, verse)
        return result!!
    }
}