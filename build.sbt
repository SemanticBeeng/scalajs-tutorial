import com.lihaoyi.workbench.Plugin._
import sbt.Keys._
import spray.revolver.RevolverPlugin.Revolver

import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._


scalaJSSettings

name := "Scala.js Tutorial"

scalaVersion := "2.11.2"

libraryDependencies += "org.scala-lang.modules.scalajs" %%% "scalajs-jquery" % "0.6"

libraryDependencies += "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6"

ScalaJSKeys.jsDependencies += scala.scalajs.sbtplugin.RuntimeDOM

skip in ScalaJSKeys.packageJSDependencies := false

// uTest settings
utest.jsrunner.Plugin.utestJsSettings

ScalaJSKeys.persistLauncher in Compile := true

ScalaJSKeys.persistLauncher in Test := false

val cross = new utest.jsrunner.JsCrossBuild(
  scalaVersion := "2.11.2",
  version := "0.1-SNAPSHOT",
  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "upickle" % "0.2.5",
    "com.lihaoyi" %%% "autowire" % "0.2.3"
  )
)

val client = cross.js.in(file("client"))
  .copy(id = "client")
  .settings(scalaJSSettings ++ workbenchSettings: _*)
  .settings(
    name := "Client",
    libraryDependencies ++= Seq(
      "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6",
      "com.scalatags" %%% "scalatags" % "0.4.2"
    ),
    bootSnippet := "ClientUsage().main();"
  )

val server = cross.jvm.in(file("server"))
  .copy(id = "server")
  .settings(Revolver.settings: _*)
  .settings(
    name := "Server",
    libraryDependencies ++= Seq(
      "io.spray" %% "spray-can" % "1.3.1",
      "io.spray" %% "spray-routing" % "1.3.1",
      "com.typesafe.akka" %% "akka-actor" % "2.3.2",
      "com.scalatags" %%% "scalatags" % "0.4.2" //added this separately for server to highlight this dependency is weird
    ),
    // SBT magic incantations
    (resources in Compile) += {
      (fastOptJS in(client, Compile)).value
      (artifactPath in(client, Compile, fastOptJS)).value
    }
  )
