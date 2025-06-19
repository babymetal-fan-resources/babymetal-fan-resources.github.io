package org.skyluc.babymetal_site.element2data

import org.skyluc.babymetal_site.data as d
import org.skyluc.babymetal_site.yaml.*
import org.skyluc.fan_resources.BaseError
import org.skyluc.fan_resources.element2data as fr

import fr.ElementToData.Result

object ElementToData extends fr.ElementToData with Processor[fr.ElementToData.Result] {

  override def processCategoriesPage(categoriesPage: CategoriesPage): Either[BaseError, Result] =
    toCategoriesPage(categoriesPage).map(d => Result(d, Nil))

  override def processContentPage(contentPage: ContentPage): Either[BaseError, Result] =
    toContentPage(contentPage).map(d => Result(d, Nil))

  override def processChronologyPage(chronologyPage: ChronologyPage): Either[BaseError, Result] =
    toChronologyPage(chronologyPage).map(d => Result(d, d.chronology.markers))

  // -------------

  private def toCategoriesPage(categoriesPage: CategoriesPage): Either[BaseError, d.CategoriesPage] = {
    val id = d.PageId(categoriesPage.id)
    for {
      startDate <- toDate(categoriesPage.`start-date`, id)
      endDate <- toDate(categoriesPage.`end-date`, id)
    } yield {
      d.CategoriesPage(
        id,
        categoriesPage.name,
        categoriesPage.categories,
        startDate,
        endDate,
      )
    }

  }

  private def toContentPage(contentPage: ContentPage): Either[BaseError, d.ContentPage] = {
    val id = d.PageId(contentPage.id)
    for {
      startDate <- toDate(contentPage.`start-date`, id)
      endDate <- toDate(contentPage.`end-date`, id)
      ids <- toIds(contentPage.content, id)
    } yield {
      d.ContentPage(
        id,
        contentPage.name,
        ids,
        startDate,
        endDate,
      )
    }

  }

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
