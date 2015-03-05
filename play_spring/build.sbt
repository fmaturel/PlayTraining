name := "play_spring"

version := "2.3.8"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.springframework" % "spring-context" % "4.1.5.RELEASE",
  "javax.inject" % "javax.inject" % "1"
)
