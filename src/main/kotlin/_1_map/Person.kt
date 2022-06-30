package _1_map

data class Person(val name: String, val weight: Int) {

    fun drinkMilkshakes(qty: Int): Person =
        this.copy(weight = this.weight + qty)

    fun eatBurgers(qty: Int): Person =
        this.copy(weight = this.weight + qty * 2)
}
