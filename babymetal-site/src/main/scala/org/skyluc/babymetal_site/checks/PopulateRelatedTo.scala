package org.skyluc.babymetal_site.checks

import org.skyluc.babymetal_site.data.*
import org.skyluc.fan_resources.checks as fr
import org.skyluc.fan_resources.checks.PopulateRelatedTo
import org.skyluc.fan_resources.data.Datum

object PopulateRelatedTo extends fr.PopulateRelatedTo with Processor[Datum[?]] {}
