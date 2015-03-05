name := "play_tp16"

version := "2.3.8"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

fork in run := true

libraryDependencies ++= Seq(
  filters,
  cache,
  javaJdbc,
  javaJpa,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "javax.el" % "el-api" % "2.2",
  "org.glassfish.web" % "el-impl" % "2.2",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.8.Final",
  "mysql" % "mysql-connector-java" % "5.1.34",
  "org.mockito" % "mockito-all" % "1.9.5" % "test",
  "org.powermock" % "powermock-module-junit4" % "1.5.4" % "test",
  "org.powermock" % "powermock-api-mockito" % "1.5.4" % "test"
)
