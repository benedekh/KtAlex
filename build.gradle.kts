import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.serialization") version "2.0.10"
    id("io.gitlab.arturbosch.detekt") version ("1.23.1")
    id("jacoco")
    id("org.jetbrains.dokka") version "1.9.20"
    id("maven-publish")
    signing
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

group = "io.github.benedekh"
version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
}

val ktorVersion by extra { "2.3.3" }
val detektVersion by extra { "1.23.1" }
val kotestVersion by extra { "5.6.2" }

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.5.6")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
    testLogging {
        events("skipped", "failed")
        showStackTraces = true
        exceptionFormat = TestExceptionFormat.FULL
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.main.get().kotlin)
}
val javadocJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    archiveClassifier.set("javadoc")
    from(tasks.named("dokkaHtml"))
}

signing {
    val signingKey = (project.findProperty("GPG_SIGNING_KEY") ?: System.getenv("GPG_SIGNING_KEY")) as String?
    val signingPassphrase =
        (project.findProperty("GPG_SIGNING_PASSPHRASE") ?: System.getenv("GPG_SIGNING_PASSPHRASE")) as String?

    useInMemoryPgpKeys(signingKey, signingPassphrase)
    val extension = extensions.getByName("publishing") as PublishingExtension
    sign(extension.publications)
}

/**
 * publish to staging: ./gradlew publish
 *
 * publish release: ./gradlew publishAllPublicationsToSonatypeRepository closeAndReleaseSonatypeStagingRepository
 */

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = rootProject.name
            version = project.version.toString()

            from(components["kotlin"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            pom {
                name.set("KtAlex")
                description.set("A Kotlin library for OpenAlex.")
                url.set("https://github.com/benedekh/ktalex")
                licenses {
                    license {
                        name.set("MIT license")
                        url.set("https://opensource.org/license/mit/")
                    }
                }
                developers {
                    developer {
                        id.set("benedekh")
                        name.set("Benedek Horvath")
                        organization.set("GitHub (default)")
                        organizationUrl.set("https://www.github.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/benedekh/ktalex.git")
                    developerConnection.set("scm:git:ssh://github.com:benedekh/ktalex.git")
                    url.set("https://github.com/benedekh/ktalex/")
                }
            }
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))

            val ossrhUsername = (project.findProperty("OSSRH_USERNAME") ?: System.getenv("OSSRH_USERNAME")) as String?
            val ossrhPassword = (project.findProperty("OSSRH_PASSWORD") ?: System.getenv("OSSRH_PASSWORD")) as String?
            username.set(ossrhUsername)
            password.set(ossrhPassword)
        }
    }
}
