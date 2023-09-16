package ar.edu.unq.tpi.ciriaqui.data

import ar.edu.unq.tpi.ciriaqui.dao.LackRepository
import ar.edu.unq.tpi.ciriaqui.dao.TeacherRepository
import ar.edu.unq.tpi.ciriaqui.model.Article
import ar.edu.unq.tpi.ciriaqui.model.Lack
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate

@Component
class DataInitializer(val teacherRepository: TeacherRepository, val lackRepository: LackRepository) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val logger: Logger = LoggerFactory.getLogger(DataInitializer::class.java)
        logger.info("Seeder is running...")
        if(teacherRepository.count()<= 0) {
            logger.info("Seeding TEACHERS")
            val teacher0 = Teacher(name = "Albus Dumbledore", password = "asd123", email = "teacher0@asd.cp")
            val teacher1 = Teacher(name = "Minerva McGonagall", password = "asd123", email = "teacher1@howgart.edu")
            val teacher2 = Teacher(name = "Remus Lupin", password = "asd123", email = "teacher2@howgart.edu")
            val teacher3 = Teacher(name = "Sybill Trelawney", password = "asd123", email = "teacher3@howgart.edu")
            val teacher4 = Teacher(name = "Quirinus Quirrell", password = "asd123", email = "teacher4@howgart.edu")
            val teachers = listOf(teacher0, teacher1, teacher2, teacher3, teacher4)
            teacherRepository.saveAll(teachers)
            logger.info("Seeding LACKS")
            val lack0 = Lack(Article.PARTICULAR, LocalDate.of(2023, 12, 30),LocalDate.of(2023, 12, 31), teacher2)
            val lack1 = Lack(Article.STUDYDAY,LocalDate.of(2023, 12, 30), LocalDate.of(2023, 12, 30), teacher4)
            val lack2 = Lack(Article.STUDYDAY,LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 30), teacher4)
            val lacks = listOf(lack0, lack1, lack2)
            lackRepository.saveAll(lacks)
            logger.info("Seeding DONE")
        }
    }
}
