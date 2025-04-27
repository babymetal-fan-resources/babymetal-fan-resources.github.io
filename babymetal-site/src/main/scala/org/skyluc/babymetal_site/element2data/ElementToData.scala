package org.skyluc.babymetal_site.element2data

import org.skyluc.babymetal_site.data as d
import org.skyluc.babymetal_site.yaml.ChronologyPage
import org.skyluc.babymetal_site.yaml.Processor
import org.skyluc.fan_resources.BaseError
import org.skyluc.fan_resources.element2data as fr

import fr.ElementToData.Result

object ElementToData extends fr.ElementToData with Processor[fr.ElementToData.Result] {

  override def processChronologyPage(chronologyPage: ChronologyPage): Either[BaseError, Result] =
    toChronologyPage(chronologyPage).map(d => Result(d, d.chronology.markers))

  // -------------

  private def toChronologyPage(chronologyPage: ChronologyPage): Either[BaseError, d.ChronologyPage] = {
    val id = d.PageId(chronologyPage.id)
    for {
      startDate <- toDate(chronologyPage.`start-date`, id)
      endDate <- toDate(chronologyPage.`end-date`, id)
      markers <- throughList(chronologyPage.markers, id)(toChronologyMarker)
    } yield {
      d.ChronologyPage(
        id,
        org.skyluc.fan_resources.data.Chronology(markers, startDate, endDate),
      )
    }
  }

}
