scalaVersion := "2.12.3"

name := "excel-stream"

organization := "com.github.hiro0107"

version := "0.1.0"

libraryDependencies ++= List(
  "org.apache.poi" % "poi-ooxml" % "3.16",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)
