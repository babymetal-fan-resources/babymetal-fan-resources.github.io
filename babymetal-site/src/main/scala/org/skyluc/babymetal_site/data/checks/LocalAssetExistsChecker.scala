package org.skyluc.babymetal_site.data.checks

import org.skyluc.babymetal_site.Main
import org.skyluc.babymetal_site.data.ProcessorMultimedia
import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.data.checks as fr

import fr.CheckError

class LocalAssetExistsChecker(staticFolderPath: Path)
    extends fr.LocalAssetExistsChecker(LocalAssetExistsProcessor(staticFolderPath))

class LocalAssetExistsChecker2(staticFolderPath: Path, staticFolderPath2: Path)
    extends fr.LocalAssetExistsChecker(LocalAssetExistsProcessor2(staticFolderPath, staticFolderPath2))

class LocalAssetExistsProcessor(staticFolderPath: Path)
    extends fr.LocalAssetExistsProcessor(staticFolderPath.resolve(Main.BASE_IMAGE_ASSET_PATH))
    with ProcessorMultimedia[Seq[CheckError]]

class LocalAssetExistsProcessor2(staticFolderPath: Path, staticFolderPath2: Path)
    extends fr.LocalAssetExistsProcessor2(
      staticFolderPath.resolve(Main.BASE_IMAGE_ASSET_PATH),
      staticFolderPath2.resolve(Main.BASE_IMAGE_ASSET_PATH),
    )
    with ProcessorMultimedia[Seq[CheckError]]
