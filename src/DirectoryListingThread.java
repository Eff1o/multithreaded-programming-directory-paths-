import java.io.File;

public class DirectoryListingThread extends Thread {
    private String directoryPath;

    public DirectoryListingThread(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void run() {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory: " + directoryPath);
            return;
        }

        System.out.println("Listing files in directory: " + directoryPath);

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                } else {
                    System.out.println("File: " + file.getName());
                }
            }
        }

        long totalSize = calculateDirectorySize(directory);
        System.out.println("Total size of files in directory: " + directoryPath + " is " + formatSize(totalSize));
    }

    private long calculateDirectorySize(File directory) {
        long size = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    size += calculateDirectorySize(file);
                } else {
                    size += file.length();
                }
            }
        }
        return size;
    }

    private String formatSize(long size) {
        final long kiloBytes = 1024;
        final long megaBytes = kiloBytes * 1024;
        final long gigaBytes = megaBytes * 1024;

        if (size >= gigaBytes) {
            return String.format("%.2f GB", (double) size / gigaBytes);
        } else if (size >= megaBytes) {
            return String.format("%.2f MB", (double) size / megaBytes);
        } else if (size >= kiloBytes) {
            return String.format("%.2f KB", (double) size / kiloBytes);
        } else {
            return size + " bytes";
        }
    }

    public static void main(String[] args) {
        String[] directoryPaths = {
                "C:\\Users\\stasi\\Desktop\\directory1",
                "C:\\Users\\stasi\\Desktop\\directory2",
                "C:\\Users\\stasi\\Desktop\\directory3"
        };

        for (String directoryPath : directoryPaths) {
            Thread thread = new DirectoryListingThread(directoryPath);
            thread.start();
        }
    }
}
