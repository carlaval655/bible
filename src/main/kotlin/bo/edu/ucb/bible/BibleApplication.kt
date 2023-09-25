package bo.edu.ucb.bible

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@SpringBootApplication
class BibleApplication

fun main(args: Array<String>) {
	runApplication<BibleApplication>(*args)
}
