package _1_map

import arrow.core.Either

object SuperSizeMe {
    private const val MORGAN = "Morgan Spurlock"
    private const val RONALD = "Ronald Mc"

    @JvmStatic
    fun main(args: Array<String>) {
        println(PeopleRepository.people); println()

        val morganOrError = superSizeMorgan()
        println(morganOrError); println()

        val ronaldOrError = superSizeRonald()
        println(ronaldOrError); println()

        val morganOrCustomError = handlePersonNotFound(MORGAN, morganOrError)
        println(morganOrCustomError); println()

        val ronaldOrCustomError = handlePersonNotFound(RONALD, ronaldOrError)
        println(ronaldOrCustomError); println()
    }

    private fun superSizeMorgan(): Either<Error, Person> =
        PeopleRepository.retrievePersonByName(MORGAN)
            .map { p -> superSize(p) }

    private fun superSizeRonald(): Either<Error, Person> =
        PeopleRepository.retrievePersonByName(RONALD)
            .map { p -> superSize(p) }

    private fun superSize(person: Person): Person =
        person.drinkMilkshakes(3).eatBurgers(7)

    private fun handlePersonNotFound(
        personName: String,
        personOrError: Either<Error, Person>
    ): Either<Error, Person> =
        personOrError.mapLeft { Error("Person $personName not found") }
}
