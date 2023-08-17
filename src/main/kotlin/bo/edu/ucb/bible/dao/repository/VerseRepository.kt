package bo.edu.ucb.bible.dao.repository

import bo.edu.ucb.bible.dao.jpa.Verse
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VerseRepository: JpaRepository<Verse,Long>