
//@todo would like this in
//scalaVersion := "2.11.4"

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.0-M3")

addSbtPlugin("com.vmunier" % "sbt-play-scalajs" % "0.1.0")

//addSbtPlugin("com.lihaoyi" % "workbench" % "0.2.3")

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

resolvers  += "Online Play Repository" at "http://repo.typesafe.com/typesafe/simple/maven-releases/"

resolvers  += "Sonatype Artifacts" at "http://oss.sonatype.org/content/repositories/central/"

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

resolvers += Resolver.url(
  "bintray-sbt-play-scalajs",
  url("http://dl.bintray.com/content/vmunier/scalajs"))(
    Resolver.ivyStylePatterns)