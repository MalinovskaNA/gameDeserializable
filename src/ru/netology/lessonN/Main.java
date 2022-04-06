package ru.netology.lessonN;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        File dirSavegames = new File("D://Games//savegames//new");
        File savezipFile = new File("D://Games//savegames//savezip.zip");

        File save1 = new File(dirSavegames, "save1.dat");
        File save2 = new File(dirSavegames, "save2.dat");
        File save3 = new File(dirSavegames, "save3.dat");

        if (dirSavegames.mkdir()) {
            System.out.println("Каталог создан");
        }
        openZip(String.valueOf(savezipFile), dirSavegames);

        for (File item : dirSavegames.listFiles()) {
            System.out.println(openProgress(String.valueOf(item)));
        }

    }

    public static void openZip(String zipPath, File dirPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(dirPath + "//" + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String path) {
        GameProgress gameProgress = null;
        // откроем входной поток для чтения файла
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            // десериализуем объект и скастим его в класс
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }

}
