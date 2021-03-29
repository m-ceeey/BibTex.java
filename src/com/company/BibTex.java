package com.company;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

class BibTex
{
    protected String bibEntry;
    protected String id;
    protected int year;

    protected static void printWithDelays(String data, TimeUnit unit, long delay) throws InterruptedException
    {
        for (char ch : data.toCharArray())
        {
            System.out.print(ch);
            unit.sleep(delay);
        }
    }

    protected static String readFileAsString(String fileName)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    //comparator class for sorting by year
    private static class yearOrder implements Comparator<BibTex>
    {
        public int compare(BibTex a, BibTex b)
        {
            return a.year - b.year;
        }
    }
    //comparator class for sorting by id
    private static class idOrder implements Comparator<BibTex>
    {
        public int compare(BibTex a, BibTex b)
        {
            return a.id.compareTo(b.id);
        }
    }

    private static Comparator<BibTex> orderById()
    {
        return new idOrder();
    }

    private static Comparator<BibTex> orderByYear()
    {
        return new yearOrder();
    }

    //getter method for id
    protected static String getId(String str)
    {
        Pattern idPattern = Pattern.compile("[@].*[{](.+?),"); //regular expression to get the id
        Matcher idMatcher = idPattern.matcher(str);
        if (idMatcher.find())
            return idMatcher.group(1);
        return "Null_Id_Exception";
    }

    //getter method for year
    protected static int getYear(String str)
    {
        Pattern yearPattern = Pattern.compile("year=[{]*(.+?)}"); //regular expression to get the year
        Matcher yearMatcher = yearPattern.matcher(str);
        if (yearMatcher.find())
            return Integer.parseInt(yearMatcher.group(1));      //if the year exists, return it as an integer
        else
            return 2021;                                        //default year is 2021
    }

    private static boolean less(Comparator c, BibTex v, BibTex w)
    { return c.compare(v, w) < 0; }

    private static void exch(BibTex[] a, int i, int j)
    {
        BibTex swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    //implementation of insertion sort with comparator
    private static void insertionSort(BibTex[] a, Comparator comparator)
    {
        int N = a.length;
        for (int i = 0; i < N; i++)
            for (int j = i; j > 0 && less(comparator, a[j], a[j-1]); j--)
                exch(a, j, j-1);
    }

    private static void mergeSort(BibTex[] a, Comparator comparator)
    {
        sort(comparator,a, 0, a.length - 1);
    }
    private static void sort(Comparator comparator, BibTex[] a, int low, int high)
    {
        if(high<=low)
            return;
        int mid = low +(high-low)/2;
        sort(comparator,a, low, mid);
        sort(comparator, a, mid+1, high);
        merge(comparator, a,low,mid,high);
    }

    private static void merge(Comparator comparator, BibTex[] a, int low, int mid, int high)
    {
        int i = low, j = mid+1;
        BibTex[] aux = new BibTex[a.length];

        for(int k = low; k<=high; ++k)
            aux[k] = a[k];

        for(int k = low;k<=high;++k)
        {
            if(i>mid)
                aux[k] = a[j++];
            else if (j>high)
                aux[k] = a[i++];
            else if(less(comparator, a[j], a[i]))
                aux[k] = a[j++];
            else
                aux[k] = a[i++];
        }
    }

    //Method sorts each bibtex by Year
    protected static void sortByID(BibTex[] bibObj)
    {
        //mergeSort(bibObj, BibTex.orderById());
        insertionSort(bibObj, BibTex.orderById());
        for (BibTex bibTex : bibObj)
            System.out.println(bibTex.bibEntry);
    }

    //Method sorts each bibtex by Year
    protected static void sortByYear(BibTex[] bibObj)
    {
        //mergeSort(bibObj, BibTex.orderByYear());
        insertionSort(bibObj, BibTex.orderByYear());
        for (BibTex bibTex : bibObj)
            System.out.println(bibTex.bibEntry);
    }
}