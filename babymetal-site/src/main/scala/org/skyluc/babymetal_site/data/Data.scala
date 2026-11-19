package org.skyluc.babymetal_site.data

import org.skyluc.fan_resources.data as fr

import org.skyluc.fan_resources.base.Path
import fr.checks.DataCheck
import fr.checks.ReferencesChecker
import fr.checks.ReferencesCheckProcessorBuilder
import checks.LocalAssetExistsChecker
import checks.LocalAssetExistsChecker2
import org.skyluc.fan_resources.descriptor.FieldDescriptor
import org.skyluc.fan_resources.descriptor.FieldDetails

object Data {

  val dispatcherBuilder = new fr.op.DataDispatcherBuilder {

    override def build(DataProcessor: fr.op.DataProcessor): fr.op.DataDispatcher =
      fr.op.DataDispatcher(DataProcessor)

  }

  def defaultCheckers(staticFolderPath: Path) = DataCheck.defaultCheckers(
    ReferencesChecker(ReferencesCheckProcessorBuilder()),
    LocalAssetExistsChecker(staticFolderPath),
  )

  def defaultCheckers(staticFolderPath: Path, staticFolderPath2: Path) = DataCheck.defaultCheckers(
    ReferencesChecker(ReferencesCheckProcessorBuilder()),
    LocalAssetExistsChecker2(staticFolderPath, staticFolderPath2),
  )

}

object FieldDescriptors {
  val wikiField = FieldDescriptor(Keys.WIKI)
  val choreographerField = FieldDescriptor(Keys.CHOREOGRAPHER)

  val LABEL_CHOREOGRAPHER = "choreographer"
  val LABEL_WIKI = "BABYMETAL Wiki"

  val fieldRepository = {
    fr.FieldDescriptors.fieldRepository
      .updated(
        FieldDescriptors.choreographerField,
        FieldDetails(LABEL_CHOREOGRAPHER, false, fr.FieldDescriptors.INFO_LEVEL_ALL),
      )
      .updated(
        FieldDescriptors.wikiField,
        FieldDetails(LABEL_WIKI, true, fr.FieldDescriptors.INFO_LEVEL_ALL),
      )
  }
}
