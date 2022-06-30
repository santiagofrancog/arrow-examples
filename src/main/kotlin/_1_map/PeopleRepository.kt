package _1_map

import arrow.core.Either
import arrow.core.rightIfNotNull

object PeopleRepository {
    private val PERSON_NOT_FOUND_ERR = Error("Person not found")

    val people = listOf(
        Person("Morgan Spurlock", 84),
    )

    fun retrievePersonByName(name: String): Either<Error, Person> =
        people.firstOrNull { person -> person.name == name }
            .rightIfNotNull { PERSON_NOT_FOUND_ERR }
}
