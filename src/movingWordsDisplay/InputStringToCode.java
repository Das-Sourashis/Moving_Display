/**
 * 
 */
package movingWordsDisplay;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Sourashis Das
 */

public class InputStringToCode {
	public String inputToCode(String inputstring) {
		String s = "l", s1, getString;
		char key;
		Map<Character, String> table = new HashMap<>();
		getString = inputstring;
		getString = getString + "     ";
		int i = 0, count = 0;

		try {
			File file = new File("asset/symbol_code.txt");
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				s = scanner.nextLine();
				key = s.charAt(0);
				s = s.substring(2);
				s1 = s + " 00";

				table.put(key, s1);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			return "Failed to open Symbol_Code file.";
		}

		try {
			FileWriter fileWriter = new FileWriter("asset/RAM_code.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write("v2.0 raw");
			bufferedWriter.newLine();

			while (i < getString.length()) {
				if (!table.containsKey(getString.charAt(i))) {
					bufferedWriter.newLine();
					bufferedWriter.close();
					String msg = getString.charAt(i) + " is not available. \n ERROR HAPPEN...";
					return msg;
				} else {
					s1 = table.get(getString.charAt(i));
					bufferedWriter.write(s1);
					bufferedWriter.newLine();
					count++;
				}
				i++;
			}

			count = count * 6;
			int total = (int) Math.pow(2, 17);
			total = total - count - 3;
			bufferedWriter.write(total + "*0");
			bufferedWriter.newLine();

			String hexCount = Integer.toHexString(count);
			int sz = hexCount.length();
			sz = 6 - sz;
			StringBuilder s2 = new StringBuilder();
			s2.append("0".repeat(Math.max(0, sz)));
			s2.append(hexCount);
			bufferedWriter.write(s2.substring(0, 2) + " " + s2.substring(2, 4) + " " + s2.substring(4, 6));
			bufferedWriter.newLine();

			bufferedWriter.close();
		} catch (IOException e) {
			return "Failed to open.";
		}

		try {
			Files.write(Paths.get("asset/input_text.txt"), inputstring.getBytes(), StandardOpenOption.CREATE,
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to write to file: " + "asset/input_text.txt BUT code is generated.");
		}
		return "SUCCESS";
	}
}
