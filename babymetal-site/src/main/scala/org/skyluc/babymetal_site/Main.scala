package org.skyluc.babymetal_site

import org.skyluc.babymetal_site.checks.CheckLocalAssetExists
import org.skyluc.babymetal_site.checks.PopulateRelatedTo
import org.skyluc.babymetal_site.data.Data
import org.skyluc.babymetal_site.data2Page.DataToPage
import org.skyluc.babymetal_site.html.CompiledDataGeneratorBuilder
import org.skyluc.fan_resources.ErrorsHolder
import org.skyluc.fan_resources.Main.displayErrors
import org.skyluc.fan_resources.checks.DataCheck
import org.skyluc.fan_resources.checks.MoreDataCheck
import org.skyluc.fan_resources.data as frData
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.html.SiteOutput

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

    val (parserErrors, datums) = BabymetalSite.Parser001.parseFolder(dataFolder.asFilePath())

    errors.append("PARSER ERRORS", parserErrors)

    val (checkErrors, checkedDatums) =
      DataCheck.check(
        datums,
        frData.Data.get(datums, Data.creator),
        PopulateRelatedTo,
        CheckLocalAssetExists(rootPath.resolve(BASE_IMAGE_ASSET_PATH)),
        false,
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

    println(s"nb of pages: ${pages.size}")

    SiteOutput.generate(pages, Seq(staticFrFolder.asFilePath(), staticFolder.asFilePath()), outputFolder.asFilePath())

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
