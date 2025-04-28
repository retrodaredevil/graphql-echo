plugins {
    // Apply the java Plugin to add support for Java.
    java

    // NOTE: Due to limitations with convention plugins, you cannot use version catalogs here.
    //   More information: https://discuss.gradle.org/t/using-version-catalog-from-buildsrc-buildlogic-xyz-common-conventions-scripts/48534/3

    // TODO configure errorprone and NullAway here
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
//    constraints {
//        // Define dependency versions as constraints
//        implementation("org.apache.commons:commons-text:1.12.0")
//    }

    // Use JUnit Jupiter for testing. https://junit.org/junit5/
    testImplementation("org.junit.jupiter:junit-jupiter:5.12.2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
