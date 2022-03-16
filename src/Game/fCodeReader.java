package Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class fCodeReader {
    protected float config_rate;

    public String readGenCode(String fileName) throws FileNotFoundException {
        StringBuilder str = new StringBuilder();
        try {
            File file = new File(fileName);
            Scanner scn = new Scanner(file);
            while (scn.hasNextLine()) {
                str.append(scn.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw e;
        }
        return str.toString();
    }

    public int[] readConfig(String fileName) throws FileNotFoundException {
        int[] arr = new int[13];
        int s = 0;
        try {
            File file = new File(fileName);
            Scanner scn = new Scanner(file);
            while (scn.hasNextLine()) {
                String[] str = scn.nextLine().split(" ");
                for (String value : str) {
                    if( value.matches("^\\d+\\.\\d+") ) {
                        config_rate = Float.parseFloat(value);
                        s++;
                    }
                    else
                        arr[s++] = Integer.parseInt(value);
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        }
        return arr;
    }
}
