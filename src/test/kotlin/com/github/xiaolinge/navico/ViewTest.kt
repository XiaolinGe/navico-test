package com.github.xiaolinge.navico

import arrow.core.None
import arrow.core.Option
import arrow.core.Right
import arrow.core.left
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ViewTest {
    // doChartsOverlap
    @Test
    fun `should return error when charts amount out of maximum`() {
        //given
        val chartA = Chart(x1 = 1, y1 = 4, x2 = 4, y2 = 1, colour = Colour(100, 100, 100))
        val chartB = chartA.copy(x1 = 3, y1 = 3, x2 = 5, y2 = 1)
        val chartC = chartA.copy(x1 = 3, y1 = 3, x2 = 5, y2 = 1)
        val charts = listOf(chartA, chartB, chartC)
        val view = View(charts)

        //when
        val result = view.doChartsOverlap()

        //then
        assertTrue(result.isLeft())
    }

    @Test
    fun `should return error when chart's coordinate out of bound`() {
        //given
        val chartA = Chart(x1 = 1, y1 = 101, x2 = 3, y2 = 99, colour = Colour(100, 100, 100))
        val chartB = chartA.copy(x1 = -1, y1 = 1, x2 = 1, y2 = -1)
        val charts = listOf(chartA, chartB)
        val view = View(charts)

        //when
        val result = view.doChartsOverlap()

        //then
        assertTrue(result.isLeft())
    }

    @Test
    fun `should return error when chart's coordinate is invalid`() {
        //given
        val chartA = Chart(x1 = 1, y1 = 1, x2 = 3, y2 = 3, colour = Colour(100, 100, 100))
        val chartB = chartA.copy(x1 = 1, y1 = 4, x2 = 4, y2 = 1)
        val charts = listOf(chartA, chartB)
        val view = View(charts)

        //when
        val result = view.doChartsOverlap()

        //then
        assertTrue(result.isLeft())
    }

    @Test
    fun `should return false when view with one chart`() {
        //given
        val chart = Chart(x1 = 1, y1 = 4, x2 = 4, y2 = 1, colour = Colour(100, 100, 100))
        val charts = listOf(chart)
        val view = View(charts)

        //when
        val result = view.doChartsOverlap()

        //then
        assertEquals(Right(false), result)
    }

    @Test
    fun `should return false when one chart is on the left of another chart`() {
        //given
        val chartA = Chart(x1 = 3, y1 = 4, x2 = 5, y2 = 1, colour = Colour(200, 200, 200))
        val chartB = chartA.copy(x1 = 1, y1 = 2, x2 = 2, y2 = 1)
        val charts = listOf(chartA, chartB)
        val view = View(charts)

        //when
        val result = view.doChartsOverlap()

        //then
        assertEquals(Right(false), result)
    }

    @Test
    fun `should return false when one chart is on the right of another chart`() {
        //given
        val chartA = Chart(x1 = 1, y1 = 2, x2 = 2, y2 = 1, colour = Colour(100, 100, 100))
        val chartB = chartA.copy(x1 = 3, y1 = 4, x2 = 5, y2 = 1)
        val charts = listOf(chartA, chartB)
        val view = View(charts)

        //when
        val result = view.doChartsOverlap()

        //then
        assertEquals(Right(false), result)
    }

    @Test
    fun `should return false when one chart is above another chart`() {
        //given
        val chartA = Chart(x1 = 1, y1 = 2, x2 = 2, y2 = 1, colour = Colour(100, 100, 100))
        val chartB = chartA.copy(x1 = 1, y1 = 5, x2 = 3, y2 = 3)
        val charts = listOf(chartA, chartB)
        val view = View(charts)

        //when
        val result = view.doChartsOverlap()

        //then
        assertEquals(Right(false), result)
    }

    @Test
    fun `should return false when one chart is below another chart`() {
        //given
        val chartA = Chart(x1 = 1, y1 = 5, x2 = 3, y2 = 3, colour = Colour(200, 200, 200))
        val chartB = chartA.copy(x1 = 1, y1 = 2, x2 = 2, y2 = 1)

        val charts = listOf(chartA, chartB)
        val view = View(charts)

        //when
        val result = view.doChartsOverlap()

        //then
        assertEquals(Right(false), result)
    }

    @Test
    fun `should return false when two charts only with one common point`() {
        //given
        val chartA = Chart(x1 = 1, y1 = 3, x2 = 2, y2 = 2, colour = Colour(100, 100, 100))
        val chartB = chartA.copy(x1 = 2, y1 = 2, x2 = 3, y2 = 1)

        val charts = listOf(chartA, chartB)
        val view = View(charts)

        //when
        val result = view.doChartsOverlap()

        //then
        assertEquals(Right(false), result)
    }

    @Test
    fun `should return false when two charts only with one common side`() {
        //given
        val chartA = Chart(x1 = 1, y1 = 2, x2 = 2, y2 = 1, colour = Colour(100, 100, 100))
        val chartB = chartA.copy(x1 = 2, y1 = 2, x2 = 3, y2 = 1)

        val charts = listOf(chartA, chartB)
        val view = View(charts)

        //when
        val result = view.doChartsOverlap()

        //then
        assertEquals(Right(false), result)
    }

    @Test
    fun `should return true when charts overlap`() {
        //given
        val chartA = Chart(x1 = 1, y1 = 4, x2 = 4, y2 = 1, colour = Colour(100, 100, 100))
        val chartB = chartA.copy(x1 = 3, y1 = 3, x2 = 5, y2 = 1)

        val charts = listOf(chartA, chartB)
        val view = View(charts)

        //when
        val result = view.doChartsOverlap()

        //then
        assertEquals(Right(true), result)
    }

    // getColour
    @Test
    fun `should return error when chart's colour is invalid`() {
        //given
        val chart = Chart(x1 = 1, y1 = 3, x2 = 3, y2 = 1, colour = Colour(300, 100, 100))
        val view = View(listOf(chart))

        //when
        val result = view.getColour(2, 2)

        //then
        assertTrue(result.isLeft())
    }

    @Test
    fun `should return single colour when coordinate in one chart`() {
        //given
        val view = createTestViewWithNoneOverlappedCharts()
        val expected = Option(view.charts[0].colour)

        //when
        val result = view.getColour(2, 4)

        //then
        assertEquals(Right(expected), result)
    }

    @Test
    fun `should return single colour when coordinate at one chart point`() {
        //given
        val view = createTestViewWithNoneOverlappedCharts()
        val expected = Option(view.charts[0].colour)

        //when
        val result = view.getColour(1, 5)

        //then
        assertEquals(Right(expected), result)

    }

    @Test
    fun `should return averaged colour when coordinate in multiple charts`() {
        //given
        val view = createTestViewWithOverlappedCharts()
        val expected = Option(getTestAveragedColour(view.charts.map { it.colour }))

        //when
        val result = view.getColour(5, 3)

        //then
        assertEquals(Right(expected), result)
    }

    @Test
    fun `should return averaged colour when coordinate at charts common points`() {
        //given
        val view = createTestViewWithNoneOverlappedCharts()
        val expected = Option(getTestAveragedColour(view.charts.map { it.colour }))

        //when
        val result = view.getColour(3, 3)

        //then
        assertEquals(Right(expected), result)
    }

    @Test
    fun `should return none when coordinate out of chart`() {
        //given
        val view = createTestViewWithNoneOverlappedCharts()

        //when
        val result = view.getColour(7, 7)

        //then
        assertEquals(Right(None), result)
    }

    private fun createTestViewWithOverlappedCharts(): View {
        val chartA = Chart(x1 = 1, y1 = 6, x2 = 6, y2 = 2, colour = Colour(100, 100, 100))
        val chartB = Chart(x1 = 4, y1 = 4, x2 = 8, y2 = 1, colour = Colour(200, 200, 200))
        val charts = listOf(chartA, chartB)
        return View(charts)
    }

    private fun createTestViewWithNoneOverlappedCharts(): View {
        val chartA = Chart(x1 = 1, y1 = 5, x2 = 3, y2 = 3, colour = Colour(100, 100, 100))
        val chartB = Chart(x1 = 3, y1 = 3, x2 = 5, y2 = 1, colour = Colour(200, 200, 200))
        val charts = listOf(chartA, chartB)
        return View(charts)
    }

    private fun getTestAveragedColour(colours: List<Colour>): Colour {
        val r = colours.map { it.r }.average().toInt()
        val g = colours.map { it.g }.average().toInt()
        val b = colours.map { it.b }.average().toInt()
        return Colour(r, g, b)
    }
}
