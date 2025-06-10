package org.skyluc.babymetal_site.data

import org.skyluc.fan_resources.BaseError
import org.skyluc.fan_resources.data as fr

object PageId {
  private val GEN = "page"
  def apply(id: String, dark: Boolean = false): fr.ElementGenId[Page] = fr.ElementGenId[Page](GEN, id, dark)
}

sealed trait Page extends fr.Element[Page] {
  val id: fr.ElementGenId[Page]
}

case class ChronologyPage(
    id: fr.ElementGenId[Page],
    chronology: fr.Chronology,
    hasError: Boolean = false,
    linkedTo: Seq[fr.Id[?]] = Nil,
) extends Page
    with WithProcessor
    with WithProcessorElement {

  override val coverImage: fr.CoverImage = fr.CoverImage(
    fr.LocalImageId(fr.GroupId("siteimages"), "kitsume_cover"),
    true,
  )
  override def errored(): ChronologyPage = copy(hasError = true)
  override def withLinkedTo(id: fr.Id[?]*): ChronologyPage = copy(linkedTo = mergeLinkedTo(id))

  override def process[T](processor: Processor[T]): T =
    process(processor: ProcessorElement[T])

  override def process[T](processor: ProcessorElement[T]): T =
    processor.processChronologyPage(this)

  override def process[A](processor: ProcessorWithError[A]): Either[BaseError, A] = {
    processor.processChronologyPage(this)
  }

}

object Pages {
  val ID_BASE_PATH = "page"
}
