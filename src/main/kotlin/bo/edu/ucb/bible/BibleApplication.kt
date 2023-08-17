package bo.edu.ucb.bible

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BibleApplication

fun main(args: Array<String>) {
	runApplication<BibleApplication>(*args)
}
