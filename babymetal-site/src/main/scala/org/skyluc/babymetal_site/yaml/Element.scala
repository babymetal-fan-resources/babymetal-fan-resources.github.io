package org.skyluc.babymetal_site.yaml

import org.skyluc.fan_resources.BaseError
import org.skyluc.fan_resources.SimpleError
import org.skyluc.fan_resources.yaml as fr
import org.virtuslab.yaml.YamlCodec

trait WithProcessor extends fr.WithProcessor {

  override def process[A](processor: fr.Processor[A]): Either[BaseError, A] = {
    processor match {
      case p: Processor[A] =>
        process(p)
      case _ =>
        Left(SimpleError("A processor for babymetal_site elements is required"))
    }
  }

  def process[A](processor: Processor[A]): Either[BaseError, A]
}

case class ChronologyPage(
    id: String,
    `start-date`: String,
    `end-date`: String,
    markers: List[fr.ChronologyMarker],
) extends fr.Element
    with WithProcessor
    derives YamlCodec {

  override def process[A](processor: Processor[A]): Either[BaseError, A] =
    processor.processChronologyPage(this)
}
