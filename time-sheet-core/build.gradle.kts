plugins {
    id("java-application-conventions")
}

dependencies {
    implementation(project(":time-sheet-activity"))

    implementation("org.jgrapht:jgrapht-core:1.5.0")
    implementation("org.jgrapht:jgrapht-ext:1.5.0")
}

application {
    mainClass.set("com.github.verhagen.timesheet.App")
}
