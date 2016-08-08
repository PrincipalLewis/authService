
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
      "com.typesafe" % "config" % "1.3.0",

      //DB
      "com.typesafe.slick"   %% "slick"              % "3.1.0",
      "com.github.tototoshi" %% "slick-joda-mapper"  % "2.1.0",
      "com.github.tminglei"  %% "slick-pg"           % "0.10.0",
      "com.zaxxer"           %  "HikariCP"           % "2.3.7",
      "org.postgresql"       %  "postgresql"         % "9.4-1201-jdbc41",


      "com.twitter" %% "finagle-core" % "6.33.0",

      //logs
      "ch.qos.logback" % "logback-classic" % "1.1.3",
      "org.slf4j"      % "slf4j-api"       % "1.7.21",
      "org.slf4j"      % "jul-to-slf4j"    % "1.7.21",
      "org.slf4j"      % "jcl-over-slf4j"  % "1.7.13",

      //tests
      "org.scalatest"  %% "scalatest"       % "2.2.4" % "test"
    )
  )