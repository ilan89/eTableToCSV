package seleniumJavaDemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class browserInvocation {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		WebDriver chrome = new ChromeDriver();
		chrome.get("https://www.premierleague.com/tables");
		WebElement tableElement = chrome.findElement(By.tagName("table"));
		
		// create empty table object and iterate through all rows of the found table element
		ArrayList<HashMap<String, WebElement>> userTable = new ArrayList<HashMap<String, WebElement>>();
		List<WebElement> rowElements = tableElement.findElements(By.xpath(".//tr"));

		// get column names of table from table headers
		ArrayList<String> columnNames = new ArrayList<String>();
		List<WebElement> headerElements = rowElements.get(0).findElements(By.xpath(".//th"));
		for (WebElement headerElement: headerElements) {
		  columnNames.add(headerElement.getText());
		}
		
		// iterate through all rows and add their content to table array
		for (WebElement rowElement: rowElements) {
		  HashMap<String, WebElement> row = new HashMap<String, WebElement>();
		  
		  // add table cells to current row
		  int columnIndex = 0;
		  List<WebElement> cellElements = rowElement.findElements(By.xpath(".//td"));
		  for (WebElement cellElement: cellElements) {
		    row.put(columnNames.get(columnIndex), cellElement);
		    columnIndex++;
		  }
		  if(row.size()<2)continue;
		  userTable.add(row);
		};
		
		// finally fetch the desired data
		WebElement cellInSecondRowFourthColumn = userTable.get(2).get("Position");
		System.out.println(cellInSecondRowFourthColumn.getText());
		
		String[] lines = new String[21];
		StringBuilder sb1 = new StringBuilder();
		for (String name : columnNames) {
			sb1.append(name);
			sb1.append(",");
		}
		lines[0] = sb1.toString();
		for(int i = 1; i<21; i++) {
			StringBuilder sb = new StringBuilder();
			for (String name : columnNames) {
				sb.append(userTable.get(i-1).get(name).getText()).append(",");
			}
			lines[i] = sb.toString();
		}
	    File csvOutputFile = new File("newFile.csv");
	    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {

	          for(String line : lines) {
	        	  pw.println(line);
	          }
	    }
	    
	    
		
	}

}
