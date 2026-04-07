package com.patterns.composite.isolation;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for Exercise 1: File System Composite
 */
class IsolationExerciseTest {

    @Test
    void file_returnsSize() {
        File file = new File("document.txt", 1024);

        assertThat(file.getSize()).isEqualTo(1024);
    }

    @Test
    void file_hasName() {
        File file = new File("readme.md", 2048);

        assertThat(file.getName()).isEqualTo("readme.md");
    }

    @Test
    void folder_startsEmpty() {
        Folder folder = new Folder("docs");

        assertThat(folder.getSize()).isEqualTo(0);
        assertThat(folder.getChildren()).isEmpty();
    }

    @Test
    void folder_addsFiles() {
        Folder folder = new Folder("docs");
        File file1 = new File("file1.txt", 100);
        File file2 = new File("file2.txt", 200);

        folder.add(file1);
        folder.add(file2);

        assertThat(folder.getChildren()).hasSize(2);
        assertThat(folder.getChildren()).contains(file1, file2);
    }

    @Test
    void folder_removesFiles() {
        Folder folder = new Folder("docs");
        File file = new File("temp.txt", 50);

        folder.add(file);
        folder.remove(file);

        assertThat(folder.getChildren()).isEmpty();
    }

    @Test
    void folder_calculatesSizeFromFiles() {
        Folder folder = new Folder("docs");
        folder.add(new File("file1.txt", 100));
        folder.add(new File("file2.txt", 200));
        folder.add(new File("file3.txt", 300));

        assertThat(folder.getSize()).isEqualTo(600);
    }

    @Test
    void folder_containsSubfolders() {
        Folder root = new Folder("root");
        Folder subfolder = new Folder("sub");
        File file = new File("data.txt", 500);

        subfolder.add(file);
        root.add(subfolder);

        assertThat(root.getChildren()).contains(subfolder);
        assertThat(root.getSize()).isEqualTo(500);
    }

    @Test
    void nestedFolders_calculateSizeRecursively() {
        Folder root = new Folder("root");
        Folder docs = new Folder("docs");
        Folder images = new Folder("images");

        docs.add(new File("doc1.txt", 100));
        docs.add(new File("doc2.txt", 200));

        images.add(new File("img1.png", 5000));
        images.add(new File("img2.jpg", 3000));

        root.add(docs);
        root.add(images);
        root.add(new File("readme.txt", 50));

        // Total: 100 + 200 + 5000 + 3000 + 50 = 8350
        assertThat(root.getSize()).isEqualTo(8350);
    }

    @Test
    void deeplyNestedFolders_calculateCorrectly() {
        Folder root = new Folder("root");
        Folder level1 = new Folder("level1");
        Folder level2 = new Folder("level2");

        level2.add(new File("deep.txt", 999));
        level1.add(level2);
        level1.add(new File("mid.txt", 111));
        root.add(level1);
        root.add(new File("top.txt", 10));

        assertThat(root.getSize()).isEqualTo(1120);
    }

    @Test
    void file_printsCorrectly() {
        ByteArrayOutputStream output = captureSystemOut();
        File file = new File("test.txt", 2048);

        file.print(0);

        assertThat(output.toString()).contains("test.txt");
        assertThat(output.toString()).contains("2048");
    }

    @Test
    void folder_printsHierarchy() {
        ByteArrayOutputStream output = captureSystemOut();
        Folder root = new Folder("root");
        root.add(new File("file1.txt", 100));
        root.add(new File("file2.txt", 200));

        root.print(0);

        String result = output.toString();
        assertThat(result).contains("[root]");
        assertThat(result).contains("file1.txt");
        assertThat(result).contains("file2.txt");
    }

    @Test
    void nestedFolders_printWithIndentation() {
        ByteArrayOutputStream output = captureSystemOut();
        Folder root = new Folder("root");
        Folder sub = new Folder("subfolder");
        sub.add(new File("nested.txt", 50));
        root.add(sub);
        root.add(new File("top.txt", 10));

        root.print(0);

        String result = output.toString();
        assertThat(result).contains("[root]");
        assertThat(result).contains("[subfolder]");
        assertThat(result).contains("nested.txt");
        assertThat(result).contains("top.txt");
    }

    private ByteArrayOutputStream captureSystemOut() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        return outputStream;
    }
}
