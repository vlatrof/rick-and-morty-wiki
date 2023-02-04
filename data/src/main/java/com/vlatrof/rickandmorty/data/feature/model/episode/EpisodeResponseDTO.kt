package com.vlatrof.rickandmorty.data.feature.model.episode

import com.google.gson.annotations.SerializedName
import com.vlatrof.rickandmorty.data.base.model.BaseResponseDTO
import com.vlatrof.rickandmorty.data.util.ResponseUtils.urlToId
import com.vlatrof.rickandmorty.domain.model.episode.DomainEpisode

data class EpisodeResponseDTO(
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val code: String,
    @SerializedName("characters")
    val characterUrls: List<String>

) : BaseResponseDTO {

    override fun toDomain() = DomainEpisode(
        id = id,
        name = name,
        code = code,
        airDate = airDate,
        characterIds = characterUrls.map { urlToId(it)!! }
    )

    override fun toEntity() = EpisodeEntity(
        id = id,
        name = name,
        code = code,
        airDate = airDate,
        characterIds = characterUrls.map { urlToId(it)!! }
    )
}
