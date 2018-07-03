package com.xx.filewatch;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FileWatch {
    public static void main(String[] a) {

        final Path path = Paths.get("G:\\logs");

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            // ��path·�������ļ��۲����
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);
            while (true) {
                final WatchKey key = watchService.take();

                for (WatchEvent<?> watchEvent : key.pollEvents()) {

                    final WatchEvent.Kind<?> kind = watchEvent.kind();

                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }
                    // �����¼�
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        System.out.println("[�½�]");
                    }
                    // �޸��¼�
                    if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        System.out.println("�޸�]");
                    }
                    // ɾ���¼�
                    if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        System.out.println("[ɾ��]");
                    }
                    // get the filename for the event
                    final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
                    final Path filename = watchEventPath.context();
                    // print it out
                    System.out.println(kind + " -> " + filename);

                }
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }

        } catch (IOException | InterruptedException ex) {
            System.err.println(ex);
        }

    }
}

