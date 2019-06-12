package game;

public class NatureFeature
{
    public int featurePosition; // store the feature position 
    public String featureType;  // store the feature name
    public int spacePenalty;  // store the value of feature penalty 

    public NatureFeature()
    {
        featurePosition = 0;
        featureType = "";
        spacePenalty = 0;

    }

    public NatureFeature(int position, int penalty, String type)
    {
        featurePosition = position;
        featureType = type;
        spacePenalty = penalty;
    }

    public int getFeaturePosition()
    {
        return featurePosition;
    }

    public String getFeatureType()
    {
        return featureType;
    }

    public int getSpacePenalty()
    {
        return spacePenalty;
    }

    public void setFeaturePosition(int position)
    {
        featurePosition = position;
    }

    public void setFeaturType(String type)
    {
        featureType = type;
    }

    public void setSpacePenalty(int penalty)
    {
        spacePenalty = penalty;
    }

}
