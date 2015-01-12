
//@todo would like this in
//scalaVersion := "2.11.4"

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.0-M3")
//addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.6.0-M3")

addSbtPlugin("com.lihaoyi" % "utest-js-plugin" % "0.2.4")

//addSbtPlugin("com.lihaoyi" % "workbench" % "0.2.3")

resolvers += "spray repo" at "http://repo.spray.io"

resolvers  += "Online Play Repository" at "http://repo.typesafe.com/typesafe/simple/maven-releases/"

resolvers  += "Sonatype Artifacts" at "http://oss.sonatype.org/content/repositories/central/"

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

