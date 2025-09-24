package org.skyluc.babymetal_site.data

import org.skyluc.babymetal_site.yaml.BabymetalSiteDecoders
import org.skyluc.fan_resources.data as fr
import org.skyluc.reference.html.edit.EditSupportContext

import fr.Path
import fr.checks.DataCheck
import fr.checks.ReferencesChecker
import fr.checks.ReferencesCheckProcessorBuilder
import checks.LocalAssetExistsChecker

object Data {

  val decoders = BabymetalSiteDecoders

  val dispatcherBuilder = new fr.op.DataDispatcherBuilder {

    override def build(DataProcessor: fr.op.DataProcessor): fr.op.DataDispatcher =
      fr.op.DataDispatcher(DataProcessor)

  }

  val defaultExpanders = fr.op.DataLoader.defaultExpanders(op.ImplicitDataExpander)

  val defaultPopulaters =
    fr.op.DataLoader.defaultPopulaters(fr.op.MultimediaExtraPopulater(fr.op.MultimediaExtraProcessBuilder()))

  def defaultCheckers(staticFolderPath: Path) = DataCheck.defaultCheckers(
    ReferencesChecker(ReferencesCheckProcessorBuilder()),
    LocalAssetExistsChecker(staticFolderPath),
  )

  val editSupportContext = new EditSupportContext {}

}
