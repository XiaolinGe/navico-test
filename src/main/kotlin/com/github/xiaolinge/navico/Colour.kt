package com.github.xiaolinge.navico

import org.hibernate.validator.constraints.Range

data class Colour(
    @field:Range(min = 0, max = 255)
    var r: Int,

    @field:Range(min = 0, max = 255)
    var g: Int,

    @field:Range(min = 0, max = 255)
    var b: Int
)
