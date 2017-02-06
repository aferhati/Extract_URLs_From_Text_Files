package net.codejava.io;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.StringTokenizer;
public class ReadWrite {
	public static void DeleteFiles(String input) throws IOException {
		File file = new File(input);
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				f.delete();
			}
		}
	}
	public static void main(String[] args) throws IOException {
		String target_dir = "./InputFile";
		File dir = new File(target_dir);
		File[] files = dir.listFiles();
		for (File f : files) {
			if (f.isFile()) {
				String line = null;
				try {
					BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
					while ((line = bufferedReader.readLine()) != null) {
						String message = line;
						String delim = "<";
						StringTokenizer st = new StringTokenizer(message, delim);
						while (st.hasMoreTokens()) {
							String token1 = st.nextToken();
							String token = token1.replace('"', '°');
							// System.out.println(token1);
							if (token.startsWith("a href=")) {
								String[] result = token.split(">");
								for (int x = 0; x < result.length; x++) {
									if (result[x].startsWith("a href=")) {
										String[] arr = result[x].substring(result[x].indexOf("°")).split("°");
										String a = arr[1].trim();
										try {
											FileWriter writer = new FileWriter("./OutputFile/MyFileOut.txt", true);
											writer.write(a + "\r\n");
											writer.close();
										} catch (IOException e) {
											e.printStackTrace();
										}
										File sourceDir1 = new File("./InputFile/");
										File destDir = new File("./ElaboratedFiles/");
										destDir.mkdirs();
										Path destPath = destDir.toPath();
										for (File sourceFile : sourceDir1.listFiles()) {
											Path sourcePath = sourceFile.toPath();
											Files.copy(sourcePath, destPath.resolve(sourcePath.getFileName()),
													StandardCopyOption.REPLACE_EXISTING);
										}
									}
								}
							}
						}
					}
					bufferedReader.close();
				} catch (FileNotFoundException ex) {
					System.out.println("Unable to open file '");
				} catch (IOException ex) {
					System.out.println("Error reading file '");
				}
			}
		}
		DeleteFiles("./InputFile");
	}
}
