package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TetrominoFileReader {
    private static TetrominoFileReader instance;
    private InputStream tetrominoStream;
    private BufferedReader bufferedReader;

    public static TetrominoFileReader GetInstance() {
        if (instance == null) {
            instance = new TetrominoFileReader();
        }
        return instance;
    }

    private TetrominoFileReader() {
        tetrominoStream = TetrominoFileReader.class.getResourceAsStream("/tetrominos.txt");
        try {
            if (tetrominoStream == null) {
                throw new FileNotFoundException("Plik z tetrominami nie został znaleziony");
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            System.exit(1);
        }
        bufferedReader = new BufferedReader(new InputStreamReader(tetrominoStream));
    }

    private void ToStartOfFile() {
        try {
            if (tetrominoStream != null) {
                bufferedReader.close();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        tetrominoStream = TetrominoFileReader.class.getResourceAsStream("/tetrominos.txt");
        bufferedReader = new BufferedReader(new InputStreamReader(tetrominoStream));
    }

    private List<Point3D> ReadOneTetrominoAsList() {
        List<Point3D> tetrominoList = new ArrayList<>();
        String line;
        try {
            if ((line = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile("\\((-?\\d+\\.?\\d*),(-?\\d+\\.?\\d*),(-?\\d+\\.?\\d*)\\)");
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    double x = Double.parseDouble(matcher.group(1));
                    double y = Double.parseDouble(matcher.group(2));
                    double z = Double.parseDouble(matcher.group(3));
                    tetrominoList.add(new Point3D(x, y, z));
                }
            }
        } catch (IOException | NumberFormatException exception) {
            exception.printStackTrace();
        }
        return tetrominoList;
    }

    public int CountTetrominos() {
        this.ToStartOfFile();
        int count = 0;
        while (!ReadOneTetrominoAsList().isEmpty()) {
            count++;
        }
        return count;
    }

    public List<Point3D> ReadTetrominoAsList(int fileIndex) {
        this.ToStartOfFile();
        List<Point3D> tetromino = new ArrayList<>();
        for (int i = 0; i <= fileIndex; i++) {
            tetromino = ReadOneTetrominoAsList();
            if (tetromino.isEmpty()) {
                return null;
            }
        }
        return tetromino;
    }
}
