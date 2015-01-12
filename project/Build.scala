
//import com.lihaoyi.workbench.Plugin._
import sbt.Keys._
import sbt._
import utest.jsrunner.Plugin

import scala.scalajs.sbtplugin.ScalaJSPlugin
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

/**
 * A standard way of defining cross-js/jvm builds. Defines two sub-projects
 * in the `jvm/` and `js/` folders, and a `shared/` folder which contains
 * any sources shared by both projects.
 *
 * @param sharedSettings Settings that will get applied to both projects,
 *                       not strictly necessary but pretty convenient.
 */

class BootstrapCrossBuild(rootFolderName: String = ".",
                          sharedSettings: Seq[Def.Setting[_]] = Nil,
                          jvmFolderName: String = "jvm",
                          jvmSettings: Seq[Def.Setting[_]] = Nil,
                          jsFolderName: String = "js",
                          jsSettings: Seq[Def.Setting[_]] = Nil) {

  val defaultSettings = Seq(
    unmanagedSourceDirectories in Compile <+= baseDirectory(_ / "shared" / "main" / "scala"),
    unmanagedSourceDirectories in Test <+= baseDirectory(_ / "shared" / "test" / "scala")
  )

  lazy val js = project.in(file(jsFolderName))
    .settings(
      //addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.5.6"))
      addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.0-M3"))
    //.enablePlugins(ScalaJSPlugin)
    .settings(jsSettings ++ Plugin.internal.utestJsSettings ++ sharedSettings ++ defaultSettings: _*)

  lazy val jvm = project.in(file(jvmFolderName))
    .settings(jvmSettings ++ Plugin.internal.utestJvmSettings ++ sharedSettings ++ defaultSettings: _*)

  lazy val root = project.in(file(rootFolderName))
    .aggregate(js, jvm)
    .settings(
      publish :=(),
      // dummy to make SBT shut up http://stackoverflow.com/a/18522706/871202
      publishTo := Some(Resolver.file("Unused transient repository", file("target/unusedrepo"))),
      crossScalaVersions := Seq("2.11.4", "2.10.4")
    )
}


//class JsCrossBuild(sharedSettings: Def.Setting[_]*) extends BootstrapCrossBuild(
//  ".",
//  sharedSettings,
//  "server",
//  libraryDependencies += "com.lihaoyi" %% "utest" % Plugin.utestVersion % "test",
//  "client",
//  libraryDependencies += "com.lihaoyi" %%%! "utest" % Plugin.utestVersion % "test"
//)

/**
 * ---------------------------------------------------
 */
object Build extends sbt.Build {

  val cross = new BootstrapCrossBuild(
    ".",
    sharedSettings,
    "server",
    libraryDependencies += "com.lihaoyi" %% "utest" % Plugin.utestVersion % "test",
    "client",
    libraryDependencies += "com.lihaoyi" %%%! "utest" % Plugin.utestVersion % "test"
  )

  val client = cross.js
    .copy(id = "client")
    //.settings(scalaJSSettings ++ workbenchSettings: _*)
    .settings(scalaJSSettings)
    .settings(
      name := "Client",
      scalaJSStage in Test := FastOptStage,
      libraryDependencies ++= Seq(
        "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6",
        "com.scalatags" %%% "scalatags" % "0.4.2"
      ),
      bootSnippet := "ClientUsage().main();"
    )

  val server = cross.jvm
    .copy(id = "server")
    .settings(settings)
    .settings(
      name := "Server",
      libraryDependencies ++= Seq(
        "io.spray" %% "spray-can" % "1.3.1",
        "io.spray" %% "spray-routing" % "1.3.1",
        "com.typesafe.akka" %% "akka-actor" % "2.3.2",
        //added this separately for server to highlight this dependency is weird
        "com.scalatags" %%% "scalatags" % "0.4.2"
      ),
      // SBT magic incantations
      (resources in Compile) += {
        (fastOptJS in(client, Compile)).value
        (artifactPath in(client, Compile, fastOptJS)).value
      }
    )

  lazy val sharedSettings = Seq(
    organization := "SemanticBeeng",
    version := "1.0",
    scalaVersion := "2.11.4",
    // Sonatype2
    publishArtifact in Test := false,
    publishTo := Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2"),
    libraryDependencies ++= Seq(
      "com.lihaoyi" %%% "upickle" % "0.2.5",
      "com.lihaoyi" %%% "autowire" % "0.2.3")
  )

}