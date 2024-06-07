package textventure.filereader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import textventure.randomizer.Randomizer;

public class FileReaderFieldDescription {
	public String getLine() {
		String filePath = "src/main/java/textventure/filereader/FieldDescriptions";
		List<String> lines = new ArrayList<>();
		Randomizer randomizer = new Randomizer();

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!lines.isEmpty()) {
			return lines.get(randomizer.GetRandomIntFromRange(0, lines.size()-1));
		} else {
			return "The file FieldDescriptions is empty or could not be read.";
		}
	}
}
