package ru.cutepool.testrecycler

import ru.cutepool.testrecycler.model.Alien
import kotlin.random.Random
import kotlin.random.nextInt

object GenerateAlien {

    private val links = listOf<String>(
        "https://randomwordgenerator.com/img/picture-generator/57e1d14a4f52a514f1dc8460962e33791c3ad6e04e507441722a72dd9e44c0_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/57e3d6464e55aa14f1dc8460962e33791c3ad6e04e507440772d73d69545c6_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/55e1d14b4e53a814f1dc8460962e33791c3ad6e04e507441722a72dc964cc7_640.png",
        "https://randomwordgenerator.com/img/picture-generator/52e2d0414e53ac14f1dc8460962e33791c3ad6e04e50744074267bd69748c4_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/53e4d54b4952b10ff3d8992cc12c30771037dbf85254794e732879dc944a_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/5fe1dc434c5bb10ff3d8992cc12c30771037dbf85254794e722e7ed19f4e_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/57e6d3474350ab14f1dc8460962e33791c3ad6e04e507441722872d59045cc_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/57e1d04b4b5aa414f1dc8460962e33791c3ad6e04e507441722978d69148c1_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/54e5dc434853af14f1dc8460962e33791c3ad6e04e507440772d7cdd9144c4_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/53e4d2424d53aa14f1dc8460962e33791c3ad6e04e507440722d7cd3924cc2_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/52e5d74a4356b10ff3d8992cc12c30771037dbf852547941742679d59044_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/52e8d0454a50a814f1dc8460962e33791c3ad6e04e507441722872d7944dcc_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/54e3d3474c5aa514f1dc8460962e33791c3ad6e04e507440772d73d79749c7_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/57e2d74a4251b10ff3d8992cc12c30771037dbf85254794177277dd3934c_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/57e1d7444f53a414f1dc8460962e33791c3ad6e04e5074417c2f73d69544c6_640.png",
        "https://randomwordgenerator.com/img/picture-generator/53e2d443425aa514f1dc8460962e33791c3ad6e04e50744074287ed59049cd_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/57e7dc4b4251a914f1dc8460962e33791c3ad6e04e5074417c2f7dd6924dc6_640.png",
        "https://randomwordgenerator.com/img/picture-generator/57e4d64a4e54a414f1dc8460962e33791c3ad6e04e507441722a72dd944cc0_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/53e4d0464854a914f1dc8460962e33791c3ad6e04e507440722d72d5964bcd_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/54e0d4434c57a514f1dc8460962e33791c3ad6e04e5074417c2d78d1934ec1_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/57e8d64b4b56a514f1dc8460962e33791c3ad6e04e507441722a72dc9448c3_640.jpg",
        "https://randomwordgenerator.com/img/picture-generator/57e8d64b435aaf14f1dc8460962e33791c3ad6e04e507440752f72d7924fc4_640.jpg"
    )

    fun generate(count: Int): List<Alien> {

        val aliens = mutableListOf<Alien>()
        val list = mutableListOf<String>()

        for (i in 1..count) {
            list.clear()
            val stories = Random.nextInt(3..7)
            for (t in 1..stories) {
                val number = Random.nextInt(0, links.size - 1)
                list.add(links[number])
            }
            aliens.add(Alien("$i", "John Weak$i", 25, list.distinct()))
        }

        return aliens
    }
}
