name := "play_tp07"

version := "1.18"

libraryDependencies ++= Seq(
  "javax.el" % "el-api" % "2.2",
  "org.glassfish.web" % "el-impl" % "2.2"
)

play.Project.playJavaSettings
