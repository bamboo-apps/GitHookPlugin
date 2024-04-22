/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package eu.bambooapps.gradle.plugin.githook

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * A simple functional test for the 'eu.bambooapps.gradle.plugin.githook' plugin.
 */
class GitHookPluginFunctionalTest {

    @field:TempDir
    lateinit var projectDir: File

    private val buildFile by lazy { projectDir.resolve("build.gradle") }
    private val gitHooksDir by lazy { projectDir.resolve("git-hooks") }
    private val gitHooksDestinationDir by lazy { projectDir.resolve(".git/hooks") }
    private val settingsFile by lazy { projectDir.resolve("settings.gradle") }

    @Test
    fun `can run task`() {
        gitHooksDir.mkdir()
        // Set up the test build
        settingsFile.writeText("")
        buildFile.writeText(
            """
            plugins {
                id('eu.bambooapps.gradle.plugin.githook')
            }
            
            gitHooks {
                gitHooksDirectory = project.layout.projectDirectory.dir("${gitHooksDir.path}")
                gitDirectory = project.layout.projectDirectory.dir(".git")
            }
        """.trimIndent()
        )

        // Run the build
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("installGitHooks")
        runner.withProjectDir(projectDir)
        val result = runner.build()

        // Verify the result
        assertEquals(
            TaskOutcome.SUCCESS,
            result.task(":installGitHooks")?.outcome
        )
        assertTrue {
            gitHooksDestinationDir.exists()
        }
    }

    @Test
    fun `use convention`() {
        gitHooksDir.mkdir()
        // Set up the test build
        settingsFile.writeText("")
        buildFile.writeText(
            """
            plugins {
                id('eu.bambooapps.gradle.plugin.githook')
            }
        """.trimIndent()
        )

        // Run the build
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("installGitHooks")
        runner.withProjectDir(projectDir)
        val result = runner.build()

        // Verify the result
        assertEquals(
            TaskOutcome.SUCCESS,
            result.task(":installGitHooks")?.outcome
        )
        assertTrue {
            gitHooksDestinationDir.exists()
        }
    }
}
