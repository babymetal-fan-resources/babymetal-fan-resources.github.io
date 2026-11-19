package org.skyluc.babymetal_site.data

import org.skyluc.fan_resources.data as fr
import org.skyluc.fan_resources.descriptor.ChoiceCategoryRepository
import org.skyluc.fan_resources.descriptor.FieldDetails

object ExtendedDatum {

  val elementFields = {
    val baseFields = fr.DataDescriptors.categoryRepository.elementFields
    baseFields
      .updated(
        fr.Song.TYPEID,
        baseFields(fr.Song.TYPEID) ++ Seq(FieldDescriptors.choreographerField, FieldDescriptors.wikiField),
      )
      .updated(
        fr.Album.TYPEID,
        baseFields(fr.Album.TYPEID) :+ FieldDescriptors.wikiField,
      )
  }

  val categoryRepository = {
    ChoiceCategoryRepository(
      Seq(
        // datum ids
        fr.Id.choiceCategoryList,
        fr.ElementId.choiceCategoryList,
        fr.MultimediaId.choiceCategoryList,
        fr.ImageId.choiceCategoryList,
        fr.WebItemId.choiceCategoryList,
        fr.PostId.choiceCategoryList,
        fr.Datum.choiceCategoryList,
        fr.Element.choiceCategoryList,
        fr.Multimedia.choiceCategoryList,
        fr.Image.choiceCategoryList,
        fr.WebItem.choiceCategoryList,
        fr.Post.choiceCategoryList,
        fr.Media.choiceCategoryList,
        // other
        fr.DisplayBlock.choiceCategoryList,
        fr.DisplaySection.choiceCategoryList,
        fr.DisplaySubsection.choiceCategoryList,
        fr.LineSection.choiceCategoryList,
      ),
      elementFields,
      FieldDescriptors.fieldRepository,
    )
  }
}
