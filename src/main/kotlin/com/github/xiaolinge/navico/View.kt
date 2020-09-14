package com.github.xiaolinge.navico

import arrow.core.Either
import arrow.core.Option
import arrow.core.toOption
import mu.KotlinLogging
import javax.validation.constraints.Size

data class View(
    @field:Size(max = 2)
    val charts: List<Chart>
) {
    private val logger = KotlinLogging.logger { }

    fun doChartsOverlap(): Either<String, Boolean> {
        if (hasInvalidData(this)) return Either.left("Invalid data")

        if (hasNoMultipleCharts()) return Either.right(false)

        if (notOverlapOnXAxis(this.charts) || notOverlapOnYAxis(this.charts)) return Either.right(false)

        return Either.right(true)
    }

    fun getColour(x: Int, y: Int): Either<String, Option<Colour>> {
        if (hasInvalidData(this)) return Either.left("Invalid data")

        val colours = this.charts
            .filter { isInsideChart(x, y, it) }
            .map { it.colour }

        return when (colours.size) {
            0 -> Either.right(Option.empty())
            1 -> getSingleColour(colours)
            else -> getAveragedColour(colours)
        }
    }

    private fun hasInvalidData(view: View): Boolean {
        logger.debug("Validate view: {}", view)
        return view.isInvalid() || view.charts.any { chartIsInvalid(it) }
    }

    private fun chartIsInvalid(chart: Chart): Boolean =
        chart.isInvalid() || chart.colour.isInvalid() || coordinateIsInvalid(chart)

    private fun coordinateIsInvalid(chart: Chart): Boolean = chart.x1 >= chart.x2 || chart.y1 <= chart.y2

    private fun hasNoMultipleCharts(): Boolean = this.charts.size < 2

    private fun notOverlapOnXAxis(charts: List<Chart>): Boolean =
        charts[0].x1 >= charts[1].x2 || charts[0].x2 <= charts[1].x1

    private fun notOverlapOnYAxis(charts: List<Chart>): Boolean =
        charts[0].y1 <= charts[1].y2 || charts[0].y2 >= charts[1].y1

    private fun isInsideChart(x: Int, y: Int, chart: Chart): Boolean =
        x >= chart.x1 && x <= chart.x2 && y <= chart.y1 && y >= chart.y2

    private fun getSingleColour(colours: List<Colour>): Either<Nothing, Option<Colour>> =
        Either.right(Option(colours.first()))

    private fun getAveragedColour(colours: List<Colour>): Either<Nothing, Option<Colour>> {
        val r = colours.map { it.r }.average().toInt()
        val g = colours.map { it.g }.average().toInt()
        val b = colours.map { it.b }.average().toInt()
        return Either.right(Colour(r, g, b).toOption())
    }
}
