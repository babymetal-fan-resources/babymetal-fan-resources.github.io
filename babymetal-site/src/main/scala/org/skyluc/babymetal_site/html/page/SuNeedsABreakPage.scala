package org.skyluc.babymetal_site.html.page

import org.skyluc.fan_resources.Common
import org.skyluc.fan_resources.data.*
import org.skyluc.fan_resources.html as fr
import org.skyluc.html.*
import org.skyluc.reference.data.op.DataView
import org.skyluc.scala.IO

import java.nio.file.Files
import scala.annotation.tailrec

import Html.*
import SvgElement.{text as stext, a as _, *}

object SuNeedsABreakPage extends MainSitePage {

  import SuNeedsABreakSupport.*

  override val outputPath: Path = Path("schedule.html")

  val SNAB_LABEL = "Snab"

  override val pageConfiguration: fr.page.MainSitePageConfiguration =
    MainSitePageConfiguration(
      TitleGenerator.generateTitle(SNAB_LABEL),
      TitleGenerator.generateTitle("current BABYMETAL schedule"),
      outputPath.toAbsoluteString(),
      MainSitePage.imageLogo.imageUrl,
    )

  override def mainContent(): Seq[BodyElement[?]] = {
    Seq(
      div().appendElements(img().withId("schedule-image")),
      script().setScript("""
var date = new Date(Date.now());
var day = ("" + date.getDate()).padStart(2, "0")
var month = ("" + (date.getMonth() + 1)).padStart(2, "0")
var dateString = date.getFullYear() + "-" + month + "-" + day
var image = document.querySelector("#schedule-image")
image.src = "/asset/image/schedule/" + dateString + ".png"
"""),
    )
  }

  val initRefDate = Date(2010, 11, 28).epochDay()

  val dateStart = Date(2026, 6, 1)
  val dateEnd = Date(2026, 7, 3)

  val imageCollection = Vector("su001.png", "moa001.png", "momo001.png")

  def pagesFor(): Seq[MainSitePage] = {
    Seq(SuNeedsABreakPage)
  }

  def generateSvgs(outputPath: Path, view: DataView): Unit = {
    val schedules = SuNeedsABreakSupport
      .extractSchedules(view)
      .filter(schedule =>
        schedule.date.comparedTo(SuNeedsABreakPage.dateStart) >= 0 && schedule.date
          .comparedTo(SuNeedsABreakPage.dateEnd) <= 0
      )

    IO.deleteDirectoryContent(outputPath.asFilePath())
    Files.createDirectories(outputPath.asFilePath())
    schedules.foreach(generateSvg(_, outputPath))
  }

  def generateSvg(schedule: Schedule, outputPath: Path): Unit = {
    val filePath = outputPath.resolve(schedule.date.toString()).withExtension(Common.SVG_EXTENSION)
    val content = HtmlRenderer.render(SuNeedsABreakSvg.svgImage(schedule))
    val output = Files.newBufferedWriter(filePath.asFilePath().toAbsolutePath())
    output.write(content)
    output.close()

  }
}

object SuNeedsABreakSupport {

  val SHORT_BREAK = 3
  val LONG_BREAK = 6

  case class Schedule(
      date: Date,
      show: Option[Show],
      tour: Option[Tour],
      previousShow: Show,
      previousTour: Option[Tour],
      nextShow: Show,
      nextTour: Option[Tour],
      seriesSize: Int,
  )

  object Schedule {
    def from(s: Schedule1, view: DataView): Schedule = {

      val previousShow = s.previousShow.get
      val nextShow = s.nextShow.get

      Schedule(
        s.date,
        s.show,
        s.show.flatMap(_.tour).map(view.mainData.get(_)),
        previousShow,
        previousShow.tour.map(view.mainData.get(_)),
        nextShow,
        nextShow.tour.map(view.mainData.get(_)),
        s.seriesSize,
      )
    }
  }

  case class Schedule1(
      date: Date,
      show: Option[Show] = None,
      previousShow: Option[Show] = None,
      nextShow: Option[Show] = None,
      seriesSize: Int = -1,
  ) {
    override def toString(): String =
      s"($date, ${show.map(_.date)}, ${previousShow.map(_.date)}, ${nextShow.map(_.date)}, $seriesSize)"
  }

  def extractSchedules(view: DataView): Seq[Schedule] = {

    val shows = view.mainData.datums.values
      .flatMap {
        case s: Show =>
          Some(s)
        case _ =>
          None
      }
      .toSeq
      .sortBy(_.date)

    val firstDate = shows.head.date
    val refDay = firstDate.epochDay()
    val lastDate = shows.last.date

    println(s"$firstDate -> $lastDate")

    val period1 = Date.datesBetween(firstDate, lastDate).map(Schedule1(_))

    val period2 = shows.foldLeft(period1) { (acc, show) =>
      acc.updated(show.date.fromRefDay(refDay), Schedule1(show.date, Some(show)))
    }

    @tailrec def loopPrevious(
        current: List[Schedule1],
        lastShow: Option[Show],
        acc: List[Schedule1],
    ): List[Schedule1] = {
      current match {
        case head :: tail =>
          head.show match {
            case Some(show) =>
              loopPrevious(tail, Some(show), head.copy(previousShow = lastShow) :: acc)
            case None =>
              loopPrevious(tail, lastShow, head.copy(previousShow = lastShow) :: acc)
          }
        case Nil =>
          acc.reverse
      }
    }

    val period3 = loopPrevious(period2.toList, None, Nil)

    @tailrec def loopNextR(
        current: List[Schedule1],
        lastShow: Option[Show],
        acc: List[Schedule1],
    ): List[Schedule1] = {
      current match {
        case head :: tail =>
          head.show match {
            case Some(show) =>
              loopNextR(tail, Some(show), head.copy(nextShow = lastShow) :: acc)
            case None =>
              loopNextR(tail, lastShow, head.copy(nextShow = lastShow) :: acc)
          }
        case Nil =>
          acc
      }
    }

    def loopNext(list: List[Schedule1]) = loopNextR(list.reverse, None, Nil)

    val period4 = loopNext(period3)

    @tailrec def loopSeriesR(period: Vector[Schedule1], series: Seq[Show], acc: Seq[Seq[Show]]): Seq[Seq[Show]] = {
      val currentShow = series.last
      val currentSchedule1 = period(currentShow.date.fromRefDay(refDay))
      currentSchedule1.nextShow match {
        case None =>
          acc :+ series
        case Some(nextShow) =>
          if (nextShow.date.fromDay(currentShow.date) > LONG_BREAK) {
            loopSeriesR(period, Seq(nextShow), acc :+ series)
          } else {
            loopSeriesR(period, series :+ nextShow, acc)
          }
      }
    }

    def loopSeries(list: List[Schedule1]): Seq[Seq[Show]] = {
      loopSeriesR(list.toVector, list.head.show.toSeq, Seq())
    }

    val series = loopSeries(period4)

    val period5 = series.foldLeft(period4.toVector) { (acc, series) =>
      val seriesSize = series.size
      val startRef = series.head.date.fromRefDay(refDay)
      val endRef = series.last.date.fromRefDay(refDay)
      (startRef to endRef).foldLeft(acc) { (acc2, i) =>
        acc2.updated(i, acc2(i).copy(seriesSize = seriesSize))
      }
    }

    period5
      .filter(s => s.previousShow.isDefined && s.nextShow.isDefined)
      .map(Schedule.from(_, view))
  }

  val CLASS_BG = "bg"
  val CLASS_TEXT_MAIN_LARGE = "text-main-large"
  val CLASS_TEXT_MAIN_MEDIUM = "text-main-medium"
  val CLASS_TEXT_MAIN_SMALL_MEDIUM = "text-main-small-medium"
  val CLASS_TEXT_MAIN_SMALL = "text-main-small"
  val CLASS_TEXT_SHOW_LARGE = "text-show-large"
  val CLASS_TEXT_SHOW_MEDIUM = "text-show-medium"
  val CLASS_TEXT_TOUR_LARGE = "text-tour-large"
  val CLASS_TEXT_TOUR_MEDIUM = "text-tour-medium"

  val CLASS_TEXT_MAIN_WHITE = "text-main-white"
  val CLASS_TEXT_MAIN_RED = "text-main-red"
  val CLASS_TEXT_MAIN_RED_MEDIUM = "text-main-red-medium"
  val CLASS_TEXT_MAIN_DATE = "text-main-date"
  val CLASS_TEXT_MAIN_SNAB = "text-main-snab"
  val CLASS_TEXT_MARKING = "text-marking"
  val CLASS_FILTER = "filter"

  val STYLE = s"""

.$CLASS_BG {
  fill: url(#bgGradient);
  stroke: none;
}

.$CLASS_TEXT_MAIN_LARGE,
.$CLASS_TEXT_MAIN_MEDIUM,
.$CLASS_TEXT_MAIN_SMALL_MEDIUM,
.$CLASS_TEXT_MAIN_SMALL,
.$CLASS_TEXT_SHOW_LARGE,
.$CLASS_TEXT_SHOW_MEDIUM,
.$CLASS_TEXT_TOUR_LARGE,
.$CLASS_TEXT_TOUR_MEDIUM,
.$CLASS_TEXT_MAIN_DATE{
  text-anchor: middle;
  text-align: center;
  stroke: none;
  font-family:'Noto Sans';
}

.$CLASS_TEXT_MAIN_LARGE {
  fill: #dfdfdf;
  font-size: 56px;
  font-weight: bold;
}

.$CLASS_TEXT_MAIN_MEDIUM {
  fill: #dfdfdf;
  font-size: 28px;
}

.$CLASS_TEXT_MAIN_SMALL_MEDIUM{
  fill: #dfdfdf;
  font-size: 22px;
}

.$CLASS_TEXT_MAIN_SMALL {
  fill: #dfdfdf;
  font-size: 16px;
}

.$CLASS_TEXT_SHOW_LARGE {
  fill: #d90009;
  font-size: 48px;
  font-weight: bold;
}

.$CLASS_TEXT_SHOW_MEDIUM {
  fill: #d90009;
  font-size: 28px;
  font-weight: bold;
}

.$CLASS_TEXT_TOUR_LARGE {
  fill: #d90009;
  font-size: 26px;
}

.$CLASS_TEXT_TOUR_MEDIUM {
  fill: #d90009;
  font-size: 18px;
}

.$CLASS_TEXT_MAIN_DATE {
  fill: #808080;
  font-size: 30px;
  font-weight: bold;
}

.$CLASS_TEXT_MAIN_SNAB {
  fill: #282828;
  font-size: 12px;
  stroke: none;
  font-family:'Noto Sans';
}

.$CLASS_TEXT_MARKING {
  fill: #707070;
  font-size: 16px;
  stroke: none;
  font-family:'Noto Sans';
}

"""

}

object SuNeedsABreakSvg {

  import SuNeedsABreakSupport.*
  import SuNeedsABreakPage.*

  def svgImage(schedule: Schedule): Svg = {

    val defs = plain(
      """
<defs>
  <linearGradient 
    id="bgGradient"
    spreadMethod="reflect"
    x1="30%"
    y1="50%"
    x2="85%"
    y2="50%"
    gradientTransform="rotate(21)">
    <stop offset="0" stop-color="#101010" />
    <stop offset="1" stop-color="#202020" />
  </linearGradient>
  <linearGradient 
    id="maskImageGradient">
    <stop offset="0.9" stop-color="#FFFFFF" />
    <stop offset="1" stop-color="#FFFFFF" stop-opacity="0"/>
  </linearGradient>
  <mask id="maskImage">
    <rect x="0" y="0" width="360" height="540" fill="url(#maskImageGradient)"/>
  </mask>
</defs>
"""
    )

    val bg = g()
      .appendElements(
        rect(0, 0, 1080, 540).withClass(CLASS_BG)
      )

    val imageIndex = schedule.date.fromRefDay(initRefDate) % (imageCollection.length)

    val bgImage = g()
      .appendElements(image(0, 0, 360, 540, "../../data-snab/" + imageCollection(imageIndex)))
      .withMask("url(#maskImage)")

    val date = stext(0, 0, schedule.date.toString())
      .withClass(CLASS_TEXT_MAIN_DATE)
      .withTransform(s"translate(720, 53)")

    val today = schedule.show
      .map { show =>
        g()
          .appendElements(
            stext(0, 0, "BABYMETAL")
              .withClass(CLASS_TEXT_MAIN_LARGE),
            stext(0, 33, "is performing today")
              .withClass(CLASS_TEXT_MAIN_MEDIUM),
            stext(0, 74, show.shortname.getOrElse(show.fullname)).withClass(CLASS_TEXT_SHOW_LARGE),
          )
          .appendElements(
            schedule.tour.map { tour =>
              stext(0, 105, tour.fullname.replace("&", "&amp;")).withClass(CLASS_TEXT_TOUR_LARGE)
            }.toSeq*
          )
          .withTransform(s"translate(720, 131)")
      }
      .getOrElse {
        val breakText = if (schedule.seriesSize < 0) {
          "is on a break"
        } else {
          schedule.nextShow.date.fromDay(schedule.previousShow.date) match {
            case 2 =>
              "has a day off"
            case 3 =>
              "has a couple of days off"
            case 4 =>
              "has a few days off"
            case _ =>
              "is on a short break"
          }
        }
        g()
          .appendElements(
            stext(0, 0, "BABYMETAL")
              .withClass(CLASS_TEXT_MAIN_LARGE),
            stext(0, 33, breakText)
              .withClass(CLASS_TEXT_MAIN_MEDIUM),
          )
          .withTransform(s"translate(720, 161)")
      }

    val previousShowTimeText = {
      val nbDays = schedule.date.fromDay(schedule.previousShow.date)

      if (nbDays == 1) {
        "yesterday"
      } else {
        s"$nbDays days ago"
      }
    }

    val previousShow =
      previousNextShow(schedule.previousShow, schedule.previousTour, "the previous show was", previousShowTimeText)
        .withTransform("translate(720, 295)")

    val nextShowTimeText = {
      val nbDays = schedule.nextShow.date.fromDay(schedule.date)

      if (nbDays == 1) {
        "tomorrow"
      } else {
        s"in $nbDays days"
      }
    }

    val nextShow =
      previousNextShow(schedule.nextShow, schedule.nextTour, "the next show is", nextShowTimeText)
        .withTransform("translate(720, 403)")

    val snab = stext(0, 0, "#SuNeedsABreak - " + Date.today)
      .withClass(CLASS_TEXT_MAIN_SNAB)
      .withTransform("translate(1075, 530) rotate(-90)")

    val marking = g()
      .appendElements(
        stext(0, 0, "http://babymetal.fan-resources.net/schedule.html").withClass(CLASS_TEXT_MARKING)
      )
      .withTransform("translate(360, 535)")

    svg()
      .withViewBox(0, 0, 1080, 540)
      .appendElements(style(STYLE))
      .appendElements(defs, bg, bgImage, date, today, previousShow, nextShow, marking, snab)
  }

  private def previousNextShow(show: Show, tour: Option[Tour], introText: String, timeText: String): SvgG = {
    g()
      .appendElements(
        stext(0, 0, introText).withClass(CLASS_TEXT_MAIN_SMALL),
        stext(0, 25, show.shortname.getOrElse(show.fullname))
          .withClass(CLASS_TEXT_SHOW_MEDIUM),
      )
      .appendElements(
        tour
          .map { tour =>
            Seq(
              stext(0, 46, tour.fullname.replace("&", "&amp;")).withClass(CLASS_TEXT_TOUR_MEDIUM),
              stext(0, 68, timeText).withClass(CLASS_TEXT_MAIN_SMALL_MEDIUM),
            )
          }
          .getOrElse(
            Seq(
              stext(0, 50, timeText).withClass(CLASS_TEXT_MAIN_SMALL_MEDIUM)
            )
          )*
      )
  }

}
