name := "play_spring"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.springframework" % "spring-context" % "4.0.3.RELEASE",
  "javax.inject" % "javax.inject" % "1"
)     

play.Project.playJavaSettings
