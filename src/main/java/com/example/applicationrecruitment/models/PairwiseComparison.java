package com.example.applicationrecruitment.models;

import com.example.applicationrecruitment.MainApplication;
import com.example.applicationrecruitment.decision.Calculate;
import com.example.applicationrecruitment.graph.Graph;

import java.util.ArrayList;
import java.util.List;

public class PairwiseComparison {

    public String id;
    public List<String> parents;
    public List<String> alternatives;

    public double[][] weightRatios;
    private double[] weightVector;

    public transient boolean cutPreviousParents;

    public double[] getWeightVector() {
        if( weightVector == null ){
            weightVector = Calculate.weightVector(this);
        }
        return weightVector;
    }

    public static PairwiseComparison get(String id){
        for( PairwiseComparison comparison : MainApplication.AHP.comparisons ){
            if( comparison.id.equals(id)){
                return comparison;
            }
        }
        return null;
    }

    public static void addCriterion(PairwiseComparison criterion) {
        criterion.print();
        MainApplication.AHP.comparisons.add(criterion);
        if( !AHP.areBasicAlternatives(criterion.alternatives, MainApplication.AHP.alternatives) ){
            for( String childId : criterion.alternatives ){
                PairwiseComparison child = PairwiseComparison.get(childId);
                if ( child != null) {
                    if( criterion.cutPreviousParents ){
                        child.parents.clear();
                    }
                    child.parents.add(criterion.id);
                }
            }
        }
        for( String parentId : criterion.parents ){
            PairwiseComparison parent = PairwiseComparison.get(parentId);
            if(  AHP.areBasicAlternatives(parent.alternatives, MainApplication.AHP.alternatives) ){
                parent.alternatives = new ArrayList<>();
            }else{
                if (criterion.cutPreviousParents){
                    parent.cleanAlternatives();
                }
            }
            parent.alternatives.add(criterion.id);
        }
        AHP.print();
        Graph.draw();
        reset(false);
    }

    private void cleanAlternatives() {
        List<String> cleaned = new ArrayList<>();
        for( String alternative : alternatives ){
            PairwiseComparison child = PairwiseComparison.get(alternative);
            if (child != null && child.parents.contains(id)) {
                cleaned.add(alternative);
            }
        }
        alternatives = cleaned;
    }

    public void setParent(String parent) {
        this.parents = new ArrayList<>();
        parents.add(parent);
    }

    public static List<String> getAllParentsCriterion(List<String> children ){
        if( children.size() < 1 || children.contains(MainApplication.AHP.headId)){
            return null;
        }
        List<String> criterions = new ArrayList<>();
        for( String childId : children ){
            PairwiseComparison child = PairwiseComparison.get(childId);
            for( String parentId : child.parents ){
                if( !criterions.contains(parentId) ){
                    criterions.add(parentId);
                }
            }
        }
        if( criterions.size() == 1 && criterions.get(0).equals(MainApplication.AHP.headId)){
            return null;
        }else{
            criterions.remove(MainApplication.AHP.headId);
            return criterions;
        }
    }

    public void print() {
        System.out.println("Comparison: "+ id
                + " | A:"+alternatives.toString() + "\ncutPreviousParents: " + cutPreviousParents);
    }

    public static void reset(boolean all) {
        for( PairwiseComparison comparison : MainApplication.AHP.comparisons ){
            comparison.weightVector = null;
            if( all ){
                comparison.weightRatios = null;
            }
        }
    }
}
