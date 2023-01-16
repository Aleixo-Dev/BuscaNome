package br.com.nicolas.buscanome.data.mapper

import br.com.nicolas.buscanome.data.response.*

//regio search name
fun ResponseSearchNameItem.toData() = Names(names = res.map { it.toName(nome) })

fun Re.toName(name: String) = Name(freq = frequencia.toString(), period = periodo, name = name)

data class Names(
    val names: List<Name>? = emptyList()
)

fun ResponseSearchName.toNames(): List<Name> {
    var abc = ArrayList<Name>()
    map { cu ->
        for (i in cu.res) {
            abc.add(
                Name(
                    i.frequencia.toString(), i.periodo, cu.nome
                )
            )
        }
    }
    return abc
}

data class Name(
    val freq: String? = "",
    val period: String? = "",
    val name: String = ""
)
// endregion

//region popular name
fun ResponsePopularityNames.toData() = this.map {
    PopularName(
        name = it.nome,
        freq = it.freq.toString(),
        rank = it.rank
    )
}

data class PopularName(
    val name: String? = null,
    val freq: String? = null,
    val rank: Int? = null
)
// endregion