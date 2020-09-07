package ru.cutepool.testrecycler.model

fun Alien.links(): List<String> {
    return images
}

fun Alien.name() : String{
    return "$name, $age"
}

data class Alien(
        val id: String = "",
        val name: String = "",
        val age: Int = 0,

        val images: List<String> = mutableListOf()
)