lazy val commonSettings = Seq(
  version in ThisBuild := "0.1.0",
  organization in ThisBuild := "com.github.hiro0107"
)

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    name := "excel-stream",
	scalaVersion := "2.12.4",
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
	resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases",
    libraryDependencies ++= Seq(
	  "org.apache.poi" % "poi-ooxml" % "3.16",
	  "com.novocode" % "junit-interface" % "0.11" % "test"
    ),
    publishMavenStyle := true,
    bintrayOrganization in bintray := None
  )
