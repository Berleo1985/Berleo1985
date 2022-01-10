package com;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
public class programmingWIthExceptions {
    private static void copyStream(InputStream in, OutputStream out) throws IOException {
        int oneByte = in.read();
        while (oneByte >= 0) { // negative value indicates stream ends
            out.write(oneByte);
            oneByte = in.read();
        }
    }
    public static void main(String[] args) {
        //Input and Output streams must be null as initial value
        InputStream in = null;
        OutputStream out = null;
        //enter our url
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter URL: ");
        String urlString = reader.nextLine();
        URL url;
        try {
            url = new URL(urlString);
            in = url.openStream();
            System.out.print("Enter filename: ");
            String fileName = reader.nextLine();
            out = new FileOutputStream(fileName);
            copyStream(in, out);
            // for incorrect url format
        } catch (MalformedURLException ex) {
            System.out.println("Invalid URL.");
        }catch (FileNotFoundException ex) {
            System.out.println("File is not found");
            //Will need our attention if stream cannot be opened
            //Indicates that the url is faulty
        } catch(IOException ex){
            System.out.println("No such website.");
            // close in and out streams and see if in and out is not null
        } finally{
            if(in != null && out != null){
                try {
                    in.close();
                    out.close();
                } catch (IOException ex) {
                    System.out.println("Input invalid.");
                }
            }
        }
    }
}
