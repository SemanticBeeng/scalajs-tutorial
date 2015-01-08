import sbt.Keys._
import sbt._

scalaVersion := "2.11.4"

//addSbtPlugin("com.lihaoyi" % "utest-js-plugin" % "0.2.4")
//
//addSbtPlugin("com.lihaoyi" % "workbench" % "0.2.3")
//
//addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.1")


resolvers += Resolver.url("scala-js-releases",
  url("http://dl.bintray.com/scala-js/scala-js-releases/"))(
    Resolver.ivyStylePatterns)

resolvers += "spray repo" at "http://repo.spray.io"

resolvers  += "Online Play Repository" at
  "http://repo.typesafe.com/typesafe/simple/maven-releases/"

//libraryDependencies += "org.scala-lang.modules.scalajs" %%% "scalajs-jquery" % "0.6"
//
//libraryDependencies += "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6"

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.0-M3")

addSbtPlugin("com.lihaoyi" % "utest-js-plugin" % "0.2.4")

//addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.6.0-M3")

addSbtPlugin("com.lihaoyi" % "workbench" % "0.2.3")
