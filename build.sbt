import Dep._
ThisBuild / scalaVersion := "2.13.10"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.digitalPlus"
ThisBuild / organizationName := "digitalPlus"

lazy val root = (project in file("."))
  .settings(
    name := "zio-akka-flight-booking",
    libraryDependencies ++= zioActor ++ zioConfig ++ zioTest ++ zioTapir ++ logs,
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
