/*
 * Copyright 2020 Leonardo Colman Lopes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    `maven-publish`
    signing
    id("org.jetbrains.dokka") version "0.10.1"
    id("io.gitlab.arturbosch.detekt").version("1.10.0")
}

group = "br.com.colman.kotest.kotless"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    // Kotest
    api("io.kotest:kotest-assertions-core-jvm:4.3.0")
    api("io.kotest:kotest-framework-api:4.3.0")
    implementation("io.kotest:kotest-extensions:4.3.0")
    testImplementation("io.kotest:kotest-runner-junit5:4.3.0")
    
    // Kotless
    implementation("io.kotless:kotless-lang-local:0.1.6")
    testImplementation("io.kotless:kotless-lang:0.1.6")
    
    // Fuel
    testImplementation("com.github.kittinunf.fuel:fuel:2.3.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kotlin {
    explicitApi()
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    val javadoc = tasks["dokka"] as org.jetbrains.dokka.gradle.DokkaTask
    javadoc.outputFormat = "javadoc"
    javadoc.outputDirectory = "$buildDir/javadoc"
    dependsOn(javadoc)
    archiveClassifier.set("javadoc")
    from(javadoc.outputDirectory)
}

publishing {
    repositories {

        maven("https://oss.sonatype.org/service/local/staging/deploy/maven2") {
            credentials {
                username = System.getProperty("OSSRH_USERNAME")
                password = System.getProperty("OSSRH_PASSWORD")
            }
        }
    }

    publications {

        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(javadocJar.get())

            pom {
                name.set("KotestKotless")
                description.set("Kotest Kotless")
                url.set("https://www.github.com/LeoColman/kotest-kotless")


                scm {
                    connection.set("scm:git:http://www.github.com/LeoColman/kotest-kotless/")
                    developerConnection.set("scm:git:http://github.com/LeoColman/")
                    url.set("https://www.github.com/LeoColman/kotest-kotless")
                }

                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://opensource.org/licenses/Apache-2.0")
                    }
                }

                developers {
                    developer {
                        id.set("LeoColman")
                        name.set("Leonardo Colman Lopes")
                        email.set("leonardo.dev@colman.com.br")
                    }
                }
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}