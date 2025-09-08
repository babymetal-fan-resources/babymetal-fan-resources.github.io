package org.skyluc.babymetal_site.data.checks

import org.skyluc.fan_resources.data.Path
import org.skyluc.fan_resources.data.checks as fr
import fr.CheckError
import org.skyluc.babymetal_site.data.ProcessorMultimedia
import org.skyluc.babymetal_site.Main

class LocalAssetExistsChecker(staticFolderPath: Path)
    extends fr.LocalAssetExistsChecker(LocalAssetExistsProcessor(staticFolderPath))

class LocalAssetExistsProcessor(staticFolderPath: Path)
    extends fr.LocalAssetExistsProcessor(staticFolderPath.resolve(Main.BASE_IMAGE_ASSET_PATH))
    with ProcessorMultimedia[Seq[CheckError]]
