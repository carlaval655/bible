package bo.edu.ucb.bible.bl

import bo.edu.ucb.bible.dao.jpa.Verse
import bo.edu.ucb.bible.dao.repository.VerseRepository
import bo.edu.ucb.bible.dto.ResponseServiceDto
import bo.edu.ucb.bible.exception.ServiceException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.awt.print.Book
import java.math.BigDecimal
import java.util.*

@Service
class VerseBl @Autowired constructor(private val verseRepository: VerseRepository){

    companion object {
        val objectMapper = jacksonObjectMapper()
        val LOGGER: Logger = LoggerFactory.getLogger(VerseBl::class.java)
    }

    @Value("\${api.url}")
    lateinit var apiUrl: String

    fun getVerse(book: String, chapter: String, verseNr: String): ResponseServiceDto? {
        LOGGER.info("Iniciando logica para obtener el versiculo solicitado")
        if (verseNr.toInt() > 80) {
            LOGGER.error("El versiculo no puede ser mayor a 80")
            throw IllegalArgumentException("El versiculo no puede ser mayor a 80")
        }
        if (chapter.toInt() > 150) {
            LOGGER.error("El capitulo no puede ser mayor a 150")
            throw IllegalArgumentException("El versiculo no puede ser mayor a 150")
        }

        if (verseNr.toInt() < 1) {
            LOGGER.error("El versiculo no puede ser menor a 1")
            throw IllegalArgumentException("El versiculo no puede ser menor a 1")
        }

        if (chapter.toInt() < 1) {
            LOGGER.error("El capitulo no puede ser menor a 1")
            throw IllegalArgumentException("El versiculo no puede ser menor a 1")
        }
        val response = invokeApi("$apiUrl/$book $chapter:$verseNr")
        val responseServiceDto = parseResponse(response)
        val verse = Verse()
        verse.book = book!!
        verse.chapter = chapter!!
        verse.verse = verseNr!!
        verse.text = responseServiceDto.text!!
        verse.date = Date()
        verseRepository.save(verse)
        return responseServiceDto
    }

    fun invokeApi(endpoint: String): Response {
        LOGGER.info("Invocando servicio de obtencion de versiculos")
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(endpoint)
                .build()
        try {
            return client.newCall(request).execute()
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("Error en el servicio de obtencion de versiculos")
        }
    }

    fun parseResponse(response: Response): ResponseServiceDto {
        LOGGER.info("Parseando respuesta del servicio de obtencion de versiculos")
        val responseBody = response.body?.string() // Obtenemos el contenido real del cuerpo de la respuesta

        if (responseBody != null && response.isSuccessful) {
            LOGGER.info("El servicio de obtencion de versiculos fue exitoso")
            LOGGER.info("El servicio de obtencion de versiculos => $responseBody")

            return objectMapper.readValue(responseBody, ResponseServiceDto::class.java)
        }

        LOGGER.info("El servicio de obtencion de versiculos fue fallido")
        throw ServiceException("Error en el servicio de obtencion de versiculos")
    }

    fun getAllRequests(): List<Verse> {
        LOGGER.info("Iniciando logica para obtener todas las solicitudes")
        val verses = verseRepository.findAll()

        return verses;
    }

    fun deleteRequest(id: Long): String {
        LOGGER.info("Iniciando logica para eliminar la solicitud con id: $id")
        if (id < 1) {
            LOGGER.error("El id no puede ser menor a 1")
            throw IllegalArgumentException("El id no puede ser menor a 1")
        }
        if (verseRepository.findById(id).isEmpty) {
            LOGGER.error("No se pudo eliminar la solicitud con id: $id")
            throw ServiceException("No existe una solicitud con id: $id")
        }
        return "Solicitud eliminada correctamente"
    }

    fun updateRequest(id: Long, book: String, chapter: String, verseNr: String): ResponseServiceDto {
        LOGGER.info("Iniciando logica para actualizar la solicitud con id: $id")
        if (id < 1) {
            LOGGER.error("El id no puede ser menor a 1")
            throw IllegalArgumentException("El id no puede ser menor a 1")
        }
        if (verseRepository.findById(id).isEmpty) {
            LOGGER.error("No se pudo actualizar la solicitud con id: $id")
            throw ServiceException("No existe una solicitud con id: $id")
        }
        val verseToUpdate = verseRepository.findById(id).get()
        val response = invokeApi("$apiUrl/$book $chapter:$verseNr")
        val responseServiceDto = parseResponse(response)
        verseToUpdate.book = book
        verseToUpdate.chapter = chapter
        verseToUpdate.verse = verseNr
        verseToUpdate.text = responseServiceDto.text
        verseRepository.save(verseToUpdate)
        return responseServiceDto
    }
}