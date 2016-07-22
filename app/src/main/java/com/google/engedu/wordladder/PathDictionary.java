package com.google.engedu.wordladder;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PathDictionary {
    private static final int MAX_WORD_LENGTH = 4;
    private static HashSet<String> words = new HashSet<>();

    public PathDictionary(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return;
        }
        Log.i("Word ladder", "Loading dict");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        Log.i("Word ladder", "Loading dict");
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() > MAX_WORD_LENGTH) {
                continue;
            }
            words.add(word);
        }
    }

    public boolean isWord(String word) {
        return words.contains(word.toLowerCase());
    }

    private ArrayList<String> neighbours(String word) {
        return new ArrayList<String>();
    }

    public ArrayList<String> findPath(String start, String end) {

        if (words.contains(start) && words.contains(end)) {

            HashSet<String> dictionary = words;
            ArrayList<String> path = new ArrayList<>();
            path.add(start);

            Queue<Ladder> q = new LinkedList<Ladder>();

            q.add(new Ladder(path, start, 1));
            dictionary.remove(start);

            while(!q.isEmpty()&&!q.peek().equals(end)){

                Ladder ladder = q.remove();

                if(ladder.getLastword().equals(end)){
                    return ladder.getPath();
                }

                Iterator<String> i = dictionary.iterator();
                while (i.hasNext()){
                    String s = i.next();

                    if(differByOne(s,ladder.getLastword())){

                        ArrayList<String> list = new ArrayList<>(ladder.getPath());
                        list.add(s);

                        q.add(new Ladder(list,s,ladder.length+1));

                        i.remove();
                    }

                }
            }

            if(!q.isEmpty()){
                return q.peek().getPath();
            }
        }
            return null;


    }

    boolean differByOne(String a,String b){
        if(a.length()!=b.length()){
            return false;
        }
        else{
            int dif = 0;
            for(int i=0;i<a.length();i++){
                if(a.charAt(i)!=b.charAt(i)){
                    dif++;
                }
            }

            if(dif==1)
                return true;
            else
                return false;
        }
    }
}


class Ladder{

    ArrayList<String> path;
    String lastword;
    int length;

    Ladder(ArrayList<String> list){

        this.path = list;
    }

    Ladder(ArrayList<String> list,String lastword,int length){
        this.path = list;
        this.lastword = lastword;
        this.length = length;
    }

    public ArrayList<String> getPath() {
        return path;
    }

    public void setPath(ArrayList<String> path) {
        this.path = path;
    }

    public String getLastword() {
        return lastword;
    }

    public void setLastword(String lastword) {
        this.lastword = lastword;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}