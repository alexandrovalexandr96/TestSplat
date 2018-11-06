import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class Node {

	File myFile;
	ArrayList<Node> childFiles;
	boolean isDisaredFile;

	public Node(File file, String expansion, String searchText, boolean isDisaredFileBuf) {
		myFile = file;
		childFiles = new ArrayList<>();
		isDisaredFile = isDisaredFileBuf;

		// если это директория, то проходим всех его детей
		// если ребенок так же директория, то продолжаем рекурсивно проходить все
		// подпапки
		// если ребенок это файл и его расширение совпадает с заданным, то добавляем его
		// и все папки в которых он содержится в дерево.
		if (file.isDirectory()&!Thread.currentThread().isInterrupted())
			for (File buf : file.listFiles()) {
				System.out.println(buf);
				if (buf.isDirectory() & buf.listFiles() != null && buf.listFiles().length != 0) {
					Node nodebuf = new Node(buf, expansion, searchText, false);
					if (nodebuf.isDisaredFile) {
						this.isDisaredFile = true;
						childFiles.add(nodebuf);
					}
				} else if (buf.isFile() & (buf.getAbsolutePath()
						.substring(buf.getAbsolutePath().length() - expansion.length()).equals(expansion))) {
					if (searchText.equals("") || textSearch(buf, searchText)) {
						childFiles.add(new Node(buf, expansion, searchText, true));
						this.isDisaredFile = true;
					}
				}
			}
	}

	char[] str;
	Reader reader;

	private boolean textSearch(File bufFile, String searchText) {
		try {
			reader = new InputStreamReader(new FileInputStream(bufFile), "UTF-8");
			str = new char[(int) bufFile.length()];
			reader.read(str);
			String text = new String(str);
			for (int i = 0; i <= (text.length() - searchText.length()); i++) {
				if (text.substring(i, i + searchText.length()).equals(searchText))
					return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	public ArrayList<Node> getChild() {
		return childFiles;
	}

	public String getName() {
		return myFile.getAbsolutePath();
	}

	static Reader reader1;
	static char[] str1;

	//метод для тестов
	public static boolean textSearch1(File bufFile, String searchText) {
		try {
			reader1 = new InputStreamReader(new FileInputStream(bufFile), "utf-8");
			str1 = new char[(int) bufFile.length()];
			reader1.read(str1);
			String text = new String(str1);
			for (int i = 0; i <= (text.length() - searchText.length()); i++) {
				if (text.substring(i, i + searchText.length()).equals(searchText))
					return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
}
