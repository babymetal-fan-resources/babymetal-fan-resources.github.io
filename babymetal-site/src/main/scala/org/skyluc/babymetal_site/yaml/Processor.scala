package org.skyluc.babymetal_site.yaml

import org.skyluc.fan_resources.BaseError
import org.skyluc.fan_resources.yaml as fr

trait Processor[A] extends fr.Processor[A] {
  def processCategoriesPage(categoriesPage: CategoriesPage): Either[BaseError, A]
  def processContentPage(contentPage: ContentPage): Either[BaseError, A]
  def processChronologyPage(chronologyPage: ChronologyPage): Either[BaseError, A]
}
