package org.skyluc.babymetal_site.checks

import org.skyluc.babymetal_site.data.*
import org.skyluc.fan_resources.checks as fr
import org.skyluc.fan_resources.checks.CheckLocalAssetExists
import org.skyluc.fan_resources.data.Path

import fr.CheckError

class CheckLocalAssetExists(baseAssetImagePath: Path)
    extends fr.CheckLocalAssetExists(baseAssetImagePath)
    with Processor[Seq[CheckError]] {}
