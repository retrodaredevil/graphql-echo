import net.ltgt.gradle.errorprone.errorprone
import net.ltgt.gradle.errorprone.CheckSeverity
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	// java-common-conventions applies java plugin, configs repositories, defines Java version and sets up JUnit
	id("buildlogic.java-common-conventions")

    // https://spring.io/projects/spring-boot
	id("org.springframework.boot") version "3.4.5"
    // https://docs.spring.io/dependency-management-plugin/docs/current/reference/html/
	id("io.spring.dependency-management") version "1.1.7"

    alias(libs.plugins.errorprone.gradle)
}

group = "me.retrodaredevil"
version = "0.0.1-SNAPSHOT"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

    // https://github.com/graphql-java/graphql-java-extended-scalars/releases
    implementation("com.graphql-java:graphql-java-extended-scalars:22.0")

    // https://github.com/uber/NullAway/releases
    errorprone(libs.errorprone.core)
    errorprone(libs.nullaway)

    implementation("org.jetbrains:annotations:26.0.2") // https://github.com/JetBrains/java-annotations/releases
}

tasks.named<BootBuildImage>("bootBuildImage") {
    // The functionality bootBuildImage can be altered in a few different ways.
    //   First, you can use command line parameters to configure most of what you can in here.
    //   Because of the limitations of building for multiple platforms at once,
    //   we decide to leave the chosen platform up to the caller of this task
    // So, we expect platform and imageName properties should be present
    //   e.g. ./gradlew build -Pplatform=linux/amd64 -PimageName=...
    description = "Builds a docker image. Please provide the platform and imageName properties using -P flags to ./gradlew bootBuildImage"

    // https://docs.spring.io/spring-boot/reference/packaging/container-images/cloud-native-buildpacks.html
    // https://docs.spring.io/spring-boot/gradle-plugin/packaging-oci-image.html

    val desiredImageName = project.findProperty("imageName") as String?
    // https://docs.spring.io/spring-boot/gradle-plugin/packaging-oci-image.html#build-image.examples.custom-image-name
    imageName.set(desiredImageName)

    // NOTE: Cannot build for multiple platforms at once https://stackoverflow.com/a/79181507/5434860
    //   https://github.com/paketo-buildpacks/spring-boot/issues/491#issuecomment-2491569221
    val desiredPlatform = project.findProperty("platform") as String?
    if (desiredPlatform != null) {
        imagePlatform.set(desiredPlatform)
    }


    // Adding labels: https://stackoverflow.com/questions/66585031/configure-custom-container-image-label-using-cloud-native-build-packs-paketo-io
    //   TODO: add custom labels https://paketo.io/docs/howto/configuration/#applying-custom-labels
//    environment // BP_IMAGE_LABELS

    // We don't specify any registry options because we're not the ones handling pushing to a registry or tagging the image
//    docker {
//        publishRegistry {
//        }
//    }
}

tasks.withType<JavaCompile>().configureEach {
    options.errorprone {
        disableWarningsInGeneratedCode.set(true)

        check("NullAway", CheckSeverity.ERROR)
        option("NullAway:AnnotatedPackages", "me.retrodaredevil")

        // Experimental Errors
        enable("ClassName")
        warn("DepAnn")
        warn("EmptyIf")
        warn("InsecureCryptoUsage")
        // Experimental Warnings
        warn("AnnotationPosition")
        warn("ConstantPatternCompile")
        warn("DifferentNameButSame")
        error("EqualsBrokenForNull")
        warn("InconsistentOverloads")
        warn("InitializeInline")
        warn("InterfaceWithOnlyStatics")
        warn("InterruptedExceptionSwallowed")
        warn("Interruption")
        warn("MemberName")
        warn("NonCanonicalStaticMemberImport")
        warn("PreferJavaTimeOverload")
        warn("RedundantOverride")
        warn("RedundantThrows")
        warn("ThrowSpecificExceptions")
        warn("TransientMisuse")
        warn("TryWithResourcesVariable")
        warn("UnnecessarilyFullyQualified")
        warn("UnnecessaryAnonymousClass") // suggest
        warn("UnusedException")

        // Experimental Suggestions
        warn("ConstantField")
        warn("FieldCanBeFinal") // Consider disabling if we ever have annotated fields that need to be non-final
        warn("FieldCanBeLocal")
        warn("FieldCanBeStatic")
        warn("FieldMissingNullable") // There are many private fields where this is easy enough to tell
        warn("ForEachIterable")
        warn("LambdaFunctionalInterface")
//        warn("MethodCanBeStatic") // TODO Very, very useful, but I don't need this warning in tests
        error("MissingBraces")
        error("MixedArrayDimensions")
        error("MultiVariableDeclaration")
        error("MultipleTopLevelClasses")
        error("PackageLocation")
        warn("ParameterMissingNullable")
//        error("PrivateConstructorForUtilityClass") // not completely ideal for spring classes with static initialization
        warn("RemoveUnusedImports") // Only a warning because https://github.com/antlr/antlr4/issues/2568 and https://github.com/google/error-prone/issues/463
        warn("ReturnMissingNullable")
        warn("ReturnsNullCollection")
        warn("SwitchDefault")
        error("SymbolToString")
        warn("ThrowsUncheckedException") // we should have unchecked exceptions in JavaDoc instead
        warn("TryFailRefactoring")
        error("TypeToString")
//        warn("UngroupedOverloads") //
        warn("UnnecessaryBoxedAssignment")
        warn("UnnecessaryBoxedVariable")
        error("UnnecessaryStaticImport")
    }
}
