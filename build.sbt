name := "SOCAssignment4"

version := "1.0"
      
lazy val `socassignment4` = (project in file(".")).enablePlugins(PlayJava,PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

resolvers ++= Seq("Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/")

scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice , javaWs, ws, "javax.json" % "javax.json-api" % "1.1.4", "mysql" % "mysql-connector-java" % "8.0.11")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      