package org.skyluc.babymetal_site.yaml

import org.skyluc.babymetal_site.data as d
import org.skyluc.fan_resources.data as dfr
import org.skyluc.fan_resources.yaml.ParserError
import org.skyluc.fan_resources.yaml.{YamlKeys as yk, *}
import org.skyluc.scala.EitherOps
import org.skyluc.yaml.*

import yk.*
import YamlKeys.*

object PageIdDecoder extends FromStringDecoder[dfr.ElementGenId[d.Page], FrDecoders] {

  override def fromString(s: String): dfr.ElementGenId[d.Page] = {
    d.PageId(s)
  }

}

case class CategoriesPageBuilder(
    id: Option[String] = None,
    label: Option[String] = None,
    categories: Option[Seq[String]] = None,
    startDate: Option[dfr.Date] = None,
    endDate: Option[dfr.Date] = None,
    linkedTo: Option[Seq[dfr.Id[?]]] = None,
    errors: Seq[ParserError] = Seq(),
) extends DatumBuilder[d.CategoriesPage, CategoriesPageBuilder] {

  import ObjectBuilder.*

  override def setLinkedTo(linkedTo: Option[Seq[dfr.Id[?]]]): CategoriesPageBuilder = copy(linkedTo = linkedTo)

  override def setErrors(errs: Seq[ParserError]): CategoriesPageBuilder = copy(errors = errors :++ errs)

  override protected def build(using
      context: DecoderContext[FrDecoders]
  ): Either[Seq[ParserError], d.CategoriesPage] = {
    for {
      id <- checkDefined(ID, id)
      label <- checkDefined(LABEL, label)
      categories <- checkDefined(CATEGORIES, categories)
      startDate <- checkDefined(START_DATE, startDate)
      endDate <- checkDefined(END_DATE, endDate)
    } yield {
      d.CategoriesPage(
        d.PageId(id),
        label,
        categories,
        startDate,
        endDate,
        linkedTo.getOrElse(Nil),
      )
    }
  }
}

object CategoriesPageDecoder extends DatumObjectDecoder[d.CategoriesPage, CategoriesPageBuilder] {

  override def datumAttributes(using
      context: DecoderContext[FrDecoders]
  ): Seq[YamlObjectAttributeProcessor[?, d.CategoriesPage, CategoriesPageBuilder, FrDecoders]] = Seq(
    YamlObjectAttribute(
      ID,
      StringDecoder(),
      (b, v) => b.copy(id = v),
    ),
    YamlObjectAttribute(
      LABEL,
      StringDecoder(),
      (b, v) => b.copy(label = v),
    ),
    YamlObjectAttribute(
      CATEGORIES,
      ListDecoder(StringDecoder()),
      (b, v) => b.copy(categories = v),
    ),
    YamlObjectAttribute(
      START_DATE,
      DateDecoder,
      (b, v) => b.copy(startDate = v),
    ),
    YamlObjectAttribute(
      END_DATE,
      DateDecoder,
      (b, v) => b.copy(endDate = v),
    ),
  )

  override def zero(): CategoriesPageBuilder = CategoriesPageBuilder()

}

case class ChronologyPageBuilder(
    id: Option[String] = None,
    markers: Option[Seq[dfr.Id[?]]] = None,
    startDate: Option[dfr.Date] = None,
    endDate: Option[dfr.Date] = None,
    linkedTo: Option[Seq[dfr.Id[?]]] = None,
    errors: Seq[ParserError] = Seq(),
) extends DatumBuilder[d.ChronologyPage, ChronologyPageBuilder] {

  import ObjectBuilder.*

  override def setLinkedTo(linkedTo: Option[Seq[dfr.Id[?]]]): ChronologyPageBuilder = copy(linkedTo = linkedTo)

  override def setErrors(errs: Seq[ParserError]): ChronologyPageBuilder = copy(errors = errors :++ errs)

  override protected def build(using
      context: DecoderContext[FrDecoders]
  ): Either[Seq[ParserError], d.ChronologyPage] = {
    for {
      id <- checkDefined(ID, id)
      itemIds <- checkDefined(MARKERS, markers)
      startDate <- checkDefined(START_DATE, startDate)
      endDate <- checkDefined(END_DATE, endDate)
      markerIds <- itemIdsToMarkerIds(itemIds)
    } yield {
      d.ChronologyPage(
        d.PageId(id),
        dfr.Chronology(
          markerIds,
          startDate,
          endDate,
        ),
        linkedTo.getOrElse(Nil),
      )
    }
  }

  private def itemIdsToMarkerIds(itemIds: Seq[dfr.Id[?]])(using
      context: DecoderContext[FrDecoders]
  ): Either[Seq[ParserError], Seq[dfr.ChronologyMarkerId]] = {

    val res: Seq[Either[ParserError, dfr.ChronologyMarkerId]] = itemIds.map {
      case a: dfr.AlbumId =>
        Right(dfr.AlbumMarkerId(a))
      case e: dfr.EventId =>
        Right(dfr.EventMarkerId(e))
      case m: dfr.MediaId =>
        Right(dfr.MediaMarkerId(m))
      case m: dfr.MultiMediaId[?] =>
        Right(dfr.MultiMediaMarkerId(m))
      case s: dfr.ShowId =>
        Right(dfr.ShowMarkerId(s))
      case s: dfr.SongId =>
        Right(dfr.SongMarkerId(s))
      case t: dfr.TourId =>
        Right(dfr.TourMarkerId(t))
      case id =>
        Left(ParserError(context.filename, Some(s"Unsupported item id for marker: $id")))
    }

    val (errors, markerIds) = EitherOps.errorsAndValues(res)

    if (errors.isEmpty) {
      Right(markerIds)
    } else {
      Left(errors)
    }
  }
}

object ChronologyPageDecoder extends DatumObjectDecoder[d.ChronologyPage, ChronologyPageBuilder] {

  override def datumAttributes(using
      context: DecoderContext[FrDecoders]
  ): Seq[YamlObjectAttributeProcessor[?, d.ChronologyPage, ChronologyPageBuilder, FrDecoders]] = Seq(
    YamlObjectAttribute(
      ID,
      StringDecoder(),
      (b, v) => b.copy(id = v),
    ),
    YamlObjectAttribute(
      MARKERS,
      ListDecoder(new DispatchDecoder[dfr.Id[?], FrDecoders] {
        override def dispatch(using
            context: DecoderContext[FrDecoders]
        ): Map[String, YamlDecoder[? <: dfr.Id[?], FrDecoders]] =
          context.decoders.markerItem
      }),
      (b, v) => b.copy(markers = v),
    ),
    YamlObjectAttribute(
      START_DATE,
      DateDecoder,
      (b, v) => b.copy(startDate = v),
    ),
    YamlObjectAttribute(
      END_DATE,
      DateDecoder,
      (b, v) => b.copy(endDate = v),
    ),
  )

  override def zero(): ChronologyPageBuilder = ChronologyPageBuilder()

}

case class ContentPageBuilder(
    id: Option[String] = None,
    label: Option[String] = None,
    content: Option[Seq[dfr.Id[?]]] = None,
    startDate: Option[dfr.Date] = None,
    endDate: Option[dfr.Date] = None,
    linkedTo: Option[Seq[dfr.Id[?]]] = None,
    errors: Seq[ParserError] = Seq(),
) extends DatumBuilder[d.ContentPage, ContentPageBuilder] {

  import ObjectBuilder.*

  override def setLinkedTo(linkedTo: Option[Seq[dfr.Id[?]]]): ContentPageBuilder = copy(linkedTo = linkedTo)

  override def setErrors(errs: Seq[ParserError]): ContentPageBuilder = copy(errors = errors :++ errs)

  override protected def build(using context: DecoderContext[FrDecoders]): Either[Seq[ParserError], d.ContentPage] = {
    for {
      id <- checkDefined(ID, id)
      label <- checkDefined(LABEL, label)
      content <- checkDefined(CONTENT, content)
      startDate <- checkDefined(START_DATE, startDate)
      endDate <- checkDefined(END_DATE, endDate)
    } yield {
      d.ContentPage(
        d.PageId(id),
        label,
        content,
        startDate,
        endDate,
        linkedTo.getOrElse(Nil),
      )
    }
  }
}

object ContentPageDecoder extends DatumObjectDecoder[d.ContentPage, ContentPageBuilder] {

  override def datumAttributes(using
      context: DecoderContext[FrDecoders]
  ): Seq[YamlObjectAttributeProcessor[?, d.ContentPage, ContentPageBuilder, FrDecoders]] = Seq(
    YamlObjectAttribute(
      ID,
      StringDecoder(),
      (b, v) => b.copy(id = v),
    ),
    YamlObjectAttribute(
      LABEL,
      StringDecoder(),
      (b, v) => b.copy(label = v),
    ),
    YamlObjectAttribute(
      CONTENT,
      ListDecoder(new DispatchDecoder[dfr.Id[?], FrDecoders] {
        override def dispatch(using
            context: DecoderContext[FrDecoders]
        ): Map[String, YamlDecoder[? <: dfr.Id[?], FrDecoders]] =
          context.decoders.markerItem
      }),
      (b, v) => b.copy(content = v),
    ),
    YamlObjectAttribute(
      START_DATE,
      DateDecoder,
      (b, v) => b.copy(startDate = v),
    ),
    YamlObjectAttribute(
      END_DATE,
      DateDecoder,
      (b, v) => b.copy(endDate = v),
    ),
  )

  override def zero(): ContentPageBuilder = ContentPageBuilder()

}
