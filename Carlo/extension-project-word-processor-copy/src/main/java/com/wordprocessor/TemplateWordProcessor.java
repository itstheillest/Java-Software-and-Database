package com.wordprocessor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class TemplateWordProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Step 1: Collect user information
            System.out.println("=== Personal Information Form ===");
            System.out.print("Enter your Last Name: ");
            String lastName = scanner.nextLine();
            
            System.out.print("Enter your First Name: ");
            String firstName = scanner.nextLine();
            
            System.out.print("Enter your Sex (M/F): ");
            String sex = scanner.nextLine();
            
            
            // Step 2: Load the template
            System.out.println("\nLoading template...");
            FileInputStream templateFile = new FileInputStream("appointmentFormDocx.docx");
            XWPFDocument document = new XWPFDocument(templateFile);
            
            // Step 3: Replace placeholders in all paragraphs
            System.out.println("Processing template...");
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String text = run.getText(0);
                    if (text != null) {
                        // Replace placeholders with actual data
                        text = text.replace("{{FIRST_NAME}}", firstName);
                        text = text.replace("{{LAST_NAME}}", lastName);
                        text = text.replace("{{SEX}}", sex);
                        run.setText(text, 0);
                        
                        // Set font properties for the replaced text
                        if (text.contains(firstName) || text.contains(lastName) || text.contains(sex)) {
                            run.setFontFamily("Arial");  // Change font
                            run.setFontSize(12);          // Change size
                            run.setBold(true);            // Make bold (optional)
                            // run.setItalic(true);       // Make italic (optional)
                            // run.setColor("FF0000");    // Set color to red (optional)
                        }
                    }
                }
            }
            
            // Step 4: Save the processed document
            // Option 1: Save to a specific folder (change path as needed)
            // "C:\Users\\carlo\\OneDrive\\Documents\\Programming\\Java - Eclipse\\word-template-processor\\output"
            String outputFolder = "C:\\Users\\carlo\\OneDrive\\Documents\\Programming\\Java - Eclipse\\extension-project-word-processor-copy\\output\\";
            String outputFileName = outputFolder + lastName + "_" + firstName + "_Processed.docx";
            
            // String outputFileName = System.getProperty("user.home") + "\\Documents\\" + lastName + "_" + firstName + "_Processed.docx";
            
            // Option 2: Save to Desktop (uncomment to use)
            // String outputFileName = System.getProperty("user.home") + "\\Desktop\\" + lastName + "_" + firstName + "_Processed.docx";
            
            // Option 3: Save to current project folder (original behavior)
            // String outputFileName = lastName + "_" + firstName + "_Processed.docx";
            
            FileOutputStream out = new FileOutputStream(outputFileName);
            document.write(out);
            
            // Clean up
            out.close();
            document.close();
            templateFile.close();
            
            System.out.println("SUCCESS! Document created: " + outputFileName);
            System.out.println("Your template has been processed with the user data!");
            
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Make sure 'template.docx' exists in your project folder!");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}