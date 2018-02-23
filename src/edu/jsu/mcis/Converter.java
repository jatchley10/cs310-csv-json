package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and other whitespace
        have been added for clarity).  Note the curly braces, square brackets, and double-quotes!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160","111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            JSONObject jsonObject = new JSONObject();
            
            // INSERT YOUR CODE HERE
            //Getting the headings of the columns
            String[] cols = iterator.next();
            
            //Adding column names to JSONARRAY
            JSONArray colHeadings = new JSONArray();
            for(int i = 0; i < cols.length; ++i){
                colHeadings.add(cols[i]);
            }
            
           
            
     
            
            //Getting rowheaders
            JSONArray rowHeadings = new JSONArray();
            for(int i = 1; i < full.size();++i){
                rowHeadings.add(full.get(i)[0]);
                
            }
            
            //Getting data values
            JSONArray data = new JSONArray();
            for(int i = 1; i < full.size();++i){
                data.add(Arrays.toString(Arrays.copyOfRange(full.get(i), 1, full.get(i).length)));
            }
            
            
            jsonObject.put("colHeaders", colHeadings);
            jsonObject.put("rowHeaders",rowHeadings);
            jsonObject.put("data", data);
            
            
            
            //adding the columns to the final product
            
            results = "{";
            //adding the row part to the final product
            results += "\"rowHeaders\":" + jsonObject.get("rowHeaders") + ",";
            
            //adding the data part
            //Had to remove spaces within the array that caused my tests to fail
            results += "\"data\":" + data.toString().replaceAll("\"", "").replaceAll("\\s", "");
             
                
            
            results += ",\"colHeaders\":" + jsonObject.get("colHeaders");
            
            results += "}";
            
            
           
            
            
            
        }
        
        catch(IOException e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            // INSERT YOUR CODE HERE
            JSONArray colHeaders = (JSONArray) jsonObject.get("colHeaders");
            JSONArray rowHeaders = (JSONArray) jsonObject.get("rowHeaders");
            JSONArray data = (JSONArray) jsonObject.get("data");
            
            
            //Adding the columns to the string with quotes
            for(int i = 0; i < colHeaders.size();++i){
                if(i != colHeaders.size()-1){
                    results += "\""+ colHeaders.get(i)+ "\""+",";
                }
                else{
                    results+= "\""+ colHeaders.get(i)+ "\"";
                }
                
            }
            results += "\n";
            
            
            //Combining Rows and Data
            
            for(int i = 0; i < rowHeaders.size();++i){
                ArrayList combine = new ArrayList<String>();
                String row = "\"" + rowHeaders.get(i) + "\"";
                combine.add(row);
                JSONArray sub = (JSONArray) data.get(i);
                
                for(int j = 0; j < sub.size();++j){
                    String indData = "\"" + sub.get(j) + "\"";
                    combine.add(indData);
                }
                
                results += combine.toString().replace("[", "").replace("]", "").replace(", ", ",") + "\n";
                
               
                
            }
            
            
            
        }
        
        catch(ParseException e) { return e.toString(); }
        
        return results.trim();
        
    }
	
}