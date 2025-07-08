package org.skyluc.babymetal_site.yaml

import org.skyluc.babymetal_site.data as d
import org.skyluc.fan_resources.yaml as fr

import fr.YamlKeys.*
import YamlKeys.*

class DataYamlWriter extends fr.DataYamlWriter with d.Processor[Unit] {

  override def processCategoriesPage(categoriesPage: d.CategoriesPage): Unit = {
    elementStart(CATEGORIESPAGE)
    attributeString(ID, categoriesPage.id.id)
    attributeString(LABEL, categoriesPage.name)
    attributeDate(START_DATE, categoriesPage.startDate)
    attributeDate(END_DATE, categoriesPage.endDate)
    attributeListString(CATEGORIES, categoriesPage.categories)
    processLinkedTo(categoriesPage)
    elementEnd
  }

  override def processChronologyPage(chronologyPage: d.ChronologyPage): Unit = {
    elementStart(CHRONOLOGYPAGE)
    attributeString(ID, chronologyPage.id.id)
    processChronology(chronologyPage.chronology)
    processLinkedTo(chronologyPage)
    elementEnd
  }

  override def processContentPage(contentPage: d.ContentPage): Unit = {
    elementStart(CONTENTPAGE)
    attributeString(ID, contentPage.id.id)
    attributeString(LABEL, contentPage.name)
    attributeDate(START_DATE, contentPage.startDate)
    attributeDate(END_DATE, contentPage.endDate)
    attributeListId(CONTENT, contentPage.content)
    processLinkedTo(contentPage)
    elementEnd
  }

}
