/**
 *
 * Created by Michael Agaruwa
 * CMPSC 463
 * Stable Sorting with Keys
 * Due Date: Monday 03, 2021
 * Description: This program extracts bibTex entries from a .bib file,
 *              parses it, sorts based on the year and identifier,
 *              and then stores the sorted BibTex in an output file
 */

package com.company;

import java.util.regex.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.FileWriter;

public class Main extends BibTex
{
    public static void main(String[] args)throws Exception
    {
        printWithDelays("Enter a file path to load: ",TimeUnit.MILLISECONDS, 100);
        Scanner reader = new Scanner(System.in);
        String filepath = reader.nextLine();  //"icse_seet20.bib";
        FileWriter writer = new FileWriter("Sorted_BibTex.bib"); //output file

        try
        {
            String fileContent = readFileAsString(filepath);  //convert contents of .bib file to a string
            Pattern p = Pattern.compile("\\n[\\n]+"); //create pattern from regex to split at new line
            String[] citeString = p.split(fileContent); //split string at empty line and store each entry in the array
            BibTex[] bibArray = new BibTex[citeString.length]; //BibTex array to store each object
            String line ="\n----------------------------------------------------------------------";
            String id = "Sorting by Identifier.......";
            String year = "Sorting by Year.......";

            for(int i  = 0; i < citeString.length; ++i)
            {
                BibTex bt = new BibTex();               //create new bibTex object
                bt.id = getId(citeString[i]);        //get id from string
                bt.year = getYear(citeString[i]);    //get year from string
                bt.bibEntry = citeString[i];            //get each bibTex entry
                bibArray[i] = bt;                       //store each bibTex object in a bibTex array
            }
            printWithDelays("Enter a choice:\n" +
                            "1: Sort by identifier and year\n" +
                            "2: Sort by year and identifier\n" +
                            "3: Sort by identifier only\n" +
                            "4: Sort by year only\n",TimeUnit.MILLISECONDS,20);

                switch(reader.nextInt())
                {
                    case 1:
                        printWithDelays("\n" + id, TimeUnit.MILLISECONDS,100);
                        System.out.println(line);
                        BibTex.sortByID(bibArray);
                        for (BibTex bibTex : bibArray)
                        {
                            writer.write(bibTex.bibEntry + "\n\n");
                        }
                        printWithDelays("\n" + year, TimeUnit.MILLISECONDS,100);
                        System.out.println(line+ "\n\n");
                        BibTex.sortByYear(bibArray);
                        for (BibTex bibTex : bibArray)
                        {
                            writer.write(bibTex.bibEntry + "\n\n");
                        }
                        writer.close();
                        break;

                    case 2:
                       printWithDelays("\n" + year, TimeUnit.MILLISECONDS,100);
                        System.out.println(line +"\n\n");
                        BibTex.sortByYear(bibArray);
                        for (BibTex bibTex : bibArray)
                        {
                            writer.write(bibTex.bibEntry + "\n\n");
                        }
                        printWithDelays("\n" + id, TimeUnit.MILLISECONDS,100);
                        System.out.println(line + "\n\n");
                        BibTex.sortByID(bibArray);
                        for (BibTex bibTex : bibArray)
                        {
                            writer.write(bibTex.bibEntry + "\n\n");
                        }
                        writer.close();
                        break;

                    case 3:
                        printWithDelays("\n" +id, TimeUnit.MILLISECONDS,100);
                        System.out.println(line + "\n\n");
                        BibTex.sortByID(bibArray);
                        for (BibTex bibTex : bibArray)
                        {
                            writer.write(bibTex.bibEntry + "\n\n");
                        }
                        writer.close();
                        break;

                        case 4:
                            printWithDelays("\n" + year, TimeUnit.MILLISECONDS,100);
                            System.out.println(line +"\n\n");
                            BibTex.sortByYear(bibArray);
                            for (BibTex bibTex : bibArray)
                            {
                                writer.write(bibTex.bibEntry + "\n\n");
                            }
                            writer.close();
                            break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + reader.nextInt());
                }
            reader.close();
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
    }
}