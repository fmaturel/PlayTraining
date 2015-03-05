name := "play_tp03"

version := "2.3.8"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

fork in run := true