plugins {
    java
    eclipse
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("org.apache.commons:commons-lang3:3.11")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("ch.qos.logback:logback-classic:1.2.3")

}

tasks.test {
    useJUnitPlatform()
}
