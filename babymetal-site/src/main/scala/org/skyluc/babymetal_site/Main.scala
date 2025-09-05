package org.skyluc.babymetal_site

import org.skyluc.babymetal_site.checks.CheckLocalAssetExists
import org.skyluc.babymetal_site.data.Data
import org.skyluc.babymetal_site.data2Page.DataToPage
import org.skyluc.babymetal_site.html.CompiledDataGeneratorBuilder
import org.skyluc.fan_resources.ErrorsHolder
import org.skyluc.fan_resources.Main.displayErrors
import org.skyluc.fan_resources.checks.DataCheck
import org.skyluc.fan_resources.checks.MoreDataCheck
import org.skyluc.fan_resources.checks.ReferencesCheckProcessor
import org.skyluc.fan_resources.data as frData
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html.SiteOutput

import frData.ImplicitDatum

object Main {

  def main(args: Array[String]): Unit = {
    main(Path())
  }

  def main(rootPath: Path): Unit = {

    println("\n***** Running BABYMETAL Fan Resources site *****\n")

    val dataFolder = rootPath.resolve(DATA_PATH)
    val staticFolder = rootPath.resolve(STATIC_PATH)
    val staticFrFolder = rootPath.resolve("fan-resources", STATIC_PATH)
    val outputFolder = rootPath.resolve(TARGET_PATH, SITE_PATH)

    val errors = ErrorsHolder()

    val (parserErrors, datums) = BabymetalSite.Parser001.parseFolder(dataFolder)

    val implicitDatums = ImplicitDatum().generate(datums)

    errors.append("PARSER ERRORS", parserErrors)

    val d = frData.Data.get(datums ++ implicitDatums, Data.creator)

    val (checkErrors, checkedDatums) =
      DataCheck.check(
        datums ++ implicitDatums,
        d,
        ReferencesCheckProcessor(d.datums.keySet, d),
        CheckLocalAssetExists(rootPath.resolve(BASE_IMAGE_ASSET_PATH)),
      )

    val data = frData.Data.get(checkedDatums, Data.creator)

    val moreCheckerrors = MoreDataCheck.check(data)

    errors.append("CHECKS ERRORS", checkErrors ++ moreCheckerrors)

    displayErrors(errors, 10)

    if (!errors.isEmpty) {
      System.exit(2)
    }

    val generator = CompiledDataGeneratorBuilder.generator(data)

    val pages = DataToPage(generator).generate(rootPath, data)

    SiteOutput.generate(pages, Seq(staticFrFolder, staticFolder), outputFolder)

  }

  // -----------

  val DATA_PATH = "data"
  val STATIC_PATH = "static"
  val ASSET_PATH = "asset"
  val IMAGE_PATH = "image"
  val TARGET_PATH = "target"
  val SITE_PATH = "site"

  val BASE_IMAGE_ASSET_PATH = Path(STATIC_PATH, ASSET_PATH, IMAGE_PATH)

}
