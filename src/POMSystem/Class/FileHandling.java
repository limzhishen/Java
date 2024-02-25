package POMSystem.Class;


import java.io.*;
import java.util.List;
import java.util.ArrayList;


public class FileHandling extends FileLocation{

    public List<String> Readfile(String Filetype){
        Filepath filepath=Filepath.valueOf(Filetype);
        List<String> lines= new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader(filepath.getPath()))){
            String lineString;
            while((lineString=br.readLine())!=null){
                lines.add(lineString);
                
            }
        }
        catch(IOException Ex){
            System.out.println(Ex);
        }
        return lines;
    }
    
    public boolean WriteFile(String SavesStrings,String Filetype){
        try (PrintWriter pw = new PrintWriter(new FileWriter(Filepath.valueOf(Filetype).getPath(),true))) {
            pw.println(SavesStrings);
            
        }
        catch(IOException Ex){
            System.out.println("Document cannot be written in successfuly");
            return false;
        } 
        
        return true;
        
    }
    public boolean EditFile(String newStrings,String Filetype){
        List<String> contents= Readfile(Filetype);
        String[] editstring=newStrings.split(",");
        int i=0;
        for (String content :contents){//Find line number
            String[] detail=content.split(",");
            if (detail[0].equals(editstring[0])){
                contents.set(i, newStrings);
                break;
            }
            i++;
        }
        try(PrintWriter pw= new PrintWriter(Filepath.valueOf(Filetype).getPath())){
            for (String line:contents){
                pw.println(line);
            }
        }
        catch(Exception ex){
            System.out.println("Document cannot be Edit");
            return false;
        }
        return true;
    }
    public boolean DeleteFilecontent(String deleteid,String Filetype){
        List<String> contents= Readfile(Filetype);
        int i=0;
        for (String content :contents){//Find line number
            String[] detail=content.split(",");
            if (detail[0].equals(deleteid)){
                contents.remove(i);
                break;
            }
            i++;
        }
        try(PrintWriter pw= new PrintWriter(Filepath.valueOf(Filetype).getPath())){
            for (String line:contents){
                pw.println(line);
            }
        }
        catch(Exception ex){
            System.out.println("Document cannot be Edit");
            return false;
        }
        return true;
    }
    public boolean EditSupplieritemFile(String EdiString,String Filetype){
        List<String> contents= Readfile(Filetype);
        String[] matchString=EdiString.split(",");
        int i=0;
        for (String content :contents){//Find line number
           String[] detail=content.split(",");
           if (detail[0].equals(matchString[0])&&detail[1].equals(matchString[1])){
               contents.set(i, EdiString);
               break;
           }
           i++;
        }
        try(PrintWriter pw= new PrintWriter(Filepath.valueOf(Filetype).getPath())){
            for (String line:contents){
                pw.println(line);
            }
        }
        catch(Exception ex){
            System.out.println("Document cannot be Edit");
            return false;
        }
        return true;
    }
    public boolean DeleteSupplierItemContent(String deleteString,String Filetype){
        List<String> contents= Readfile(Filetype);
        int i=0;
        String[] matchStrings=deleteString.split(",");
        for (String content :contents){//Find line number
            String[] detail=content.split(",");
            if (detail[0].equals(matchStrings[0])&&detail[1].equals(matchStrings[1])){
                contents.remove(i);
                break;
            }
            i++;
        }
        try(PrintWriter pw= new PrintWriter(Filepath.valueOf(Filetype).getPath())){
            for (String line:contents){
                pw.println(line);
            }
        }
        catch(Exception ex){
            System.out.println("Document cannot be Edit");
            return false;
        }
        return true;
    }
    public boolean EditPR(String editString,String Filetype){
         List<String> contents= Readfile(Filetype);
        String[] matchString=editString.split(",");
        int i=0;
        for (String content :contents){//Find line number
           String[] detail=content.split(",");
           if (detail[0].equals(matchString[0])&&detail[3].equals(matchString[3])){
               contents.set(i, editString);
               break;
           }
           i++;
        }
        try(PrintWriter pw= new PrintWriter(Filepath.valueOf(Filetype).getPath())){
            for (String line:contents){
                pw.println(line);
            }
        }
        catch(Exception ex){
            System.out.println("Document cannot be Edit");
            return false;
        }
        return true;
    }
    public boolean DeletePR(String prid,String suppid,String Filetype){
        List<String> contents= Readfile(Filetype);
        int i=0;
        for (String content :contents){//Find line number
            String[] detail=content.split(",");
            if (detail[0].equals(prid)&&detail[3].equals(suppid)){
                contents.remove(i);
                break;
            }
            i++;
        }
        try(PrintWriter pw= new PrintWriter(Filepath.valueOf(Filetype).getPath())){
            for (String line:contents){
                pw.println(line);
            }
        }
        catch(Exception ex){
            System.out.println("Document cannot be Edit");
            return false;
        }
        return true;
    }
    
}
