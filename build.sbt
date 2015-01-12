//import sbt._
//import Keys._

//import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import playscalajs.PlayScalaJS

lazy val rootProject = project.in(file(".")).
  aggregate(jvm, js, sharedProject)
lazy val sharedProject = project.in(file("shared"))

lazy val root = PlayScalaJS("jvm", "js", file("."), CrossType.Full).
  settings(
    normalizedName := "scala-tutorial",

    //version := "0.1-SNAPSHOT",

    scalaVersion := "2.11.2",

    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "upickle" % "0.2.5",           // @todo if using '%%%' it cannot resolve
      "com.lihaoyi" %% "autowire" % "0.2.3",          // @todo if using '%%%' it cannot resolve
      "com.lihaoyi" %%% "utest" % "0.2.5-M3" % "test"
    ),

    testFrameworks += new TestFramework("utest.runner.Framework")
  ).
  jvmSettings(
    name := "server",

    libraryDependencies ++= Seq(
      "io.spray" %% "spray-can" % "1.3.1",
      "io.spray" %% "spray-routing" % "1.3.1",
      "com.typesafe.akka" %% "akka-actor" % "2.3.2",
      //added this separately for server to highlight this dependency is weird
      "com.scalatags" %% "scalatags" % "0.4.2"
    )


  ).
  jsSettings(
    name := "client",

    persistLauncher := true,

    persistLauncher in Test := false,

    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.7.0",
      "com.scalatags" %% "scalatags" % "0.4.2" // @todo if using '%%%' it cannot resolve
    )
  )

// Only if you use IntelliJ: the shared project makes IntelliJ happy without using symlinks
lazy val shared = Project("shared", file("shared"))
lazy val jvm = root.jvm
lazy val js = root.js

jvm.dependsOn(shared)
js.dependsOn(shared)
