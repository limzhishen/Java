

package POMSystem.Class;

import java.util.ArrayList;
import java.util.List;


// A class containing utility methods
public class Tools {
    
    // Method to display data in a formatted table-like view
    public static void View(List<String> item) {
        // Calculate the number of columns based on the first item's comma-separated values
        int columnNumber = item.get(0).split(",").length;
        int[] column = new int[columnNumber + 1]; // Add one column for index numbers
        List<String> newItemList = new ArrayList<>();

        // Add "No" to the first column and adjust column widths
        column[0] = Math.max(2, "No".length());

        for (int i = 0; i < item.size(); i++) {
            String[] line = item.get(i).split(",");
            for (int j = 0; j < columnNumber; j++) {
                
                column[j + 1] = Math.max(column[j + 1], line[j].length());
            }

            // Add index number to each row (except header)
            if (i != 0) {
                String[] newLine = new String[columnNumber + 1];
                newLine[0] = String.valueOf(i); // Row index (index + 1)
                System.arraycopy(line, 0, newLine, 1, columnNumber);
                newItemList.add(String.join(",", newLine));
            } else {
                newItemList.add("No," + item.get(i)); // Header
            }
        }

        for (int i = 0; i < newItemList.size(); i++) {
            String[] line = newItemList.get(i).split(",");
            for (int j = 0; j < columnNumber + 1; j++) {
                // Format each cell in the row to fit the determined column width
                int width = Math.max(column[j], line[j].length());
                column[j] = width;
                line[j] = String.format("%-" + width + "s", line[j]);
            }

            // Print the formatted row
            System.out.println(formatRow(String.join(",", line), column));
            if (i == 0) {
                System.out.println("-".repeat((columnNumber + 1) * 15));// Separator line
            }
        }
    }
    
    // Method to format a row with specified column widths
    private static String formatRow(String row, int[] columnWidths) {
    String[] columns = row.split(",");
    StringBuilder formattedRow = new StringBuilder();
    for (int i = 0; i < columns.length; i++) {
        // Format each cell in the row to fit the specified column width
        formattedRow.append(String.format("%-" + columnWidths[i] + "s", columns[i])).append("\t");
    }
    return formattedRow.toString();
    }
    
    // Method to generate a unique ID based on role and existing IDs    
    public static String id_Generate(String role,String filetype){
       
        FileHandling fh = new FileHandling();
        List<String> Id_detailList=fh.Readfile(filetype);// Read existing IDs
        List<Integer> Id_NumList= new ArrayList<>();
        Id_detailList.remove(0);// Remove header
        
        // Extract and store numerical parts of existing IDs based on role
        for (String i:Id_detailList){
            String[] detail=i.split(",");
            if ((detail[0].substring(0,detail[0].length()-3)).equals(role)){
                Id_NumList.add(Integer.valueOf(detail[0].substring(detail[0].length()-3,detail[0].length())));
            }
        }
        int num=1;
        // Find the smallest available number for a unique ID
        while(Id_NumList.contains(num)){
            num++;
        }
        
        String id;
        // Generate ID based on role and available number
        if (num<10){
            id=role+"00"+Integer.toString(num);
        }
        else if(num<100){
            id=role+"0"+Integer.toString(num);
        }
        else{
            id=role+Integer.toString(num);
        }
        return id;
    }
    
    // Method to delete the specified column position from the data item
    public static void removeColumns(List<String> item, int numColumnsToRemove) {
        for (int i = 0; i < item.size(); i++) {
            String[] line = item.get(i).split(",");
            if (line.length > numColumnsToRemove) {
                // Create a new line with fewer columns by copying the relevant ones
                String[] newLine = new String[line.length - numColumnsToRemove];
                System.arraycopy(line, 0, newLine, 0, newLine.length);
                // Replace the original line with the new one in the list
                item.set(i, String.join(",", newLine));
            }
        }
    }
}
