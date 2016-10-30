name := """mighty-squidward"""
version := "0.1"
scalaVersion := "2.11.7"
lazy val akkaVersion = "2.4.11"
lazy val camelVersion = "2.13.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "org.iq80.leveldb" % "leveldb" % "0.7",
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "junit" % "junit" % "4.11" % "test",
  "com.novocode" % "junit-interface" % "0.9" % "test->default",
  "commons-io" % "commons-io" % "2.4" % "test",
  
  "com.typesafe.akka" % "akka-camel_2.11" % akkaVersion,
  "com.typesafe.akka" % "akka-http-core_2.11" % akkaVersion,
  "org.apache.camel" % "camel-core" % camelVersion,
  "org.apache.camel" % "camel-test" % camelVersion,
  "org.apache.camel" % "camel-jetty" % camelVersion,
  "org.apache.camel" % "camel-http" % camelVersion,
  "org.apache.camel" % "camel-mail" % camelVersion,
  "org.apache.camel" % "camel-http4" % camelVersion,
  "org.apache.camel" % "camel-ftp" % camelVersion,
  "org.apache.camel" % "camel-quartz2" % camelVersion,
  "org.slf4j" % "slf4j-api" % "1.7.2",
  "ch.qos.logback" % "logback-classic" % "1.0.7"
)

fork in Test := true
testOptions += Tests.Argument(TestFrameworks.JUnit, "-v", "-a")
compileOrder := CompileOrder.JavaThenScala


fork in run := true
