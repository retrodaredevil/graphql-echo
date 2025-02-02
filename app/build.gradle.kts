import net.ltgt.gradle.errorprone.errorprone
import net.ltgt.gradle.errorprone.CheckSeverity

plugins {
	// java-common-conventions applies java plugin, configs repositories, defines Java version and sets up JUnit
	id("buildlogic.java-common-conventions")

	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"

    alias(libs.plugins.errorprone.gradle)
}

group = "me.retrodaredevil"
version = "0.0.1-SNAPSHOT"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

    // https://github.com/uber/NullAway/releases
    errorprone(libs.errorprone.core)
    errorprone(libs.nullaway)

    implementation("org.jetbrains:annotations:26.0.2") // https://github.com/JetBrains/java-annotations/releases
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
        warn("PreferJavaTimeOverload") // SolarThing uses a lot of dateMillis, and many times it does not make sense to use java.time
        warn("RedundantOverride")
        warn("RedundantThrows")
        warn("ThrowSpecificExceptions") // TODO Something we need to work on in the SolarThing codebase
        warn("TransientMisuse")
        warn("TryWithResourcesVariable")
        warn("UnnecessarilyFullyQualified")
        warn("UnnecessaryAnonymousClass") // suggest
        warn("UnusedException")

        // Experimental Suggestions
        warn("ConstantField")
        warn("FieldCanBeFinal") // Useful on everything but :client module, because of the usages of @JsonProperty on mutable fields.
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
        warn("ReturnMissingNullable") // Useful onlyl if we become more religious about use of @Nullable
        warn("ReturnsNullCollection")
        warn("SwitchDefault")
        error("SymbolToString")
        warn("ThrowsUncheckedException") // we should be unchecked exceptions in JavaDoc instead
        warn("TryFailRefactoring")
        error("TypeToString")
//        warn("UngroupedOverloads") //
        warn("UnnecessaryBoxedAssignment")
        warn("UnnecessaryBoxedVariable")
        error("UnnecessaryStaticImport")
    }
}
