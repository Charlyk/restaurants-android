package io.dotcoding.software.restaurante.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties(ignoreUnknown = true)
data class MainResponse(@JsonProperty("response") val response: Response)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Response(@JsonProperty("groups") val groups: ArrayList<ResponseGroup>)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseGroup(@JsonProperty("items") val items: ArrayList<Item>)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Item(@JsonProperty("venue") val venue: Venue?)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Venue(@JsonProperty("name") val name: String?,
                 @JsonProperty("location") val location: Location?,
                 @JsonProperty("hours") val hours: Hours?,
                 @JsonProperty("url") val url: String?,
                 @JsonProperty("photos") val photos: Photo?)


@JsonIgnoreProperties(ignoreUnknown = true)
data class Hours(@JsonProperty("isOpen") val isOpen: Boolean)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Location(@JsonProperty("lat") val lat: Double,
                    @JsonProperty("lng") val lng: Double,
                    @JsonProperty("distance") val distance: String)



@JsonIgnoreProperties(ignoreUnknown = true)
data class Photo(@JsonProperty("groups") val groups: ArrayList<PhotoGroup>?)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PhotoGroup(@JsonProperty("items") val items: ArrayList<PhotoItemGroup>?)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PhotoItemGroup(@JsonProperty("prefix") val prefix: String?,
                         @JsonProperty("suffix") val suffix: String?)
