
lazy val root = (project in file("."))
  .enablePlugins(DockerPlugin).
  settings(
    name := "Authorization-Service",
    organization := "auth",
    version := "0.0.1",
    scalaVersion := "2.11.8",
    libraryDependencies ++= Seq(
      "com.twitter"    %% "finagle-http"    % "6.35.0",
      "net.liftweb"    %% "lift-json"       % "2.6.2",

      "org.scalatest"  %% "scalatest"       % "2.2.4" % "test"
    )
  )