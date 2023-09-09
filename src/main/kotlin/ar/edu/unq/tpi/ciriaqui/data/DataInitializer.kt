package ar.edu.unq.tpi.ciriaqui.data

import ar.edu.unq.tpi.ciriaqui.dao.TeacherRepository
import ar.edu.unq.tpi.ciriaqui.model.Teacher
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Component
class DataInitializer(val teacherRepository: TeacherRepository) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val logger: Logger = LoggerFactory.getLogger(DataInitializer::class.java)

        logger.info("Seeder is running...")
        logger.info(teacherRepository.count().toString())
        if(teacherRepository.count()<= 0){
            logger.info("Seeding")
            val teacher0 = Teacher(name = "Albus Dumbledore", password = "asd123", email="teacher0@asd.cp")
            val teacher1 = Teacher(name = "Minerva McGonagall", password = "asd123", email="teacher1@asd.cp")
            val teacher2 = Teacher(name = "Remus Lupin", password = "asd123", email="teacher2@asd.cp")
            val teacher3 = Teacher(name = "Sybill Trelawney", password = "asd123", email="teacher3@howgart")
            val teacher4 = Teacher(name = "Quirinus Quirrell", password = "asd123", email="teacher3@howgart")
            val teachers = listOf(teacher0, teacher1, teacher2, teacher3, teacher4)
            teacherRepository.saveAll(teachers)
            logger.info("Seeding DONE")
        }
    }
}
