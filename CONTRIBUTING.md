# Before you contribute

To contribute, use [GitHub Pull Requests](https://github.com/benedekh/ktalex/pulls) (PRs), from your own fork.

Also, make sure you have set up your Git authorship correctly:

```
git config --global user.name "Your Full Name"
git config --global user.email your.email@example.com
```

If you use different computers to contribute, please make sure the name is the same on all your computers.

We use this information to acknowledge your contributions in release announcements.

# Code reviews

All submissions need to be reviewed before being merged. When you are ready with your contributions, open a Pull Request, assign the PR to yourself and add one of the project members (or [benedekh](https://github.com/benedekh)) as a reviewer. Make sure that the CI build is running without errors. Finally, fix the findings of the reviewer if there are any.

# Setup for development

## Recommended setup

1. Install Git and configure your GitHub access.
2. Install a Java SDK (e.g. [Eclipse Temurin](https://adoptium.net/temurin/releases)) with compliance level Java 11 at least.
   - Set the `JAVA_HOME` environment variable to the installation directory of the Java SDK.
3. Install [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (Community Edition is enough).
4. Install the [detekt plugin](https://plugins.jetbrains.com/plugin/10761-detekt) in IntelliJ.
5. Make sure to disable the grouped imports (e.g. `import java.util.*`) in IntelliJ:
   1. Go to `Preferences -> Editor -> Code Style -> Java -> Imports` and:
      1. set `Use single name import` to `Always`.
      2. set `Class count to use import with '*'` to `99999`.
      3. set `Names count to use static import with '*'` to `99999`.
      4. unselect all packages in the `Packages to Use Import with '*'` list.
      5. unselect all packages in the `Import Layout` list.
   2. Go to `Preferences -> Editor -> Code Style -> Java -> Imports` and:
      1. select `Use single name import` at `Top-Level Symbols`.
      2. select `Use single name import` at `Java Statics and Enum Members`.
      3. unselect all packages in the `Packages to Use Import with '*'` list.
      4. unselect all packages in the `Import Layout` list.
6. Clone the repository and open it in IntelliJ.
7. Make sure to use the Java SDK you installed in step 2. Set it in `File -> Project Structure -> Project -> Project SDK`.
8. Make sure to use Java 11 as the language level. Set it in `File -> Project Structure -> Project -> Project language level`.
9. Develop your code.
10. Build and test the project with `./gradlew build` (or `gradlew.bat build` on Windows).
11. Fix any findings of the static code analysis tool (detekt).
12. Fix any failing tests.

## Alternative setup

1. Install Git and configure your GitHub access.
2. Install a Java SDK (e.g. [Eclipse Temurin](https://adoptium.net/temurin/releases)) with compliance level Java 11 at least.
    - Set the `JAVA_HOME` environment variable to the installation directory of the Java SDK.
3. Install [Gradle](https://gradle.org/install/).
4. Clone the repository and open it in your favourite IDE.
5. Develop your code.
6. Build and test the project with `./gradlew build` (or `gradlew.bat build` on Windows).
7. Fix any findings of the static code analysis tool (detekt). 
8. Fix any failing tests.