package com.example.applicationrecruitment.models;

import com.example.applicationrecruitment.MainApplication;
import com.example.applicationrecruitment.graph.Graph;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AHP {

    public String headId = "";
    public List<PairwiseComparison> comparisons = new ArrayList<>();
    public List<String> alternatives = new ArrayList<>();

    public static boolean idOccupied( String id ){
        for( PairwiseComparison comparison : MainApplication.AHP.comparisons ){
            if (comparison.id.equals(id)){
                return true;
            }
        }
        return MainApplication.AHP.headId.equals(id);
    }

    public static void initComparisons(){
        if( MainApplication.AHP.comparisons.size() > 0 ){
            return;
        }
        PairwiseComparison comparison = new PairwiseComparison();
        comparison.alternatives = MainApplication.AHP.alternatives;
        comparison.id = MainApplication.AHP.headId;
        MainApplication.AHP.comparisons.add(comparison);
    }

    public static List<String> getAlternativeParents(){
        List<String> parents = new ArrayList<>();
        for( PairwiseComparison comparison : MainApplication.AHP.comparisons ){
            if( areBasicAlternatives(comparison.alternatives, MainApplication.AHP.alternatives) ){
                parents.add(comparison.id);
            }
        }
        return parents;
    }

    public static boolean areBasicAlternatives(List<String> alternatives, List<String> basic ) {
        return alternatives.contains(basic.get(0));
    }

    public static void saveToJson() {
        if (MainApplication.currentFile == null) {
            return;
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (Writer writer = new FileWriter(MainApplication.currentFile)) {
            gson.toJson(MainApplication.AHP, AHP.class, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromJson() {
        try {
            JsonReader reader = new JsonReader(new FileReader(MainApplication.currentFile));
            MainApplication.AHP = new Gson().fromJson(reader, AHP.class);
            Graph.draw();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static class Rebuilder {

        String oldHeadId;
        List<String> oldAlternatives;

        public Rebuilder(String headId, List<String> alternatives) {
            oldHeadId = headId;
            oldAlternatives = alternatives;
        }

        public void invoke() {
            if( oldAlternatives == null || oldAlternatives.size() == 0 ){ return; }
            if( oldAlternatives != MainApplication.AHP.alternatives ){
                System.out.println("rebuilder alternatives");
                for( PairwiseComparison comparison : MainApplication.AHP.comparisons ){
                    if( comparison.alternatives != null ){
                        if( AHP.areBasicAlternatives(comparison.alternatives, oldAlternatives)){
                            comparison.alternatives = MainApplication.AHP.alternatives;
                        }
                    }
                }
            }
            if( !oldHeadId.equals(MainApplication.AHP.headId) ){
                for( PairwiseComparison comparison : MainApplication.AHP.comparisons ){
                    if( comparison.id.equals(oldHeadId) ){
                        comparison.id = MainApplication.AHP.headId;
                    }
                    if( comparison.parents != null ){
                        for( int i=0; i<comparison.parents.size(); i++ ){
                            if( comparison.parents.get(i).equals(oldHeadId) ){
                                comparison.parents.set(i, MainApplication.AHP.headId);
                            }
                        }
                    }
                    if( comparison.alternatives != null ){
                        for( int i=0; i<comparison.alternatives.size(); i++ ){
                            if( comparison.alternatives.get(i).equals(oldHeadId) ){
                                comparison.alternatives.set(i, MainApplication.AHP.headId);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void print(){
        System.out.println(
                new GsonBuilder().setPrettyPrinting().create().toJson(MainApplication.AHP)
        );
    }
}
