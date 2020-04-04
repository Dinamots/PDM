package fr.m1miage.tmdb.utils.extension

import fr.m1miage.tmdb.api.model.Person
import fr.m1miage.tmdb.api.model.PersonDetail
import fr.m1miage.tmdb.api.model.PersonResponse
import fr.m1miage.tmdb.utils.ANONYMOUS_IMG_PATH
import fr.m1miage.tmdb.utils.TMDB_IMAGES_PATH

fun Person.isCast() = this.cast_id != null
fun Person.getImgLink() =
    if (this.profile_path != null) TMDB_IMAGES_PATH + this.profile_path else ANONYMOUS_IMG_PATH

fun PersonDetail.getImgLink() =
    if (this.profile_path != null) TMDB_IMAGES_PATH + this.profile_path else ANONYMOUS_IMG_PATH

fun PersonResponse.toPerson(): Person {
    return Person(
        id = this.id!!,
        name = this.name!!,
        profile_path = this.profile_path,
        job = null,
        cast_id = null,
        character = null,
        credit_id = null,
        department = null,
        gender = 0,
        order = null
    )
}

fun List<PersonResponse>.toPersons(): List<Person> {
    return this.fold(mutableListOf()) { acc, personResponse ->
        run {
            acc.add(personResponse.toPerson())
            acc
        }
    }

}