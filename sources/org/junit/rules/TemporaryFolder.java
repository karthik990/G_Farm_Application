package org.junit.rules;

import java.io.File;
import java.io.IOException;

public class TemporaryFolder extends ExternalResource {
    private File folder;
    private final File parentFolder;

    public TemporaryFolder() {
        this(null);
    }

    public TemporaryFolder(File file) {
        this.parentFolder = file;
    }

    /* access modifiers changed from: protected */
    public void before() throws Throwable {
        create();
    }

    /* access modifiers changed from: protected */
    public void after() {
        delete();
    }

    public void create() throws IOException {
        this.folder = createTemporaryFolderIn(this.parentFolder);
    }

    public File newFile(String str) throws IOException {
        File file = new File(getRoot(), str);
        if (file.createNewFile()) {
            return file;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("a file with the name '");
        sb.append(str);
        sb.append("' already exists in the test folder");
        throw new IOException(sb.toString());
    }

    public File newFile() throws IOException {
        return File.createTempFile("junit", null, getRoot());
    }

    public File newFolder(String str) throws IOException {
        return newFolder(str);
    }

    public File newFolder(String... strArr) throws IOException {
        File root = getRoot();
        int i = 0;
        while (i < strArr.length) {
            String str = strArr[i];
            validateFolderName(str);
            File file = new File(root, str);
            if (file.mkdir() || !isLastElementInArray(i, strArr)) {
                i++;
                root = file;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("a folder with the name '");
                sb.append(str);
                sb.append("' already exists");
                throw new IOException(sb.toString());
            }
        }
        return root;
    }

    private void validateFolderName(String str) throws IOException {
        if (new File(str).getParent() != null) {
            throw new IOException("Folder name cannot consist of multiple path components separated by a file separator. Please use newFolder('MyParentFolder','MyFolder') to create hierarchies of folders");
        }
    }

    private boolean isLastElementInArray(int i, String[] strArr) {
        return i == strArr.length - 1;
    }

    public File newFolder() throws IOException {
        return createTemporaryFolderIn(getRoot());
    }

    private File createTemporaryFolderIn(File file) throws IOException {
        File createTempFile = File.createTempFile("junit", "", file);
        createTempFile.delete();
        createTempFile.mkdir();
        return createTempFile;
    }

    public File getRoot() {
        File file = this.folder;
        if (file != null) {
            return file;
        }
        throw new IllegalStateException("the temporary folder has not yet been created");
    }

    public void delete() {
        File file = this.folder;
        if (file != null) {
            recursiveDelete(file);
        }
    }

    private void recursiveDelete(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File recursiveDelete : listFiles) {
                recursiveDelete(recursiveDelete);
            }
        }
        file.delete();
    }
}
