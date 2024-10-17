# Upgrade Plugin

To upgrade the version of this plugin and publish a new version

1. Upgrade the version of the IntelliJ IDE.
2. Increment the `thisPluginVersion` in `gradle.properties`.
3. Update the `pluginUntilBuild` in `gradle.properties`.
   See: https://plugins.jetbrains.com/docs/intellij/build-number-ranges.html#platformVersions
4. Update the `platformVersion` in `gradle.properties`, only if you want to change the local version of Rider to be used
   for testing
5. Check, and update the `intellijPluginVersion`, and `gradleVersion` in `gradle.properties`
6. Rebuild gradle
7. Complete the release process in: [Versioning](CONTRIBUTING.md#Versioning)