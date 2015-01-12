//import sbt._
//import sbt.Keys._
//import org.scalajs.sbtplugin.ScalaJSPlugin
//import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

////import scala.scalajs.sbtplugin.ScalaJSPlugin
//
///**
// * A standard way of defining cross-js/jvm builds. Defines two sub-projects
// * in the `jvm/` and `js/` folders, and a `shared/` folder which contains
// * any sources shared by both projects.
// *
// * @param sharedSettings Settings that will get applied to both projects,
// *                       not strictly necessary but pretty convenient.
// */
//
//class BootstrapCrossBuild(rootFolderName:String = ".",
//                          sharedSettings: Seq[Def.Setting[_]] = Nil,
//                          jvmFolderName:String = "jvm",
//                          jvmSettings: Seq[Def.Setting[_]] = Nil,
//                          jsFolderName:String = "js",
//                          jsSettings: Seq[Def.Setting[_]] = Nil){
//
////  val scalaVersion := "2.11.4"
////  val version := "0.1-SNAPSHOT"
//
//  val defaultSettings = Seq(
//    unmanagedSourceDirectories in Compile <+= baseDirectory(_ /  "shared" / "main" / "scala"),
//    unmanagedSourceDirectories in Test <+= baseDirectory(_ / "shared" / "test" / "scala")
//  )
//
//  lazy val js = project.in(file(jsFolderName))
//    .settings(addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.5.6"))
//    //.enablePlugins(ScalaJSPlugin)
//    .settings(jsSettings ++ Plugin.internal.utestJsSettings ++ sharedSettings ++ defaultSettings: _*)
//
//  lazy val jvm = project.in(file(jvmFolderName))
//    .settings(jvmSettings ++ Plugin.internal.utestJvmSettings ++ sharedSettings ++ defaultSettings:_*)
//
//  lazy val root = project.in(file(rootFolderName))
//    .aggregate(js, jvm)
//    .settings(
//      publish := (),
//      // dummy to make SBT shut up http://stackoverflow.com/a/18522706/871202
//      publishTo := Some(Resolver.file("Unused transient repository", file("target/unusedrepo"))),
//      crossScalaVersions := Seq("2.11.4", "2.10.4")
//    )
//}
//
//class A(a:String = "a", b:String = "b", c:String="c") {
//
//}
//
//
//class JsCrossBuild(sharedSettings: Def.Setting[_]*) extends BootstrapCrossBuild(
//  ".",
//  sharedSettings,
//  "server",
//  libraryDependencies += "com.lihaoyi" %% "utest" % Plugin.utestVersion % "test",
//  "client",
//  //libraryDependencies += "com.lihaoyi" %%%! "utest" % Plugin.utestVersion % "test"
//  libraryDependencies += "com.lihaoyi" %% "utest" % Plugin.utestVersion % "test"
//)
