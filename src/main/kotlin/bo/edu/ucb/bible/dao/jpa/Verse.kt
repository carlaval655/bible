package bo.edu.ucb.bible.dao.jpa
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "verse")
class Verse (
    var book: String,
    var chapter: String,
    var verse: String,
    var text: String,
    var date: Date,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,
        )
{
    constructor() : this("","","","", Date())
}