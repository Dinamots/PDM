package fr.m1miage.tmdb.utils.extension

import fr.m1miage.tmdb.api.model.Person
import fr.m1miage.tmdb.utils.ANONYMOUS_IMG_PATH
import fr.m1miage.tmdb.utils.TMDB_IMAGES_PATH

fun Person.isCast() = this.cast_id != null
fun Person.getImgLink() =
    if (this.profile_path != null) TMDB_IMAGES_PATH + this.profile_path else ANONYMOUS_IMG_PATH