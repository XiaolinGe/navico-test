package com.github.xiaolinge.navico

import org.hibernate.validator.constraints.Range

data class Chart(
    @field:Range(min = 0, max = 100)
    var x1: Int,

    @field:Range(min = 0, max = 100)
    var y1: Int,

    @field:Range(min = 0, max = 100)
    var x2: Int,

    @field:Range(min = 0, max = 100)
    var y2: Int,

    var colour: Colour
)
