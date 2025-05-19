import Common.*

lazy val fanResources = Project(id = "fan-resources", base = file("fan-resources"))

lazy val `babymetal-site` = project
  .in(file("babymetal-site"))
  .settings(buildSettings)
  .settings(
    name := "babymetal-site",
    version := "0.1.0-SNAPSHOT",

    // 2.13 compatibility
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.4.3"
    ),
  )
  .dependsOn(fanResources)

lazy val root = (project in file("."))
  .aggregate(fanResources, `babymetal-site`)
  .settings(buildSettings)
  .settings(
    Compile / mainClass := Some("org.skyluc.babymetal_site.Main")
    // TODO: Remove
    // Compile / unmanagedResourceDirectories ++= Seq(
    //   baseDirectory.value / "data",
    //   baseDirectory.value / "static",
    //   baseDirectory.value / "static_pieces",
    // ),
  )
  .dependsOn(`babymetal-site`)
