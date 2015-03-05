name := "play_tp07"

version := "2.3.8"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

fork in run := true

libraryDependencies ++= Seq(
  "javax.el" % "el-api" % "2.2",
  "org.glassfish.web" % "el-impl" % "2.2"
)
