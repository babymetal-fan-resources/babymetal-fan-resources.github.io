package org.skyluc.babymetal_site.html.pages

import org.skyluc.babymetal_site.data as d
import org.skyluc.babymetal_site.html.ChronologyMarkerCompiledDataGenerator
import org.skyluc.babymetal_site.html.PageDescription
import org.skyluc.babymetal_site.html.Site
import org.skyluc.babymetal_site.html.SitePage
import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.Date
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html.CompiledDataGenerator
import org.skyluc.fan_resources.html.MarkerCompiledData
import org.skyluc.fan_resources.html.TitleAndDescription
import org.skyluc.fan_resources.html.component.ChronologyMarkerDetails
import org.skyluc.fan_resources.html.component.MarkerCard
import org.skyluc.html.*
import Html.*

class ChronologyPage(years: Seq[ChronologyPage.ChronologyYear], description: PageDescription)
    extends SitePage(description) {

  import ChronologyPage.*

  var yearCounter: Boolean = false
  var monthCounter: Boolean = true
  var dayCounter: Boolean = false

  override def elementContent(): Seq[BodyElement[?]] = {
    val elements = years.flatMap(_.months.flatMap(_.days.flatMap(_.markers)))
    Seq(
      div()
        .withId(ID_CHRONOLOGY_SECTION)
        .appendElements(categoriesBlock())
        .appendElements(years.map(yearBlock)*)
        .appendElements(
          ChronologyMarkerDetails.generateData(elements)
        )
    )
  }

  private def categoriesBlock(): Div = {
    div()
      .withClass(CLASS_CHRONOLOGY_CATERGORIES)
      .appendElements(
        Seq("song", "show", "album", "tour", "multimedia").map { cat =>
          div().appendElements(
            inputCheckbox()
              .withId("cat-check-" + cat)
              .withClass(CLASS_CATEGORY_CHECK)
              .withChecked(true)
              .withOnChange(s"""toggleMarkerCategory('$cat')"""),
            text(cat),
          )
        }*
      )
  }

  private def yearBlock(year: ChronologyYear): Div = {
    div()
      .withClass(CLASS_CHRONOLOGY_BLOCK)
      .withClass(CLASS_CHRONOLOGY_YEAR)
      .appendElements(
        div()
          .withClass(CLASS_CHRONOLOGY_BLOCK_LABEL)
          .appendElements(text(year.year.toString())),
        div()
          .withClass(CLASS_CHRONOLOGY_BLOCK_CONTENT)
          .appendElements(year.months.map(monthBlock)*),
      )
  }

  private def monthBlock(month: ChronologyMonth): Div = {
    div()
      .withClass(CLASS_CHRONOLOGY_BLOCK)
      .withClass(CLASS_CHRONOLOGY_MONTH)
      .appendElements(
        div()
          .withClass(CLASS_CHRONOLOGY_BLOCK_LABEL)
          .appendElements(text(Date.MONTH_LABELS(month.month))),
        div()
          .withClass(CLASS_CHRONOLOGY_BLOCK_CONTENT)
          .appendElements(month.days.map(dayBlock)*),
      )
  }

  private def dayBlock(day: ChronologyDay): Div = {
    div()
      .withClass(CLASS_CHRONOLOGY_BLOCK)
      .withClass(CLASS_CHRONOLOGY_DAY)
      .appendElements(
        div()
          .withClass(CLASS_CHRONOLOGY_BLOCK_LABEL)
          .appendElements(text(zeroLeadString(day.day))),
        div()
          .withClass(CLASS_CHRONOLOGY_BLOCK_CONTENT)
          .appendElements(day.markers.map { m =>
            MarkerCard.generate(m.id, m.marker)
          }*),
      )
  }

  private def zeroLeadString(i: Int): String = {
    if (i < 10)
      "0" + i
    else
      i.toString()
  }
}

object ChronologyPage {

  // val PAGE_PATH = Path("chronology") // TODO: decide what is the main page
  val PAGE_PATH = Path("index")

  val ID_CHRONOLOGY_SECTION = "chronology-section"
  val CLASS_CATEGORY_CHECK = "cat-check"
  val CLASS_CHRONOLOGY_CATERGORIES = "chronology-caterogies"
  val CLASS_CHRONOLOGY_BLOCK = "chronology-block"
  val CLASS_CHRONOLOGY_YEAR = "chronology-year"
  val CLASS_CHRONOLOGY_MONTH = "chronology-month"
  val CLASS_CHRONOLOGY_DAY = "chronology-day"
  val CLASS_CHRONOLOGY_BLOCK_LABEL = "chronology-block-label"
  val CLASS_CHRONOLOGY_BLOCK_CONTENT = "chronology-block-content"
  val CLASS_CHRONOLOGY_BLOCK_LABEL_BGLIGHT = "chronology-block-label-bglight"
  val CLASS_CHRONOLOGY_BLOCK_LABEL_BGDARK = "chronology-block-label-bgdark"

  // ----------

  case class ChronologyYear(
      year: Int,
      months: Seq[ChronologyMonth],
  )

  case class ChronologyMonth(
      month: Int,
      days: Seq[ChronologyDay],
  )

  case class ChronologyDay(
      day: Int,
      markers: Seq[MarkerCompiledData],
  )

  def pageFor(chronologyPage: d.ChronologyPage, generator: CompiledDataGenerator): Seq[SitePage] = {

    val markerGenerator = new ChronologyMarkerCompiledDataGenerator(generator)

    val markersCompiledData = chronologyPage.chronology.markers.map(_.process(markerGenerator))

    val byYears: Seq[ChronologyYear] =
      markersCompiledData
        .groupBy(_.marker.date.year)
        .map { (year, l) =>
          val byMonth: Seq[ChronologyMonth] = l
            .groupBy(_.marker.date.month)
            .map { (month, l) =>
              val byDay: Seq[ChronologyDay] = l
                .groupBy(_.marker.date.day)
                .map { (day, l) =>
                  ChronologyDay(day, l.reverse)
                }
                .toSeq
                .sortBy(_.day)
                .reverse
              ChronologyMonth(month, byDay)
            }
            .toSeq
            .sortBy(_.month)
            .reverse
          ChronologyYear(year, byMonth)
        }
        .toSeq
        .sortBy(_.year)
        .reverse

    val mainPage =
      ChronologyPage(
        byYears,
        PageDescription(
          TitleAndDescription.formattedTitle(
            None,
            None,
            "Chronology",
            None,
            None,
            None,
          ),
          TitleAndDescription.formattedDescription(
            None,
            None,
            "Chronology",
            None,
            None,
            None,
          ),
          SitePage.absoluteUrl(Site.DEFAULT_COVER_IMAGE.source),
          SitePage.canonicalUrlFor(PAGE_PATH),
          PAGE_PATH.withExtension(Common.HTML_EXTENSION),
          None,
          None,
          false,
        ),
      )

    Seq(mainPage)
  }
}
