name := "play_tp09"

version := "2.3.8"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

fork in run := true

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "javax.el" % "el-api" % "2.2",
  "org.glassfish.web" % "el-impl" % "2.2",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.8.Final",
  "mysql" % "mysql-connector-java" % "5.1.34"
)     
