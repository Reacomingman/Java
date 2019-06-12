package game;

import java.util.Random;

import java.util.ArrayList;


public class Trail
{
    private NatureFeature[] features;  // store a number of  NatureFeature

    public Trail()
    {
        features = new NatureFeature[4];
    }

    public NatureFeature[] getFeatures()
    {
        return features;
    }

    /**
     * Here I assume that each action can appear on only one postion  .
     */
    public ArrayList<Integer> setFeatures(int length) // return the  ArrayList which stores all random feature positions
    {
        Random random = new Random();       
        ArrayList<Integer> featuresPositions = new ArrayList<Integer>();

        int a = random.nextInt(length - 2) + 1;  // get a random value in [1, trailLength - 1), so features will not appear at start point and end point
        featuresPositions.add(a);

        int b = random.nextInt(length - 2) + 1;
        while(featuresPositions.contains(b))   // check if the arraylist has the number, so as to have different number in the arraylist
        {
            b = random.nextInt(length - 2) + 1;
        } 
        featuresPositions.add(b);

        int c = random.nextInt(length - 2) + 1;
        while(featuresPositions.contains(c) == true)   
        {
            c = random.nextInt(length - 2) + 1;
        } 
        featuresPositions.add(c);

        int d = random.nextInt(length - 2) + 1;
        while(featuresPositions.contains(d) == true)   
        {
            d = random.nextInt(length - 2) + 1;
        } 
        featuresPositions.add(d);

        NatureFeature creek = new NatureFeature();
        creek.setFeaturePosition(a);
        creek.setFeaturType("Creek");
        creek.setSpacePenalty(-2);
        features[0] = creek;

        NatureFeature bridge = new NatureFeature();
        bridge.setFeaturePosition(b);
        bridge.setFeaturType("Bridge");
        bridge.setSpacePenalty(4);
        features[1] = bridge;

        NatureFeature fallenTree = new NatureFeature();
        fallenTree.setFeaturePosition(c);
        fallenTree.setFeaturType("Fallen tree");
        fallenTree.setSpacePenalty(-3);
        features[2] = fallenTree;

        NatureFeature landSlide = new NatureFeature();
        landSlide.setFeaturePosition(d);
        landSlide.setFeaturType("Landslide");
        landSlide.setSpacePenalty(-5);
        features[3] = landSlide;

        return featuresPositions;
    } 

      
}
