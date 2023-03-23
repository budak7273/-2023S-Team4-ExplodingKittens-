# Developer Setup Instructions

## Steps

1. Install git bash (or a git client like sourcetree)
2. Clone the repo from <https://github.com/budak7273/2023S-Team4-ExplodingKittens.git>
3. Install IntelliJ via JetBrains toolbox
    - https://www.jetbrains.com/toolbox-app/
    - Installing via Toolbox avoids numerous unusual errors, consider reinstalling via Toolbox if you used another approach
    - Ultimate or Community editions both work
4. Configure Gradle's JDK to use Java 1.8
    - It doesn't always show up in this list, but try Shift+Shift and search `gradle jvm`
    - It should be in Build, Execution, Deployment > Build Tools > Gradle
5. Configure the Java SDK to be Java 1.8
    - Shift+Shift and search `Project Structure`
    - In the SDK field, select any Java 1.8 variant, use Add SDK if needed to download one
6. Manually install jacoco `0.7.5.<anything is fine here>`
    - Shift+Shift and search `Project Structure`
    - Switch to the Libraries tab and press the small plus on the left side
    - Select "From Maven..."
    - TODO move this to gradle so it doesn't have to be done locally
7. Run the Gradle build > build task
    - It should succeed, otherwise try the troubleshooting ideas below
8. Run the Gradle verification > test task
    - Click the ok prompts on any dialog boxes that appear
    - It should succeed, otherwise try the troubleshooting ideas below
    - Note - the shuffle test can randomly fail TODO on trello, fix it

## Troubleshooting

If you get:
- `Invalid Gradle JDK configuration found. There is no bin/java`
    - Go to the Gradle settings again and have IntelliJ download a version of 1.8 for you (even if you already have one)
- `Caused by: java.lang.NoSuchFieldException: $jacocoAccess`
    - Your Java SDK is the wrong version, go back to that step
    - Make sure you have the Jacoco maven library added
