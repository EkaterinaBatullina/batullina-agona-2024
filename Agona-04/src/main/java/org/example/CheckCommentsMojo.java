package org.example;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Mojo(name = "check-comments", defaultPhase = LifecyclePhase.VALIDATE, threadSafe = true)
public class CheckCommentsMojo extends AbstractMojo {

    @Parameter(property = "commentPattern", defaultValue = "TODO|FIXME", required = false)
    private String commentPattern;

    @Parameter(property = "taskPattern", defaultValue = "AGONA-\\d+", required = false)
    private String taskPattern;

    @Parameter(defaultValue = "${project.basedir}", required = false)
    private File projectDirectory;

    @Parameter(defaultValue = "${project}", required = false)
    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException {
        List<File> modifiedFiles = getModifiedFilesInBranch();
        for (File file : modifiedFiles) {
            checkFileForComments(file);
        }
    }

    private void checkFileForComments(File file) throws MojoExecutionException {
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.matches(".*\\b(%s)\\b.*".formatted(commentPattern))) {
                    if (!currentLine.matches(".*\\b(%s)\\b\\s%s\\b.*".formatted(commentPattern, taskPattern))) {
                        throw new MojoExecutionException("Некорректный комментарий в файле %s: %s".formatted(file.getName(), currentLine));
                    }
                }
            }
        } catch (IOException e) {
            throw new MojoExecutionException("Ошибка при чтении файла: %s".formatted(file.getAbsolutePath()), e);
        }
    }

    private List<File> getModifiedFilesInBranch() throws MojoExecutionException {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("git", "diff", "--name-only", "main");
            processBuilder.directory(projectDirectory);
            Process process = processBuilder.start();
            List<String> modifiedFileNames = new BufferedReader(new InputStreamReader(process.getInputStream()))
                    .lines()
                    .collect(Collectors.toList());
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new MojoExecutionException("Ошибка выполнения команды git diff.");
            }
            return modifiedFileNames.stream()
                    .map(File::new)
                    .collect(Collectors.toList());
        } catch (IOException | InterruptedException e) {
            throw new MojoExecutionException("Ошибка при получении списка измененных файлов.", e);
        }
    }

}
