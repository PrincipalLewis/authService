
lazy val root = (project in file("."))
  .enablePlugins(DockerPlugin).
  settings(
    name := "Authorization-Service",
    organization := "auth",
    version := "0.0.1",
    scalaVersion := "2.11.8",
    libraryDependencies ++= Seq(
      "org.scalikejdbc" %% "scalikejdbc"       % "2.4.2",
      "com.h2database"  %  "h2"                % "1.4.192",
      "ch.qos.logback"  %  "logback-classic"   % "1.1.7",
      "com.twitter"    %% "finagle-http"    % "6.35.0",
      "net.liftweb"    %% "lift-json"       % "2.6.2",
      "org.postgresql" % "postgresql" % "9.3-1102-jdbc41",
      "org.scalatest"  %% "scalatest"       % "2.2.4" % "test"
    )
  )