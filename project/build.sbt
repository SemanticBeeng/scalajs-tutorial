addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.5.6")

addSbtPlugin("com.lihaoyi" % "utest-js-plugin" % "0.2.4")

resolvers += "spray repo" at "http://repo.spray.io"

resolvers  += "Online Play Repository" at
  "http://repo.typesafe.com/typesafe/simple/maven-releases/"

addSbtPlugin("com.lihaoyi" % "workbench" % "0.2.3")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.1")
