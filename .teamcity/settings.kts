import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.project
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.buildSteps.scriptStep
import jetbrains.buildServer.configs.kotlin.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

project {
    for (i in 1..100) {
        buildType(GenerateBuild(i))
    }
}

class GenerateBuild(private val index: Int) : BuildType({
    id("GeneratedBuild_$index")
    name = "Build #$index"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        scriptStep {
            name = "Print build number"
            scriptContent = "echo Hello from build $index"
        }
    }

    triggers {
        vcs {
            id = "vcsTrigger_$index"
        }
    }
})
