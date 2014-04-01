name := "play_tp10"

version := "1.18"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "javax.el" % "el-api" % "2.2",
  "org.glassfish.web" % "el-impl" % "2.2",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.4.Final",
  "mysql" % "mysql-connector-java" % "5.1.29",
  "org.mockito" % "mockito-all" % "1.9.5" % "test"
)     

play.Project.playJavaSettings
